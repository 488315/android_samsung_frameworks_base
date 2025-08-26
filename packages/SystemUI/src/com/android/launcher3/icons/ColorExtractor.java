package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.SparseArray;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class ColorExtractor {
    static {
        new ColorExtractor();
    }

    private ColorExtractor() {
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a3 A[LOOP:1: B:17:0x0060->B:33:0x00a3, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00aa A[EDGE_INSN: B:54:0x00aa->B:34:0x00aa BREAK  A[LOOP:1: B:17:0x0060->B:33:0x00a3], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final int findDominantColorByHue(Bitmap bitmap) {
        char c;
        int i;
        char c2;
        int i2;
        int i3;
        int i4;
        char c3;
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int iSqrt = (int) Math.sqrt((height * width) / 20.0d);
        if (iSqrt < 1) {
            iSqrt = 1;
        }
        float[] fArr = new float[3];
        float[] fArr2 = new float[360];
        char c4 = 20;
        int[] iArr = new int[20];
        int i5 = 0;
        IntProgression intProgressionStep = RangesKt___RangesKt.step(RangesKt___RangesKt.until(0, height), iSqrt);
        int i6 = intProgressionStep.first;
        int i7 = intProgressionStep.last;
        int i8 = intProgressionStep.step;
        int i9 = -1;
        if ((i8 <= 0 || i6 > i7) && (i8 >= 0 || i7 > i6)) {
            c = 1;
            i = 0;
            c2 = 2;
            i2 = -16777216;
            i3 = 0;
        } else {
            c = 1;
            int i10 = 0;
            float f = -1.0f;
            while (true) {
                IntProgression intProgressionStep2 = RangesKt___RangesKt.step(RangesKt___RangesKt.until(i5, width), iSqrt);
                i = i5;
                int i11 = intProgressionStep2.first;
                c2 = 2;
                int i12 = intProgressionStep2.last;
                int i13 = intProgressionStep2.step;
                if ((i13 <= 0 || i11 > i12) && (i13 >= 0 || i12 > i11)) {
                    i4 = i8;
                    c3 = c4;
                    i2 = -16777216;
                } else {
                    int i14 = i10;
                    i2 = -16777216;
                    while (true) {
                        int pixel = bitmap.getPixel(i11, i6);
                        if (((pixel >> 24) & 255) < 128) {
                            i4 = i8;
                            c3 = 20;
                            if (i11 == i12) {
                                break;
                            }
                            i11 += i13;
                            i8 = i4;
                        } else {
                            int i15 = pixel | (-16777216);
                            Color.colorToHSV(i15, fArr);
                            int i16 = (int) fArr[i];
                            if (i16 >= 0) {
                                i4 = i8;
                                if (i16 < 360) {
                                    c3 = 20;
                                    if (i14 < 20) {
                                        iArr[i14] = i15;
                                        i14++;
                                    }
                                    float f2 = fArr2[i16] + (fArr[1] * fArr[2]);
                                    fArr2[i16] = f2;
                                    if (f2 > f) {
                                        i9 = i16;
                                        f = f2;
                                    }
                                }
                                if (i11 == i12) {
                                }
                            }
                            c3 = 20;
                            if (i11 == i12) {
                            }
                        }
                    }
                    i10 = i14;
                }
                if (i6 == i7) {
                    break;
                }
                i6 += i4;
                c4 = c3;
                i5 = i;
                i8 = i4;
            }
            i3 = i10;
        }
        SparseArray sparseArray = new SparseArray();
        int i17 = i2;
        float f3 = -1.0f;
        for (int i18 = i; i18 < i3; i18++) {
            int i19 = iArr[i18];
            Color.colorToHSV(i19, fArr);
            if (((int) fArr[i]) == i9) {
                float f4 = fArr[c];
                float f5 = fArr[c2];
                int i20 = ((int) (100 * f4)) + ((int) (10000 * f5));
                float fFloatValue = f4 * f5;
                Float f6 = (Float) sparseArray.get(i20);
                if (f6 != null) {
                    fFloatValue += f6.floatValue();
                }
                sparseArray.put(i20, Float.valueOf(fFloatValue));
                if (fFloatValue > f3) {
                    i17 = i19;
                    f3 = fFloatValue;
                }
            }
        }
        return i17;
    }
}
