package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityViewFlipperController;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecurityContainerController$$ExternalSyntheticLambda5 implements KeyguardSecurityViewFlipperController.OnViewInflatedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecurityContainerController$$ExternalSyntheticLambda5(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
    public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                KeyguardSecurityContainerController.$r8$lambda$KYJR4tS9tqY0po_fGpKS0AEbzLU((KeyguardSecSecurityContainerController) obj, keyguardInputViewController);
                break;
            default:
                Runnable runnable = (Runnable) obj;
                if (!keyguardInputViewController.startDisappearAnimation(runnable) && runnable != null) {
                    runnable.run();
                    break;
                }
                break;
        }
    }
}
