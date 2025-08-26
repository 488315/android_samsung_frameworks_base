package com.android.systemui.keyguard;

/* loaded from: classes2.dex */
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
