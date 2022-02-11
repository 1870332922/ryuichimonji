package mod.ryuichimonji.biomes;

import java.util.List;
import java.util.Random;

import mod.ryuichimonji.entity.EntityMobFelid;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomePatum extends Biome{
	
	protected final WorldGenAbstractTree treeGen = new WorldGenBigTree(false);

	public BiomePatum(BiomeProperties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
		
		spawnableMonsterList.clear();
		spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
		
		spawnableWaterCreatureList.clear();
		
		spawnableCreatureList.clear();
		spawnableCreatureList.add(new SpawnListEntry(EntityMobFelid.class, 10, 4, 6));
		
		spawnableCaveCreatureList.clear();
		spawnableCaveCreatureList.add(new SpawnListEntry(EntityBat.class, 10, 8, 8));
		
		this.decorator.treesPerChunk = 1;
		this.decorator.flowersPerChunk = 16;
		this.decorator.grassPerChunk = 32;
		
	}
	
	@Override
	public float getSpawningChance() {
		// okay, 20% more animals
		return 0.12F;
	}


	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random random) {

		return BIG_TREE_FEATURE;
	}
	
	
	public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos){
        return BlockFlower.EnumFlowerType.BLUE_ORCHID;
    }
	
	@Override
	public void addDefaultFlowers(){
        addFlower(Blocks.RED_FLOWER.getDefaultState().withProperty(Blocks.RED_FLOWER.getTypeProperty(), BlockFlower.EnumFlowerType.BLUE_ORCHID), 40);
    }

	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random random) {
		return new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
	}
	
}
