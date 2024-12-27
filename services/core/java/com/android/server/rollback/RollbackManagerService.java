package com.android.server.rollback;

import android.content.Context;
import android.content.rollback.PackageRollbackInfo;
import android.provider.DeviceConfig;
import android.util.Slog;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.pm.ApexManager;
import com.android.server.pm.Installer;
import java.util.ArrayList;
import java.util.List;

public final class RollbackManagerService extends SystemService {
    public RollbackManagerServiceImpl mService;

    public RollbackManagerService(Context context) {
        super(context);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 1000) {
            final RollbackManagerServiceImpl rollbackManagerServiceImpl = this.mService;
            DeviceConfig.addOnPropertiesChangedListener("rollback_boot", rollbackManagerServiceImpl.mExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda13
                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                    RollbackManagerServiceImpl.this.updateRollbackLifetimeDurationInMillis();
                }
            });
            rollbackManagerServiceImpl.mHandler.post(new RollbackManagerServiceImpl$$ExternalSyntheticLambda7(rollbackManagerServiceImpl, 2));
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        ?? rollbackManagerServiceImpl = new RollbackManagerServiceImpl(getContext());
        this.mService = rollbackManagerServiceImpl;
        publishBinderService("rollback", rollbackManagerServiceImpl);
        LocalServices.addService(RollbackManagerInternal.class, this.mService);
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        final RollbackManagerServiceImpl rollbackManagerServiceImpl = this.mService;
        final int userIdentifier = targetUser.getUserIdentifier();
        rollbackManagerServiceImpl.assertNotInWorkerThread();
        if (RollbackManagerServiceImpl.LOCAL_LOGV) {
            ProxyManager$$ExternalSyntheticOutline0.m(userIdentifier, "onUnlockUser id=", "RollbackManager");
        }
        final int i = 0;
        rollbackManagerServiceImpl.awaitResult(new Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                boolean z;
                boolean z2;
                switch (i) {
                    case 0:
                        RollbackManagerServiceImpl rollbackManagerServiceImpl2 = rollbackManagerServiceImpl;
                        int i2 = userIdentifier;
                        rollbackManagerServiceImpl2.assertInWorkerThread();
                        ArrayList arrayList = new ArrayList(rollbackManagerServiceImpl2.mRollbacks);
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            Rollback rollback = (Rollback) arrayList.get(i3);
                            AppDataRollbackHelper appDataRollbackHelper = rollbackManagerServiceImpl2.mAppDataRollbackHelper;
                            rollback.assertInWorkerThread();
                            appDataRollbackHelper.getClass();
                            boolean z3 = false;
                            for (PackageRollbackInfo packageRollbackInfo : rollback.info.getPackages()) {
                                List pendingBackups = packageRollbackInfo.getPendingBackups();
                                boolean z4 = true;
                                if (pendingBackups == null || pendingBackups.indexOf(Integer.valueOf(i2)) == -1) {
                                    z = false;
                                } else {
                                    z3 = true;
                                    z = true;
                                }
                                PackageRollbackInfo.RestoreInfo restoreInfo = packageRollbackInfo.getRestoreInfo(i2);
                                if (restoreInfo != null) {
                                    z2 = true;
                                } else {
                                    z2 = z3;
                                    z4 = false;
                                }
                                if (z && z4) {
                                    packageRollbackInfo.removePendingBackup(i2);
                                    packageRollbackInfo.removePendingRestoreInfo(i2);
                                } else {
                                    if (z) {
                                        int indexOf = pendingBackups.indexOf(Integer.valueOf(i2));
                                        if (appDataRollbackHelper.doSnapshot(packageRollbackInfo, i2, rollback.info.getRollbackId(), 2)) {
                                            pendingBackups.remove(indexOf);
                                        }
                                    }
                                    if (z4 && appDataRollbackHelper.doRestoreOrWipe(packageRollbackInfo, i2, rollback.info.getRollbackId(), restoreInfo.appId, restoreInfo.seInfo, 2)) {
                                        packageRollbackInfo.removeRestoreInfo(restoreInfo);
                                    }
                                }
                                z3 = z2;
                            }
                            if (z3) {
                                RollbackStore.saveRollback(rollback, rollback.mBackupDir);
                            }
                        }
                        break;
                    default:
                        RollbackManagerServiceImpl rollbackManagerServiceImpl3 = rollbackManagerServiceImpl;
                        int i4 = userIdentifier;
                        int size = ((ArrayList) rollbackManagerServiceImpl3.mRollbacks).size();
                        int[] iArr = new int[size];
                        for (int i5 = 0; i5 < size; i5++) {
                            iArr[i5] = ((Rollback) ((ArrayList) rollbackManagerServiceImpl3.mRollbacks).get(i5)).info.getRollbackId();
                        }
                        ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) ApexManager.getInstance();
                        apexManagerImpl.getClass();
                        try {
                            apexManagerImpl.waitForApexService().destroyCeSnapshotsNotSpecified(i4, iArr);
                        } catch (Exception e) {
                            Slog.e("ApexManager", e.getMessage(), e);
                        }
                        try {
                            Installer installer = rollbackManagerServiceImpl3.mInstaller;
                            if (installer.checkBeforeRemote()) {
                                try {
                                    installer.mInstalld.destroyCeSnapshotsNotSpecified(null, i4, iArr);
                                    break;
                                } catch (Exception e2) {
                                    Installer.InstallerException.from(e2);
                                    throw null;
                                }
                            }
                        } catch (Installer.InstallerException e3) {
                            Slog.e("RollbackManager", "Failed to delete snapshots for user: " + i4, e3);
                        }
                        break;
                }
            }
        });
        final int i2 = 1;
        rollbackManagerServiceImpl.mHandler.post(new Runnable() { // from class: com.android.server.rollback.RollbackManagerServiceImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                boolean z;
                boolean z2;
                switch (i2) {
                    case 0:
                        RollbackManagerServiceImpl rollbackManagerServiceImpl2 = rollbackManagerServiceImpl;
                        int i22 = userIdentifier;
                        rollbackManagerServiceImpl2.assertInWorkerThread();
                        ArrayList arrayList = new ArrayList(rollbackManagerServiceImpl2.mRollbacks);
                        for (int i3 = 0; i3 < arrayList.size(); i3++) {
                            Rollback rollback = (Rollback) arrayList.get(i3);
                            AppDataRollbackHelper appDataRollbackHelper = rollbackManagerServiceImpl2.mAppDataRollbackHelper;
                            rollback.assertInWorkerThread();
                            appDataRollbackHelper.getClass();
                            boolean z3 = false;
                            for (PackageRollbackInfo packageRollbackInfo : rollback.info.getPackages()) {
                                List pendingBackups = packageRollbackInfo.getPendingBackups();
                                boolean z4 = true;
                                if (pendingBackups == null || pendingBackups.indexOf(Integer.valueOf(i22)) == -1) {
                                    z = false;
                                } else {
                                    z3 = true;
                                    z = true;
                                }
                                PackageRollbackInfo.RestoreInfo restoreInfo = packageRollbackInfo.getRestoreInfo(i22);
                                if (restoreInfo != null) {
                                    z2 = true;
                                } else {
                                    z2 = z3;
                                    z4 = false;
                                }
                                if (z && z4) {
                                    packageRollbackInfo.removePendingBackup(i22);
                                    packageRollbackInfo.removePendingRestoreInfo(i22);
                                } else {
                                    if (z) {
                                        int indexOf = pendingBackups.indexOf(Integer.valueOf(i22));
                                        if (appDataRollbackHelper.doSnapshot(packageRollbackInfo, i22, rollback.info.getRollbackId(), 2)) {
                                            pendingBackups.remove(indexOf);
                                        }
                                    }
                                    if (z4 && appDataRollbackHelper.doRestoreOrWipe(packageRollbackInfo, i22, rollback.info.getRollbackId(), restoreInfo.appId, restoreInfo.seInfo, 2)) {
                                        packageRollbackInfo.removeRestoreInfo(restoreInfo);
                                    }
                                }
                                z3 = z2;
                            }
                            if (z3) {
                                RollbackStore.saveRollback(rollback, rollback.mBackupDir);
                            }
                        }
                        break;
                    default:
                        RollbackManagerServiceImpl rollbackManagerServiceImpl3 = rollbackManagerServiceImpl;
                        int i4 = userIdentifier;
                        int size = ((ArrayList) rollbackManagerServiceImpl3.mRollbacks).size();
                        int[] iArr = new int[size];
                        for (int i5 = 0; i5 < size; i5++) {
                            iArr[i5] = ((Rollback) ((ArrayList) rollbackManagerServiceImpl3.mRollbacks).get(i5)).info.getRollbackId();
                        }
                        ApexManager.ApexManagerImpl apexManagerImpl = (ApexManager.ApexManagerImpl) ApexManager.getInstance();
                        apexManagerImpl.getClass();
                        try {
                            apexManagerImpl.waitForApexService().destroyCeSnapshotsNotSpecified(i4, iArr);
                        } catch (Exception e) {
                            Slog.e("ApexManager", e.getMessage(), e);
                        }
                        try {
                            Installer installer = rollbackManagerServiceImpl3.mInstaller;
                            if (installer.checkBeforeRemote()) {
                                try {
                                    installer.mInstalld.destroyCeSnapshotsNotSpecified(null, i4, iArr);
                                    break;
                                } catch (Exception e2) {
                                    Installer.InstallerException.from(e2);
                                    throw null;
                                }
                            }
                        } catch (Installer.InstallerException e3) {
                            Slog.e("RollbackManager", "Failed to delete snapshots for user: " + i4, e3);
                        }
                        break;
                }
            }
        });
    }
}
