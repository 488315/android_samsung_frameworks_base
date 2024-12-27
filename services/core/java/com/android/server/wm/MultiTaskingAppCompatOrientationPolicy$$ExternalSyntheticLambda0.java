package com.android.server.wm;

public final /* synthetic */ class MultiTaskingAppCompatOrientationPolicy$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivityRecord f$0;

    public /* synthetic */ MultiTaskingAppCompatOrientationPolicy$$ExternalSyntheticLambda0(
            int i, ActivityRecord activityRecord) {
        this.$r8$classId = i;
        this.f$0 = activityRecord;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        ActivityRecord activityRecord = this.f$0;
        switch (i) {
            case 0:
                activityRecord.onConfigurationChanged(
                        activityRecord.getParent().getConfiguration());
                break;
            default:
                activityRecord.recomputeConfiguration();
                break;
        }
    }
}
