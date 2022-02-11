package mod.ryuichimonji.creativetab;

import mod.ryuichimonji.items.ItemRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabRyuIchimonji {
	public static final CreativeTabs tabRyuIchimonji = new CreativeTabs("TabRyuIchimonji") {
		
		@Override
		public ItemStack getTabIconItem() {
			// TODO Auto-generated method stub
			return new ItemStack(ItemRegistry.tiger);
		}
		
	};
}
