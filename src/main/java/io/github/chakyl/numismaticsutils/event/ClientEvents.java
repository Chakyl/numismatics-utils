package io.github.chakyl.numismaticsutils.event;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import io.github.chakyl.numismaticsutils.client.NumismaticsUtilsOverlay;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class ClientEvents {


    @EventBusSubscriber(modid = NumismaticsUtils.MODID, value = Dist.CLIENT)
    public static class ClientModBusEvents {

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
            NumismaticsUtilsOverlay.init();
            event.registerAbove(VanillaGuiLayers.EFFECTS, ResourceLocation.fromNamespaceAndPath(NumismaticsUtils.MODID, "bank_balance"), NumismaticsUtilsOverlay.HUD_INSTANCE);
        }

    }
}