package androidx.interpolator.view.animation;

import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LookupTableInterpolator {
    private LookupTableInterpolator() {
    }

    public static float interpolate(float f, float f2, float[] fArr) {
        if (f2 >= 1.0f) {
            return 1.0f;
        }
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        int min = Math.min((int) ((fArr.length - 1) * f2), fArr.length - 2);
        float f3 = (f2 - (min * f)) / f;
        float f4 = fArr[min];
        return DependencyGraph$$ExternalSyntheticOutline0.m20m(fArr[min + 1], f4, f3, f4);
    }
}
