package com.android.systemui.keyguard.animator;

import android.hardware.fingerprint.FingerprintManager;
import android.util.Log;
import android.view.MotionEvent;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardTouchSecurityInjector {
    public final FingerprintManager mFingerprintManager;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

    public KeyguardTouchSecurityInjector(KeyguardUpdateMonitor keyguardUpdateMonitor, FingerprintManager fingerprintManager) {
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mFingerprintManager = fingerprintManager == null ? null : fingerprintManager;
    }

    public final boolean isFingerprintArea(MotionEvent motionEvent) {
        FingerprintManager fingerprintManager;
        if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || (fingerprintManager = this.mFingerprintManager) == null) {
            return false;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (!keyguardUpdateMonitor.isFingerprintOptionEnabled() || keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) || !fingerprintManager.semGetSensorAreaInDisplay().contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
            return false;
        }
        Log.e("KeyguardFingerPrintSwipe", "mLongPressCallback canceled. Touch position is FP-InDisplay area!");
        return true;
    }
}
