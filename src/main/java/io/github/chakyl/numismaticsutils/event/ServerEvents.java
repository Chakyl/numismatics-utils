package io.github.chakyl.numismaticsutils.event;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import io.github.chakyl.numismaticsutils.utils.TerminalUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static io.github.chakyl.numismaticsutils.registry.TagRegistry.ATM_BLOCK;

public class ServerEvents {
    @Mod.EventBusSubscriber(modid = NumismaticsUtils.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onBlockInteract(PlayerInteractEvent.RightClickBlock event) {
            Entity player = event.getEntity();
            BlockState clickedBlock = event.getLevel().getBlockState(event.getPos());
            if (player instanceof ServerPlayer && !player.level().isClientSide) {
                if ((clickedBlock.is(ATM_BLOCK) && TerminalUtils.openTerminal(player.level(), (Player) player))) {
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }
}