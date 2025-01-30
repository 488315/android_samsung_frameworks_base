package com.android.systemui.statusbar.policy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface KeyguardStateController extends CallbackController {
    default boolean isUnlocked() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this;
        return !keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mCanDismissLockScreen;
    }

    default boolean isVisible() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this;
        return keyguardStateControllerImpl.mShowing && !keyguardStateControllerImpl.mOccluded;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        default void onKeyguardDismissAmountChanged() {
        }

        default void onKeyguardFadingAwayChanged() {
        }

        default void onKeyguardGoingAwayChanged() {
        }

        default void onKeyguardShowingChanged() {
        }

        default void onLaunchTransitionFadingAwayChanged() {
        }

        default void onPrimaryBouncerShowingChanged() {
        }

        default void onUnlockedChanged() {
        }
    }
}
