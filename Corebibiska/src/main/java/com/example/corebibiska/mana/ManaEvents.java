package com.example.corebibiska.mana;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "yourmodid")
public class ManaEvents {
    // Регенерация маны каждую секунду
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            Player player = event.player;
            player.getCapability(ManaProvider.MANA).ifPresent(mana -> {
                if (mana.getMana() < mana.getMaxMana()) {
                    mana.setMana(mana.getMana() + 1); // +1 маны в секунду
                }
            });
        }
    }

    // Сброс маны при смерти
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        event.getEntity().getCapability(ManaProvider.MANA).ifPresent(newMana -> {
            event.getOriginal().getCapability(ManaProvider.MANA).ifPresent(oldMana -> {
                newMana.setMana(oldMana.getMana() / 2); // Сохраняем 50% маны
            });
        });
    }
}