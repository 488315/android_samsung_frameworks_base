package com.samsung.android.wallpaper.colortheme.monet;

import com.android.internal.graphics.ColorUtils;

public class Shades {
    public static final float MIDDLE_LSTAR = 49.6f;

    public static int[] of(float hue, float chroma) {
        int[] shades = new int[12];
        shades[0] = ColorUtils.CAMToColor(hue, Math.min(40.0f, chroma), 99.0f);
        shades[1] = ColorUtils.CAMToColor(hue, Math.min(40.0f, chroma), 95.0f);
        int i = 2;
        while (i < 12) {
            float lStar = i == 6 ? 49.6f : 100 - ((i - 1) * 10);
            if (lStar >= 90.0f) {
                chroma = Math.min(40.0f, chroma);
            }
            shades[i] = ColorUtils.CAMToColor(hue, chroma, lStar);
            i++;
        }
        return shades;
    }
}
