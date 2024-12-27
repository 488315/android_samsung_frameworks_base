package com.android.systemui.shade.carrier;

import com.android.keyguard.CarrierTextManager;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.systemui.shade.carrier.ShadeCarrierGroupController;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ShadeCarrierGroupController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShadeCarrierGroupController f$0;

    public /* synthetic */ ShadeCarrierGroupController$$ExternalSyntheticLambda0(ShadeCarrierGroupController shadeCarrierGroupController, int i) {
        this.$r8$classId = i;
        this.f$0 = shadeCarrierGroupController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ShadeCarrierGroupController shadeCarrierGroupController = this.f$0;
        switch (i) {
            case 0:
                boolean z = shadeCarrierGroupController.mListening;
                ShadeCarrierGroupController.AnonymousClass1 anonymousClass1 = shadeCarrierGroupController.mSignalCallback;
                CarrierTextManager carrierTextManager = shadeCarrierGroupController.mCarrierTextManager;
                NetworkController networkController = shadeCarrierGroupController.mNetworkController;
                if (!z) {
                    ((NetworkControllerImpl) networkController).removeCallback(anonymousClass1);
                    carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(carrierTextManager, null));
                    break;
                } else {
                    NetworkControllerImpl networkControllerImpl = (NetworkControllerImpl) networkController;
                    if (networkControllerImpl.mPhone.getPhoneType() != 0) {
                        networkControllerImpl.addCallback(anonymousClass1);
                    }
                    carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(carrierTextManager, shadeCarrierGroupController.mCallback));
                    break;
                }
            default:
                shadeCarrierGroupController.handleUpdateState();
                break;
        }
    }
}
