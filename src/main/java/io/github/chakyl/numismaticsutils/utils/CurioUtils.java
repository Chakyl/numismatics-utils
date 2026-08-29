package io.github.chakyl.numismaticsutils.utils;

import dev.ithundxr.createnumismatics.Numismatics;
import dev.ithundxr.createnumismatics.content.backend.BankAccount;
import dev.ithundxr.createnumismatics.content.backend.ReasonHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static dev.ithundxr.createnumismatics.registry.NumismaticsDataComponents.CARD_ACCOUNT_ID;

public class CurioUtils {

    public static UUID getCardCurio(Player player) {
        Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(player);
        AtomicReference<UUID> cardUUID = new AtomicReference<>(player.getUUID());
        curios.ifPresent(curiosInventory -> {
            if (!curiosInventory.findCurios("card").isEmpty()) {
                ItemStack card = curiosInventory.findCurios("card").get(0).stack();
                if (!card.getComponents().isEmpty() && card.getComponents().has(CARD_ACCOUNT_ID)) {
                    cardUUID.set(card.getComponents().get(CARD_ACCOUNT_ID));
                }
            }
        });
        return cardUUID.get();
    }

    public static BankAccount getPersonalOrCurioAccount(Level level, Player player) {
        if (level.isClientSide) return null;

        BankAccount account = null;
        UUID cardUUID = getCardCurio(player);

        if (cardUUID != null) {
            account = Numismatics.BANK.getAccount(cardUUID);
        }

        if (account == null) {
            account = Numismatics.BANK.getAccount(player);
        }
        return account;
    }

    public static boolean depositIntoPersonalOrCurio(Level level, Player player, int amount) {
        BankAccount account = getPersonalOrCurioAccount(level, player);
        if (account == null) return false;
        if (account.isAuthorized(player)) {
            account.deposit(amount);
            return true;
        } else {
            return false;
        }
    }

    public static boolean deductFromPersonalOrCurio(Level level, Player player, int amount) {
        BankAccount account = getPersonalOrCurioAccount(level, player);
        if (account == null) return false;
        if (account.isAuthorized(player)) {
            account.deduct(amount, ReasonHolder.IGNORED);
            return true;
        } else {
            return false;
        }
    }
}
