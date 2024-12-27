package com.android.systemui.keyguard;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class KeyguardSecLegacyUnlockAnimationControllerImplKt {
    public static final Interpolator ALPHA_INTERPOLATOR = new PathInterpolator(0.0f, 1.0f, 0.0f, 2.45f);
    public static final Interpolator SCALE_INTERPOLATOR = new PathInterpolator(0.4f, 1.61f, 0.28f, 0.985f);
}
