package mod.ryuichimonji.block;

import mod.ryuichimonji.item.ModCreativeModeTab;
import mod.ryuichimonji.item.ModItemsRegister;
import mod.ryuichimonji.utils.GlobalParam;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocksRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, GlobalParam.ModId);

    // quartz_ore
    public static final RegistryObject<Block> QUARTZ_ORE = registerBlock(
            "quartz_ore",
            () -> new OreBlock(
                    BlockBehaviour.Properties.of(Material.METAL)
                            .strength(3.0f)
                            .requiresCorrectToolForDrops()
                    , UniformInt.of(2,7)     // Drop 2-7 exp
            )
            , ModCreativeModeTab.RyuIchimonjiModeTab
    );


















    // block register
    protected static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block,CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name,block);
        registerBlockItem(name,toReturn,tab);
        return toReturn;
    }

    // blockItem register
    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ModItemsRegister.ITEMS.register(name, () -> new BlockItem(block.get(),
                        new Item.Properties().tab(tab)));
    }

    // BLOCKS register
    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
