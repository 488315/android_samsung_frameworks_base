package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityViewFlipperController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
