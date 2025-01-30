package com.android.systemui.media.audiovisseekbar.utils.easing;

import android.view.animation.PathInterpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Interpolators {
    public static final Interpolators INSTANCE = new Interpolators();
    public static final PathInterpolator MOTION_ACTIVITY_EASING = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);

    private Interpolators() {
    }
}
