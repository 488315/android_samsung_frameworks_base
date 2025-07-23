package com.android.systemui.media.audiovisseekbar.utils.easing;

import android.view.animation.PathInterpolator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Interpolators {
    public static final Interpolators INSTANCE = new Interpolators();
    public static final PathInterpolator MOTION_ACTIVITY_EASING = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    private Interpolators() {
    }
}
