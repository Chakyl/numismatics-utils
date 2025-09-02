package io.github.chakyl.numismaticsutils.client;

import io.github.chakyl.numismaticsutils.config.DefaultClientSettings;
import io.github.chakyl.numismaticsutils.config.NumismaticsConfigClient;
import io.github.chakyl.numismaticsutils.utils.OverlayUtils;
import io.github.chakyl.numismaticsutils.utils.StringUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class NumismaticsUtilsOverlay implements IGuiOverlay {
    public static NumismaticsUtilsOverlay HUD_INSTANCE;

    public static void init() {
        HUD_INSTANCE = new NumismaticsUtilsOverlay();
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!OverlayUtils.shouldRender(minecraft)) return;
        LazyOptional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(minecraft.player);
        curios.ifPresent(curiosInventory -> {
            ItemStack meter = curiosInventory.findCurios("bank_meter").get(0).stack();

            if (meter != null && meter.getTag() != null) {
                int x = NumismaticsConfigClient.getHudX();
                int y = NumismaticsConfigClient.getHudY();
                double scale = NumismaticsConfigClient.getHudScale();

                Component value = Component.literal(StringUtils.formatBalance(meter.getTag().getInt("balance"))).withStyle(ChatFormatting.GOLD);
                guiGraphics.pose().pushPose();
                guiGraphics.pose().scale((float) scale, (float) scale, 1F);
                guiGraphics.drawString(minecraft.font, Component.translatable("gui.numismatics_utils.bank_meter",value), x, y, 0xffffff);
                guiGraphics.pose().popPose();
            }
        });
    }
}