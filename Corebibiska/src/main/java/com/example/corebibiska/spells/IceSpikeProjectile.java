package com.example.corebibiska.spells;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class IceSpikeProjectile extends Snowball {
    public IceSpikeProjectile(Level level, LivingEntity shooter) {
        super(level, shooter);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (result.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) result;
            if (entityHit.getEntity() instanceof LivingEntity target) {
                target.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        60, // 3 секунды (20 тиков = 1 секунда)
                        2
                ));
            }
        }

        // Эффект частиц
        this.level().addParticle(ParticleTypes.SNOWFLAKE,
                this.getX(), this.getY(), this.getZ(), 0, 0, 0);
    }
}
