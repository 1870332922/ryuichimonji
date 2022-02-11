package mod.ryuichimonji.entity;

import javax.annotation.Nullable;

import mod.ryuichimonji.RyuIchimonji;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityMobFelid extends EntityMob{
	
	public static final String ID = "MOB_FELID";
	public static final String NAME = RyuIchimonji.MODID +".MobFelid";
	
	private static final DataParameter<Byte> skinIndex = EntityDataManager.createKey(EntityMobFelid.class, DataSerializers.BYTE);

	public EntityMobFelid(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
		this.setSize(1.0F, 2.0F);
		this.experienceValue = 20;
	}
	
	@Override
	protected void initEntityAI(){
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(0, new EntityAIAvoidEntity(this, EntityCreeper.class, 4.0F, 0.6D, 1.0D));
		this.tasks.addTask(1, new EntityAIWander(this, 0.4));
        this.tasks.addTask(2, new EntityAIWanderAvoidWater(this, 0.6));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 4.0F));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0 , false));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIPanic(this, 1.2F));

        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, AbstractSkeleton.class, false));
		
    }
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataManager().register(skinIndex, (byte)0);
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data) {
		this.dataManager.set(skinIndex, (byte)this.rand.nextInt(7));
		
		return super.onInitialSpawn(difficulty, data);
	}
	
	@Override
	protected void applyEntityAttributes(){
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16.0D);
    }
	
	public byte getSkinIndex() {
		return this.getDataManager().get(skinIndex);
	}
	
	@Nullable
    protected ResourceLocation getLootTable(){
        return LootTableList.GAMEPLAY_FISHING;
    }

}
