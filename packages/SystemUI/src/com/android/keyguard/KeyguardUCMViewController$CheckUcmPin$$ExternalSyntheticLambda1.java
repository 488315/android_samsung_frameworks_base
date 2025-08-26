package com.android.keyguard;

import com.android.keyguard.KeyguardUCMViewController;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                int i2 = KeyguardUCMViewController.CheckUcmPin.$r8$clinit;
                ((KeyguardUCMViewController.CheckUcmPin) obj).onVerifyPinResponse(-1, -1, null);
                break;
            default:
                int i3 = KeyguardUCMViewController.CheckUcmPuk.$r8$clinit;
                ((KeyguardUCMViewController.CheckUcmPuk) obj).onVerifyPukResponse(-1, -1, null);
                break;
        }
    }
}
