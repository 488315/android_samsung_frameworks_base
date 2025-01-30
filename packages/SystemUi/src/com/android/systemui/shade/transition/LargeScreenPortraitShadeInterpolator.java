package com.android.systemui.shade.transition;

import android.util.MathUtils;
import com.android.systemui.animation.ShadeInterpolation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LargeScreenPortraitShadeInterpolator implements LargeScreenShadeInterpolator {
    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getBehindScrimAlpha(float f) {
        return MathUtils.constrainedMap(0.0f, 1.0f, 0.0f, 0.3f, f);
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getNotificationContentAlpha(float f) {
        return ShadeInterpolation.getContentAlpha(f);
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getNotificationScrimAlpha(float f) {
        return MathUtils.constrainedMap(0.0f, 1.0f, 0.3f, 0.75f, f);
    }
}
