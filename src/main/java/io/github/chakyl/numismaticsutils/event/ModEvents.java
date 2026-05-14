package io.github.chakyl.numismaticsutils.event;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import io.github.chakyl.numismaticsutils.curio.BankMeterCurio;
import io.github.chakyl.numismaticsutils.registry.ItemRegistry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import top.theillusivec4.curios.api.CuriosCapability;

/**
 * Mod-bus events. Registers item capabilities (Curios) so the Bank Meter is
 * recognised as a curio in the "bank_meter" slot; without this, Curios 9.x on
 * NeoForge 1.21.1 will not call curioTick or expose the item to findCurios().
 */
@EventBusSubscriber(modid = NumismaticsUtils.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
                CuriosCapability.ITEM,
                (stack, ctx) -> new BankMeterCurio(stack),
                ItemRegistry.BANK_METER.get()
        );
    }
}
