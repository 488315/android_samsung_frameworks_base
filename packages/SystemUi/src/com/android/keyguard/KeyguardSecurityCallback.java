package com.android.keyguard;

import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.KeyguardSecurityModel;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface KeyguardSecurityCallback {
    default void dismiss(int i, KeyguardSecurityModel.SecurityMode securityMode, boolean z) {
    }

    default boolean dismiss(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
        return false;
    }

    default void onSecurityModeChanged(boolean z) {
    }

    default void setPrevCredential(LockscreenCredential lockscreenCredential) {
    }

    default void showBackupSecurity(KeyguardSecurityModel.SecurityMode securityMode) {
    }

    default void onCancelClicked() {
    }

    default void onUserInput() {
    }

    default void reset() {
    }

    default void userActivity() {
    }

    default void finish(int i, boolean z) {
    }

    default void reportUnlockAttempt(int i, int i2, boolean z) {
    }
}
