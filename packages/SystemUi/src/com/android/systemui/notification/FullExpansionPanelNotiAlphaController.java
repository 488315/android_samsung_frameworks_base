package com.android.systemui.notification;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;
import com.android.systemui.p016qs.TouchAnimator;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FullExpansionPanelNotiAlphaController {
    public final KeyguardEditModeController mKeyguardEditModeController;
    public final KeyguardTouchAnimator mKeyguardTouchAnimator;
    public final Interpolator mSineInOut33 = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
    public NotificationStackScrollLayout mStackScrollLayout;
    public TouchAnimator mStackScrollerAlphaAnimator;
    public boolean mStackScrollerOverscrolling;

    public FullExpansionPanelNotiAlphaController(KeyguardTouchAnimator keyguardTouchAnimator, KeyguardEditModeController keyguardEditModeController) {
        this.mKeyguardTouchAnimator = keyguardTouchAnimator;
        this.mKeyguardEditModeController = keyguardEditModeController;
    }
}
