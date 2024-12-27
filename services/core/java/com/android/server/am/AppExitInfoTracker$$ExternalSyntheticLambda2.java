package com.android.server.am;

import android.os.SystemProperties;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppExitInfoTracker$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppExitInfoTracker f$0;

    public /* synthetic */ AppExitInfoTracker$$ExternalSyntheticLambda2(
            AppExitInfoTracker appExitInfoTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = appExitInfoTracker;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AppExitInfoTracker appExitInfoTracker = this.f$0;
        switch (i) {
            case 0:
                appExitInfoTracker.persistProcessExitInfo();
                break;
            default:
                appExitInfoTracker.getClass();
                SystemProperties.set(
                        "persist.sys.lmk.reportkills",
                        Boolean.toString(
                                SystemProperties.getBoolean("sys.lmk.reportkills", false)));
                appExitInfoTracker.loadExistingProcessExitInfo();
                break;
        }
    }
}
