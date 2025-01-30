package com.android.systemui.keyguard;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyguardSecLegacyUnlockAnimationControllerImplKt {
    public static final Interpolator CUSTOM_INTERPOLATOR;
    public static final Interpolator SINE_IN_OUT_33;

    static {
        new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        SINE_IN_OUT_33 = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
        CUSTOM_INTERPOLATOR = new PathInterpolator(0.33f, 1.0f, 0.9f, 1.0f);
    }
}
