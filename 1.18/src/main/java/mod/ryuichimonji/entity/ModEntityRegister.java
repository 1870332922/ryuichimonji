package mod.ryuichimonji.entity;

import mod.ryuichimonji.utils.GlobalParam;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, GlobalParam.ModId);



    public static void register(IEventBus eventBus){
        ENTITIES.register(eventBus);
    }

}
