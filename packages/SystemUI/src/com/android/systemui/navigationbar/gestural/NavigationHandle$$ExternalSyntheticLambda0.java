package com.android.systemui.navigationbar.gestural;

import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.navigationbar.gestural.NavigationHandle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NavigationHandle$$ExternalSyntheticLambda0 implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public final float getInterpolation(float f) {
        NavigationHandle.AnonymousClass1 anonymousClass1 = NavigationHandle.PULSE_ANIMATION_PROGRESS;
        return f <= 0.9f ? Interpolators.clampToProgress(Interpolators.LEGACY, f, 0.0f, 0.9f) : 1.0f - Interpolators.clampToProgress(Interpolators.LINEAR, f, 0.9f, 1.0f);
    }
}
