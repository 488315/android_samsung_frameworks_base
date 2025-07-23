package com.android.app.animation;

import android.graphics.Path;
import androidx.core.animation.AccelerateDecelerateInterpolator;
import androidx.core.animation.AccelerateInterpolator;
import androidx.core.animation.BounceInterpolator;
import androidx.core.animation.DecelerateInterpolator;
import androidx.core.animation.Interpolator;
import androidx.core.animation.LinearInterpolator;
import androidx.core.animation.OvershootInterpolator;
import androidx.core.animation.PathInterpolator;
import androidx.core.animation.PathInterpolator$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class InterpolatorsAndroidX {
    public static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE = null;
    public static final PathInterpolator ALPHA_IN;
    public static final PathInterpolator ALPHA_OUT;
    public static final DecelerateInterpolator DECELERATE_3;
    public static final PathInterpolator EMPHASIZED;
    public static final PathInterpolator EMPHASIZED_ACCELERATE;
    public static final PathInterpolator EMPHASIZED_DECELERATE;
    public static final PathInterpolator FAST_OUT_SLOW_IN;
    public static final PathInterpolator LEGACY;
    public static final PathInterpolator LEGACY_ACCELERATE;
    public static final PathInterpolator LEGACY_DECELERATE;
    public static final LinearInterpolator LINEAR;
    public static final PathInterpolator LINEAR_OUT_SLOW_IN;
    public static final PathInterpolator STANDARD;
    public static final PathInterpolator STANDARD_ACCELERATE;
    public static final PathInterpolator STANDARD_DECELERATE;
    public static final PathInterpolator TOUCH_RESPONSE = null;
    public static final AnonymousClass2 ZOOM_OUT;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.app.animation.InterpolatorsAndroidX$2, reason: invalid class name */
    public class AnonymousClass2 implements Interpolator {
        @Override // androidx.core.animation.Interpolator
        public final float getInterpolation(float f) {
            return (1.0f - (0.35f / (f + 0.35f))) / 0.7407408f;
        }
    }

    static {
        Path m = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
        m.cubicTo(0.05f, 0.0f, 0.133333f, 0.06f, 0.166666f, 0.4f);
        m.cubicTo(0.208333f, 0.82f, 0.25f, 1.0f, 1.0f, 1.0f);
        EMPHASIZED = new PathInterpolator(m);
        Path m2 = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
        m2.cubicTo(0.1217f, 0.0462f, 0.15f, 0.4686f, 0.1667f, 0.66f);
        m2.cubicTo(0.1834f, 0.8878f, 0.1667f, 1.0f, 1.0f, 1.0f);
        new PathInterpolator(m2);
        EMPHASIZED_ACCELERATE = new PathInterpolator(0.3f, 0.0f, 0.8f, 0.15f);
        EMPHASIZED_DECELERATE = new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
        Path m3 = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
        m3.cubicTo(0.05f, 0.0f, 0.133333f, 0.08f, 0.166666f, 0.4f);
        m3.cubicTo(0.225f, 0.94f, 0.5f, 1.0f, 1.0f, 1.0f);
        new PathInterpolator(m3);
        new OvershootInterpolator(0.75f);
        new OvershootInterpolator(1.2f);
        new OvershootInterpolator(1.7f);
        STANDARD = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
        STANDARD_ACCELERATE = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
        STANDARD_DECELERATE = new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
        PathInterpolator pathInterpolator = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        LEGACY = pathInterpolator;
        LEGACY_ACCELERATE = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        LEGACY_DECELERATE = pathInterpolator2;
        LINEAR = new LinearInterpolator();
        FAST_OUT_SLOW_IN = pathInterpolator;
        LINEAR_OUT_SLOW_IN = pathInterpolator2;
        new PathInterpolator(0.8f, 0.0f, 0.6f, 1.0f);
        new PathInterpolator(0.8f, 0.0f, 1.0f, 1.0f);
        new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
        new PathInterpolator(0.6f, 0.0f, 0.4f, 1.0f);
        new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        ALPHA_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        ALPHA_OUT = new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
        new AccelerateInterpolator();
        new AccelerateInterpolator(0.5f);
        new AccelerateInterpolator(0.75f);
        new AccelerateInterpolator(1.5f);
        new AccelerateInterpolator(2.0f);
        new AccelerateDecelerateInterpolator();
        new DecelerateInterpolator();
        new DecelerateInterpolator(1.5f);
        new DecelerateInterpolator(1.7f);
        new DecelerateInterpolator(2.0f);
        new DecelerateInterpolator(2.5f);
        DECELERATE_3 = new DecelerateInterpolator(3.0f);
        new PathInterpolator(0.4f, 0.0f, 0.6f, 1.0f);
        new PathInterpolator(0.4f, 0.0f, 0.2f, 1.4f);
        new PathInterpolator(0.4f, 0.0f, 0.2f, 1.1f);
        new PathInterpolator(0.3f, 0.0f, 0.5f, 1.0f);
        new BounceInterpolator();
        new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        new PathInterpolator(0.3f, 0.0f, 0.1f, 1.0f);
        new PathInterpolator(0.9f, 0.0f, 0.7f, 1.0f);
        new Interpolator() { // from class: com.android.app.animation.InterpolatorsAndroidX.1
            @Override // androidx.core.animation.Interpolator
            public final float getInterpolation(float f) {
                return InterpolatorsAndroidX.DECELERATE_3.getInterpolation(1.0f - InterpolatorsAndroidX.ZOOM_OUT.getInterpolation(1.0f - f));
            }
        };
        ZOOM_OUT = new AnonymousClass2();
        new Interpolator() { // from class: com.android.app.animation.InterpolatorsAndroidX.3
            @Override // androidx.core.animation.Interpolator
            public final float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        };
        new Interpolator() { // from class: com.android.app.animation.InterpolatorsAndroidX.4
            @Override // androidx.core.animation.Interpolator
            public final float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2) + 1.0f;
            }
        };
        new PathInterpolator(0.1f, 0.1f, 0.0f, 1.0f);
    }
}
