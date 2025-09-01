package io.github.chakyl.numismaticsutils.utils;

import io.github.chakyl.numismaticsutils.NumismaticsUtils;
import io.github.chakyl.numismaticsutils.registry.ItemRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.List;

public class CurioUtils {

    public static boolean playerHasBankMeterEquipped(Player player) {
        Minecraft mc = Minecraft.getInstance();
        boolean curioEquipped = false;

        if (mc.level == null || mc.player == null ) return false;

            List<SlotResult> curiosInventory = CuriosApi.getCuriosHelper().findCurios(player, ItemRegistry.BANK_METER.get());
            curioEquipped = !curiosInventory.isEmpty();

        return curioEquipped;
    }
}
