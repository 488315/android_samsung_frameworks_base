package com.android.keyguard;

import com.android.keyguard.biometrics.KeyguardBiometricViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecSecurityContainerController$$ExternalSyntheticLambda3 implements DualDarKeyguardSecurityCallback {
    public final /* synthetic */ KeyguardSecSecurityContainerController f$0;

    public final void onSecurityModeChanged(boolean z) {
        KeyguardSecSecurityContainerController keyguardSecSecurityContainerController = this.f$0;
        ViewMediatorCallback viewMediatorCallback = keyguardSecSecurityContainerController.mViewMediatorCallback;
        if (viewMediatorCallback != null) {
            viewMediatorCallback.setNeedsInput(z);
        }
        KeyguardBiometricViewController keyguardBiometricViewController = keyguardSecSecurityContainerController.mBiometricViewController;
        if (keyguardBiometricViewController != null) {
            keyguardBiometricViewController.startLockIconAnimation(true);
        }
    }
}
