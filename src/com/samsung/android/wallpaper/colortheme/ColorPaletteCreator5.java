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
    private static final int[] range = {-8, 23, 34, 44, 52, 61, 79, 134, 168, 184, 194, 201, 222, 264, 289, 314, 329, 345, 352};
    float[][] mColorHsl;
    float[] accent1 = new float[3];
    float[] accent2 = new float[3];
    float[] accent3 = new float[3];
    float[] neutral1 = new float[3];
    float[] neutral2 = new float[3];

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    float comp(float f) {
        float f2 = f + 180.0f;
        return f2 > 360.0f ? f2 - 360.0f : f2;
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public List<int[][]> getColorPalettes() {
        return this.mColorPalettes;
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void setColors(float[][] fArr) {
        if (fArr == null || fArr.length <= 0) {
            return;
        }
        this.mSeedColors = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            this.mSeedColors[i] = ColorUtils.HSLToColor(fArr[i]);
        }
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void setColors(int[] iArr) {
        this.mSeedColors = iArr;
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void generateColorPalette() {
        this.mColorPalettes.clear();
        populateStyles();
    }

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void generateColorPalette(boolean z) {
        if (z) {
            super.generateColorPalette(true);
        } else {
            generateColorPalette();
        }
    }

    static int findRange(float f) {
        if (f < 0.0f) {
            return 0;
        }
        int i = 0;
        while (true) {
            if (i >= range.length) {
                return -1;
            }
            if (r2[18] <= f) {
                return 0;
            }
            if (f < r2[i]) {
                return i - 1;
            }
            i++;
        }
    }

    static float findRatio(float f, int i) {
        int[] iArr = range;
        int i2 = i + 1;
        float f2 = (((f - iArr[i]) + 360.0f) % 360.0f) / (iArr[i2] - r1);
        if (f2 <= 1.0f && f2 >= 0.0f) {
            return f2;
        }
        Log.e(TAG, "findRatio : ratio is more than 1");
        Log.v(TAG, "findRatio : hue = " + f + " range[r] = " + iArr[i] + " range[r+1] = " + iArr[i2] + " ratio = " + f2);
        return f2;
    }

    static float getHue(int i, float f) {
        float f2 = range[((i + r0.length) - 1) % (r0.length - 1)] + ((r0[r3 + 1] - r1) * f);
        return f2 < 0.0f ? f2 + 360.0f : f2 > 360.0f ? f2 - 360.0f : f2;
    }

    static float hueMove(float f, int i) {
        int length;
        int findRange = findRange(f);
        float findRatio = findRatio(f, findRange);
        if (findRatio > 0.5f) {
            length = (findRange + i) % (range.length - 1);
        } else {
            length = (findRange - i) % (range.length - 1);
        }
        return getHue(length, findRatio);
    }

    static boolean isGrayImage(float[][] fArr) {
        for (float[] fArr2 : fArr) {
            if (fArr2[1] > 0.01f) {
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
        this.mColorHsl = (float[][]) Array.newInstance((Class<?>) Float.TYPE, this.mSeedColors.length, 3);
        int i = 0;
        while (i < this.mSeedColors.length) {
            ColorUtils.colorToHSL(this.mSeedColors[i], this.mColorHsl[i]);
            StringBuilder sb = new StringBuilder("populateStyles : seed = ");
            sb.append(this.mSeedColors[i]);
            sb.append(" C");
            int i2 = i + 1;
            sb.append(i2);
            sb.append(" = ");
            sb.append(this.mColorHsl[i][0]);
            Log.v(TAG, sb.toString());
            i = i2;
        }
        if (isGrayImage(this.mColorHsl)) {
            addGrayStylePalette();
            return;
        }
        for (int i3 = 0; i3 < this.mSeedColors.length; i3++) {
            float[] fArr = this.mColorHsl[i3];
            addTonalSpot(fArr);
            addNeutral(fArr);
            addVibrant(fArr);
            addExpressive(fArr, i3);
        }
    }

    private void addGrayStylePalette() {
        float[] fArr = this.accent1;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        float[] fArr2 = this.accent2;
        fArr2[0] = 0.0f;
        fArr2[1] = 0.0f;
        float[] fArr3 = this.accent3;
        fArr3[0] = 0.0f;
        fArr3[1] = 0.0f;
        float[] fArr4 = this.neutral1;
        fArr4[0] = 0.0f;
        fArr4[1] = 0.0f;
        float[] fArr5 = this.neutral2;
        fArr5[0] = 0.0f;
        fArr5[1] = 0.0f;
        this.mColorPalettes.add(new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2).getTable());
        float[] fArr6 = this.accent1;
        fArr6[0] = 0.0f;
        fArr6[1] = 0.05f;
        float[] fArr7 = this.accent2;
        fArr7[0] = 0.0f;
        fArr7[1] = 0.05f;
        float[] fArr8 = this.accent3;
        fArr8[0] = 0.0f;
        fArr8[1] = 0.05f;
        float[] fArr9 = this.neutral1;
        fArr9[0] = 0.0f;
        fArr9[1] = 0.05f;
        float[] fArr10 = this.neutral2;
        fArr10[0] = 0.0f;
        fArr10[1] = 0.05f;
        this.mColorPalettes.add(new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2).getTable());
        float[] fArr11 = this.accent1;
        fArr11[0] = 120.0f;
        fArr11[1] = 0.05f;
        float[] fArr12 = this.accent2;
        fArr12[0] = 120.0f;
        fArr12[1] = 0.05f;
        float[] fArr13 = this.accent3;
        fArr13[0] = 120.0f;
        fArr13[1] = 0.05f;
        float[] fArr14 = this.neutral1;
        fArr14[0] = 120.0f;
        fArr14[1] = 0.05f;
        float[] fArr15 = this.neutral2;
        fArr15[0] = 120.0f;
        fArr15[1] = 0.05f;
        this.mColorPalettes.add(new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2).getTable());
        float[] fArr16 = this.accent1;
        fArr16[0] = 240.0f;
        fArr16[1] = 0.05f;
        float[] fArr17 = this.accent2;
        fArr17[0] = 240.0f;
        fArr17[1] = 0.05f;
        float[] fArr18 = this.accent3;
        fArr18[0] = 240.0f;
        fArr18[1] = 0.05f;
        float[] fArr19 = this.neutral1;
        fArr19[0] = 240.0f;
        fArr19[1] = 0.05f;
        float[] fArr20 = this.neutral2;
        fArr20[0] = 240.0f;
        fArr20[1] = 0.05f;
        this.mColorPalettes.add(new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2).getTable());
    }

    private ColorPalette addTonalSpot(float[] fArr) {
        float[] fArr2 = this.accent1;
        fArr2[0] = fArr[0];
        fArr2[1] = Math.min(fArr[1] + 0.3f, 0.7f);
        this.accent2[0] = hueMove(fArr[0], 2);
        this.accent2[1] = Math.min(fArr[1] + 0.0f, 0.4f);
        this.accent3[0] = hueMove(fArr[0], 5);
        this.accent3[1] = Math.min(fArr[1] + 0.1f, 0.5f);
        float[] fArr3 = this.neutral1;
        fArr3[0] = fArr[0];
        fArr3[1] = Math.min(fArr[1] + 0.0f, 0.15f);
        float[] fArr4 = this.neutral2;
        fArr4[0] = fArr[0];
        fArr4[1] = Math.min(fArr[1] + 0.0f, 0.0f);
        ColorPalette colorPalette = new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
        this.mColorPalettes.add(colorPalette.getTable());
        return colorPalette;
    }

    private ColorPalette addNeutral(float[] fArr) {
        float[] fArr2 = this.accent1;
        fArr2[0] = fArr[0];
        fArr2[1] = Math.min(fArr[1] + 0.0f, 0.2f);
        this.accent2[0] = hueMove(fArr[0], 2);
        this.accent2[1] = Math.min(fArr[1] + 0.0f, 0.1f);
        this.accent3[0] = hueMove(fArr[0], 5);
        this.accent3[1] = Math.min(fArr[1] + 0.0f, 0.2f);
        float[] fArr3 = this.neutral1;
        fArr3[0] = fArr[0];
        fArr3[1] = Math.min(fArr[1] + 0.0f, GRAY_SAT_PRESET2);
        float[] fArr4 = this.neutral2;
        fArr4[0] = fArr[0];
        fArr4[1] = Math.min(fArr[1] + 0.0f, 0.0f);
        ColorPalette colorPalette = new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
        this.mColorPalettes.add(colorPalette.getTable());
        return colorPalette;
    }

    private ColorPalette addVibrant(float[] fArr) {
        float[] fArr2 = this.accent1;
        fArr2[0] = fArr[0];
        fArr2[1] = Math.min(fArr[1] + 0.3f, 0.8f);
        float[] fArr3 = this.accent2;
        fArr3[0] = fArr[0];
        fArr3[1] = Math.min(fArr[1] + 0.0f, 0.6f);
        this.accent3[0] = hueMove(fArr[0], 1);
        this.accent3[1] = Math.min(fArr[1] + 0.1f, 0.6f);
        float[] fArr4 = this.neutral1;
        fArr4[0] = fArr[0];
        fArr4[1] = Math.min(fArr[1] + 0.0f, 0.2f);
        float[] fArr5 = this.neutral2;
        fArr5[0] = fArr[0];
        fArr5[1] = Math.min(fArr[1] + 0.0f, 0.2f);
        ColorPalette colorPalette = new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
        this.mColorPalettes.add(colorPalette.getTable());
        return colorPalette;
    }

    private ColorPalette addExpressive(float[] fArr, int i) {
        this.accent1[0] = getExpressiveHue(fArr[0], i);
        this.accent1[1] = Math.min(fArr[1] + 0.0f, 0.7f);
        float[] fArr2 = this.accent2;
        fArr2[0] = fArr[0];
        fArr2[1] = Math.min(fArr[1] + 0.0f, 0.6f);
        this.accent3[0] = hueMove(fArr[0], 1);
        this.accent3[1] = Math.min(fArr[1] + 0.0f, 0.4f);
        float[] fArr3 = this.neutral1;
        fArr3[0] = fArr[0];
        fArr3[1] = Math.min(fArr[1] + 0.0f, 0.3f);
        float[] fArr4 = this.neutral2;
        fArr4[0] = fArr[0];
        fArr4[1] = Math.min(fArr[1] + 0.0f, 0.2f);
        ColorPalette colorPalette = new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2);
        this.mColorPalettes.add(colorPalette.getTable());
        return colorPalette;
    }

    public float getExpressiveHue(float f, int i) {
        float f2;
        float findRatio = findRatio(f, findRange(f));
        int[] iArr = range;
        float hue = getHue((((r0 - 5) + iArr.length) - 1) % (iArr.length - 1), findRatio);
        float hue2 = getHue((((r0 - (-5)) + iArr.length) - 1) % (iArr.length - 1), findRatio);
        Log.v(TAG, "getExpressiveHue : leftHue = " + hue + " rightHue = " + hue2 + " C" + (i + 1) + " case");
        if (hue > hue2) {
            Log.v(TAG, "getExpressiveHue : leftHue(" + hue + ") > c1, c2, c3, c4 > rightHue(" + hue2 + NavigationBarInflaterView.KEY_CODE_END);
            int i2 = 0;
            while (true) {
                float[][] fArr = this.mColorHsl;
                if (i2 >= fArr.length) {
                    break;
                }
                if (i2 != i) {
                    float f3 = fArr[i2][0];
                    if (hue2 < f3 && f3 < hue) {
                        Log.v(TAG, "getExpressiveHue : return seeds C" + (i2 + 1) + " " + f3);
                        return f3;
                    }
                }
                i2++;
            }
        } else {
            Log.v(TAG, "getExpressiveHue : 360 > c1,c2,c3,c4 > rightHue(" + hue2 + ") || 0 < c1,c2,c3,c4 < leftHue(" + hue + NavigationBarInflaterView.KEY_CODE_END);
            int i3 = 0;
            while (true) {
                float[][] fArr2 = this.mColorHsl;
                if (i3 >= fArr2.length) {
                    break;
                }
                if (i3 != i) {
                    f2 = fArr2[i3][0];
                    if ((360.0f > f2 && f2 > hue2) || (hue > f2 && f2 > 0.0f)) {
                        break;
                    }
                }
                i3++;
            }
            Log.v(TAG, "getExpressiveHue : return seeds C" + (i3 + 1) + " " + f2);
            return f2;
        }
        return hueMove(f, -5);
    }
}
