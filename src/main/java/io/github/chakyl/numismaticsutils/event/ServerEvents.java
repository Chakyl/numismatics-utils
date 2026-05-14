package io.github.chakyl.numismaticsutils.event;

import dev.ithundxr.createnumismatics.Numismatics;
import dev.ithundxr.createnumismatics.content.backend.BankAccount;
import dev.ithundxr.createnumismatics.util.Utils;
import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import io.github.chakyl.numismaticsutils.utils.CurioUtils;
import io.github.chakyl.numismaticsutils.utils.TerminalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.UUID;

import static io.github.chakyl.numismaticsutils.registry.TagRegistry.ATM_BLOCK;

@EventBusSubscriber(modid = NumismaticsUtils.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ServerEvents {

    private static final ResourceLocation BANK_TERMINAL = ResourceLocation.fromNamespaceAndPath("numismatics", "bank_terminal");

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
        Entity entity = event.getEntity();
        Level level = event.getLevel();
        if (level.isClientSide || !(entity instanceof ServerPlayer player)) return;

        BlockPos pos = event.getPos();
        BlockState clicked = level.getBlockState(pos);

        // Tagged ATM blocks (from whimsy_deco): open the player/card account.
        if (clicked.is(ATM_BLOCK) && TerminalUtils.openTerminal(level, player)) {
            event.setCancellationResult(InteractionResult.SUCCESS);
            event.setCanceled(true);
            return;
        }

        // Numismatics bank terminal: if the player wears a linked card, redirect to that account
        // (replaces the BankTerminalBlockMixin from the 1.20.1 build).
        Block block = clicked.getBlock();
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
        if (BANK_TERMINAL.equals(id)) {
            UUID cardUUID = CurioUtils.getCardCurio(player);
            if (cardUUID != null && !cardUUID.equals(player.getUUID())) {
                BankAccount cardAccount = Numismatics.BANK.getAccount(cardUUID);
                if (cardAccount != null && cardAccount.isAuthorized(player)) {
                    Utils.openScreen(player, cardAccount, cardAccount::sendToMenu);
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }
}
