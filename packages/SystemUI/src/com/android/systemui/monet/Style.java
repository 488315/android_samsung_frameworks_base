package com.android.systemui.monet;

/* loaded from: classes2.dex */
public final class Style {
    private Style() {
    }

    public static int valueOf(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Invalid style value: null");
        }
        switch (str) {
            case "TONAL_SPOT":
                return 1;
            case "SPRITZ":
                return 0;
            case "EXPRESSIVE":
                return 3;
            case "FRUIT_SALAD":
                return 5;
            case "CLOCK_VIBRANT":
                return 9;
            case "CLOCK":
                return 8;
            case "VIBRANT":
                return 2;
            case "MONOCHROMATIC":
                return 7;
            case "CONTENT":
                return 6;
            case "RAINBOW":
                return 4;
            default:
                throw new IllegalArgumentException("Invalid style name: ".concat(str));
        }
    }
}
