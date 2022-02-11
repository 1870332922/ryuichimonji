package mod.ryuichimonji.blade;

import mod.ryuichimonji.RyuIchimonji;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Wasabi {
	static public final String NAME = "ryuichimonji.blade.wasabi";
	static private final String KEY = "ryuichimonji.Wasabi";
	public static void registBlade() {
		ItemStack blade = new ItemStack(SlashBlade.bladeNamed, 1, 0);
		NBTTagCompound tag = new NBTTagCompound();

		blade.setTagCompound(tag);
		ItemSlashBladeNamed.CurrentItemName.set(tag, NAME);
		ItemSlashBladeNamed.CustomMaxDamage.set(tag, 60);
		ItemSlashBlade.setBaseAttackModifier(tag, 8F);
		ItemSlashBlade.TextureName.set(tag, "wasabi/wasabi");
		ItemSlashBlade.ModelName.set(tag, "wasabi/wasabi");
		ItemSlashBlade.SpecialAttackType.set(tag, 19);
		ItemSlashBlade.StandbyRenderType.set(tag, 0);
		ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
		ItemSlashBladeNamed.NamedBlades.add(NAME);
		
		//SpecialEffects.addEffect(blade,  "Kamui", 10);
		//SpecialEffects.addEffect(blade,  "NeoBurstDrive", 30);
		
		SlashBlade.registerCustomItemStack(NAME, blade);
	}
	
	public static void registRecipe(){
		ItemStack target = SlashBlade.getCustomBlade(NAME);
		
		ItemStack sphere = SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.SphereBladeSoulStr, 1);
		 
        ItemStack reqiredBlade = SlashBlade.getCustomBlade("slashblade");
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(reqiredBlade);
        ItemSlashBlade.KillCount.set(reqTag, 600);
        
        RyuIchimonji.addRecipe(KEY,
				new RecipeAwakeBladeRandom(RyuIchimonji.RecipeGroup,
						target, 
						reqiredBlade,
					"CSC",
					"CBC",
					"SCS",
					'C', Items.IRON_INGOT,
					'S', sphere,
					'B', reqiredBlade)
        		);
		
	}
}
