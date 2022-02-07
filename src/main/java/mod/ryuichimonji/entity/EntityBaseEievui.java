package mod.ryuichimonji.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mod.ryuichimonji.Math2;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorDestructable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBaseEievui extends Entity implements IThrowableEntity{
	
	protected EntityLivingBase thrower = null;
	protected ItemStack blade = ItemStack.EMPTY;
	
	protected List<Entity> alreadyHitEntity = new ArrayList<Entity>();
	protected float attackLevel = 0.0f;
	
	protected EnumParticleTypes particle = EnumParticleTypes.EXPLOSION_HUGE;
	
	float speed = 1.75F;
	float iniYaw = Float.NaN;
	float iniPitch = Float.NaN;
	
	private static final DataParameter<Integer> THROWER_ENTITY_ID = EntityDataManager.<Integer>createKey(EntityBaseEievui.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> LIFETIME = EntityDataManager.<Integer>createKey(EntityBaseEievui.class, DataSerializers.VARINT);
	private static final DataParameter<Float> ROLL = EntityDataManager.<Float>createKey(EntityBaseEievui.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> TARGET_ENTITY_ID = EntityDataManager.<Integer>createKey(EntityBaseEievui.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityBaseEievui.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> INTERVAL = EntityDataManager.<Integer>createKey(EntityBaseEievui.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> SPEED = EntityDataManager.<Integer>createKey(EntityBaseEievui.class, DataSerializers.VARINT);

	public EntityBaseEievui(World worldIn) {
		super(worldIn);
		this.noClip = true;
		ticksExisted = 0;
		setSize(0.5F, 0.5F);
		// TODO Auto-generated constructor stub
	}
	
	public EntityBaseEievui(World worldIn, EntityLivingBase thrower, float attackLevel,float roll) {
		super(worldIn);
		this.thrower = thrower;
		this.attackLevel = attackLevel;
		this.setRoll(roll);
	}
	
	public EntityBaseEievui(World worldIn, EntityLivingBase thrower, float attackLevel){
		super(worldIn);
		// TODO Auto-generated method stub
		this.ticksExisted = 0;

		this.thrower = thrower;
		this.attackLevel = attackLevel;

		this.blade = thrower.getHeldItem(EnumHand.MAIN_HAND);
		if (!blade.isEmpty() && !(blade.getItem() instanceof ItemSlashBlade))
			blade = ItemStack.EMPTY;

		alreadyHitEntity.add(thrower);
		alreadyHitEntity.add(thrower.getRidingEntity());
		alreadyHitEntity.addAll(thrower.getPassengers());
		
		{
			float dist = 2.0F;
			double ran = (rand.nextFloat() -0.5D) * 2.0D;
			double yaw = Math.toRadians(-thrower.rotationYaw + 90);
			
			double x = ran * Math.sin(yaw);
			double y = 1.0 * Math.abs(ran);
			double z = ran * Math.cos(yaw);
			
			x *= dist;
			y *= dist;
			z *= dist;
			
			setLocationAndAngles(thrower.posX + x,
					thrower.posY + y,
					thrower.posZ + z,
					thrower.rotationYaw,
					thrower.rotationPitch);
			
			iniYaw = thrower.rotationYaw;
			iniPitch = thrower.rotationPitch;
			
			setDriveVector(1.75F);
		}
		
	}

	@Override
	public Entity getThrower() {
		// TODO Auto-generated method stub
		return this.thrower;
	}

	@Override
	public void setThrower(Entity entity) {
		// TODO Auto-generated method stub
		this.thrower = (EntityLivingBase)entity;
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		EntityDataManager manager = getDataManager();
		manager.register(THROWER_ENTITY_ID, 0);
		manager.register(LIFETIME, 20);
        manager.register(ROLL, 0.0F);
        manager.register(TARGET_ENTITY_ID, 0);
        manager.register(INTERVAL, 7);
		manager.register(COLOR, 0x3333FF);
	}

	
	public void setInitialPosition(double x, double y, double z, float yaw, float pitch, float roll, float speed){
		this.prevPosX = this.lastTickPosX = x;
		this.prevPosY = this.lastTickPosY = y;
		this.prevPosZ = this.lastTickPosZ = z;

		this.prevRotationYaw   = this.rotationYaw   = MathHelper.wrapDegrees(-yaw);
		this.prevRotationPitch = this.rotationPitch = MathHelper.wrapDegrees(-pitch);
		setRoll(roll);

		setMotionToForward(speed);

		setPosition(x, y, z);
	}
	
	protected void setMotionToForward(float speed){
        this.motionX = Math2.sin(rotationYaw)*Math2.cos(rotationPitch)*speed;
        this.motionY = Math2.sin(rotationPitch)*speed;
        this.motionZ = Math2.cos(rotationYaw)*Math2.cos(rotationPitch)*speed;
    }
	
	protected boolean onImpact(Entity target, float damage){
		return onImpact(target, damage, "directMagic");
	}
	
	protected boolean onImpact(Entity target, float damage, String source){
		DamageSource ds = new EntityDamageSource(source, thrower)
			.setDamageBypassesArmor()
			.setMagicDamage();
		
		return onImpact(target, damage, ds);
	}
	
	protected boolean onImpact(Entity target, float damage, DamageSource ds){
		target.hurtResistantTime = 0;
		target.attackEntityFrom(ds, damage);
		
		if (blade.isEmpty() || !(target instanceof EntityLivingBase))
			return false;

		blade.getItem().hitEntity(blade,
								   (EntityLivingBase)target,
								   thrower);
		return true;
	}
	
	protected boolean intercept(AxisAlignedBB area, boolean fragile){
		List<Entity> list = world.getEntitiesInAABBexcluding(thrower, area, EntitySelectorDestructable.getInstance());

		list.removeAll(alreadyHitEntity);
		alreadyHitEntity.addAll(list);
        
		for (Entity target : list) {
			if (!isDestruction(target))
				continue;

			target.motionX = 0.0;
			target.motionY = 0.0;
			target.motionZ = 0.0;
			target.setDead();

			spawnParticle(target, particle);

			StylishRankManager.setNextAttackType(thrower, StylishRankManager.AttackTypes.DestructObject);
			StylishRankManager.doAttack(thrower);

			if (fragile) {
				setDead();
				return true;
			}
		}
		return false;
	}
	
	public boolean isDestruction(Entity target){
		if (target instanceof EntityArrow)
			return !isSameThrower(((EntityArrow)target).shootingEntity);
		if (target instanceof IThrowableEntity)
			return !isSameThrower(((IThrowableEntity)target).getThrower());
		if (target instanceof EntityThrowable)
			return !isSameThrower(((EntityThrowable)target).getThrower());

		if (target instanceof EntityFireball) {
			if (isSameThrower(((EntityFireball)target).shootingEntity)) {
				return false;
			} else {
				return !target.attackEntityFrom(
					DamageSource.causeMobDamage(thrower), attackLevel);
			}
		}

		return true;
	}


	private boolean isSameThrower(Entity targetThrower){
		return targetThrower != null &&
			targetThrower.getEntityId() == thrower.getEntityId();
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}
	
	private void spawnParticle(Entity entity, EnumParticleTypes particle){
		Random rand = this.rand;

		for (int var1 = 0; var1 < 10; var1++) {

			double xSpeed = rand.nextGaussian() * 0.02;
			double ySpeed = rand.nextGaussian() * 0.02;
			double zSpeed = rand.nextGaussian() * 0.02;

			double rx = rand.nextDouble();
			double ry = rand.nextDouble();
			double rz = rand.nextDouble();
			
			world.spawnParticle(
					particle,
				entity.posX + (rx*2 - 1)*entity.width  - xSpeed * 10.0,
				entity.posY + (ry      )*entity.height - ySpeed * 10.0,
				entity.posZ + (rz*2 - 1)*entity.width  - zSpeed * 10.0,
				xSpeed, ySpeed, zSpeed);
		}
	}
	
	public void setParticle(EnumParticleTypes particle) {
		this.particle = particle;
	}
	
	public EnumParticleTypes getParticle() {
		return this.particle;
	}
	
	protected static void coolDownEnderman(EntityEnderman entity){
		entity.setAttackTarget(null);

		for (EntityAITasks.EntityAITaskEntry task : entity.targetTasks.taskEntries) {
			if (task.priority == 1) {
				task.action.resetTask();
			}
		}
	}
	
	public final float getIniPitch() {
		return this.iniPitch;
	}
	
	public final void setIniPitch(float ip) {
		this.iniPitch = ip;
	}
	
	public final float getIniYaw() {
		return this.iniYaw;
	}
	
	public final void setIniYaw(float iy) {
		this.iniYaw = iy;
	}
	public int getThrowerEntityId() {
		return this.getDataManager().get(THROWER_ENTITY_ID);
	}
	
	public final void setThrowerEntityId(int entityid) {
		this.getDataManager().set(THROWER_ENTITY_ID, entityid);
	}
	public final int getLifeTime(){
        return this.getDataManager().get(LIFETIME);
    }

    public final void setLifeTime(int lifetime){
        this.getDataManager().set(LIFETIME,lifetime);
    }
    
	public final float getRoll(){
        return this.getDataManager().get(ROLL);
    }

    public final void setRoll(float roll){
        this.getDataManager().set(ROLL,roll);
    }

    public int getTargetEntityId() {
    	return this.dataManager.get(TARGET_ENTITY_ID);
    }
    
    public void setTargetEntityId(int entityid) {
    	this.getDataManager().set(TARGET_ENTITY_ID, entityid);
    }
    
    public int getInterval() {
    	return this.getDataManager().get(INTERVAL);
    }
    
    public void setInterval(int value) {
    	this.getDataManager().set(INTERVAL, value);
    }

	public final int getColor(){
		return getDataManager().get(COLOR);
	}
  
	public final void setColor(int value){
		getDataManager().set(COLOR, value);
	}
	
    public Random getRand(){
        return this.rand;
    }
    
    @Override
    public boolean isOffsetPositionInLiquid(double x, double y, double z){
        return false;
    }
    
    @Override
    public void move(MoverType type, double x, double y, double z){

	}

    @Override
    protected void dealFireDamage(int amount){
    	
	}

    @Override
    public boolean handleWaterMovement(){
        return false;
    }
    
    @Override
    public boolean isInsideOfMaterial(Material materialIn){
        return false;
    }

    @Override
    public boolean isInLava(){
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getBrightnessForRender(){

        float f1 = 0.5f;

        int i = super.getBrightnessForRender();
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0f * 16.0f);

        if (j > 240)
            j = 240;

        return j | k << 16;
    }

    @Override
    public float getBrightness(){

        float f1 = super.getBrightness();

        float f2 = 0.9f;
        f2 = f2 * f2 * f2 * f2;
        return f1 * (1.0f - f2) + f2;

    }


    @Override
    public void setPortal(BlockPos pos){
    	
    }


    @Override
    public boolean isBurning(){
        return false;
    }


    @Override
    public void setInWeb(){
		
	}

    @Override
    public boolean shouldRenderInPass(int pass){
        return pass == 1;
    }

    private void doTargeting(){
		if(this.ticksExisted > getInterval())
			return;
		
		int targetid = this.getTargetEntityId();
		Entity owner = this.thrower;
		
		if(this.thrower == null)
			owner = this;
		
		if(targetid == 0) {
			Entity rayEntity = getRayTrace(owner, 30.0F);
			if(rayEntity != null) {
				targetid = rayEntity.getEntityId();
				this.setTargetEntityId(rayEntity.getEntityId());
			}
		}
		
		if(targetid == 0) {
			Entity rayEntity = getRayTrace(owner, 30.0F, 5.0F, 5.0F);
			if(rayEntity != null) {
				targetid = rayEntity.getEntityId();
				this.setTargetEntityId(rayEntity.getEntityId());
			}
		}
		
		if(targetid != 0) {
			Entity target = world.getEntityByID( targetid);
			
			if(target != null) {
				if(Float.isNaN(iniPitch) && thrower != null) {
					iniYaw = thrower.rotationYaw;
					iniPitch = thrower.rotationPitch;
				}
				faceEntity(this, target, ticksExisted * 1.0F, ticksExisted * 1.0F);
				setDriveVector(1.75F, false);
			}
		}
		
	}
    public Entity getRayTrace(Entity owner, double reachMax){
        return this.getRayTrace(owner, reachMax,1.0f,0.0f);
    }
    
    public static RayTraceResult rayTrace(Entity owner, double par1, float par3){
        Vec3d Vec3d = getPosition(owner);
        Vec3d Vec3d1 = getLook(owner, par3);
        Vec3d Vec3d2 = Vec3d.addVector(Vec3d1.x * par1, Vec3d1.y * par1, Vec3d1.z * par1);
        return owner.world.rayTraceBlocks(Vec3d, Vec3d2, false, false, true);
    }
    
	private Entity getRayTrace(Entity owner, double reachMax, float expandFactor, float expandBorder) {
		// TODO Auto-generated method stub
		Entity pointedEntity;
		float par1 = 1.0F;
		
		RayTraceResult objectMouseOver = rayTrace(owner, reachMax, par1);
		double reachMin = reachMax;
		Vec3d entityPos = getPosition(owner);
		
		if(objectMouseOver != null) {
			reachMin = objectMouseOver.hitVec.distanceTo(entityPos);
		}
		
		Vec3d lookVec = getLook(owner, par1);
		Vec3d reachVec = entityPos.addVector(lookVec.x * reachMax, lookVec.y * reachMax, lookVec.z * reachMax);
        pointedEntity = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this
                , this.getEntityBoundingBox()
                .offset(lookVec.x * reachMax, lookVec.y * reachMax, lookVec.z * reachMax)
                .grow((double) expandFactor + reachMax, (double) expandFactor + reachMax, (double) expandFactor + reachMax));
        list.removeAll(alreadyHitEntity);

        double tmpDistance = reachMin;
        EntityLivingBase viewer = (owner instanceof EntityLivingBase) ? (EntityLivingBase) owner : null;
        
        for (Entity entity : list) {
        	if (entity == null || !entity.canBeCollidedWith()) 
        		continue;
            if (!EntitySelectorAttackable.getInstance().apply(entity))
                continue;
            if(viewer != null && !viewer.canEntityBeSeen(entity))
                continue;
            
            float borderSize = entity.getCollisionBorderSize() + expandBorder;
            AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow((double) borderSize, (double) borderSize, (double) borderSize);
            RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(entityPos, reachVec);
            double counter = reachMax;
            
            if (axisalignedbb.contains(entityPos)) {
                if (0.0D < tmpDistance || tmpDistance == 0.0D) {
                    pointedEntity = entity;
                    tmpDistance = 0.0D;
                }
            } else if (movingobjectposition != null) {
                double d3 = entityPos.distanceTo(movingobjectposition.hitVec);

                if (d3 < tmpDistance || tmpDistance == 0.0D) {
                    if (entity == this.getRidingEntity() && !entity.canRiderInteract()) {
                        if (tmpDistance == 0.0D) {
                            pointedEntity = entity;
                        }
                    } else {
                        pointedEntity = entity;
                        tmpDistance = d3;
                    }
                }
            }
            
        }
		
		return pointedEntity;
	}
	
	public void faceEntity(Entity viewer, Entity target, float yawStep, float pitchStep){
        double d0 = target.posX - viewer.posX;
        double d1 = target.posZ - viewer.posZ;
        double d2;

        if (target instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)target;
            d2 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (viewer.posY + (double)viewer.getEyeHeight());
        }
        else
        {
            AxisAlignedBB boundingBox = target.getEntityBoundingBox();
            d2 = (boundingBox.minY + boundingBox.maxY) / 2.0D - (viewer.posY + (double)viewer.getEyeHeight());
        }

        double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1);
        float f2 = (float)(Math.atan2(d1, d0) * 180.0D / Math.PI) - 90.0F;
        float f3 = (float)(-(Math.atan2(d2, d3) * 180.0D / Math.PI));


        iniPitch = this.updateRotation(iniPitch, f3, pitchStep);
        iniYaw = this.updateRotation(iniYaw, f2, yawStep);
        
    }
	private float updateRotation(float par1, float par2, float par3){
        float f3 = MathHelper.wrapDegrees(par2 - par1);

        if (f3 > par3){
            f3 = par3;
        }

        if (f3 < -par3){
            f3 = -par3;
        }

        return par1 + f3;
    }

	 public static Vec3d getPosition(Entity owner){
	    return new Vec3d(owner.posX, owner.posY + owner.getEyeHeight(), owner.posZ);
	}
	    public static Vec3d getLook(Entity owner, float rotMax){
	        float f1;
	        float f2;
	        float f3;
	        float f4;

	        if (rotMax == 1.0F){
	            f1 =  MathHelper.cos(-owner.rotationYaw   * 0.017453292F - (float)Math.PI);
	            f2 =  MathHelper.sin(-owner.rotationYaw   * 0.017453292F - (float)Math.PI);
	            f3 = -MathHelper.cos(-owner.rotationPitch * 0.017453292F);
	            f4 =  MathHelper.sin(-owner.rotationPitch * 0.017453292F);
	            return new Vec3d((double)(f2 * f3), (double)f4, (double)(f1 * f3));
	        }
	        else
	        {
	            f1 = owner.prevRotationPitch + (owner.rotationPitch - owner.prevRotationPitch) * rotMax;
	            f2 = owner.prevRotationYaw + (owner.rotationYaw - owner.prevRotationYaw) * rotMax;
	            f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
	            f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
	            float f5 = -MathHelper.cos(-f1 * 0.017453292F);
	            float f6 = MathHelper.sin(-f1 * 0.017453292F);
	            return new Vec3d((double)(f4 * f5), (double)f6, (double)(f3 * f5));
	        }
	    }

	public void setDriveVector(float fYVecOfst){
	        setDriveVector(fYVecOfst,true);
	}
	    
	public void setDriveVector(float fYVecOfst,boolean init) {
		// TODO Auto-generated method stub
		float fYawDtoR = (iniYaw / 180F) * (float)Math.PI;
		float fPitDtoR = (iniPitch / 180F) * (float)Math.PI;

        //¡ö…gÎ»¥Ù¥¯¥È¥ë
        motionX = -MathHelper.sin(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst;
        motionY = -MathHelper.sin(fPitDtoR) * fYVecOfst;
        motionZ =  MathHelper.cos(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst;

        float f3 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);
        rotationPitch = (float)((Math.atan2(motionY, f3) * 180D) / Math.PI);
        if(init){
            speed = fYVecOfst;
            prevRotationYaw = rotationYaw;
            prevRotationPitch = rotationPitch;
        }
	}

}
