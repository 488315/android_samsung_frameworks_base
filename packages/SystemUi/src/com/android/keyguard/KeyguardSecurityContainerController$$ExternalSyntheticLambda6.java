package com.android.keyguard;

import com.android.keyguard.KeyguardSecurityViewFlipperController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecurityContainerController$$ExternalSyntheticLambda6 implements KeyguardSecurityViewFlipperController.OnViewInflatedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ KeyguardSecurityContainerController$$ExternalSyntheticLambda6(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // com.android.keyguard.KeyguardSecurityViewFlipperController.OnViewInflatedCallback
    public final void onViewInflated(KeyguardInputViewController keyguardInputViewController) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        switch (i) {
            case 0:
                keyguardInputViewController.onResume(i2);
                break;
            default:
                keyguardInputViewController.showPromptReason(i2);
                break;
        }
    }
}
