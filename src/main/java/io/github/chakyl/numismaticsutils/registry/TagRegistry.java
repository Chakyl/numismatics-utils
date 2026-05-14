package io.github.chakyl.numismaticsutils.registry;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TagRegistry {
    public static final TagKey<Block> ATM_BLOCK =
            BlockTags.create(ResourceLocation.fromNamespaceAndPath(NumismaticsUtils.MODID, "atm_block"));
}
