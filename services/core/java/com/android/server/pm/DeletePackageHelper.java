package com.android.server.pm;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserProperties;
import android.content.pm.VersionedPackage;
import android.os.Binder;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.incremental.IncrementalManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.om.SemSamsungThemeUtils;
import com.android.server.pm.pkg.ArchiveState;
import com.android.server.wm.ActivityTaskManagerInternal;

import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.install.SilentUninstallerList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class DeletePackageHelper {
    public final BroadcastHelper mBroadcastHelper;
    public final PackageManagerService mPm;
    public final RemovePackageHelper mRemovePackageHelper;
    public final UserManagerService.LocalService mUserManagerInternal;
    public final SilentUninstallerList mSilentUninstallerList = new SilentUninstallerList();
    public final SparseArray mRunningOverlayInstalls = new SparseArray();
    public int mNextOverlayInstallToken = 1;
    public IDevicePolicyManager mDpms = null;

    public final class TempUserState {
        public final int enabledState;
        public final boolean installed;
        public final String lastDisableAppCaller;

        public TempUserState(int i, String str, boolean z) {
            this.enabledState = i;
            this.lastDisableAppCaller = str;
            this.installed = z;
        }
    }

    public DeletePackageHelper(
            PackageManagerService packageManagerService,
            RemovePackageHelper removePackageHelper,
            BroadcastHelper broadcastHelper) {
        this.mPm = packageManagerService;
        this.mUserManagerInternal =
                packageManagerService.mInjector.getUserManagerService().mLocalService;
        this.mRemovePackageHelper = removePackageHelper;
        this.mBroadcastHelper = broadcastHelper;
    }

    public static DeletePackageAction mayDeletePackageLocked(
            int i,
            UserHandle userHandle,
            PackageRemovedInfo packageRemovedInfo,
            PackageSetting packageSetting,
            PackageSetting packageSetting2) {
        if (packageSetting == null) {
            return null;
        }
        boolean z = PackageManagerServiceUtils.DEBUG;
        boolean z2 = true;
        if ((packageSetting.getFlags() & 1) != 0) {
            boolean z3 = (i & 4) != 0;
            if (userHandle != null && userHandle.getIdentifier() != -1) {
                z2 = false;
            }
            if ((!z3 || z2) && packageSetting2 == null) {
                Slog.w(
                        "PackageManager",
                        "Attempt to delete unknown system package "
                                + packageSetting.pkg.getPackageName());
                return null;
            }
        }
        return new DeletePackageAction(
                i, userHandle, packageRemovedInfo, packageSetting, packageSetting2);
    }

    public final void deleteInstalledPackageLIF(
            PackageSetting packageSetting,
            int i,
            boolean z,
            int i2,
            int[] iArr,
            PackageRemovedInfo packageRemovedInfo,
            boolean z2) {
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                packageRemovedInfo.mUid = packageSetting.mAppId;
                PackageManagerService packageManagerService = this.mPm;
                packageRemovedInfo.mBroadcastAllowList =
                        packageManagerService.mAppsFilter.getVisibilityAllowList(
                                packageManagerService.snapshotComputer(),
                                packageSetting,
                                iArr,
                                this.mPm.mSettings.mPackages);
            } catch (Throwable th) {
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        this.mRemovePackageHelper.removePackageDataLIF(
                packageSetting, i, iArr, packageRemovedInfo, i2, z2);
        if (z) {
            packageRemovedInfo.mArgs =
                    new CleanUpArgs(
                            InstructionSets.getAppDexInstructionSets(
                                    packageSetting.mPrimaryCpuAbi, packageSetting.mSecondaryCpuAbi),
                            packageSetting.mName,
                            packageSetting.mPathString);
        }
    }

    public final boolean deletePackageLIF(
            String str,
            UserHandle userHandle,
            boolean z,
            int[] iArr,
            int i,
            PackageRemovedInfo packageRemovedInfo,
            boolean z2) {
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                if ("true"
                        .equals(
                                SystemProperties.get(
                                        "persist.sys.knox.non_required_apps_task", "false"))) {
                    if (this.mDpms == null) {
                        this.mDpms =
                                IDevicePolicyManager.Stub.asInterface(
                                        ServiceManager.getService("device_policy"));
                    }
                    arrayList.addAll(
                            this.mDpms.getDisallowedSystemApps(
                                    new ComponentName("dummy", "dummy"),
                                    0,
                                    "android.app.action.PROVISION_MANAGED_DEVICE"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            boolean z3 = !arrayList.contains(str);
            PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    PackageSetting packageLPr = this.mPm.mSettings.getPackageLPr(str);
                    if (packageLPr == null) {
                        return false;
                    }
                    boolean z5 =
                            CoreRune.SYSUI_GRADLE_BUILD
                                    && ((PackageManagerInternal)
                                                    this.mPm.mInjector.mGetLocalServiceProducer
                                                            .produce(PackageManagerInternal.class))
                                            .getSystemUiServiceComponent()
                                            .getPackageName()
                                            .equals(str);
                    Settings settings = this.mPm.mSettings;
                    settings.getClass();
                    PackageSetting disabledSystemPkgLPr =
                            settings.getDisabledSystemPkgLPr(packageLPr.mName);
                    boolean z6 = PackageManagerServiceUtils.DEBUG;
                    if ((packageLPr.getFlags() & 1) != 0
                            && !z5
                            && z3
                            && this.mPm.checkPermission(
                                            "android.permission.CONTROL_KEYGUARD", str, 0)
                                    == 0) {
                        Slog.w(
                                "PackageManager",
                                "Attempt to delete keyguard system package " + str);
                        return false;
                    }
                    DeletePackageAction mayDeletePackageLocked =
                            mayDeletePackageLocked(
                                    i,
                                    userHandle,
                                    packageRemovedInfo,
                                    packageLPr,
                                    disabledSystemPkgLPr);
                    if (mayDeletePackageLocked == null) {
                        return false;
                    }
                    try {
                        executeDeletePackageLIF(mayDeletePackageLocked, str, z, iArr, z2);
                        return true;
                    } catch (SystemDeleteException unused) {
                        return false;
                    }
                } catch (Throwable th) {
                    boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th;
                }
            }
        } catch (Throwable th2) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th2;
        }
    }

    public final void deletePackageVersionedInternal(
            VersionedPackage versionedPackage,
            final IPackageDeleteObserver2 iPackageDeleteObserver2,
            final int i,
            final int i2,
            boolean z) {
        long j;
        int i3;
        boolean z2;
        DeletePackageHelper deletePackageHelper;
        String str;
        int i4;
        int i5;
        int[] iArr;
        int callingUid = Binder.getCallingUid();
        PackageManagerService packageManagerService = this.mPm;
        packageManagerService.mContext.enforceCallingOrSelfPermission(
                "android.permission.DELETE_PACKAGES", null);
        Computer snapshotComputer = packageManagerService.snapshotComputer();
        boolean canViewInstantApps = snapshotComputer.canViewInstantApps(callingUid, i);
        Preconditions.checkNotNull(versionedPackage);
        Preconditions.checkNotNull(iPackageDeleteObserver2);
        Preconditions.checkArgumentInRange(
                versionedPackage.getLongVersionCode(),
                -1L,
                Long.MAX_VALUE,
                "versionCode must be >= -1");
        final String packageName = versionedPackage.getPackageName();
        long longVersionCode = versionedPackage.getLongVersionCode();
        List list = SemSamsungThemeUtils.disableOverlayList;
        boolean equals = "com.samsung.android.themecenter".equals(packageName);
        Handler handler = packageManagerService.mHandler;
        if (equals && i == 0) {
            handler.post(
                    new DeletePackageHelper$$ExternalSyntheticLambda0(
                            packageName, callingUid, iPackageDeleteObserver2, 0));
            return;
        }
        if (packageManagerService.isRequiredSystemPackage(snapshotComputer, packageName, i)
                && (packageManagerService.mSettings.getDisabledSystemPkgLPr(packageName) == null
                        || (i2 & 4) != 0)) {
            handler.post(
                    new DeletePackageHelper$$ExternalSyntheticLambda0(
                            packageName, callingUid, iPackageDeleteObserver2, 1));
            return;
        }
        try {
            if (((ActivityTaskManagerInternal)
                            packageManagerService.mInjector.mGetLocalServiceProducer.produce(
                                    ActivityTaskManagerInternal.class))
                    .isBaseOfLockedTask(packageName)) {
                iPackageDeleteObserver2.onPackageDeleted(packageName, -7, (String) null);
                EventLog.writeEvent(1397638484, "127605586", -1, "");
                return;
            }
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
        }
        final String resolveInternalPackageName =
                snapshotComputer.resolveInternalPackageName(longVersionCode, packageName);
        int callingUid2 = Binder.getCallingUid();
        PackageSetting packageStateInternal =
                snapshotComputer.getPackageStateInternal(resolveInternalPackageName);
        if (packageStateInternal == null || !packageStateInternal.installSource.mIsOrphaned) {
            if (z) {
                j = longVersionCode;
                i3 = callingUid;
                z2 = canViewInstantApps;
            } else {
                if (PackageManagerServiceUtils.isRootOrShell(callingUid2)
                        || UserHandle.getAppId(callingUid2) == 1000) {
                    j = longVersionCode;
                    i3 = callingUid;
                } else {
                    int userId = UserHandle.getUserId(callingUid2);
                    i3 = callingUid;
                    if (callingUid2
                            == snapshotComputer.getPackageUid(
                                    snapshotComputer.getInstallerPackageName(
                                            i, resolveInternalPackageName),
                                    0L,
                                    userId)) {
                        j = longVersionCode;
                    } else {
                        String[] strArr = packageManagerService.mRequiredVerifierPackages;
                        int length = strArr.length;
                        int i6 = 0;
                        while (true) {
                            if (i6 < length) {
                                j = longVersionCode;
                                String[] strArr2 = strArr;
                                z2 = canViewInstantApps;
                                if (callingUid2
                                        == snapshotComputer.getPackageUid(strArr[i6], 0L, userId)) {
                                    break;
                                }
                                i6++;
                                strArr = strArr2;
                                longVersionCode = j;
                                canViewInstantApps = z2;
                            } else {
                                j = longVersionCode;
                                z2 = canViewInstantApps;
                                String str2 = packageManagerService.mRequiredUninstallerPackage;
                                if ((str2 == null
                                                || callingUid2
                                                        != snapshotComputer.getPackageUid(
                                                                str2, 0L, userId))
                                        && ((str = packageManagerService.mStorageManagerPackage)
                                                        == null
                                                || callingUid2
                                                        != snapshotComputer.getPackageUid(
                                                                str, 0L, userId))) {
                                    deletePackageHelper = this;
                                    long j2 = j;
                                    Iterator it =
                                            deletePackageHelper.mSilentUninstallerList
                                                    .allowedSilentUninstallers.iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            j = j2;
                                            if (callingUid2
                                                    == snapshotComputer.getPackageUid(
                                                            (String) it.next(), 0L, userId)) {
                                                break;
                                            } else {
                                                j2 = j;
                                            }
                                        } else {
                                            j = j2;
                                            if (snapshotComputer.checkUidPermission(
                                                            "android.permission.MANAGE_PROFILE_AND_DEVICE_OWNERS",
                                                            callingUid2)
                                                    != 0) {
                                                handler.post(
                                                        new DeletePackageHelper$$ExternalSyntheticLambda2(
                                                                packageName,
                                                                iPackageDeleteObserver2,
                                                                0));
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                z2 = canViewInstantApps;
            }
            deletePackageHelper = this;
        } else {
            j = longVersionCode;
            i3 = callingUid;
            z2 = canViewInstantApps;
            deletePackageHelper = this;
        }
        final boolean z3 = (i2 & 2) != 0;
        if (z3) {
            iArr = UserManagerService.this.getUserIds();
            i4 = 1;
            i5 = 0;
        } else {
            i4 = 1;
            i5 = 0;
            iArr = new int[] {i};
        }
        if (UserHandle.getUserId(callingUid2) != i || (z3 && iArr.length > i4)) {
            packageManagerService.mContext.enforceCallingOrSelfPermission(
                    "android.permission.INTERACT_ACROSS_USERS_FULL", "deletePackage for user " + i);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int length2 = iArr.length;
            while (i5 < length2) {
                int i7 = length2;
                int i8 = iArr[i5];
                if (packageManagerService.isPackageDeviceAdmin(i8, packageName)) {
                    handler.post(
                            new DeletePackageHelper$$ExternalSyntheticLambda2(
                                    packageName, iPackageDeleteObserver2, 1));
                    return;
                }
                ProtectedPackages protectedPackages = packageManagerService.mProtectedPackages;
                if (!protectedPackages.hasDeviceOwnerOrProfileOwner(i8, packageName)
                        && !protectedPackages.isProtectedPackage(i8, packageName)) {
                    i5++;
                    length2 = i7;
                }
                handler.post(
                        new DeletePackageHelper$$ExternalSyntheticLambda2(
                                packageName, iPackageDeleteObserver2, 2));
                return;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (packageManagerService.isUserRestricted(i, "no_uninstall_apps")) {
                handler.post(
                        new DeletePackageHelper$$ExternalSyntheticLambda2(
                                iPackageDeleteObserver2, packageName, 3));
                return;
            }
            if (!z3 && snapshotComputer.getBlockUninstallForUser(resolveInternalPackageName, i)) {
                handler.post(
                        new DeletePackageHelper$$ExternalSyntheticLambda2(
                                iPackageDeleteObserver2, packageName, 4));
                return;
            }
            StringBuilder sb = new StringBuilder("START DELETE PACKAGE: observer{");
            sb.append(
                    iPackageDeleteObserver2 != null
                            ? Integer.valueOf(iPackageDeleteObserver2.hashCode())
                            : "null");
            sb.append("}\npkg{");
            sb.append(packageName);
            sb.append("}, user{");
            ServiceKeeper$$ExternalSyntheticOutline0.m(
                    i, callingUid2, "}, caller{", "} flags{", sb);
            sb.append(i2);
            sb.append("}");
            PmLog.logDebugInfoAndLogcat(sb.toString());
            final int i9 = i3;
            final boolean z4 = z2;
            final long j3 = j;
            final int[] iArr2 = iArr;
            handler.post(
                    new Runnable() { // from class:
                                     // com.android.server.pm.DeletePackageHelper$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            PackageManagerService packageManagerService2;
                            String str3;
                            IPackageDeleteObserver2 iPackageDeleteObserver22;
                            boolean z5;
                            String str4;
                            int i10;
                            int i11;
                            int[] iArr3;
                            int i12;
                            String str5;
                            String str6;
                            UserProperties userProperties;
                            int i13;
                            int i14;
                            int[] iArr4;
                            UserManagerService.LocalService localService;
                            String str7;
                            String str8;
                            DeletePackageHelper deletePackageHelper2 = DeletePackageHelper.this;
                            String str9 = resolveInternalPackageName;
                            int i15 = i9;
                            boolean z6 = z4;
                            boolean z7 = z3;
                            long j4 = j3;
                            int i16 = i;
                            int i17 = i2;
                            int[] iArr5 = iArr2;
                            String str10 = packageName;
                            IPackageDeleteObserver2 iPackageDeleteObserver23 =
                                    iPackageDeleteObserver2;
                            PackageManagerService packageManagerService3 = deletePackageHelper2.mPm;
                            Computer snapshotComputer2 = packageManagerService3.snapshotComputer();
                            PackageSetting packageStateInternal2 =
                                    snapshotComputer2.getPackageStateInternal(str9);
                            if (packageStateInternal2 != null
                                    && packageStateInternal2
                                            .getUserStateOrDefault(UserHandle.getUserId(i15))
                                            .isInstantApp()
                                    && !z6) {
                                i10 = -1;
                                packageManagerService2 = packageManagerService3;
                                str3 = str10;
                                iPackageDeleteObserver22 = iPackageDeleteObserver23;
                                z5 = true;
                                str4 = "PackageManager";
                            } else if (z7) {
                                packageManagerService2 = packageManagerService3;
                                String str11 = "Package delete failed for user ";
                                str3 = str10;
                                iPackageDeleteObserver22 = iPackageDeleteObserver23;
                                z5 = true;
                                str4 = "PackageManager";
                                String str12 = ", returnCode ";
                                int[] iArr6 = PackageManagerService.EMPTY_INT_ARRAY;
                                for (int i18 : iArr5) {
                                    if (snapshotComputer2.getBlockUninstallForUser(str9, i18)) {
                                        iArr6 = ArrayUtils.appendInt(iArr6, i18);
                                    }
                                }
                                if (ArrayUtils.isEmpty(iArr6)) {
                                    i10 =
                                            deletePackageHelper2.deletePackageX(
                                                    i16, i17, j4, str9, false);
                                } else {
                                    int i19 = i17 & (-3);
                                    int length3 = iArr5.length;
                                    int i20 = 0;
                                    while (i20 < length3) {
                                        int i21 = iArr5[i20];
                                        if (ArrayUtils.contains(iArr6, i21)) {
                                            i11 = i20;
                                            iArr3 = iArr6;
                                            i12 = length3;
                                            str5 = str11;
                                            str6 = str12;
                                        } else {
                                            i11 = i20;
                                            iArr3 = iArr6;
                                            i12 = length3;
                                            String str13 = str11;
                                            String str14 = str12;
                                            int deletePackageX =
                                                    deletePackageHelper2.deletePackageX(
                                                            i21, i19, j4, str9, false);
                                            if (deletePackageX != 1) {
                                                str5 = str13;
                                                str6 = str14;
                                                PendingIntentController$$ExternalSyntheticOutline0
                                                        .m(i21, deletePackageX, str5, str6, str4);
                                            } else {
                                                str5 = str13;
                                                str6 = str14;
                                            }
                                        }
                                        str12 = str6;
                                        str11 = str5;
                                        iArr6 = iArr3;
                                        length3 = i12;
                                        i20 = i11 + 1;
                                    }
                                    i10 = -4;
                                }
                            } else {
                                String str15 = ", returnCode ";
                                String str16 = "Package delete failed for user ";
                                iPackageDeleteObserver22 = iPackageDeleteObserver23;
                                packageManagerService2 = packageManagerService3;
                                str3 = str10;
                                str4 = "PackageManager";
                                i10 =
                                        deletePackageHelper2.deletePackageX(
                                                i16, i17, j4, str9, false);
                                if (i10 != 1 || packageStateInternal2 == null) {
                                    z5 = true;
                                } else {
                                    UserManagerService.LocalService localService2 =
                                            deletePackageHelper2.mUserManagerInternal;
                                    int[] profileIds = localService2.getProfileIds(i16, true);
                                    int length4 = profileIds.length;
                                    int i22 = 0;
                                    int i23 = i10;
                                    while (i22 < length4) {
                                        int i24 = profileIds[i22];
                                        if (i24 != i16
                                                && UserManagerService.this
                                                                .getProfileParentIdUnchecked(i24)
                                                        == i16
                                                && packageStateInternal2
                                                        .getUserStateOrDefault(i24)
                                                        .isInstalled()
                                                && (userProperties =
                                                                localService2.getUserProperties(
                                                                        i24))
                                                        != null
                                                && userProperties.getDeleteAppWithParent()) {
                                            i13 = length4;
                                            i14 = i22;
                                            iArr4 = profileIds;
                                            localService = localService2;
                                            int deletePackageX2 =
                                                    deletePackageHelper2.deletePackageX(
                                                            i24, i17, j4, str9, false);
                                            str7 = str15;
                                            str8 = str16;
                                            if (deletePackageX2 != 1) {
                                                PendingIntentController$$ExternalSyntheticOutline0
                                                        .m(i24, deletePackageX2, str8, str7, str4);
                                                i23 = -8;
                                            }
                                        } else {
                                            i13 = length4;
                                            i14 = i22;
                                            iArr4 = profileIds;
                                            localService = localService2;
                                            str7 = str15;
                                            str8 = str16;
                                        }
                                        i22 = i14 + 1;
                                        str16 = str8;
                                        str15 = str7;
                                        length4 = i13;
                                        profileIds = iArr4;
                                        localService2 = localService;
                                    }
                                    z5 = true;
                                    i10 = i23;
                                }
                            }
                            String str17 = str3;
                            if (i10 < 0) {
                                PmHook.uninstallLog(i16, str17);
                            }
                            try {
                                PmLog.logDebugInfoAndLogcat(
                                        "result of delete: "
                                                + i10
                                                + "{"
                                                + iPackageDeleteObserver22.hashCode()
                                                + "}");
                                iPackageDeleteObserver22.onPackageDeleted(
                                        str17, i10, (String) null);
                            } catch (RemoteException unused) {
                                Log.i(str4, "Observer no longer exists.");
                            }
                            packageManagerService2.schedulePruneUnusedStaticSharedLibraries(z5);
                        }
                    });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:234:0x0572, code lost:

       if (r39 == com.android.server.pm.PackageManagerService.sPersonaManager.getAppSeparationId()) goto L259;
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00b8, code lost:

       if (r1.getUserInfo(com.android.server.pm.UserManagerService.this.getProfileParentIdUnchecked(r39)).isAdmin() == false) goto L40;
    */
    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:154:0x03f5 A[Catch: all -> 0x0401, TryCatch #0 {all -> 0x0401, blocks: (B:152:0x03f1, B:154:0x03f5, B:156:0x0407, B:157:0x040b, B:188:0x0485, B:192:0x049a, B:194:0x04a0, B:195:0x04a4, B:201:0x04b6, B:203:0x04ba, B:204:0x04d4, B:205:0x04da, B:207:0x04de, B:211:0x04fb, B:212:0x04fd, B:253:0x0494, B:260:0x0489, B:261:0x048b, B:159:0x040c, B:161:0x0416, B:163:0x041a, B:167:0x042a, B:169:0x042d, B:171:0x0446, B:178:0x0457, B:186:0x047c, B:187:0x0484, B:256:0x045f, B:197:0x04a5, B:198:0x04b1), top: B:151:0x03f1, inners: #2, #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0407 A[Catch: all -> 0x0401, TryCatch #0 {all -> 0x0401, blocks: (B:152:0x03f1, B:154:0x03f5, B:156:0x0407, B:157:0x040b, B:188:0x0485, B:192:0x049a, B:194:0x04a0, B:195:0x04a4, B:201:0x04b6, B:203:0x04ba, B:204:0x04d4, B:205:0x04da, B:207:0x04de, B:211:0x04fb, B:212:0x04fd, B:253:0x0494, B:260:0x0489, B:261:0x048b, B:159:0x040c, B:161:0x0416, B:163:0x041a, B:167:0x042a, B:169:0x042d, B:171:0x0446, B:178:0x0457, B:186:0x047c, B:187:0x0484, B:256:0x045f, B:197:0x04a5, B:198:0x04b1), top: B:151:0x03f1, inners: #2, #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0491  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x04a5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:228:0x054f  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x05be  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0494 A[Catch: all -> 0x0401, TryCatch #0 {all -> 0x0401, blocks: (B:152:0x03f1, B:154:0x03f5, B:156:0x0407, B:157:0x040b, B:188:0x0485, B:192:0x049a, B:194:0x04a0, B:195:0x04a4, B:201:0x04b6, B:203:0x04ba, B:204:0x04d4, B:205:0x04da, B:207:0x04de, B:211:0x04fb, B:212:0x04fd, B:253:0x0494, B:260:0x0489, B:261:0x048b, B:159:0x040c, B:161:0x0416, B:163:0x041a, B:167:0x042a, B:169:0x042d, B:171:0x0446, B:178:0x0457, B:186:0x047c, B:187:0x0484, B:256:0x045f, B:197:0x04a5, B:198:0x04b1), top: B:151:0x03f1, inners: #2, #10 }] */
    /* JADX WARN: Removed duplicated region for block: B:262:0x048c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int deletePackageX(int r39, int r40, long r41, java.lang.String r43, boolean r44) {
        /*
            Method dump skipped, instructions count: 1516
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.DeletePackageHelper.deletePackageX(int, int, long,"
                    + " java.lang.String, boolean):int");
    }

    public final void executeDeletePackageLIF(
            DeletePackageAction deletePackageAction,
            String str,
            boolean z,
            int[] iArr,
            boolean z2) {
        int identifier;
        int i;
        int[] iArr2;
        boolean z3;
        SparseBooleanArray sparseBooleanArray;
        boolean z4;
        int identifier2;
        boolean contains;
        PackageRemovedInfo packageRemovedInfo;
        SparseBooleanArray sparseBooleanArray2;
        int i2;
        PackageSetting packageSetting = deletePackageAction.mDeletingPs;
        PackageRemovedInfo packageRemovedInfo2 = deletePackageAction.mRemovedInfo;
        UserHandle userHandle = deletePackageAction.mUser;
        int i3 = deletePackageAction.mFlags;
        boolean z5 = PackageManagerServiceUtils.DEBUG;
        boolean z6 = (packageSetting.getFlags() & 1) != 0;
        SparseBooleanArray sparseBooleanArray3 = new SparseBooleanArray();
        for (int i4 : iArr) {
            sparseBooleanArray3.put(
                    i4, this.mPm.checkPermission("android.permission.SUSPEND_APPS", str, i4) == 0);
        }
        if (userHandle == null) {
            i = -1;
            identifier = -1;
        } else {
            identifier = userHandle.getIdentifier();
            i = -1;
        }
        if (identifier == i) {
            iArr2 = packageSetting.queryUsersInstalledOrHasData(iArr);
            z3 = false;
        } else {
            z3 = false;
            iArr2 = new int[] {identifier};
        }
        packageRemovedInfo2.mRemovedUsers = iArr2;
        int[] iArr3 = PackageRemovedInfo.EMPTY_INT_ARRAY;
        packageRemovedInfo2.mBroadcastUsers = iArr3;
        packageRemovedInfo2.mInstantUserIds = iArr3;
        for (int length = iArr2.length - 1; length >= 0; length--) {
            int i5 = packageRemovedInfo2.mRemovedUsers[length];
            if (packageSetting.getInstantApp(i5)) {
                packageRemovedInfo2.mInstantUserIds =
                        ArrayUtils.appendInt(packageRemovedInfo2.mInstantUserIds, i5);
            } else {
                packageRemovedInfo2.mBroadcastUsers =
                        ArrayUtils.appendInt(packageRemovedInfo2.mBroadcastUsers, i5);
            }
        }
        packageRemovedInfo2.mDataRemoved = (i3 & 1) == 0 ? true : z3;
        packageRemovedInfo2.mRemovedPackage = packageSetting.mName;
        packageRemovedInfo2.mInstallerPackageName =
                packageSetting.installSource.mInstallerPackageName;
        AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
        packageRemovedInfo2.mIsStaticSharedLib =
                (androidPackageInternal == null
                                || androidPackageInternal.getStaticSharedLibraryName() == null)
                        ? z3
                        : true;
        packageRemovedInfo2.mIsExternal = packageSetting.isExternalStorage();
        packageRemovedInfo2.mRemovedPackageVersionCode = packageSetting.versionCode;
        if ((z6 && (i3 & 4) == 0) || identifier == -1) {
            sparseBooleanArray = sparseBooleanArray3;
        } else {
            PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
            boolean z7 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    markPackageUninstalledForUserLPw(packageSetting, userHandle, i3);
                    if (z6) {
                        sparseBooleanArray = sparseBooleanArray3;
                    } else {
                        PackageManagerService packageManagerService = this.mPm;
                        synchronized (packageManagerService.mKeepUninstalledPackages) {
                            contains = packageManagerService.mKeepUninstalledPackages.contains(str);
                        }
                        int[] userIds = UserManagerService.this.getUserIds();
                        int length2 = userIds.length;
                        int i6 = 0;
                        while (true) {
                            if (i6 < length2) {
                                sparseBooleanArray = sparseBooleanArray3;
                                int i7 = userIds[i6];
                                if (i7 != identifier
                                        && packageSetting.readUserState(i7).isInstalled()) {
                                    break;
                                }
                                i6++;
                                sparseBooleanArray3 = sparseBooleanArray;
                            } else {
                                sparseBooleanArray = sparseBooleanArray3;
                                if (!contains) {
                                    this.mPm.mSettings.writeKernelMappingLPr(packageSetting);
                                    z4 = false;
                                }
                            }
                        }
                    }
                    z4 = true;
                } finally {
                    boolean z8 = PackageManagerService.DEBUG_COMPRESSION;
                }
            }
            if (z4) {
                this.mRemovePackageHelper.clearPackageStateForUserLIF(
                        packageSetting, identifier, i3);
                packageRemovedInfo2.mUid = packageSetting.mAppId;
                if (packageRemovedInfo2.mDataRemoved) {
                    packageRemovedInfo2.mIsAppIdRemoved = true;
                }
                PackageManagerService packageManagerService2 = this.mPm;
                if (userHandle == null) {
                    identifier2 = -1;
                } else {
                    packageManagerService2.getClass();
                    identifier2 = userHandle.getIdentifier();
                }
                packageManagerService2.scheduleWritePackageRestrictions(identifier2);
                return;
            }
        }
        if (z && !z6 && packageSetting.isExternalStorage()) {
            this.mPm.snapshotComputer();
            this.mPm.getClass();
            PackageManagerService.deleteOatArtifactsOfPackage(str);
        }
        if (z6) {
            int i8 = deletePackageAction.mFlags;
            PackageRemovedInfo packageRemovedInfo3 = deletePackageAction.mRemovedInfo;
            int[] iArr4 = packageRemovedInfo3.mOrigUsers;
            PackageSetting packageSetting2 = deletePackageAction.mDeletingPs;
            packageSetting2.getClass();
            Slog.d("PackageManager", "Deleting system pkg from data partition");
            packageRemovedInfo3.mIsRemovedPackageSystemUpdate = true;
            PackageSetting packageSetting3 = deletePackageAction.mDisabledPs;
            packageRemovedInfo = packageRemovedInfo2;
            int i9 =
                    (packageSetting3.versionCode < packageSetting2.versionCode
                                    || packageSetting3.mAppId != packageSetting2.mAppId)
                            ? i8 & (-2)
                            : i8 | 1;
            PackageManagerTracedLock packageManagerTracedLock2 = this.mPm.mInstallLock;
            packageManagerTracedLock2.mLock.lock();
            sparseBooleanArray2 = sparseBooleanArray;
            try {
                deleteInstalledPackageLIF(
                        packageSetting2, -1, true, i9, iArr, packageRemovedInfo3, z2);
                packageManagerTracedLock2.close();
                this.mPm.restoreDisabledSystemPackageLIF(deletePackageAction, iArr, z2);
            } finally {
            }
        } else {
            packageRemovedInfo = packageRemovedInfo2;
            sparseBooleanArray2 = sparseBooleanArray;
            if (IncrementalManager.isIncrementalPath(packageSetting.mPathString)) {
                PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                        PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
                try {
                    try {
                        DexOptHelper.getArtManagerLocal()
                                .deleteDexoptArtifacts(withFilteredSnapshot, str);
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        Slog.w("PackageManager", e.toString());
                    }
                    if (withFilteredSnapshot != null) {
                        withFilteredSnapshot.close();
                    }
                } finally {
                }
            }
            deleteInstalledPackageLIF(
                    packageSetting, identifier, z, i3, iArr, packageRemovedInfo, z2);
        }
        Computer snapshotComputer = this.mPm.snapshotComputer();
        PackageRemovedInfo packageRemovedInfo4 = packageRemovedInfo;
        int[] iArr5 = packageRemovedInfo4.mRemovedUsers;
        int length3 = iArr5.length;
        int i10 = 0;
        while (i10 < length3) {
            int i11 = iArr5[i10];
            if (sparseBooleanArray2.get(i11)) {
                i2 = 1;
                this.mPm.unsuspendForSuspendingPackage(i11, snapshotComputer, str, true);
                PackageManagerService packageManagerService3 = this.mPm;
                packageManagerService3.getClass();
                packageManagerService3.mDistractingPackageHelper
                        .removeDistractingPackageRestrictions(
                                snapshotComputer,
                                snapshotComputer.getAllAvailablePackageNames(),
                                i11);
            } else {
                i2 = 1;
            }
            i10 += i2;
        }
        PackageManagerTracedLock packageManagerTracedLock3 = this.mPm.mLock;
        boolean z9 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock3) {
            try {
                packageRemovedInfo4.mRemovedForAllUsers =
                        this.mPm.mPackages.mStorage.get(packageSetting.mName) == null;
            } finally {
            }
        }
    }

    public final void markPackageUninstalledForUserLPw(
            PackageSetting packageSetting, UserHandle userHandle, int i) {
        ArraySet arraySet;
        ArraySet arraySet2;
        PackageSetting packageSetting2 = packageSetting;
        boolean z = true;
        int[] userIds =
                (userHandle == null || userHandle.getIdentifier() == -1)
                        ? UserManagerService.this.getUserIds()
                        : new int[] {userHandle.getIdentifier()};
        int i2 = 0;
        for (int length = userIds.length; i2 < length; length = length) {
            int i3 = userIds[i2];
            int i4 = i & 1;
            if (i4 != 0) {
                arraySet =
                        new ArraySet(packageSetting2.readUserState(i3).m788getEnabledComponents());
                arraySet2 =
                        new ArraySet(packageSetting2.readUserState(i3).m787getDisabledComponents());
            } else {
                arraySet = null;
                arraySet2 = null;
            }
            ArchiveState archiveState =
                    i4 == 0 ? null : packageSetting2.getUserStateOrDefault(i3).getArchiveState();
            long firstInstallTimeMillis =
                    i4 == 0
                            ? 0L
                            : packageSetting2.getUserStateOrDefault(i3).getFirstInstallTimeMillis();
            boolean z2 =
                    (archiveState == null
                                    || !"auto_disabler"
                                            .equals(
                                                    packageSetting2
                                                            .readUserState(i3)
                                                            .getLastDisableAppCaller()))
                            ? false
                            : z;
            packageSetting.setUserState(
                    i3,
                    packageSetting2.readUserState(i3).getCeDataInode(),
                    packageSetting2.readUserState(i3).getDeDataInode(),
                    z2 ? packageSetting2.getEnabled(i3) : 0,
                    false,
                    true,
                    true,
                    false,
                    0,
                    null,
                    false,
                    false,
                    z2 ? "auto_disabler" : null,
                    arraySet,
                    arraySet2,
                    0,
                    0,
                    null,
                    null,
                    firstInstallTimeMillis,
                    0,
                    archiveState);
            i2++;
            packageSetting2 = packageSetting;
            z = z;
            userIds = userIds;
        }
        this.mPm.mSettings.writeKernelMappingLPr(packageSetting);
    }

    public final void removeUnusedPackagesLPw(UserManagerService userManagerService, int i) {
        boolean contains;
        int[] userIds = userManagerService.getUserIds();
        PackageManagerService packageManagerService = this.mPm;
        int size = packageManagerService.mSettings.mPackages.mStorage.size();
        for (int i2 = 0; i2 < size; i2++) {
            PackageSetting packageSetting =
                    (PackageSetting) packageManagerService.mSettings.mPackages.mStorage.valueAt(i2);
            AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
            if (androidPackageInternal != null) {
                String packageName = androidPackageInternal.getPackageName();
                boolean z = true;
                if ((packageSetting.mPkgFlags & 1) == 0
                        && TextUtils.isEmpty(packageSetting.pkg.getStaticSharedLibraryName())
                        && TextUtils.isEmpty(packageSetting.pkg.getSdkLibraryName())) {
                    synchronized (packageManagerService.mKeepUninstalledPackages) {
                        contains =
                                packageManagerService.mKeepUninstalledPackages.contains(
                                        packageName);
                    }
                    if (!contains) {
                        for (int i3 : userIds) {
                            if (i3 != i && packageSetting.getInstalled(i3)) {
                                break;
                            }
                        }
                    }
                    z = contains;
                    if (!z) {
                        packageManagerService.mHandler.post(
                                new DeletePackageHelper$$ExternalSyntheticLambda0(
                                        this, packageName, i));
                    }
                }
            }
        }
    }
}
