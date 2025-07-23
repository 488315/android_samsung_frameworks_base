package com.android.systemui.keyguard;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface KeyguardEditModeController {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Listener {
        void onAnimationEnded();

        void onAnimationStarted(boolean z);

        default void onTouchDownCanceled() {
        }

        default void onTouchDownStarted() {
        }
    }
}
