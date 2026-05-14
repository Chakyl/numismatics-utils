package io.github.chakyl.numismaticsutils.client;

import io.github.chakyl.numismaticsutils.config.NumismaticsConfigClient;
import io.github.chakyl.numismaticsutils.registry.DataComponentRegistry;
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

public class NumismaticsUtilsOverlay implements LayeredDraw.Layer {
    public static final NumismaticsUtilsOverlay HUD_INSTANCE = new NumismaticsUtilsOverlay();

    /** Pixels between the top of the health bar (at screenH - 39) and the bottom of our text. */
    private static final int GAP_ABOVE_HEALTH = 2;

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!OverlayUtils.shouldRender(minecraft)) return;

        Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(minecraft.player);
        if (curios.isEmpty()) return;
        List<SlotResult> slots = curios.get().findCurios("bank_meter");
        if (slots.isEmpty()) return;

        ItemStack meter = slots.get(0).stack();
        Integer balance = meter.get(DataComponentRegistry.BALANCE.get());
        if (balance == null) return;

        float scale = (float) NumismaticsConfigClient.getHudScale();
        Component value = Component.literal(StringUtils.formatCog(balance)).withStyle(ChatFormatting.GOLD);
        Component text = Component.translatable("gui.numismatics_utils.bank_meter", value);

        int textWidth = minecraft.font.width(text);
        int fontHeight = minecraft.font.lineHeight;
        int screenW = guiGraphics.guiWidth();
        int screenH = guiGraphics.guiHeight();

        // Anchor: just above the top edge of the heart bar (vanilla puts hearts at screenH - 39).
        int anchorScreenX = screenW / 2;
        int anchorScreenY = screenH - 39 - fontHeight - GAP_ABOVE_HEALTH;

        // Convert screen-space target into the scaled coord space so the rendered text is
        // centred horizontally regardless of the user's hud_scale.
        float drawX = anchorScreenX / scale - textWidth / 2f + NumismaticsConfigClient.getHudX();
        float drawY = anchorScreenY / scale + NumismaticsConfigClient.getHudY();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1F);
        guiGraphics.drawString(minecraft.font, text, (int) drawX, (int) drawY, 0xffffff);
        guiGraphics.pose().popPose();
    }
}
