package com.android.systemui.keyguard.animator;

public interface KeyguardTouchSwipeCallback {
    void callUserActivity();

    void onUnlockExecuted();

    default void onScreenOnOffAnimationEnd() {
    }

    default void setMotionAborted() {
    }
}
