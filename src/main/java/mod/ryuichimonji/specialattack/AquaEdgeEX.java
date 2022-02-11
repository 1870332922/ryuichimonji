package mod.ryuichimonji.specialattack;

import java.util.List;

import mod.ryuichimonji.entity.EntityAquaEdgeEX;
import mod.ryuichimonji.entity.EntityDriveEievui;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class AquaEdgeEX extends SpecialAttackBase{
	
	public static String AttackType = StylishRankManager.AttackTypes.registerAttackType("AquaEdgeEx", 0.5F);

	@Override
	public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
		// TODO Auto-generated method stub
		World world = player.world;
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
		
		
		player.playSound(SoundEvents.ENTITY_PLAYER_SWIM, 1.0f, 1.5f);
		world.spawnParticle(EnumParticleTypes.WATER_SPLASH, player.posX, player.posY, player.posZ, 2.0, 2.0, 2.0);
		
		for (int i = 0; i < 50; i++){
	      double d0 = player.getRNG().nextGaussian() * 0.02D;
	      double d1 = player.getRNG().nextGaussian() * 0.02D;
	      double d2 = player.getRNG().nextGaussian() * 0.02D;
	      double d3 = 10.0D;
	      world.spawnParticle(EnumParticleTypes.WATER_SPLASH, player.posX + (player.getRNG().nextFloat() * player.width * 2.0F - player.width - d0 * d3) * 2.0D, player.posY, player.posZ + (player.getRNG().nextFloat() * player.width * 2.0F - player.width - d2 * d3) * 2.0D, d0, d1, d2);
	    }
		
		if (player.isBurning()) {
			player.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,
					 0.7f,
					 1.2f + 0.8f*player.getRNG().nextFloat());
			player.extinguish();
		}
		
		if (!world.isRemote) {

			if (!ItemSlashBlade.ProudSoul.tryAdd(tag, Integer.valueOf(-40), false)) {
		        stack.damageItem(10, player);
		      }
			
			ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();
			
			int rank = StylishRankManager.getStylishRank(player);
			float damage = blade.getBaseAttackModifiers(tag);
			if (rank >= 5) {
				int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
				damage += ItemSlashBlade.AttackAmplifier.get(tag) * (0.5f + level / 5.0f);
			}
			
			for(int i = 0; i < 7;i++){
				EntityAquaEdgeEX entity = new EntityAquaEdgeEX(world, player, damage);
				entity.setLifeTime(10);
				entity.setLocationAndAngles(player.posX, player.posY + (double)player.getEyeHeight()/2D, player.posZ, player.rotationYaw + 60 * i, 0);
				entity.setDriveVector(0.5f);
				entity.setLifeTime(10);
				entity.setIsMultiHit(false);
				entity.setRoll(90.0f);
				entity.setColor(0x0000FF);
				
				entity.setInitialPosition(
						player.posX,
						player.posY + entity.getYOffset(),
						player.posZ,
						player.rotationYaw + 60 * i,
						0,
						90.0f - ItemSlashBlade.ComboSequence.Iai.swingDirection,
						0.5F);
				
				if(entity!=null) {
					world.spawnEntity(entity); 
				}
			}

		}
		ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Battou);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "AquaEdgeEX";
	}

}
