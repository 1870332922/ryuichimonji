package mod.ryuichimonji.entity;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class EntityRegisterHander {
	public static final EntityEntry feline = EntityEntryBuilder.create().entity(EntityMobFelid.class)
			.id(EntityMobFelid.ID, 0)
			.name(EntityMobFelid.NAME)
			.tracker(80, 3, true).egg(0x02e0ee, 0xff66ae)
			.build();
	
	@SubscribeEvent
	public static void onRegistry(RegistryEvent.Register<EntityEntry> event) {
		IForgeRegistry<EntityEntry> registry = event.getRegistry();
		registry.register(feline);
	}

}
