package com.samsung.android.wallpaper.colortheme;

import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import com.android.internal.graphics.ColorUtils;
import com.samsung.android.wallpaper.colortheme.monet.ColorScheme;
import java.lang.reflect.Array;

/* loaded from: classes6.dex */
public class ColorPalette {
    public static final int BOTTOM_DEFAULT = 4;
    public static final int BOTTOM_REVERSED = 8;
    public static final int INDEX_ACCENT1 = 0;
    public static final int LUMINANCE_NUM = 13;
    public static final int MID_DEFAULT = 10;
    public static final int MID_REVERSED = 3;
    public static final int NIO_TEXT_DEFAULT = 10;
    public static final int NIO_TEXT_REVERSED = 3;
    public static final int SATURATION_NUM = 5;
    public static final int TOP_DEFAULT = 3;
    public static final int TOP_REVERSED = 10;
    public static float[] guideIntensity = {100.0f, 99.0f, 95.0f, 90.0f, 80.0f, 70.0f, 60.0f, 50.0f, 40.0f, 30.0f, 20.0f, 10.0f, 0.0f};
    private final int[][] table;

    public ColorPalette(float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float[] fArr5) {
        this.table = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 5, 13);
        generateTable(fArr[0], fArr[1], 0);
        generateTable(fArr2[0], fArr2[1], 1);
        generateTable(fArr3[0], fArr3[1], 2);
        generateTable(fArr4[0], fArr4[1], 3);
        generateTable(fArr5[0], fArr5[1], 4);
    }

    public ColorPalette(ColorScheme colorScheme) {
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 5, 13);
        this.table = iArr;
        int[] array = colorScheme.getAccent1().stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        int[] array2 = colorScheme.getAccent2().stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        int[] array3 = colorScheme.getAccent3().stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        int[] array4 = colorScheme.getNeutral1().stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        int[] array5 = colorScheme.getNeutral2().stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        int[] iArr2 = iArr[0];
        int[] iArr3 = iArr[1];
        int[] iArr4 = iArr[2];
        int[] iArr5 = iArr[3];
        iArr[4][0] = -1;
        iArr5[0] = -1;
        iArr4[0] = -1;
        iArr3[0] = -1;
        iArr2[0] = -1;
        int i = 0;
        while (i < 12) {
            int[][] iArr6 = this.table;
            int i2 = i + 1;
            iArr6[0][i2] = array[i];
            iArr6[1][i2] = array2[i];
            iArr6[2][i2] = array3[i];
            iArr6[3][i2] = array4[i];
            iArr6[4][i2] = array5[i];
            i = i2;
        }
    }

    public int[][] getTable() {
        return this.table;
    }

    public static int[] getnerateSingleTable(float f, float f2) {
        int[] iArr = new int[13];
        float[] fArr = {f, f2, 0.0f};
        float f3 = 1.0f;
        for (int i = 0; i < 13; i++) {
            f3 = searchL(f, f2, f3, guideIntensity[i]);
            fArr[2] = f3;
            iArr[i] = ColorUtils.HSLToColor(fArr);
        }
        return iArr;
    }

    private void generateTable(float f, float f2, int i) {
        float[] fArr = {f, f2, 0.0f};
        float f3 = 1.0f;
        for (int i2 = 0; i2 < 13; i2++) {
            f3 = searchL(f, f2, f3, guideIntensity[i2]);
            fArr[2] = f3;
            this.table[i][i2] = ColorUtils.HSLToColor(fArr);
        }
    }

    private static float searchL(float f, float f2, float f3, float f4) {
        double[] dArr = new double[3];
        int i = 0;
        while (true) {
            float f5 = f3 - (i * 0.001f);
            if (f5 < -0.001d) {
                return 0.0f;
            }
            float max = Math.max(f5, 0.0f);
            ColorUtils.colorToLAB(ColorUtils.HSLToColor(new float[]{f, f2, max}), dArr);
            if (dArr[0] <= f4) {
                return max;
            }
            i++;
        }
    }
}
