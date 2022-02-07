package mod.ryuichimonji.items;

import javax.annotation.Nonnull;

import mod.ryuichimonji.RyuIchimonji;
import mod.ryuichimonji.creativetab.TabRyuIchimonji;
import mod.ryuichimonji.entity.EntityDriveEievui;
import mod.ryuichimonji.entity.EntityDriveEievuiEX;
import mod.ryuichimonji.potion.PotionRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemLoong extends Item{
	public ItemLoong(){
		this.setUnlocalizedName(RyuIchimonji.MODID + "."+ "loong");
		this.setRegistryName("loong");
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setCreativeTab(TabRyuIchimonji.tabRyuIchimonji);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		
		if((player.experienceLevel <= 1 && !player.isCreative())) {
			return ActionResult.newResult(EnumActionResult.FAIL, stack);
		}
		
		world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if (!world.isRemote) {
			if(player.experienceLevel > 1 || player.isCreative()) {
				if(!player.isCreative()) {
					player.addExperienceLevel(-1);
					player.setAir(690);
				}
					
				player.addPotionEffect(new PotionEffect(PotionRegistry.PotionUnseenAidByLoong, 320, 0));
				
				player.playSound(SoundEvents.BLOCK_GRASS_BREAK, 1.0f, 1.5f);
				for(int i=1;i<3;i++) {
					int roll = 45;
					if(i == 2 ) {
						roll = -45;
					}
					EntityDriveEievuiEX entityDrive = new EntityDriveEievuiEX(world, player, 4.0F, false, roll);
		            if (entityDrive != null) {
		            	entityDrive.setInitialSpeed(i);
		                entityDrive.setLifeTime(10);
		                entityDrive.setColor(0x00FFFF);
		                
		                world.spawnEntity(entityDrive);
		            }
				}
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

}
