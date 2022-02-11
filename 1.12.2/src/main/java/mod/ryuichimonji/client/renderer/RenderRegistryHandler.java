package mod.ryuichimonji.client.renderer;

import mod.ryuichimonji.entity.EntityMobFelid;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class RenderRegistryHandler {
	public static void register() {
		RenderingRegistry.registerEntityRenderingHandler(EntityMobFelid.class, renderer ->{
			return new RenderMobFelid(renderer);
		});
	}

}
