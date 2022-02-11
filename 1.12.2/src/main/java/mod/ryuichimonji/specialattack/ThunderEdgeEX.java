package mod.ryuichimonji.specialattack;

import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;

import mod.ryuichimonji.Math2;
import mod.ryuichimonji.entity.EntityLightningSwordEX;
import mod.ryuichimonji.entity.EntityStormSwordsEX;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThunderEdgeEX extends PhantomSwordsBase{
	
	public static String AttackType = StylishRankManager.AttackTypes.registerAttackType("ThunderEdgeEX", 0.5F);

	@Override
	public String toString()
	{
		return "ThunderEdgeEX";
	}

	@Override
	protected void spawnEntity(float damage, int count,
							   Entity target, EntityPlayer player)
	{

		final World world = player.world;
		final int targetId = target.getEntityId();


		final float rotUnit = 360.0f / count;
		final Random rnd = player.getRNG();

		for (int i = 0; i < count; i++) {
			EntityStormSwordsEX entity = new EntityStormSwordsEX(world, player, damage, this);
			entity.setLifeTime(30);
			entity.setTargetEntityId(targetId);
			entity.setColor(0x705DA8);
			entity.setInterval(7 + i / 2);

			final float rot = rotUnit*i;
			
			double x = -2.0*Math2.getInstance().sin(rot);
			double y =  2.0*(1.0 + rnd.nextDouble()) + 0.5;
			double z =  2.0*Math2.getInstance().cos(rot);

			entity.setInitialPosition(
				target.posX + x,
				target.posY + y,
				target.posZ + z,
				rot + 180,
				90.0f,
				90.0f,
				EntityStormSwordsEX.SPEED);

			world.spawnEntity(entity);
		}
		
		for (int i = 0; i < count; i++) {
			EntityStormSwordsEX entity = new EntityStormSwordsEX(world, player, damage, this);
			entity.setLifeTime(30);
			entity.setTargetEntityId(targetId);
			entity.setColor(0x705DA8);
			entity.setInterval(7 + i / 2);

			final float rot = rotUnit*i;
			
			double x = -2.0*Math2.getInstance().sin(rot);
			double y =  2.0*(1.0 + rnd.nextDouble()) + 0.5;
			double z =  2.0*Math2.getInstance().cos(rot);

			entity.setInitialPosition(
				target.posX + x,
				target.posY + y,
				target.posZ + z,
				rot + 180,
				90.0f,
				90.0f,
				EntityStormSwordsEX.SPEED);

			world.spawnEntity(entity);
		}
		
		for(int i = 0;i<3;i++) {
			EntityLightningSwordEX entity = new EntityLightningSwordEX(world, player, damage);
			entity.setColor(0xFFD700);
			entity.setInterval(7 + count / 2 + 10);
			entity.setLifeTime(40);
							
			entity.setTargetEntityId(targetId);

			entity.setInitialPosition(
				target.posX,
				target.posY + 4.5,
				target.posZ,
				180.0f,
				90.0f,
				90.0f,
				EntityStormSwordsEX.SPEED);
			
			world.spawnEntity(entity);
		}
		
	}

	public void onImpact(Entity target)
	{
		target.motionX = 0.0;
		target.motionY = 0.0;
		target.motionZ = 0.0;
	}

	public void onSticking(Entity target)
	{
		target.motionX = 0.0;
		target.motionY = 0.0;
		target.motionZ = 0.0;

	}
}
