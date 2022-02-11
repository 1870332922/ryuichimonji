package mod.ryuichimonji.item;

import mod.ryuichimonji.effect.ModEffectRegister;
import mod.ryuichimonji.utils.GlobalParam;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class Tiger extends Item {

    public Tiger(Properties prop) {
        super(prop);
    }

    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
        if (level.isClientSide) {
            if (player.isCreative()) itemFunction(player);
            else if (player.experienceLevel>0) payPriceThenUse(player);
        }
        return super.use(level, player, interactionHand);
    }

    private void itemFunction(Player player) {
        player.playSound(SoundEvents.PLAYER_ATTACK_NODAMAGE,1.0f,0.4F / (GlobalParam.random.nextFloat() * 0.4F + 0.8F));
        player.addEffect(new MobEffectInstance(ModEffectRegister.effectUnseenAidByTiger,15));
        for(int i=0 ; i<2 ; i++) {
            int roll = i==1 ? 45 : 90 ;
            // TODO: Hurt entity
            // Code in 1.12.2:
            /*
                EntityDriveEievuiEX entityDrive = new EntityDriveEievuiEX(world, player,4.0F , false, roll);
                entityDrive.setInitialSpeed(i);
                entityDrive.setLifeTime(10);
                entityDrive.setColor(0xF8F400);
                world.spawnEntity(entityDrive);
            */


        }
    }

    private void payPriceThenUse(Player player){
        player.experienceLevel-=1;
        player.setHealth(player.getMaxHealth());
        itemFunction(player);
    }

}
