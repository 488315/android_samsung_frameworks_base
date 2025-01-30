package com.android.systemui.biometrics;

import android.util.Log;
import com.android.systemui.biometrics.UdfpsController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class UdfpsController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ UdfpsController$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 1:
                ((UdfpsController) this.f$0).tryAodSendFingerUp();
                break;
            default:
                UdfpsController.UdfpsOverlayController udfpsOverlayController = (UdfpsController.UdfpsOverlayController) this.f$0;
                if (UdfpsController.this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
                    Log.d("UdfpsController", "hiding udfps overlay when mKeyguardUpdateMonitor.isFingerprintDetectionRunning()=true");
                }
                UdfpsController.this.hideUdfpsOverlay();
                break;
        }
    }
}
