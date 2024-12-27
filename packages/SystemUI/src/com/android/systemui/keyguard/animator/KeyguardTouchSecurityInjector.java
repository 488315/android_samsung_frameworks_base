package com.android.systemui.keyguard.animator;

import android.hardware.fingerprint.FingerprintManager;
import android.util.Log;
import android.view.MotionEvent;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardTouchSecurityInjector {
    public final FingerprintManager mFingerprintManager;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final SelectedUserInteractor mSelectedUserInteractor;

    public KeyguardTouchSecurityInjector(KeyguardUpdateMonitor keyguardUpdateMonitor, FingerprintManager fingerprintManager, SelectedUserInteractor selectedUserInteractor) {
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mFingerprintManager = fingerprintManager == null ? null : fingerprintManager;
        this.mSelectedUserInteractor = selectedUserInteractor;
    }

    public final boolean isFingerprintArea(MotionEvent motionEvent) {
        if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || this.mFingerprintManager == null) {
            return false;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (!keyguardUpdateMonitor.isFingerprintOptionEnabled() || keyguardUpdateMonitor.getUserCanSkipBouncer(this.mSelectedUserInteractor.getSelectedUserId()) || !this.mFingerprintManager.semGetSensorAreaInDisplay().contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
            return false;
        }
        Log.e("KeyguardFingerPrintSwipe", "mLongPressCallback canceled. Touch position is FP-InDisplay area!");
        return true;
    }
}
