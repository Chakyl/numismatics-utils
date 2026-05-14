package io.github.chakyl.numismaticsutils.config;

import io.github.chakyl.numismaticsutils.config.DefaultClientSettings.Client;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;

public class NumismaticsConfigClient {
    public static final ModConfigSpec CLIENT_SPEC;
    private static ConfigValue<Integer> hudX;
    private static ConfigValue<Integer> hudY;
    private static ConfigValue<Double> hudScale;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        setupConfig(builder);
        CLIENT_SPEC = builder.build();
    }

    private NumismaticsConfigClient() {
    }

    private static void setupConfig(ModConfigSpec.Builder builder) {
        builder.push("Numismatics Utils");

        hudX = builder.comment(
                        "The horizontal offset of the Bank Meter text (in pixels)\n" + "Default is " + Client.DEFAULT_X_OFFSET + ".")
                .define("hud_x_position", Client.DEFAULT_X_OFFSET);

        hudY = builder.comment(
                        "The vertical offset of the Bank Meter text (in pixels)\n" + "Default is " + Client.DEFAULT_Y_OFFSET + ".")
                .define("hud_y_position", Client.DEFAULT_Y_OFFSET);

        hudScale = builder.comment(
                        "The scale of the Bank Meter text.\n" + "Default is " + Client.DEFAULT_HUD_SCALE + ".")
                .defineInRange("hud_scale", Client.DEFAULT_HUD_SCALE, Client.HUD_SCALE_MIN, Client.HUD_SCALE_MAX);

        builder.pop();
    }

    private static <T> T getOrDefault(ConfigValue<T> config) {
        if (CLIENT_SPEC.isLoaded()) {
            return config.get();
        } else {
            return config.getDefault();
        }
    }

    public static int getHudX() {
        return getOrDefault(hudX);
    }

    public static void setHudX(int x) {
        hudX.set(x);
    }

    public static int getHudY() {
        return getOrDefault(hudY);
    }

    public static void setHudY(int y) {
        hudY.set(y);
    }

    public static double getHudScale() {
        return getOrDefault(hudScale);
    }

    public static void setHudScale(double scale) {
        hudScale.set(scale);
    }
}
