package com.android.keyguard;

import com.android.keyguard.CarrierTextManager;
import java.util.concurrent.Executor;

public final /* synthetic */ class CarrierTextManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CarrierTextManager$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((CarrierTextManager.CarrierTextCallback) this.f$0).updateCarrierInfo((CarrierTextManager.CarrierTextCallbackInfo) this.f$1);
                break;
            case 1:
                CarrierTextManager carrierTextManager = (CarrierTextManager) this.f$0;
                Executor executor = (Executor) this.f$1;
                boolean isDataCapable = carrierTextManager.mTelephonyManager.isDataCapable();
                if (isDataCapable && carrierTextManager.mNetworkSupported.compareAndSet(false, isDataCapable)) {
                    carrierTextManager.handleSetListening(carrierTextManager.mCarrierTextCallback);
                    executor.execute(new CarrierTextManager$$ExternalSyntheticLambda6(carrierTextManager, 1));
                    break;
                }
                break;
            default:
                ((CarrierTextManager) this.f$1).handleSetListening((CarrierTextManager.CarrierTextCallback) this.f$0);
                break;
        }
    }

    public /* synthetic */ CarrierTextManager$$ExternalSyntheticLambda0(CarrierTextManager carrierTextManager, CarrierTextManager.CarrierTextCallback carrierTextCallback) {
        this.$r8$classId = 2;
        this.f$1 = carrierTextManager;
        this.f$0 = carrierTextCallback;
    }
}
