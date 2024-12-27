package com.android.server.pm;

import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.CreateAppDataArgs;
import android.os.CreateAppDataResult;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Trace;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.os.storage.StorageManagerInternal;
import android.security.AndroidKeyStoreMaintenance;
import android.text.TextUtils;
import android.util.Slog;
import android.util.TimingsTraceLog;

import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServerInitThreadPool;
import com.android.server.pm.dex.DexManager;
import com.android.server.pm.dex.DynamicCodeLogger;
import com.android.server.pm.dex.PackageDexUsage;
import com.android.server.pm.dex.PackageDynamicCodeLoading;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.pm.pkg.PackageUserStateImpl;
import com.android.server.pm.pkg.PackageUserStateInternal;

import dalvik.system.BlockGuard;
import dalvik.system.VMRuntime;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppDataHelper {
    public final PackageManagerServiceInjector mInjector;
    public final Installer mInstaller;
    public final PackageManagerService mPm;

    public AppDataHelper(PackageManagerService packageManagerService) {
        this.mPm = packageManagerService;
        PackageManagerServiceInjector packageManagerServiceInjector =
                packageManagerService.mInjector;
        this.mInjector = packageManagerServiceInjector;
        this.mInstaller = packageManagerServiceInjector.mInstaller;
    }

    public static void assertPackageStorageValid(
            Computer computer, String str, String str2, int i) {
        PackageSetting packageStateInternal = computer.getPackageStateInternal(str2);
        if (packageStateInternal == null) {
            throw new PackageManagerException(
                    XmlUtils$$ExternalSyntheticOutline0.m("Package ", str2, " is unknown"), -7);
        }
        if (!TextUtils.equals(str, packageStateInternal.volumeUuid)) {
            StringBuilder m =
                    InitialConfiguration$$ExternalSyntheticOutline0.m(
                            "Package ",
                            str2,
                            " found on unknown volume ",
                            str,
                            "; expected volume ");
            m.append(packageStateInternal.volumeUuid);
            throw new PackageManagerException(m.toString(), -8);
        }
        PackageUserStateInternal userStateOrDefault = packageStateInternal.getUserStateOrDefault(i);
        if (!userStateOrDefault.isInstalled() && !userStateOrDefault.dataExists()) {
            throw new PackageManagerException(
                    AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                            i,
                            "Package ",
                            str2,
                            " not installed for user ",
                            " or was deleted without DELETE_KEEP_DATA"),
                    -9);
        }
        AndroidPackageInternal androidPackageInternal = packageStateInternal.pkg;
        if (androidPackageInternal != null && !shouldHaveAppStorage(androidPackageInternal)) {
            throw new PackageManagerException(
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "Package ", str2, " shouldn't have storage"),
                    -10);
        }
    }

    public static void clearAppProfilesLIF(AndroidPackage androidPackage) {
        if (androidPackage == null) {
            Slog.wtf("PackageManager", "Package was null!", new Throwable());
        } else {
            destroyAppProfilesLIF(androidPackage.getPackageName());
        }
    }

    public static void destroyAppProfilesLIF(String str) {
        if (DexOptHelper.sArtManagerLocalIsInitialized) {
            PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                    PackageManagerServiceUtils.getPackageManagerLocal().withFilteredSnapshot();
            try {
                try {
                    DexOptHelper.getArtManagerLocal().clearAppProfiles(withFilteredSnapshot, str);
                } catch (IllegalArgumentException e) {
                    Slog.w("PackageManager", e);
                }
                if (withFilteredSnapshot != null) {
                    withFilteredSnapshot.close();
                }
            } catch (Throwable th) {
                if (withFilteredSnapshot != null) {
                    try {
                        withFilteredSnapshot.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    public static boolean shouldHaveAppStorage(AndroidPackage androidPackage) {
        PackageManager.Property property =
                (PackageManager.Property)
                        androidPackage
                                .getProperties()
                                .get("android.internal.PROPERTY_NO_APP_DATA_STORAGE");
        return (property == null || !property.getBoolean()) && androidPackage.getUid() >= 0;
    }

    public final void clearAppDataLIF(AndroidPackage androidPackage, int i, int i2) {
        if (androidPackage == null) {
            return;
        }
        String packageName = androidPackage.getPackageName();
        String volumeUuid = androidPackage.getVolumeUuid();
        PackageManagerService packageManagerService = this.mPm;
        PackageSetting packageStateInternal =
                packageManagerService.snapshotComputer().getPackageStateInternal(packageName);
        for (int i3 : packageManagerService.resolveUserIds(i)) {
            try {
                this.mInstaller.clearAppData(
                        volumeUuid,
                        packageName,
                        i3,
                        i2,
                        packageStateInternal != null
                                ? packageStateInternal.getUserStateOrDefault(i3).getCeDataInode()
                                : 0L);
            } catch (Installer.InstallerException e) {
                Slog.w("PackageManager", String.valueOf(e));
            }
        }
        if ((131072 & i2) == 0) {
            clearAppProfilesLIF(androidPackage);
        }
    }

    public final void clearKeystoreData(int i, int i2) {
        if (i2 < 0) {
            return;
        }
        if (UserHandle.getAppId(i2) == 1000) {
            Slog.w("PackageManager", "skip to clear keystore for SYSTEM_UID");
            return;
        }
        if (i2 == 5250 || i2 == 1250) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    i2, "skip to clear keystore for knox app with ", "PackageManager");
            return;
        }
        int length = this.mPm.resolveUserIds(i).length;
        for (int i3 = 0; i3 < length; i3++) {
            AndroidKeyStoreMaintenance.clearNamespace(0, UserHandle.getUid(r4[i3], i2));
        }
    }

    public final void destroyAppDataLeafLIF(int i, int i2, String str, String str2) {
        boolean z;
        boolean z2;
        PackageSetting packageStateInternal =
                this.mPm.snapshotComputer().getPackageStateInternal(str);
        for (int i3 : this.mPm.resolveUserIds(i)) {
            try {
                this.mInstaller.destroyAppData(
                        str2,
                        str,
                        i3,
                        i2,
                        packageStateInternal != null
                                ? packageStateInternal.getUserStateOrDefault(i3).getCeDataInode()
                                : 0L);
            } catch (Installer.InstallerException e) {
                Slog.w("PackageManager", String.valueOf(e));
            }
            DexManager dexManager = this.mPm.mDexManager;
            boolean z3 = true;
            if (i == -1) {
                PackageDexUsage packageDexUsage = dexManager.mPackageDexUsage;
                synchronized (packageDexUsage.mPackageUseInfoMap) {
                    z2 = ((HashMap) packageDexUsage.mPackageUseInfoMap).remove(str) != null;
                }
                if (z2) {
                    dexManager.mPackageDexUsage.maybeWriteAsync(null);
                }
            } else {
                PackageDexUsage packageDexUsage2 = dexManager.mPackageDexUsage;
                synchronized (packageDexUsage2.mPackageUseInfoMap) {
                    try {
                        PackageDexUsage.PackageUseInfo packageUseInfo =
                                (PackageDexUsage.PackageUseInfo)
                                        ((HashMap) packageDexUsage2.mPackageUseInfoMap).get(str);
                        if (packageUseInfo == null) {
                            z = false;
                        } else {
                            Iterator it =
                                    ((HashMap) packageUseInfo.mDexUseInfoMap).entrySet().iterator();
                            z = false;
                            while (it.hasNext()) {
                                if (((PackageDexUsage.DexUseInfo)
                                                        ((Map.Entry) it.next()).getValue())
                                                .mOwnerUserId
                                        == i) {
                                    it.remove();
                                    z = true;
                                }
                            }
                            if (((HashMap) packageUseInfo.mDexUseInfoMap).isEmpty()
                                    && !(!((HashMap) packageUseInfo.mPrimaryCodePaths).isEmpty())) {
                                ((HashMap) packageDexUsage2.mPackageUseInfoMap).remove(str);
                                z = true;
                            }
                        }
                    } finally {
                    }
                }
                if (z) {
                    dexManager.mPackageDexUsage.maybeWriteAsync(null);
                }
            }
            DynamicCodeLogger dynamicCodeLogger = this.mPm.mDynamicCodeLogger;
            if (i == -1) {
                PackageDynamicCodeLoading packageDynamicCodeLoading =
                        dynamicCodeLogger.mPackageDynamicCodeLoading;
                synchronized (packageDynamicCodeLoading.mLock) {
                    if (((HashMap) packageDynamicCodeLoading.mPackageMap).remove(str) == null) {
                        z3 = false;
                    }
                }
                if (z3) {
                    dynamicCodeLogger.mPackageDynamicCodeLoading.maybeWriteAsync(null);
                }
            } else {
                PackageDynamicCodeLoading packageDynamicCodeLoading2 =
                        dynamicCodeLogger.mPackageDynamicCodeLoading;
                if (packageDynamicCodeLoading2.removeUserPackage(i, str)) {
                    packageDynamicCodeLoading2.maybeWriteAsync(null);
                }
            }
        }
    }

    public final void executeBatchLI(Installer.Batch batch) {
        try {
            batch.execute(this.mInstaller);
        } catch (Installer.InstallerException e) {
            Slog.w("PackageManager", "Failed to execute pending operations", e);
        }
    }

    public final Future fixAppsDataOnBoot() {
        final int i = StorageManager.isFileEncrypted() ? 1 : 3;
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mInstallLock;
        packageManagerTracedLock.mLock.lock();
        try {
            final List reconcileAppsDataLI =
                    reconcileAppsDataLI(StorageManager.UUID_PRIVATE_INTERNAL, 0, i, true, true);
            packageManagerTracedLock.close();
            return SystemServerInitThreadPool.submit(
                    "prepareAppData",
                    new Runnable() { // from class:
                                     // com.android.server.pm.AppDataHelper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AppDataHelper appDataHelper = AppDataHelper.this;
                            List list = reconcileAppsDataLI;
                            int i2 = i;
                            appDataHelper.getClass();
                            TimingsTraceLog timingsTraceLog =
                                    new TimingsTraceLog("SystemServerTimingAsync", 262144L);
                            timingsTraceLog.traceBegin("AppDataFixup");
                            try {
                                Installer installer = appDataHelper.mInstaller;
                                String str = StorageManager.UUID_PRIVATE_INTERNAL;
                                if (installer.checkBeforeRemote()) {
                                    try {
                                        installer.mInstalld.fixupAppData(str, 3);
                                    } catch (Exception e) {
                                        Installer.InstallerException.from(e);
                                        throw null;
                                    }
                                }
                            } catch (Installer.InstallerException e2) {
                                Slog.w("PackageManager", "Trouble fixing GIDs", e2);
                            }
                            timingsTraceLog.traceEnd();
                            timingsTraceLog.traceBegin("AppDataPrepare");
                            if (list == null || list.isEmpty()) {
                                return;
                            }
                            Installer.Batch batch = new Installer.Batch();
                            Iterator it = list.iterator();
                            int i3 = 0;
                            while (it.hasNext()) {
                                PackageSetting packageStateInternal =
                                        appDataHelper
                                                .mPm
                                                .snapshotComputer()
                                                .getPackageStateInternal((String) it.next());
                                if (packageStateInternal != null
                                        && packageStateInternal
                                                .getUserStateOrDefault(0)
                                                .isInstalled()) {
                                    appDataHelper.prepareAppDataAndMigrate(
                                            batch, packageStateInternal.pkg, 0, i2, true);
                                    i3++;
                                }
                            }
                            PackageManagerTracedLock packageManagerTracedLock2 =
                                    appDataHelper.mPm.mInstallLock;
                            packageManagerTracedLock2.mLock.lock();
                            try {
                                appDataHelper.executeBatchLI(batch);
                                packageManagerTracedLock2.close();
                                timingsTraceLog.traceEnd();
                                Slog.i(
                                        "PackageManager",
                                        "Deferred reconcileAppsData finished " + i3 + " packages");
                            } catch (Throwable th) {
                                try {
                                    packageManagerTracedLock2.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        }
                    });
        } catch (Throwable th) {
            try {
                packageManagerTracedLock.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final CompletableFuture prepareAppData(
            Installer.Batch batch,
            final PackageSetting packageSetting,
            int i,
            final int i2,
            final int i3) {
        String str;
        final String str2 = packageSetting.mName;
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                str =
                        packageSetting.readUserState(i2).isInstantApp()
                                ? ":ephemeralapp:complete"
                                : ":complete";
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        final AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
        final String str3 = packageSetting.volumeUuid;
        int i4 = packageSetting.mAppId;
        String seInfo = packageSetting.getSeInfo();
        Preconditions.checkNotNull(seInfo);
        final CreateAppDataArgs buildCreateAppDataArgs =
                Installer.buildCreateAppDataArgs(
                        i2,
                        i3,
                        packageSetting.getUsesSdkLibraries().length > 0,
                        i4,
                        str3,
                        str2,
                        seInfo + str,
                        packageSetting.mTargetSdkVersion);
        buildCreateAppDataArgs.previousAppId = i;
        return batch.createAppData(buildCreateAppDataArgs)
                .whenComplete(
                        new BiConsumer() { // from class:
                                           // com.android.server.pm.AppDataHelper$$ExternalSyntheticLambda3
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                AppDataHelper appDataHelper = AppDataHelper.this;
                                String str4 = str2;
                                String str5 = str3;
                                int i5 = i2;
                                int i6 = i3;
                                CreateAppDataArgs createAppDataArgs = buildCreateAppDataArgs;
                                PackageSetting packageSetting2 = packageSetting;
                                AndroidPackage androidPackage = androidPackageInternal;
                                CreateAppDataResult createAppDataResult = (CreateAppDataResult) obj;
                                Throwable th2 = (Throwable) obj2;
                                appDataHelper.getClass();
                                if (th2 != null) {
                                    PackageManagerServiceUtils.logCriticalInfo(
                                            5,
                                            "Failed to create app data for "
                                                    + str4
                                                    + ", but trying to recover: "
                                                    + th2);
                                    appDataHelper.destroyAppDataLeafLIF(i5, i6, str4, str5);
                                    try {
                                        Installer installer = appDataHelper.mInstaller;
                                        if (installer.checkBeforeRemote()) {
                                            createAppDataArgs.previousAppId = 0;
                                            try {
                                                createAppDataResult =
                                                        installer.mInstalld.createAppData(
                                                                createAppDataArgs);
                                            } catch (Exception e) {
                                                Installer.InstallerException.from(e);
                                                throw null;
                                            }
                                        } else {
                                            CreateAppDataResult createAppDataResult2 =
                                                    new CreateAppDataResult();
                                            createAppDataResult2.ceDataInode = -1L;
                                            createAppDataResult2.deDataInode = -1L;
                                            createAppDataResult2.exceptionCode = 0;
                                            createAppDataResult2.exceptionMessage = null;
                                            createAppDataResult = createAppDataResult2;
                                        }
                                        PackageManagerServiceUtils.logCriticalInfo(
                                                3, "Recovery succeeded!");
                                    } catch (Installer.InstallerException unused) {
                                        PackageManagerServiceUtils.logCriticalInfo(
                                                3, "Recovery failed!");
                                    }
                                }
                                long j = createAppDataResult.ceDataInode;
                                long j2 = createAppDataResult.deDataInode;
                                if ((i6 & 2) != 0 && j != -1) {
                                    PackageManagerTracedLock packageManagerTracedLock2 =
                                            appDataHelper.mPm.mLock;
                                    boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                                    synchronized (packageManagerTracedLock2) {
                                        try {
                                            PackageUserStateImpl modifyUserState =
                                                    packageSetting2.modifyUserState(i5);
                                            modifyUserState.mCeDataInode = j;
                                            modifyUserState.onChanged$4();
                                            packageSetting2.onChanged$2();
                                        } finally {
                                        }
                                    }
                                }
                                if ((i6 & 1) != 0 && j2 != -1) {
                                    PackageManagerTracedLock packageManagerTracedLock3 =
                                            appDataHelper.mPm.mLock;
                                    boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                                    synchronized (packageManagerTracedLock3) {
                                        try {
                                            PackageUserStateImpl modifyUserState2 =
                                                    packageSetting2.modifyUserState(i5);
                                            modifyUserState2.mDeDataInode = j2;
                                            modifyUserState2.onChanged$4();
                                            packageSetting2.onChanged$2();
                                        } finally {
                                        }
                                    }
                                }
                                if (androidPackage != null) {
                                    appDataHelper.prepareAppDataContentsLeafLIF(
                                            androidPackage, packageSetting2, i5, i6);
                                }
                            }
                        });
    }

    public final void prepareAppDataAfterInstallLIF(AndroidPackage androidPackage) {
        PackageSetting packageLPr;
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        ArrayList arrayList =
                (ArrayList) this.mInjector.getUserManagerService().mLocalService.getUsers(false);
        int[] iArr = new int[arrayList.size()];
        int size = arrayList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = ((UserInfo) arrayList.get(i2)).id;
            if (packageLPr.getInstalled(i3)) {
                iArr[i] = i3;
                i++;
            }
        }
        prepareAppDataPostCommitLIF(packageLPr, Arrays.copyOf(iArr, i));
    }

    public final void prepareAppDataAndMigrate(
            Installer.Batch batch,
            AndroidPackage androidPackage,
            final int i,
            final int i2,
            final boolean z) {
        final PackageSetting packageLPr;
        if (androidPackage == null) {
            Slog.wtf("PackageManager", "Package was null!", new Throwable());
            return;
        }
        if (!shouldHaveAppStorage(androidPackage)) {
            Slog.w(
                    "PackageManager",
                    "Skipping preparing app data for " + androidPackage.getPackageName());
            return;
        }
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                packageLPr = this.mPm.mSettings.getPackageLPr(androidPackage.getPackageName());
            } catch (Throwable th) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        prepareAppData(batch, packageLPr, -1, i, i2)
                .thenRun(
                        new Runnable() { // from class:
                                         // com.android.server.pm.AppDataHelper$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                AppDataHelper appDataHelper = AppDataHelper.this;
                                boolean z4 = z;
                                PackageSetting packageSetting = packageLPr;
                                int i3 = i;
                                int i4 = i2;
                                appDataHelper.getClass();
                                if (z4
                                        && packageSetting.isSystem()
                                        && !StorageManager.isFileEncrypted()) {
                                    int i5 =
                                            packageSetting.isDefaultToDeviceProtectedStorage()
                                                    ? 1
                                                    : 2;
                                    try {
                                        Installer installer = appDataHelper.mInstaller;
                                        String str = packageSetting.volumeUuid;
                                        String str2 = packageSetting.mName;
                                        if (installer.checkBeforeRemote()) {
                                            try {
                                                installer.mInstalld.migrateAppData(
                                                        str, str2, i3, i5);
                                            } catch (Exception e) {
                                                Installer.InstallerException.from(e);
                                                throw null;
                                            }
                                        }
                                    } catch (Installer.InstallerException e2) {
                                        PackageManagerServiceUtils.logCriticalInfo(
                                                5,
                                                "Failed to migrate "
                                                        + packageSetting.mName
                                                        + ": "
                                                        + e2.getMessage());
                                    }
                                    Installer.Batch batch2 = new Installer.Batch();
                                    appDataHelper.prepareAppData(
                                            batch2, packageSetting, -1, i3, i4);
                                    appDataHelper.executeBatchLI(batch2);
                                }
                            }
                        });
    }

    public final void prepareAppDataContentsLeafLIF(
            AndroidPackage androidPackage,
            PackageStateInternal packageStateInternal,
            int i,
            int i2) {
        String volumeUuid = androidPackage.getVolumeUuid();
        String packageName = androidPackage.getPackageName();
        if ((i2 & 2) != 0) {
            String primaryCpuAbi =
                    packageStateInternal == null
                            ? ((AndroidPackageHidden) androidPackage).getPrimaryCpuAbi()
                            : packageStateInternal.getPrimaryCpuAbi();
            if (primaryCpuAbi == null || VMRuntime.is64BitAbi(primaryCpuAbi)) {
                return;
            }
            String nativeLibraryDir = androidPackage.getNativeLibraryDir();
            if (BatteryService$$ExternalSyntheticOutline0.m45m(nativeLibraryDir)) {
                try {
                    Installer installer = this.mInstaller;
                    if (installer.checkBeforeRemote()) {
                        BlockGuard.getVmPolicy().onPathAccess(nativeLibraryDir);
                        try {
                            installer.mInstalld.linkNativeLibraryDirectory(
                                    volumeUuid, packageName, nativeLibraryDir, i);
                        } catch (Exception e) {
                            Installer.InstallerException.from(e);
                            throw null;
                        }
                    }
                } catch (Installer.InstallerException e2) {
                    Slog.e(
                            "PackageManager",
                            "Failed to link native for " + packageName + ": " + e2);
                }
            }
        }
    }

    public final void prepareAppDataPostCommitLIF(final PackageSetting packageSetting, int[] iArr) {
        int i;
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                this.mPm.mSettings.writeKernelMappingLPr(packageSetting);
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        AndroidPackageInternal androidPackageInternal = packageSetting.pkg;
        if (androidPackageInternal != null && !shouldHaveAppStorage(androidPackageInternal)) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Skipping preparing app data for "),
                    packageSetting.mName,
                    "PackageManager");
            return;
        }
        Installer.Batch batch = new Installer.Batch();
        final UserManagerService.LocalService localService =
                this.mInjector.getUserManagerService().mLocalService;
        final StorageManagerInternal storageManagerInternal =
                (StorageManagerInternal)
                        this.mInjector.mGetLocalServiceProducer.produce(
                                StorageManagerInternal.class);
        for (final int i2 : iArr) {
            if (StorageManager.isCeStorageUnlocked(i2)
                    && storageManagerInternal.isCeStoragePrepared(i2)) {
                i = 3;
            } else if (localService.isUserRunning(i2)) {
                i = 1;
            }
            prepareAppData(batch, packageSetting, 0, i2, i)
                    .thenRun(
                            new Runnable() { // from class:
                                             // com.android.server.pm.AppDataHelper$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UserManagerInternal userManagerInternal =
                                            UserManagerInternal.this;
                                    int i3 = i2;
                                    PackageSetting packageSetting2 = packageSetting;
                                    StorageManagerInternal storageManagerInternal2 =
                                            storageManagerInternal;
                                    if (userManagerInternal.isUserUnlockingOrUnlocked(i3)) {
                                        storageManagerInternal2.prepareAppDataAfterInstall(
                                                packageSetting2.mName,
                                                UserHandle.getUid(i3, packageSetting2.mAppId));
                                    }
                                }
                            });
        }
        executeBatchLI(batch);
    }

    public final List reconcileAppsDataLI(String str, int i, int i2, boolean z, boolean z2) {
        ArrayList arrayList;
        String str2;
        String str3;
        String str4;
        String str5;
        int i3;
        Computer computer;
        String str6;
        ArrayList arrayList2;
        ArrayList arrayList3;
        String str7;
        String str8;
        Computer computer2;
        int i4;
        int i5;
        ArrayList arrayList4;
        String str9;
        String str10;
        String str11;
        int i6;
        Computer computer3;
        String str12;
        int i7;
        File[] fileArr;
        int i8;
        StringBuilder m =
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        i, "reconcileAppsData for ", str, " u", " 0x");
        m.append(Integer.toHexString(i2));
        m.append(" migrateAppData=");
        m.append(z);
        String str13 = "PackageManager";
        Slog.v("PackageManager", m.toString());
        ArrayList arrayList5 = z2 ? new ArrayList() : null;
        int i9 = 5;
        try {
            Installer installer = this.mInstaller;
            if (installer.checkBeforeRemote()) {
                try {
                    installer.mInstalld.cleanupInvalidPackageDirs(str, i, i2);
                } catch (Exception e) {
                    Installer.InstallerException.from(e);
                    throw null;
                }
            }
        } catch (Installer.InstallerException e2) {
            PackageManagerServiceUtils.logCriticalInfo(5, "Failed to cleanup deleted dirs: " + e2);
        }
        File dataUserCeDirectory = Environment.getDataUserCeDirectory(str, i);
        File dataUserDeDirectory = Environment.getDataUserDeDirectory(str, i);
        Computer snapshotComputer = this.mPm.snapshotComputer();
        String str14 = " due to: ";
        String str15 = "Destroying ";
        if ((i2 & 2) == 0) {
            arrayList = arrayList5;
            str2 = "PackageManager";
            str3 = "Destroying ";
            str4 = " due to: ";
            str5 = "Failed to destroy: ";
            i3 = 5;
            computer = snapshotComputer;
        } else {
            if (StorageManager.isFileEncrypted() && !StorageManager.isCeStorageUnlocked(i)) {
                throw new RuntimeException(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i,
                                "Yikes, someone asked us to reconcile CE storage while ",
                                " was still locked; this would have caused massive data loss!"));
            }
            File[] listFilesOrEmpty = FileUtils.listFilesOrEmpty(dataUserCeDirectory);
            int length = listFilesOrEmpty.length;
            String str16 = "Failed to destroy: ";
            int i10 = 0;
            while (i10 < length) {
                File file = listFilesOrEmpty[i10];
                String name = file.getName();
                try {
                    assertPackageStorageValid(snapshotComputer, str, name, i);
                    arrayList4 = arrayList5;
                    str9 = str13;
                    i7 = length;
                    fileArr = listFilesOrEmpty;
                    str10 = str15;
                    str11 = str14;
                    computer3 = snapshotComputer;
                    str12 = str16;
                    i6 = 5;
                    i8 = i10;
                } catch (PackageManagerException e3) {
                    int i11 = length;
                    PackageManagerServiceUtils.logCriticalInfo(5, str15 + file + str14 + e3);
                    try {
                        i7 = i11;
                        fileArr = listFilesOrEmpty;
                        str10 = str15;
                        str11 = str14;
                        arrayList4 = arrayList5;
                        str12 = str16;
                        i8 = i10;
                        str9 = str13;
                        i6 = 5;
                        computer3 = snapshotComputer;
                    } catch (Installer.InstallerException e4) {
                        e = e4;
                        arrayList4 = arrayList5;
                        str9 = str13;
                        str10 = str15;
                        str11 = str14;
                        i6 = 5;
                        computer3 = snapshotComputer;
                        str12 = str16;
                        i7 = i11;
                        fileArr = listFilesOrEmpty;
                        i8 = i10;
                    }
                    try {
                        this.mInstaller.destroyAppData(str, name, i, 2, 0L);
                    } catch (Installer.InstallerException e5) {
                        e = e5;
                        PackageManagerServiceUtils.logCriticalInfo(i6, str12 + e);
                        i10 = i8 + 1;
                        str16 = str12;
                        i9 = i6;
                        str14 = str11;
                        length = i7;
                        listFilesOrEmpty = fileArr;
                        str15 = str10;
                        arrayList5 = arrayList4;
                        str13 = str9;
                        snapshotComputer = computer3;
                    }
                }
                i10 = i8 + 1;
                str16 = str12;
                i9 = i6;
                str14 = str11;
                length = i7;
                listFilesOrEmpty = fileArr;
                str15 = str10;
                arrayList5 = arrayList4;
                str13 = str9;
                snapshotComputer = computer3;
            }
            arrayList = arrayList5;
            str2 = str13;
            str3 = str15;
            str4 = str14;
            i3 = i9;
            computer = snapshotComputer;
            str5 = str16;
        }
        if ((i2 & 1) != 0) {
            File[] listFilesOrEmpty2 = FileUtils.listFilesOrEmpty(dataUserDeDirectory);
            int length2 = listFilesOrEmpty2.length;
            int i12 = 0;
            while (i12 < length2) {
                File file2 = listFilesOrEmpty2[i12];
                String name2 = file2.getName();
                Computer computer4 = computer;
                try {
                    assertPackageStorageValid(computer4, str, name2, i);
                    computer2 = computer4;
                    i4 = length2;
                    i5 = i12;
                    str8 = str3;
                } catch (PackageManagerException e6) {
                    String str17 = str3;
                    PackageManagerServiceUtils.logCriticalInfo(i3, str17 + file2 + str4 + e6);
                    try {
                        str8 = str17;
                        computer2 = computer4;
                        i4 = length2;
                        i5 = i12;
                    } catch (Installer.InstallerException e7) {
                        e = e7;
                        str8 = str17;
                        computer2 = computer4;
                        i4 = length2;
                        i5 = i12;
                    }
                    try {
                        this.mInstaller.destroyAppData(str, name2, i, 1, 0L);
                    } catch (Installer.InstallerException e8) {
                        e = e8;
                        PackageManagerServiceUtils.logCriticalInfo(i3, str5 + e);
                        i12 = i5 + 1;
                        str3 = str8;
                        length2 = i4;
                        computer = computer2;
                    }
                }
                i12 = i5 + 1;
                str3 = str8;
                length2 = i4;
                computer = computer2;
            }
        }
        Trace.traceBegin(262144L, "prepareAppDataAndMigrate");
        Installer.Batch batch = new Installer.Batch();
        Iterator it = ((ArrayList) computer.getVolumePackages(str)).iterator();
        int i13 = 0;
        while (it.hasNext()) {
            PackageStateInternal packageStateInternal = (PackageStateInternal) it.next();
            String packageName = packageStateInternal.getPackageName();
            if (packageStateInternal.getPkg() == null) {
                str6 = str2;
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "Odd, missing scanned package ", packageName, str6);
                arrayList2 = arrayList;
            } else {
                str6 = str2;
                if (!z2 || packageStateInternal.getPkg().isCoreApp()) {
                    ArrayList arrayList6 = arrayList;
                    if (packageStateInternal.getUserStateOrDefault(i).isInstalled()) {
                        arrayList3 = arrayList6;
                        str7 = str6;
                        prepareAppDataAndMigrate(batch, packageStateInternal.getPkg(), i, i2, z);
                        i13++;
                    } else {
                        arrayList3 = arrayList6;
                        str7 = str6;
                    }
                    arrayList = arrayList3;
                    str2 = str7;
                } else {
                    arrayList2 = arrayList;
                    arrayList2.add(packageName);
                }
            }
            arrayList = arrayList2;
            str2 = str6;
        }
        ArrayList arrayList7 = arrayList;
        executeBatchLI(batch);
        Trace.traceEnd(262144L);
        Slog.v(str2, "reconcileAppsData finished " + i13 + " packages");
        return arrayList7;
    }
}
