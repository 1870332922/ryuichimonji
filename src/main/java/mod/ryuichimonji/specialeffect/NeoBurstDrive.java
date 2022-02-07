package mod.ryuichimonji.specialeffect;

import java.util.Random;

import mod.ryuichimonji.entity.EntityDriveEievui;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.TagPropertyAccessor;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade.ComboSequence;
import mods.flammpfeil.slashblade.specialeffect.IRemovable;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NeoBurstDrive implements ISpecialEffect, IRemovable{
	
	private static final String EffectKey = "NeoBurstDrive";
	private static final int COST = 2;
	private static final int NO_COST_DAMAGE = 1;

	@Override
	public boolean canCopy(ItemStack arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canRemoval(ItemStack arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getDefaultRequiredLevel() {
		// TODO Auto-generated method stub
		return 30;
	}

	@Override
	public String getEffectKey() {
		// TODO Auto-generated method stub
		return EffectKey;
	}

	@Override
	public void register() {
		// TODO Auto-generated method stub
		SlashBladeHooks.EventBus.register(this);
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
	
	@SubscribeEvent
	public void onUpdateItemSlashBlade(SlashBladeEvent.OnUpdateEvent event){
		if (!SpecialEffects.isPlayer(event.entity))
			return;
		
		EntityPlayer player = (EntityPlayer)event.entity;
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
		ItemSlashBlade.ComboSequence seq = ItemSlashBlade.getComboSequence(tag);
		if(!useBlade(seq)) return;
        if (!useBlade(seq))
			return;
		if (ItemSlashBlade.IsBroken.get(tag).booleanValue())
			return;

		if(SpecialEffects.isEffective(player, event.blade, this) != SpecialEffects.State.Effective) {
			return;
		}

		spawnParticle(EnumParticleTypes.SPELL_WITCH, player, 1, 1.0);

		PotionEffect haste = player.getActivePotionEffect(MobEffects.HASTE);
        int check = haste != null ? haste.getAmplifier() != 1 ? 3 : 4 : 2;
    
		if (player.swingProgressInt != check)
			return;

		doAddAttack(event.blade, player, seq);
	}
  
	public void doAddAttack(ItemStack stack, EntityPlayer player, ItemSlashBlade.ComboSequence setCombo){
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
		World world = player.world;

		if (!ItemSlashBlade.ProudSoul.tryAdd(tag, -COST, false)) {
			ItemSlashBlade.damageItem(stack, NO_COST_DAMAGE, player);
			return;
		}
		
		if (world.isRemote)
			return;
		
		float baseModif = ((ItemSlashBlade)stack.getItem()).getBaseAttackModifiers(tag);
		int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
		
		float magicDamage = baseModif + level;
		int rank = StylishRankManager.getStylishRank(player);
		if (rank >= 5) {
			magicDamage += ItemSlashBlade.AttackAmplifier.get(tag)*(level/5.0 + 0.5f);
		}
		
		Random rand = player.getRNG();
		int color = 0x3333FF;
        
        TagPropertyAccessor.TagPropertyInteger SSColor = new TagPropertyAccessor.TagPropertyInteger("SummonedSwordColor");
        
        if (SSColor.exists(tag)) {
        	color = (SSColor.get(tag));
        }
		
		EntityDriveEievui entityDrive = new EntityDriveEievui(world, player, magicDamage, false, rand.nextFloat()*10.0F - setCombo.swingDirection);
		entityDrive.setInitialSpeed(1.5f);
		entityDrive.setLifeTime(10);
		entityDrive.setColor(color);

		world.spawnEntity(entityDrive);
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
