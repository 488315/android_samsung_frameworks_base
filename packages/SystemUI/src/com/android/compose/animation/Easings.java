package com.android.compose.animation;

import androidx.compose.animation.core.CubicBezierEasing;
import androidx.compose.animation.core.Easing;
import androidx.core.animation.LinearInterpolator;
import androidx.core.animation.PathInterpolator;
import com.android.app.animation.InterpolatorsAndroidX;

/* loaded from: classes.dex */
public final class Easings {
    public static final Easings$fromInterpolator$1 Emphasized;
    public static final Easings INSTANCE = new Easings();
    public static final Easings$fromInterpolator$1 Legacy;
    public static final Easings$fromInterpolator$1 LegacyDecelerate;
    public static final Easings$fromInterpolator$1 Linear;
    public static final CubicBezierEasing PredictiveBack = null;
    public static final Easings$fromInterpolator$1 Standard;
    public static final Easings$fromInterpolator$1 StandardAccelerate;
    public static final Easings$fromInterpolator$1 StandardDecelerate;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.compose.animation.Easings$fromInterpolator$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.compose.animation.Easings$fromInterpolator$1] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.compose.animation.Easings$fromInterpolator$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.compose.animation.Easings$fromInterpolator$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.compose.animation.Easings$fromInterpolator$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.compose.animation.Easings$fromInterpolator$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.compose.animation.Easings$fromInterpolator$1] */
    static {
        final PathInterpolator pathInterpolator = InterpolatorsAndroidX.STANDARD;
        Standard = new Easing() { // from class: com.android.compose.animation.Easings$fromInterpolator$1
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                return pathInterpolator.getInterpolation(f);
            }
        };
        final PathInterpolator pathInterpolator2 = InterpolatorsAndroidX.STANDARD_ACCELERATE;
        StandardAccelerate = new Easing() { // from class: com.android.compose.animation.Easings$fromInterpolator$1
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                return pathInterpolator2.getInterpolation(f);
            }
        };
        final PathInterpolator pathInterpolator3 = InterpolatorsAndroidX.STANDARD_DECELERATE;
        StandardDecelerate = new Easing() { // from class: com.android.compose.animation.Easings$fromInterpolator$1
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                return pathInterpolator3.getInterpolation(f);
            }
        };
        final PathInterpolator pathInterpolator4 = InterpolatorsAndroidX.EMPHASIZED;
        Emphasized = new Easing() { // from class: com.android.compose.animation.Easings$fromInterpolator$1
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                return pathInterpolator4.getInterpolation(f);
            }
        };
        final PathInterpolator pathInterpolator5 = InterpolatorsAndroidX.EMPHASIZED_ACCELERATE;
        new Easing() { // from class: com.android.compose.animation.Easings$fromInterpolator$1
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                return pathInterpolator5.getInterpolation(f);
            }
        };
        final PathInterpolator pathInterpolator6 = InterpolatorsAndroidX.EMPHASIZED_DECELERATE;
        new Easing() { // from class: com.android.compose.animation.Easings$fromInterpolator$1
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                return pathInterpolator6.getInterpolation(f);
            }
        };
        final LinearInterpolator linearInterpolator = InterpolatorsAndroidX.LINEAR;
        Linear = new Easing() { // from class: com.android.compose.animation.Easings$fromInterpolator$1
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                return linearInterpolator.getInterpolation(f);
            }
        };
        new CubicBezierEasing(0.1f, 0.1f, 0.0f, 1.0f);
        final PathInterpolator pathInterpolator7 = InterpolatorsAndroidX.LEGACY;
        Legacy = new Easing() { // from class: com.android.compose.animation.Easings$fromInterpolator$1
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                return pathInterpolator7.getInterpolation(f);
            }
        };
        final PathInterpolator pathInterpolator8 = InterpolatorsAndroidX.LEGACY_ACCELERATE;
        new Easing() { // from class: com.android.compose.animation.Easings$fromInterpolator$1
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                return pathInterpolator8.getInterpolation(f);
            }
        };
        final PathInterpolator pathInterpolator9 = InterpolatorsAndroidX.LEGACY_DECELERATE;
        LegacyDecelerate = new Easing() { // from class: com.android.compose.animation.Easings$fromInterpolator$1
            @Override // androidx.compose.animation.core.Easing
            public final float transform(float f) {
                return pathInterpolator9.getInterpolation(f);
            }
        };
    }

    private Easings() {
    }
}
