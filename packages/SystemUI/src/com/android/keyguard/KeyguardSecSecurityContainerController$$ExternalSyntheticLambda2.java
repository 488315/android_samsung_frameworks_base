package com.android.keyguard;

import com.android.keyguard.biometrics.KeyguardBiometricViewController;

public final /* synthetic */ class KeyguardSecSecurityContainerController$$ExternalSyntheticLambda2 implements DualDarKeyguardSecurityCallback {
    public final /* synthetic */ KeyguardSecSecurityContainerController f$0;

    public /* synthetic */ KeyguardSecSecurityContainerController$$ExternalSyntheticLambda2(KeyguardSecSecurityContainerController keyguardSecSecurityContainerController) {
        this.f$0 = keyguardSecSecurityContainerController;
    }

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
