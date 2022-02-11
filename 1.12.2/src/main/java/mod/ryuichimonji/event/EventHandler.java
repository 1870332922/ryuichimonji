package mod.ryuichimonji.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;

import akka.event.EventBus;
import mod.ryuichimonji.entity.EntityDriveEievui;
import mod.ryuichimonji.items.ItemRegistry;
import mod.ryuichimonji.potion.PotionRegistry;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import net.minecraft.block.BlockAir;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class EventHandler {
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
		DamageSource ds = event.getSource();
		EntityLivingBase target = event.getEntityLiving();
		ItemStack tailsman  = target.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
		
		//target.setCustomNameTag(ds.getDamageType());
		
		Potion potion = PotionRegistry.PotionUnseenAid;
		if(target.isPotionActive(potion)) {
			PotionEffect effect = target.getActivePotionEffect(potion);
			if(effect.getAmplifier() <= 20.0F) {
				event.setAmount(0);
				target.hurtResistantTime = 0;
				target.setHealth((target.getMaxHealth()*0.1F + (2.0F*effect.getAmplifier())));
				if(!target.world.isRemote) {
					spawnParticle(EnumParticleTypes.HEART, target, 9 * effect.getAmplifier(), 0.8D);
				}
			}
		}
		
		Potion potionFox = PotionRegistry.PotionUnseenAidByFox;
		if(target.isPotionActive(potionFox)||(tailsman != null && tailsman.isItemEqual(new ItemStack(ItemRegistry.tailsmanfoxi)))) {
			PotionEffect effect = target.getActivePotionEffect(potionFox);
			if(ds.equals(DamageSource.ON_FIRE) 
					|| ds.equals(DamageSource.IN_FIRE) 
					|| ds.equals(DamageSource.HOT_FLOOR) 
					|| ds.equals(DamageSource.STARVE)) {
				event.setAmount(0);
				target.hurtResistantTime = 0;
				target.extinguish();
				if(!target.world.isRemote) {
					if(target.isPotionActive(potionFox)) {
						spawnParticle(EnumParticleTypes.FLAME, target, 9 * effect.getAmplifier(), 0.8D);
						ItemDye.spawnBonemealParticles(target.world, target.getPosition(), 0);
					}
					
				}
			}
		}
		
		Potion potionLoong = PotionRegistry.PotionUnseenAidByLoong;
		if(target.isPotionActive(potionLoong)||(tailsman != null && tailsman.isItemEqual(new ItemStack(ItemRegistry.tailsmanloongi)))) {
			PotionEffect effect = target.getActivePotionEffect(potionLoong);
			if(ds.equals(DamageSource.DROWN) 
					|| ds.equals(DamageSource.DRAGON_BREATH) 
					|| ds.equals(DamageSource.MAGIC) 
					|| ds.equals(DamageSource.LAVA)) {
				event.setAmount(0);
				target.hurtResistantTime = 0;
				if(!target.world.isRemote) {
					if(target.isPotionActive(potionLoong)) {
						spawnParticle(EnumParticleTypes.CRIT_MAGIC, target, 9 * effect.getAmplifier(), 0.8D);
					}
					
				}
			}
		}
		
		Potion potionTiger = PotionRegistry.PotionUnseenAidByTiger;
		if(target.isPotionActive(potionTiger)||(tailsman != null && tailsman.isItemEqual(new ItemStack(ItemRegistry.tailsmantigeri)))) {
			PotionEffect effect = target.getActivePotionEffect(potionTiger);
			if(ds.equals(DamageSource.LIGHTNING_BOLT) 
					|| ds.equals(DamageSource.FIREWORKS) 
					|| ds.equals(DamageSource.GENERIC) 
					|| ds.equals(DamageSource.OUT_OF_WORLD)) {
				event.setAmount(0);
				target.hurtResistantTime = 0;
				if(!target.world.isRemote) {
					if(target.isPotionActive(potionTiger)) {
						spawnParticle(EnumParticleTypes.SPELL_INSTANT, target, 9 * effect.getAmplifier(), 0.8D);
					}
					
				}
			}
		}
		
	}
	
	@SubscribeEvent
	public void onUpdateItemSlashBlade(SlashBladeEvent.OnUpdateEvent event){
		EntityPlayer player = (EntityPlayer)event.entity;
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
		ItemSlashBlade.ComboSequence seq = ItemSlashBlade.getComboSequence(tag);
		if(!useBlade(seq)) return;
        if (!useBlade(seq))
			return;
		if (ItemSlashBlade.IsBroken.get(tag).booleanValue())
			return;
		
		doAddAttack(event.blade, player, seq);
	}
	
	private boolean useBlade(ItemSlashBlade.ComboSequence sequence){
		if(sequence.useScabbard) {
			return false;
		}
		if(sequence == ItemSlashBlade.ComboSequence.None) {
		     return false;
		}
		if(sequence == ItemSlashBlade.ComboSequence.Noutou) {
		     return false;
		}
		return true;
	}
	
	public void doAddAttack(ItemStack stack, EntityPlayer player, ItemSlashBlade.ComboSequence setCombo){
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
		World world = player.world;
		
		if (!world.isRemote) {
			//
			{
		    	Potion potionFox = PotionRegistry.PotionUnseenAidByFox;
				Potion potionLoong = PotionRegistry.PotionUnseenAidByLoong;
				Potion potionTiger = PotionRegistry.PotionUnseenAidByTiger;
				
				if(player.isPotionActive(potionFox)) {
					PotionEffect effect = player.getActivePotionEffect(potionFox);
					for(int i=1;i<3;i++) {
						EntityDriveEievui entityDrive = new EntityDriveEievui(world, player, 4.0F * effect.getAmplifier() , false, 90.0F*i);
			            if (entityDrive != null) {
			            	entityDrive.setInitialSpeed(i);
			                entityDrive.setLifeTime(10);
			                entityDrive.setColor(0xDF0024);
			                
			                world.spawnEntity(entityDrive);
			            }
					}
				}
				
				if(player.isPotionActive(potionLoong)) {
					PotionEffect effect = player.getActivePotionEffect(potionLoong);
					for(int i=1;i<3;i++) {
						int roll = 45;
						if(i == 2 ) {
							roll = -45;
						}
						EntityDriveEievui entityDrive = new EntityDriveEievui(world, player, 4.0F * effect.getAmplifier() , false, roll);
			            if (entityDrive != null) {
			            	entityDrive.setInitialSpeed(i);
			                entityDrive.setLifeTime(10);
			                entityDrive.setColor(0x00FFFF);
			                
			                world.spawnEntity(entityDrive);
			            }
					}
				}
				
				if(player.isPotionActive(potionTiger)) {
					PotionEffect effect = player.getActivePotionEffect(potionTiger);
					for(int i=1;i<3;i++) {
						int roll = 90;
						if(i == 2) {
							roll = 45;
						}
						EntityDriveEievui entityDrive = new EntityDriveEievui(world, player,4.0F * effect.getAmplifier() , false, roll);
			            if (entityDrive != null) {
			            	entityDrive.setInitialSpeed(i);
			                entityDrive.setLifeTime(10);
			                entityDrive.setColor(0xF8F400);
			                
			                world.spawnEntity(entityDrive);
			            }
					}
				}
		    }
			//
			
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void spawnParticle(EnumParticleTypes type, Entity player, int num, double rate){

		World world = player.world;
		Random rand = new Random();
		
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
