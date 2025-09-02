package io.github.chakyl.numismaticsutils.event;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import io.github.chakyl.numismaticsutils.client.NumismaticsUtilsOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {


    @Mod.EventBusSubscriber(modid = NumismaticsUtils.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            NumismaticsUtilsOverlay.init();
            event.registerAbove(VanillaGuiOverlay.FROSTBITE.id(), "numismatics_utils", NumismaticsUtilsOverlay.HUD_INSTANCE);
        }

    }
}