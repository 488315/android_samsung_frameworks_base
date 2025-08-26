package com.android.app.animation;

import android.graphics.Path;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import androidx.core.animation.PathInterpolator$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public class Interpolators {
    public static final Interpolator ACCELERATE;
    public static final Interpolator ACCELERATE_DECELERATE;
    public static final Interpolator ALPHA_IN;
    public static final Interpolator ALPHA_OUT;
    public static final Interpolator BACK_GESTURE;
    public static final Interpolator CONTROL_STATE;
    public static final Interpolator CUSTOM_40_40;
    public static final Interpolator DECELERATE_1_5;
    public static final Interpolator DECELERATE_3;
    public static final Interpolator DECELERATE_QUINT;
    public static final Interpolator EMPHASIZED;
    public static final Interpolator EMPHASIZED_ACCELERATE;
    public static final Interpolator EMPHASIZED_COMPLEMENT;
    public static final Interpolator EMPHASIZED_DECELERATE;
    public static final Interpolator FAST_OUT_LINEAR_IN;
    public static final Interpolator FAST_OUT_SLOW_IN;
    public static final Interpolator FAST_OUT_SLOW_IN_REVERSE;
    public static final Interpolator ICON_OVERSHOT;
    public static final Interpolator ICON_OVERSHOT_LESS;
    public static final Interpolator LEGACY;
    public static final Interpolator LEGACY_DECELERATE;
    public static final Interpolator LINEAR;
    public static final Interpolator LINEAR_OUT_SLOW_IN;
    public static final Interpolator PANEL_CLOSE_ACCELERATED;
    public static final Interpolator SLOW_OUT_LINEAR_IN;
    public static final Interpolator STANDARD;
    public static final Interpolator STANDARD_ACCELERATE;
    public static final Interpolator STANDARD_DECELERATE;
    public static final Interpolator TOUCH_RESPONSE;
    public static final Interpolator TOUCH_RESPONSE_REVERSE;
    public static final AnonymousClass2 ZOOM_OUT;

    /* renamed from: com.android.app.animation.Interpolators$2, reason: invalid class name */
    public class AnonymousClass2 implements Interpolator {
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return (1.0f - (0.35f / (f + 0.35f))) / 0.7407408f;
        }
    }

    static {
        Path pathM = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
        pathM.cubicTo(0.05f, 0.0f, 0.133333f, 0.06f, 0.166666f, 0.4f);
        pathM.cubicTo(0.208333f, 0.82f, 0.25f, 1.0f, 1.0f, 1.0f);
        EMPHASIZED = new PathInterpolator(pathM);
        Path pathM2 = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
        pathM2.cubicTo(0.1217f, 0.0462f, 0.15f, 0.4686f, 0.1667f, 0.66f);
        pathM2.cubicTo(0.1834f, 0.8878f, 0.1667f, 1.0f, 1.0f, 1.0f);
        EMPHASIZED_COMPLEMENT = new PathInterpolator(pathM2);
        EMPHASIZED_ACCELERATE = new PathInterpolator(0.3f, 0.0f, 0.8f, 0.15f);
        EMPHASIZED_DECELERATE = new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
        Path pathM3 = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
        pathM3.cubicTo(0.05f, 0.0f, 0.133333f, 0.08f, 0.166666f, 0.4f);
        pathM3.cubicTo(0.225f, 0.94f, 0.5f, 1.0f, 1.0f, 1.0f);
        new PathInterpolator(pathM3);
        new OvershootInterpolator(0.75f);
        new OvershootInterpolator(1.2f);
        new OvershootInterpolator(1.7f);
        STANDARD = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
        STANDARD_ACCELERATE = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
        STANDARD_DECELERATE = new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
        PathInterpolator pathInterpolator = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        LEGACY = pathInterpolator;
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        PathInterpolator pathInterpolator3 = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        LEGACY_DECELERATE = pathInterpolator3;
        LINEAR = new LinearInterpolator();
        FAST_OUT_SLOW_IN = pathInterpolator;
        FAST_OUT_LINEAR_IN = pathInterpolator2;
        LINEAR_OUT_SLOW_IN = pathInterpolator3;
        FAST_OUT_SLOW_IN_REVERSE = new PathInterpolator(0.8f, 0.0f, 0.6f, 1.0f);
        SLOW_OUT_LINEAR_IN = new PathInterpolator(0.8f, 0.0f, 1.0f, 1.0f);
        new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
        new PathInterpolator(0.6f, 0.0f, 0.4f, 1.0f);
        new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        ALPHA_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        ALPHA_OUT = new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
        ACCELERATE = new AccelerateInterpolator();
        new AccelerateInterpolator(0.5f);
        new AccelerateInterpolator(0.75f);
        new AccelerateInterpolator(1.5f);
        new AccelerateInterpolator(2.0f);
        ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        new DecelerateInterpolator();
        DECELERATE_1_5 = new DecelerateInterpolator(1.5f);
        new DecelerateInterpolator(1.7f);
        new DecelerateInterpolator(2.0f);
        DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
        DECELERATE_3 = new DecelerateInterpolator(3.0f);
        CUSTOM_40_40 = new PathInterpolator(0.4f, 0.0f, 0.6f, 1.0f);
        ICON_OVERSHOT = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.4f);
        ICON_OVERSHOT_LESS = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.1f);
        PANEL_CLOSE_ACCELERATED = new PathInterpolator(0.3f, 0.0f, 0.5f, 1.0f);
        new BounceInterpolator();
        CONTROL_STATE = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        TOUCH_RESPONSE = new PathInterpolator(0.3f, 0.0f, 0.1f, 1.0f);
        TOUCH_RESPONSE_REVERSE = new PathInterpolator(0.9f, 0.0f, 0.7f, 1.0f);
        new Interpolator() { // from class: com.android.app.animation.Interpolators.1
            @Override // android.animation.TimeInterpolator
            public final float getInterpolation(float f) {
                return ((DecelerateInterpolator) Interpolators.DECELERATE_3).getInterpolation(1.0f - Interpolators.ZOOM_OUT.getInterpolation(1.0f - f));
            }
        };
        ZOOM_OUT = new AnonymousClass2();
        new Interpolator() { // from class: com.android.app.animation.Interpolators.3
            @Override // android.animation.TimeInterpolator
            public final float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        };
        new Interpolator() { // from class: com.android.app.animation.Interpolators.4
            @Override // android.animation.TimeInterpolator
            public final float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2) + 1.0f;
            }
        };
        BACK_GESTURE = new PathInterpolator(0.1f, 0.1f, 0.0f, 1.0f);
    }

    public static float clampToProgress(Interpolator interpolator, float f, float f2, float f3) {
        if (f3 < f2) {
            throw new IllegalArgumentException(String.format("upperBound (%f) must be greater than lowerBound (%f)", Float.valueOf(f3), Float.valueOf(f2)));
        }
        if (f == f2 && f == f3) {
            if (f != 0.0f) {
                return 1.0f;
            }
        } else if (f >= f2) {
            if (f > f3) {
                return 1.0f;
            }
            return interpolator.getInterpolation((f - f2) / (f3 - f2));
        }
        return 0.0f;
    }
}
