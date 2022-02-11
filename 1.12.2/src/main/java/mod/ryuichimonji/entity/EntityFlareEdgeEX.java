package mod.ryuichimonji.entity;

import mods.flammpfeil.slashblade.ability.TeleportCanceller;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class EntityFlareEdgeEX extends EntityDriveEievui{
	public EntityFlareEdgeEX(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	public EntityFlareEdgeEX(World par1World, EntityLivingBase entityLiving, float AttackLevel, boolean multiHit) {
		super(par1World, entityLiving, AttackLevel, multiHit);
		setColor(0XFF0000);
		// TODO Auto-generated constructor stub
	}

	public EntityFlareEdgeEX(World par1World, EntityLivingBase entityLiving, float AttackLevel, boolean multiHit, float roll) {
		super(par1World, entityLiving, AttackLevel, multiHit, roll);
		// TODO Auto-generated constructor stub
	}
	
	 public EntityFlareEdgeEX(World par1World, EntityLivingBase entityLiving,float AttackLevel) {
		 super(par1World, entityLiving, AttackLevel);
			// TODO Auto-generated constructor stub
	 }
	 
	 public void onImpact(Entity curEntity, float damage) {
		 
		 if(TeleportCanceller.canTeleport(curEntity)){
			 TeleportCanceller.setCancel(curEntity);
		 }
		 curEntity.setFire(5);
		 
		 DamageSource ds = new EntityDamageSource(DamageSource.IN_FIRE.damageType, getThrower());
		 
		 super.onImpact(curEntity, damage, ds);
		 curEntity.attackEntityFrom(ds, damage);
		 
		 if((!curEntity.world.isRemote) && (this.blade != null) && ((curEntity instanceof EntityLivingBase))) {
		      ((ItemSlashBlade)this.blade.getItem()).hitEntity(this.blade, (EntityLivingBase)curEntity, (EntityLivingBase)this.thrower);
		 }
	 }
}
