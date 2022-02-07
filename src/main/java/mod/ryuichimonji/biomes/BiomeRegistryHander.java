package mod.ryuichimonji.biomes;

import mod.ryuichimonji.RyuIchimonji;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class BiomeRegistryHander {
	
	public static Biome PATUM = new BiomePatum(
			new BiomeProperties("Patum")
			.setWaterColor(0xd0e9f6)
			.setTemperature(0.8F)
			.setRainfall(0.9F)
			.setBaseHeight(0.2F)
			.setHeightVariation(0.2F));
	
	
	@SubscribeEvent
	public static void onRegisterBiomes(Register<Biome> event) {
		final BiomeRegistry biomes = new BiomeRegistry(event.getRegistry());
		
		biomes.register(
				"patum", PATUM, Type.PLAINS);
		
		BiomeManager.addBiome(BiomeManager.BiomeType.WARM , new BiomeManager.BiomeEntry(PATUM, 8));
	}
	
	private static class BiomeRegistry {

		private final IForgeRegistry<Biome> registry;

		BiomeRegistry(IForgeRegistry<Biome> registry) {

			this.registry = registry;
		}

	public void register(String registryName, Biome biome, Type... biomeTypes) {
		biome.setRegistryName(RyuIchimonji.MODID, registryName);
		registry.register(biome);
		BiomeDictionary.addTypes(biome, biomeTypes);
		}
	}
}
