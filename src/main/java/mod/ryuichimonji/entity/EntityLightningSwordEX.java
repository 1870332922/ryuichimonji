package mod.ryuichimonji.entity;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLightningSwordEX extends EntityStormSwordsEX{
	public static String NAME_ATTACK_TYPE = StylishRankManager.AttackTypes.registerAttackType("LightningSword", -0.5F);
	
	
	public EntityLightningSwordEX(World worldIn){
		super(worldIn);
	}

	public EntityLightningSwordEX(World worldIn,
								EntityLivingBase thrower,
								float attackLevel)
	{
		super(worldIn, thrower, attackLevel, null);
	}

	@Override
	protected void attackToEntity(Entity target)
	{
		target.hurtResistantTime = 0;
        
		if (!world.isRemote) {
			this.world.addWeatherEffect(new EntitySlashBladeLightningBolt(this.world, target, EntitySelectorAttackable.getInstance(), this.getColor()));
		}
		StylishRankManager.setNextAttackType(thrower, NAME_ATTACK_TYPE);
		StylishRankManager.doAttack(thrower);
        
		setDead();
	}

	/*
	double hitX;
	double hitY;
	double hitZ;
	
	float hitYaw;
	float hitPitch;
	
	float iniYaw;
	float inPitch;
	boolean initIniYP = false;
	Entity ridingEntity2 = null;
	
	public EntityLightningSwordEX(World worldIn){
		super(worldIn);
	}

	public EntityLightningSwordEX(World worldIn,
								EntityLivingBase thrower,
								float attackLevel){
		super(worldIn, thrower, attackLevel, null);
	}
	
	public void onUpdate() {
		if(this.thrower == null) {
			setDead();
			return;
		}
		if(this.ridingEntity2 != null) {
			updateRidden();
		}
		else {
			this.lastTickPosX = this.posX;
			this.lastTickPosY = this.posY;
			this.lastTickPosZ = this.posZ;
			
			double dAmbit = 0.75D;
			AxisAlignedBB bb = new AxisAlignedBB(
					this.posX - dAmbit,
					this.posY - dAmbit,
					this.posZ - dAmbit,
					this.posX + dAmbit,
					this.posY + dAmbit,
					this.posZ + dAmbit
					);
			EntityLivingBase entityLiving;
			double var2;
			if(getThrower() instanceof EntityLivingBase) {
				entityLiving = (EntityLivingBase) getThrower();
				List<Entity> list = world.getEntitiesInAABBexcluding(getThrower(), bb, EntitySelectorAttackable.getInstance());
				
				StylishRankManager.setNextAttackType(this.thrower, StylishRankManager.AttackTypes.DestructObject);
				
				list.removeAll(this.alreadyHitEntity);
				this.alreadyHitEntity.addAll(list);
				
				for(Entity curEntity : list) {
					boolean isDestruction = true;
					if(isDestruction(curEntity)) {
						isDestruction = false;
					}
					
					if(isDestruction) {
						curEntity.motionX = 0.0D;
						curEntity.motionY = 0.0D;
						curEntity.motionZ = 0.0D;
						
						for(int var1 = 0; var1 < 10; var1++) {
							Random rand = getRand();
							var2 = rand.nextGaussian() * 0.02D;
							double var4 = rand.nextGaussian() * 0.02D;
							double var6 = rand.nextGaussian() * 0.02D;
							double var8 = 10.0D;
						}
						
						StylishRankManager.doAttack(this.thrower);
						
						setDead();
						return;
					}
				}
			}
			
			List<Entity> list = world.getEntitiesInAABBexcluding(getThrower(), bb, EntitySelectorAttackable.getInstance());
			list.removeAll(this.alreadyHitEntity);
			if(getTargetEntityId() != 0) {
				Entity target = this.world.getEntityByID(getTargetEntityId());
				
				if((target != null) && (target.isEntityAlive()) && (target.getEntityBoundingBox().intersects(bb))) {
					list.add(target);
				}
			}
			this.alreadyHitEntity.addAll(list);
			
			double d0 = 10.0D;
			
			Entity hitEntity = null;
			for(Entity curEntity: list) {
				if(curEntity.canBeCollidedWith()) {
					double d1 = curEntity.getDistanceSq(this);
					if((d1 < d0 ) || (d0 == 0.0D)) {
						hitEntity = curEntity;
						d0 = d1;
					}
				}
			}
			
			if((hitEntity != null) && (!hitEntity.isDead)) {
				if(!this.world.isRemote) {
					this.world.addWeatherEffect(new EntitySlashBladeLightningBolt(this.world, hitEntity, EntitySelectorAttackable.getInstance(), this.getColor()));
				}
				StylishRankManager.doAttack(this.thrower);
				
				setDead();
			}
			
			int nPosX = MathHelper.floor(this.posX);
			int nPosY = MathHelper.floor(this.posY);
			int nPosZ = MathHelper.floor(this.posZ);
			
			if(this.ridingEntity2 == null) {
				Block nBlock = this.world.getBlockState(new BlockPos(nPosX, nPosY, nPosZ)).getBlock();
				
				if(!nBlock.isAir(this.world.getBlockState((new BlockPos(nPosX, nPosY, nPosZ))), world,(new BlockPos(nPosX, nPosY, nPosZ)))
						&& (nBlock.getCollisionBoundingBox((this.world.getBlockState((new BlockPos(nPosX, nPosY, nPosZ)))), world, (new BlockPos(nPosX, nPosY, nPosZ))) != null)
						){
					setDead();
					return;
				}
			}
			
			if(getInterval() < this.ticksExisted) {
				this.posX += this.motionX;
				this.posY += this.motionY;
				this.posZ += this.motionX;
			}
			else {
				doTargeting();
			}
			setPosition(this.posX, this.posY, this.posZ);
			if(this.ticksExisted >= getLifeTime()) {
				setDead();
			}
		}
	}
	
	public void mountEntity(Entity par1Entity) {
		if(par1Entity != null) {
			this.hitYaw = this.rotationYaw - par1Entity.rotationYaw;
			this.hitPitch = this.rotationPitch - par1Entity.rotationPitch;
			
			this.hitX = this.posX - par1Entity.posX;
			this.hitY = this.posY - par1Entity.posY;
			this.hitZ = this.posZ - par1Entity.posZ;
			this.ridingEntity2 = par1Entity;
			
			this.ticksExisted = Math.max(0, getLifeTime() -20);
		}
	}
	
	public void doTargeting() {
		int targetid = getTargetEntityId();
		if(targetid != 0) {
			Entity target = this.world.getEntityByID(targetid);
			if((target != null) && (this.thrower != null) && (!this.thrower.isDead)) {
				if(!this.initIniYP) {
					this.initIniYP = true;
					if(super.iniPitch < -700.0F) {
						this.iniYaw = this.thrower.rotationYaw;
						this.iniPitch = this.thrower.rotationPitch;
						
					}
					else {
						this.iniYaw = super.iniYaw;
						this.iniPitch = super.iniPitch;
					}
				}
				faceEntity(this, target, this.ticksExisted * 1.0F, this.ticksExisted * 1.0F);
				setDriveVector(1.75F, false);
			}
		}
	}
	
	public void faceEntity(Entity viewer, Entity target, float yawStep, float pitchStep) {
		double d0 = target.posX - viewer.posX;
		double d1 = target.posZ - viewer.posZ;
		double d2;
		if(target instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase) target;
			d2 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - (viewer.posY + viewer.getEyeHeight());
		}
		else {
			d2 = target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY / 2.0D - (viewer.posY + viewer.getEyeHeight());
		}
		double d3 = MathHelper.sqrt(d0 * d0 + d1 *d1);
		
		float f2 = (float) ((Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F);
		float f3 = (float) ((Math.atan2(d2, d3) * 180.0D / Math.PI));
		
		this.iniPitch = updateRotation(this.iniPitch, f3, pitchStep);
		this.iniYaw = updateRotation(this.iniYaw, f2, yawStep);
	}
	
	public void setDriveVector(float fYVecOfst, boolean init) {
		float fYawDtoR = (float) (this.iniYaw / 180.0F * Math.PI);
		float fPitDtoR = (float) (this.iniPitch / 180.0F * Math.PI);
		
		this.motionX = (-MathHelper.sin(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst);
		this.motionY = (-MathHelper.sin(fPitDtoR) * fYVecOfst);
		this.motionZ = (MathHelper.cos(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst);
		
		float f3 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		
		this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
		this.rotationPitch = (float) (Math.atan2(this.motionY, f3) * 180.0D / Math.PI);
		
		if(init) {
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}
		
	}
	*/

}
