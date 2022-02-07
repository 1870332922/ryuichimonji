package mod.ryuichimonji.specialeffect;

import java.util.List;

import mod.ryuichimonji.entity.EntityDriveEievui;
import mod.ryuichimonji.entity.EntityLightningSwordEX;
import mod.ryuichimonji.potion.PotionRegistry;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.IRemovable;
import mods.flammpfeil.slashblade.specialeffect.ISpecialEffect;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Kamui implements ISpecialEffect, IRemovable{
	
	private static final String EffectKey = "Kamui";
	private static final Potion potion = PotionRegistry.PotionUnseenAid;
	private static final Potion potionFox = PotionRegistry.PotionUnseenAidByFox;
	private static final Potion potionLoong = PotionRegistry.PotionUnseenAidByLoong;
	private static final Potion potionTiger = PotionRegistry.PotionUnseenAidByTiger;

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
		return 1;
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
	    if (sequence.useScabbard) {
	      return false;
	    }
	    if (sequence == ItemSlashBlade.ComboSequence.None) {
	      return false;
	    }
	    if (sequence == ItemSlashBlade.ComboSequence.Noutou) {
	      return false;
	    }
	    return true;
	}
	
	public void doAddAttack(ItemStack stack, EntityPlayer player, ItemSlashBlade.ComboSequence setCombo) {
		NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
		World world = player.world;
		
		int cost = -2;
		
		if (!ItemSlashBlade.ProudSoul.tryAdd(tag, Integer.valueOf(cost), false)) {
			ItemSlashBlade.damageItem(stack, 5, player);
		      return;
		}
		
		Entity target = null;
	    int entityId = ItemSlashBlade.TargetEntityId.get(tag).intValue();
	    
	    if (entityId != 0){
	      Entity tmp = world.getEntityByID(entityId);
	      if ((tmp != null) && (tmp.getDistance(player) < 30.0F)) {
	        target = tmp;
	      }
	    }
	    if (target == null) {
	      target = getEntityToWatch(player);
	    }
	    
	    if ((target != null) && (!target.isDead) && (!world.isRemote)) {
	    	float baseModif = ((ItemSlashBlade)stack.getItem()).getBaseAttackModifiers(tag);
	        int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
	        float magicDamage = baseModif * (level / 20);
	        
	        int rank = StylishRankManager.getStylishRank(player);
	        if (5 <= rank) {
	          magicDamage *= 2.0F;
	        }
	        if(player.isPotionActive(potion)) {
	        	magicDamage /= 4.0F;
	        }
	        if(player.isPotionActive(potionFox)) {
	        	player.getFoodStats().setFoodLevel(20);
	        }
	        if(player.isPotionActive(potionLoong)) {
	        	stack.setItemDamage(0);
	        }
	        if(player.isPotionActive(potionTiger)) {
	        	PotionEffect effect = player.getActivePotionEffect(potionTiger);
	        	magicDamage += (1 * effect.getAmplifier());
	        }
	        
	        EntityDriveEievui entityDrive  = new EntityDriveEievui(world, player, magicDamage);
	        entityDrive.setColor(16766720);
	        entityDrive.setLifeTime(10);
	        entityDrive.setRoll(45.0F);
	        
	        if(entityDrive != null) {
	        	world.spawnEntity(entityDrive);
	        }
	        
	    }
		
	}
	
	@SubscribeEvent
	public void onUpdateItemSlashBlade(SlashBladeEvent.OnUpdateEvent event){
	   if (!SpecialEffects.isPlayer(event.entity)) {
	      return;
	    }
	    EntityPlayer player = (EntityPlayer) event.entity;
	    World world = player.world;

	    ItemStack blade = event.blade;
	    NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);
	    if (ItemSlashBlade.IsBroken.get(tag).booleanValue()) {
	      return;
	    }
	    
	    switch (SpecialEffects.isEffective(player, event.blade, this).ordinal()){
	    case 0:
	      return;
	    case 1:
	      return;
	    case 2:
	      double d0 = player.getRNG().nextGaussian() * 0.02D;
	      double d1 = player.getRNG().nextGaussian() * 0.02D;
	      double d2 = player.getRNG().nextGaussian() * 0.02D;
	      double d3 = 10.0D;
	    }
	    ItemSlashBlade.ComboSequence seq = ItemSlashBlade.getComboSequence(tag);
	    if (!useBlade(seq)) {
	      return;
	    }
	    PotionEffect haste = player.getActivePotionEffect(MobEffects.HASTE);

	    //int check = haste.getAmplifier() != 1 ? 3 : haste != null ? 4 : 2;
	    /*
	    if (player.swingProgressInt != check) {
	      return;
	    }*/
	    doAddAttack(event.blade, player, seq);
	  }
	
	private Entity getEntityToWatch(EntityPlayer player){
        World world = player.world;
        Entity target = null;
        for(int dist = 2; dist < 20; dist+=2){
            AxisAlignedBB bb = player.getEntityBoundingBox();
            Vec3d vec = player.getLookVec();
            vec = vec.normalize();
            bb = bb.expand(2.0f, 0.25f, 2.0f);
            bb = bb.offset(vec.x*(float)dist,vec.y*(float)dist,vec.z*(float)dist);

            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, bb);
            float distance = 30.0f;
            for(Entity curEntity : list){
                float curDist = curEntity.getDistance(player);
                if(curDist < distance)
                {
                    target = curEntity;
                    distance = curDist;
                }
            }
            if(target != null)
                break;
        }
        return target;
    }
	
}
