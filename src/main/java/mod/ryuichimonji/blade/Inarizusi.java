package mod.ryuichimonji.blade;

import mod.ryuichimonji.RyuIchimonji;
import mod.ryuichimonji.items.ItemRegistry;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Inarizusi {
	static public final String NAME = "ryuichimonji.blade.inarizusi";
	static private final String KEY = "ryuichimonji.inarizusi";
	public static void registBlade() {
		ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();
		
		blade.setTagCompound(tag);

		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, 100);
		ItemSlashBlade.setBaseAttackModifier(tag, 12);
		ItemSlashBlade.TextureName.set(tag, "inarizusi/inarizusi");
		ItemSlashBlade.ModelName.set(tag, "inarizusi/inarizusi");
		ItemSlashBlade.SpecialAttackType.set(tag, 22);
		ItemSlashBlade.StandbyRenderType.set(tag, 2);
		ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
		ItemSlashBladeNamed.NamedBlades.add(NAME);
		
		SpecialEffects.addEffect(blade,  "Kamui", 10);
		SpecialEffects.addEffect(blade,  "NeoBurstDrive", 30);

		blade.addEnchantment(Enchantments.POWER, 3);
		blade.addEnchantment(Enchantments.UNBREAKING, 3);

		SlashBlade.registerCustomItemStack(NAME, blade);
	}
	
	public static void registRecipe(){
		ItemStack target = SlashBlade.getCustomBlade(NAME);
		ItemStack sphere = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1);
		 
        ItemStack reqiredBlade = SlashBlade.getCustomBlade(Wasabi.NAME);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(reqiredBlade);
        ItemSlashBlade.KillCount.set(reqTag, 1200);
        
        RyuIchimonji.addRecipe(KEY,
				new RecipeAwakeBlade(RyuIchimonji.RecipeGroup,
						target, 
						reqiredBlade,
					"CSC",
					"CBC",
					"SCS",
					'C', ItemRegistry.fox,
					'S', sphere,
					'B', reqiredBlade)
        		);
		
	}
}
