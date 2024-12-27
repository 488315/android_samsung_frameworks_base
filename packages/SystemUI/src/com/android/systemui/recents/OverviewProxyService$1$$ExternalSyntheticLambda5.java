package com.android.systemui.recents;

import android.os.Bundle;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.searcle.SearcleManager;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OverviewProxyService.AnonymousClass1 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda5(OverviewProxyService.AnonymousClass1 anonymousClass1, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                OverviewProxyService.AnonymousClass1 anonymousClass1 = this.f$0;
                String str = (String) this.f$1;
                SearcleManager searcleManager = OverviewProxyService.this.mSearcleManager;
                searcleManager.invokedPackageName = str;
                searcleManager.startSearcleByHomeKey(false, true);
                break;
            case 1:
                OverviewProxyService.AnonymousClass1 anonymousClass12 = this.f$0;
                int[] iArr = (int[]) this.f$1;
                OverviewProxyService overviewProxyService = OverviewProxyService.this;
                for (int size = ((ArrayList) overviewProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService.mConnectionCallbacks).get(size)).setAssistantOverridesRequested(iArr);
                }
                break;
            default:
                OverviewProxyService.AnonymousClass1 anonymousClass13 = this.f$0;
                Bundle bundle = (Bundle) this.f$1;
                OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                for (int size2 = ((ArrayList) overviewProxyService2.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                    ((OverviewProxyService.OverviewProxyListener) ((ArrayList) overviewProxyService2.mConnectionCallbacks).get(size2)).startAssistant(bundle);
                }
                break;
        }
    }

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda5(OverviewProxyService.AnonymousClass1 anonymousClass1, int[] iArr) {
        this.$r8$classId = 1;
        this.f$0 = anonymousClass1;
        this.f$1 = iArr;
    }
}
