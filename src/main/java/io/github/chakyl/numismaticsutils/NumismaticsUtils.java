package io.github.chakyl.numismaticsutils;

import com.mojang.logging.LogUtils;
import io.github.chakyl.numismaticsutils.config.NumismaticsConfigClient;
import io.github.chakyl.numismaticsutils.registry.CreativeTab;
import io.github.chakyl.numismaticsutils.registry.DataComponentRegistry;
import io.github.chakyl.numismaticsutils.registry.ItemRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(NumismaticsUtils.MODID)
public class NumismaticsUtils {
    public static final String MODID = "numismaticsutils";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NumismaticsUtils(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        ItemRegistry.ITEMS.register(modEventBus);
        CreativeTab.TABS.register(modEventBus);
        DataComponentRegistry.DATA_COMPONENT_TYPES.register(modEventBus);
//        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.CLIENT, NumismaticsConfigClient.CLIENT_SPEC, "numismaticsutils-client.toml");
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }
}