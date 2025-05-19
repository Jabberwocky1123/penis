package com.example.corebibiska.effect;

import com.example.corebibiska.mana.ManaProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ManaRegenerationEffect extends MobEffect {
    public ManaRegenerationEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00AAFF);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            player.getCapability(ManaProvider.MANA).ifPresent(mana -> {
                int amount = amplifier + 1;
                mana.setMana(Math.min(mana.getMana() + amount, mana.getMaxMana()));
            });
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0; // Каждую секунду
    }
}
