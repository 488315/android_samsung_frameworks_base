package com.android.keyguard;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecPinBasedInputViewController f$0;

    public /* synthetic */ KeyguardSecPinBasedInputViewController$$ExternalSyntheticLambda3(KeyguardSecPinBasedInputViewController keyguardSecPinBasedInputViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecPinBasedInputViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.initializeBottomContainerView$1();
                break;
            default:
                this.f$0.mPasswordEntry.requestFocus();
                break;
        }
    }
}
