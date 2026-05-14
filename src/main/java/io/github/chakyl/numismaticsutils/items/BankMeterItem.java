package io.github.chakyl.numismaticsutils.items;

import io.github.chakyl.numismaticsutils.registry.DataComponentRegistry;
import io.github.chakyl.numismaticsutils.utils.StringUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

/**
 * Bank Meter item. Curio behaviour (ticking the balance into the BALANCE data
 * component) is provided by {@link io.github.chakyl.numismaticsutils.curio.BankMeterCurio},
 * registered against the {@code CuriosCapability.ITEM} item capability in
 * {@link io.github.chakyl.numismaticsutils.event.ModEvents}.
 */
public class BankMeterItem extends Item {

    public BankMeterItem(Properties props) {
        super(props);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        Integer balance = stack.get(DataComponentRegistry.BALANCE.get());
        if (balance == null) return;
        tooltip.add(Component.translatable("info.numismatics_utils.balance", StringUtils.formatBalance(balance))
                .withStyle(ChatFormatting.GOLD));
    }
}
