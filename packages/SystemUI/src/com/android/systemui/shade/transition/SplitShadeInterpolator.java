package com.android.systemui.shade.transition;

import android.util.MathUtils;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
}
