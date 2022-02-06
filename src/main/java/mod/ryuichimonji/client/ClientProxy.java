package mod.ryuichimonji.client;

import mod.ryuichimonji.CommonProxy;
import mod.ryuichimonji.client.renderer.RenderEntityDriveEievui;
import mod.ryuichimonji.client.renderer.RenderEntityDriveEievuiEX;
import mod.ryuichimonji.client.renderer.RenderEntitySlashBladeLightningBolt;
import mod.ryuichimonji.client.renderer.RenderEntityStormSwordsEX;
import mod.ryuichimonji.client.renderer.RenderRegistryHandler;
import mod.ryuichimonji.entity.EntityDriveEievui;
import mod.ryuichimonji.entity.EntityDriveEievuiEX;
import mod.ryuichimonji.entity.EntitySlashBladeLightningBolt;
import mod.ryuichimonji.entity.EntityStormSwordsEX;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	
	public static final ResourceLocation ryuichimonji = new ResourceLocation(SlashBlade.modid,"ryuichimonji");
	
	@Override
    public void preInit(FMLPreInitializationEvent event){
		// TODO Auto-generated method stub
		RenderRegistryHandler.register();
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain("ryuichimonji");
        RenderingRegistry.registerEntityRenderingHandler(EntityDriveEievui.class,
        		new IRenderFactory<EntityDriveEievui>() {
        	@Override
        	public Render<? super EntityDriveEievui>createRenderFor(RenderManager manager){
        		return new RenderEntityDriveEievui(manager);
        	}
        });
        
        RenderingRegistry.registerEntityRenderingHandler(EntityDriveEievuiEX.class,
        		new IRenderFactory<EntityDriveEievuiEX>() {
        	@Override
        	public Render<? super EntityDriveEievuiEX>createRenderFor(RenderManager manager){
        		return new RenderEntityDriveEievuiEX(manager);
        	}
        });
        
        RenderingRegistry.registerEntityRenderingHandler(EntityStormSwordsEX.class,
        		new IRenderFactory<EntityStormSwordsEX>() {
        	@Override
        	public Render<? super EntityStormSwordsEX>createRenderFor(RenderManager manager){
        		return new RenderEntityStormSwordsEX(manager);
        	}
        });
        
        RenderingRegistry.registerEntityRenderingHandler(EntitySlashBladeLightningBolt.class,
        		new IRenderFactory<EntitySlashBladeLightningBolt>() {
        	@Override
        	public Render<? super EntitySlashBladeLightningBolt>createRenderFor(RenderManager manager){
        		return new RenderEntitySlashBladeLightningBolt(manager);
        	}
        });
    }
	
	@Override
    public void init(FMLInitializationEvent event){
		// TODO Auto-generated method stub
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        // TODO Auto-generated method stub
        super.postInit(event);
    }

}
