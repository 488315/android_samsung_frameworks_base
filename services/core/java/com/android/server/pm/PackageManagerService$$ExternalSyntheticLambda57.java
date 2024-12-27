package com.android.server.pm;

import android.content.pm.PackageManagerInternal;
import android.util.Slog;

import com.android.server.compat.CompatChange;
import com.android.server.pm.pkg.AndroidPackage;

public final /* synthetic */ class PackageManagerService$$ExternalSyntheticLambda57
        implements ApkChecksums.Injector.Producer, CompatChange.ChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PackageManagerService f$0;

    public /* synthetic */ PackageManagerService$$ExternalSyntheticLambda57(
            PackageManagerService packageManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = packageManagerService;
    }

    @Override // com.android.server.compat.CompatChange.ChangeListener
    public void onCompatChange(String str) {
        PackageManagerService packageManagerService = this.f$0;
        synchronized (packageManagerService.mInstallLock) {
            try {
                Computer snapshotComputer = packageManagerService.snapshotComputer();
                PackageSetting packageStateInternal = snapshotComputer.getPackageStateInternal(str);
                if (packageStateInternal == null) {
                    Slog.e("PackageManager", "Failed to find package setting " + str);
                    return;
                }
                AndroidPackage androidPackage = packageStateInternal.pkg;
                SharedUserSetting sharedUser =
                        snapshotComputer.getSharedUser(packageStateInternal.mSharedUserAppId);
                String seInfo = packageStateInternal.getSeInfo();
                if (androidPackage == null) {
                    Slog.e("PackageManager", "Failed to find package " + str);
                    return;
                }
                String seInfo2 =
                        SELinuxMMAC.getSeInfo(
                                packageStateInternal,
                                androidPackage,
                                sharedUser,
                                packageManagerService.mInjector.getCompatibility());
                if (!seInfo2.equals(seInfo)) {
                    Slog.i(
                            "PackageManager",
                            "Updating seInfo for package "
                                    + str
                                    + " from: "
                                    + seInfo
                                    + " to: "
                                    + seInfo2);
                    packageManagerService.commitPackageStateMutation(
                            null,
                            str,
                            new PackageManagerService$$ExternalSyntheticLambda40(0, seInfo2));
                    packageManagerService.mAppDataHelper.prepareAppDataAfterInstallLIF(
                            androidPackage);
                }
            } finally {
            }
        }
    }

    @Override // com.android.server.pm.ApkChecksums.Injector.Producer
    public Object produce() {
        switch (this.$r8$classId) {
            case 0:
                return this.f$0.mContext;
            case 1:
                return (PackageManagerInternal)
                        this.f$0.mInjector.mGetLocalServiceProducer.produce(
                                PackageManagerInternal.class);
            case 2:
                return this.f$0.mContext;
            default:
                return (PackageManagerInternal)
                        this.f$0.mInjector.mGetLocalServiceProducer.produce(
                                PackageManagerInternal.class);
        }
    }
}
