package com.android.server.pm;

import com.android.server.pm.pkg.mutate.PackageStateMutator;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */
class PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ PackageManagerService$IPackageManagerImpl$$ExternalSyntheticLambda0(
            int i, boolean z) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        PackageStateMutator.StateWriteWrapper stateWriteWrapper =
                (PackageStateMutator.StateWriteWrapper) obj;
        switch (i) {
            case 0:
                PackageSetting packageSetting = stateWriteWrapper.mState;
                if (packageSetting != null) {
                    packageSetting.setBoolean(2, z);
                    packageSetting.onChanged$2();
                    break;
                }
                break;
            default:
                PackageSetting packageSetting2 = stateWriteWrapper.mState;
                if (packageSetting2 != null) {
                    if (!z) {
                        packageSetting2.setPrivateFlags(packageSetting2.mPkgPrivateFlags & (-513));
                        break;
                    } else {
                        packageSetting2.setPrivateFlags(packageSetting2.mPkgPrivateFlags | 512);
                        break;
                    }
                }
                break;
        }
    }
}
