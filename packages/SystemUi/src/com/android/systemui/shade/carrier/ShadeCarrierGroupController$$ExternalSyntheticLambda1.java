package com.android.systemui.shade.carrier;

import com.android.keyguard.CarrierTextManager;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.systemui.shade.carrier.ShadeCarrierGroupController;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShadeCarrierGroupController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ShadeCarrierGroupController f$0;

    public /* synthetic */ ShadeCarrierGroupController$$ExternalSyntheticLambda1(ShadeCarrierGroupController shadeCarrierGroupController, int i) {
        this.$r8$classId = i;
        this.f$0 = shadeCarrierGroupController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.handleUpdateState();
                break;
            default:
                ShadeCarrierGroupController shadeCarrierGroupController = this.f$0;
                boolean z = shadeCarrierGroupController.mListening;
                ShadeCarrierGroupController.C24641 c24641 = shadeCarrierGroupController.mSignalCallback;
                CarrierTextManager carrierTextManager = shadeCarrierGroupController.mCarrierTextManager;
                NetworkController networkController = shadeCarrierGroupController.mNetworkController;
                if (!z) {
                    ((NetworkControllerImpl) networkController).removeCallback(c24641);
                    carrierTextManager.getClass();
                    carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(carrierTextManager, (CarrierTextManager.CarrierTextCallback) null));
                    break;
                } else {
                    NetworkControllerImpl networkControllerImpl = (NetworkControllerImpl) networkController;
                    if (networkControllerImpl.mPhone.getPhoneType() != 0) {
                        networkControllerImpl.addCallback(c24641);
                    }
                    carrierTextManager.getClass();
                    carrierTextManager.mBgExecutor.execute(new CarrierTextManager$$ExternalSyntheticLambda0(carrierTextManager, shadeCarrierGroupController.mCallback));
                    break;
                }
        }
    }
}
