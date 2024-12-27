package com.android.server.pm;

import com.android.server.pm.GentleUpdateHelper;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class GentleUpdateHelper$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ GentleUpdateHelper f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ GentleUpdateHelper$$ExternalSyntheticLambda3(GentleUpdateHelper gentleUpdateHelper, GentleUpdateHelper.PendingInstallConstraintsCheck pendingInstallConstraintsCheck) {
        this.f$0 = gentleUpdateHelper;
        this.f$1 = pendingInstallConstraintsCheck;
    }

    public /* synthetic */ GentleUpdateHelper$$ExternalSyntheticLambda3(GentleUpdateHelper gentleUpdateHelper, String str, int i) {
        this.f$0 = gentleUpdateHelper;
        this.f$1 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                GentleUpdateHelper gentleUpdateHelper = this.f$0;
                String str = (String) this.f$1;
                int size = gentleUpdateHelper.mPendingChecks.size();
                for (int i = 0; i < size; i++) {
                    GentleUpdateHelper.PendingInstallConstraintsCheck pendingInstallConstraintsCheck = (GentleUpdateHelper.PendingInstallConstraintsCheck) gentleUpdateHelper.mPendingChecks.remove();
                    if (!((ArrayList) gentleUpdateHelper.mAppStateHelper.getDependencyPackages(pendingInstallConstraintsCheck.packageNames)).contains(str) || !gentleUpdateHelper.processPendingCheck(pendingInstallConstraintsCheck, false)) {
                        gentleUpdateHelper.mPendingChecks.add(pendingInstallConstraintsCheck);
                    }
                }
                if (!gentleUpdateHelper.mPendingChecks.isEmpty()) {
                    gentleUpdateHelper.scheduleIdleJob();
                    break;
                }
                break;
            default:
                this.f$0.processPendingCheck((GentleUpdateHelper.PendingInstallConstraintsCheck) this.f$1, false);
                break;
        }
    }
}
