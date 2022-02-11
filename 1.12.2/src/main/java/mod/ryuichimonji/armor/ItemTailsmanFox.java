package mod.ryuichimonji.armor;

import java.util.function.Function;

import mod.ryuichimonji.RyuIchimonji;
import mod.ryuichimonji.creativetab.TabRyuIchimonji;
import mod.ryuichimonji.items.ItemRegistry;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

public class ItemTailsmanFox extends ItemArmor{
	
	public ItemTailsmanFox(EntityEquipmentSlot equipmentSlotIn) {
		super(ItemRegistry.tailsmanfox, 0, equipmentSlotIn);
		// TODO Auto-generated constructor stub
		this.setUnlocalizedName(RyuIchimonji.MODID + "."+ "tailsmanfox");
		this.setRegistryName("tailsmanfox");
		this.setCreativeTab(TabRyuIchimonji.tabRyuIchimonji);
	}
	
	@Deprecated // Use getRepairItemStack below
    public Item getRepairItem(){
		return ItemRegistry.fox;
		
    }
}
