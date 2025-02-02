package com.airbnb.lottie.model.content;

import androidx.constraintlayout.core.widgets.analyzer.RunGroup$$ExternalSyntheticOutline0;
import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.utils.MiscUtils;

/* loaded from: classes.dex */
public final class GradientColor {
    private final int[] colors;
    private final float[] positions;

    public GradientColor(float[] fArr, int[] iArr) {
        this.positions = fArr;
        this.colors = iArr;
    }

    public final int[] getColors() {
        return this.colors;
    }

    public final float[] getPositions() {
        return this.positions;
    }

    public final int getSize() {
        return this.colors.length;
    }

    public final void lerp(GradientColor gradientColor, GradientColor gradientColor2, float f) {
        int length = gradientColor.colors.length;
        int length2 = gradientColor2.colors.length;
        int[] iArr = gradientColor.colors;
        int[] iArr2 = gradientColor2.colors;
        if (length != length2) {
            throw new IllegalArgumentException("Cannot interpolate between gradients. Lengths vary (" + iArr.length + " vs " + iArr2.length + ")");
        }
        for (int i = 0; i < iArr.length; i++) {
            float f2 = gradientColor.positions[i];
            float f3 = gradientColor2.positions[i];
            int i2 = MiscUtils.$r8$clinit;
            this.positions[i] = RunGroup$$ExternalSyntheticOutline0.m3m(f3, f2, f, f2);
            this.colors[i] = GammaEvaluator.evaluate(f, iArr[i], iArr2[i]);
        }
    }
}
