package io.github.chakyl.numismaticsutils.client;

import io.github.chakyl.numismaticsutils.config.NumismaticsConfigClient;
import io.github.chakyl.numismaticsutils.utils.OverlayUtils;
import io.github.chakyl.numismaticsutils.utils.StringUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;

import static io.github.chakyl.numismaticsutils.registry.DataComponentRegistry.STORED_METER_BALANCE;

public class NumismaticsUtilsOverlay implements LayeredDraw.Layer {
    public static NumismaticsUtilsOverlay HUD_INSTANCE;

    public static void init() {
        HUD_INSTANCE = new NumismaticsUtilsOverlay();
    }

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!OverlayUtils.shouldRender(minecraft)) return;
        Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(minecraft.player);
        curios.ifPresent(curiosInventory -> {
            List<SlotResult> slots = curiosInventory.findCurios("bank_meter");
            if (!slots.isEmpty()) {
                ItemStack meter = slots.get(0).stack();
                if (meter != null && !meter.getComponents().isEmpty() && meter.getComponents().has(STORED_METER_BALANCE.get())) {
                    int x = NumismaticsConfigClient.getHudX();
                    int y = NumismaticsConfigClient.getHudY();
                    double scale = NumismaticsConfigClient.getHudScale();

                    Component value = Component.literal(StringUtils.formatBalance(meter.getComponents().getOrDefault(STORED_METER_BALANCE.get(), 0))).withStyle(ChatFormatting.GOLD);
                    guiGraphics.pose().pushPose();
                    guiGraphics.pose().scale((float) scale, (float) scale, 1F);
                    guiGraphics.drawString(minecraft.font, Component.translatable("gui.numismaticsutils.bank_meter", value), x, y, 0xffffff);
                    guiGraphics.pose().popPose();
                }
            }
        });
    }
}