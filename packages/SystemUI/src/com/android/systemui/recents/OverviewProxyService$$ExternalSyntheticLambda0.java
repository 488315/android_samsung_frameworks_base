package com.android.systemui.recents;

import android.util.Log;
import com.android.systemui.shade.ShadeViewController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverviewProxyService$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OverviewProxyService f$0;

    public /* synthetic */ OverviewProxyService$$ExternalSyntheticLambda0(OverviewProxyService overviewProxyService, int i) {
        this.$r8$classId = i;
        this.f$0 = overviewProxyService;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        OverviewProxyService overviewProxyService = this.f$0;
        switch (i) {
            case 0:
                overviewProxyService.internalConnectToCurrentUser("runnable: startConnectionToCurrentUser");
                break;
            case 1:
                overviewProxyService.getClass();
                Log.w("OverviewProxyService", "Binder supposed established connection but actual connection to service timed out, trying again");
                overviewProxyService.retryConnectionWithBackoff();
                break;
            default:
                overviewProxyService.mInputFocusTransferStarted = false;
                ((ShadeViewController) overviewProxyService.mShadeViewControllerLazy.get()).cancelInputFocusTransfer();
                break;
        }
    }
}
