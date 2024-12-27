package com.android.server.am;

public final /* synthetic */ class AppStartInfoTracker$$ExternalSyntheticLambda4
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AppStartInfoTracker f$0;

    public /* synthetic */ AppStartInfoTracker$$ExternalSyntheticLambda4(
            AppStartInfoTracker appStartInfoTracker, int i) {
        this.$r8$classId = i;
        this.f$0 = appStartInfoTracker;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AppStartInfoTracker appStartInfoTracker = this.f$0;
        switch (i) {
            case 0:
                appStartInfoTracker.persistProcessStartInfo();
                break;
            default:
                appStartInfoTracker.loadExistingProcessStartInfo();
                break;
        }
    }
}
