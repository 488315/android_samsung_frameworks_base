package android.content.theming;

import android.content.om.WallpaperThemeConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class ThemeStyle {
    public static final int CLOCK = 8;
    public static final int CLOCK_VIBRANT = 9;
    public static final int CONTENT = 6;
    public static final int EXPRESSIVE = 3;
    public static final int FRUIT_SALAD = 5;
    public static final int MONOCHROMATIC = 7;
    public static final int RAINBOW = 4;
    public static final int SPRITZ = 0;
    public static final int TONAL_SPOT = 1;
    public static final int VIBRANT = 2;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private ThemeStyle() {
    }

    public static String toString(Integer num) {
        if (num == null) {
            throw new IllegalArgumentException("Invalid style value: null");
        }
        switch (num.intValue()) {
            case 0:
                return "SPRITZ";
            case 1:
                return WallpaperThemeConstants.DYNAMIC_COLOR_THEME_STYLE_DEFAULT;
            case 2:
                return "VIBRANT";
            case 3:
                return "EXPRESSIVE";
            case 4:
                return "RAINBOW";
            case 5:
                return "FRUIT_SALAD";
            case 6:
                return "CONTENT";
            case 7:
                return WallpaperThemeConstants.DYNAMIC_COLOR_THEME_STYLE_MONOCHROMATIC;
            case 8:
                return "CLOCK";
            case 9:
                return "CLOCK_VIBRANT";
            default:
                throw new IllegalArgumentException("Invalid style value: " + num);
        }
    }

    public static int valueOf(String str) {
        str.hashCode();
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
                throw new IllegalArgumentException("Invalid style name: " + str);
        }
    }

    public static String name(int i) {
        return toString(Integer.valueOf(i));
    }

    public static int[] values() {
        return new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    }
}
