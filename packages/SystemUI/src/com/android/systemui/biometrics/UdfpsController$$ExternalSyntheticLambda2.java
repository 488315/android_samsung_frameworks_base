package com.android.systemui.biometrics;

import android.util.Log;
import com.android.systemui.Flags;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class UdfpsController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ UdfpsController$$ExternalSyntheticLambda2(UdfpsController.UdfpsOverlayController udfpsOverlayController, String str) {
        this.$r8$classId = 2;
        this.f$0 = udfpsOverlayController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((UdfpsController) obj).tryAodSendFingerUp();
                break;
            case 1:
                UdfpsController.UdfpsOverlayController udfpsOverlayController = (UdfpsController.UdfpsOverlayController) obj;
                if (UdfpsController.this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
                    Log.d("UdfpsController", "hiding udfps overlay when mKeyguardUpdateMonitor.isFingerprintDetectionRunning()=true");
                }
                UdfpsController.this.hideUdfpsOverlay();
                break;
            default:
                UdfpsControllerOverlay udfpsControllerOverlay = UdfpsController.this.mOverlay;
                if (udfpsControllerOverlay != null) {
                    Flags.deviceEntryUdfpsRefactor();
                    if (udfpsControllerOverlay.overlayTouchView != null) {
                        DeviceEntryUdfpsRefactor deviceEntryUdfpsRefactor = DeviceEntryUdfpsRefactor.INSTANCE;
                        Flags.deviceEntryUdfpsRefactor();
                        break;
                    }
                }
                break;
        }
    }

    public /* synthetic */ UdfpsController$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }
}
