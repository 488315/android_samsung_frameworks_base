package com.android.keyguard;

import android.telephony.PinResult;
import android.view.View;
import com.android.keyguard.KeyguardSecSimPinViewController;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecSimPinViewController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ KeyguardSecSimPinViewController$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardSecSimPinViewController keyguardSecSimPinViewController = (KeyguardSecSimPinViewController) this.f$0;
                View findViewById = ((KeyguardSecSimPinView) keyguardSecSimPinViewController.mView).findViewById(R.id.keyguard_sec_sim_info_view_container);
                if (findViewById != null) {
                    findViewById.setVisibility(0);
                }
                keyguardSecSimPinViewController.updateESimLayout();
                break;
            default:
                KeyguardSecSimPinViewController.SecCheckSimPin secCheckSimPin = (KeyguardSecSimPinViewController.SecCheckSimPin) this.f$0;
                int i = KeyguardSecSimPinViewController.SecCheckSimPin.$r8$clinit;
                secCheckSimPin.getClass();
                secCheckSimPin.onSimCheckResponse(PinResult.getDefaultFailedResult());
                break;
        }
    }
}
