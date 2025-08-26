package com.android.keyguard;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecPinBasedInputViewController f$0;

    public /* synthetic */ KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda6(KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecPinBasedInputViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = this.f$0;
        switch (i) {
            case 0:
                keyguardSecPinBasedInputViewController.mPasswordEntry.requestFocus();
                break;
            default:
                keyguardSecPinBasedInputViewController.initializeBottomContainerView$1();
                break;
        }
    }
}
