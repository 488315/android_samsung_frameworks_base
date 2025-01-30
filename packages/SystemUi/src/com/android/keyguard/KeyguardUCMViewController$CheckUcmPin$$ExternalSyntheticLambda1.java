package com.android.keyguard;

import com.android.keyguard.KeyguardUCMViewController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Thread f$0;

    public /* synthetic */ KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda1(Thread thread, int i) {
        this.$r8$classId = i;
        this.f$0 = thread;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardUCMViewController.CheckUcmPin) this.f$0).onVerifyPinResponse(-1, -1, null);
                break;
            default:
                KeyguardUCMViewController.CheckUcmPuk checkUcmPuk = (KeyguardUCMViewController.CheckUcmPuk) this.f$0;
                int i = KeyguardUCMViewController.CheckUcmPuk.$r8$clinit;
                checkUcmPuk.onVerifyPukResponse(-1, -1, null);
                break;
        }
    }
}
