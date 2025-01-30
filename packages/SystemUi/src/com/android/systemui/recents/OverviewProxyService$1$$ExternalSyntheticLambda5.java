package com.android.systemui.recents;

import android.os.Binder;
import com.android.systemui.model.SysUiState;
import com.android.systemui.recents.OverviewProxyService;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Binder f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda5(Binder binder, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = binder;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                OverviewProxyService.BinderC23081 binderC23081 = (OverviewProxyService.BinderC23081) this.f$0;
                binderC23081.this$0.mHandler.post(new OverviewProxyService$1$$ExternalSyntheticLambda5(binderC23081, this.f$1, 3));
                break;
            case 1:
                OverviewProxyService.BinderC23081 binderC230812 = (OverviewProxyService.BinderC23081) this.f$0;
                boolean z = this.f$1;
                ArrayList arrayList = (ArrayList) binderC230812.this$0.mConnectionCallbacks;
                int size = arrayList.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    } else {
                        ((OverviewProxyService.OverviewProxyListener) arrayList.get(size)).onTaskbarAutohideSuspend(z);
                    }
                }
            case 2:
                OverviewProxyService.BinderC23081 binderC230813 = (OverviewProxyService.BinderC23081) this.f$0;
                int size2 = ((ArrayList) binderC230813.this$0.mConnectionCallbacks).size();
                while (true) {
                    size2--;
                    if (size2 < 0) {
                        break;
                    } else {
                        ((OverviewProxyService.OverviewProxyListener) ((ArrayList) binderC230813.this$0.mConnectionCallbacks).get(size2)).onOverviewShown();
                    }
                }
            case 3:
                OverviewProxyService.BinderC23081 binderC230814 = (OverviewProxyService.BinderC23081) this.f$0;
                boolean z2 = this.f$1;
                ArrayList arrayList2 = (ArrayList) binderC230814.this$0.mConnectionCallbacks;
                int size3 = arrayList2.size();
                while (true) {
                    size3--;
                    if (size3 < 0) {
                        break;
                    } else {
                        ((OverviewProxyService.OverviewProxyListener) arrayList2.get(size3)).onHomeRotationEnabled(z2);
                    }
                }
            default:
                OverviewProxyService.C23114 c23114 = (OverviewProxyService.C23114) this.f$0;
                boolean z3 = this.f$1;
                OverviewProxyService overviewProxyService = c23114.this$0;
                SysUiState sysUiState = overviewProxyService.mSysUiState;
                sysUiState.setFlag(33554432L, z3);
                sysUiState.commitUpdate(overviewProxyService.mContext.getDisplayId());
                break;
        }
    }
}
