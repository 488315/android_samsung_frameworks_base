package com.android.systemui.media.audiovisseekbar.utils.easing;

import android.view.animation.PathInterpolator;

public final class Interpolators {
    public static final Interpolators INSTANCE = new Interpolators();
    public static final PathInterpolator MOTION_ACTIVITY_EASING = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    private Interpolators() {
    }
}
