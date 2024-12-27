package com.android.systemui.battery.unified;

import android.graphics.Color;

public interface BatteryColors {
    public static final LightThemeColors LIGHT_THEME_COLORS;

    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
        }

        private Companion() {
        }
    }

    public final class DarkThemeColors implements BatteryColors {
        public static final DarkThemeColors INSTANCE = null;
        public static final int activeFill = 0;
        public static final int bg = 0;
        public static final int errorFill = 0;
        public static final int fg = 0;
        public static final int fill = 0;
        public static final int fillOnly = 0;
        public static final int warnFill = 0;

        static {
            new DarkThemeColors();
            Color.valueOf(0.0f, 0.0f, 0.0f, 0.18f).toArgb();
            Color.parseColor("#5F6368");
            Color.parseColor("#BDC1C6");
            Color.parseColor("#188038");
            Color.parseColor("#F29900");
            Color.parseColor("#C5221F");
        }

        private DarkThemeColors() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DarkThemeColors);
        }

        public final int hashCode() {
            return -791469770;
        }

        public final String toString() {
            return "DarkThemeColors";
        }
    }

    public final class LightThemeColors implements BatteryColors {
        public static final LightThemeColors INSTANCE = new LightThemeColors();
        public static final int activeFill = 0;
        public static final int bg = 0;
        public static final int errorFill = 0;
        public static final int fg = 0;
        public static final int fill = 0;
        public static final int fillOnly = 0;
        public static final int warnFill = 0;

        static {
            Color.valueOf(1.0f, 1.0f, 1.0f, 0.22f).toArgb();
            Color.parseColor("#9AA0A6");
            Color.parseColor("#80868B");
            Color.parseColor("#34A853");
            Color.parseColor("#FBBC04");
            Color.parseColor("#EA4335");
        }

        private LightThemeColors() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof LightThemeColors);
        }

        public final int hashCode() {
            return 782959120;
        }

        public final String toString() {
            return "LightThemeColors";
        }
    }

    static {
        int i = Companion.$r8$clinit;
        LIGHT_THEME_COLORS = LightThemeColors.INSTANCE;
        DarkThemeColors darkThemeColors = DarkThemeColors.INSTANCE;
    }
}
