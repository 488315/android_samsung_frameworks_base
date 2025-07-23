package com.android.keyguard;

import com.android.keyguard.CarrierTextManager;
import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class CarrierTextManager$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ CarrierTextManager.CarrierTextCallback f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CarrierTextManager$$ExternalSyntheticLambda1(CarrierTextManager.CarrierTextCallback carrierTextCallback, CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
        this.f$0 = carrierTextCallback;
        this.f$1 = carrierTextCallbackInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CarrierTextManager.CarrierTextCallback carrierTextCallback = this.f$0;
                CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo = (CarrierTextManager.CarrierTextCallbackInfo) this.f$1;
                HashMap hashMap = CarrierTextManager.shortCarrierNameMap;
                carrierTextCallback.updateCarrierInfo(carrierTextCallbackInfo);
                break;
            default:
                CarrierTextManager carrierTextManager = (CarrierTextManager) this.f$1;
                CarrierTextManager.CarrierTextCallback carrierTextCallback2 = this.f$0;
                HashMap hashMap2 = CarrierTextManager.shortCarrierNameMap;
                carrierTextManager.handleSetListening(carrierTextCallback2);
                break;
        }
    }

    public /* synthetic */ CarrierTextManager$$ExternalSyntheticLambda1(CarrierTextManager carrierTextManager, CarrierTextManager.CarrierTextCallback carrierTextCallback) {
        this.f$1 = carrierTextManager;
        this.f$0 = carrierTextCallback;
    }
}
