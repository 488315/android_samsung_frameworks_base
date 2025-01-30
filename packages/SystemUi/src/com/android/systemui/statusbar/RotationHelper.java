package com.android.systemui.statusbar;

import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RotationHelper {
    public static final Interpolator ROTATION = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    public static void counterClockWise(View view, float f) {
        view.animate().cancel();
        float min = Math.min(f / 0.5833333f, 1.0f);
        view.setRotation(((PathInterpolator) ROTATION).getInterpolation(1.0f - min) * 180.0f);
    }
}
