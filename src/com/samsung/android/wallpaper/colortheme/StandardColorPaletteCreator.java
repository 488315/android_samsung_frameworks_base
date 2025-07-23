package com.samsung.android.wallpaper.colortheme;

import android.graphics.Color;
import android.util.Log;
import com.android.internal.graphics.ColorUtils;
import com.samsung.android.wallpaper.colortheme.monet.ColorScheme;
import com.samsung.android.wallpaper.colortheme.monet.Style;
import java.lang.reflect.Array;

/* loaded from: classes6.dex */
public class StandardColorPaletteCreator extends ColorPaletteCreator {
    private static final int MAX_RANGE = 19;
    private static final String TAG = "StandardColorPaletteCreator";
    private static final int[] range = {-8, 23, 34, 44, 52, 61, 79, 134, 168, 184, 194, 201, 222, 264, 289, 314, 329, 345, 352};
    float[][] mColorHsl;
    float[] oneColorHsl;
    int[] oneColorIntSeeds;
    float[] twoColorHsl;
    int[] twoColorIntSeeds;
    int[] seedRange = {7, 39, 56, 106, 176, 211, 276, 321};
    int[] twoColorRange = {7, 39, 106, 211};
    String[] seeds = {"#D73B26", "#D99A26", "#D9CD26", "#50D926", "#26D9CD", "#267DD9", "#9126D9", "#D9269A"};
    String[] twoColorSeeds = {"#808080", "#D73B26", "#D99A26", "#50D926", "#267DD9"};
    float[] accent1 = new float[3];
    float[] accent2 = new float[3];
    float[] accent3 = new float[3];
    float[] neutral1 = new float[3];
    float[] neutral2 = new float[3];
    protected final Style[] mBasicColorStyle = {Style.RAINBOW, Style.FRUIT_SALAD};

    @Override // com.samsung.android.wallpaper.colortheme.ColorPaletteCreator
    public void generateColorPalette(boolean z) {
        String[] strArr;
        this.mColorPalettes.clear();
        if (z) {
            int i = 0;
            while (true) {
                strArr = this.seeds;
                if (i >= strArr.length) {
                    break;
                }
                this.mColorPalettes.add(new ColorPalette(new ColorScheme(this.mSeedColors[i], false, Style.RAINBOW)).getTable());
                i++;
            }
            for (int length = strArr.length; length < this.seeds.length + this.twoColorSeeds.length; length++) {
                this.mColorPalettes.add(new ColorPalette(new ColorScheme(this.mSeedColors[length], false, Style.FRUIT_SALAD)).getTable());
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
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = this.seeds;
            if (i2 >= strArr.length) {
                break;
            }
            this.oneColorIntSeeds[i2] = Color.parseColor(strArr[i2]);
            i2++;
        }
        int[] iArr = this.oneColorIntSeeds;
        if (iArr == null || iArr.length <= 0) {
            return;
        }
        this.mColorHsl = (float[][]) Array.newInstance((Class<?>) Float.TYPE, iArr.length, 3);
        while (true) {
            int[] iArr2 = this.oneColorIntSeeds;
            if (i >= iArr2.length) {
                return;
            }
            ColorUtils.colorToHSL(iArr2[i], this.mColorHsl[i]);
            i++;
        }
    }

    private void setTwoColors() {
        this.twoColorIntSeeds = new int[this.twoColorSeeds.length];
        int i = 0;
        while (true) {
            String[] strArr = this.twoColorSeeds;
            if (i >= strArr.length) {
                break;
            }
            this.twoColorIntSeeds[i] = Color.parseColor(strArr[i]);
            i++;
        }
        int[] iArr = this.twoColorIntSeeds;
        if (iArr == null || iArr.length <= 0) {
            return;
        }
        this.mColorHsl = (float[][]) Array.newInstance((Class<?>) Float.TYPE, iArr.length, 3);
        int i2 = 0;
        while (true) {
            int[] iArr2 = this.twoColorIntSeeds;
            if (i2 < iArr2.length) {
                ColorUtils.colorToHSL(iArr2[i2], this.mColorHsl[i2]);
                i2++;
            } else {
                this.mSeedColors = new int[this.oneColorIntSeeds.length + iArr2.length];
                System.arraycopy(this.oneColorIntSeeds, 0, this.mSeedColors, 0, this.oneColorIntSeeds.length);
                System.arraycopy(this.twoColorIntSeeds, 0, this.mSeedColors, this.oneColorIntSeeds.length, this.twoColorIntSeeds.length);
                return;
            }
        }
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
            addOneColorPalette(this.mColorHsl[i]);
        }
        setTwoColors();
        Log.d(TAG, "populateStyles : seeds length = " + this.twoColorSeeds.length + " addTowColorPalette");
        for (int i2 = 0; i2 < this.twoColorSeeds.length; i2++) {
            addTwoColorPalette(this.mColorHsl[i2]);
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

    static float getHue(int i) {
        float f = range[i];
        return f < 0.0f ? f + 360.0f : f > 360.0f ? f - 360.0f : f;
    }

    static float hueMove(float f, int i) {
        return getHue((findRange(Math.round(f)) + i) % range.length);
    }

    private void addOneColorPalette(float[] fArr) {
        float[] fArr2 = this.accent1;
        fArr2[0] = fArr[0];
        fArr2[1] = 0.7f;
        float[] fArr3 = this.accent2;
        fArr3[0] = fArr[0];
        fArr3[1] = 0.4f;
        this.accent3[0] = hueMove(fArr[0], 1);
        this.accent3[1] = 0.5f;
        float[] fArr4 = this.neutral1;
        fArr4[0] = fArr[0];
        fArr4[1] = 0.0f;
        float[] fArr5 = this.neutral2;
        fArr5[0] = fArr[0];
        fArr5[1] = 0.0f;
        this.mColorPalettes.add(new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2).getTable());
    }

    private void addTwoColorPalette(float[] fArr) {
        if (isGrayColor(fArr)) {
            addGrayColorPalette();
            return;
        }
        float[] fArr2 = this.accent1;
        fArr2[0] = fArr[0];
        fArr2[1] = 0.8f;
        this.accent2[0] = hueMove(fArr[0], 3);
        this.accent2[1] = 0.6f;
        this.accent3[0] = hueMove(fArr[0], 3);
        this.accent3[1] = 0.4f;
        this.neutral1[0] = hueMove(fArr[0], 3);
        this.neutral1[1] = 0.15f;
        this.neutral2[0] = hueMove(fArr[0], 3);
        this.neutral2[1] = 0.15f;
        this.mColorPalettes.add(new ColorPalette(this.accent1, this.accent2, this.accent3, this.neutral1, this.neutral2).getTable());
    }

    private void addGrayColorPalette() {
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
    }

    static boolean isGrayColor(float[] fArr) {
        return fArr[1] <= 0.01f;
    }
}
