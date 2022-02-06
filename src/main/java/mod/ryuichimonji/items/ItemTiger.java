package mod.ryuichimonji.items;

import javax.annotation.Nonnull;

import mod.ryuichimonji.RyuIchimonji;
import mod.ryuichimonji.creativetab.TabRyuIchimonji;
import mod.ryuichimonji.entity.EntityDriveEievui;
import mod.ryuichimonji.entity.EntityDriveEievuiEX;
import mod.ryuichimonji.entity.EntityMobFelid;
import mod.ryuichimonji.entity.EntityRegisterHander;
import mod.ryuichimonji.potion.PotionRegistry;
import mods.flammpfeil.slashblade.entity.EntityDrive;
import net.minecraft.entity.Entity;
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

public class ItemTiger extends Item{
	public ItemTiger(){
		this.setUnlocalizedName(RyuIchimonji.MODID + "."+ "tiger");
		this.setRegistryName("tiger");
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
		
		world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, SoundCategory.PLAYERS, 1.0F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if (!world.isRemote) {
			if(player.experienceLevel > 1 || player.isCreative()) {
				if(!player.isCreative()) {
					player.addExperienceLevel(-1);
					player.heal(player.getMaxHealth()-player.getHealth());
				}
				
				player.addPotionEffect(new PotionEffect(PotionRegistry.PotionUnseenAidByTiger, 320, 0));
				
				player.playSound(SoundEvents.BLOCK_ANVIL_PLACE, 1.0f, 1.5f);
				for(int i=1;i<3;i++) {
					int roll = 90;
					if(i == 2) {
						roll = 45;
					}
					EntityDriveEievuiEX entityDrive = new EntityDriveEievuiEX(world, player,4.0F , false, roll);
		            if (entityDrive != null) {
		            	entityDrive.setInitialSpeed(i);
		                entityDrive.setLifeTime(10);
		                entityDrive.setColor(0xF8F400);
		                
		                world.spawnEntity(entityDrive);
		            }
				}
			}
		}
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

}
