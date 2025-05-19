package com.example.corebibiska.items;

import com.example.corebibiska.mana.ManaProvider;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;

public class ShotgunSwordItem extends SwordItem {
    public ShotgunSwordItem() {
        super(Tiers.DIAMOND, 3, -2.4f,
                new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            player.getCapability(ManaProvider.MANA).ifPresent(mana -> {
                if (mana.getMana() >= 10) {
                    mana.consumeMana(10);
                    shootShotgun(player);
                    player.getCooldowns().addCooldown(this, 20);
                }
            });
        }
        return InteractionResultHolder.success(stack);
    }

    private void shootShotgun(Player player) {
        for (int i = 0; i < 5; i++) {
            AbstractArrow projectile = new Arrow(player.level(), player);
            projectile.shootFromRotation(player,
                    player.getXRot() + randomSpread(),
                    player.getYRot() + randomSpread(),
                    0, 3.0f, 1.0f);
            player.level().addFreshEntity(projectile);
        }
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0f, 1.5f);
    }

    private float randomSpread() {
        return (float) (Math.random() * 10 - 5);
    }
}