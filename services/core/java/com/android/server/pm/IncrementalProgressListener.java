package com.android.server.pm;

import android.content.pm.IPackageLoadingProgressCallback;
import com.android.server.pm.pkg.mutate.PackageStateMutator;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class IncrementalProgressListener extends IPackageLoadingProgressCallback.Stub {
    public final String mPackageName;
    public final PackageManagerService mPm;

    public IncrementalProgressListener(PackageManagerService packageManagerService, String str) {
        this.mPackageName = str;
        this.mPm = packageManagerService;
    }

    public final void onPackageLoadingProgressChanged(final float f) {
        PackageSetting packageStateInternal = this.mPm.snapshotComputer().getPackageStateInternal(this.mPackageName);
        if (packageStateInternal != null && packageStateInternal.isLoading()) {
            this.mPm.commitPackageStateMutation(null, this.mPackageName, new Consumer() { // from class: com.android.server.pm.IncrementalProgressListener$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    float f2 = f;
                    PackageSetting packageSetting = ((PackageStateMutator.StateWriteWrapper) obj).mState;
                    if (packageSetting == null || packageSetting.mLoadingProgress >= f2) {
                        return;
                    }
                    packageSetting.mLoadingProgress = f2;
                    packageSetting.onChanged$2();
                }
            });
            if (Math.abs(1.0f - f) < 1.0E-8f) {
                this.mPm.commitPackageStateMutation(null, this.mPackageName, new IncrementalProgressListener$$ExternalSyntheticLambda1());
                this.mPm.mIncrementalManager.unregisterLoadingProgressCallbacks(packageStateInternal.mPathString);
                this.mPm.scheduleWriteSettings();
            }
        }
    }
}
