package io.github.chakyl.numismaticsutils.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtils {

    public static String formatBalance(Integer balance) {
        return NumberFormat.getNumberInstance(Locale.US).format(balance);
    };
}
