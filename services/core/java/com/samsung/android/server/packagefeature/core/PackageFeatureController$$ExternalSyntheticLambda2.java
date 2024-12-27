package com.samsung.android.server.packagefeature.core;

import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PackageFeatureController$$ExternalSyntheticLambda2
        implements Runnable {
    public final /* synthetic */ PackageFeatureController f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ long f$2;

    public /* synthetic */ PackageFeatureController$$ExternalSyntheticLambda2(
            PackageFeatureController packageFeatureController, String str, long j) {
        this.f$0 = packageFeatureController;
        this.f$1 = str;
        this.f$2 = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PackageFeatureController packageFeatureController = this.f$0;
        String str = this.f$1;
        long j = this.f$2;
        Set groupNames = packageFeatureController.getGroupNames();
        if (str == null) {
            packageFeatureController.mTmpUpdateRequestedGroupNames.addAll(groupNames);
        } else if (!groupNames.contains(str)) {
            return;
        } else {
            ((HashSet) packageFeatureController.mTmpUpdateRequestedGroupNames).add(str);
        }
        packageFeatureController.mHandler.removeCallbacks(
                packageFeatureController.mUpdateGroupDataImmediately);
        packageFeatureController.mHandler.postDelayed(
                packageFeatureController.mUpdateGroupDataImmediately, j);
    }
}
