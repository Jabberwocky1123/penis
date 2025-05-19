package com.example.corebibiska.items;

import com.example.corebibiska.mana.ManaProvider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class DoubleCrossbowItem extends ProjectileWeaponItem {
    public DoubleCrossbowItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            player.getCapability(ManaProvider.MANA).ifPresent(mana -> {
                if (mana.getMana() >= 1) { // Изменил на целое число
                    mana.consumeMana(1);
                    AbstractArrow arrow = new Arrow(level, player);
                    arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 3.0f, 1.0f);
                    level.addFreshEntity(arrow);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 0.8f, 1.2f);
                }
            });
        }
        return InteractionResultHolder.success(stack);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return stack -> false; // Не используем стандартные стрелы
    }

    @Override
    public int getDefaultProjectileRange() {
        return 15;
    }
}
