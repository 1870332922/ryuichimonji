package mod.ryuichimonji.entity;

import java.util.List;

import mod.ryuichimonji.Math2;
import mod.ryuichimonji.specialattack.PhantomSwordsBase;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityStormSwords;
import mods.flammpfeil.slashblade.entity.EntitySummonedSwordBase;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityStormSwordsEX extends EntityBaseEievui{
	
	private Entity stuckEntity_ = null;	
	private double hitX_;
    private double hitY_;
    private double hitZ_;
    private float hitYaw_;
    private float hitPitch_;
	private float hitBaseYaw_;
	private PhantomSwordsBase sa_ = null;
	
	public static final float SPEED = 1.75f;
	private static final double AMBIT = 0.75;
    private static final DataParameter<Integer> TARGET_ENTITY_ID = EntityDataManager.<Integer>createKey(EntityStormSwordsEX.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> INTERVAL = EntityDataManager.<Integer>createKey(EntityStormSwordsEX.class, DataSerializers.VARINT);
	
	public EntityStormSwordsEX(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}

	public EntityStormSwordsEX(World worldIn, EntityLivingBase thrower, float attackLevel, PhantomSwordsBase sa) {
		super(worldIn, thrower, attackLevel);
		// TODO Auto-generated constructor stub
		this.sa_ = sa;
		setSize(0.5f, 0.5f);
	}
	
	public EntityStormSwordsEX(World worldIn, EntityLivingBase thrower, float attackLevel, float roll) {
		super(worldIn, thrower, attackLevel);
		// TODO Auto-generated constructor stub
		this.sa_ = null;
		setRoll(roll);
		setSize(0.5f, 0.5f);
	}
	
	@Override
	protected void entityInit(){
		super.entityInit();

		EntityDataManager manager = getDataManager();
        manager.register(TARGET_ENTITY_ID, 0);
		manager.register(INTERVAL, 7);
	}
	
	public final int getTargetEntityId(){
        return this.getDataManager().get(TARGET_ENTITY_ID);
    }

    public final void setTargetEntityId(int entityid){
        this.getDataManager().set(TARGET_ENTITY_ID,entityid);
    }

	public final int getInterval(){
		return getDataManager().get(INTERVAL);
	}

	public final void setInterval(int value){
		getDataManager().set(INTERVAL, value);
	}

	protected final boolean isStuck(){
		return stuckEntity_ != null;
	}

	protected final Entity getTargetEntity(){
		int targetid = getTargetEntityId();
		if (targetid == 0)
			return null;
				
		return this.world.getEntityByID(targetid);
	}
	

    @Override
	public void onUpdate(){
		if (isStuck()) {
			updateRidden();
			return;
		}

		AxisAlignedBB bb = new AxisAlignedBB(this.posX - AMBIT,
											 this.posY - AMBIT,
											 this.posZ - AMBIT,
											 this.posX + AMBIT,
											 this.posY + AMBIT,
											 this.posZ + AMBIT);
				
		if (intercept(bb, true))
			return;


		Entity target = getNearestHitEntity(bb);
		if (target != null) {
			attackToEntity(target);

		} else if (!world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()) {
			setDead();
			return;
		}

		move();

		if (this.ticksExisted >= getLifeTime())
			setDead();
	}

    @Override
	public void updateRidden(){

		final Entity entity = stuckEntity_;
		if (entity == null)
			return;
    
		if (entity.isDead) {
			setDead();
			return;
		}
		
		if (sa_ != null)
			sa_.onSticking(entity);

		updatePositionBaseStuckEntity();		

		if (this.ticksExisted >= getLifeTime()) {
			StylishRankManager.setNextAttackType(thrower, StylishRankManager.AttackTypes.BreakPhantomSword);
			onImpact(entity, attackLevel*0.5f);
			setDead();
		}
	}

	private void updatePositionBaseStuckEntity(){
		final Entity entity = stuckEntity_;

		float r = entity.rotationYaw - hitBaseYaw_;
		double x = entity.posX + hitX_*Math2.cos(r) - hitZ_*Math2.sin(r);
		double y = entity.posY + hitY_;
		double z = entity.posZ + hitX_*Math2.sin(r) + hitZ_*Math2.cos(r);

		float pitch = entity.rotationPitch + hitPitch_;
		float yaw = entity.rotationYaw + hitYaw_;
		
		setLocationAndAngles(x, y, z,
							 MathHelper.wrapDegrees(yaw),
							 MathHelper.wrapDegrees(pitch));
	}

	private void move(){
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
      
		if (getInterval() < this.ticksExisted) {
			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
		} else {
			doTargeting();
		}
		setPosition(this.posX, this.posY, this.posZ);
	}
	
	public void mountEntity(Entity entity) {
		if(entity!=null) {
			this.hitY_ = this.rotationYaw - entity.rotationYaw;
			this.hitPitch_ = this.rotationPitch - entity.rotationPitch;
			
			this.hitX_ = this.posX - entity.posX;
			this.hitY_ = this.posY - entity.posY;
			this.hitZ_ = this.posZ - entity.posZ;
			
			this.startRiding(entity);
			this.ticksExisted = Math.max(0, getLifeTime() - 20);
		}
	}

	protected void doTargeting(){
		Entity target = getTargetEntity();
		if (target == null)
			return;

        final double dx = target.posX - posX;
        final double dz = target.posZ - posZ;
        double dy = - posY;

        if (target instanceof EntityLivingBase) {
            dy += target.posY + target.getEyeHeight()/2;
        } else {
            AxisAlignedBB bb = target.getEntityBoundingBox();
            dy += (bb.minY + bb.maxY) / 2.0;
        }

        double d = Math.sqrt(dx*dx + dz*dz);
        float yaw   = (float)Math.toDegrees(Math.atan2(dx, dz));
		float pitch = (float)Math.toDegrees(Math.atan2(dy, d));

		prevRotationYaw   = rotationYaw;
		prevRotationPitch = rotationPitch;

		rotationYaw   += MathHelper.clamp(yaw   - rotationYaw, -4.5f, 4.5f);
		rotationPitch += MathHelper.clamp(pitch - rotationPitch, -4.5f, 4.5f);

		setMotionToForward(SPEED);
	}
	
	protected void attackToEntity(Entity target){
		StylishRankManager.setNextAttackType(thrower, StylishRankManager.AttackTypes.PhantomSword);
		onImpact(target, attackLevel);

		stickEntity(target);
	}


	private Entity getNearestHitEntity(AxisAlignedBB bb){
		List<Entity> list = world.getEntitiesInAABBexcluding(thrower, bb, EntitySelectorAttackable.getInstance());

		list.removeAll(alreadyHitEntity);

		Entity target = getTargetEntity();
		if (target != null &&
			target.isEntityAlive() &&
			target.getEntityBoundingBox().intersects(bb)) {

			list.add(target);
		}

		alreadyHitEntity.addAll(list);
      
		double d0 = 10.0;
		Entity hitEntity = null;
		for (Entity entity : list) {
			if (!entity.canBeCollidedWith())
				continue;

			double d1 = entity.getDistance(this);
			if (d1 < d0 /* || d0 == 0.0*/) {
				hitEntity = entity;
				d0 = d1;
			}
		}
		return hitEntity;
	}

	@Override
	protected boolean onImpact(Entity target, float damage){
		if (super.onImpact(target, Math.max(1.0f, damage))) {
			if (sa_ != null) {
				sa_.onImpact(target);
			}
			return true;
		}
		return false;
	}

    protected void stickEntity(Entity target){
        if (target == null)
			return;

		hitYaw_		= this.rotationYaw - target.rotationYaw;
		hitPitch_	= this.rotationPitch - target.rotationPitch;
		hitX_		= this.posX - target.posX;
		hitY_		= this.posY - target.posY;
		hitZ_		= this.posZ - target.posZ;
		hitBaseYaw_	= target.rotationYaw;

		stuckEntity_ = target;

		this.ticksExisted = Math.max(0, getLifeTime() - 20);
	}
    
    public void faceEntity(Entity viewer,Entity target, float yawStep, float pitchStep) {
    	double d0 = target.posX - viewer.posX;
    	double d1 = target.posZ - viewer.posZ;
    	double d2;
    	
    	if(target instanceof EntityLivingBase) {
    		EntityLivingBase entitylivingbase = (EntityLivingBase)target;
    		d2 = entitylivingbase.posY +entitylivingbase.getEyeHeight() - (viewer.posY + viewer.getEyeHeight());
    	}
    	else {
    		d2 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D - (viewer.posY + viewer.getEyeHeight());
    	}
    	double d3 = MathHelper.sqrt(d0 * d0 + d1*d1);
    	
    	float f2 = (float)((float)(Math.atan2(d1, d0)) * 180.0D / Math.PI) - 90.0F;
    	float f3 = (float)((float)-(Math.atan2(d2, d3)) * 180.0D/ Math.PI);
    	
    	this.hitPitch_ = updateRotation(this.hitPitch_, f3, pitchStep);
    	this.hitYaw_ = updateRotation(this.hitYaw_, f2, yawStep);
    	
    }

	protected float updateRotation(float f1, float f2, float f3) {
		float f4 = MathHelper.wrapDegrees(f2 -f1);
		if(f4 > f3) {
			f4 = f3;
		}
		if(f4 < -f3) {
			f4 = -f3;
		}
		return f1 + f4;
	}
	
	public void setDriveVector(float fYVecOfse, boolean init) {
		super.setDriveVector(fYVecOfse, init);
		
		float fYawDtoR = (float) (this.iniYaw / 180.0F * Math.PI);
		float fPitDtoR = (float) (this.iniPitch / 180.0F * Math.PI);
		
		this.motionX = (-MathHelper.sin(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfse);
	    this.motionY = (-MathHelper.sin(fPitDtoR) * fYVecOfse);
	    this.motionZ = (MathHelper.cos(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfse);

	    float f3 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
	    this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592653589793D));
	    this.rotationPitch = ((float)(Math.atan2(this.motionY, f3) * 180.0D / 3.141592653589793D));
	    if (init)
	    {
	      this.prevRotationYaw = this.rotationYaw;
	      this.prevRotationPitch = this.rotationPitch;
	    }
	}
	
}
