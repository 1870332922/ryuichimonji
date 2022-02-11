package mod.ryuichimonji;

import mod.ryuichimonji.blade.Inarizusi;
import mod.ryuichimonji.blade.Toranoken;
import mod.ryuichimonji.blade.Wasabi;
import mod.ryuichimonji.block.BlockRegistry;
import mod.ryuichimonji.items.ItemRegistry;
import mod.ryuichimonji.world.WorldGen;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid = RyuIchimonji.MODID, name = RyuIchimonji.NAME, version = RyuIchimonji.VERSION, dependencies="required-after:forge@[14.23.4.2768,);required-after:flammpfeil.slashblade@[mc1.12-r15,);")
public class RyuIchimonji {
	public static final String MODID = "ryuichimonji";
    public static final String NAME = "RyuIchimonji";
    public static final String VERSION = "mc1.12.2-r1";
    
    public static final ResourceLocation RecipeGroup = new ResourceLocation(RyuIchimonji.MODID,"ryuichimonji");
    
    @Instance(RyuIchimonji.MODID)
	public static RyuIchimonji instance;
    
    @SidedProxy(clientSide = "mod.ryuichimonji.client.ClientProxy",serverSide = "mod.ryuichimonji.CommonProxy")
    public static CommonProxy proxy; 
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit(event);
        GameRegistry.registerWorldGenerator(new WorldGen(), 3);
        OreDictionary.registerOre("oreQuartz", BlockRegistry.quartzore);
		
		
        OreDictionary.registerOre("listAllsugar", ItemRegistry.kompeito);
        OreDictionary.registerOre("molecule_sucrose", ItemRegistry.kompeito);
        OreDictionary.registerOre("kompeito", ItemRegistry.kompeito);
        OreDictionary.registerOre("sugar", ItemRegistry.kompeito);
        
        OreDictionary.registerOre("listAllsugar", ItemRegistry.kompeitop);
        OreDictionary.registerOre("molecule_sucrose", ItemRegistry.kompeitop);
        OreDictionary.registerOre("kompeito", ItemRegistry.kompeitop);
        OreDictionary.registerOre("sugar", ItemRegistry.kompeitop);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
        proxy.init(event);
        Wasabi.registBlade();
        Inarizusi.registBlade();
        Toranoken.registBlade();
        mod.ryuichimonji.blade.RyuIchimonji.registBlade();
        
    
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
        
        ItemRegistry.registRecipe();
        Wasabi.registRecipe();
        Inarizusi.registRecipe();
        Toranoken.registRecipe();
        mod.ryuichimonji.blade.RyuIchimonji.registRecipe();
    
    }
    
    public static void addRecipe(String key, String name, IRecipe recipe){
    	recipe.setRegistryName(new ResourceLocation(SlashBlade.modid, name));
		SlashBlade.addRecipe(key, recipe);
	}
    
    public static void addRecipe(String key, IRecipe recipe){
		addRecipe(key, key, recipe);
	}
}
