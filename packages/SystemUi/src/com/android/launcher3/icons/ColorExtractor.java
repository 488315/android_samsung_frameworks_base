package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseArray;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ColorExtractor {
    public final float[] mTmpHsv = new float[3];
    public final float[] mTmpHueScoreHistogram = new float[360];
    public final int[] mTmpPixels = new int[20];
    public final SparseArray mTmpRgbScores = new SparseArray();

    public final int findDominantColorByHue(Bitmap bitmap) {
        int i;
        char c;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        char c2 = 20;
        int sqrt = (int) Math.sqrt((height * width) / 20);
        if (sqrt < 1) {
            sqrt = 1;
        }
        float[] fArr = this.mTmpHsv;
        Arrays.fill(fArr, 0.0f);
        float[] fArr2 = this.mTmpHueScoreHistogram;
        Arrays.fill(fArr2, 0.0f);
        int[] iArr = this.mTmpPixels;
        int i2 = 0;
        Arrays.fill(iArr, 0);
        int i3 = -1;
        int i4 = 0;
        int i5 = 0;
        float f = -1.0f;
        while (true) {
            i = EmergencyPhoneWidget.BG_COLOR;
            if (i4 >= height) {
                break;
            }
            int i6 = i2;
            while (i6 < width) {
                int pixel = bitmap.getPixel(i6, i4);
                if (((pixel >> 24) & 255) >= 128) {
                    int i7 = pixel | EmergencyPhoneWidget.BG_COLOR;
                    Color.colorToHSV(i7, fArr);
                    int i8 = (int) fArr[i2];
                    if (i8 >= 0 && i8 < fArr2.length) {
                        c = 20;
                        if (i5 < 20) {
                            iArr[i5] = i7;
                            i5++;
                        }
                        float f2 = fArr2[i8] + (fArr[1] * fArr[2]);
                        fArr2[i8] = f2;
                        if (f2 > f) {
                            f = f2;
                            i3 = i8;
                        }
                        i6 += sqrt;
                        c2 = c;
                        i2 = 0;
                    }
                }
                c = 20;
                i6 += sqrt;
                c2 = c;
                i2 = 0;
            }
            i4 += sqrt;
            i2 = 0;
        }
        SparseArray sparseArray = this.mTmpRgbScores;
        sparseArray.clear();
        float f3 = -1.0f;
        for (int i9 = 0; i9 < i5; i9++) {
            int i10 = iArr[i9];
            Color.colorToHSV(i10, fArr);
            if (((int) fArr[0]) == i3) {
                float f4 = fArr[1];
                float f5 = fArr[2];
                int i11 = ((int) (100.0f * f4)) + ((int) (10000.0f * f5));
                float f6 = f4 * f5;
                Float f7 = (Float) sparseArray.get(i11);
                if (f7 != null) {
                    f6 += f7.floatValue();
                }
                sparseArray.put(i11, Float.valueOf(f6));
                if (f6 > f3) {
                    i = i10;
                    f3 = f6;
                }
            }
        }
        return i;
    }
}
