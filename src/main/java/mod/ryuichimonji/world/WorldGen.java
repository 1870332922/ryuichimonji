package mod.ryuichimonji.world;

import java.util.Random;

import mod.ryuichimonji.block.BlockRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator{
	  public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
		  
	    if (world.provider.getDimension() == 0 || world.provider.getDimension() == 7){
	      generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
	    }
	  }

	  private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider){
	    generateOre(BlockRegistry.quartzore.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, random.nextInt(12) + 6, 10);
	  }

	  private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances){
	    int deltaY = maxY - minY;

	    for (int iAttempt = 0; iAttempt < chances; iAttempt++){
	      BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
	      WorldGenMinable generator = new WorldGenMinable(ore, size);
	      generator.generate(world, random, pos);
	    }
	  }
}
