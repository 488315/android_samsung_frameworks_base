package com.android.server.backup.utils;

import android.app.compat.CompatChanges;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArraySet;
import android.util.Slog;

import com.android.internal.infra.AndroidFuture;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.backup.transport.BackupTransportClient;
import com.android.server.backup.transport.TransportConnection;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.pkg.PackageStateInternal;

import com.google.android.collect.Sets;

import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BackupEligibilityRules {
    public static final Set systemPackagesAllowedForNonSystemUsers;
    public static final Set systemPackagesAllowedForProfileUser;
    public final int mBackupDestination;
    public final boolean mIsProfileUser;
    public final PackageManager mPackageManager;
    public final PackageManagerInternal mPackageManagerInternal;
    public final boolean mSkipRestoreForLaunchedApps;
    public final int mUserId;

    static {
        ArraySet newArraySet = Sets.newArraySet(new String[] {"@pm@", "android"});
        systemPackagesAllowedForProfileUser = newArraySet;
        ArraySet newArraySet2 =
                Sets.newArraySet(
                        new String[] {
                            "com.android.wallpaperbackup", "com.android.providers.settings"
                        });
        HashSet hashSet = new HashSet(newArraySet);
        hashSet.addAll(newArraySet2);
        systemPackagesAllowedForNonSystemUsers = hashSet;
    }

    public BackupEligibilityRules(
            PackageManager packageManager,
            PackageManagerInternal packageManagerInternal,
            int i,
            Context context,
            int i2,
            boolean z) {
        this.mIsProfileUser = false;
        this.mPackageManager = packageManager;
        this.mPackageManagerInternal = packageManagerInternal;
        this.mUserId = i;
        this.mBackupDestination = i2;
        this.mIsProfileUser =
                ((UserManager) context.getSystemService(UserManager.class)).isProfile();
        this.mSkipRestoreForLaunchedApps = z;
    }

    public static boolean appIsStopped(ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 2097152) != 0;
    }

    public boolean appGetsFullBackup(PackageInfo packageInfo) {
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        return applicationInfo.backupAgentName == null || (applicationInfo.flags & 67108864) != 0;
    }

    public boolean appIsDisabled(ApplicationInfo applicationInfo) {
        PackageStateInternal packageStateInternal =
                ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal)
                        .getPackageStateInternal(applicationInfo.packageName);
        int enabledState =
                packageStateInternal == null
                        ? 0
                        : packageStateInternal
                                .getUserStateOrDefault(this.mUserId)
                                .getEnabledState();
        return enabledState != 0
                ? enabledState == 2 || enabledState == 3 || enabledState == 4
                : !applicationInfo.enabled;
    }

    public boolean appIsEligibleForBackup(ApplicationInfo applicationInfo) {
        if (!isAppBackupAllowed(applicationInfo)) {
            return false;
        }
        if (UserHandle.isCore(applicationInfo.uid)) {
            if (this.mUserId != 0) {
                boolean z = this.mIsProfileUser;
                if (z) {
                    if (!((ArraySet) systemPackagesAllowedForProfileUser)
                            .contains(applicationInfo.packageName)) {
                        return false;
                    }
                }
                if (!z) {
                    if (!((HashSet) systemPackagesAllowedForNonSystemUsers)
                            .contains(applicationInfo.packageName)) {
                        return false;
                    }
                }
            }
            if (applicationInfo.backupAgentName == null) {
                return false;
            }
        }
        if (applicationInfo.packageName.equals("com.android.sharedstoragebackup")
                || applicationInfo.isInstantApp()) {
            return false;
        }
        return !appIsDisabled(applicationInfo);
    }

    public final boolean appIsRunningAndEligibleForBackupWithTransport(
            TransportConnection transportConnection, String str) {
        try {
            PackageInfo packageInfoAsUser =
                    this.mPackageManager.getPackageInfoAsUser(str, 134217728, this.mUserId);
            ApplicationInfo applicationInfo = packageInfoAsUser.applicationInfo;
            if (appIsEligibleForBackup(applicationInfo)
                    && !appIsStopped(applicationInfo)
                    && !appIsDisabled(applicationInfo)) {
                if (transportConnection != null) {
                    try {
                        BackupTransportClient connectOrThrow =
                                transportConnection.connectOrThrow(
                                        "AppBackupUtils.appIsRunningAndEligibleForBackupWithTransport");
                        boolean appGetsFullBackup = appGetsFullBackup(packageInfoAsUser);
                        AndroidFuture newFuture = connectOrThrow.mTransportFutures.newFuture();
                        connectOrThrow.mTransportBinder.isAppEligibleForBackup(
                                packageInfoAsUser, appGetsFullBackup, newFuture);
                        Boolean bool = (Boolean) connectOrThrow.getFutureResult(newFuture);
                        if (bool != null) {
                            return bool.booleanValue();
                        }
                        return false;
                    } catch (Exception e) {
                        Slog.e(
                                "BackupManagerService",
                                "Unable to ask about eligibility: " + e.getMessage());
                    }
                }
                return true;
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return false;
    }

    public final boolean isAppBackupAllowed(ApplicationInfo applicationInfo) {
        int i = applicationInfo.flags;
        boolean z = (32768 & i) != 0;
        int i2 = this.mBackupDestination;
        if (i2 == 0) {
            return z;
        }
        int i3 = this.mUserId;
        if (i2 == 1) {
            return ((i & 1) == 0
                            && CompatChanges.isChangeEnabled(
                                    183147249L, applicationInfo.packageName, UserHandle.of(i3)))
                    || z;
        }
        if (i2 != 2) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i2, "Unknown operation type:", "BackupManagerService");
            return false;
        }
        String str = applicationInfo.packageName;
        if (str == null) {
            Slog.w("BackupManagerService", "Invalid ApplicationInfo object");
            return false;
        }
        if (!CompatChanges.isChangeEnabled(171032338L, str, UserHandle.of(i3))) {
            return z;
        }
        if ("android".equals(str)) {
            return true;
        }
        int i4 = applicationInfo.flags;
        boolean z2 = (i4 & 8) != 0;
        boolean z3 = (2 & i4) != 0;
        if (!UserHandle.isCore(applicationInfo.uid) && !z2) {
            return z3;
        }
        try {
            return this.mPackageManager
                    .getPropertyAsUser("android.backup.ALLOW_ADB_BACKUP", str, null, i3)
                    .getBoolean();
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.w(
                    "BackupManagerService",
                    "Failed to read allowAdbBackup property for + ".concat(str));
            return z;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0055, code lost:

       r0 = r0 + 1;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean signaturesMatch(
            android.content.pm.Signature[] r8, android.content.pm.PackageInfo r9) {
        /*
            r7 = this;
            java.lang.String r0 = r9.packageName
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            android.content.pm.ApplicationInfo r0 = r9.applicationInfo
            int r0 = r0.flags
            r2 = 1
            r0 = r0 & r2
            if (r0 == 0) goto Lf
            return r2
        Lf:
            boolean r0 = com.android.internal.util.ArrayUtils.isEmpty(r8)
            if (r0 == 0) goto L16
            return r1
        L16:
            android.content.pm.SigningInfo r0 = r9.signingInfo
            if (r0 != 0) goto L23
            java.lang.String r7 = "BackupManagerService"
            java.lang.String r8 = "signingInfo is empty, app was either unsigned or the flag PackageManager#GET_SIGNING_CERTIFICATES was not specified"
            android.util.Slog.w(r7, r8)
            return r1
        L23:
            int r3 = r8.length
            if (r3 != r2) goto L40
            r8 = r8[r1]
            java.lang.String r9 = r9.packageName
            android.content.pm.PackageManagerInternal r7 = r7.mPackageManagerInternal
            com.android.server.pm.PackageManagerService$PackageManagerInternalImpl r7 = (com.android.server.pm.PackageManagerService.PackageManagerInternalImpl) r7
            com.android.server.pm.PackageManagerService r7 = r7.mService
            com.android.server.pm.Computer r7 = r7.snapshotComputer()
            android.content.pm.SigningDetails r7 = r7.getSigningDetails(r9)
            if (r7 != 0) goto L3b
            goto L3f
        L3b:
            boolean r1 = r7.hasCertificate(r8, r2)
        L3f:
            return r1
        L40:
            android.content.pm.Signature[] r7 = r0.getApkContentsSigners()
            int r9 = r7.length
            r0 = r1
        L46:
            if (r0 >= r3) goto L5c
            r4 = r1
        L49:
            if (r4 >= r9) goto L5b
            r5 = r8[r0]
            r6 = r7[r4]
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L58
            int r0 = r0 + 1
            goto L46
        L58:
            int r4 = r4 + 1
            goto L49
        L5b:
            return r1
        L5c:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.utils.BackupEligibilityRules.signaturesMatch(android.content.pm.Signature[],"
                    + " android.content.pm.PackageInfo):boolean");
    }
}
