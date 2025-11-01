package io.github.chakyl.numismaticsutils.utils;

import dev.ithundxr.createnumismatics.Numismatics;
import dev.ithundxr.createnumismatics.content.backend.BankAccount;
import dev.ithundxr.createnumismatics.util.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

import static io.github.chakyl.numismaticsutils.utils.CurioUtils.getCardCurio;

public class TerminalUtils {

    public static boolean openTerminal(Level level, Player player) {
        if (level.isClientSide) return true;

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
            return true;
        } else {
            return false;
        }
    }
}
