package com.android.systemui.statusbar;

import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

public final class RotationHelper {
    public static final Interpolator ROTATION = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    public static void clockWise(float f, View view) {
        view.animate().cancel();
        view.setRotation(Math.min((((PathInterpolator) ROTATION).getInterpolation(Math.min(f / 0.5833333f, 1.0f)) * 180.0f) + 180.0f, 360.0f));
    }

    public static void counterClockWise(float f, View view) {
        view.animate().cancel();
        float min = Math.min(f / 0.5833333f, 1.0f);
        view.setRotation(((PathInterpolator) ROTATION).getInterpolation(1.0f - min) * 180.0f);
    }
}
