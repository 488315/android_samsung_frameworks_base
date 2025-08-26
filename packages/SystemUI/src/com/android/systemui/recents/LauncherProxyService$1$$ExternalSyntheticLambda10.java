package com.android.systemui.recents;

import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.recents.LauncherProxyService;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final /* synthetic */ class LauncherProxyService$1$$ExternalSyntheticLambda10 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LauncherProxyService.AnonymousClass1 f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ LauncherProxyService$1$$ExternalSyntheticLambda10(int i, LauncherProxyService.AnonymousClass1 anonymousClass1, String str, boolean z) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = z;
        this.f$2 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LauncherProxyService.AnonymousClass1 anonymousClass1 = this.f$0;
                LauncherProxyService.this.mHandler.post(new LauncherProxyService$1$$ExternalSyntheticLambda10(1, anonymousClass1, this.f$2, this.f$1));
                break;
            default:
                LauncherProxyService.AnonymousClass1 anonymousClass12 = this.f$0;
                boolean z = this.f$1;
                String str = this.f$2;
                LauncherProxyService launcherProxyService = LauncherProxyService.this;
                GestureType gestureTypeValueOf = GestureType.valueOf(str);
                for (int size = ((ArrayList) launcherProxyService.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService.mConnectionCallbacks).get(size)).updateContextualEduStats(z, gestureTypeValueOf);
                }
                break;
        }
    }
}
