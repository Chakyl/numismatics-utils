package io.github.chakyl.numismaticsutils.registry;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import io.github.chakyl.numismaticsutils.items.BankMeterItem;
import io.github.chakyl.numismaticsutils.items.PortableBankTerminalItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NumismaticsUtils.MODID);

    public static final RegistryObject<BankMeterItem> BANK_METER = ITEMS.register("bank_meter", () -> new BankMeterItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<PortableBankTerminalItem> PORTABLE_BANK_TERMINAL = ITEMS.register("portable_bank_terminal", () -> new PortableBankTerminalItem(new Item.Properties().stacksTo(1)));

}
