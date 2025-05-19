package com.example.corebibiska;

import com.example.corebibiska.client.ManaHUD;
import com.example.corebibiska.init.ModItems;
import com.example.corebibiska.init.ModSounds;
import com.example.corebibiska.mana.ManaEvents;
import com.example.corebibiska.mana.ManaProvider;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.example.corebibiska.spells.IceSpikeProjectile;

@Mod("corebibiska")
public class YourModMain {
    public YourModMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Регистрация предметов и звуков
        ModItems.register(modEventBus);
        ModSounds.register(modEventBus);

        // Регистрация capability
        modEventBus.addListener(this::registerCapabilities);

        // Регистрация событий
        MinecraftForge.EVENT_BUS.register(new ManaEvents());

        // Клиентская регистрация
        modEventBus.addListener(this::clientSetup);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(ManaProvider.class);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ManaHUD::register);
    }
}