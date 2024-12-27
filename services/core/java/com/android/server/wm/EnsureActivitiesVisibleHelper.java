package com.android.server.wm;

public final class EnsureActivitiesVisibleHelper {
    public boolean mAboveTop;
    public boolean mBehindFullyOccludedContainer;
    public boolean mNotifyClients;
    public ActivityRecord mStarting;
    public final TaskFragment mTaskFragment;
    public ActivityRecord mTopRunningActivity;

    public EnsureActivitiesVisibleHelper(TaskFragment taskFragment) {
        this.mTaskFragment = taskFragment;
    }

    /* JADX WARN: Code restructure failed: missing block: B:187:0x0123, code lost:

       if (r11.mLaunchTaskBehind != false) goto L80;
    */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x011f, code lost:

       if (r14.getActivity(new com.android.server.wm.PopOverState$$ExternalSyntheticLambda0(1, r13), r12, false, false) != null) goto L78;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void process(boolean r17, com.android.server.wm.ActivityRecord r18) {
        /*
            Method dump skipped, instructions count: 663
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.EnsureActivitiesVisibleHelper.process(boolean,"
                    + " com.android.server.wm.ActivityRecord):void");
    }
}
