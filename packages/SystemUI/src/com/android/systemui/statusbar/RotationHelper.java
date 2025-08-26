package com.android.systemui.statusbar;

import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* loaded from: classes3.dex */
public class RotationHelper {
    public static final Interpolator ROTATION = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    public static void counterClockWise(float f, View view) {
        view.animate().cancel();
        float fMin = Math.min(f / 0.5833333f, 1.0f);
        view.setRotation(((PathInterpolator) ROTATION).getInterpolation(1.0f - fMin) * 180.0f);
    }
}
