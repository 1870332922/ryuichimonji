package mod.ryuichimonji.items;

import javax.annotation.Nonnull;

import mod.ryuichimonji.RyuIchimonji;
import mod.ryuichimonji.creativetab.TabRyuIchimonji;
import mods.flammpfeil.slashblade.entity.EntityBladeStand;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemBladeStand0 extends Item{
	public ItemBladeStand0() {
		this.setUnlocalizedName(RyuIchimonji.MODID + "."+ "bladestandtype0");
		this.setRegistryName("bladestandtype0");
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setCreativeTab(TabRyuIchimonji.tabRyuIchimonji);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		BlockPos pos = player.getPosition();
		
		if(!world.isRemote) {
            EntityBladeStand e = new EntityBladeStand(world);
            e.setStandType(0);
            e.setPositionAndRotation(pos.getX() + 0.5 ,pos.getY() + 0.5 ,pos.getZ() + 0.5,Math.round(player.rotationYaw / 45.0f) * 45.0f + 180.0f,e.rotationPitch);
            world.spawnEntity(e);
		}
		stack.shrink(1);
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

}
