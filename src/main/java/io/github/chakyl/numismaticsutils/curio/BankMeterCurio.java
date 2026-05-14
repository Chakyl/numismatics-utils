package io.github.chakyl.numismaticsutils.curio;

import dev.ithundxr.createnumismatics.Numismatics;
import dev.ithundxr.createnumismatics.content.backend.BankAccount;
import io.github.chakyl.numismaticsutils.registry.DataComponentRegistry;
import io.github.chakyl.numismaticsutils.utils.CurioUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.UUID;

/**
 * Curio adapter for the Bank Meter. Curios 9.x on NeoForge 1.21.1 no longer
 * picks up {@code ICurioItem} directly off the {@link net.minecraft.world.item.Item}
 * class — items must expose {@link ICurio} via the {@code CuriosCapability.ITEM}
 * item capability. Registration happens in
 * {@link io.github.chakyl.numismaticsutils.event.ModEvents#registerCapabilities}.
 */
public class BankMeterCurio implements ICurio {
    private final ItemStack stack;

    public BankMeterCurio(ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public ItemStack getStack() {
        return stack;
    }

    @Override
    public void curioTick(SlotContext slotContext) {
        if (!(slotContext.entity() instanceof ServerPlayer player)) return;
        // Refresh the displayed balance once per second. tickCount is monotonic
        // regardless of doDaylightCycle / fixed-time worlds, unlike Level#dayTime.
        if (player.tickCount % 20 != 0) return;

        UUID accountId = CurioUtils.getCardCurio(player);

        BankAccount account = Numismatics.BANK.getAccount(accountId);
        if (account == null) {
            account = Numismatics.BANK.getOrCreateAccount(player.getUUID(), BankAccount.Type.PLAYER);
        }

        if (!account.isAuthorized(accountId)) return;

        int balance = account.getBalance();
        Integer current = stack.get(DataComponentRegistry.BALANCE.get());
        if (current == null || current != balance) {
            stack.set(DataComponentRegistry.BALANCE.get(), balance);
        }
    }
}
