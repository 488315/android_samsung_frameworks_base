package com.android.keyguard.biometrics;

import android.view.View;

public final class KeyguardBiometricViewController$setLockIconOnClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ KeyguardBiometricViewController this$0;

    public KeyguardBiometricViewController$setLockIconOnClickListener$1(KeyguardBiometricViewController keyguardBiometricViewController) {
        this.this$0 = keyguardBiometricViewController;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        KeyguardBiometricViewController keyguardBiometricViewController = this.this$0;
        int i = KeyguardBiometricViewController.$r8$clinit;
        if (keyguardBiometricViewController.needsToChangeRetryButton()) {
            KeyguardBiometricViewController.access$onClickRetryButton(this.this$0);
        }
        if (this.this$0.isLandscape$1() && this.this$0.keyguardUpdateMonitor.isFaceOptionEnabled()) {
            this.this$0.updateLockIcon();
        }
        KeyguardBiometricViewController keyguardBiometricViewController2 = this.this$0;
        int i2 = keyguardBiometricViewController2.debugCount + 1;
        keyguardBiometricViewController2.debugCount = i2;
        if (i2 == 10) {
            keyguardBiometricViewController2.keyguardUpdateMonitor.enableSecurityDebug();
            this.this$0.debugCount = 0;
        }
    }
}
