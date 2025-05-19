package com.example.corebibiska.items;

import com.example.corebibiska.mana.ManaProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MageStaffItem extends Item {
    public MageStaffItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown()) {
            // Временная заглушка для GUI
            player.displayClientMessage(Component.literal("Выбор заклинаний (GUI в разработке)"), true);
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }

        player.getCapability(ManaProvider.MANA).ifPresent(mana -> {
            if (mana.getMana() >= 50) {
                mana.consumeMana(50);
                castFireball(player);
            }
        });
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    private void castFireball(Player player) {
        // Логика огненного шара
    }
}