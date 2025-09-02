package io.github.chakyl.numismaticsutils.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class CurioUtils {

    public static UUID getCardCurio(Player player) {
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
        return cardUUID.get();
    }
}
