package mod.ryuichimonji.item;

import mod.ryuichimonji.block.ModBlocksRegister;
import mod.ryuichimonji.utils.GlobalParam;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

// Creative Tab
public class ModCreativeModeTab {
    public static final CreativeModeTab RyuIchimonjiModeTab = new CreativeModeTab(GlobalParam.ModId) {
        @Override
        @NotNull
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocksRegister.QUARTZ_ORE.get());
        }
    };
}
