package mod.ryuichimonji.entity;

import java.util.List;

import com.google.common.base.Predicate;

import mods.flammpfeil.slashblade.ability.TeleportCanceller;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class EntitySlashBladeLightningBolt extends EntityLightningBolt{
	
	private final Entity target;
	private final Predicate<Entity> selector;
	private int lightningState;
	private int boltLivingTime;
	private int color = 0xffffff;
	
	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntitySlashBladeLightningBolt.class, DataSerializers.VARINT);

	public EntitySlashBladeLightningBolt(World worldIn) {
		super(worldIn, 0, 0, 0, true);
		target = null;
		selector = null;
		// TODO Auto-generated constructor stub
	}
	
	public EntitySlashBladeLightningBolt(World worldIn, Entity target, Predicate<Entity> selector){
		super(worldIn, target.posX, target.posY, target.posZ, true);

		this.target = target;
		this.selector = selector;

		this.lightningState = 5;
		this.boltLivingTime = this.rand.nextInt(3) + 1;
	}
	
	public EntitySlashBladeLightningBolt(World worldIn, Entity target, Predicate<Entity> selector, int color){
		super(worldIn, target.posX, target.posY, target.posZ, true);

		this.target = target;
		this.selector = selector;

		this.lightningState = 5;
		this.boltLivingTime = this.rand.nextInt(3) + 1;
		this.color = color;
	}
	
	@Override
    protected void entityInit(){
		EntityDataManager manager = getDataManager();
		manager.register(COLOR, color);
    }
	
	@Override
	public void onUpdate(){
		onEntityUpdate();
		
		if (lightningState == 5) {
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
            this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.WEATHER, 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
		}
    
		if (--lightningState < 0) {

			if (boltLivingTime == 0) {
				setDead();
			} else if (lightningState < -this.rand.nextInt(10)) {
				--boltLivingTime;
				lightningState = 1;

				this.boltVertex = this.rand.nextLong();
			}
		}

		if (lightningState < 0)
			return;

		final double AMBIT = 3.0D;
		AxisAlignedBB bb = new AxisAlignedBB(this.posX - AMBIT,
											 this.posY - AMBIT,
											 this.posZ - AMBIT,
											 this.posX + AMBIT,
											 this.posY + 6.0D + AMBIT,
											 this.posZ + AMBIT);
		List<Entity> list = world.getEntitiesInAABBexcluding(this, bb, selector);
		if (target.isEntityAlive())
			list.add(target);

		for (Entity entity : list) {
			TeleportCanceller.setCancel(entity);
			
			if (!this.world.isRemote &&
				!ForgeEventFactory.onEntityStruckByLightning(entity, this)) {
				
				entity.onStruckByLightning(this);
			}
		}
	}
	
	public void setColor(int color) {
		getDataManager().set(COLOR, color);
		this.color = color;
	}
	
	public int getColor() {
		return getDataManager().get(COLOR);
	}
}