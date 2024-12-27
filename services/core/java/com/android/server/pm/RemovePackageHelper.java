package com.android.server.pm;

import android.os.Environment;
import android.os.SystemClock;
import android.os.Trace;
import android.os.incremental.IncrementalManager;
import android.util.Slog;
import android.util.SparseBooleanArray;

import com.android.internal.pm.parsing.pkg.AndroidPackageLegacyUtils;
import com.android.internal.pm.parsing.pkg.PackageImpl;
import com.android.internal.pm.pkg.component.ParsedInstrumentation;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.pm.parsing.PackageCacher;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageUserStateImpl;
import com.android.server.pm.resolution.ComponentResolver;
import com.android.server.pm.verify.domain.DomainVerificationService;
import com.android.server.pm.verify.domain.DomainVerificationSettings;
import com.android.server.pm.verify.domain.models.DomainVerificationStateMap;

import java.io.File;
import java.util.Collections;

public final class RemovePackageHelper {
    public final AppDataHelper mAppDataHelper;
    public final BroadcastHelper mBroadcastHelper;
    public final IncrementalManager mIncrementalManager;
    public final Installer mInstaller;
    public final PermissionManagerService.PermissionManagerServiceInternalImpl mPermissionManager;
    public final PackageManagerService mPm;
    public final SharedLibrariesImpl mSharedLibraries;

    public RemovePackageHelper(
            PackageManagerService packageManagerService,
            AppDataHelper appDataHelper,
            BroadcastHelper broadcastHelper) {
        this.mPm = packageManagerService;
        PackageManagerServiceInjector packageManagerServiceInjector =
                packageManagerService.mInjector;
        this.mIncrementalManager =
                (IncrementalManager)
                        packageManagerServiceInjector.mIncrementalManagerProducer.get(
                                packageManagerServiceInjector.mPackageManager,
                                packageManagerServiceInjector);
        PackageManagerServiceInjector packageManagerServiceInjector2 =
                packageManagerService.mInjector;
        this.mInstaller = packageManagerServiceInjector2.mInstaller;
        this.mPermissionManager =
                (PermissionManagerService.PermissionManagerServiceInternalImpl)
                        packageManagerServiceInjector2.mPermissionManagerServiceProducer.get(
                                packageManagerServiceInjector2.mPackageManager,
                                packageManagerServiceInjector2);
        this.mSharedLibraries = packageManagerServiceInjector2.getSharedLibrariesImpl();
        this.mAppDataHelper = appDataHelper;
        this.mBroadcastHelper = broadcastHelper;
    }

    public final void cleanPackageDataStructuresLILPw(
            AndroidPackage androidPackage, boolean z, boolean z2) {
        ComponentResolver componentResolver = this.mPm.mComponentResolver;
        PackageManagerTracedLock packageManagerTracedLock = componentResolver.mLock;
        boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                componentResolver.removeAllComponentsLocked(androidPackage);
                componentResolver.dispatchChange(componentResolver);
            } catch (Throwable th) {
                boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        PermissionManagerService.this.mPermissionManagerServiceImpl.onPackageRemoved(
                androidPackage);
        PackageProperty packageProperty = this.mPm.mPackageProperty;
        packageProperty.getClass();
        packageProperty.mApplicationProperties =
                PackageProperty.removeProperties(
                        androidPackage.getProperties(), packageProperty.mApplicationProperties);
        packageProperty.mActivityProperties =
                PackageProperty.removeComponentProperties(
                        androidPackage.getActivities(), packageProperty.mActivityProperties);
        packageProperty.mProviderProperties =
                PackageProperty.removeComponentProperties(
                        androidPackage.getProviders(), packageProperty.mProviderProperties);
        packageProperty.mReceiverProperties =
                PackageProperty.removeComponentProperties(
                        androidPackage.getReceivers(), packageProperty.mReceiverProperties);
        packageProperty.mServiceProperties =
                PackageProperty.removeComponentProperties(
                        androidPackage.getServices(), packageProperty.mServiceProperties);
        int size = ArrayUtils.size(androidPackage.getInstrumentations());
        for (int i = 0; i < size; i++) {
            this.mPm.mInstrumentation.remove(
                    ((ParsedInstrumentation) androidPackage.getInstrumentations().get(i))
                            .getComponentName());
        }
        if (z) {
            int size2 = androidPackage.getLibraryNames().size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.mSharedLibraries.removeSharedLibrary(
                        0L, (String) androidPackage.getLibraryNames().get(i2));
            }
        }
        if (androidPackage.getSdkLibraryName() != null) {
            this.mSharedLibraries.removeSharedLibrary(
                    androidPackage.getSdkLibVersionMajor(), androidPackage.getSdkLibraryName());
        }
        if (androidPackage.getStaticSharedLibraryName() != null) {
            this.mSharedLibraries.removeSharedLibrary(
                    androidPackage.getStaticSharedLibraryVersion(),
                    androidPackage.getStaticSharedLibraryName());
        }
    }

    public final void cleanUpForMoveInstall(String str, String str2, String str3) {
        int i;
        int i2;
        File file = new File(Environment.getDataAppDirectory(str), new File(str3).getName());
        GmsAlarmManager$$ExternalSyntheticOutline0.m(
                "Cleaning up ", str2, " on ", str, "PackageManager");
        PackageManagerService packageManagerService = this.mPm;
        int[] userIds = packageManagerService.mUserManager.getUserIds();
        PackageManagerTracedLock packageManagerTracedLock = packageManagerService.mInstallLock;
        packageManagerTracedLock.mLock.lock();
        try {
            int length = userIds.length;
            int i3 = 0;
            while (i3 < length) {
                try {
                    i = length;
                    i2 = i3;
                    try {
                        packageManagerService.mInstaller.destroyAppData(
                                str, str2, userIds[i3], 131075, 0L);
                    } catch (Installer.InstallerException e) {
                        e = e;
                        Slog.w("PackageManager", String.valueOf(e));
                        i3 = i2 + 1;
                        length = i;
                    }
                } catch (Installer.InstallerException e2) {
                    e = e2;
                    i = length;
                    i2 = i3;
                }
                i3 = i2 + 1;
                length = i;
            }
            removeCodePathLI(file);
            packageManagerTracedLock.close();
        } finally {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0055 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void cleanUpResources(
            java.lang.String r4, java.io.File r5, java.lang.String[] r6) {
        /*
            r3 = this;
            com.android.server.pm.PackageManagerService r0 = r3.mPm
            com.android.server.pm.PackageManagerTracedLock r0 = r0.mInstallLock
            com.android.server.pm.PackageManagerTracedLock$RawLock r1 = r0.mLock
            r1.lock()
            if (r5 == 0) goto L27
            java.lang.String r1 = r5.getAbsolutePath()     // Catch: java.lang.Throwable -> L25
            java.lang.String r2 = com.android.server.pm.AsecInstallHelper.ASEC_INTERNAL_PATH     // Catch: java.lang.Throwable -> L25
            java.lang.String r2 = "/mnt/asec"
            boolean r1 = r1.startsWith(r2)     // Catch: java.lang.Throwable -> L25
            if (r1 == 0) goto L27
            com.android.server.pm.PackageManagerService r1 = r3.mPm     // Catch: java.lang.Throwable -> L25
            com.samsung.android.server.pm.lifecycle.PmCustomInjector r1 = r1.mCustomInjector     // Catch: java.lang.Throwable -> L25
            com.android.server.pm.AsecInstallHelper r1 = r1.getAsecInstallHelper()     // Catch: java.lang.Throwable -> L25
            r1.cleanUpAsecResources(r5, r6)     // Catch: java.lang.Throwable -> L25
            goto L50
        L25:
            r3 = move-exception
            goto L73
        L27:
            java.util.List r6 = java.util.Collections.EMPTY_LIST     // Catch: java.lang.Throwable -> L25
            if (r5 == 0) goto L4d
            boolean r6 = r5.exists()     // Catch: java.lang.Throwable -> L25
            if (r6 == 0) goto L4d
            android.content.pm.parsing.result.ParseTypeImpl r6 = android.content.pm.parsing.result.ParseTypeImpl.forDefaultParsing()     // Catch: java.lang.Throwable -> L25
            android.content.pm.parsing.result.ParseInput r6 = r6.reset()     // Catch: java.lang.Throwable -> L25
            r1 = 0
            android.content.pm.parsing.result.ParseResult r6 = android.content.pm.parsing.ApkLiteParseUtils.parsePackageLite(r6, r5, r1)     // Catch: java.lang.Throwable -> L25
            boolean r1 = r6.isSuccess()     // Catch: java.lang.Throwable -> L25
            if (r1 == 0) goto L4d
            java.lang.Object r6 = r6.getResult()     // Catch: java.lang.Throwable -> L25
            android.content.pm.parsing.PackageLite r6 = (android.content.pm.parsing.PackageLite) r6     // Catch: java.lang.Throwable -> L25
            r6.getAllApkPaths()     // Catch: java.lang.Throwable -> L25
        L4d:
            r3.removeCodePathLI(r5)     // Catch: java.lang.Throwable -> L25
        L50:
            r0.close()
            if (r4 != 0) goto L56
            return
        L56:
            com.android.server.pm.PackageManagerService r6 = r3.mPm
            com.android.server.pm.PackageManagerTracedLock r6 = r6.mLock
            boolean r0 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION
            monitor-enter(r6)
            com.android.server.pm.PackageManagerService r3 = r3.mPm     // Catch: java.lang.Throwable -> L6b
            com.android.server.pm.Settings r3 = r3.mSettings     // Catch: java.lang.Throwable -> L6b
            com.android.server.pm.PackageSetting r3 = r3.getPackageLPr(r4)     // Catch: java.lang.Throwable -> L6b
            if (r3 == 0) goto L6d
            r3.removeOldPath(r5)     // Catch: java.lang.Throwable -> L6b
            goto L6d
        L6b:
            r3 = move-exception
            goto L6f
        L6d:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L6b
            return
        L6f:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L6b
            boolean r4 = com.android.server.pm.PackageManagerService.DEBUG_COMPRESSION
            throw r3
        L73:
            r0.close()     // Catch: java.lang.Throwable -> L77
            goto L7b
        L77:
            r4 = move-exception
            r3.addSuppressed(r4)
        L7b:
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.RemovePackageHelper.cleanUpResources(java.lang.String,"
                    + " java.io.File, java.lang.String[]):void");
    }

    public final void clearPackageStateForUserLIF(
            final PackageSetting packageSetting, final int i, int i2) {
        AndroidPackage androidPackage;
        SharedUserSetting sharedUserSettingLPr;
        int indexOfValue;
        String str = packageSetting.mName;
        this.mAppDataHelper.getClass();
        AppDataHelper.destroyAppProfilesLIF(str);
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                androidPackage = (AndroidPackage) this.mPm.mPackages.mStorage.get(str);
                sharedUserSettingLPr = this.mPm.mSettings.getSharedUserSettingLPr(packageSetting);
            } finally {
            }
        }
        AndroidPackage buildFakeForDeletion =
                androidPackage != null
                        ? androidPackage
                        : PackageImpl.buildFakeForDeletion(str, packageSetting.volumeUuid);
        if ((i2 & 1) != 0) {
            if ((i2 & 16) != 0) {
                this.mAppDataHelper.clearAppDataLIF(buildFakeForDeletion, i, 23);
                this.mAppDataHelper.clearAppDataLIF(buildFakeForDeletion, i, 39);
                return;
            }
            return;
        }
        AppDataHelper appDataHelper = this.mAppDataHelper;
        appDataHelper.getClass();
        if (buildFakeForDeletion == null) {
            Slog.wtf("PackageManager", "Package was null!", new Throwable());
        } else {
            appDataHelper.destroyAppDataLeafLIF(
                    i,
                    7,
                    buildFakeForDeletion.getPackageName(),
                    buildFakeForDeletion.getVolumeUuid());
        }
        if (i != -1) {
            PackageUserStateImpl modifyUserState = packageSetting.modifyUserState(i);
            modifyUserState.mCeDataInode = -1L;
            modifyUserState.onChanged$4();
            packageSetting.onChanged$2();
            PackageUserStateImpl modifyUserState2 = packageSetting.modifyUserState(i);
            modifyUserState2.mDeDataInode = -1L;
            modifyUserState2.onChanged$4();
            packageSetting.onChanged$2();
        }
        PackageManagerService packageManagerService = this.mPm;
        final PreferredActivityHelper preferredActivityHelper =
                new PreferredActivityHelper(packageManagerService, this.mBroadcastHelper);
        if (i == -1) {
            DomainVerificationService domainVerificationService =
                    (DomainVerificationService) packageManagerService.mDomainVerificationManager;
            synchronized (domainVerificationService.mLock) {
                DomainVerificationStateMap domainVerificationStateMap =
                        domainVerificationService.mAttachedPkgStates;
                Object remove = domainVerificationStateMap.mPackageNameMap.remove(str);
                if (remove != null
                        && (indexOfValue =
                                        domainVerificationStateMap.mDomainSetIdMap.indexOfValue(
                                                remove))
                                >= 0) {
                    domainVerificationStateMap.mDomainSetIdMap.removeAt(indexOfValue);
                }
                DomainVerificationSettings domainVerificationSettings =
                        domainVerificationService.mSettings;
                synchronized (domainVerificationSettings.mLock) {
                    domainVerificationSettings.mPendingPkgStates.remove(str);
                    domainVerificationSettings.mRestoredPkgStates.remove(str);
                }
            }
            domainVerificationService.mConnection.scheduleWriteSettings();
            synchronized (this.mPm.mLock) {
                try {
                    this.mPm.mSettings.mKeySetManagerService.removeAppKeySetDataLPw(str);
                    PackageManagerServiceInjector packageManagerServiceInjector =
                            this.mPm.mInjector;
                    ((UpdateOwnershipHelper)
                                    packageManagerServiceInjector.mUpdateOwnershipHelperProducer
                                            .get(
                                                    packageManagerServiceInjector.mPackageManager,
                                                    packageManagerServiceInjector))
                            .removeUpdateOwnerDenyList(str);
                    Computer snapshotComputer = this.mPm.snapshotComputer();
                    AppsFilterImpl appsFilterImpl = this.mPm.mAppsFilter;
                    PackageSetting packageStateInternal =
                            snapshotComputer.getPackageStateInternal(str);
                    appsFilterImpl.getClass();
                    long currentTimeMicro = SystemClock.currentTimeMicro();
                    appsFilterImpl.removePackageInternal(
                            snapshotComputer, packageStateInternal, false, false);
                    long currentTimeMicro2 = SystemClock.currentTimeMicro() - currentTimeMicro;
                    int length = snapshotComputer.getUserInfos().length;
                    int size = snapshotComputer.getPackageStates().size();
                    int i3 = packageStateInternal.mAppId;
                    if (appsFilterImpl.mCacheReady) {
                        FrameworkStatsLog.write(
                                FrameworkStatsLog.PACKAGE_MANAGER_APPS_FILTER_CACHE_UPDATE_REPORTED,
                                2,
                                i3,
                                currentTimeMicro2,
                                length,
                                size,
                                appsFilterImpl.mShouldFilterCache.mSize);
                    }
                    final SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
                    this.mPm.clearPackagePreferredActivitiesLPw(str, sparseBooleanArray, -1);
                    this.mPm.mInjector.mBackgroundHandler.post(
                            new Runnable() { // from class:
                                             // com.android.server.pm.RemovePackageHelper$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    RemovePackageHelper removePackageHelper =
                                            RemovePackageHelper.this;
                                    SparseBooleanArray sparseBooleanArray2 = sparseBooleanArray;
                                    PreferredActivityHelper preferredActivityHelper2 =
                                            preferredActivityHelper;
                                    removePackageHelper.getClass();
                                    if (sparseBooleanArray2.size() > 0) {
                                        preferredActivityHelper2.updateDefaultHomeNotLocked(
                                                removePackageHelper.mPm.snapshotComputer(),
                                                sparseBooleanArray2);
                                        removePackageHelper.mBroadcastHelper
                                                .sendPreferredActivityChangedBroadcast(-1);
                                    }
                                }
                            });
                } finally {
                }
            }
        } else {
            ((DomainVerificationService) packageManagerService.mDomainVerificationManager)
                    .clearPackageForUser(i, str);
            preferredActivityHelper.clearPackagePreferredActivities(i, str);
            this.mPermissionManager.onPackageUninstalled(
                    str,
                    packageSetting.mAppId,
                    packageSetting,
                    androidPackage,
                    sharedUserSettingLPr != null
                            ? sharedUserSettingLPr.getPackages()
                            : Collections.emptyList(),
                    i);
        }
        this.mPm.mInjector.mBackgroundHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.RemovePackageHelper$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        RemovePackageHelper removePackageHelper = RemovePackageHelper.this;
                        PackageSetting packageSetting2 = packageSetting;
                        int i4 = i;
                        removePackageHelper.getClass();
                        try {
                            Trace.traceBegin(
                                    262144L,
                                    "clearKeystoreData:"
                                            + packageSetting2.mAppId
                                            + " for user: "
                                            + i4);
                            removePackageHelper.mAppDataHelper.clearKeystoreData(
                                    i4, packageSetting2.mAppId);
                        } finally {
                            Trace.traceEnd(262144L);
                        }
                    }
                });
    }

    public final void removeCodePath(File file) {
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mInstallLock;
        packageManagerTracedLock.mLock.lock();
        try {
            removeCodePathLI(file);
            packageManagerTracedLock.close();
        } catch (Throwable th) {
            try {
                packageManagerTracedLock.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void removeCodePathLI(File file) {
        Installer installer = this.mInstaller;
        if (file == null || !file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            file.delete();
            return;
        }
        File parentFile = file.getParentFile();
        boolean startsWith = parentFile.getName().startsWith("~~");
        try {
            if (this.mIncrementalManager != null
                    && IncrementalManager.isIncrementalPath(file.getAbsolutePath())) {
                if (startsWith) {
                    this.mIncrementalManager.rmPackageDir(parentFile);
                } else {
                    this.mIncrementalManager.rmPackageDir(file);
                }
            }
            String name = file.getName();
            installer.rmPackageDir(name, file.getAbsolutePath());
            if (startsWith) {
                installer.rmPackageDir(name, parentFile.getAbsolutePath());
                PackageManagerService packageManagerService = this.mPm;
                if (packageManagerService.mCacheDir == null) {
                    return;
                }
                new PackageCacher(packageManagerService.mCacheDir, null)
                        .cleanCachedResult(parentFile);
            }
        } catch (Installer.InstallerException e) {
            Slog.w("PackageManager", "Failed to remove code path", e);
        }
    }

    public final void removePackage(AndroidPackage androidPackage) {
        PackageManagerService packageManagerService = this.mPm;
        PackageManagerTracedLock packageManagerTracedLock = packageManagerService.mInstallLock;
        packageManagerTracedLock.mLock.lock();
        try {
            PackageSetting packageStateInternal =
                    packageManagerService
                            .snapshotComputer()
                            .getPackageStateInternal(androidPackage.getPackageName());
            if (packageStateInternal != null) {
                removePackageLI(packageStateInternal.mName, true);
            }
            packageManagerTracedLock.close();
        } catch (Throwable th) {
            try {
                packageManagerTracedLock.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void removePackageData(PackageSetting packageSetting, int[] iArr) {
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mInstallLock;
        packageManagerTracedLock.mLock.lock();
        try {
            removePackageDataLIF(packageSetting, -1, iArr, new PackageRemovedInfo(), 0, false);
            packageManagerTracedLock.close();
        } catch (Throwable th) {
            try {
                packageManagerTracedLock.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00c8 A[Catch: all -> 0x007a, TryCatch #1 {all -> 0x007a, blocks: (B:17:0x0052, B:19:0x006a, B:21:0x0074, B:23:0x0081, B:25:0x008c, B:28:0x009a, B:30:0x00a8, B:32:0x00ae, B:38:0x00c8, B:40:0x00b5, B:44:0x007c, B:45:0x00cb, B:46:0x00d6), top: B:16:0x0052 }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void removePackageDataLIF(
            com.android.server.pm.PackageSetting r10,
            int r11,
            int[] r12,
            com.android.server.pm.PackageRemovedInfo r13,
            int r14,
            boolean r15) {
        /*
            Method dump skipped, instructions count: 355
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.RemovePackageHelper.removePackageDataLIF(com.android.server.pm.PackageSetting,"
                    + " int, int[], com.android.server.pm.PackageRemovedInfo, int, boolean):void");
    }

    public final void removePackageLI(String str, boolean z) {
        PackageManagerTracedLock packageManagerTracedLock = this.mPm.mLock;
        boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                AndroidPackage androidPackage = (AndroidPackage) this.mPm.mPackages.remove(str);
                PermissionManagerService.this.mPermissionManagerServiceImpl
                        .removePackageGrantedPermissionsForMDM(str);
                if (androidPackage != null) {
                    cleanPackageDataStructuresLILPw(
                            androidPackage, AndroidPackageLegacyUtils.isSystem(androidPackage), z);
                }
            } catch (Throwable th) {
                boolean z3 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
    }
}
