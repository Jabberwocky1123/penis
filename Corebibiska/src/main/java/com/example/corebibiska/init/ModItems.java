package com.example.corebibiska.init;
import com.example.corebibiska.items.*;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "corebibiska");

    public static final RegistryObject<Item> ELECTRIC_GUITAR = ITEMS.register("electric_guitar",
            () -> new ElectricGuitarItem());
    public static final RegistryObject<Item> DOUBLE_CROSSBOW = ITEMS.register("double_crossbow",
            () -> new DoubleCrossbowItem());
    public static final RegistryObject<Item> SHOTGUN_SWORD = ITEMS.register("shotgun_sword",
            () -> new ShotgunSwordItem());
    public static final RegistryObject<Item> MAGE_STAFF = ITEMS.register("mage_staff",
            () -> new MageStaffItem());
    public static final RegistryObject<Item> TECH_GLOVE = ITEMS.register("tech_glove",
            () -> new TechGloveItem());
    public static final RegistryObject<Item> SPELL_CODEX = ITEMS.register("spell_codex",
            () -> new SpellCodexItem());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}