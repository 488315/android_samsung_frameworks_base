package com.android.keyguard;

import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.KeyguardSecurityModel;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
