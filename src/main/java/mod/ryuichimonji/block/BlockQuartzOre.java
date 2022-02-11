package mod.ryuichimonji.block;

import java.util.Random;

import mod.ryuichimonji.RyuIchimonji;
import mod.ryuichimonji.creativetab.TabRyuIchimonji;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockQuartzOre extends Block{

	public BlockQuartzOre() {
		super(Material.ROCK);
		this.setUnlocalizedName(RyuIchimonji.MODID +"quartz_ore");
		this.setRegistryName("quartz_ore");
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(3.0F);
		this.blockResistance = 30.0F;
		this.blockHardness = 3.0F;
		this.blockSoundType = SoundType.STONE;
		
		this.setCreativeTab(TabRyuIchimonji.tabRyuIchimonji);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random chance, int fortune){
	    return Items.QUARTZ;
	}

	@Override
	public int quantityDropped(Random amount){
	    return amount.nextInt(4) + 1;
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune){
		Random amount = new Random();
        return amount.nextInt(5) + 2;
    }
}
