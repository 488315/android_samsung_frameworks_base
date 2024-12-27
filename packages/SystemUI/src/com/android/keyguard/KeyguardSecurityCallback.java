package com.android.keyguard;

import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.KeyguardSecurityModel;

public interface KeyguardSecurityCallback {
    default void dismiss(boolean z, int i, KeyguardSecurityModel.SecurityMode securityMode) {
    }

    default boolean dismiss(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
        return false;
    }

    default void onCancelClicked() {
    }

    default void onUserInput() {
    }

    default void reset() {
    }

    default void showCurrentSecurityScreen() {
    }

    default void userActivity() {
    }

    default void finish(int i) {
    }

    default void onAttemptLockoutStart(long j) {
    }

    default void onSecurityModeChanged(boolean z) {
    }

    default void setPrevCredential(LockscreenCredential lockscreenCredential) {
    }

    default void showBackupSecurity(KeyguardSecurityModel.SecurityMode securityMode) {
    }

    default void reportUnlockAttempt(int i, int i2, boolean z) {
    }
}
