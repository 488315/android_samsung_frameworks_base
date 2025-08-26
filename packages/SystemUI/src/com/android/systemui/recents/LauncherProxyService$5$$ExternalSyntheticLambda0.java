package com.android.systemui.recents;

import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.recents.LauncherProxyService;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final /* synthetic */ class LauncherProxyService$5$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ LauncherProxyService$5$$ExternalSyntheticLambda0(LauncherProxyService.AnonymousClass1 anonymousClass1, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = anonymousClass1;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                LauncherProxyService.AnonymousClass5 anonymousClass5 = (LauncherProxyService.AnonymousClass5) this.f$0;
                boolean z = this.f$1;
                LauncherProxyService launcherProxyService = anonymousClass5.this$0;
                SysUiState flag = launcherProxyService.mDefaultDisplaySysUIState.setFlag(33554432L, z);
                launcherProxyService.mContext.getDisplayId();
                ((SysUiStateImpl) flag).commitUpdate();
                break;
            case 1:
                LauncherProxyService.AnonymousClass1 anonymousClass1 = (LauncherProxyService.AnonymousClass1) this.f$0;
                anonymousClass1.this$0.mHandler.post(new LauncherProxyService$5$$ExternalSyntheticLambda0(anonymousClass1, this.f$1, 3));
                break;
            case 2:
                LauncherProxyService.AnonymousClass1 anonymousClass12 = (LauncherProxyService.AnonymousClass1) this.f$0;
                boolean z2 = this.f$1;
                LauncherProxyService launcherProxyService2 = anonymousClass12.this$0;
                for (int size = ((ArrayList) launcherProxyService2.mConnectionCallbacks).size() - 1; size >= 0; size--) {
                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService2.mConnectionCallbacks).get(size)).onTaskbarAutohideSuspend(z2);
                }
                break;
            default:
                LauncherProxyService.AnonymousClass1 anonymousClass13 = (LauncherProxyService.AnonymousClass1) this.f$0;
                boolean z3 = this.f$1;
                LauncherProxyService launcherProxyService3 = anonymousClass13.this$0;
                for (int size2 = ((ArrayList) launcherProxyService3.mConnectionCallbacks).size() - 1; size2 >= 0; size2--) {
                    ((LauncherProxyService.LauncherProxyListener) ((ArrayList) launcherProxyService3.mConnectionCallbacks).get(size2)).onHomeRotationEnabled(z3);
                }
                break;
        }
    }

    public /* synthetic */ LauncherProxyService$5$$ExternalSyntheticLambda0(LauncherProxyService.AnonymousClass5 anonymousClass5, boolean z) {
        this.$r8$classId = 0;
        this.f$0 = anonymousClass5;
        this.f$1 = z;
    }
}
