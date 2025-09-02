package io.github.chakyl.numismaticsutils.config;


import io.github.chakyl.numismaticsutils.config.DefaultClientSettings.Client;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class NumismaticsConfigClient {
    public static final ForgeConfigSpec CLIENT_SPEC;
    private static ConfigValue<Integer> hudX;
    private static ConfigValue<Integer> hudY;
    private static ConfigValue<Double> hudScale;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        setupConfig(builder);
        CLIENT_SPEC = builder.build();
    }

    private NumismaticsConfigClient() {
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
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
    }

    private static <T> T getOrDefault(ForgeConfigSpec.ConfigValue<T> config) {
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
        NumismaticsConfigClient.hudX.set(x);
    }

    public static int getHudY() {
        return getOrDefault(hudY);
    }

    public static void setHudY(int y) {
        NumismaticsConfigClient.hudY.set(y);
    }

    public static double getHudScale() {
        return getOrDefault(hudScale);

    }

    public static void setHudScale(double scale) {
        NumismaticsConfigClient.hudScale.set(scale);
    }

}