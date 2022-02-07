package mod.ryuichimonji.potion;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class PotionRegistry {
	
	public static Potion PotionUnseenAid = new PotionUnseenAid();
	public static Potion PotionUnseenAidByFox = new PotionUnseenAidByFox();
	public static Potion PotionUnseenAidByLoong = new PotionUnseenAidByLoong();
	public static Potion PotionUnseenAidByTiger = new PotionUnseenAidByTiger();
	
	@SubscribeEvent
	public static void doPotionRegistry(RegistryEvent.Register<Potion> event) {
		// TODO Auto-generated method stub
		IForgeRegistry<Potion> registry = event.getRegistry();
		registry.register(PotionUnseenAid);
		registry.register(PotionUnseenAidByFox);
		registry.register(PotionUnseenAidByLoong);
		registry.register(PotionUnseenAidByTiger);
	}
}