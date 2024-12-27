package com.android.server.rollback;

import android.content.rollback.PackageRollbackInfo;
import android.os.storage.StorageManager;
import android.util.Slog;

import com.android.internal.util.Preconditions;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.pm.ApexManager;
import com.android.server.pm.Installer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class AppDataRollbackHelper {
    public final ApexManager mApexManager;
    public final Installer mInstaller;

    public AppDataRollbackHelper(Installer installer) {
        this.mInstaller = installer;
        this.mApexManager = ApexManager.getInstance();
    }

    public AppDataRollbackHelper(Installer installer, ApexManager apexManager) {
        this.mInstaller = installer;
        this.mApexManager = apexManager;
    }

    public final boolean doRestoreOrWipe(
            PackageRollbackInfo packageRollbackInfo, int i, int i2, int i3, String str, int i4) {
        String str2;
        if (!packageRollbackInfo.isApex()) {
            try {
                int rollbackDataPolicy = packageRollbackInfo.getRollbackDataPolicy();
                if (rollbackDataPolicy == 0) {
                    Installer installer = this.mInstaller;
                    String packageName = packageRollbackInfo.getPackageName();
                    if (installer.checkBeforeRemote()) {
                        try {
                            installer.mInstalld.restoreAppDataSnapshot(
                                    null, packageName, i3, str, i, i2, i4);
                        } catch (Exception e) {
                            Installer.InstallerException.from(e);
                            throw null;
                        }
                    }
                } else if (rollbackDataPolicy == 1) {
                    this.mInstaller.clearAppData(
                            null, packageRollbackInfo.getPackageName(), i, i4, 0L);
                }
            } catch (Installer.InstallerException e2) {
                Slog.e(
                        "RollbackManager",
                        "Unable to restore/wipe app data: "
                                + packageRollbackInfo.getPackageName()
                                + " policy="
                                + packageRollbackInfo.getRollbackDataPolicy(),
                        e2);
                return false;
            }
        } else if (packageRollbackInfo.getRollbackDataPolicy() == 0 && (i4 & 2) != 0) {
            ApexManager apexManager = this.mApexManager;
            String packageName2 = packageRollbackInfo.getPackageName();
            ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) apexManager;
            synchronized (apexManagerImpl.mLock) {
                Preconditions.checkState(
                        apexManagerImpl.mPackageNameToApexModuleName != null,
                        "APEX packages have not been scanned");
                str2 = (String) apexManagerImpl.mPackageNameToApexModuleName.get(packageName2);
            }
            if (str2 == null) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "Invalid apex package name: ", packageName2, "ApexManager");
            } else {
                try {
                    apexManagerImpl.waitForApexService().restoreCeData(i, i2, str2);
                } catch (Exception e3) {
                    Slog.e("ApexManager", e3.getMessage(), e3);
                }
            }
        }
        return true;
    }

    public final boolean doSnapshot(
            PackageRollbackInfo packageRollbackInfo, int i, int i2, int i3) {
        String str;
        if (packageRollbackInfo.isApex()) {
            if ((i3 & 2) == 0) {
                return true;
            }
            ApexManager apexManager = this.mApexManager;
            String packageName = packageRollbackInfo.getPackageName();
            ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) apexManager;
            synchronized (apexManagerImpl.mLock) {
                Preconditions.checkState(
                        apexManagerImpl.mPackageNameToApexModuleName != null,
                        "APEX packages have not been scanned");
                str = (String) apexManagerImpl.mPackageNameToApexModuleName.get(packageName);
            }
            if (str == null) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "Invalid apex package name: ", packageName, "ApexManager");
                return false;
            }
            try {
                apexManagerImpl.waitForApexService().snapshotCeData(i, i2, str);
                return true;
            } catch (Exception e) {
                Slog.e("ApexManager", e.getMessage(), e);
                return false;
            }
        }
        try {
            Installer installer = this.mInstaller;
            String packageName2 = packageRollbackInfo.getPackageName();
            if (!installer.checkBeforeRemote()) {
                return false;
            }
            try {
                installer.mInstalld.snapshotAppData(null, packageName2, i, i2, i3);
                return true;
            } catch (Exception e2) {
                Installer.InstallerException.from(e2);
                throw null;
            }
        } catch (Installer.InstallerException e3) {
            Slog.e(
                    "RollbackManager",
                    "Unable to create app data snapshot for: "
                            + packageRollbackInfo.getPackageName()
                            + ", userId: "
                            + i,
                    e3);
            return false;
        }
    }

    public boolean isUserCredentialLocked(int i) {
        return StorageManager.isFileEncrypted() && !StorageManager.isCeStorageUnlocked(i);
    }
}
