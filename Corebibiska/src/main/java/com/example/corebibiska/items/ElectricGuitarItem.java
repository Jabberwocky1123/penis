package com.example.corebibiska.items;

import com.example.corebibiska.effect.ModEffects;
import com.example.corebibiska.init.ModSounds;
import com.example.corebibiska.mana.ManaProvider;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ElectricGuitarItem extends Item {
    private static final int MANA_COST = 1; // Трата за тик
    private static final int TICKS_BETWEEN_COST = 60; // 3 секунды (20 ticks = 1 сек)
    private static final int BUFF_DURATION = 100; // 5 секунд

    public ElectricGuitarItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        if (!level.isClientSide) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.GUITAR_PLAY.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
        if (entity instanceof Player player && remainingTicks % TICKS_BETWEEN_COST == 0) {
            player.getCapability(ManaProvider.MANA).ifPresent(mana -> {
                if (mana.getMana() >= MANA_COST) {
                    mana.consumeMana(MANA_COST);
                    applyBuffs(player);
                } else {
                    player.stopUsingItem();
                }
            });
        }
    }

    private void applyBuffs(Player player) {
        // Эффекты для команды
        player.level().getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(20))
                .forEach(teammate -> {
                    teammate.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, BUFF_DURATION, 1));
                    teammate.addEffect(new MobEffectInstance(MobEffects.REGENERATION, BUFF_DURATION, 1));
                    teammate.addEffect(new MobEffectInstance(ModEffects.MANA_REGENERATION.get(), BUFF_DURATION, 1));
                    teammate.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, BUFF_DURATION, 1));
                });

        // Усиленные эффекты для самого барда
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, BUFF_DURATION, 3));
        player.addEffect(new MobEffectInstance(ModEffects.MANA_REGENERATION.get(), BUFF_DURATION, 0));
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000; // Максимальное время использования
    }
}