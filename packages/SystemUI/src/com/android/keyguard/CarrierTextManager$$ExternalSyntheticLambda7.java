package com.android.keyguard;

import com.android.keyguard.CarrierTextManager;
import java.util.HashMap;

/* loaded from: classes.dex */
public final /* synthetic */ class CarrierTextManager$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ CarrierTextManager$$ExternalSyntheticLambda7(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                CarrierTextManager carrierTextManager = (CarrierTextManager) obj;
                carrierTextManager.mKeyguardUpdateMonitor.registerCallback(carrierTextManager.mCallback);
                break;
            case 1:
                CarrierTextManager carrierTextManager2 = (CarrierTextManager) obj;
                carrierTextManager2.mKeyguardUpdateMonitor.registerCallback(carrierTextManager2.mCallback);
                break;
            case 2:
                CarrierTextManager carrierTextManager3 = (CarrierTextManager) obj;
                carrierTextManager3.mWakefulnessLifecycle.addObserver(carrierTextManager3.mWakefulnessObserver);
                break;
            case 3:
                CarrierTextManager carrierTextManager4 = (CarrierTextManager) obj;
                carrierTextManager4.mWakefulnessLifecycle.removeObserver(carrierTextManager4.mWakefulnessObserver);
                break;
            default:
                HashMap map = CarrierTextManager.shortCarrierNameMap;
                ((CarrierTextManager.CarrierTextCallback) obj).updateCarrierInfo(new CarrierTextManager.CarrierTextCallbackInfo(null, "", "", null, false, false, null, false));
                break;
        }
    }
}
