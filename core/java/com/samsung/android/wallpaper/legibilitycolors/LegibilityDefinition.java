package com.samsung.android.wallpaper.legibilitycolors;

public class LegibilityDefinition {
    public static final int CONTENT_COLOR_DARK = -12303292;
    public static final float CONTENT_COLOR_DARK_BRIGHTNESS = 27.0f;
    public static final int CONTENT_COLOR_LIGHT = -328966;
    public static final float CONTENT_COLOR_LIGHT_BRIGHTNESS = 98.0f;
    public static final int SHADOW_DARK = 1358954496;
    public static final int SHADOW_LIGHT = 654311423;
    public static final String VERSION = "1.1.0";

    public enum ColorType {
        NONE,
        DARK,
        LIGHT,
        GRAY
    }

    public enum ColorWeightType {
        EACH,
        UNITY
    }
}
