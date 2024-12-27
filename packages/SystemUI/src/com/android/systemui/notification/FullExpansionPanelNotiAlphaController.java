package com.android.systemui.notification;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FullExpansionPanelNotiAlphaController {
    public final Interpolator mSineInOut33 = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
    public NotificationStackScrollLayout mStackScrollLayout;
    public TouchAnimator mStackScrollerAlphaAnimator;
    public boolean mStackScrollerOverscrolling;
}
