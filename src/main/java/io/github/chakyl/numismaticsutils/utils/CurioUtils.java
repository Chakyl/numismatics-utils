package io.github.chakyl.numismaticsutils.utils;

import dev.ithundxr.createnumismatics.Numismatics;
import dev.ithundxr.createnumismatics.content.backend.BankAccount;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CurioUtils {

    /**
     * Reads the AccountID off the card stack. Numismatics historically stored this in NBT under
     * "AccountID"; on 1.21 stack-NBT lives inside the {@link DataComponents#CUSTOM_DATA custom_data}
     * component. If a future Numismatics build promotes AccountID to a typed component, swap this
     * accessor for the official one.
     */
    public static UUID getCardAccountId(ItemStack card) {
        if (card == null || card.isEmpty()) return null;
        CustomData data = card.get(DataComponents.CUSTOM_DATA);
        if (data == null) return null;
        CompoundTag tag = data.copyTag();
        if (tag.hasUUID("AccountID")) return tag.getUUID("AccountID");
        return null;
    }

    /**
     * Returns the AccountID stored on the player's card curio, or the player's own UUID
     * when no card is equipped.
     */
    public static UUID getCardCurio(Player player) {
        UUID fallback = player.getUUID();
        Optional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(player);
        if (curios.isEmpty()) return fallback;
        List<top.theillusivec4.curios.api.SlotResult> results = curios.get().findCurios("card");
        if (results.isEmpty()) return fallback;
        UUID uuid = getCardAccountId(results.get(0).stack());
        return uuid != null ? uuid : fallback;
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
        }
        return false;
    }

    public static boolean deductFromPersonalOrCurio(Level level, Player player, int amount) {
        BankAccount account = getPersonalOrCurioAccount(level, player);
        if (account == null) return false;
        if (account.isAuthorized(player)) {
            account.deduct(amount);
            return true;
        }
        return false;
    }
}
