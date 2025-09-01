package io.github.chakyl.numismaticsutils.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.DeathScreen;

public class OverlayUtils {
    public static Boolean shouldRender(Minecraft minecraft) {
        if (minecraft.player == null) return false;
        return (minecraft.screen == null || minecraft.screen instanceof ChatScreen || minecraft.screen instanceof DeathScreen) && !minecraft.options.renderDebug && !minecraft.options.hideGui && !minecraft.player.isScoping();
    }
}
