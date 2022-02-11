package mod.ryuichimonji;

import mod.ryuichimonji.block.ModBlocksRegister;
import mod.ryuichimonji.effect.ModEffectRegister;
import mod.ryuichimonji.item.ModItemsRegister;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("ryuichimonji")
public class RyuIchimonji {
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public RyuIchimonji() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Registry custom Items \ Blocks \ potion
        ModItemsRegister.register(eventBus);
        ModBlocksRegister.register(eventBus);
        ModEffectRegister.register(eventBus);



        // event listener
        eventBus.addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
