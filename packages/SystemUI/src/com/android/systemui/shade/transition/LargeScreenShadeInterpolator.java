package com.android.systemui.shade.transition;

public interface LargeScreenShadeInterpolator {
    float getBehindScrimAlpha(float f);

    float getNotificationContentAlpha(float f);

    float getNotificationScrimAlpha(float f);
}
