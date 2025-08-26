package com.samsung.android.wallpaper.colortheme;

import com.android.internal.graphics.ColorUtils;
import com.samsung.android.wallpaper.colortheme.monet.ColorScheme;
import com.samsung.android.wallpaper.colortheme.monet.Style;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

/* loaded from: classes6.dex */
public class ColorPaletteCreator {
    private static final float ACCENT1_SAT_DELTA = 0.3f;
    private static final float ACCENT1_SAT_MAX = 0.8f;
    private static final float ACCENT2_SAT_MAX = 0.4f;
    private static final float ACCENT3_SAT_DELTA = 0.1f;
    private static final float ACCENT3_SAT_MAX = 0.6f;
    private static final int GRAY_HUE_PRESET1 = 0;
    private static final int GRAY_HUE_PRESET2 = 240;
    private static final float GRAY_SAT_PRESET1 = 0.0f;
    private static final float GRAY_SAT_PRESET2 = 0.05f;
    private static final int MAX_RANGE = 17;
    private static final float NEUTRAL_SAT_MAX = 0.15f;
    private static final int[] hueRange = {8, 22, 40, 52, 60, 81, 139, 169, 200, 219, 256, 279, 318, 337, 348, 356, 361};
    private static final int[] range = {-4, 8, 22, 40, 52, 60, 81, 139, 169, 200, 219, 256, 279, 318, 337, 348, 356};
    protected int[] mSeedColors;
    protected final List<int[][]> mColorPalettes = new ArrayList();
    protected final Style[] mWallpaperColorStyles = {Style.TONAL_SPOT, Style.SPRITZ, Style.VIBRANT, Style.EXPRESSIVE};

    float comp(float f) {
        float f2 = f + 180.0f;
        return f2 > 360.0f ? f2 - 360.0f : f2;
    }

    public List<int[][]> getColorPalettes() {
        return this.mColorPalettes;
    }

    public int[] getSeedColors() {
        return this.mSeedColors;
    }

    public void setColors(float[][] fArr) {
        if (fArr == null || fArr.length <= 0) {
            return;
        }
        this.mSeedColors = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            this.mSeedColors[i] = ColorUtils.HSLToColor(fArr[i]);
        }
    }

    public void setColors(int[] iArr) {
        this.mSeedColors = iArr;
    }

    public void generateColorPalette() {
        this.mColorPalettes.clear();
        populateStyles();
    }

    public void generateColorPalette(boolean z) {
        this.mColorPalettes.clear();
        if (z) {
            for (int i : this.mSeedColors) {
                for (Style style : this.mWallpaperColorStyles) {
                    this.mColorPalettes.add(new ColorPalette(new ColorScheme(i, false, style)).getTable());
                }
            }
            return;
        }
        populateStyles();
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
            if (r2[16] <= f) {
                return 0;
            }
            if (f < r2[i]) {
                return i - 1;
            }
            i++;
        }
    }

    static float findRatio(float f, int i) {
        return (f - range[i]) / (r0[i + 1] - r1);
    }

    static float getHue(int i, float f) {
        float f2 = range[i] + ((r0[i + 1] - r1) * f);
        return f2 < 0.0f ? f2 + 360.0f : f2 > 360.0f ? f2 - 360.0f : f2;
    }

    static float hueMove(float f, int i) {
        int length;
        int iFindRange = findRange(f);
        float fFindRatio = findRatio(f, iFindRange);
        if (fFindRatio > 0.5f) {
            length = (iFindRange + i) % (range.length - 1);
        } else {
            int i2 = iFindRange - i;
            int[] iArr = range;
            length = ((i2 + iArr.length) - 1) % (iArr.length - 1);
        }
        return getHue(length, fFindRatio);
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
        int[] iArr = this.mSeedColors;
        if (iArr == null || iArr.length <= 0) {
            return;
        }
        float[] fArr = new float[3];
        float[] fArr2 = new float[3];
        float[] fArr3 = new float[3];
        float[] fArr4 = new float[3];
        float[] fArr5 = new float[3];
        float[][] fArr6 = (float[][]) Array.newInstance((Class<?>) Float.TYPE, iArr.length, 3);
        int i = 0;
        while (true) {
            int[] iArr2 = this.mSeedColors;
            if (i >= iArr2.length) {
                break;
            }
            ColorUtils.colorToHSL(iArr2[i], fArr6[i]);
            i++;
        }
        if (isGrayImage(fArr6)) {
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            fArr2[0] = 0.0f;
            fArr2[1] = 0.0f;
            fArr3[0] = 0.0f;
            fArr3[1] = 0.0f;
            fArr4[0] = 0.0f;
            fArr4[1] = 0.0f;
            fArr5[0] = 0.0f;
            fArr5[1] = 0.0f;
            this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
            fArr[0] = 0.0f;
            fArr[1] = 0.05f;
            fArr2[0] = 0.0f;
            fArr2[1] = 0.05f;
            fArr3[0] = 0.0f;
            fArr3[1] = 0.05f;
            fArr4[0] = 0.0f;
            fArr4[1] = 0.05f;
            fArr5[0] = 0.0f;
            fArr5[1] = 0.05f;
            this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
            fArr[0] = 240.0f;
            fArr[1] = 0.05f;
            fArr2[0] = 240.0f;
            fArr2[1] = 0.05f;
            fArr3[0] = 240.0f;
            fArr3[1] = 0.05f;
            fArr4[0] = 240.0f;
            fArr4[1] = 0.05f;
            fArr5[0] = 240.0f;
            fArr5[1] = 0.05f;
            this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
            return;
        }
        int[] iArr3 = this.mSeedColors;
        if (iArr3.length == 1) {
            float[] fArr7 = fArr6[0];
            fArr[0] = fArr7[0];
            fArr2[0] = hueMove(fArr7[0], 1);
            fArr3[0] = comp(fArr6[0][0]);
            float[] fArr8 = fArr6[0];
            fArr4[0] = fArr8[0];
            fArr5[0] = comp(fArr8[0]);
            fArr[1] = Math.min(fArr6[0][1] + ACCENT1_SAT_DELTA, 0.8f);
            fArr2[1] = Math.min(fArr6[0][1], 0.4f);
            fArr3[1] = Math.min(fArr6[0][1] + 0.1f, 0.6f);
            fArr4[1] = Math.min(fArr6[0][1], NEUTRAL_SAT_MAX);
            fArr5[1] = Math.min(fArr6[0][1], NEUTRAL_SAT_MAX);
            this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
            fArr[0] = hueMove(fArr6[0][0], 1);
            fArr2[0] = hueMove(fArr6[0][0], 2);
            fArr3[0] = comp(hueMove(fArr6[0][0], 1));
            fArr4[0] = hueMove(fArr6[0][0], 1);
            fArr5[0] = comp(hueMove(fArr6[0][0], 1));
            this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
            float[] fArr9 = fArr6[0];
            fArr[0] = fArr9[0];
            fArr2[0] = comp(fArr9[0]);
            fArr3[0] = hueMove(fArr6[0][0], 1);
            float[] fArr10 = fArr6[0];
            fArr4[0] = fArr10[0];
            fArr5[0] = comp(fArr10[0]);
            this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
            return;
        }
        if (iArr3.length == 2) {
            float[] fArr11 = fArr6[0];
            fArr[0] = fArr11[0];
            fArr2[0] = hueMove(fArr11[0], 1);
            float[] fArr12 = fArr6[1];
            fArr3[0] = fArr12[0];
            float[] fArr13 = fArr6[0];
            fArr4[0] = fArr13[0];
            fArr5[0] = fArr12[0];
            fArr[1] = Math.min(fArr13[1] + ACCENT1_SAT_DELTA, 0.8f);
            fArr2[1] = Math.min(fArr6[0][1], 0.4f);
            fArr3[1] = Math.min(fArr6[1][1] + 0.1f, 0.6f);
            fArr4[1] = Math.min(fArr6[0][1], NEUTRAL_SAT_MAX);
            fArr5[1] = Math.min(fArr6[1][1], NEUTRAL_SAT_MAX);
            this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
            float[] fArr14 = fArr6[1];
            fArr[0] = fArr14[0];
            fArr2[0] = hueMove(fArr14[0], 1);
            float[] fArr15 = fArr6[0];
            fArr3[0] = fArr15[0];
            float[] fArr16 = fArr6[1];
            fArr4[0] = fArr16[0];
            fArr5[0] = fArr15[0];
            fArr[1] = Math.min(fArr16[1] + ACCENT1_SAT_DELTA, 0.8f);
            fArr2[1] = Math.min(fArr6[1][1], 0.4f);
            fArr3[1] = Math.min(fArr6[0][1] + 0.1f, 0.6f);
            fArr4[1] = Math.min(fArr6[1][1], NEUTRAL_SAT_MAX);
            fArr5[1] = Math.min(fArr6[0][1], NEUTRAL_SAT_MAX);
            this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
            fArr[0] = hueMove(fArr6[0][0], 1);
            fArr2[0] = hueMove(fArr6[0][0], 2);
            fArr3[0] = fArr6[1][0];
            fArr4[0] = hueMove(fArr6[0][0], 1);
            fArr5[0] = fArr6[1][0];
            fArr[1] = Math.min(fArr6[0][1] + ACCENT1_SAT_DELTA, 0.8f);
            fArr2[1] = Math.min(fArr6[0][1], 0.4f);
            fArr3[1] = Math.min(fArr6[1][1] + 0.1f, 0.6f);
            fArr4[1] = Math.min(fArr6[0][1], NEUTRAL_SAT_MAX);
            fArr5[1] = Math.min(fArr6[1][1], NEUTRAL_SAT_MAX);
            this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
            fArr[0] = fArr6[0][0];
            float[] fArr17 = fArr6[1];
            fArr2[0] = fArr17[0];
            fArr3[0] = hueMove(fArr17[0], 1);
            float[] fArr18 = fArr6[0];
            fArr4[0] = fArr18[0];
            fArr5[0] = fArr6[1][0];
            fArr[1] = Math.min(fArr18[1] + ACCENT1_SAT_DELTA, 0.8f);
            fArr2[1] = Math.min(fArr6[1][1], 0.4f);
            fArr3[1] = Math.min(fArr6[1][1] + 0.1f, 0.6f);
            fArr4[1] = Math.min(fArr6[0][1], NEUTRAL_SAT_MAX);
            fArr5[1] = Math.min(fArr6[1][1], NEUTRAL_SAT_MAX);
            this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
            return;
        }
        float[] fArr19 = fArr6[0];
        fArr[0] = fArr19[0];
        fArr2[0] = hueMove(fArr19[0], 1);
        float[] fArr20 = fArr6[1];
        fArr3[0] = fArr20[0];
        float[] fArr21 = fArr6[0];
        fArr4[0] = fArr21[0];
        fArr5[0] = fArr20[0];
        fArr[1] = Math.min(fArr21[1] + ACCENT1_SAT_DELTA, 0.8f);
        fArr2[1] = Math.min(fArr6[0][1], 0.4f);
        fArr3[1] = Math.min(fArr6[1][1] + 0.1f, 0.6f);
        fArr4[1] = Math.min(fArr6[0][1], NEUTRAL_SAT_MAX);
        fArr5[1] = Math.min(fArr6[1][1], NEUTRAL_SAT_MAX);
        this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
        float[] fArr22 = fArr6[1];
        fArr[0] = fArr22[0];
        fArr2[0] = hueMove(fArr22[0], 1);
        float[] fArr23 = fArr6[0];
        fArr3[0] = fArr23[0];
        float[] fArr24 = fArr6[1];
        fArr4[0] = fArr24[0];
        fArr5[0] = fArr23[0];
        fArr[1] = Math.min(fArr24[1] + ACCENT1_SAT_DELTA, 0.8f);
        fArr2[1] = Math.min(fArr6[1][1], 0.4f);
        fArr3[1] = Math.min(fArr6[0][1] + 0.1f, 0.6f);
        fArr4[1] = Math.min(fArr6[1][1], NEUTRAL_SAT_MAX);
        fArr5[1] = Math.min(fArr6[0][1], NEUTRAL_SAT_MAX);
        this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
        float[] fArr25 = fArr6[2];
        fArr[0] = fArr25[0];
        fArr2[0] = hueMove(fArr25[0], 1);
        float[] fArr26 = fArr6[0];
        fArr3[0] = fArr26[0];
        float[] fArr27 = fArr6[2];
        fArr4[0] = fArr27[0];
        fArr5[0] = fArr26[0];
        fArr[1] = Math.min(fArr27[1] + ACCENT1_SAT_DELTA, 0.8f);
        fArr2[1] = Math.min(fArr6[2][1], 0.4f);
        fArr3[1] = Math.min(fArr6[0][1] + 0.1f, 0.6f);
        fArr4[1] = Math.min(fArr6[2][1], NEUTRAL_SAT_MAX);
        fArr5[1] = Math.min(fArr6[0][1], NEUTRAL_SAT_MAX);
        this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
        float[] fArr28 = fArr6[0];
        fArr[0] = fArr28[0];
        float[] fArr29 = fArr6[1];
        fArr2[0] = fArr29[0];
        fArr3[0] = fArr6[2][0];
        fArr4[0] = fArr28[0];
        fArr5[0] = fArr29[0];
        fArr[1] = Math.min(fArr28[1] + ACCENT1_SAT_DELTA, 0.8f);
        fArr2[1] = Math.min(fArr6[1][1], 0.4f);
        fArr3[1] = Math.min(fArr6[2][1] + 0.1f, 0.6f);
        fArr4[1] = Math.min(fArr6[0][1], NEUTRAL_SAT_MAX);
        fArr5[1] = Math.min(fArr6[1][1], NEUTRAL_SAT_MAX);
        this.mColorPalettes.add(new ColorPalette(fArr, fArr2, fArr3, fArr4, fArr5).getTable());
    }

    public static int[] converAccent1ToSeedColors(int[] iArr) {
        if (iArr == null || iArr.length <= 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        float[] fArr = new float[3];
        float[][] fArr2 = (float[][]) Array.newInstance((Class<?>) Float.TYPE, iArr.length, 3);
        for (int i = 0; i < iArr.length; i++) {
            ColorUtils.colorToHSL(iArr[i], fArr2[i]);
        }
        if (isGrayImage(fArr2)) {
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            fArr[2] = 0.5f;
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
            fArr[0] = 0.0f;
            fArr[1] = 0.05f;
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
            fArr[0] = 240.0f;
            fArr[1] = 0.05f;
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
        } else if (iArr.length == 1) {
            float[] fArr3 = fArr2[0];
            fArr[0] = fArr3[0];
            fArr[1] = Math.min(fArr3[1] + ACCENT1_SAT_DELTA, 0.8f);
            fArr[2] = 0.5f;
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
            fArr[0] = hueMove(fArr2[0][0], 1);
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
            fArr[0] = fArr2[0][0];
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
        } else if (iArr.length == 2) {
            float[] fArr4 = fArr2[0];
            fArr[0] = fArr4[0];
            fArr[1] = Math.min(fArr4[1] + ACCENT1_SAT_DELTA, 0.8f);
            fArr[2] = 0.5f;
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
            float[] fArr5 = fArr2[1];
            fArr[0] = fArr5[0];
            fArr[1] = Math.min(fArr5[1] + ACCENT1_SAT_DELTA, 0.8f);
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
            fArr[0] = hueMove(fArr2[0][0], 1);
            fArr[1] = Math.min(fArr2[0][1] + ACCENT1_SAT_DELTA, 0.8f);
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
            float[] fArr6 = fArr2[0];
            fArr[0] = fArr6[0];
            fArr[1] = Math.min(fArr6[1] + ACCENT1_SAT_DELTA, 0.8f);
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
        } else {
            float[] fArr7 = fArr2[0];
            fArr[0] = fArr7[0];
            fArr[1] = Math.min(fArr7[1] + ACCENT1_SAT_DELTA, 0.8f);
            fArr[2] = 0.5f;
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
            float[] fArr8 = fArr2[1];
            fArr[0] = fArr8[0];
            fArr[1] = Math.min(fArr8[1] + ACCENT1_SAT_DELTA, 0.8f);
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
            float[] fArr9 = fArr2[2];
            fArr[0] = fArr9[0];
            fArr[1] = Math.min(fArr9[1] + ACCENT1_SAT_DELTA, 0.8f);
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
            float[] fArr10 = fArr2[0];
            fArr[0] = fArr10[0];
            fArr[1] = Math.min(fArr10[1] + ACCENT1_SAT_DELTA, 0.8f);
            arrayList.add(Integer.valueOf(ColorUtils.HSLToColor(fArr)));
        }
        return arrayList.stream().mapToInt(new ToIntFunction() { // from class: com.samsung.android.wallpaper.colortheme.ColorPaletteCreator$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Integer) obj).intValue();
            }
        }).toArray();
    }
}
