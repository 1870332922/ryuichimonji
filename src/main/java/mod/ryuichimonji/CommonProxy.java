package mod.ryuichimonji;

import org.lwjgl.Sys;

import mod.ryuichimonji.client.ClientProxy;
import mod.ryuichimonji.creativetab.TabRyuIchimonji;
import mod.ryuichimonji.entity.EntityAquaEdgeEX;
import mod.ryuichimonji.entity.EntityDriveEievui;
import mod.ryuichimonji.entity.EntityDriveEievuiEX;
import mod.ryuichimonji.entity.EntityFlareEdgeEX;
import mod.ryuichimonji.entity.EntityLightningSwordEX;
import mod.ryuichimonji.entity.EntitySlashBladeLightningBolt;
import mod.ryuichimonji.entity.EntityStormSwordsEX;
import mod.ryuichimonji.specialattack.AquaEdgeEX;
import mod.ryuichimonji.specialattack.ConvergingWishes;
import mod.ryuichimonji.specialattack.FlareEdgeEX;
import mod.ryuichimonji.specialattack.ThunderEdgeEX;
import mod.ryuichimonji.specialeffect.Kamui;
import mod.ryuichimonji.specialeffect.NeoBurstDrive;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		// TODO Auto-generated method stub
		new TabRyuIchimonji();
		
		MinecraftForge.EVENT_BUS.register(this);
		SlashBlade.InitEventBus.register(this);
		
		//0 SlashDimension
		//1 Drive
		//2 WaveEdge
		//3 Drive
		//4 Spear
		//5 CircleSlash
		//6 BlisteringWitherSwords
		//7 SakuraEnd
		//8 MaximumBet
		//9 SlashDimensionSpiral
		//10 DimensionCrash
		//11 Koorinoyaiba
		//12 Kubikarinoyaiba
		//13 Dimensionimpact
		//14 hakurouSA
		//15 roukanSA
		//16 SAFoxProtection
		//17 SAGodFoxPower
		//18 ShinkuSmash
		ItemSlashBlade.specialAttacks.put(Integer.valueOf(19), new ConvergingWishes());
		//20 OverSlash
		ItemSlashBlade.specialAttacks.put(Integer.valueOf(21), new AquaEdgeEX());
		ItemSlashBlade.specialAttacks.put(Integer.valueOf(22), new FlareEdgeEX());
		ItemSlashBlade.specialAttacks.put(Integer.valueOf(23), new ThunderEdgeEX());
		//30 RapidPhantomSwords
		//31 SpiralEdge
		//32 GalePhantomSwords
		//35 None
		//36 AquaEdge
		//37 FlareSpiral
		//38 LightningSwords
		//39 TerraSwords
		//42 avadaKedavra
		//43 CrossEdge
		//44 SlashDimensionLust
		//45 SlashDimensionLightning
		//46 HeavyRainLightning
		//47 NekoPunch
		//48 KamiCrossEdge
		//49 JustFewLightning
		//50 SunShineSpike
		//51 Insane
		//52 BiteOfIce
		//53 LunarZephyr
		//54 TideSlash
		//55 Rushduel
		//56 SsakuyaSA
		//57 DeepDarkImpact
		//152 SACursedBlood
		//263 SASpaceDim
		//264 SlashDimension_old
		//265 SASakuraSkill
		//12075 SAslashdimension_ex
		//12076 SAspear_ex
		//12077 SAsakuraend_ex
		//750954 SA_HighEnergyParticleFlow_alpha
		//750955 SA_EnergyLevelTransition
		//760959 SA_KineticImpact
		//13248569 SA_BloodRev
		//13248999 SA_CraftRev
		
		SpecialEffects.register(new NeoBurstDrive());
		SpecialEffects.register(new Kamui());
		
	}
	
	public void init(FMLInitializationEvent event) {
		// TODO Auto-generated method stub
		EntityRegistry.registerModEntity(ClientProxy.ryuichimonji, EntityDriveEievui.class, "EntityDriveEievui", 1, RyuIchimonji.instance, 250, 1, true);
		EntityRegistry.registerModEntity(ClientProxy.ryuichimonji, EntitySlashBladeLightningBolt.class, "EntitySlashBladeLightningBolt", 2, RyuIchimonji.instance, 250, 1, true);
		EntityRegistry.registerModEntity(ClientProxy.ryuichimonji, EntityStormSwordsEX.class, "EntityStormSwordsEX", 3, RyuIchimonji.instance, 250, 1, true);
		EntityRegistry.registerModEntity(ClientProxy.ryuichimonji, EntityDriveEievuiEX.class, "EntityDriveEievuiEX", 4, RyuIchimonji.instance, 250, 1, true);
		EntityRegistry.registerModEntity(ClientProxy.ryuichimonji, EntityAquaEdgeEX.class, "EntityAquaEdgeEX", 5, RyuIchimonji.instance, 250, 1, true);
		EntityRegistry.registerModEntity(ClientProxy.ryuichimonji, EntityFlareEdgeEX.class, "EntityFlareEdgeEX", 6, RyuIchimonji.instance, 250, 1, true);
		EntityRegistry.registerModEntity(ClientProxy.ryuichimonji, EntityLightningSwordEX.class, "EntityLightningSwordEX", 7, RyuIchimonji.instance, 250, 1, true);
		
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub
		
	}
}
