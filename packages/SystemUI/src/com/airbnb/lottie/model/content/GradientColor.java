package com.airbnb.lottie.model.content;

import com.airbnb.lottie.utils.GammaEvaluator;
import java.util.Arrays;

/* loaded from: classes.dex */
public class GradientColor {
    public final int[] colors;
    public final float[] positions;

    public GradientColor(float[] fArr, int[] iArr) {
        this.positions = fArr;
        this.colors = iArr;
    }

    public final GradientColor copyWithPositions(float[] fArr) {
        int iEvaluate;
        int[] iArr = new int[fArr.length];
        for (int i = 0; i < fArr.length; i++) {
            float f = fArr[i];
            float[] fArr2 = this.positions;
            int iBinarySearch = Arrays.binarySearch(fArr2, f);
            int[] iArr2 = this.colors;
            if (iBinarySearch >= 0) {
                iEvaluate = iArr2[iBinarySearch];
            } else {
                int i2 = -(iBinarySearch + 1);
                if (i2 == 0) {
                    iEvaluate = iArr2[0];
                } else if (i2 == iArr2.length - 1) {
                    iEvaluate = iArr2[iArr2.length - 1];
                } else {
                    int i3 = i2 - 1;
                    float f2 = fArr2[i3];
                    iEvaluate = GammaEvaluator.evaluate((f - f2) / (fArr2[i2] - f2), iArr2[i3], iArr2[i2]);
                }
            }
            iArr[i] = iEvaluate;
        }
        return new GradientColor(fArr, iArr);
    }
}
