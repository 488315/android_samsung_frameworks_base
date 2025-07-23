package com.samsung.android.wallpaper.legibilitycolors;

import com.samsung.android.wallpaper.legibilitycolors.utils.IUXColorUtils;

/* loaded from: classes6.dex */
public class ColorHSV {
    int avgColor = 0;
    float avgH = 0.0f;
    float avgS = 0.0f;
    float avgV = 0.0f;

    public static void colorToHSV(int i, float[] fArr) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        int i2 = 16777215 & i;
        int i3 = i2 >> 16;
        int i4 = (i2 >> 8) & 255;
        int i5 = i & 255;
        float min = Math.min(Math.min(i3, i4), i5);
        float max = Math.max(Math.max(i3, i4), i5);
        float f6 = max - min;
        float f7 = 0.0f;
        if (max != 0.0f) {
            if (f6 == 0.0f) {
                f2 = 0.0f;
            } else {
                f2 = f6 / max;
                if (i3 == max) {
                    f5 = (i4 - i5) / f6;
                } else {
                    if (i4 == max) {
                        f3 = (i5 - i3) / f6;
                        f4 = 2.0f;
                    } else {
                        f3 = (i3 - i4) / f6;
                        f4 = 4.0f;
                    }
                    f5 = f3 + f4;
                }
                float f8 = f5 * 60.0f;
                if (f8 < 0.0f) {
                    f8 += 360.0f;
                }
                f7 = f8;
            }
            f = max / 255.0f;
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        fArr[0] = f7;
        fArr[1] = f2;
        fArr[2] = f;
    }

    public void calcAvgColor(int[] iArr) {
        int averageColor = IUXColorUtils.getAverageColor(iArr);
        this.avgColor = averageColor;
        float[] fArr = new float[3];
        colorToHSV(averageColor, fArr);
        this.avgH = fArr[0];
        this.avgS = fArr[1];
        this.avgV = fArr[2];
    }

    public int getAvgColor() {
        return this.avgColor;
    }

    public float getAvgH() {
        return this.avgH;
    }

    public float getAvgS() {
        return this.avgS;
    }

    public float getAvgV() {
        return this.avgV;
    }

    public void reset() {
        this.avgH = 0.0f;
        this.avgS = 0.0f;
        this.avgV = 0.0f;
    }
}
