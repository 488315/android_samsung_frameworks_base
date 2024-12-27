package com.android.keyguard;

import android.os.Bundle;
import com.android.keyguard.KeyguardUCMViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int[] f$1;
    public final /* synthetic */ Bundle f$2;

    public /* synthetic */ KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda0(KeyguardUCMViewController.CheckUcmPin checkUcmPin, int[] iArr, Bundle bundle) {
        this.f$0 = checkUcmPin;
        this.f$1 = iArr;
        this.f$2 = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardUCMViewController.CheckUcmPin checkUcmPin = (KeyguardUCMViewController.CheckUcmPin) this.f$0;
                int[] iArr = this.f$1;
                Bundle bundle = this.f$2;
                checkUcmPin.getClass();
                checkUcmPin.onVerifyPinResponse(iArr[0], iArr[2], bundle);
                break;
            default:
                KeyguardUCMViewController.CheckUcmPuk checkUcmPuk = (KeyguardUCMViewController.CheckUcmPuk) this.f$0;
                int[] iArr2 = this.f$1;
                Bundle bundle2 = this.f$2;
                checkUcmPuk.getClass();
                checkUcmPuk.onVerifyPukResponse(iArr2[0], iArr2[2], bundle2);
                break;
        }
    }

    public /* synthetic */ KeyguardUCMViewController$CheckUcmPin$$ExternalSyntheticLambda0(KeyguardUCMViewController.CheckUcmPuk checkUcmPuk, int[] iArr, Bundle bundle) {
        this.f$0 = checkUcmPuk;
        this.f$1 = iArr;
        this.f$2 = bundle;
    }
}
