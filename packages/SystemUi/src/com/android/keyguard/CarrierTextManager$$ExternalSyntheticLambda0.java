package com.android.keyguard;

import com.android.keyguard.CarrierTextManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class CarrierTextManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ CarrierTextManager.CarrierTextCallback f$1;

    public /* synthetic */ CarrierTextManager$$ExternalSyntheticLambda0(CarrierTextManager.CarrierTextCallback carrierTextCallback, CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
        this.f$1 = carrierTextCallback;
        this.f$0 = carrierTextCallbackInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((CarrierTextManager) this.f$0).handleSetListening(this.f$1);
                break;
            default:
                this.f$1.updateCarrierInfo((CarrierTextManager.CarrierTextCallbackInfo) this.f$0);
                break;
        }
    }

    public /* synthetic */ CarrierTextManager$$ExternalSyntheticLambda0(CarrierTextManager carrierTextManager, CarrierTextManager.CarrierTextCallback carrierTextCallback) {
        this.f$0 = carrierTextManager;
        this.f$1 = carrierTextCallback;
    }
}
