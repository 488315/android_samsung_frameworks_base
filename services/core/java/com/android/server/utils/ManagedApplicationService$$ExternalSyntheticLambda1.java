package com.android.server.utils;

import com.android.server.utils.ManagedApplicationService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ManagedApplicationService$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ long f$1;

    public /* synthetic */ ManagedApplicationService$$ExternalSyntheticLambda1(int i, long j, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ManagedApplicationService managedApplicationService = (ManagedApplicationService) this.f$0;
                managedApplicationService.mEventCb.onServiceEvent(new ManagedApplicationService.LogEvent(managedApplicationService.mComponent, this.f$1, 4));
                break;
            case 1:
                ManagedApplicationService.AnonymousClass1 anonymousClass1 = (ManagedApplicationService.AnonymousClass1) this.f$0;
                long j = this.f$1;
                ManagedApplicationService managedApplicationService2 = ManagedApplicationService.this;
                managedApplicationService2.mEventCb.onServiceEvent(new ManagedApplicationService.LogEvent(managedApplicationService2.mComponent, j, 1));
                break;
            case 2:
                ManagedApplicationService.AnonymousClass1 anonymousClass12 = (ManagedApplicationService.AnonymousClass1) this.f$0;
                long j2 = this.f$1;
                ManagedApplicationService managedApplicationService3 = ManagedApplicationService.this;
                managedApplicationService3.mEventCb.onServiceEvent(new ManagedApplicationService.LogEvent(managedApplicationService3.mComponent, j2, 2));
                break;
            default:
                ManagedApplicationService.AnonymousClass1 anonymousClass13 = (ManagedApplicationService.AnonymousClass1) this.f$0;
                long j3 = this.f$1;
                ManagedApplicationService managedApplicationService4 = ManagedApplicationService.this;
                managedApplicationService4.mEventCb.onServiceEvent(new ManagedApplicationService.LogEvent(managedApplicationService4.mComponent, j3, 3));
                break;
        }
    }
}
