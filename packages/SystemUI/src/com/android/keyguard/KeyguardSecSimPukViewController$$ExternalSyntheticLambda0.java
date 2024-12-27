package com.android.keyguard;

import android.util.Log;
import com.android.keyguard.KeyguardSecSimPukViewController;

public final /* synthetic */ class KeyguardSecSimPukViewController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecSimPukViewController$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                KeyguardSecSimPukViewController keyguardSecSimPukViewController = (KeyguardSecSimPukViewController) obj;
                keyguardSecSimPukViewController.setSimInfoViewVisibility$1(0);
                keyguardSecSimPukViewController.updateESimLayout$1();
                break;
            default:
                KeyguardSecSimPukViewController keyguardSecSimPukViewController2 = ((KeyguardSecSimPukViewController.AnonymousClass2) obj).this$0;
                if (keyguardSecSimPukViewController2.mESimSkipArea != null) {
                    Log.d("KeyguardSecSimPukViewController", "eraseSubscriptions");
                    keyguardSecSimPukViewController2.mESimSkipArea.eraseSubscriptions();
                    break;
                }
                break;
        }
    }
}
