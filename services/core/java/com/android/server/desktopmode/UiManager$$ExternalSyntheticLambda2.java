package com.android.server.desktopmode;

import android.os.RemoteException;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.DesktopModeUiConstants;
import com.samsung.android.desktopmode.IDesktopModeUiService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class UiManager$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ UiManager f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ UiManager$$ExternalSyntheticLambda2(UiManager uiManager, int i, int i2, int i3) {
        this.$r8$classId = i3;
        this.f$0 = uiManager;
        this.f$1 = i;
        this.f$2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                UiManager uiManager = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                uiManager.getClass();
                if (DesktopModeFeature.DEBUG) {
                    Log.d("[DMS]UiManager", "handleDismissOverlay(), where=" + DesktopModeUiConstants.whereToString(i) + ", type=" + DesktopModeUiConstants.typeToString(i2));
                }
                IDesktopModeUiService iDesktopModeUiService = uiManager.mService;
                if (iDesktopModeUiService != null) {
                    try {
                        iDesktopModeUiService.dismissOverlay(i, i2);
                    } catch (RemoteException e) {
                        uiManager.handleRemoteException(e);
                    }
                }
                uiManager.mPendingUiCommands.queue(901, i2, i, null);
                uiManager.postUnbindServiceRunnable();
                break;
            default:
                UiManager uiManager2 = this.f$0;
                int i3 = this.f$1;
                int i4 = this.f$2;
                uiManager2.getClass();
                if (DesktopModeFeature.DEBUG) {
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i3, "handleDismissDialog(), displayId=", ", type=");
                    m.append(DesktopModeUiConstants.typeToString(i4));
                    Log.d("[DMS]UiManager", m.toString());
                }
                IDesktopModeUiService iDesktopModeUiService2 = uiManager2.mService;
                if (iDesktopModeUiService2 != null) {
                    try {
                        iDesktopModeUiService2.dismissDialog(i3, i4);
                    } catch (RemoteException e2) {
                        uiManager2.handleRemoteException(e2);
                    }
                }
                uiManager2.mPendingUiCommands.queue(901, i4, i3, null);
                uiManager2.postUnbindServiceRunnable();
                break;
        }
    }
}
