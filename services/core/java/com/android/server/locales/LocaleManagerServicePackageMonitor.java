package com.android.server.locales;

import android.app.LocaleConfig;
import android.app.backup.BackupManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.content.PackageMonitor;
import java.io.File;
import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class LocaleManagerServicePackageMonitor extends PackageMonitor {
    public LocaleManagerBackupHelper mBackupHelper;
    public LocaleManagerService mLocaleManagerService;
    public SystemAppUpdateTracker mSystemAppUpdateTracker;

    public final void onPackageAddedWithExtras(String str, int i, Bundle bundle) {
        LocaleManagerBackupHelper localeManagerBackupHelper = this.mBackupHelper;
        localeManagerBackupHelper.getClass();
        int userId = UserHandle.getUserId(i);
        if (bundle != null && bundle.getBoolean("android.intent.extra.ARCHIVAL", false)) {
            String num = Integer.toString(userId);
            SharedPreferences archivedPackagesSp = localeManagerBackupHelper.getArchivedPackagesSp(localeManagerBackupHelper.getArchivedPackagesFile());
            ArraySet arraySet = new ArraySet(archivedPackagesSp.getStringSet(num, new ArraySet()));
            if (arraySet.add(str) && !archivedPackagesSp.edit().putStringSet(num, arraySet).commit()) {
                Slog.e("LocaleManagerBkpHelper", "failed to add the package");
            }
        }
        localeManagerBackupHelper.checkStageDataAndApplyRestore(userId, str);
    }

    public final void onPackageDataCleared(String str, int i) {
        LocaleManagerBackupHelper localeManagerBackupHelper = this.mBackupHelper;
        localeManagerBackupHelper.getClass();
        try {
            BackupManager.dataChanged("android");
            localeManagerBackupHelper.removePackageFromPersistedInfo(UserHandle.getUserId(i), str);
        } catch (Exception e) {
            Slog.e("LocaleManagerBkpHelper", "Exception in onPackageDataCleared.", e);
        }
    }

    public final void onPackageRemoved(String str, int i) {
        LocaleManagerBackupHelper localeManagerBackupHelper = this.mBackupHelper;
        localeManagerBackupHelper.getClass();
        try {
            BackupManager.dataChanged("android");
            localeManagerBackupHelper.removePackageFromPersistedInfo(UserHandle.getUserId(i), str);
        } catch (Exception e) {
            Slog.e("LocaleManagerBkpHelper", "Exception in onPackageRemoved.", e);
        }
        LocaleManagerService localeManagerService = this.mLocaleManagerService;
        int userId = UserHandle.getUserId(i);
        localeManagerService.getClass();
        File xmlFileNameForUser = LocaleManagerService.getXmlFileNameForUser(userId, str);
        if (xmlFileNameForUser.exists()) {
            Slog.d("LocaleManagerService", "Delete the override LocaleConfig.");
            xmlFileNameForUser.delete();
        }
    }

    public final void onPackageUpdateFinished(String str, int i) {
        ApplicationInfo applicationInfo;
        String installingPackageName;
        LocaleManagerBackupHelper localeManagerBackupHelper = this.mBackupHelper;
        localeManagerBackupHelper.getClass();
        int userId = UserHandle.getUserId(i);
        String num = Integer.toString(userId);
        File archivedPackagesFile = localeManagerBackupHelper.getArchivedPackagesFile();
        if (archivedPackagesFile.exists()) {
            SharedPreferences archivedPackagesSp = localeManagerBackupHelper.getArchivedPackagesSp(archivedPackagesFile);
            ArraySet arraySet = new ArraySet(archivedPackagesSp.getStringSet(num, new ArraySet()));
            if (arraySet.remove(str)) {
                SharedPreferences.Editor edit = archivedPackagesSp.edit();
                if (arraySet.isEmpty()) {
                    if (!edit.remove(num).commit()) {
                        Slog.e("LocaleManagerBkpHelper", "Failed to remove the user");
                    }
                    if (archivedPackagesSp.getAll().isEmpty()) {
                        archivedPackagesFile.delete();
                    }
                } else if (!edit.putStringSet(num, arraySet).commit()) {
                    Slog.e("LocaleManagerBkpHelper", "failed to remove the package");
                }
                localeManagerBackupHelper.checkStageDataAndApplyRestore(userId, str);
            }
        }
        LocaleManagerService localeManagerService = localeManagerBackupHelper.mLocaleManagerService;
        if (localeManagerBackupHelper.mDelegateAppLocalePackages == null) {
            Slog.w("LocaleManagerBkpHelper", "Failed to persist data into the shared preference!");
        } else {
            ArraySet arraySet2 = new ArraySet(localeManagerBackupHelper.mDelegateAppLocalePackages.getStringSet(Integer.toString(userId), new ArraySet()));
            try {
                if (!localeManagerService.getApplicationLocales(str, userId).isEmpty()) {
                    if (arraySet2.contains(str)) {
                        try {
                            localeManagerService.removeUnsupportedAppLocales(str, userId, new LocaleConfig(localeManagerBackupHelper.mContext.createPackageContextAsUser(str, 0, UserHandle.of(userId))), 4);
                        } catch (PackageManager.NameNotFoundException e) {
                            Slog.e("LocaleManagerBkpHelper", "Can not found the package name : " + str + " / " + e);
                        }
                    }
                }
            } catch (RemoteException | IllegalArgumentException e2) {
                Slog.e("LocaleManagerBkpHelper", "Exception when getting locales for " + str, e2);
            }
        }
        SystemAppUpdateTracker systemAppUpdateTracker = this.mSystemAppUpdateTracker;
        LocaleManagerService localeManagerService2 = systemAppUpdateTracker.mLocaleManagerService;
        try {
            if (((HashSet) systemAppUpdateTracker.mUpdatedApps).contains(str)) {
                return;
            }
            try {
                applicationInfo = systemAppUpdateTracker.mContext.getPackageManager().getApplicationInfo(str, PackageManager.ApplicationInfoFlags.of(1048576L));
            } catch (PackageManager.NameNotFoundException unused) {
                applicationInfo = null;
            }
            if (applicationInfo == null || (applicationInfo.flags & 128) == 0) {
                return;
            }
            int userId2 = UserHandle.getUserId(i);
            if (localeManagerService2.getInstallingPackageName(userId2, str) == null) {
                return;
            }
            try {
                LocaleList applicationLocales = localeManagerService2.getApplicationLocales(str, userId2);
                if (!applicationLocales.isEmpty() && (installingPackageName = localeManagerService2.getInstallingPackageName(userId2, str)) != null) {
                    Intent createBaseIntent = LocaleManagerService.createBaseIntent("android.intent.action.APPLICATION_LOCALE_CHANGED", str, applicationLocales);
                    createBaseIntent.setPackage(installingPackageName);
                    localeManagerService2.mContext.sendBroadcastAsUser(createBaseIntent, UserHandle.of(userId2));
                }
            } catch (RemoteException unused2) {
            }
            systemAppUpdateTracker.updateBroadcastedAppsList(str);
        } catch (Exception e3) {
            Slog.e("SystemAppUpdateTracker", "Exception in onPackageUpdateFinished.", e3);
        }
    }
}
