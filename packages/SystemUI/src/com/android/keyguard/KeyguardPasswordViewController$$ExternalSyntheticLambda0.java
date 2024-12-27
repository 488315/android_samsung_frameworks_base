package com.android.keyguard;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                KeyguardPasswordViewController.$r8$lambda$R6r4GN6BrTpuhpwcEizM9RE7nJM(keyguardPasswordViewController);
                break;
        }
    }
}
