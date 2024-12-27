package com.samsung.android.wallpaper.colortheme;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;

import com.android.internal.graphics.ColorUtils;

import java.lang.reflect.Array;
import java.util.List;

/* loaded from: classes6.dex */
public class ColorPaletteCreator5 extends ColorPaletteCreator {
    private static final int GRAY_HUE_PRESET1 = 0;
    private static final int GRAY_HUE_PRESET2 = 120;
    private static final int GRAY_HUE_PRESET3 = 240;
    private static final float GRAY_SAT_PRESET1 = 0.0f;
    private static final float GRAY_SAT_PRESET2 = 0.05f;
    private static final int MAX_RANGE = 19;
    private static final String TAG = "ColorPaletteCreator5";
    private static final int[] range = {
        -8, 23, 34, 44, 52, 61, 79, 134, 168, 184, 194, 201, 222, 264, 289, 314, 329, 345, 352
    };
    float[][] mColorHsl;
    float[] accent1 = new float[3];
    float[] accent2 = new float[3];
    float[] accent3 = new float[3];
    float[] neutral1 = new float[3];
    float[] neutral2 = new float[3];

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public List<int[][]> getColorPalettes() {
        return this.mColorPalettes;
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void setColors(float[][] p) {
        if (p != null && p.length > 0) {
            this.mSeedColors = new int[p.length];
            for (int i = 0; i < p.length; i++) {
                this.mSeedColors[i] = ColorUtils.HSLToColor(p[i]);
            }
        }
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void setColors(int[] seeds) {
        this.mSeedColors = seeds;
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void generateColorPalette() {
        this.mColorPalettes.clear();
        populateStyles();
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void generateColorPalette(boolean monet) {
        if (monet) {
            super.generateColorPalette(true);
        } else {
            generateColorPalette();
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

    static float findRatio(float hue, int r) {
        float ratio = (((hue - range[r]) + 360.0f) % 360.0f) / (range[r + 1] - range[r]);
        if (ratio > 1.0f || ratio < 0.0f) {
            Log.e(TAG, "findRatio : ratio is more than 1");
            Log.v(
                    TAG,
                    "findRatio : hue = "
                            + hue
                            + " range[r] = "
                            + range[r]
                            + " range[r+1] = "
                            + range[r + 1]
                            + " ratio = "
                            + ratio);
        }
        return ratio;
    }

    static float getHue(int r, float ratio) {
        int r2 = ((range.length + r) - 1) % (range.length - 1);
        float hue = range[r2] + ((range[r2 + 1] - range[r2]) * ratio);
        if (hue < 0.0f) {
            return hue + 360.0f;
        }
        if (hue > 360.0f) {
            return hue - 360.0f;
        }
        return hue;
    }

    static float hueMove(float hue, int step) {
        int r;
        int r2 = findRange(hue);
        float ratio = findRatio(hue, r2);
        if (ratio > 0.5f) {
            r = (r2 + step) % (range.length - 1);
        } else {
            r = (r2 - step) % (range.length - 1);
        }
        return getHue(r, ratio);
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    float comp(float hue) {
        float hue2 = hue + 180.0f;
        if (hue2 > 360.0f) {
            return hue2 - 360.0f;
        }
        return hue2;
    }

    static boolean isGrayImage(float[][] colorHsl) {
        for (float[] color : colorHsl) {
            if (color[1] > 0.01f) {
                return false;
            }
        }
        return true;
    }

    private void populateStyles() {
        if (this.mSeedColors == null || this.mSeedColors.length <= 0) {
            return;
        }
        Log.v(TAG, "populateStyles : seedsColor length" + this.mSeedColors.length);
        this.mColorHsl =
                (float[][]) Array.newInstance((Class<?>) Float.TYPE, this.mSeedColors.length, 3);
        for (int i = 0; i < this.mSeedColors.length; i++) {
            ColorUtils.colorToHSL(this.mSeedColors[i], this.mColorHsl[i]);
            Log.v(
                    TAG,
                    "populateStyles : seed = "
                            + this.mSeedColors[i]
                            + " C"
                            + (i + 1)
                            + " = "
                            + this.mColorHsl[i][0]);
        }
        if (isGrayImage(this.mColorHsl)) {
            addGrayStylePalette();
            return;
        }
        for (int i2 = 0; i2 < this.mSeedColors.length; i2++) {
            float[] floats = this.mColorHsl[i2];
            addTonalSpot(floats);
            addNeutral(floats);
            addVibrant(floats);
            addExpressive(floats, i2);
        }
    }

    private void addGrayStylePalette() {
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
        this.accent1[0] = 0.0f;
        this.accent1[1] = 0.05f;
        this.accent2[0] = 0.0f;
        this.accent2[1] = 0.05f;
        this.accent3[0] = 0.0f;
        this.accent3[1] = 0.05f;
        this.neutral1[0] = 0.0f;
        this.neutral1[1] = 0.05f;
        this.neutral2[0] = 0.0f;
        this.neutral2[1] = 0.05f;
        this.mColorPalettes.add(
                new ColorPalette(
                                this.accent1,
                                this.accent2,
                                this.accent3,
                                this.neutral1,
                                this.neutral2)
                        .getTable());
        this.accent1[0] = 120.0f;
        this.accent1[1] = 0.05f;
        this.accent2[0] = 120.0f;
        this.accent2[1] = 0.05f;
        this.accent3[0] = 120.0f;
        this.accent3[1] = 0.05f;
        this.neutral1[0] = 120.0f;
        this.neutral1[1] = 0.05f;
        this.neutral2[0] = 120.0f;
        this.neutral2[1] = 0.05f;
        this.mColorPalettes.add(
                new ColorPalette(
                                this.accent1,
                                this.accent2,
                                this.accent3,
                                this.neutral1,
                                this.neutral2)
                        .getTable());
        this.accent1[0] = 240.0f;
        this.accent1[1] = 0.05f;
        this.accent2[0] = 240.0f;
        this.accent2[1] = 0.05f;
        this.accent3[0] = 240.0f;
        this.accent3[1] = 0.05f;
        this.neutral1[0] = 240.0f;
        this.neutral1[1] = 0.05f;
        this.neutral2[0] = 240.0f;
        this.neutral2[1] = 0.05f;
        this.mColorPalettes.add(
                new ColorPalette(
                                this.accent1,
                                this.accent2,
                                this.accent3,
                                this.neutral1,
                                this.neutral2)
                        .getTable());
    }

    private ColorPalette addTonalSpot(float[] colorHsl) {
        this.accent1[0] = colorHsl[0];
        this.accent1[1] = Math.min(colorHsl[1] + 0.3f, 0.7f);
        this.accent2[0] = hueMove(colorHsl[0], 2);
        this.accent2[1] = Math.min(colorHsl[1] + 0.0f, 0.4f);
        this.accent3[0] = hueMove(colorHsl[0], 5);
        this.accent3[1] = Math.min(colorHsl[1] + 0.1f, 0.5f);
        this.neutral1[0] = colorHsl[0];
        this.neutral1[1] = Math.min(colorHsl[1] + 0.0f, 0.15f);
        this.neutral2[0] = colorHsl[0];
        this.neutral2[1] = Math.min(colorHsl[1] + 0.0f, 0.0f);
        ColorPalette palette =
                new ColorPalette(
                        this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
        this.mColorPalettes.add(palette.getTable());
        return palette;
    }

    private ColorPalette addNeutral(float[] colorHsl) {
        this.accent1[0] = colorHsl[0];
        this.accent1[1] = Math.min(colorHsl[1] + 0.0f, 0.2f);
        this.accent2[0] = hueMove(colorHsl[0], 2);
        this.accent2[1] = Math.min(colorHsl[1] + 0.0f, 0.1f);
        this.accent3[0] = hueMove(colorHsl[0], 5);
        this.accent3[1] = Math.min(colorHsl[1] + 0.0f, 0.2f);
        this.neutral1[0] = colorHsl[0];
        this.neutral1[1] = Math.min(colorHsl[1] + 0.0f, GRAY_SAT_PRESET2);
        this.neutral2[0] = colorHsl[0];
        this.neutral2[1] = Math.min(colorHsl[1] + 0.0f, 0.0f);
        ColorPalette palette =
                new ColorPalette(
                        this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
        this.mColorPalettes.add(palette.getTable());
        return palette;
    }

    private ColorPalette addVibrant(float[] colorHsl) {
        this.accent1[0] = colorHsl[0];
        this.accent1[1] = Math.min(colorHsl[1] + 0.3f, 0.8f);
        this.accent2[0] = colorHsl[0];
        this.accent2[1] = Math.min(colorHsl[1] + 0.0f, 0.6f);
        this.accent3[0] = hueMove(colorHsl[0], 1);
        this.accent3[1] = Math.min(colorHsl[1] + 0.1f, 0.6f);
        this.neutral1[0] = colorHsl[0];
        this.neutral1[1] = Math.min(colorHsl[1] + 0.0f, 0.2f);
        this.neutral2[0] = colorHsl[0];
        this.neutral2[1] = Math.min(colorHsl[1] + 0.0f, 0.2f);
        ColorPalette palette =
                new ColorPalette(
                        this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
        this.mColorPalettes.add(palette.getTable());
        return palette;
    }

    private ColorPalette addExpressive(float[] colorHsl, int order) {
        this.accent1[0] = getExpressiveHue(colorHsl[0], order);
        this.accent1[1] = Math.min(colorHsl[1] + 0.0f, 0.7f);
        this.accent2[0] = colorHsl[0];
        this.accent2[1] = Math.min(colorHsl[1] + 0.0f, 0.6f);
        this.accent3[0] = hueMove(colorHsl[0], 1);
        this.accent3[1] = Math.min(colorHsl[1] + 0.0f, 0.4f);
        this.neutral1[0] = colorHsl[0];
        this.neutral1[1] = Math.min(colorHsl[1] + 0.0f, 0.3f);
        this.neutral2[0] = colorHsl[0];
        this.neutral2[1] = Math.min(colorHsl[1] + 0.0f, 0.2f);
        ColorPalette palette =
                new ColorPalette(
                        this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
        this.mColorPalettes.add(palette.getTable());
        return palette;
    }

    public float getExpressiveHue(float hue, int order) {
        int r = findRange(hue);
        float ratio = findRatio(hue, r);
        int leftRange = (((r - 5) + range.length) - 1) % (range.length - 1);
        float leftHue = getHue(leftRange, ratio);
        int rightRange = (((r + 5) + range.length) - 1) % (range.length - 1);
        float rightHue = getHue(rightRange, ratio);
        Log.v(
                TAG,
                "getExpressiveHue : leftHue = "
                        + leftHue
                        + " rightHue = "
                        + rightHue
                        + " C"
                        + (order + 1)
                        + " case");
        if (leftHue > rightHue) {
            Log.v(
                    TAG,
                    "getExpressiveHue : leftHue("
                            + leftHue
                            + ") > c1, c2, c3, c4 > rightHue("
                            + rightHue
                            + NavigationBarInflaterView.KEY_CODE_END);
            for (int i = 0; i < this.mColorHsl.length; i++) {
                if (i != order) {
                    float targetHue = this.mColorHsl[i][0];
                    if (rightHue < targetHue && targetHue < leftHue) {
                        Log.v(TAG, "getExpressiveHue : return seeds C" + (i + 1) + " " + targetHue);
                        return targetHue;
                    }
                }
            }
        } else {
            Log.v(
                    TAG,
                    "getExpressiveHue : 360 > c1,c2,c3,c4 > rightHue("
                            + rightHue
                            + ") || 0 < c1,c2,c3,c4 < leftHue("
                            + leftHue
                            + NavigationBarInflaterView.KEY_CODE_END);
            for (int i2 = 0; i2 < this.mColorHsl.length; i2++) {
                if (i2 != order) {
                    float targetHue2 = this.mColorHsl[i2][0];
                    if ((360.0f > targetHue2 && targetHue2 > rightHue)
                            || (leftHue > targetHue2 && targetHue2 > 0.0f)) {
                        Log.v(
                                TAG,
                                "getExpressiveHue : return seeds C" + (i2 + 1) + " " + targetHue2);
                        return targetHue2;
                    }
                }
            }
        }
        return hueMove(hue, -5);
    }
}
