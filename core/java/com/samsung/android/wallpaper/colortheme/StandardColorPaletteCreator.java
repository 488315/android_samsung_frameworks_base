package com.samsung.android.wallpaper.colortheme;

import android.graphics.Color;
import android.util.Log;

import com.android.internal.graphics.ColorUtils;

import com.samsung.android.wallpaper.colortheme.monet.ColorScheme;
import com.samsung.android.wallpaper.colortheme.monet.Style;

import java.lang.reflect.Array;

public class StandardColorPaletteCreator extends ColorPaletteCreator {
    private static final int MAX_RANGE = 19;
    private static final String TAG = "StandardColorPaletteCreator";
    private static final int[] range = {
        -8, 23, 34, 44, 52, 61, 79, 134, 168, 184, 194, 201, 222, 264, 289, 314, 329, 345, 352
    };
    float[][] mColorHsl;
    float[] oneColorHsl;
    int[] oneColorIntSeeds;
    float[] twoColorHsl;
    int[] twoColorIntSeeds;
    int[] seedRange = {7, 39, 56, 106, 176, 211, 276, 321};
    int[] twoColorRange = {7, 39, 106, 211};
    String[] seeds = {
        "#D73B26", "#D99A26", "#D9CD26", "#50D926", "#26D9CD", "#267DD9", "#9126D9", "#D9269A"
    };
    String[] twoColorSeeds = {"#808080", "#D73B26", "#D99A26", "#50D926", "#267DD9"};
    float[] accent1 = new float[3];
    float[] accent2 = new float[3];
    float[] accent3 = new float[3];
    float[] neutral1 = new float[3];
    float[] neutral2 = new float[3];
    protected final Style[] mBasicColorStyle = {Style.RAINBOW, Style.FRUIT_SALAD};

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void generateColorPalette(boolean fromGoogle) {
        this.mColorPalettes.clear();
        if (fromGoogle) {
            for (int i = 0; i < this.seeds.length; i++) {
                int seed = this.mSeedColors[i];
                ColorScheme colorScheme = new ColorScheme(seed, false, Style.RAINBOW);
                ColorPalette colorPalette = new ColorPalette(colorScheme);
                this.mColorPalettes.add(colorPalette.getTable());
            }
            for (int i2 = this.seeds.length;
                    i2 < this.seeds.length + this.twoColorSeeds.length;
                    i2++) {
                int seed2 = this.mSeedColors[i2];
                ColorScheme colorScheme2 = new ColorScheme(seed2, false, Style.FRUIT_SALAD);
                ColorPalette colorPalette2 = new ColorPalette(colorScheme2);
                this.mColorPalettes.add(colorPalette2.getTable());
            }
            return;
        }
        populateStyles();
    }

    public void initSeedColors() {
        setColors();
        setTwoColors();
    }

    private void setColors() {
        this.oneColorIntSeeds = new int[this.seeds.length];
        for (int i = 0; i < this.seeds.length; i++) {
            int colorInt = Color.parseColor(this.seeds[i]);
            this.oneColorIntSeeds[i] = colorInt;
        }
        if (this.oneColorIntSeeds == null || this.oneColorIntSeeds.length <= 0) {
            return;
        }
        this.mColorHsl =
                (float[][])
                        Array.newInstance((Class<?>) Float.TYPE, this.oneColorIntSeeds.length, 3);
        for (int i2 = 0; i2 < this.oneColorIntSeeds.length; i2++) {
            ColorUtils.colorToHSL(this.oneColorIntSeeds[i2], this.mColorHsl[i2]);
        }
    }

    private void setTwoColors() {
        this.twoColorIntSeeds = new int[this.twoColorSeeds.length];
        for (int i = 0; i < this.twoColorSeeds.length; i++) {
            int colorInt = Color.parseColor(this.twoColorSeeds[i]);
            this.twoColorIntSeeds[i] = colorInt;
        }
        if (this.twoColorIntSeeds == null || this.twoColorIntSeeds.length <= 0) {
            return;
        }
        this.mColorHsl =
                (float[][])
                        Array.newInstance((Class<?>) Float.TYPE, this.twoColorIntSeeds.length, 3);
        for (int i2 = 0; i2 < this.twoColorIntSeeds.length; i2++) {
            ColorUtils.colorToHSL(this.twoColorIntSeeds[i2], this.mColorHsl[i2]);
        }
        this.mSeedColors = new int[this.oneColorIntSeeds.length + this.twoColorIntSeeds.length];
        System.arraycopy(
                this.oneColorIntSeeds, 0, this.mSeedColors, 0, this.oneColorIntSeeds.length);
        System.arraycopy(
                this.twoColorIntSeeds,
                0,
                this.mSeedColors,
                this.oneColorIntSeeds.length,
                this.twoColorIntSeeds.length);
    }

    public int[] getOneColorSeeds() {
        return this.oneColorIntSeeds;
    }

    public int[] getTwoColorSeeds() {
        return this.twoColorIntSeeds;
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void generateColorPalette() {
        this.mColorPalettes.clear();
        populateStyles();
    }

    private void populateStyles() {
        setColors();
        Log.d(TAG, "populateStyles : seeds length = " + this.seeds.length + " addOneColorPalette");
        for (int i = 0; i < this.seeds.length; i++) {
            float[] floats = this.mColorHsl[i];
            addOneColorPalette(floats);
        }
        setTwoColors();
        Log.d(
                TAG,
                "populateStyles : seeds length = "
                        + this.twoColorSeeds.length
                        + " addTowColorPalette");
        for (int i2 = 0; i2 < this.twoColorSeeds.length; i2++) {
            float[] floats2 = this.mColorHsl[i2];
            addTwoColorPalette(floats2);
        }
    }

    static int findRange(float hue) {
        if (hue < 0.0f) {
            return 0;
        }
        for (int i = 0; i < range.length; i++) {
            if (range[18] <= hue) {
                return 0;
            }
            if (hue < range[i]) {
                return i - 1;
            }
        }
        return -1;
    }

    static float getHue(int r) {
        float hue = range[r];
        if (hue < 0.0f) {
            return hue + 360.0f;
        }
        if (hue > 360.0f) {
            return hue - 360.0f;
        }
        return hue;
    }

    static float hueMove(float fhue, int step) {
        int hue = Math.round(fhue);
        int r = findRange(hue);
        return getHue((r + step) % range.length);
    }

    private void addOneColorPalette(float[] colorHsl) {
        this.accent1[0] = colorHsl[0];
        this.accent1[1] = 0.7f;
        this.accent2[0] = colorHsl[0];
        this.accent2[1] = 0.4f;
        this.accent3[0] = hueMove(colorHsl[0], 1);
        this.accent3[1] = 0.5f;
        this.neutral1[0] = colorHsl[0];
        this.neutral1[1] = 0.0f;
        this.neutral2[0] = colorHsl[0];
        this.neutral2[1] = 0.0f;
        ColorPalette palette =
                new ColorPalette(
                        this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
        this.mColorPalettes.add(palette.getTable());
    }

    private void addTwoColorPalette(float[] colorHsl) {
        if (isGrayColor(colorHsl)) {
            addGrayColorPalette();
            return;
        }
        this.accent1[0] = colorHsl[0];
        this.accent1[1] = 0.8f;
        this.accent2[0] = hueMove(colorHsl[0], 3);
        this.accent2[1] = 0.6f;
        this.accent3[0] = hueMove(colorHsl[0], 3);
        this.accent3[1] = 0.4f;
        this.neutral1[0] = hueMove(colorHsl[0], 3);
        this.neutral1[1] = 0.15f;
        this.neutral2[0] = hueMove(colorHsl[0], 3);
        this.neutral2[1] = 0.15f;
        ColorPalette palette =
                new ColorPalette(
                        this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
        this.mColorPalettes.add(palette.getTable());
    }

    private void addGrayColorPalette() {
        this.accent1[0] = 0.0f;
        this.accent1[1] = 0.0f;
        this.accent2[0] = 0.0f;
        this.accent2[1] = 0.0f;
        this.accent3[0] = 0.0f;
        this.accent3[1] = 0.0f;
        this.neutral1[0] = 0.0f;
        this.neutral1[1] = 0.0f;
        this.neutral2[0] = 0.0f;
        this.neutral2[1] = 0.0f;
        this.mColorPalettes.add(
                new ColorPalette(
                                this.accent1,
                                this.accent2,
                                this.accent3,
                                this.neutral1,
                                this.neutral2)
                        .getTable());
    }

    static boolean isGrayColor(float[] colorHsl) {
        return colorHsl[1] <= 0.01f;
    }
}
