package io.github.chakyl.numismaticsutils.items;

import io.github.chakyl.numismaticsutils.utils.TerminalUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class PortableBankTerminalItem extends Item {
    public PortableBankTerminalItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        if (TerminalUtils.openTerminal(level, player)) return InteractionResultHolder.success(handStack);
        return InteractionResultHolder.fail(handStack);
    }

}