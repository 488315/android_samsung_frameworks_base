package com.samsung.android.server.packagefeature.core;

import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PackageFeatureController$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PackageFeatureController f$0;

    public /* synthetic */ PackageFeatureController$$ExternalSyntheticLambda0(
            PackageFeatureController packageFeatureController, int i) {
        this.$r8$classId = i;
        this.f$0 = packageFeatureController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        PackageFeatureController packageFeatureController = this.f$0;
        switch (i) {
            case 0:
                synchronized (packageFeatureController.mLock) {
                    try {
                        packageFeatureController.mUpdateRequestedGroupNames.addAll(
                                packageFeatureController.mTmpUpdateRequestedGroupNames);
                        ((HashSet) packageFeatureController.mTmpUpdateRequestedGroupNames).clear();
                        if (((HashSet) packageFeatureController.mUpdateRequestedGroupNames)
                                .isEmpty()) {
                            return;
                        }
                        packageFeatureController.mLogger.log(
                                4,
                                "updateGroupData, Queue="
                                        + packageFeatureController.mUpdateRequestedGroupNames,
                                null);
                        packageFeatureController.mLock.notifyAll();
                        return;
                    } finally {
                    }
                }
            default:
                packageFeatureController.start();
                return;
        }
    }
}
