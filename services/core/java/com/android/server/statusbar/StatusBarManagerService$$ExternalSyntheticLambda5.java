package com.android.server.statusbar;

import com.android.server.policy.GlobalActions;

public final /* synthetic */ class StatusBarManagerService$$ExternalSyntheticLambda5
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StatusBarManagerService f$0;

    public /* synthetic */ StatusBarManagerService$$ExternalSyntheticLambda5(
            StatusBarManagerService statusBarManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = statusBarManagerService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        StatusBarManagerService statusBarManagerService = this.f$0;
        switch (i) {
            case 0:
                statusBarManagerService.mActivityManagerInternal.restart();
                return;
            case 1:
                GlobalActions globalActions = statusBarManagerService.mGlobalActionListener;
                if (globalActions == null) {
                    return;
                }
                globalActions.onGlobalActionsAvailableChanged(statusBarManagerService.mBar != null);
                return;
            default:
                synchronized (statusBarManagerService.mLock) {
                    statusBarManagerService.setUdfpsRefreshRateCallback(
                            statusBarManagerService.mUdfpsRefreshRateRequestCallback);
                    statusBarManagerService.setBiometicContextListener(
                            statusBarManagerService.mBiometricContextListener);
                }
                return;
        }
    }
}
