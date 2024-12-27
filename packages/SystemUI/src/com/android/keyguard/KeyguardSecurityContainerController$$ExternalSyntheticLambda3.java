package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityViewFlipperController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
