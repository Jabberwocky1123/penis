package com.example.corebibiska.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "corebibiska");

    public static final RegistryObject<SoundEvent> GUITAR_PLAY = SOUNDS.register("pigstep.ogg",
            () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("corebibiska", "pigstep.ogg")));

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}