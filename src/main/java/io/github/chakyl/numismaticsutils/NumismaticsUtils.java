package io.github.chakyl.numismaticsutils;

import com.mojang.logging.LogUtils;
import io.github.chakyl.numismaticsutils.config.NumismaticsConfigClient;
import io.github.chakyl.numismaticsutils.registry.CreativeTab;
import io.github.chakyl.numismaticsutils.registry.ItemRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NumismaticsUtils.MODID)
public class NumismaticsUtils {
    public static final String MODID = "numismatics_utils";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NumismaticsUtils() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(modEventBus);
        CreativeTab.TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, NumismaticsConfigClient.CLIENT_SPEC, "numismatics-utils-client.toml");

    }
}