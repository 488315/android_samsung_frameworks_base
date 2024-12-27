package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

public interface KeyguardStateController extends CallbackController, Dumpable {
    default boolean isUnlocked() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this;
        return !keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mCanDismissLockScreen;
    }

    default boolean isVisible() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this;
        return keyguardStateControllerImpl.mShowing && !keyguardStateControllerImpl.mOccluded;
    }

    public interface Callback {
        default void onFaceEnrolledChanged() {
        }

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
