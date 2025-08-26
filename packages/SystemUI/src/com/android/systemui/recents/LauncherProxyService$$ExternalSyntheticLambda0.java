package com.android.systemui.recents;

import android.util.Log;
import com.android.systemui.shade.ShadeViewController;

/* loaded from: classes2.dex */
public final /* synthetic */ class LauncherProxyService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LauncherProxyService f$0;

    public /* synthetic */ LauncherProxyService$$ExternalSyntheticLambda0(LauncherProxyService launcherProxyService, int i) {
        this.$r8$classId = i;
        this.f$0 = launcherProxyService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        LauncherProxyService launcherProxyService = this.f$0;
        switch (i) {
            case 0:
                launcherProxyService.internalConnectToCurrentUser("runnable: startConnectionToCurrentUser");
                break;
            case 1:
                launcherProxyService.getClass();
                Log.w("LauncherProxyService", "Binder supposed established connection but actual connection to service timed out, trying again");
                launcherProxyService.retryConnectionWithBackoff();
                break;
            case 2:
                launcherProxyService.getClass();
                Log.w("LauncherProxyService", "Timed out waiting for previous service to clean up, binding to new one");
                launcherProxyService.mIsPrevServiceCleanedUp = true;
                launcherProxyService.maybeBindService();
                break;
            default:
                launcherProxyService.mInputFocusTransferStarted = false;
                ((ShadeViewController) launcherProxyService.mShadeViewControllerLazy.get()).cancelInputFocusTransfer();
                break;
        }
    }
}
