package com.android.systemui.statusbar;

import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class RotationHelper {
    public static final Interpolator ROTATION = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    public static void counterClockWise(float f, View view) {
        view.animate().cancel();
        float min = Math.min(f / 0.5833333f, 1.0f);
        view.setRotation(((PathInterpolator) ROTATION).getInterpolation(1.0f - min) * 180.0f);
    }
}
