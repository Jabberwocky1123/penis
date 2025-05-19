package com.example.corebibiska.client;

import com.example.corebibiska.mana.ManaProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ManaHUD {
    private static final ResourceLocation MANA_ICON = new ResourceLocation("corebibiska", "textures/gui/mana_icon.png");
    private static long lastPulseTime;
    private static float pulseScale = 1.0f;

    // Изменяем тип на NamedGuiOverlay
    public static final IGuiOverlay MANA_OVERLAY = new IGuiOverlay() {
        @Override
        public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null) return;

            player.getCapability(ManaProvider.MANA).ifPresent(mana -> {
                int manaValue = mana.getMana();
                int maxMana = mana.getMaxMana();
                float manaPercent = (float) manaValue / maxMana;

                // Позиционирование
                int x = screenWidth - 122;
                int y = screenHeight - 50;

                // Анимация пульсации
                PoseStack pose = guiGraphics.pose();
                pose.pushPose();

                if (manaPercent < 0.2f) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastPulseTime > 200) {
                        pulseScale = 1.1f;
                        lastPulseTime = currentTime;
                    } else {
                        pulseScale = Math.max(1.0f, pulseScale - 0.01f);
                    }
                    pose.scale(pulseScale, pulseScale, 1.0f);
                }

                // Рисуем полосу маны
                guiGraphics.fill(x, y, x + 102, y + 12, 0xFF555555);
                for (int i = 0; i < (int)(100 * manaPercent); i++) {
                    int color = blendColors(0xFF00AAFF, 0xFF0066FF, i / 100f);
                    guiGraphics.fill(x + 1 + i, y + 1, x + 1 + i + 1, y + 11, color);
                }

                // Иконка и текст
                guiGraphics.blit(MANA_ICON, x - 18, y - 2, 0, 0, 16, 16, 16, 16);
                String text = manaValue + "/" + maxMana;
                guiGraphics.drawString(mc.font, text, x + 51 - mc.font.width(text)/2, y + 2, 0xFFFFFF);

                pose.popPose();
            });
        }
    };

    private static int blendColors(int color1, int color2, float ratio) {
        int r = (int) ((color1 >> 16 & 0xFF) * (1 - ratio) + (color2 >> 16 & 0xFF) * ratio);
        int g = (int) ((color1 >> 8 & 0xFF) * (1 - ratio) + (color2 >> 8 & 0xFF) * ratio);
        int b = (int) ((color1 & 0xFF) * (1 - ratio) + (color2 & 0xFF) * ratio);
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    public static void register() {
        // Регистрируем через событие RenderGuiOverlayEvent.Post
        MinecraftForge.EVENT_BUS.addListener(ManaHUD::onRenderGuiOverlay);
    }

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() == VanillaGuiOverlay.HOTBAR.type()) {
            ForgeGui gui = (ForgeGui) Minecraft.getInstance().gui;
            MANA_OVERLAY.render(
                    gui,
                    event.getGuiGraphics(),
                    event.getPartialTick(),
                    event.getWindow().getGuiScaledWidth(),
                    event.getWindow().getGuiScaledHeight()
            );
        }
    }
}