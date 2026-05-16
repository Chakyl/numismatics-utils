package io.github.chakyl.numismaticsutils.items;

import dev.ithundxr.createnumismatics.Numismatics;
import dev.ithundxr.createnumismatics.content.backend.BankAccount;
import io.github.chakyl.numismaticsutils.utils.StringUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static io.github.chakyl.numismaticsutils.registry.DataComponentRegistry.STORED_METER_BALANCE;
import static io.github.chakyl.numismaticsutils.utils.CurioUtils.getPersonalOrCurioAccount;


public class BankMeterItem extends Item implements ICurioItem {
    public BankMeterItem(Properties props) {
        super(props);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext tooltipContext, List<Component> pList, TooltipFlag pFlag) {
        if (pStack.getComponents().isEmpty()) return;
        pList.add(Component.translatable("info.numismaticsutils.balance", StringUtils.formatBalance(pStack.getComponents().getOrDefault(STORED_METER_BALANCE.get(), 0))).withStyle(ChatFormatting.GOLD));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.dayTime() % 100 != 0) return;
        if (!(slotContext.entity() instanceof ServerPlayer)) return;
        boolean updateTag = false;
        AtomicReference<UUID> uuid = new AtomicReference<>(slotContext.entity().getUUID());
        BankAccount account = getPersonalOrCurioAccount(level, (ServerPlayer) slotContext.entity());
        if (account == null)
            account = Numismatics.BANK.getOrCreateAccount(slotContext.entity().getUUID(), BankAccount.Type.PLAYER);

        if (!account.isAuthorized(uuid.get())) return;
        int balance = account.getBalance();
        if (!stack.getComponents().isEmpty() && stack.getComponents().has(STORED_METER_BALANCE.get())) {
            int currentBalance = stack.getComponents().getOrDefault(STORED_METER_BALANCE.get(), 0);
            if (currentBalance != balance) updateTag = true;
        } else {
            updateTag = true;
        }
        if (updateTag) stack.set(STORED_METER_BALANCE, balance);
    }


}