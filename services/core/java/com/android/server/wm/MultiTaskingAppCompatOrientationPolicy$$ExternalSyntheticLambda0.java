package com.android.server.wm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
