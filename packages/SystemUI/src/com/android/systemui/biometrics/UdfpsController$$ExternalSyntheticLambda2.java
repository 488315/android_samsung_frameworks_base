package com.android.systemui.biometrics;

import android.util.Log;
import com.android.systemui.biometrics.UdfpsController;
import java.util.HashSet;
import java.util.Iterator;

/* loaded from: classes.dex */
public final /* synthetic */ class UdfpsController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ UdfpsController$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
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
            case 2:
                Iterator it = ((HashSet) UdfpsController.this.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((UdfpsController.Callback) it.next()).onFingerDown();
                }
                break;
            case 3:
                Iterator it2 = ((HashSet) UdfpsController.this.mCallbacks).iterator();
                while (it2.hasNext()) {
                    ((UdfpsController.Callback) it2.next()).onFingerUp();
                }
                break;
            default:
                UdfpsControllerOverlay udfpsControllerOverlay = UdfpsController.this.mOverlay;
                break;
        }
    }
}
