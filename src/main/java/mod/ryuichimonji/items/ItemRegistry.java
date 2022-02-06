package mod.ryuichimonji.items;

import mod.ryuichimonji.RyuIchimonji;
import mod.ryuichimonji.armor.ItemTailsmanFox;
import mod.ryuichimonji.armor.ItemTailsmanLoong;
import mod.ryuichimonji.armor.ItemTailsmanTiger;
import mod.ryuichimonji.block.BlockRegistry;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class ItemRegistry {
	public static final ItemTiger tiger = new ItemTiger();
	public static final ItemFox fox = new ItemFox();
	public static final ItemLoong loong = new ItemLoong();
	
	public static final ItemBladeStand0 stand0 = new ItemBladeStand0();
	public static final ItemBladeStand1 stand1 = new ItemBladeStand1();
	public static final ItemBladeStand2 stand2 = new ItemBladeStand2();
	public static final ItemBladeStand3 stand3 = new ItemBladeStand3();
	
	public static final ItemKompeito kompeito = new ItemKompeito(6, 9.6F, false);
	public static final ItemKompeito_P kompeitop = new ItemKompeito_P(6, 9.6F, false);
	
	public static final ItemBlock quartzore = new ItemBlock(BlockRegistry.quartzore);
	
	@SubscribeEvent
	public static void onRegistry(Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		
		registry.register(tiger);
		registry.register(fox);
		registry.register(loong);
		
		quartzore.setRegistryName(quartzore.getBlock().getRegistryName());
		registry.register(quartzore);
		
		registry.register(tailsmantigeri);
		registry.register(tailsmanfoxi);
		registry.register(tailsmanloongi);
		
		registry.register(stand0);
		registry.register(stand1);
		registry.register(stand2);
		registry.register(stand3);
		
		registry.register(kompeito);
		registry.register(kompeitop);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public static void onModelRegistry(ModelRegistryEvent event) {
		ModelLoader.setCustomModelResourceLocation(tiger, 0, 
				new ModelResourceLocation("ryuichimonji:tiger", "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(fox, 0, 
				new ModelResourceLocation("ryuichimonji:fox", "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(loong, 0, 
				new ModelResourceLocation("ryuichimonji:loong", "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(quartzore, 0, 
				new ModelResourceLocation("ryuichimonji:quartz_ore", "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(tailsmanfoxi, 0,
				new ModelResourceLocation("ryuichimonji:tailsmanfox", "inventory"));
		ModelLoader.setCustomModelResourceLocation(tailsmantigeri, 0,
				new ModelResourceLocation("ryuichimonji:tailsmantiger", "inventory"));
		ModelLoader.setCustomModelResourceLocation(tailsmanloongi, 0,
				new ModelResourceLocation("ryuichimonji:tailsmanloong", "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(stand0, 0,
				new ModelResourceLocation("ryuichimonji:bladestandtype0", "inventory"));
		ModelLoader.setCustomModelResourceLocation(stand1, 0,
				new ModelResourceLocation("ryuichimonji:bladestandtype1", "inventory"));
		ModelLoader.setCustomModelResourceLocation(stand2, 0,
				new ModelResourceLocation("ryuichimonji:bladestandtype2", "inventory"));
		ModelLoader.setCustomModelResourceLocation(stand3, 0,
				new ModelResourceLocation("ryuichimonji:bladestandtype3", "inventory"));
		
		ModelLoader.setCustomModelResourceLocation(kompeito, 0,
				new ModelResourceLocation("ryuichimonji:kompeito", "inventory"));
		ModelLoader.setCustomModelResourceLocation(kompeitop, 0,
				new ModelResourceLocation("ryuichimonji:kompeito_p", "inventory"));
	}
	
	public static void registRecipe(){
		
		ItemStack targettiger = new ItemStack(tiger);
		ItemStack targettailsmantiger = new ItemStack(tailsmantigeri);
		
		ItemStack proudSoul = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.ProudSoulStr, 1);
		ItemStack ingot= SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.IngotBladeSoulStr, 1);
		ItemStack sphere= SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1);
		ItemStack tiny= SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.TinyBladeSoulStr, 1);
		
		ItemStack targetstand0 = new ItemStack(stand0);
		ItemStack targetstand1 = new ItemStack(stand1);
		ItemStack targetstand2 = new ItemStack(stand2);
		ItemStack targetstand3 = new ItemStack(stand3);
		
		ItemStack targetkompeito = new ItemStack(kompeito);
		ItemStack targetkompeitop = new ItemStack(kompeitop);
		
		RyuIchimonji.addRecipe("ryuichimonji:kompeito", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targetkompeito, 
				" X ",
				" S ",
				"   ",
				'X', tiny,
				'S', Items.SUGAR)
				);
		
		RyuIchimonji.addRecipe("ryuichimonji:kompeito_p", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targetkompeitop, 
				"   ",
				" S ",
				" X ",
				'X', tiny,
				'S', Items.SUGAR)
				);
		
		RyuIchimonji.addRecipe("ryuichimonji:tiger", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targettiger, 
				"G G",
				" P ",
				"G G",
				'P', proudSoul,
				'G', Items.GOLD_INGOT)
				);
		
		RyuIchimonji.addRecipe("ryuichimonji:tailsmantiger", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targettailsmantiger, 
				"W W",
				" C ",
				"   ",
				'C', targettiger,
				'W', Items.STRING)
				);
		
		ItemStack targetfox = new ItemStack(fox);
		ItemStack targettailsmanfox = new ItemStack(tailsmanfoxi);
		
		RyuIchimonji.addRecipe("ryuichimonji:fox", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targetfox, 
				"  B",
				"BPB",
				"B  ",
				'P', proudSoul,
				'B', Items.REDSTONE)
				);
		
		RyuIchimonji.addRecipe("ryuichimonji:tailsmanfox", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targettailsmanfox, 
				"W W",
				" C ",
				"   ",
				'C', targetfox,
				'W', Items.STRING)
				);
		
		ItemStack targetloong = new ItemStack(loong);
		ItemStack targettailsmanloong = new ItemStack(tailsmanloongi);
		
		RyuIchimonji.addRecipe("ryuichimonji:loong", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targetloong, 
				" G ",
				"GPG",
				" G ",
				'P', proudSoul,
				'G', Items.PRISMARINE_CRYSTALS)
				);
		
		RyuIchimonji.addRecipe("ryuichimonji:tailsmanloong", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targettailsmanloong, 
				"W W",
				" C ",
				"   ",
				'C', targetloong,
				'W', Items.STRING)
				);
		
		RyuIchimonji.addRecipe("ryuichimonji:stand0", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targetstand0, 
				" X ",
				" V ",
				"   ",
				'X', proudSoul,
				'V', Blocks.OAK_FENCE)
				);
		
		RyuIchimonji.addRecipe("ryuichimonji:stand1", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targetstand1, 
				" X ",
				" V ",
				"   ",
				'X', ingot,
				'V', Blocks.OAK_FENCE)
				);
		
		RyuIchimonji.addRecipe("ryuichimonji:stand2", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targetstand2, 
				" X ",
				" V ",
				"   ",
				'X', sphere,
				'V', Blocks.OAK_FENCE)
				);
		
		RyuIchimonji.addRecipe("ryuichimonji:stand3", new ShapedOreRecipe(RyuIchimonji.RecipeGroup, targetstand3, 
				" X ",
				" V ",
				"   ",
				'X', tiny,
				'V', Blocks.OAK_FENCE)
				);
		
	}
	
	public static final ItemArmor.ArmorMaterial tailsmanfox = EnumHelper.addArmorMaterial("tailsmanfox", "ryuichimonji:tailsmanfox", 0, new int[] {3, 6, 8, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5);
	public static final ItemTailsmanFox tailsmanfoxi = new ItemTailsmanFox(EntityEquipmentSlot.CHEST);
	
	public static final ItemArmor.ArmorMaterial tailsmantiger = EnumHelper.addArmorMaterial("tailsmantiger", "ryuichimonji:tailsmantiger", 0, new int[] {3, 6, 8, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5);
	public static final ItemTailsmanTiger tailsmantigeri = new ItemTailsmanTiger(EntityEquipmentSlot.CHEST);
	
	public static final ItemArmor.ArmorMaterial tailsmanloong = EnumHelper.addArmorMaterial("tailsmanloong", "ryuichimonji:tailsmanloong", 0, new int[] {3, 6, 8, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 5);
	public static final ItemTailsmanLoong tailsmanloongi = new ItemTailsmanLoong(EntityEquipmentSlot.CHEST);
	
}
