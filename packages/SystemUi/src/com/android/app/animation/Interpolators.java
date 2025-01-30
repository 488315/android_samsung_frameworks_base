package com.android.app.animation;

import android.graphics.Path;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Interpolators {
    public static final Interpolator ACCELERATE;
    public static final Interpolator ACCELERATE_DECELERATE;
    public static final Interpolator ALPHA_IN;
    public static final Interpolator ALPHA_OUT;
    public static final Interpolator CONTROL_STATE;
    public static final Interpolator CUSTOM_40_40;
    public static final Interpolator DECELERATE_QUINT;
    public static final Interpolator EMPHASIZED;
    public static final Interpolator EMPHASIZED_ACCELERATE;
    public static final Interpolator EMPHASIZED_DECELERATE;
    public static final Interpolator FAST_OUT_LINEAR_IN;
    public static final Interpolator FAST_OUT_SLOW_IN;
    public static final Interpolator FAST_OUT_SLOW_IN_REVERSE;
    public static final Interpolator ICON_OVERSHOT;
    public static final Interpolator ICON_OVERSHOT_LESS;
    public static final Interpolator LEGACY_DECELERATE;
    public static final Interpolator LINEAR;
    public static final Interpolator LINEAR_OUT_SLOW_IN;
    public static final Interpolator PANEL_CLOSE_ACCELERATED;
    public static final Interpolator STANDARD;
    public static final Interpolator STANDARD_ACCELERATE;
    public static final Interpolator STANDARD_DECELERATE;
    public static final Interpolator TOUCH_RESPONSE;
    public static final Interpolator TOUCH_RESPONSE_REVERSE;

    static {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(0.05f, 0.0f, 0.133333f, 0.06f, 0.166666f, 0.4f);
        path.cubicTo(0.208333f, 0.82f, 0.25f, 1.0f, 1.0f, 1.0f);
        EMPHASIZED = new PathInterpolator(path);
        EMPHASIZED_ACCELERATE = new PathInterpolator(0.3f, 0.0f, 0.8f, 0.15f);
        EMPHASIZED_DECELERATE = new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
        STANDARD = new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
        STANDARD_ACCELERATE = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
        STANDARD_DECELERATE = new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
        PathInterpolator pathInterpolator = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        PathInterpolator pathInterpolator3 = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        LEGACY_DECELERATE = pathInterpolator3;
        LINEAR = new LinearInterpolator();
        FAST_OUT_SLOW_IN = pathInterpolator;
        FAST_OUT_LINEAR_IN = pathInterpolator2;
        LINEAR_OUT_SLOW_IN = pathInterpolator3;
        FAST_OUT_SLOW_IN_REVERSE = new PathInterpolator(0.8f, 0.0f, 0.6f, 1.0f);
        new PathInterpolator(0.8f, 0.0f, 1.0f, 1.0f);
        ALPHA_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        ALPHA_OUT = new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
        ACCELERATE = new AccelerateInterpolator();
        ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
        CUSTOM_40_40 = new PathInterpolator(0.4f, 0.0f, 0.6f, 1.0f);
        ICON_OVERSHOT = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.4f);
        ICON_OVERSHOT_LESS = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.1f);
        PANEL_CLOSE_ACCELERATED = new PathInterpolator(0.3f, 0.0f, 0.5f, 1.0f);
        new BounceInterpolator();
        CONTROL_STATE = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        TOUCH_RESPONSE = new PathInterpolator(0.3f, 0.0f, 0.1f, 1.0f);
        TOUCH_RESPONSE_REVERSE = new PathInterpolator(0.9f, 0.0f, 0.7f, 1.0f);
    }

    public static float getOvershootInterpolation(float f, float f2) {
        if (f2 == 0.0f) {
            throw new IllegalArgumentException("Invalid values for overshoot");
        }
        float exp = ((float) (1.0d - Math.exp((-(((float) Math.log(2.6666665f)) / f2)) * f))) * 1.6f;
        if (0.0f > exp) {
            return 0.0f;
        }
        return exp;
    }
}
