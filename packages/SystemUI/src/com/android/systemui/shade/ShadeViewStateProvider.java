package com.android.systemui.shade;

import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;

/* loaded from: classes3.dex */
public interface ShadeViewStateProvider {
    KeyguardTouchAnimator getKeyguardTouchAnimator();

    float getLockscreenShadeDragProgress();

    float getPanelViewExpandedHeight();

    boolean shouldHeadsUpBeVisible();
}
