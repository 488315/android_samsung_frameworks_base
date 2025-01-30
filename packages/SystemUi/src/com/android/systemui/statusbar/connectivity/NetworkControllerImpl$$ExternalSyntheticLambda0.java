package com.android.systemui.statusbar.connectivity;

import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.qs.tiles.dialog.InternetDialog;
import com.android.systemui.qs.tiles.dialog.InternetDialogFactory;
import com.android.systemui.qs.tiles.dialog.InternetDialogFactoryKt;
import com.android.systemui.settings.UserTrackerImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NetworkControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NetworkControllerImpl f$0;

    public /* synthetic */ NetworkControllerImpl$$ExternalSyntheticLambda0(NetworkControllerImpl networkControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = networkControllerImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                this.f$0.updateConnectivity();
                break;
            case 1:
                NetworkControllerImpl networkControllerImpl = this.f$0;
                if (networkControllerImpl.mLastServiceState == null) {
                    networkControllerImpl.mLastServiceState = networkControllerImpl.mPhone.getServiceState();
                    if (networkControllerImpl.mMobileSignalControllers.size() == 0) {
                        networkControllerImpl.recalculateEmergency();
                        break;
                    }
                }
                break;
            case 2:
                this.f$0.recalculateEmergency();
                break;
            case 3:
                this.f$0.handleConfigurationChanged();
                break;
            case 4:
                this.f$0.handleConfigurationChanged();
                break;
            case 5:
                NetworkControllerImpl networkControllerImpl2 = this.f$0;
                InternetDialogFactory internetDialogFactory = networkControllerImpl2.mInternetDialogFactory;
                AccessPointControllerImpl accessPointControllerImpl = networkControllerImpl2.mAccessPoints;
                if (!accessPointControllerImpl.mUserManager.hasUserRestriction("no_config_mobile_networks", UserHandle.of(accessPointControllerImpl.mCurrentUser)) && ((UserTrackerImpl) accessPointControllerImpl.mUserTracker).getUserInfo().isAdmin()) {
                    z = true;
                }
                boolean z2 = z;
                boolean canConfigWifi = networkControllerImpl2.mAccessPoints.canConfigWifi();
                internetDialogFactory.getClass();
                if (InternetDialogFactory.internetDialog == null) {
                    InternetDialog internetDialog = new InternetDialog(internetDialogFactory.context, internetDialogFactory, internetDialogFactory.internetDialogController, z2, canConfigWifi, true, internetDialogFactory.uiEventLogger, internetDialogFactory.dialogLaunchAnimator, internetDialogFactory.handler, internetDialogFactory.executor, internetDialogFactory.keyguardStateController);
                    InternetDialogFactory.internetDialog = internetDialog;
                    internetDialog.show();
                    break;
                } else if (InternetDialogFactoryKt.DEBUG) {
                    Log.d("InternetDialogFactory", "InternetDialog is showing, do not create it twice.");
                    break;
                }
                break;
            case 6:
                NetworkControllerImpl networkControllerImpl3 = this.f$0;
                if (NetworkControllerImpl.DEBUG) {
                    networkControllerImpl3.getClass();
                    Log.d("NetworkController", ": mClearForceValidated");
                }
                networkControllerImpl3.mForceCellularValidated = false;
                networkControllerImpl3.updateConnectivity();
                break;
            default:
                this.f$0.registerListeners();
                break;
        }
    }
}
