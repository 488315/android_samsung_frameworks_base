package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

/* loaded from: classes6.dex */
public class BounceCurve extends Easing {
    private static final float D1 = 2.75f;
    private static final float N1 = 7.5625f;

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float f) {
        if (f < 0.0f) {
            return 0.0f;
        }
        if (f < 0.36363637f) {
            return ((N1 * f * f) + f) * 0.73333335f;
        }
        if (f < 0.72727275f) {
            float f2 = f - 0.54545456f;
            return (N1 * f2 * f2) + 0.75f;
        }
        if (f < 0.9090909090909091d) {
            float f3 = f - 0.8181818f;
            return (N1 * f3 * f3) + 0.9375f;
        }
        if (f > 1.0f) {
            return 1.0f;
        }
        float f4 = f - 0.95454544f;
        return (N1 * f4 * f4) + 0.984375f;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float f) {
        if (f < 0.0f) {
            return 0.0f;
        }
        if (f < 0.36363637f) {
            return ((f * 15.125f) / 1.3636364f) + 0.73333335f;
        }
        if (f < 0.72727275f) {
            return (f - 0.54545456f) * 15.125f;
        }
        if (f < 0.9090909090909091d) {
            return (f - 0.8181818f) * 15.125f;
        }
        if (f <= 1.0f) {
            return (f - 0.95454544f) * 15.125f;
        }
        return 0.0f;
    }

    BounceCurve(int i) {
        this.mType = i;
    }
}
