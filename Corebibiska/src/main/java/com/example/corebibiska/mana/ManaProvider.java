package com.example.corebibiska.mana;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ManaProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
    public static final Capability<ManaProvider> MANA = CapabilityManager.get(new CapabilityToken<>() {});

    private int mana = 0;
    private final LazyOptional<ManaProvider> holder = LazyOptional.of(() -> this);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return MANA.orEmpty(cap, holder);
    }

    // Геттеры/сеттеры
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = Math.max(0, Math.min(mana, getMaxMana()));
    }

    public void consumeMana(int amount) {
        setMana(mana - amount);
    }

    public int getMaxMana() {
        return 250; // Пример для воина (можно привязать к классу)
    }

    // Сериализация (для сохранения)
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("mana", mana);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        mana = tag.getInt("mana");
    }
}