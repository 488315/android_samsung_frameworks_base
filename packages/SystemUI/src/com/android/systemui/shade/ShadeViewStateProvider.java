package com.android.systemui.shade;

import com.android.systemui.keyguard.animator.KeyguardTouchAnimator;

public interface ShadeViewStateProvider {
    KeyguardTouchAnimator getKeyguardTouchAnimator();

    float getLockscreenShadeDragProgress();

    float getPanelViewExpandedHeight();

    boolean shouldHeadsUpBeVisible();
}
