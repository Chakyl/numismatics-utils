package io.github.chakyl.numismaticsutils.registry;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeTab {
    public static final net.neoforged.neoforge.registries.DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NumismaticsUtils.MODID);
    
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> numismaticsutils_TAB = TABS.register("numismaticsutils_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.numismaticsutils"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ItemRegistry.BANK_METER.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.BANK_METER.get());
                output.accept(ItemRegistry.PORTABLE_BANK_TERMINAL.get());
            }).build());

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }
}