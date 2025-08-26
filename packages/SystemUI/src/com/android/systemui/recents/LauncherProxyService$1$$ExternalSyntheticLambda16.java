package com.android.systemui.recents;

import android.os.Bundle;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.searcle.SearcleManager;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final /* synthetic */ class LauncherProxyService$1$$ExternalSyntheticLambda16 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LauncherProxyService.AnonymousClass1 f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ LauncherProxyService$1$$ExternalSyntheticLambda16(LauncherProxyService.AnonymousClass1 anonymousClass1, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LauncherProxyService.AnonymousClass1 anonymousClass1 = this.f$0;
                Bundle bundle = (Bundle) this.f$1;
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                for (int size = ((ArrayList) launcherProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService.mConnectionCallbacks).get(size)).startAssistant(bundle);
                }
                break;
            case 1:
                LauncherProxyService.AnonymousClass1 anonymousClass12 = this.f$0;
                int[] iArr = (int[]) this.f$1;
                LauncherProxyService launcherProxyService2 = LauncherProxyService.this;
                for (int size2 = ((ArrayList) launcherProxyService2.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService2.mConnectionCallbacks).get(size2)).setAssistantOverridesRequested(iArr);
                }
                break;
            default:
                LauncherProxyService.AnonymousClass1 anonymousClass13 = this.f$0;
                String str = (String) this.f$1;
                SearcleManager searcleManager = LauncherProxyService.this.mSearcleManager;
                searcleManager.invokedPackageName = str;
                searcleManager.startSearcleByHomeKey(false, true);
                break;
        }
    }

    public /* synthetic */ LauncherProxyService$1$$ExternalSyntheticLambda16(LauncherProxyService.AnonymousClass1 anonymousClass1, int[] iArr) {
        this.$r8$classId = 1;
        this.f$0 = anonymousClass1;
        this.f$1 = iArr;
    }
}
