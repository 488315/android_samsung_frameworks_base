package com.android.server.desktopmode;

import android.os.RemoteException;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.DesktopModeUiConstants;
import com.samsung.android.desktopmode.IDesktopModeUiService;

import java.util.ArrayList;

public final /* synthetic */ class UiManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UiManager f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ UiManager$$ExternalSyntheticLambda0(UiManager uiManager, int i) {
        this.$r8$classId = i;
        this.f$0 = uiManager;
        this.f$1 = 400;
    }

    public /* synthetic */ UiManager$$ExternalSyntheticLambda0(UiManager uiManager, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = uiManager;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.handleRemoveNotification(this.f$1);
                break;
            case 1:
                this.f$0.handleShowNotification(this.f$1);
                break;
            case 2:
                UiManager uiManager = this.f$0;
                int i = this.f$1;
                uiManager.getClass();
                if (DesktopModeFeature.DEBUG) {
                    Log.d(
                            "[DMS]UiManager",
                            "handleFinishActivity(), type="
                                    + DesktopModeUiConstants.typeToString(i));
                }
                IDesktopModeUiService iDesktopModeUiService = uiManager.mService;
                if (iDesktopModeUiService != null) {
                    try {
                        iDesktopModeUiService.finishActivity(i);
                    } catch (RemoteException e) {
                        uiManager.handleRemoteException(e);
                    }
                }
                uiManager.mPendingUiCommands.queue(901, i, -1, null);
                uiManager.postUnbindServiceRunnable();
                break;
            case 3:
                UiManager uiManager2 = this.f$0;
                int i2 = this.f$1;
                if (uiManager2.mCurrentUserId != i2) {
                    uiManager2.mCurrentUserId = i2;
                    ((ArrayList) uiManager2.mPendingUiCommands.mUiCommands).clear();
                    if (uiManager2.mBound) {
                        uiManager2.bindService();
                        break;
                    }
                }
                break;
            case 4:
                this.f$0.handleShowNotification(this.f$1);
                break;
            case 5:
                this.f$0.handleShowNotification(this.f$1);
                break;
            case 6:
                this.f$0.handleShowNotification(this.f$1);
                break;
            case 7:
                this.f$0.handleRemoveNotification(this.f$1);
                break;
            case 8:
                this.f$0.handleRemoveNotification(this.f$1);
                break;
            case 9:
                this.f$0.handleNavBarIcon();
                break;
            case 10:
                this.f$0.handleRemoveNavBarIcon();
                break;
            case 11:
                this.f$0.handleRemoveNavBarIcon();
                break;
            case 12:
                this.f$0.handleRemoveNavBarIcon();
                break;
            case 13:
                this.f$0.handleNavBarIcon();
                break;
            default:
                this.f$0.handleNavBarIcon();
                break;
        }
    }
}
