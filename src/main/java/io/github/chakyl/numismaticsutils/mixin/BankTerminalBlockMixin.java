package io.github.chakyl.numismaticsutils.mixin;

import dev.ithundxr.createnumismatics.Numismatics;
import dev.ithundxr.createnumismatics.content.backend.BankAccount;
import dev.ithundxr.createnumismatics.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.UUID;

import static io.github.chakyl.numismaticsutils.utils.CurioUtils.getCardCurio;

@Mixin(targets = "dev.ithundxr.createnumismatics.content.bank.BankTerminalBlock")
public abstract class BankTerminalBlockMixin {

    @Inject(method = "use", at = @At(value = "HEAD"), cancellable=true)
    private void use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir) {
        if (!level.isClientSide) {
            BankAccount cardAccount;
            UUID cardUUID = getCardCurio(player);

            if (cardUUID != null) {
                cardAccount = Numismatics.BANK.getAccount(cardUUID);
                if (cardAccount != null && cardAccount.isAuthorized(player)) {
                    ServerPlayer var10000 = (ServerPlayer) player;
                    Objects.requireNonNull(cardAccount);
                    Utils.openScreen(var10000, cardAccount, cardAccount::sendToMenu);
                    cir.setReturnValue(InteractionResult.SUCCESS);
                }
            }
        }

    }
}
