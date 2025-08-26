package com.android.wm.shell.shared.animation;

import android.graphics.Path;
import android.view.animation.BackGestureInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import androidx.core.animation.PathInterpolator$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public class Interpolators {
    public static final Interpolator BACK_GESTURE;
    public static final PathInterpolator DIM_INTERPOLATOR;
    public static final Interpolator EMPHASIZED;
    public static final Interpolator EMPHASIZED_ACCELERATE;
    public static final Interpolator EMPHASIZED_DECELERATE;
    public static final Interpolator PANEL_CLOSE_ACCELERATED;
    public static final Interpolator STANDARD_ACCELERATE;
    public static final Interpolator TOUCH_RESPONSE;
    public static final Interpolator LINEAR = new LinearInterpolator();
    public static final Interpolator ALPHA_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final Interpolator ALPHA_OUT = new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
    public static final Interpolator FAST_OUT_LINEAR_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final Interpolator FAST_OUT_SLOW_IN = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    public static final Interpolator LINEAR_OUT_SLOW_IN = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);

    static {
        Path pathM = PathInterpolator$$ExternalSyntheticOutline0.m(0.0f, 0.0f);
        pathM.cubicTo(0.05f, 0.0f, 0.133333f, 0.06f, 0.166666f, 0.4f);
        pathM.cubicTo(0.208333f, 0.82f, 0.25f, 1.0f, 1.0f, 1.0f);
        EMPHASIZED = new PathInterpolator(pathM);
        EMPHASIZED_ACCELERATE = new PathInterpolator(0.3f, 0.0f, 0.8f, 0.15f);
        EMPHASIZED_DECELERATE = new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
        STANDARD_ACCELERATE = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
        new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
        TOUCH_RESPONSE = new PathInterpolator(0.3f, 0.0f, 0.1f, 1.0f);
        PANEL_CLOSE_ACCELERATED = new PathInterpolator(0.3f, 0.0f, 0.5f, 1.0f);
        new PathInterpolator(0.5f, 1.0f, 0.5f, 1.0f);
        DIM_INTERPOLATOR = new PathInterpolator(0.23f, 0.87f, 0.52f, -0.11f);
        new PathInterpolator(0.23f, 0.87f, 0.83f, 0.83f);
        BACK_GESTURE = new BackGestureInterpolator();
    }
}
