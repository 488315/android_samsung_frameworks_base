package com.android.internal.widget.remotecompose.core.operations.utilities.easing;

public class BounceCurve extends Easing {
    private static final float D1 = 2.75f;
    private static final float N1 = 7.5625f;

    BounceCurve(int type) {
        this.mType = type;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float get(float x) {
        if (x < 0.0f) {
            return 0.0f;
        }
        if (x < 0.36363637f) {
            return ((N1 * x * x) + x) * 0.73333335f;
        }
        if (x < 0.72727275f) {
            float t = x - 0.54545456f;
            return (N1 * t * t) + 0.75f;
        }
        if (x < 0.9090909090909091d) {
            float t2 = x - 0.8181818f;
            return (N1 * t2 * t2) + 0.9375f;
        }
        if (x <= 1.0f) {
            float t3 = x - 0.95454544f;
            return (N1 * t3 * t3) + 0.984375f;
        }
        return 1.0f;
    }

    @Override // com.android.internal.widget.remotecompose.core.operations.utilities.easing.Easing
    public float getDiff(float x) {
        if (x < 0.0f) {
            return 0.0f;
        }
        if (x < 0.36363637f) {
            return ((15.125f * x) / 1.3636364f) + 0.73333335f;
        }
        if (x < 0.72727275f) {
            return (x - 0.54545456f) * 15.125f;
        }
        if (x < 0.9090909090909091d) {
            return (x - 0.8181818f) * 15.125f;
        }
        if (x > 1.0f) {
            return 0.0f;
        }
        return (x - 0.95454544f) * 15.125f;
    }
}
