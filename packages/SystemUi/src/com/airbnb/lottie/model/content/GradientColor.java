package com.airbnb.lottie.model.content;

import com.airbnb.lottie.utils.GammaEvaluator;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class GradientColor {
    public final int[] colors;
    public final float[] positions;

    public GradientColor(float[] fArr, int[] iArr) {
        this.positions = fArr;
        this.colors = iArr;
    }

    public final GradientColor copyWithPositions(float[] fArr) {
        int evaluate;
        int[] iArr = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            float f = fArr[i];
            float[] fArr2 = this.positions;
            int binarySearch = Arrays.binarySearch(fArr2, f);
            int[] iArr2 = this.colors;
            if (binarySearch >= 0) {
                evaluate = iArr2[binarySearch];
            } else {
                int i2 = -(binarySearch + 1);
                if (i2 == 0) {
                    evaluate = iArr2[0];
                } else if (i2 == iArr2.length - 1) {
                    evaluate = iArr2[iArr2.length - 1];
                } else {
                    int i3 = i2 - 1;
                    float f2 = fArr2[i3];
                    evaluate = GammaEvaluator.evaluate((f - f2) / (fArr2[i2] - f2), iArr2[i3], iArr2[i2]);
                }
            }
            iArr[i] = evaluate;
        }
        return new GradientColor(fArr, iArr);
    }
}
