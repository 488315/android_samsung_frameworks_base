package com.android.app.animation;

import android.graphics.Path;
import androidx.core.animation.AccelerateDecelerateInterpolator;
import androidx.core.animation.AccelerateInterpolator;
import androidx.core.animation.BounceInterpolator;
import androidx.core.animation.DecelerateInterpolator;
import androidx.core.animation.LinearInterpolator;
import androidx.core.animation.PathInterpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class InterpolatorsAndroidX {
    public static final DecelerateInterpolator DECELERATE_QUINT;
    public static final PathInterpolator FAST_OUT_SLOW_IN;
    public static final PathInterpolator LEGACY;
    public static final PathInterpolator LEGACY_ACCELERATE;
    public static final PathInterpolator LEGACY_DECELERATE;
    public static final LinearInterpolator LINEAR;
    public static final PathInterpolator LINEAR_OUT_SLOW_IN;

    static {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(0.05f, 0.0f, 0.133333f, 0.06f, 0.166666f, 0.4f);
        path.cubicTo(0.208333f, 0.82f, 0.25f, 1.0f, 1.0f, 1.0f);
        new PathInterpolator(path);
        new PathInterpolator(0.3f, 0.0f, 0.8f, 0.15f);
        new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
        new PathInterpolator(0.2f, 0.0f, 0.0f, 1.0f);
        new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
        new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f);
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
        new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
        new AccelerateInterpolator();
        new AccelerateDecelerateInterpolator();
        DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
        new PathInterpolator(0.4f, 0.0f, 0.6f, 1.0f);
        new PathInterpolator(0.4f, 0.0f, 0.2f, 1.4f);
        new PathInterpolator(0.4f, 0.0f, 0.2f, 1.1f);
        new PathInterpolator(0.3f, 0.0f, 0.5f, 1.0f);
        new BounceInterpolator();
        new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        new PathInterpolator(0.3f, 0.0f, 0.1f, 1.0f);
        new PathInterpolator(0.9f, 0.0f, 0.7f, 1.0f);
    }
}
