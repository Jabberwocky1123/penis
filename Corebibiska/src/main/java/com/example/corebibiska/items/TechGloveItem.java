package com.example.corebibiska.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public class TechGloveItem extends Item {
    public TechGloveItem() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            context.getPlayer().displayClientMessage(
                    Component.literal("Портативный крафт (GUI в разработке)"), true);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}