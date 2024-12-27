package com.android.server.pm;

import android.os.Environment;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;

import com.android.internal.content.om.OverlayConfig;
import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.pm.parsing.PackageCacher;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.utils.Watchable;
import com.android.server.utils.WatchedArrayMap;
import com.android.server.utils.WatchedArraySet;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.server.pm.PmLog;
import com.samsung.android.server.pm.install.PmConfigParser;
import com.samsung.android.server.pm.install.PreloadDuplicateApps;

import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class InitAppsHelper {
    public final ApexManager mApexManager;
    public int mCachedSystemApps;
    public final List mDirsToScanAsSystem;
    public final ExecutorService mExecutorService;
    public final InstallPackageHelper mInstallPackageHelper;
    public final boolean mIsDeviceUpgrading;
    public final PackageManagerService mPm;
    public final int mScanFlags;
    public int mSystemPackagesCount;
    public final int mSystemParseFlags;
    public final int mSystemScanFlags;
    public long mSystemScanTime;
    public final ArrayMap mExpectingBetter = new ArrayMap();
    public final List mPossiblyDeletedUpdatedSystemApps = new ArrayList();
    public final List mStubSystemApps = new ArrayList();

    public InitAppsHelper(
            PackageManagerService packageManagerService,
            ApexManager apexManager,
            InstallPackageHelper installPackageHelper,
            List list) {
        ScanPartition scanPartition;
        this.mPm = packageManagerService;
        this.mApexManager = apexManager;
        this.mInstallPackageHelper = installPackageHelper;
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(list);
        ArrayList arrayList2 = new ArrayList();
        List activeApexInfos = apexManager.getActiveApexInfos();
        for (int i = 0; i < activeApexInfos.size(); i++) {
            ApexManager.ActiveApexInfo activeApexInfo =
                    (ApexManager.ActiveApexInfo) activeApexInfos.get(i);
            int size = PackageManagerService.SYSTEM_PARTITIONS.size();
            for (int i2 = 0; i2 < size; i2++) {
                ScanPartition scanPartition2 =
                        (ScanPartition) PackageManagerService.SYSTEM_PARTITIONS.get(i2);
                if (!activeApexInfo
                        .preInstalledApexPath
                        .getAbsolutePath()
                        .equals(scanPartition2.getFolder().getAbsolutePath())) {
                    if (!activeApexInfo
                            .preInstalledApexPath
                            .getAbsolutePath()
                            .startsWith(
                                    scanPartition2.getFolder().getAbsolutePath()
                                            + File.separator)) {}
                }
                scanPartition =
                        new ScanPartition(
                                activeApexInfo.apexDirectory, scanPartition2, activeApexInfo);
                break;
            }
            scanPartition = null;
            if (scanPartition != null) {
                arrayList2.add(scanPartition);
            }
        }
        arrayList.addAll(arrayList2);
        Slog.d("PackageManager", "Directories scanned as system partitions: " + arrayList);
        this.mDirsToScanAsSystem = arrayList;
        boolean isDeviceUpgrading = this.mPm.isDeviceUpgrading();
        this.mIsDeviceUpgrading = isDeviceUpgrading;
        if (isDeviceUpgrading || this.mPm.mFirstBoot) {
            this.mScanFlags = 4624;
        } else {
            this.mScanFlags = FrameworkStatsLog.EXPRESS_EVENT_REPORTED;
        }
        this.mSystemParseFlags = this.mPm.mDefParseFlags | 16;
        this.mSystemScanFlags =
                this.mScanFlags | EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT;
        this.mExecutorService = ConcurrentUtils.newFixedThreadPool(4, "package-parsing-thread", -2);
    }

    public int getSystemScanFlags() {
        return this.mSystemScanFlags;
    }

    public final void initNonSystemApps(final PackageParser2 packageParser2, int[] iArr, long j) {
        PackageManagerService packageManagerService;
        int i;
        int i2;
        String str;
        SharedUserSetting sharedUserSettingLPr;
        Slog.i("PackageManager", "!@Boot_EBS_F: boot_progress_pms_data_scan_start");
        EventLog.writeEvent(3080, SystemClock.uptimeMillis());
        if ((this.mScanFlags & 4096) == 4096) {
            try {
                DirectoryStream<Path> newDirectoryStream =
                        Files.newDirectoryStream(this.mPm.mAppInstallDir.toPath());
                try {
                    newDirectoryStream.forEach(new InitAppsHelper$$ExternalSyntheticLambda0());
                    newDirectoryStream.close();
                } finally {
                }
            } catch (Exception e) {
                Slog.w(
                        "PackageManager",
                        "Failed to walk the app install directory to fix the modes",
                        e);
            }
        }
        scanDirTracedLI(
                this.mPm.mAppInstallDir,
                0,
                this.mScanFlags | 128,
                packageParser2,
                this.mExecutorService,
                null);
        List<Runnable> shutdownNow = this.mExecutorService.shutdownNow();
        if (!shutdownNow.isEmpty()) {
            throw new IllegalStateException(
                    "Not all tasks finished before calling close: " + shutdownNow);
        }
        InstallPackageHelper installPackageHelper = this.mInstallPackageHelper;
        List list = this.mPossiblyDeletedUpdatedSystemApps;
        int i3 = this.mScanFlags;
        installPackageHelper.getClass();
        ArrayList arrayList = (ArrayList) list;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            String str2 = (String) arrayList.get(size);
            PackageManagerService packageManagerService2 = installPackageHelper.mPm;
            AndroidPackage androidPackage =
                    (AndroidPackage) packageManagerService2.mPackages.mStorage.get(str2);
            Settings settings = packageManagerService2.mSettings;
            PackageSetting packageSetting =
                    (PackageSetting) settings.mDisabledSysPackages.remove(str2);
            if (packageSetting != null
                    && (sharedUserSettingLPr = settings.getSharedUserSettingLPr(packageSetting))
                            != null) {
                sharedUserSettingLPr.mDisabledPackages.remove(packageSetting);
                settings.checkAndPruneSharedUserLPw(sharedUserSettingLPr, false);
            }
            RemovePackageHelper removePackageHelper = installPackageHelper.mRemovePackageHelper;
            if (androidPackage == null) {
                str =
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "Updated system package ",
                                str2,
                                " no longer exists; removing its data");
            } else {
                String str3 =
                        "Updated system package "
                                + str2
                                + " no longer exists; rescanning package on data";
                removePackageHelper.removePackage(androidPackage);
                PackageSetting packageLPr = packageManagerService2.mSettings.getPackageLPr(str2);
                if (packageLPr != null) {
                    packageLPr.pkgState.setUpdatedSystemApp(false);
                }
                File file = new File(androidPackage.getPath());
                try {
                    PackageManagerTracedLock packageManagerTracedLock =
                            packageManagerService2.mInstallLock;
                    packageManagerTracedLock.mLock.lock();
                    try {
                        installPackageHelper.initPackageTracedLI(file, 0, i3);
                        packageManagerTracedLock.close();
                    } finally {
                    }
                } catch (PackageManagerException e2) {
                    Slog.e(
                            "PackageManager",
                            "Failed to parse updated, ex-system package: " + e2.getMessage());
                }
                str = str3;
            }
            PackageSetting packageLPr2 = packageManagerService2.mSettings.getPackageLPr(str2);
            if (packageLPr2 != null
                    && packageManagerService2.mPackages.mStorage.get(str2) == null) {
                removePackageHelper.removePackageData(packageLPr2, iArr);
            }
            PackageManagerServiceUtils.logCriticalInfo(5, str);
        }
        if (this.mPm.mFirstBoot) {
            final int i4 = this.mScanFlags | 4224;
            final InstallPackageHelper installPackageHelper2 = this.mInstallPackageHelper;
            final ArrayMap arrayMap = this.mExpectingBetter;
            PreloadDuplicateApps preloadDuplicateApps = installPackageHelper2.mPreloadDuplicateApps;
            synchronized (preloadDuplicateApps.mLock) {
                ((ArrayMap) preloadDuplicateApps.mSystemPackages)
                        .forEach(
                                new BiConsumer() { // from class:
                                                   // com.samsung.android.server.pm.install.PreloadDuplicateApps$$ExternalSyntheticLambda0
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        ArrayMap arrayMap2 = arrayMap;
                                        String str4 = (String) obj;
                                        File file2 = (File) obj2;
                                        Log.d(
                                                "PreloadDuplicateApps",
                                                "Add system package "
                                                        + str4
                                                        + " ("
                                                        + file2
                                                        + ") to expectingBetter");
                                        arrayMap2.put(str4, file2);
                                    }
                                });
            }
            final ExecutorService newFixedThreadPool =
                    ConcurrentUtils.newFixedThreadPool(4, "package-parsing-thread", -2);
            PreloadDuplicateApps preloadDuplicateApps2 =
                    installPackageHelper2.mPreloadDuplicateApps;
            BiConsumer biConsumer =
                    new BiConsumer() { // from class:
                                       // com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda3
                        public final /* synthetic */ int f$1 = 0;

                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            int i5;
                            InstallPackageHelper installPackageHelper3 = InstallPackageHelper.this;
                            int i6 = this.f$1;
                            int i7 = i4;
                            PackageParser2 packageParser22 = packageParser2;
                            ExecutorService executorService = newFixedThreadPool;
                            String str4 = (String) obj;
                            File file2 = (File) obj2;
                            installPackageHelper3.getClass();
                            PackageManagerServiceUtils.logCriticalInfo(
                                    5, "Update a duplicate package: " + str4 + ", apk: " + file2);
                            PackageManagerService packageManagerService3 =
                                    installPackageHelper3.mPm;
                            PackageSetting packageLPr3 =
                                    packageManagerService3.mSettings.getPackageLPr(str4);
                            if (packageLPr3 == null) {
                                return;
                            }
                            packageManagerService3.mSettings.disableSystemPackageLPw(str4);
                            AndroidPackage androidPackage2 = packageLPr3.pkg;
                            RemovePackageHelper removePackageHelper2 =
                                    installPackageHelper3.mRemovePackageHelper;
                            removePackageHelper2.removePackage(androidPackage2);
                            if (ArrayUtils.isEmpty(file2.listFiles())) {
                                Log.d("PackageManager", "No files in app dir " + file2);
                                return;
                            }
                            ParallelPackageParser parallelPackageParser =
                                    new ParallelPackageParser(packageParser22, executorService);
                            parallelPackageParser.mExecutorService.submit(
                                    new ParallelPackageParser$$ExternalSyntheticLambda0(
                                            parallelPackageParser, file2, i6));
                            ParallelPackageParser.ParseResult take = parallelPackageParser.take();
                            Throwable th = take.throwable;
                            if (th == null) {
                                try {
                                    installPackageHelper3.addForInitLI(
                                            take.parsedPackage, i6, i7, null, null);
                                    i5 = 1;
                                } catch (PackageManagerException e3) {
                                    i5 = e3.error;
                                    PackageManagerServiceUtils.logCriticalInfo(
                                            5,
                                            "Failed to scan "
                                                    + take.scanFile
                                                    + ": "
                                                    + e3.getMessage());
                                }
                            } else {
                                if (!(th instanceof PackageManagerException)) {
                                    throw new IllegalStateException(
                                            "Unexpected exception occurred while parsing "
                                                    + take.scanFile,
                                            th);
                                }
                                PackageManagerException packageManagerException =
                                        (PackageManagerException) th;
                                i5 = packageManagerException.error;
                                PackageManagerServiceUtils.logCriticalInfo(
                                        5,
                                        "Failed to parse "
                                                + take.scanFile
                                                + ": "
                                                + packageManagerException.getMessage());
                            }
                            if ((65536 & i7) != 0 || i5 == 1) {
                                return;
                            }
                            PackageManagerServiceUtils.logCriticalInfo(
                                    5, "Deleting invalid package at " + take.scanFile);
                            removePackageHelper2.removeCodePath(take.scanFile);
                        }
                    };
            synchronized (preloadDuplicateApps2.mLock) {
                ((ArrayMap) preloadDuplicateApps2.mDuplicateDataPackages).forEach(biConsumer);
            }
            PreloadDuplicateApps preloadDuplicateApps3 =
                    installPackageHelper2.mPreloadDuplicateApps;
            synchronized (preloadDuplicateApps3.mLock) {
                ((ArrayMap) preloadDuplicateApps3.mDuplicateDataPackages).clear();
                ((ArrayMap) preloadDuplicateApps3.mSystemPackages).clear();
            }
            newFixedThreadPool.shutdownNow();
        }
        InstallPackageHelper installPackageHelper3 = this.mInstallPackageHelper;
        ArrayMap arrayMap2 = this.mExpectingBetter;
        List list2 = this.mStubSystemApps;
        int i5 = this.mSystemScanFlags;
        int i6 = this.mSystemParseFlags;
        installPackageHelper3.getClass();
        for (int i7 = 0; i7 < arrayMap2.size(); i7++) {
            String str4 = (String) arrayMap2.keyAt(i7);
            PackageManagerService packageManagerService3 = installPackageHelper3.mPm;
            if (!packageManagerService3.mPackages.mStorage.containsKey(str4)) {
                File file2 = (File) arrayMap2.valueAt(i7);
                PackageManagerServiceUtils.logCriticalInfo(
                        5, "Expected better " + str4 + " but never showed up; reverting to system");
                ArrayList arrayList2 =
                        (ArrayList) packageManagerService3.mInitAppsHelper.mDirsToScanAsSystem;
                for (int size2 = arrayList2.size() + (-1); size2 >= 0; size2--) {
                    ScanPartition scanPartition = (ScanPartition) arrayList2.get(size2);
                    if (scanPartition.containsPrivApp(file2)) {
                        i = 131072 | i5 | scanPartition.scanFlag;
                    } else if (scanPartition.containsApp(file2)) {
                        i = scanPartition.scanFlag | i5;
                    }
                    i2 = i6;
                    break;
                }
                i = 0;
                i2 = 0;
                Pair pair = new Pair(Integer.valueOf(i), Integer.valueOf(i2));
                int intValue = ((Integer) pair.first).intValue();
                int intValue2 = ((Integer) pair.second).intValue();
                if (intValue == 0) {
                    Slog.e("PackageManager", "Ignoring unexpected fallback path " + file2);
                } else {
                    packageManagerService3.mSettings.enableSystemPackageLPw(str4);
                    try {
                        PackageManagerTracedLock packageManagerTracedLock2 =
                                packageManagerService3.mInstallLock;
                        packageManagerTracedLock2.mLock.lock();
                        try {
                            if (installPackageHelper3
                                    .initPackageTracedLI(file2, intValue2, intValue)
                                    .isStub()) {
                                ((ArrayList) list2).add(str4);
                            }
                            packageManagerTracedLock2.close();
                        } finally {
                        }
                    } catch (PackageManagerException e3) {
                        Slog.e(
                                "PackageManager",
                                "Failed to parse original system package: " + e3.getMessage());
                    }
                }
            }
        }
        InstallPackageHelper installPackageHelper4 = this.mInstallPackageHelper;
        List list3 = this.mStubSystemApps;
        int i8 = this.mScanFlags;
        installPackageHelper4.getClass();
        ArrayList arrayList3 = (ArrayList) list3;
        int size3 = arrayList3.size() - 1;
        while (true) {
            packageManagerService = installPackageHelper4.mPm;
            if (size3 < 0) {
                break;
            }
            String str5 = (String) arrayList3.get(size3);
            if (packageManagerService.mSettings.mDisabledSysPackages.mStorage.containsKey(str5)) {
                arrayList3.remove(size3);
            } else {
                AndroidPackage androidPackage2 =
                        (AndroidPackage) packageManagerService.mPackages.mStorage.get(str5);
                if (androidPackage2 == null) {
                    arrayList3.remove(size3);
                } else {
                    PackageSetting packageLPr3 =
                            packageManagerService.mSettings.getPackageLPr(str5);
                    if (packageLPr3 == null || packageLPr3.getEnabled(0) != 3) {
                        try {
                            installPackageHelper4.installStubPackageLI(androidPackage2, 0, i8);
                            if (packageLPr3 != null) {
                                packageLPr3.setEnabled(0, 0, "android");
                            }
                            arrayList3.remove(size3);
                        } catch (PackageManagerException e4) {
                            Slog.e(
                                    "PackageManager",
                                    "Failed to parse uncompressed system package: "
                                            + e4.getMessage());
                        }
                    } else {
                        arrayList3.remove(size3);
                    }
                }
            }
            size3--;
        }
        for (int size4 = arrayList3.size() - 1; size4 >= 0; size4 += -1) {
            String str6 = (String) arrayList3.get(size4);
            packageManagerService.mSettings.getPackageLPr(str6).setEnabled(2, 0, "android");
            PackageManagerServiceUtils.logCriticalInfo(6, "Stub disabled; pkg: " + str6);
        }
        int i9 = PackageCacher.sCachedPackageReadCount.get() - this.mCachedSystemApps;
        long uptimeMillis = (SystemClock.uptimeMillis() - this.mSystemScanTime) - j;
        int size5 = this.mPm.mPackages.mStorage.size() - this.mSystemPackagesCount;
        StringBuilder m =
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        size5,
                        "!@Boot_EBS_F: Finished scanning non-system apps (",
                        uptimeMillis,
                        " ms), packageCount: ");
        m.append(" , cached: ");
        m.append(i9);
        Slog.i("PackageManager", m.toString());
        StringBuilder sb = new StringBuilder("Finished scanning non-system apps. Time: ");
        sb.append(uptimeMillis);
        sb.append(" ms, packageCount: ");
        sb.append(size5);
        sb.append(" , timePerPackage: ");
        sb.append(size5 == 0 ? 0L : uptimeMillis / size5);
        sb.append(" , cached: ");
        sb.append(i9);
        Slog.i("PackageManager", sb.toString());
        PmLog.logFinishedScanningInfo(size5, i9, uptimeMillis, "non-system");
        if (this.mIsDeviceUpgrading && size5 > 0) {
            FrameworkStatsLog.write(
                    FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED, 14, uptimeMillis / size5);
        }
        this.mExpectingBetter.clear();
        Settings settings2 = this.mPm.mSettings;
        WatchedArrayMap watchedArrayMap = settings2.mRenamedPackages;
        for (int size6 = watchedArrayMap.mStorage.size() - 1; size6 >= 0; size6--) {
            if (((PackageSetting)
                            settings2.mPackages.mStorage.get(
                                    watchedArrayMap.mStorage.valueAt(size6)))
                    == null) {
                Object removeAt = watchedArrayMap.mStorage.removeAt(size6);
                if (watchedArrayMap.mWatching
                        && (removeAt instanceof Watchable)
                        && !watchedArrayMap.mStorage.containsValue(removeAt)) {
                    ((Watchable) removeAt).unregisterObserver(watchedArrayMap.mObserver);
                }
                watchedArrayMap.dispatchChange(watchedArrayMap);
            }
        }
    }

    public final OverlayConfig initSystemApps(
            PackageParser2 packageParser2,
            WatchedArrayMap watchedArrayMap,
            final int[] iArr,
            long j) {
        int i;
        int i2;
        Trace.traceBegin(262144L, "scanApexPackages");
        try {
            InstallPackageHelper installPackageHelper = this.mInstallPackageHelper;
            ApexManager.ApexManagerImpl apexManagerImpl =
                    (ApexManager.ApexManagerImpl) this.mApexManager;
            apexManagerImpl.getClass();
            try {
                List scanApexPackages =
                        installPackageHelper.scanApexPackages(
                                apexManagerImpl.waitForApexService().getAllPackages(),
                                this.mSystemParseFlags,
                                this.mSystemScanFlags,
                                packageParser2,
                                this.mExecutorService);
                Trace.traceEnd(262144L);
                ApexManager.ApexManagerImpl apexManagerImpl2 =
                        (ApexManager.ApexManagerImpl) this.mApexManager;
                synchronized (apexManagerImpl2.mLock) {
                    apexManagerImpl2.notifyScanResultLocked(scanApexPackages);
                }
                ExecutorService executorService = this.mExecutorService;
                File file = new File(Environment.getRootDirectory(), "framework");
                int size = ((ArrayList) this.mDirsToScanAsSystem).size() - 1;
                while (true) {
                    i = this.mSystemScanFlags;
                    if (size < 0) {
                        break;
                    }
                    ScanPartition scanPartition =
                            (ScanPartition) ((ArrayList) this.mDirsToScanAsSystem).get(size);
                    if (scanPartition.getOverlayFolder() != null) {
                        scanDirTracedLI(
                                scanPartition.getOverlayFolder(),
                                this.mSystemParseFlags,
                                i | scanPartition.scanFlag,
                                packageParser2,
                                executorService,
                                scanPartition.apexInfo);
                    }
                    size--;
                }
                scanDirTracedLI(
                        file,
                        this.mSystemParseFlags,
                        i | 131073,
                        packageParser2,
                        executorService,
                        null);
                if (!this.mPm.mPackages.mStorage.containsKey("android")) {
                    throw new IllegalStateException(
                            "Failed to load frameworks package; check log for warnings");
                }
                int size2 = ((ArrayList) this.mDirsToScanAsSystem).size();
                for (int i3 = 0; i3 < size2; i3++) {
                    ScanPartition scanPartition2 =
                            (ScanPartition) ((ArrayList) this.mDirsToScanAsSystem).get(i3);
                    if (scanPartition2.getPrivAppFolder() != null) {
                        scanDirTracedLI(
                                scanPartition2.getPrivAppFolder(),
                                this.mSystemParseFlags,
                                131072 | i | scanPartition2.scanFlag,
                                packageParser2,
                                executorService,
                                scanPartition2.apexInfo);
                    }
                    scanDirTracedLI(
                            scanPartition2.getAppFolder(),
                            this.mSystemParseFlags,
                            i | scanPartition2.scanFlag,
                            packageParser2,
                            executorService,
                            scanPartition2.apexInfo);
                }
                final ArrayMap arrayMap = new ArrayMap();
                for (ApexManager.ActiveApexInfo activeApexInfo :
                        this.mApexManager.getActiveApexInfos()) {
                    Iterator it =
                            this.mApexManager
                                    .getApksInApex(
                                            this.mApexManager.getActivePackageNameForApexModuleName(
                                                    activeApexInfo.apexModuleName))
                                    .iterator();
                    while (it.hasNext()) {
                        arrayMap.put((String) it.next(), activeApexInfo.preInstalledApexPath);
                    }
                }
                OverlayConfig initializeSystemInstance =
                        OverlayConfig.initializeSystemInstance(
                                new OverlayConfig
                                        .PackageProvider() { // from class:
                                                             // com.android.server.pm.InitAppsHelper$$ExternalSyntheticLambda1
                                    public final void forEachPackage(
                                            final TriConsumer triConsumer) {
                                        InitAppsHelper initAppsHelper = InitAppsHelper.this;
                                        final ArrayMap arrayMap2 = arrayMap;
                                        PackageManagerService.forEachPackageState(
                                                initAppsHelper.mPm.snapshotComputer(),
                                                new Consumer() { // from class:
                                                                 // com.android.server.pm.InitAppsHelper$$ExternalSyntheticLambda2
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        TriConsumer triConsumer2 = triConsumer;
                                                        ArrayMap arrayMap3 = arrayMap2;
                                                        PackageStateInternal packageStateInternal =
                                                                (PackageStateInternal) obj;
                                                        AndroidPackageInternal pkg =
                                                                packageStateInternal.getPkg();
                                                        if (pkg != null) {
                                                            triConsumer2.accept(
                                                                    pkg,
                                                                    Boolean.valueOf(
                                                                            packageStateInternal
                                                                                    .isSystem()),
                                                                    (File)
                                                                            arrayMap3.get(
                                                                                    pkg
                                                                                            .getPackageName()));
                                                        }
                                                    }
                                                });
                                    }
                                });
                List list = this.mStubSystemApps;
                PackageManagerService packageManagerService = this.mPm;
                int size3 = packageManagerService.mPackages.mStorage.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    AndroidPackage androidPackage =
                            (AndroidPackage) packageManagerService.mPackages.mStorage.valueAt(i4);
                    if (androidPackage.isStub()) {
                        ((ArrayList) list).add(androidPackage.getPackageName());
                    }
                }
                final InstallPackageHelper installPackageHelper2 = this.mInstallPackageHelper;
                List list2 = this.mPossiblyDeletedUpdatedSystemApps;
                ArrayMap arrayMap2 = this.mExpectingBetter;
                PackageManagerService packageManagerService2 = installPackageHelper2.mPm;
                List parsePackages =
                        packageManagerService2.isDeviceUpgrading()
                                ? PmConfigParser.parsePackages(
                                        "/system/etc/system_to_data_app_list.xml")
                                : null;
                for (int size4 = watchedArrayMap.mStorage.size() - 1; size4 >= 0; size4--) {
                    final PackageSetting packageSetting =
                            (PackageSetting) watchedArrayMap.mStorage.valueAt(size4);
                    String str = packageSetting.mName;
                    if (packageSetting.isSystem()) {
                        AndroidPackage androidPackage2 =
                                (AndroidPackage) packageManagerService2.mPackages.mStorage.get(str);
                        PackageSetting disabledSystemPkgLPr =
                                packageManagerService2.mSettings.getDisabledSystemPkgLPr(str);
                        if (androidPackage2 == null) {
                            if (disabledSystemPkgLPr != null) {
                                File file2 = disabledSystemPkgLPr.mPath;
                                if (file2 == null
                                        || !file2.exists()
                                        || disabledSystemPkgLPr.pkg == null) {
                                    ((ArrayList) list2).add(str);
                                } else {
                                    arrayMap2.put(
                                            disabledSystemPkgLPr.mName, disabledSystemPkgLPr.mPath);
                                }
                            } else if (parsePackages == null
                                    || parsePackages.isEmpty()
                                    || !parsePackages.contains(packageSetting.mName)) {
                                PackageManagerServiceUtils.logCriticalInfo(
                                        5,
                                        "System package "
                                                + str
                                                + " no longer exists; its data will be wiped");
                                installPackageHelper2
                                        .mInjector
                                        .getHandler()
                                        .post(
                                                new Runnable() { // from class:
                                                                 // com.android.server.pm.InstallPackageHelper$$ExternalSyntheticLambda6
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        InstallPackageHelper installPackageHelper3 =
                                                                InstallPackageHelper.this;
                                                        installPackageHelper3.mRemovePackageHelper
                                                                .removePackageData(
                                                                        packageSetting, iArr);
                                                    }
                                                });
                            } else {
                                packageSetting.mPkgFlags = -2;
                                packageSetting.onChanged$2();
                                PackageManagerServiceUtils.logCriticalInfo(
                                        5,
                                        "Don't remove this system package "
                                                + packageSetting.mName
                                                + "; It will be re-installed in data partition.");
                            }
                        } else if (!androidPackage2.isApex() && disabledSystemPkgLPr != null) {
                            StringBuilder m =
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "Expecting better updated system app for ",
                                            str,
                                            "; removing system app.  Last known codePath=");
                            m.append(packageSetting.mPathString);
                            m.append(", versionCode=");
                            m.append(packageSetting.versionCode);
                            m.append("; scanned versionCode=");
                            m.append(androidPackage2.getLongVersionCode());
                            PackageManagerServiceUtils.logCriticalInfo(5, m.toString());
                            installPackageHelper2.mRemovePackageHelper.removePackage(
                                    androidPackage2);
                            arrayMap2.put(packageSetting.mName, packageSetting.mPath);
                        }
                    }
                }
                this.mCachedSystemApps = PackageCacher.sCachedPackageReadCount.get();
                PackageManagerService packageManagerService3 = this.mPm;
                final Settings settings = packageManagerService3.mSettings;
                settings.getClass();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                final WatchedArrayMap watchedArrayMap2 = settings.mSharedUsers;
                for (Map.Entry entry : watchedArrayMap2.entrySet()) {
                    SharedUserSetting sharedUserSetting = (SharedUserSetting) entry.getValue();
                    if (sharedUserSetting == null) {
                        arrayList.add((String) entry.getKey());
                    } else {
                        WatchedArraySet watchedArraySet = sharedUserSetting.mPackages;
                        boolean z = false;
                        for (int size5 = watchedArraySet.mStorage.size() - 1; size5 >= 0; size5--) {
                            if (settings.mPackages.mStorage.get(
                                            ((PackageSetting)
                                                            watchedArraySet.mStorage.valueAt(size5))
                                                    .mName)
                                    == null) {
                                Object removeAt = watchedArraySet.mStorage.removeAt(size5);
                                if (watchedArraySet.mWatching
                                        && (removeAt instanceof Watchable)
                                        && !watchedArraySet.mStorage.contains(removeAt)) {
                                    ((Watchable) removeAt)
                                            .unregisterObserver(watchedArraySet.mObserver);
                                }
                                watchedArraySet.dispatchChange(watchedArraySet);
                                z = true;
                            }
                        }
                        WatchedArraySet watchedArraySet2 = sharedUserSetting.mDisabledPackages;
                        for (int size6 = watchedArraySet2.mStorage.size() - 1;
                                size6 >= 0;
                                size6--) {
                            if (settings.mDisabledSysPackages.mStorage.get(
                                            ((PackageSetting)
                                                            watchedArraySet2.mStorage.valueAt(
                                                                    size6))
                                                    .mName)
                                    == null) {
                                Object removeAt2 = watchedArraySet2.mStorage.removeAt(size6);
                                if (watchedArraySet2.mWatching
                                        && (removeAt2 instanceof Watchable)
                                        && !watchedArraySet2.mStorage.contains(removeAt2)) {
                                    ((Watchable) removeAt2)
                                            .unregisterObserver(watchedArraySet2.mObserver);
                                }
                                watchedArraySet2.dispatchChange(watchedArraySet2);
                                z = true;
                            }
                        }
                        if (z) {
                            sharedUserSetting.onChanged$2();
                        }
                        if (watchedArraySet.mStorage.isEmpty()
                                && watchedArraySet2.mStorage.isEmpty()) {
                            arrayList2.add(sharedUserSetting);
                        }
                    }
                }
                final int i5 = 0;
                arrayList.forEach(
                        new Consumer() { // from class:
                                         // com.android.server.pm.Settings$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i6 = i5;
                                Object obj2 = watchedArrayMap2;
                                switch (i6) {
                                    case 0:
                                        ((WatchedArrayMap) obj2).remove((String) obj);
                                        break;
                                    default:
                                        ((Settings) obj2)
                                                .checkAndPruneSharedUserLPw(
                                                        (SharedUserSetting) obj, true);
                                        break;
                                }
                            }
                        });
                final int i6 = 1;
                arrayList2.forEach(
                        new Consumer() { // from class:
                                         // com.android.server.pm.Settings$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i62 = i6;
                                Object obj2 = settings;
                                switch (i62) {
                                    case 0:
                                        ((WatchedArrayMap) obj2).remove((String) obj);
                                        break;
                                    default:
                                        ((Settings) obj2)
                                                .checkAndPruneSharedUserLPw(
                                                        (SharedUserSetting) obj, true);
                                        break;
                                }
                            }
                        });
                this.mSystemScanTime = SystemClock.uptimeMillis() - j;
                this.mSystemPackagesCount = packageManagerService3.mPackages.mStorage.size();
                Slog.i(
                        "PackageManager",
                        "!@Boot_EBS_F: Finished scanning system apps ("
                                + this.mSystemScanTime
                                + " ms), packageCount: "
                                + this.mSystemPackagesCount
                                + ", cached: "
                                + this.mCachedSystemApps);
                StringBuilder sb = new StringBuilder("Finished scanning system apps. Time: ");
                sb.append(this.mSystemScanTime);
                sb.append(" ms, packageCount: ");
                sb.append(this.mSystemPackagesCount);
                sb.append(" , timePerPackage: ");
                int i7 = this.mSystemPackagesCount;
                sb.append(i7 == 0 ? 0L : this.mSystemScanTime / i7);
                sb.append(" , cached: ");
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        sb, this.mCachedSystemApps, "PackageManager");
                PmLog.logFinishedScanningInfo(
                        this.mSystemPackagesCount,
                        this.mCachedSystemApps,
                        this.mSystemScanTime,
                        "system");
                if (this.mIsDeviceUpgrading && (i2 = this.mSystemPackagesCount) > 0) {
                    FrameworkStatsLog.write(
                            FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED,
                            15,
                            this.mSystemScanTime / i2);
                }
                return initializeSystemInstance;
            } catch (RemoteException e) {
                Slog.e(
                        "ApexManager",
                        "Unable to retrieve packages from apexservice: " + e.toString());
                throw new RuntimeException(e);
            }
        } catch (Throwable th) {
            Trace.traceEnd(262144L);
            throw th;
        }
    }

    public final void scanDirTracedLI(
            File file,
            int i,
            int i2,
            PackageParser2 packageParser2,
            ExecutorService executorService,
            ApexManager.ActiveApexInfo activeApexInfo) {
        Trace.traceBegin(262144L, "scanDir [" + file.getAbsolutePath() + "]");
        try {
            this.mInstallPackageHelper.installPackagesFromDir(
                    file,
                    (8388608 & i2) != 0 ? i | 512 : i,
                    i2,
                    packageParser2,
                    executorService,
                    activeApexInfo);
        } finally {
            Trace.traceEnd(262144L);
        }
    }
}
