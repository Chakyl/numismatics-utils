package io.github.chakyl.numismaticsutils.registry;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import io.github.chakyl.numismaticsutils.items.BankMeterItem;
import io.github.chakyl.numismaticsutils.items.PortableBankTerminalItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class TagRegistry {
    public static final TagKey<Block> ATM_BLOCK = BlockTags.create(new ResourceLocation(NumismaticsUtils.MODID, "atm_block"));

}
