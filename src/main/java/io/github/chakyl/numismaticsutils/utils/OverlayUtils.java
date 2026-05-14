package io.github.chakyl.numismaticsutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;

public class OverlayUtils {
    public static boolean shouldRender(Minecraft minecraft) {
        if (minecraft.player == null) return false;
        boolean acceptableScreen = minecraft.screen == null
                || minecraft.screen instanceof ChatScreen
                || minecraft.screen instanceof DeathScreen;
        return acceptableScreen
                && !minecraft.getDebugOverlay().showDebugScreen()
                && !minecraft.options.hideGui
                && !minecraft.player.isScoping();
    }
}
