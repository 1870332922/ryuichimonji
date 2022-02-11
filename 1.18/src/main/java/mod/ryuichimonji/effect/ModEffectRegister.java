package mod.ryuichimonji.effect;

import mod.ryuichimonji.utils.GlobalParam;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffectRegister {
	// effects set
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, GlobalParam.ModId);

	// register
	public static void register(IEventBus eventBus){
		EFFECTS.register(eventBus);
	}

	// All the effects
	public static final CustomEffect effectUnseenAid = new CustomEffect(MobEffectCategory.BENEFICIAL,2002002001);
	public static final RegistryObject<MobEffect> UNSEEN_AID = EFFECTS.register(
			"unseen_aid_by_tiger"
			, () -> effectUnseenAid
	);

	public static final CustomEffect effectUnseenAidByFox = new CustomEffect(MobEffectCategory.BENEFICIAL,2002002001);
	public static final RegistryObject<MobEffect> UNSEEN_AID_BY_FOX = EFFECTS.register(
			"unseen_aid_by_tiger"
			, () -> effectUnseenAidByFox
	);

	public static final CustomEffect effectUnseenAidByLoong = new CustomEffect(MobEffectCategory.BENEFICIAL,2002002001);
	public static final RegistryObject<MobEffect> UNSEEN_AID_BY_LOONG = EFFECTS.register(
			"unseen_aid_by_loong"
			, () -> effectUnseenAidByLoong
	);

	public static final CustomEffect effectUnseenAidByTiger = new CustomEffect(MobEffectCategory.BENEFICIAL,2002002001);
	public static final RegistryObject<MobEffect> UNSEEN_AID_BY_TIGER = EFFECTS.register(
			"unseen_aid_by_tiger"
			, () -> effectUnseenAidByTiger
	);

}