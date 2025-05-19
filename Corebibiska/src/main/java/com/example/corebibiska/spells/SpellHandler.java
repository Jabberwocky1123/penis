package com.example.corebibiska.spells;

import com.example.corebibiska.mana.ManaProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SpellHandler {
    public static void castSpell(Player player, String spellId) {
        if (player.level().isClientSide) return;

        player.getCapability(ManaProvider.MANA).ifPresent(mana -> {
            switch(spellId) {
                case "fire_ball" -> {
                    if (mana.getMana() >= 40) {
                        mana.consumeMana(40);
                        castFireball(player);
                    }
                }
                case "teleport" -> {
                    if (mana.getMana() >= 50) {
                        mana.consumeMana(50);
                        castTeleport(player);
                    }
                }
                case "ice_spikes" -> {
                    if (mana.getMana() >= 30) {
                        mana.consumeMana(30);
                        castIceSpikes(player);
                    }
                }
            }
        });
    }

    // Заглушка для огненного шара (используем SmallFireball)
    private static void castFireball(Player player) {
        SmallFireball fireball = new SmallFireball(
                player.level(),
                player,
                player.getLookAngle().x,
                player.getLookAngle().y,
                player.getLookAngle().z
        );
        fireball.setPos(player.getX(), player.getEyeY(), player.getZ());
        fireball.setDeltaMovement(fireball.getDeltaMovement().scale(2.0));
        player.level().addFreshEntity(fireball);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    // Телепорт без частиц
    private static void castTeleport(Player player) {
        Vec3 look = player.getLookAngle();
        Vec3 newPos = player.position().add(look.x * 10, look.y * 10, look.z * 10);

        // Простая проверка земли
        while (!player.level().isEmptyBlock(BlockPos.containing(newPos))) {
            newPos = newPos.add(0, 1, 0);
            if (newPos.y > player.level().getMaxBuildHeight()) break;
        }

        player.teleportTo(newPos.x, newPos.y, newPos.z);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
    }

    // Заглушка для льда (используем Snowball)
    private static void castIceSpikes(Player player) {
        for (int i = 0; i < 3; i++) {
            Snowball snowball = new Snowball(player.level(), player);
            snowball.shootFromRotation(player,
                    player.getXRot() + randomSpread(),
                    player.getYRot() + randomSpread(),
                    0.0f, 1.2f, 1.0f);
            player.level().addFreshEntity(snowball);
        }
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 1.0f, 0.5f);
    }

    private static float randomSpread() {
        return (float) (Math.random() * 10 - 5);
    }
}