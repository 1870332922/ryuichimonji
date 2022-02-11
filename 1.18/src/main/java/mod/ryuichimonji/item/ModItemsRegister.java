package mod.ryuichimonji.item;

import mod.ryuichimonji.utils.GlobalParam;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemsRegister {
    // All items here
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, GlobalParam.ModId);

    public static final RegistryObject<Item> KOMPEITO =
            ITEMS.register(
                    "kompeito"
                    , () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RyuIchimonjiModeTab).food(ModFoods.kompeito).stacksTo(16)) // 16 items per group
            );

    public static final RegistryObject<Item> KOMPEITO_P =
            ITEMS.register(
                    "kompeito_p"
                    , () -> new Item(new Item.Properties().tab(ModCreativeModeTab.RyuIchimonjiModeTab).food(ModFoods.kompeito_p).stacksTo(16)) // 16 items per group
            );

    public static final RegistryObject<Item> GOLDEN_TIGER =
            ITEMS.register(
                    "golden_tiger"
                    , () -> new Tiger(new Item.Properties().setNoRepair().tab(ModCreativeModeTab.RyuIchimonjiModeTab).stacksTo(1))
            );










    // ITEMS register
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
