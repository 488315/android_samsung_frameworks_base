package com.android.systemui.media.audiovisseekbar.utils.easing;

import android.view.animation.PathInterpolator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Interpolators {
    public static final Interpolators INSTANCE = new Interpolators();
    public static final PathInterpolator MOTION_ACTIVITY_EASING = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    private Interpolators() {
    }
}
