package com.android.server.appop;

import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppOpsUidStateTrackerImpl$1$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Executor f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ AppOpsUidStateTrackerImpl$1$$ExternalSyntheticLambda0(
            AppOpsService$$ExternalSyntheticLambda11 appOpsService$$ExternalSyntheticLambda11,
            Runnable runnable,
            int i) {
        this.$r8$classId = i;
        this.f$0 = appOpsService$$ExternalSyntheticLambda11;
        this.f$1 = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.execute(this.f$1);
                break;
            default:
                this.f$0.execute(this.f$1);
                break;
        }
    }
}
