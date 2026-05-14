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
import org.slf4j.Logger;

@Mod(NumismaticsUtils.MODID)
public class NumismaticsUtils {
    public static final String MODID = "numismatics_utils";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NumismaticsUtils(IEventBus modEventBus, ModContainer container) {
        DataComponentRegistry.DATA_COMPONENTS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
        CreativeTab.TABS.register(modEventBus);
        container.registerConfig(ModConfig.Type.CLIENT, NumismaticsConfigClient.CLIENT_SPEC, "numismatics-utils-client.toml");
    }
}
