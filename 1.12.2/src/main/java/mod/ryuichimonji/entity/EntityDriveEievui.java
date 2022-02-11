package mod.ryuichimonji.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mods.flammpfeil.slashblade.ability.ArmorPiercing;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorDestructable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDriveEievui extends Entity implements IThrowableEntity{

	protected Entity thrower;
    protected ItemStack blade = ItemStack.EMPTY;
    protected List<Entity> alreadyHitEntity = new ArrayList<Entity>();
    protected static float AttackLevel = 0.0f;
    protected static int color = 0x3333FF;

    public EntityDriveEievui(World par1World){
        super(par1World);
    }

    public EntityDriveEievui(World par1World, EntityLivingBase entityLiving,float AttackLevel,boolean multiHit, float roll){
        this(par1World,entityLiving,AttackLevel,multiHit);
        this.setRoll(roll);
    }

    public EntityDriveEievui(World par1World, EntityLivingBase entityLiving,float AttackLevel,boolean multiHit){
        this(par1World, entityLiving, AttackLevel);
        this.setIsMultiHit(multiHit);
    }
    
    public EntityDriveEievui(World par1World, EntityLivingBase entityLiving,float AttackLevel) {
    	this(par1World);
        this.AttackLevel = AttackLevel;
        thrower = entityLiving;
        blade = entityLiving.getHeldItem(EnumHand.MAIN_HAND);
        if(!blade.isEmpty() && !(blade.getItem() instanceof ItemSlashBlade)){
            blade = ItemStack.EMPTY;
        }
        
        alreadyHitEntity.clear();
        alreadyHitEntity.add(thrower);
        alreadyHitEntity.add(thrower.getRidingEntity());
        alreadyHitEntity.addAll(thrower.getPassengers());
        
        ticksExisted = 0;
        setSize(1.0F, 2.0F);
        
        setLocationAndAngles(thrower.posX,
                thrower.posY + (double) thrower.getEyeHeight() / 2D,
                thrower.posZ,
                thrower.rotationYaw,
                thrower.rotationPitch);
        
        setDriveVector(0.75F);
        
        Vec3d motion = thrower.getLookVec();
        if(motion == null) motion = new Vec3d(motionX,motionY,motionZ);
        motion = motion.normalize();
        setPosition(posX + motion.x * 20, posY + motion.y * 20, posZ + motion.z * 20);
    }
    
    private static final DataParameter<Float> ROLL = EntityDataManager.<Float>createKey(EntityDriveEievui.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> LIFETIME = EntityDataManager.<Integer>createKey(EntityDriveEievui.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IS_MULTI_HIT = EntityDataManager.<Boolean>createKey(EntityDriveEievui.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_SLASH_DIMENSION = EntityDataManager.<Boolean>createKey(EntityDriveEievui.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityDriveEievui.class, DataSerializers.VARINT);
    
    @Override
	protected void entityInit() {
		// TODO Auto-generated method stub
    	this.getDataManager().register(ROLL, 0.0f);
    	this.getDataManager().register(LIFETIME, 20);
        this.getDataManager().register(IS_MULTI_HIT, false);
        this.getDataManager().register(IS_SLASH_DIMENSION, false);
        this.getDataManager().register(COLOR, 0x3333FF);
	}
    
    public boolean getIsMultiHit(){
        return this.getDataManager().get(IS_MULTI_HIT);
    }
    
    public void setIsMultiHit(boolean isMultiHit){
        this.getDataManager().set(IS_MULTI_HIT,isMultiHit);
    }

    public float getRoll(){
        return this.getDataManager().get(ROLL);
    }
    
    public void setRoll(float roll){
        this.getDataManager().set(ROLL, roll);
    }

    public int getLifeTime(){
        return this.getDataManager().get(LIFETIME);
    }
    
    public void setLifeTime(int lifetime){
        this.getDataManager().set(LIFETIME,lifetime);
    }

    public boolean getIsSlashDimension(){
        return this.getDataManager().get(IS_SLASH_DIMENSION);
    }
    
    public void setIsSlashDimension(boolean isSlashDimension){
        this.getDataManager().set(IS_SLASH_DIMENSION, isSlashDimension);
    }
    
    public int getColor() {
    	return this.getDataManager().get(COLOR);
    }
    
    public void setColor(int color) {
    	this.getDataManager().set(COLOR,color);
    }
    
    @Override
	public Entity getThrower() {
		// TODO Auto-generated method stub
    	 return this.thrower;
	}

	@Override
	public void setThrower(Entity entity) {
		// TODO Auto-generated method stub
		this.thrower = entity;
	}
	
	public void setInitialSpeed(float f){
		setLocationAndAngles(thrower.posX,
                thrower.posY + (double)thrower.getEyeHeight()/2D,
                thrower.posZ,
                thrower.rotationYaw,
                thrower.rotationPitch);
        setDriveVector(f);
        
        Vec3d motion = thrower.getLookVec();
        if(motion == null) motion = new Vec3d(motionX,motionY,motionZ);
        motion = motion.normalize();
        setPosition(posX + motion.x * 1, posY + motion.y * 1, posZ + motion.z * 1);
	}
	
    public void setDriveVector(float fYVecOfst) {
    	float fYawDtoR = (  rotationYaw / 180F) * (float)Math.PI;
        float fPitDtoR = (rotationPitch / 180F) * (float)Math.PI;
        
        motionX = -MathHelper.sin(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst;
        motionY = -MathHelper.sin(fPitDtoR) * fYVecOfst;
        motionZ =  MathHelper.cos(fYawDtoR) * MathHelper.cos(fPitDtoR) * fYVecOfst;
        
        float f3 = MathHelper.sqrt(motionX * motionX + motionZ * motionZ);
        prevRotationYaw = rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / Math.PI);
        prevRotationPitch = rotationPitch = (float)((Math.atan2(motionY, f3) * 180D) / Math.PI);
    }
    
    @Override
    public void onUpdate() {
    	lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;
        if(!world.isRemote) {
        	{
                double dAmbit = 1.5D;
                AxisAlignedBB bb = new AxisAlignedBB(posX - dAmbit, posY - dAmbit, posZ - dAmbit, posX + dAmbit, posY + dAmbit, posZ + dAmbit);

                if(this.getThrower() instanceof EntityLivingBase){
                    EntityLivingBase entityLiving = (EntityLivingBase)this.getThrower();
                    List<Entity> list = this.world.getEntitiesInAABBexcluding(this.getThrower(), bb, EntitySelectorDestructable.getInstance());

                    StylishRankManager.setNextAttackType(this.thrower ,StylishRankManager.AttackTypes.DestructObject);

                    list.removeAll(alreadyHitEntity);
                    alreadyHitEntity.addAll(list);
                    for(Entity curEntity : list){
                        if(blade.isEmpty()) break;

                        boolean isDestruction = true;

                        if(curEntity instanceof EntityFireball){
                            if((((EntityFireball)curEntity).shootingEntity != null && ((EntityFireball)curEntity).shootingEntity.getEntityId() == entityLiving.getEntityId())){
                                isDestruction = false;
                            }else{
                                isDestruction = !curEntity.attackEntityFrom(DamageSource.causeMobDamage(entityLiving), this.AttackLevel);
                            }
                        }else if(curEntity instanceof EntityArrow){
                            if((((EntityArrow)curEntity).shootingEntity != null && ((EntityArrow)curEntity).shootingEntity.getEntityId() == entityLiving.getEntityId())){
                                isDestruction = false;
                            }
                        }else if(curEntity instanceof IThrowableEntity){
                            if((((IThrowableEntity)curEntity).getThrower() != null && ((IThrowableEntity)curEntity).getThrower().getEntityId() == entityLiving.getEntityId())){
                                isDestruction = false;
                            }
                        }else if(curEntity instanceof EntityThrowable){
                            if((((EntityThrowable)curEntity).getThrower() != null && ((EntityThrowable)curEntity).getThrower().getEntityId() == entityLiving.getEntityId())){
                                isDestruction = false;
                            }
                        }

                        if(!isDestruction)
                            continue;
                        else{
                            ReflectionAccessHelper.setVelocity(curEntity, 0, 0, 0);
                            curEntity.setDead();

                            for (int var1 = 0; var1 < 10; ++var1){
                                Random rand = this.getRand();
                                double var2 = rand.nextGaussian() * 0.02D;
                                double var4 = rand.nextGaussian() * 0.02D;
                                double var6 = rand.nextGaussian() * 0.02D;
                                double var8 = 10.0D;
                                this.world.spawnParticle(EnumParticleTypes.REDSTONE
                                        , curEntity.posX + (double)(rand.nextFloat() * curEntity.width * 2.0F) - (double)curEntity.width - var2 * var8
                                        , curEntity.posY + (double)(rand.nextFloat() * curEntity.height) - var4 * var8
                                        , curEntity.posZ + (double)(rand.nextFloat() * curEntity.width * 2.0F) - (double)curEntity.width - var6 * var8
                                        , var2, var4, var6);
                            }
                        }

                        StylishRankManager.doAttack(this.thrower);
                    }
                }

                if(!getIsMultiHit() || this.ticksExisted % 2 == 0){
                    List<Entity> list = this.world.getEntitiesInAABBexcluding(this.getThrower(), bb, EntitySelectorAttackable.getInstance());
                    list.removeAll(alreadyHitEntity);

                    if(!getIsMultiHit())
                        alreadyHitEntity.addAll(list);

                    float magicDamage = Math.max(1.0f, AttackLevel);

                    if(getIsMultiHit())
                        StylishRankManager.setNextAttackType(this.thrower ,StylishRankManager.AttackTypes.QuickDrive);
                    else
                        StylishRankManager.setNextAttackType(this.thrower ,StylishRankManager.AttackTypes.Drive);

                    for(Entity curEntity : list){
                        if(blade.isEmpty()) break;

                        if(getIsSlashDimension()){
                            ArmorPiercing.doAPAttack(curEntity, magicDamage);
                        }

                        curEntity.hurtResistantTime = 0;
                        DamageSource ds = new EntityDamageSource("directMagic",this.getThrower()).setDamageBypassesArmor().setMagicDamage();
                        curEntity.attackEntityFrom(ds, magicDamage);


                        if(!blade.isEmpty() && curEntity instanceof EntityLivingBase)
                            ((ItemSlashBlade)blade.getItem()).hitEntity(blade,(EntityLivingBase)curEntity,(EntityLivingBase)thrower);
                    }
                }
            }
        	
        	if(!world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
                this.setDead();
            }
        }
        
        motionX *= 1.05f;
        motionY *= 1.05f;
        motionZ *= 1.05f;

        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        setPosition(posX, posY, posZ);

        if(ticksExisted >= getLifeTime()) {
            alreadyHitEntity.clear();
            alreadyHitEntity = null;
            setDead();
        }
    }
    
    public Random getRand(){
        return this.rand;
    }
    
    @Override
    public boolean isOffsetPositionInLiquid(double par1, double par3, double par5) {
    	return false;
    }
    
    @Override
    public void move(MoverType moverType, double par1, double par3, double par5) {
    	
    }
    
    @Override
    protected void dealFireDamage(int par1) {
    	
    }
    
    @Override
    public boolean handleWaterMovement(){
        return false;
    }
    
    @Override
    public boolean isInsideOfMaterial(Material par1Material){
        return false;
    }
    
    @Override
    public boolean isInLava() {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public int getBrightnessForRender(){
        float f1 = 0.5F;

        if (f1 < 0.0F){
            f1 = 0.0F;
        }

        if (f1 > 1.0F){
            f1 = 1.0F;
        }

        int i = super.getBrightnessForRender();
        int j = i & 255;
        int k = i >> 16 & 255;
        j += (int)(f1 * 15.0F * 16.0F);

        if (j > 240){
            j = 240;
        }

        return j | k << 16;
    }
    
    @Override
    public float getBrightness(){
        float f1 = super.getBrightness();
        float f2 = 0.9F;
        f2 = f2 * f2 * f2 * f2;
        return f1 * (1.0F - f2) + f2;
    }
    
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}
	
	@SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9) {
		
	}
	
	@Override
    public void setPortal(BlockPos pos) {
		
	}
	
	@Override
    public boolean isBurning(){
        return false;
    }

    @Override
    public boolean shouldRenderInPass(int pass){
        return pass == 1;
    }

    @Override
    public void setInWeb() {
    	
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
	
	public void onImpact(Entity curEntity, float damage) {
		DamageSource ds = new EntityDamageSource("directMagic", thrower)
				.setDamageBypassesArmor()
				.setMagicDamage();
		// TODO Auto-generated method stub
	}

	public void onImpact(Entity curEntity, float damage, String source) {
		DamageSource ds = new EntityDamageSource(source, thrower)
				.setDamageBypassesArmor()
				.setMagicDamage();
		// TODO Auto-generated method stub
	}
	
	public void onImpact(Entity curEntity, float damage, DamageSource ds) {
		curEntity.hurtResistantTime = 0;
		curEntity.attackEntityFrom(ds, damage);
		
		if(blade.isEmpty() || !(curEntity instanceof EntityLivingBase))
			return;
		
		blade.getItem().hitEntity(blade, (EntityLivingBase)curEntity, (EntityLivingBase) thrower);
		// TODO Auto-generated method stub
	}

}
