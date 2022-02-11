package mod.ryuichimonji.entity;

import mods.flammpfeil.slashblade.ability.TeleportCanceller;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class EntityAquaEdgeEX extends EntityDriveEievui{
	
	public EntityAquaEdgeEX(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	public EntityAquaEdgeEX(World par1World, EntityLivingBase entityLiving, float AttackLevel, boolean multiHit) {
		super(par1World, entityLiving, AttackLevel, multiHit);
		// TODO Auto-generated constructor stub
	}

	public EntityAquaEdgeEX(World par1World, EntityLivingBase entityLiving, float AttackLevel, boolean multiHit, float roll) {
		super(par1World, entityLiving, AttackLevel, multiHit, roll);
		// TODO Auto-generated constructor stub
	}
	
	 public EntityAquaEdgeEX(World par1World, EntityLivingBase entityLiving,float AttackLevel) {
		 super(par1World, entityLiving, AttackLevel);
			// TODO Auto-generated constructor stub
	 }
	 
	 public void onImpact(Entity curEntity, float damage) {
		 if(TeleportCanceller.canTeleport(curEntity)){
			 TeleportCanceller.setCancel(curEntity);
			 spawnParticle(curEntity);
		 }
		 
		 if(!curEntity.world.isRemote) {
			 curEntity.hurtResistantTime = 0;
			 
			 DamageSource ds = new EntityDamageSource(DamageSource.DROWN.toString(), getThrower());
			 ds.setDamageAllowedInCreativeMode().setDamageBypassesArmor().setMagicDamage();
			 curEntity.attackEntityFrom(ds, damage);
		 }
		 
		 if(curEntity.isBurning()) {
			 curEntity.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
			 curEntity.extinguish();
		 }
		 
	 
		 if((!curEntity.world.isRemote) && (this.blade != null) && ((curEntity instanceof EntityLivingBase))) {
		      ((ItemSlashBlade)this.blade.getItem()).hitEntity(this.blade, (EntityLivingBase)curEntity, (EntityLivingBase)this.thrower);
		 }
		  if ((curEntity instanceof EntityEnderman)){
		      ((EntityEnderman)curEntity).setAttackTarget(null);
		      curEntity.getEntityData().setBoolean("SCREAMING", true);
		      ReflectionHelper.setPrivateValue(EntityEnderman.class, (EntityEnderman)curEntity, Boolean.valueOf(false), "isAggressive");
		 }
		 
	 }
	 
	 private void spawnParticle(Entity target){
		target.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, true, target.posX, target.posY + target.height, target.posZ, 3.0D, 3.0D, 3.0D, 6);
		target.world.spawnParticle(EnumParticleTypes.WATER_WAKE, true, target.posX, target.posY + target.height, target.posZ, 3.0D, 3.0D, 3.0D, 6);
		target.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, true, target.posX, target.posY + target.height, target.posZ, 3.0D, 3.0D, 3.0D, 6);

	  }
	 
	 public void setInitialPosition(double x, double y, double z,
			   float yaw, float pitch, float roll,
			   float speed){
  	this.prevPosX = this.lastTickPosX = x;
  	this.prevPosY = this.lastTickPosY = y;
  	this.prevPosZ = this.lastTickPosZ = z;

  	this.prevRotationYaw   = this.rotationYaw   = MathHelper.wrapDegrees(-yaw);
  	this.prevRotationPitch = this.rotationPitch = MathHelper.wrapDegrees(-pitch);
  	setRoll(roll);

  	setMotionToForward(speed);

  	setPosition(x, y, z);
  	// TODO Auto-generated method stub
  }

	protected void setMotionToForward(float speed){
      this.motionX = Math.sin(rotationYaw)*Math.cos(rotationPitch)*speed;
      this.motionY = Math.sin(rotationPitch)*speed;
      this.motionZ = Math.cos(rotationYaw)*Math.cos(rotationPitch)*speed;
   // TODO Auto-generated method stub
  }

	 
}
