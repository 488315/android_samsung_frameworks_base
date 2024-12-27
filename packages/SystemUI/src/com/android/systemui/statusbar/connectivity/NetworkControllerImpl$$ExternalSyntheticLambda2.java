package com.android.systemui.statusbar.connectivity;

import android.util.Log;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NetworkControllerImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NetworkControllerImpl f$0;

    public /* synthetic */ NetworkControllerImpl$$ExternalSyntheticLambda2(NetworkControllerImpl networkControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = networkControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        NetworkControllerImpl networkControllerImpl = this.f$0;
        switch (i) {
            case 0:
                if (networkControllerImpl.mLastServiceState == null) {
                    networkControllerImpl.mLastServiceState = networkControllerImpl.mPhone.getServiceState();
                    if (networkControllerImpl.mMobileSignalControllers.size() == 0) {
                        networkControllerImpl.recalculateEmergency();
                        break;
                    }
                }
                break;
            case 1:
                networkControllerImpl.updateConnectivity();
                break;
            case 2:
                networkControllerImpl.mInternetDialogManager.create(networkControllerImpl.mAccessPoints.canConfigMobileData(), networkControllerImpl.mAccessPoints.canConfigWifi(), null);
                break;
            case 3:
                networkControllerImpl.recalculateEmergency();
                break;
            case 4:
                if (NetworkControllerImpl.DEBUG) {
                    networkControllerImpl.getClass();
                    Log.d("NetworkController", ": mClearForceValidated");
                }
                networkControllerImpl.mForceCellularValidated = false;
                networkControllerImpl.updateConnectivity();
                break;
            case 5:
                networkControllerImpl.registerListeners();
                break;
            default:
                networkControllerImpl.handleConfigurationChanged();
                break;
        }
    }
}
