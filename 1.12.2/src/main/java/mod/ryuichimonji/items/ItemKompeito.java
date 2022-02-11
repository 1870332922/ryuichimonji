package mod.ryuichimonji.items;

import mod.ryuichimonji.RyuIchimonji;
import mod.ryuichimonji.creativetab.TabRyuIchimonji;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemKompeito extends ItemFood{
	
	public final int itemUseDuration;

	public ItemKompeito(int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		// TODO Auto-generated constructor stub
		
		this.itemUseDuration = 8;
		
		this.setAlwaysEdible();
		this.setUnlocalizedName(RyuIchimonji.MODID + "."+ "kompeito");
		this.setRegistryName("kompeito");
		this.setMaxStackSize(16);
		this.setMaxDamage(0);
		this.setCreativeTab(TabRyuIchimonji.tabRyuIchimonji);
		
	}
	
	public int getMaxItemUseDuration(ItemStack stack) {
        return itemUseDuration;
    }


}
