package io.github.chakyl.numismaticsutils.items;

import dev.ithundxr.createnumismatics.Numismatics;
import dev.ithundxr.createnumismatics.content.backend.BankAccount;
import dev.ithundxr.createnumismatics.content.bank.CardItem;
import dev.ithundxr.createnumismatics.registry.NumismaticsTags;
import dev.ithundxr.createnumismatics.util.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;


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

        LazyOptional<ICuriosItemHandler> curios = CuriosApi.getCuriosInventory(player);
        AtomicReference<UUID> cardUUID = new AtomicReference<>(player.getUUID());
        curios.ifPresent(curiosInventory -> {
            if (!curiosInventory.findCurios("card").isEmpty()) {
                ItemStack card = curiosInventory.findCurios("card").get(0).stack();
                if (card.getTag() != null && card.getTag().contains("AccountID")) {
                    cardUUID.set(card.getTag().getUUID("AccountID"));
                }
            }
        });
        if (cardUUID.get() != null) {
            account = Numismatics.BANK.getAccount(cardUUID.get());
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