package com.android.systemui.shade.transition;

import android.util.MathUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SplitShadeInterpolator implements LargeScreenShadeInterpolator {
    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getBehindScrimAlpha(float f) {
        return MathUtils.constrainedMap(0.0f, 1.0f, 0.0f, 0.4f, f);
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getNotificationContentAlpha(float f) {
        return getNotificationScrimAlpha(f);
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getNotificationScrimAlpha(float f) {
        return MathUtils.constrainedMap(0.0f, 1.0f, 0.39f, 0.66f, f);
    }

    @Override // com.android.systemui.shade.transition.LargeScreenShadeInterpolator
    public final float getQsAlpha(float f) {
        return getNotificationScrimAlpha(f);
    }
}
