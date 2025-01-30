package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityViewFlipperController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecurityContainerController$$ExternalSyntheticLambda4 implements KeyguardSecurityViewFlipperController.OnViewInflatedCallback {
    public final /* synthetic */ int $r8$classId;

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
            default:
                keyguardInputViewController.onPause();
                break;
        }
    }
}
