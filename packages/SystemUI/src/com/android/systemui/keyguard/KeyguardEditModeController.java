package com.android.systemui.keyguard;

public interface KeyguardEditModeController {

    public interface Listener {
        void onAnimationEnded();

        void onAnimationStarted(boolean z);

        default void onTouchDownCanceled() {
        }

        default void onTouchDownStarted() {
        }
    }
}
