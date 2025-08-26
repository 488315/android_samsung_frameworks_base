package com.android.keyguard;

import android.telephony.PinResult;
import android.util.Log;
import com.android.keyguard.KeyguardSecSimPinViewController;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecSimPinViewController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecSimPinViewController$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                KeyguardSecSimPinViewController keyguardSecSimPinViewController = (KeyguardSecSimPinViewController) obj;
                keyguardSecSimPinViewController.setSimInfoViewVisibility(0);
                keyguardSecSimPinViewController.updateESimLayout();
                break;
            case 1:
                KeyguardSecSimPinViewController keyguardSecSimPinViewController2 = ((KeyguardSecSimPinViewController.AnonymousClass2) obj).this$0;
                if (keyguardSecSimPinViewController2.mESimSkipArea != null) {
                    Log.d("KeyguardSecSimPinViewController", "eraseSubscriptions");
                    keyguardSecSimPinViewController2.mESimSkipArea.eraseSubscriptions();
                    break;
                }
                break;
            default:
                KeyguardSecSimPinViewController.SecCheckSimPin secCheckSimPin = (KeyguardSecSimPinViewController.SecCheckSimPin) obj;
                int i2 = KeyguardSecSimPinViewController.SecCheckSimPin.$r8$clinit;
                secCheckSimPin.getClass();
                secCheckSimPin.onSimCheckResponse(PinResult.getDefaultFailedResult());
                break;
        }
    }
}
