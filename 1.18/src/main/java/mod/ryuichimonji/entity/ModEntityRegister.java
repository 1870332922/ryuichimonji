package mod.ryuichimonji.entity;

import mod.ryuichimonji.utils.GlobalParam;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntityRegister {

    private void EntityInit() {};

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, GlobalParam.ModId);

    public static final RegistryObject<EntityType<?>> DRIVE_EIEVUI_EX = ENTITIES.register(
            "example_entity",
            () -> EntityType.Builder.of(EntityDriveEievuiEX::new, MobCategory.CREATURE).build()
        );

    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }

}
