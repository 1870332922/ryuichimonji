package mod.ryuichimonji.specialattack;

import mod.ryuichimonji.entity.EntityFlareEdgeEX;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class FlareEdgeEX extends SpecialAttackBase{
	
	public static String AttackType = StylishRankManager.AttackTypes.registerAttackType("FlareEdgeEX", 0.5F);

	@Override
	public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
		// TODO Auto-generated method stub
		World world = player.world;
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
		
		player.playSound(SoundEvents.BLOCK_LAVA_POP, 1.0f, 1.5f);
		world.spawnParticle(EnumParticleTypes.LAVA, player.posX, player.posY, player.posZ, 2.0, 2.0, 2.0);
		
		for (int i = 0; i < 50; i++){
		      double d0 = player.getRNG().nextGaussian() * 0.02D;
		      double d1 = player.getRNG().nextGaussian() * 0.02D;
		      double d2 = player.getRNG().nextGaussian() * 0.02D;
		      double d3 = 10.0D;
		      world.spawnParticle(EnumParticleTypes.LAVA, player.posX + (player.getRNG().nextFloat() * player.width * 2.0F - player.width - d0 * d3) * 2.0D, player.posY, player.posZ + (player.getRNG().nextFloat() * player.width * 2.0F - player.width - d2 * d3) * 2.0D, d0, d1, d2);
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
			
			if (7 <= rank) {
				damage *= 2.0F;
		    }
		   int maxCol = 3;
		   int maxCount = 3;
		   maxCount += rank;
		   
		   double radBaseRot = Math.toRadians(player.rotationYaw);
		   double radRot = 6.283185307179586D / maxCount;
			
		   for (int j = 0; j < maxCol; j++) {
			   for (int i = 0; i < maxCount; i++) {
				   EntityFlareEdgeEX entity = new EntityFlareEdgeEX(world, player, damage, false, 0.0F);
				   if(entity != null) {
					   double posY = player.posY + player.getEyeHeight() / 2.0D;
			            if ((maxCount % 2 == 1) && (i == Math.floor(maxCount / 2) + 1.0D) && (rank >= 5)) {
			              posY = player.posY + player.getEyeHeight();
			            }
			            
			            entity.setLocationAndAngles(player.posX + 1.0D * Math.cos(radBaseRot + radRot * i), posY, player.posZ + 1.0D * Math.sin(radBaseRot + radRot * i), player.rotationYaw, player.rotationPitch);
			            
			            entity.setDriveVector(0.8F + j * 2 / 5);
			            if ((int)(Math.random() * 10.0D + 1.0D) < 3) {
			            	world.spawnParticle(EnumParticleTypes.LAVA, entity.posX, entity.posY, entity.posZ, 3.0, 3.0, 3.0, 6);
			            }
			            entity.setIsMultiHit(true);
			            entity.setLifeTime(4 + 3 * j + i);
			            entity.setRoll(90.0F);
			            entity.setColor(0xDF0024);
			            
			            world.spawnEntity(entity);
				   }
			   }
		   }
		}
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "FlareEdgeEX";
	}

}
