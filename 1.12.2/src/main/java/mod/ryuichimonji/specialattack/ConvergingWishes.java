package mod.ryuichimonji.specialattack;

import java.util.Random;

import mod.ryuichimonji.potion.PotionRegistry;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ConvergingWishes extends SpecialAttackBase{
	
	public static String AttackType = StylishRankManager.AttackTypes.registerAttackType("ConvergingWishes", -1.0f);
	private static final int COST = 10;

	@Override
	public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
		
		// TODO Auto-generated method stub
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
		if (player.isBurning()) {
			player.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,
					 0.7f,
					 1.2f + 0.8f*player.getRNG().nextFloat());
			player.extinguish();
		}
		
		player.removePotionEffect(MobEffects.SLOWNESS);
		player.removePotionEffect(MobEffects.MINING_FATIGUE);
		player.removePotionEffect(MobEffects.NAUSEA);
		player.removePotionEffect(MobEffects.BLINDNESS);
		player.removePotionEffect(MobEffects.HUNGER);
		player.removePotionEffect(MobEffects.WEAKNESS);
		player.removePotionEffect(MobEffects.POISON);
		player.removePotionEffect(MobEffects.WITHER);
		
		player.addPotionEffect(new PotionEffect(PotionRegistry.PotionUnseenAid, 600, 0));
		
		if (ItemSlashBlade.ProudSoul.tryAdd(tag, -COST, false)) {

			spawnParticle(EnumParticleTypes.HEART, player, 20, 1.0);
			StylishRankManager.setNextAttackType(player, AttackType);
			StylishRankManager.doAttack(player);
	
		} else {
			spawnParticle(EnumParticleTypes.SMOKE_LARGE, player, 20, 1.0);
		}
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "ConvergingWishes";
	}
	
	public static void spawnParticle(EnumParticleTypes type, EntityPlayer player, int num, double rate){

		World world = player.world;
		Random rand = player.getRNG();
		
		for (int i = 0; i < num; i++) {
			double xSpeed = rand.nextGaussian() * 0.02;
			double ySpeed = rand.nextGaussian() * 0.02;
			double zSpeed = rand.nextGaussian() * 0.02;

			double rx = rand.nextDouble();
			double rz = rand.nextDouble();
			
			world.spawnParticle(
				type,
				player.posX + ((rx*2 - 1)*player.width  - xSpeed * 10.0)*rate,
				player.posY,
				player.posZ + ((rz*2 - 1)*player.width  - zSpeed * 10.0)*rate,
				xSpeed, ySpeed, zSpeed);
		}
	}

}
