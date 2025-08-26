package com.android.systemui.keyguard;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* loaded from: classes2.dex */
public abstract class KeyguardSecLegacyUnlockAnimationControllerImplKt {
    public static final Interpolator ALPHA_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    public static final Interpolator SCALE_INTERPOLATOR = new PathInterpolator(0.4f, 1.61f, 0.28f, 0.985f);
}
