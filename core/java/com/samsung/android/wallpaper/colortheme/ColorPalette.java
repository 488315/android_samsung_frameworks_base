package com.samsung.android.wallpaper.colortheme;

import android.app.admin.PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2;
import com.android.internal.graphics.ColorUtils;
import com.samsung.android.wallpaper.colortheme.monet.ColorScheme;
import java.lang.reflect.Array;

/* loaded from: classes5.dex */
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

    public ColorPalette(float[] accent1, float[] accent2, float[] accent3, float[] neutral1, float[] neutral2) {
        this.table = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 5, 13);
        generateTable(accent1[0], accent1[1], 0);
        generateTable(accent2[0], accent2[1], 1);
        generateTable(accent3[0], accent3[1], 2);
        generateTable(neutral1[0], neutral1[1], 3);
        generateTable(neutral2[0], neutral2[1], 4);
    }

    public ColorPalette(ColorScheme colorScheme) {
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, 5, 13);
        this.table = iArr;
        int[] a1 = colorScheme.getAccent1().stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        int[] a2 = colorScheme.getAccent2().stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        int[] a3 = colorScheme.getAccent3().stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        int[] n1 = colorScheme.getNeutral1().stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        int[] n2 = colorScheme.getNeutral2().stream().mapToInt(new PreferentialNetworkServiceConfig$$ExternalSyntheticLambda2()).toArray();
        int[] iArr2 = iArr[0];
        int[] iArr3 = iArr[1];
        int[] iArr4 = iArr[2];
        int[] iArr5 = iArr[3];
        iArr[4][0] = -1;
        iArr5[0] = -1;
        iArr4[0] = -1;
        iArr3[0] = -1;
        iArr2[0] = -1;
        for (int i = 0; i < 12; i++) {
            int[][] iArr6 = this.table;
            iArr6[0][i + 1] = a1[i];
            iArr6[1][i + 1] = a2[i];
            iArr6[2][i + 1] = a3[i];
            iArr6[3][i + 1] = n1[i];
            iArr6[4][i + 1] = n2[i];
        }
    }

    public int[][] getTable() {
        return this.table;
    }

    public static int[] getnerateSingleTable(float h, float s) {
        int[] singleTable = new int[13];
        float lVal = 1.0f;
        float[] hsl = {h, s, 0.0f};
        for (int i = 0; i < 13; i++) {
            lVal = searchL(h, s, lVal, guideIntensity[i]);
            hsl[2] = lVal;
            singleTable[i] = ColorUtils.HSLToColor(hsl);
        }
        return singleTable;
    }

    private void generateTable(float h, float s, int idx) {
        float lVal = 1.0f;
        float[] hsl = {h, s, 0.0f};
        for (int i = 0; i < 13; i++) {
            lVal = searchL(h, s, lVal, guideIntensity[i]);
            hsl[2] = lVal;
            this.table[idx][i] = ColorUtils.HSLToColor(hsl);
        }
    }

    private static float searchL(float hue, float sat, float startL, float destIntensity) {
        double[] lab = new double[3];
        for (int count = 0; startL - (count * 0.001f) >= -0.001d; count++) {
            float l = Math.max(startL - (count * 0.001f), 0.0f);
            float[] hsl = {hue, sat, l};
            int color = ColorUtils.HSLToColor(hsl);
            ColorUtils.colorToLAB(color, lab);
            if (lab[0] <= destIntensity) {
                return l;
            }
        }
        return 0.0f;
    }
}
