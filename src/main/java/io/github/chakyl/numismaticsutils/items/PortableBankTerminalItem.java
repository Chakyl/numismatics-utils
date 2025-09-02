package io.github.chakyl.numismaticsutils.items;

import dev.ithundxr.createnumismatics.Numismatics;
import dev.ithundxr.createnumismatics.content.backend.BankAccount;
import dev.ithundxr.createnumismatics.util.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

import static io.github.chakyl.numismaticsutils.utils.CurioUtils.getCardCurio;


public class PortableBankTerminalItem extends Item {
    public static final String BALANCE = "balance";

    public PortableBankTerminalItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack handStack = player.getItemInHand(hand);
        if (level.isClientSide)
            return InteractionResultHolder.success(handStack);


        BankAccount account = null;
        UUID cardUUID = getCardCurio(player);

        if (cardUUID != null) {
            account = Numismatics.BANK.getAccount(cardUUID);
        }

        if (account == null) {
            account = Numismatics.BANK.getAccount(player);
        }

        if (account.isAuthorized(player)) {
            Utils.openScreen((ServerPlayer) player, account, account::sendToMenu);
            return InteractionResultHolder.success(handStack);
        } else {
            return InteractionResultHolder.fail(handStack);
        }
    }


}