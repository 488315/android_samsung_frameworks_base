package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityViewFlipperController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecurityContainerController$$ExternalSyntheticLambda1 implements KeyguardSecurityViewFlipperController.OnViewInflatedCallback {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ KeyguardSecurityContainerController$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
    public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
        switch (this.$r8$classId) {
            case 0:
                keyguardInputViewController.onPause();
                break;
            case 1:
                keyguardInputViewController.startAppearAnimation();
                break;
            case 2:
                keyguardInputViewController.onStartingToHide();
                break;
            case 3:
                keyguardInputViewController.onPause();
                break;
        }
    }

    private final void onViewInflated$com$android$keyguard$KeyguardSecurityContainerController$1$$ExternalSyntheticLambda0(KeyguardInputViewController keyguardInputViewController) {
    }
}
