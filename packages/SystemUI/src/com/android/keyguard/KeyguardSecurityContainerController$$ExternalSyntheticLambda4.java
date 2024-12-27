package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityViewFlipperController;

public final /* synthetic */ class KeyguardSecurityContainerController$$ExternalSyntheticLambda4 implements KeyguardSecurityViewFlipperController.OnViewInflatedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ KeyguardSecurityContainerController$$ExternalSyntheticLambda4() {
        this.$r8$classId = 1;
        this.f$0 = 1;
    }

    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
    public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
        switch (this.$r8$classId) {
            case 0:
                keyguardInputViewController.showPromptReason(this.f$0);
                break;
            default:
                keyguardInputViewController.onResume(this.f$0);
                break;
        }
    }

    public /* synthetic */ KeyguardSecurityContainerController$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = 0;
        this.f$0 = i;
    }
}
