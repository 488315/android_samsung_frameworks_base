package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityViewFlipperController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecurityContainerController$$ExternalSyntheticLambda0 implements KeyguardSecurityViewFlipperController.OnViewInflatedCallback {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ KeyguardSecurityContainerController$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
    public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
        switch (this.$r8$classId) {
            case 0:
                keyguardInputViewController.onPause();
                break;
            case 1:
                keyguardInputViewController.onResume(1);
                break;
            case 2:
                keyguardInputViewController.startAppearAnimation();
                break;
            case 3:
                keyguardInputViewController.onStartingToHide();
                break;
            default:
                keyguardInputViewController.onPause();
                break;
        }
    }
}
