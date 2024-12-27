package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityViewFlipperController;

public final /* synthetic */ class KeyguardSecurityContainerController$$ExternalSyntheticLambda3 implements KeyguardSecurityViewFlipperController.OnViewInflatedCallback, KeyguardSecurityContainer.UserSwitcherViewMode.UserSwitcherCallback {
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecurityContainerController$$ExternalSyntheticLambda3(Object obj) {
        this.f$0 = obj;
    }

    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
    public void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
        Runnable runnable = (Runnable) this.f$0;
        if (keyguardInputViewController.startDisappearAnimation(runnable) || runnable == null) {
            return;
        }
        runnable.run();
    }
}
