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
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


public class BankMeterItem extends Item implements ICurioItem {
    public static final String BALANCE = "balance";

    public BankMeterItem(Properties props) {
        super(props);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Level pLevel, List<Component> list, TooltipFlag pFlag) {
        if (pStack.getTag() == null) return;
        list.add(Component.translatable("info.numismatics_utils.balance", StringUtils.formatBalance(pStack.getTag().getInt(BALANCE))).withStyle(ChatFormatting.GOLD));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.dayTime() % 100 != 0) return;
        if (!(slotContext.entity() instanceof ServerPlayer)) return;
        boolean updateTag = false;
        LazyOptional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(slotContext.entity());
        AtomicReference<UUID> uuid = new AtomicReference<>(slotContext.entity().getUUID());
        curios.ifPresent(curiosInventory -> {
            if (!curiosInventory.findCurios("card").isEmpty()) {
                ItemStack card = curiosInventory.findCurios("card").get(0).stack();
                if (card.getTag() != null && card.getTag().contains("AccountID")) {
                    uuid.set(card.getTag().getUUID("AccountID"));
                }
            }
        });
        BankAccount account = Numismatics.BANK.getAccount(uuid.get());
        if (account == null )
            account = Numismatics.BANK.getOrCreateAccount(slotContext.entity().getUUID(), BankAccount.Type.PLAYER);

        if (!account.isAuthorized(uuid.get())) return;
        int balance = account.getBalance();
        if (stack.getTag() != null && stack.getTag().get(BALANCE) != null) {
            int currentBalance = stack.getTag().getInt(BALANCE);
            if (currentBalance != balance) updateTag = true;
        } else {
            updateTag = true;
        }
        if (updateTag) stack.getOrCreateTag().putInt(BALANCE, balance);
    }


}