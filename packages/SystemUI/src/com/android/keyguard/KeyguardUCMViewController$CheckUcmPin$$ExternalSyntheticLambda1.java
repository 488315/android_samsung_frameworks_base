package com.android.keyguard;

import com.android.keyguard.KeyguardUCMViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((KeyguardUCMViewController.CheckUcmPin) obj).onVerifyPinResponse(-1, -1, null);
                break;
            default:
                ((KeyguardUCMViewController.CheckUcmPuk) obj).onVerifyPukResponse(-1, -1, null);
                break;
        }
    }
}
