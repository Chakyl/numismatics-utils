package io.github.chakyl.numismaticsutils.registry;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NumismaticsUtils.MODID);

    public static final RegistryObject<CreativeModeTab> NUMISMATICS_UTILS_TAB = TABS.register("numismatics_utils_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemRegistry.BANK_METER.get()))
                    .title(Component.translatable("itemGroup.numismatics_utils"))
                    .displayItems((parameters, output) -> {
                        output.accept(ItemRegistry.BANK_METER.get());
                        output.accept(ItemRegistry.PORTABLE_BANK_TERMINAL.get());
                    }).build());

    public static void register(IEventBus modEventBus) {
        TABS.register(modEventBus);
    }
}