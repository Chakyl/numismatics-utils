package io.github.chakyl.numismaticsutils.registry;


import com.mojang.serialization.Codec;
import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataComponentRegistry {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, NumismaticsUtils.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> STORED_METER_BALANCE = DATA_COMPONENT_TYPES.registerComponentType("balance", builder -> builder.persistent(Codec.INT));
}
