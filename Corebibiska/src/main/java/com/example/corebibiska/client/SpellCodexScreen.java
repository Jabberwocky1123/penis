package com.example.corebibiska.client;

import com.example.corebibiska.spells.SpellHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import java.util.Map;

import static net.minecraft.core.BlockPos.getX;
import static net.minecraft.core.BlockPos.getY;


public class SpellCodexScreen extends Screen {
    private static final int ICON_SIZE = 40;
    private static final int PADDING = 20;
    private String selectedSpell = "";

    // Ресурсы иконок
    private static final Map<String, ResourceLocation> SPELL_ICONS = Map.of(
            "fire_ball", new ResourceLocation("corebibiska", "textures/gui/spells/fire_ball.png"),
            "ice_spikes", new ResourceLocation("corebibiska", "textures/gui/spells/ice_spikes.png"),
            "teleport", new ResourceLocation("corebibiska", "textures/gui/spells/teleport.png")
    );

    public SpellCodexScreen() {
        super(Component.literal("Выбор заклинания"));
    }

    @Override
    protected void init() {
        int centerX = width / 2 - (ICON_SIZE + PADDING);
        int centerY = height / 2 - (ICON_SIZE + PADDING);

        // Правильное создание кнопок через Builder
        this.addRenderableWidget(
                Button.builder(Component.empty(), button -> selectSpell("fire_ball"))
                        .pos(centerX, centerY)
                        .size(ICON_SIZE, ICON_SIZE)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(Component.empty(), button -> selectSpell("teleport"))
                        .pos(centerX + ICON_SIZE + PADDING, centerY)
                        .size(ICON_SIZE, ICON_SIZE)
                        .build()
        );

        this.addRenderableWidget(
                Button.builder(Component.empty(), button -> selectSpell("ice_spikes"))
                        .pos(centerX, centerY + ICON_SIZE + PADDING)
                        .size(ICON_SIZE, ICON_SIZE)
                        .build()
        );

        // Кнопка подтверждения
        this.addRenderableWidget(
                Button.builder(Component.literal("Подтвердить"), button -> confirmSpell())
                        .pos(width / 2 - 50, centerY + ICON_SIZE * 2 + PADDING)
                        .size(100, 20)
                        .build()
        );
    }

    private void selectSpell(String spellId) {
        this.selectedSpell = spellId;
        Minecraft.getInstance().getSoundManager().play(
                SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }

    private void confirmSpell() {
        if (!selectedSpell.isEmpty()) {
            SpellHandler.castSpell(Minecraft.getInstance().player, selectedSpell);
            onClose();
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        renderBackground(gui);

        // Рисуем иконки поверх кнопок
        int centerX = width / 2 - (ICON_SIZE + PADDING);
        int centerY = height / 2 - (ICON_SIZE + PADDING);

        // Fire ball
        gui.blit(SPELL_ICONS.get("fire_ball"),
                centerX, centerY,
                0, 0,
                ICON_SIZE, ICON_SIZE,
                ICON_SIZE, ICON_SIZE);

        // Teleport
        gui.blit(SPELL_ICONS.get("teleport"),
                centerX + ICON_SIZE + PADDING, centerY,
                0, 0,
                ICON_SIZE, ICON_SIZE,
                ICON_SIZE, ICON_SIZE);

        // Ice spikes
        gui.blit(SPELL_ICONS.get("ice_spikes"),
                centerX, centerY + ICON_SIZE + PADDING,
                0, 0,
                ICON_SIZE, ICON_SIZE,
                ICON_SIZE, ICON_SIZE);

        // Выделение выбранного заклинания
        if (!selectedSpell.isEmpty()) {
            int x = selectedSpell.equals("teleport") ?
                    centerX + ICON_SIZE + PADDING : centerX;
            int y = selectedSpell.equals("ice_spikes") ?
                    centerY + ICON_SIZE + PADDING : centerY;

            gui.renderOutline(x, y, ICON_SIZE, ICON_SIZE, 0xFF00FF00);
        }

        super.render(gui, mouseX, mouseY, partialTick);
    }
}