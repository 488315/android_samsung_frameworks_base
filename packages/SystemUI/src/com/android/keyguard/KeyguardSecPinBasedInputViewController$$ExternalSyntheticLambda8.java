package com.android.keyguard;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecPinBasedInputViewController f$0;

    public /* synthetic */ KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda8(KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecPinBasedInputViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController = this.f$0;
        switch (i) {
            case 0:
                keyguardSecPinBasedInputViewController.initializeBottomContainerView$1();
                break;
            default:
                keyguardSecPinBasedInputViewController.mPasswordEntry.requestFocus();
                break;
        }
    }
}
