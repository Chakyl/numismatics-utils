package io.github.chakyl.numismaticsutils.registry;

import com.mojang.serialization.Codec;
import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Replaces the legacy {@code stack.getTag().getInt("balance")} NBT storage with a typed
 * data component so the Bank Meter survives the 1.20.5+ component migration.
 */
public class DataComponentRegistry {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, NumismaticsUtils.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> BALANCE =
            DATA_COMPONENTS.registerComponentType("balance", builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT));
}
