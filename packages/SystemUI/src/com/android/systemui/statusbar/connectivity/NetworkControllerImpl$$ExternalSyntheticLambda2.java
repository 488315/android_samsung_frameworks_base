package com.android.systemui.statusbar.connectivity;

import android.util.Log;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
                boolean z = NetworkControllerImpl.DEBUG;
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
                boolean z2 = NetworkControllerImpl.DEBUG;
                networkControllerImpl.registerListeners();
                break;
            default:
                networkControllerImpl.handleConfigurationChanged();
                break;
        }
    }
}
