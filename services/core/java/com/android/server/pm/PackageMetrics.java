package com.android.server.pm;

import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageMetrics {
    public final InstallRequest mInstallRequest;
    public final SparseArray mInstallSteps = new SparseArray();
    public final long mInstallStartTimestampMillis = System.currentTimeMillis();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ComponentStateMetrics {
        public int mCallingUid;
        public String mClassName;
        public int mComponentNewState;
        public int mComponentOldState;
        public boolean mIsForWholeApp;
        public String mPackageName;
        public int mUid;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InstallStep {
        public long mDurationMillis;
        public final long mStartTimestampMillis;

        public InstallStep() {
            this.mDurationMillis = -1L;
            this.mStartTimestampMillis = System.currentTimeMillis();
        }

        public InstallStep(long j) {
            this.mStartTimestampMillis = -1L;
            this.mDurationMillis = j;
        }
    }

    public PackageMetrics(InstallRequest installRequest) {
        this.mInstallRequest = installRequest;
    }

    public final void onStepFinished(int i) {
        InstallStep installStep = (InstallStep) this.mInstallSteps.get(i);
        if (installStep != null) {
            installStep.mDurationMillis =
                    System.currentTimeMillis() - installStep.mStartTimestampMillis;
        }
    }

    public final void onStepStarted(int i) {
        this.mInstallSteps.put(i, new InstallStep());
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0192  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void reportInstallationStats(boolean r37) {
        /*
            Method dump skipped, instructions count: 447
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageMetrics.reportInstallationStats(boolean):void");
    }
}
