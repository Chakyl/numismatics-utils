package io.github.chakyl.numismaticsutils.registry;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import io.github.chakyl.numismaticsutils.items.BankMeterItem;
import io.github.chakyl.numismaticsutils.items.PortableBankTerminalItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NumismaticsUtils.MODID);

    public static final DeferredItem<BankMeterItem> BANK_METER = ITEMS.register("bank_meter", () -> new BankMeterItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<PortableBankTerminalItem> PORTABLE_BANK_TERMINAL = ITEMS.register("portable_bank_terminal", () -> new PortableBankTerminalItem(new Item.Properties().stacksTo(1)));
}
