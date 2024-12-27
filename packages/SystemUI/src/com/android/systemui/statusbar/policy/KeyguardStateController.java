package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
