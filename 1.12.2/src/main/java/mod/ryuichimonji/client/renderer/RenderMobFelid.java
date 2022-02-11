package mod.ryuichimonji.client.renderer;

import mod.ryuichimonji.entity.EntityMobFelid;
import mod.ryuichimonji.RyuIchimonji;
import mod.ryuichimonji.client.model.ModelMobFelid;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMobFelid extends RenderLiving{
	
	private static final ResourceLocation MobFeildTexture0 = new ResourceLocation(
			RyuIchimonji.MODID  + ":textures/entity/felid/tiger.png"
			);
	private static final ResourceLocation MobFeildTexture1 = new ResourceLocation(
			RyuIchimonji.MODID  + ":textures/entity/felid/tigerwhite.png"
			);
	private static final ResourceLocation MobFeildTexture2 = new ResourceLocation(
			RyuIchimonji.MODID  + ":textures/entity/felid/tigerblack.png"
			);
	private static final ResourceLocation MobFeildTexture3 = new ResourceLocation(
			RyuIchimonji.MODID  + ":textures/entity/felid/tigerblue.png"
			);
	private static final ResourceLocation MobFeildTexture4 = new ResourceLocation(
			RyuIchimonji.MODID  + ":textures/entity/felid/tigerred.png"
			);
	private static final ResourceLocation MobFeildTexture5 = new ResourceLocation(
			RyuIchimonji.MODID  + ":textures/entity/felid/tigergreen.png"
			);
	private static final ResourceLocation MobFeildTexture6 = new ResourceLocation(
			RyuIchimonji.MODID  + ":textures/entity/felid/tigerbrown.png"
			);
	
	public RenderMobFelid(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelMobFelid(), 1.0F);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO Auto-generated method stub
		
		byte index = ((EntityMobFelid) entity).getSkinIndex();
		
		if(index == 0) {
			return MobFeildTexture0;
		}
		else if(index == 1) {
			return MobFeildTexture1;
		}
		else if(index == 2) {
			return MobFeildTexture2;
		}
		else if(index == 3) {
			return MobFeildTexture3;
		}
		else if(index == 4) {
			return MobFeildTexture4;
		}
		else if(index == 5) {
			return MobFeildTexture5;
		}
		else if(index == 6) {
			return MobFeildTexture6;
		}
		
		return MobFeildTexture0;
	}

}
