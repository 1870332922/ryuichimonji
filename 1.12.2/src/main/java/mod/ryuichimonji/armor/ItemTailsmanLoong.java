package mod.ryuichimonji.armor;

import mod.ryuichimonji.RyuIchimonji;
import mod.ryuichimonji.creativetab.TabRyuIchimonji;
import mod.ryuichimonji.items.ItemRegistry;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;

public class ItemTailsmanLoong extends ItemArmor{
	
	public ItemTailsmanLoong(EntityEquipmentSlot equipmentSlotIn) {
		super(ItemRegistry.tailsmanloong, 0, equipmentSlotIn);
		// TODO Auto-generated constructor stub
		this.setUnlocalizedName(RyuIchimonji.MODID + "."+ "tailsmanloong");
		this.setRegistryName("tailsmanloong");
		this.setCreativeTab(TabRyuIchimonji.tabRyuIchimonji);
	}
	
	@Deprecated // Use getRepairItemStack below
    public Item getRepairItem(){
		return ItemRegistry.loong;
		
    }
}