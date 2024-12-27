package com.android.server.display.color;

import android.animation.TypeEvaluator;
import android.util.Slog;

import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CctEvaluator implements TypeEvaluator {
    public final int mIndexOffset;
    final int[] mSteppedCctsAtOffsetCcts;
    final int[] mStepsAtOffsetCcts;

    public CctEvaluator(int i, int i2, int[] iArr, int[] iArr2) {
        int i3 = (i2 - i) + 1;
        int[] iArr3 = new int[i3];
        this.mStepsAtOffsetCcts = iArr3;
        this.mSteppedCctsAtOffsetCcts = new int[i3];
        this.mIndexOffset = i;
        int length = iArr.length;
        int i4 = 0;
        if (iArr.length != iArr2.length) {
            Slog.e(
                    "CctEvaluator",
                    "Parallel arrays cctRangeMinimums and steps are different lengths; setting step"
                        + " of 1");
            Arrays.fill(iArr3, 1);
            while (true) {
                int[] iArr4 = this.mSteppedCctsAtOffsetCcts;
                if (i4 >= iArr4.length) {
                    return;
                }
                iArr4[i4] = this.mIndexOffset + i4;
                i4++;
            }
        } else {
            if (length != 0) {
                int i5 = Integer.MIN_VALUE;
                int i6 = 0;
                while (i4 < i3) {
                    int i7 = this.mIndexOffset + i4;
                    int i8 = i6 + 1;
                    while (i8 < length && i7 >= iArr[i8]) {
                        int i9 = i8;
                        i8++;
                        i6 = i9;
                    }
                    this.mStepsAtOffsetCcts[i4] = iArr2[i6];
                    if (i5 == Integer.MIN_VALUE || Math.abs(i5 - i7) >= iArr2[i6]) {
                        i5 = i7;
                    }
                    this.mSteppedCctsAtOffsetCcts[i4] = i5;
                    i4++;
                }
                return;
            }
            Slog.e("CctEvaluator", "No cctRangeMinimums or steps are set; setting step of 1");
            Arrays.fill(iArr3, 1);
            while (true) {
                int[] iArr5 = this.mSteppedCctsAtOffsetCcts;
                if (i4 >= iArr5.length) {
                    return;
                }
                iArr5[i4] = this.mIndexOffset + i4;
                i4++;
            }
        }
    }

    @Override // android.animation.TypeEvaluator
    public final Object evaluate(float f, Object obj, Object obj2) {
        Integer num = (Integer) obj;
        int intValue =
                (int) ((f * (((Integer) obj2).intValue() - num.intValue())) + num.intValue());
        int i = intValue - this.mIndexOffset;
        if (i >= 0) {
            int[] iArr = this.mSteppedCctsAtOffsetCcts;
            if (i < iArr.length) {
                return Integer.valueOf(iArr[i]);
            }
        }
        Slog.e(
                "CctEvaluator",
                "steppedCctValueAt: returning same since invalid requested index=" + i);
        return Integer.valueOf(intValue);
    }
}
