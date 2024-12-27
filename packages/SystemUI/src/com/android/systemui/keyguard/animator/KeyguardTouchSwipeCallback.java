package com.android.systemui.keyguard.animator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface KeyguardTouchSwipeCallback {
    void callUserActivity();

    void onUnlockExecuted();

    default void onScreenOnOffAnimationEnd() {
    }

    default void setMotionAborted() {
    }
}
