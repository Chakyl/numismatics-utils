package io.github.chakyl.numismaticsutils.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {

    /** Numismatics stores balances in spurs; 64 spurs == 1 cog. */
    public static final int SPURS_PER_COG = 64;

    public static String formatBalance(Integer balance) {
        return NumberFormat.getNumberInstance(Locale.US).format(balance);
    }

    /**
     * Converts a spur balance to a cog count (floor) and formats it with a thousand
     * separator, suffixed with " cog". 192 spurs -> "3 cog", 1_000_000 spurs -> "15,625 cog".
     */
    public static String formatCog(int balanceInSpurs) {
        int cogs = balanceInSpurs / SPURS_PER_COG;
        return NumberFormat.getNumberInstance(Locale.US).format(cogs) + " cog";
    }
}
