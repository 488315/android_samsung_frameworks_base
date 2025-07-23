package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface KeyguardStateController extends CallbackController, Dumpable {
    default boolean isUnlocked() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this;
        return !keyguardStateControllerImpl.mShowing || keyguardStateControllerImpl.mCanDismissLockScreen;
    }

    default boolean isVisible() {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this;
        return keyguardStateControllerImpl.mShowing && !keyguardStateControllerImpl.mOccluded;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        default void onPrimaryBouncerShowingChanged() {
        }

        default void onUnlockedChanged() {
        }
    }
}
