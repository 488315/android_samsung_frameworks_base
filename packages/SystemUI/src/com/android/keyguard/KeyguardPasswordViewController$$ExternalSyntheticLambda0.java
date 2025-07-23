package com.android.keyguard;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardPasswordViewController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardPasswordViewController f$0;

    public /* synthetic */ KeyguardPasswordViewController$$ExternalSyntheticLambda0(KeyguardPasswordViewController keyguardPasswordViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardPasswordViewController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        KeyguardPasswordViewController keyguardPasswordViewController = this.f$0;
        switch (i) {
            case 0:
                keyguardPasswordViewController.mPasswordEntry.requestFocus();
                keyguardPasswordViewController.mInputMethodManager.showSoftInput(keyguardPasswordViewController.mPasswordEntry, 1);
                break;
            default:
                KeyguardPasswordViewController.$r8$lambda$pfvxiKS_CgCR3sXpIpEKkFQKRAc(keyguardPasswordViewController);
                break;
        }
    }
}
