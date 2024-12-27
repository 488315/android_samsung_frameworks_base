package com.android.systemui.notification;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;

public final class FullExpansionPanelNotiAlphaController {
    public final Interpolator mSineInOut33 = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
    public NotificationStackScrollLayout mStackScrollLayout;
    public TouchAnimator mStackScrollerAlphaAnimator;
    public boolean mStackScrollerOverscrolling;
}
