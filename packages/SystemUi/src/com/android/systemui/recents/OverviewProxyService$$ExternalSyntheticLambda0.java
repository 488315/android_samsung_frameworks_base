package com.android.systemui.recents;

import android.util.Log;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        switch (this.$r8$classId) {
            case 0:
                this.f$0.internalConnectToCurrentUser("runnable: startConnectionToCurrentUser");
                break;
            case 1:
                OverviewProxyService overviewProxyService = this.f$0;
                overviewProxyService.getClass();
                Log.w("OverviewProxyService", "Binder supposed established connection but actual connection to service timed out, trying again");
                overviewProxyService.retryConnectionWithBackoff();
                break;
            default:
                OverviewProxyService overviewProxyService2 = this.f$0;
                ((Optional) overviewProxyService2.mCentralSurfacesOptionalLazy.get()).ifPresent(new OverviewProxyService$$ExternalSyntheticLambda4(overviewProxyService2, 0));
                break;
        }
    }
}
