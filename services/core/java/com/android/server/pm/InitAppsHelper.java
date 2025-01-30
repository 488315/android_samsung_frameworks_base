package com.android.server.pm;

import android.os.Environment;
import android.os.SystemClock;
import android.os.Trace;
import android.p005os.IInstalld;
import android.system.ErrnoException;
import android.system.Os;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Slog;
import com.android.internal.content.om.OverlayConfig;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.function.TriConsumer;
import com.android.server.pm.parsing.PackageCacher;
import com.android.server.pm.parsing.PackageParser2;
import com.android.server.pm.parsing.pkg.AndroidPackageInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.utils.WatchedArrayMap;
import com.samsung.android.server.p025pm.PmLog;
import java.io.File;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final class InitAppsHelper {
  public final ApexManager mApexManager;
  public int mCachedSystemApps;
  public final ExecutorService mExecutorService;
  public final InstallPackageHelper mInstallPackageHelper;
  public final boolean mIsDeviceUpgrading;
  public final PackageManagerService mPm;
  public final int mScanFlags;
  public int mSystemPackagesCount;
  public final int mSystemParseFlags;
  public final List mSystemPartitions;
  public final int mSystemScanFlags;
  public long mSystemScanTime;
  public final ArrayMap mExpectingBetter = new ArrayMap();
  public final List mPossiblyDeletedUpdatedSystemApps = new ArrayList();
  public final List mStubSystemApps = new ArrayList();
  public final List mDirsToScanAsSystem = getSystemScanPartitions();

  public InitAppsHelper(
      PackageManagerService packageManagerService,
      ApexManager apexManager,
      InstallPackageHelper installPackageHelper,
      List list) {
    this.mPm = packageManagerService;
    this.mApexManager = apexManager;
    this.mInstallPackageHelper = installPackageHelper;
    this.mSystemPartitions = list;
    boolean isDeviceUpgrading = packageManagerService.isDeviceUpgrading();
    this.mIsDeviceUpgrading = isDeviceUpgrading;
    if (isDeviceUpgrading || packageManagerService.isFirstBoot()) {
      this.mScanFlags = 4624;
    } else {
      this.mScanFlags = FrameworkStatsLog.EXPRESS_EVENT_REPORTED;
    }
    this.mSystemParseFlags = packageManagerService.getDefParseFlags() | 16;
    this.mSystemScanFlags = this.mScanFlags | 65536;
    this.mExecutorService = ParallelPackageParser.makeExecutorService();
  }

  public final List getSystemScanPartitions() {
    ArrayList arrayList = new ArrayList();
    arrayList.addAll(this.mSystemPartitions);
    arrayList.addAll(getApexScanPartitions());
    Slog.d("PackageManager", "Directories scanned as system partitions: " + arrayList);
    return arrayList;
  }

  public final List getApexScanPartitions() {
    ArrayList arrayList = new ArrayList();
    List activeApexInfos = this.mApexManager.getActiveApexInfos();
    for (int i = 0; i < activeApexInfos.size(); i++) {
      ScanPartition resolveApexToScanPartition =
          resolveApexToScanPartition((ApexManager.ActiveApexInfo) activeApexInfos.get(i));
      if (resolveApexToScanPartition != null) {
        arrayList.add(resolveApexToScanPartition);
      }
    }
    return arrayList;
  }

  public static ScanPartition resolveApexToScanPartition(
      ApexManager.ActiveApexInfo activeApexInfo) {
    int size = PackageManagerService.SYSTEM_PARTITIONS.size();
    for (int i = 0; i < size; i++) {
      ScanPartition scanPartition = (ScanPartition) PackageManagerService.SYSTEM_PARTITIONS.get(i);
      if (!activeApexInfo
          .preInstalledApexPath
          .getAbsolutePath()
          .equals(scanPartition.getFolder().getAbsolutePath())) {
        if (!activeApexInfo
            .preInstalledApexPath
            .getAbsolutePath()
            .startsWith(scanPartition.getFolder().getAbsolutePath() + File.separator)) {}
      }
      return new ScanPartition(activeApexInfo.apexDirectory, scanPartition, activeApexInfo);
    }
    return null;
  }

  public final List scanApexPackagesTraced(PackageParser2 packageParser2) {
    Trace.traceBegin(262144L, "scanApexPackages");
    try {
      return this.mInstallPackageHelper.scanApexPackages(
          this.mApexManager.getAllApexInfos(),
          this.mSystemParseFlags,
          this.mSystemScanFlags,
          packageParser2,
          this.mExecutorService);
    } finally {
      Trace.traceEnd(262144L);
    }
  }

  public OverlayConfig initSystemApps(
      PackageParser2 packageParser2, WatchedArrayMap watchedArrayMap, int[] iArr, long j) {
    this.mApexManager.notifyScanResult(scanApexPackagesTraced(packageParser2));
    scanSystemDirs(packageParser2, this.mExecutorService);
    final ArrayMap arrayMap = new ArrayMap();
    for (ApexManager.ActiveApexInfo activeApexInfo : this.mApexManager.getActiveApexInfos()) {
      Iterator it = this.mApexManager.getApksInApex(activeApexInfo.apexModuleName).iterator();
      while (it.hasNext()) {
        arrayMap.put((String) it.next(), activeApexInfo.preInstalledApexPath);
      }
    }
    OverlayConfig initializeSystemInstance =
        OverlayConfig.initializeSystemInstance(
            new OverlayConfig.PackageProvider() { // from class:
              // com.android.server.pm.InitAppsHelper$$ExternalSyntheticLambda1
              public final void forEachPackage(TriConsumer triConsumer) {
                InitAppsHelper.this.lambda$initSystemApps$1(arrayMap, triConsumer);
              }
            });
    updateStubSystemAppsList(this.mStubSystemApps);
    this.mInstallPackageHelper.prepareSystemPackageCleanUp(
        watchedArrayMap, this.mPossiblyDeletedUpdatedSystemApps, this.mExpectingBetter, iArr);
    logSystemAppsScanningTime(j);
    return initializeSystemInstance;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$initSystemApps$1(
      final ArrayMap arrayMap, final TriConsumer triConsumer) {
    PackageManagerService packageManagerService = this.mPm;
    packageManagerService.forEachPackageState(
        packageManagerService.snapshotComputer(),
        new Consumer() { // from class:
          // com.android.server.pm.InitAppsHelper$$ExternalSyntheticLambda2
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            InitAppsHelper.lambda$initSystemApps$0(
                triConsumer, arrayMap, (PackageStateInternal) obj);
          }
        });
  }

  public static /* synthetic */ void lambda$initSystemApps$0(
      TriConsumer triConsumer, ArrayMap arrayMap, PackageStateInternal packageStateInternal) {
    AndroidPackageInternal pkg = packageStateInternal.getPkg();
    if (pkg != null) {
      triConsumer.accept(
          pkg,
          Boolean.valueOf(packageStateInternal.isSystem()),
          (File) arrayMap.get(pkg.getPackageName()));
    }
  }

  public final void logSystemAppsScanningTime(long j) {
    int i;
    this.mCachedSystemApps = PackageCacher.sCachedPackageReadCount.get();
    this.mPm.mSettings.pruneSharedUsersLPw();
    this.mSystemScanTime = SystemClock.uptimeMillis() - j;
    this.mSystemPackagesCount = this.mPm.mPackages.size();
    Slog.i(
        "PackageManager",
        "!@Boot_EBS_F: Finished scanning system apps ("
            + this.mSystemScanTime
            + " ms), packageCount: "
            + this.mSystemPackagesCount
            + ", cached: "
            + this.mCachedSystemApps);
    StringBuilder sb = new StringBuilder();
    sb.append("Finished scanning system apps. Time: ");
    sb.append(this.mSystemScanTime);
    sb.append(" ms, packageCount: ");
    sb.append(this.mSystemPackagesCount);
    sb.append(" , timePerPackage: ");
    int i2 = this.mSystemPackagesCount;
    sb.append(i2 == 0 ? 0L : this.mSystemScanTime / i2);
    sb.append(" , cached: ");
    sb.append(this.mCachedSystemApps);
    Slog.i("PackageManager", sb.toString());
    PmLog.logFinishedScanningInfo(
        "system", this.mSystemScanTime, this.mSystemPackagesCount, this.mCachedSystemApps, 3090);
    if (!this.mIsDeviceUpgrading || (i = this.mSystemPackagesCount) <= 0) {
      return;
    }
    FrameworkStatsLog.write(
        FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED, 15, this.mSystemScanTime / i);
  }

  public void fixInstalledAppDirMode() {
    try {
      DirectoryStream<Path> newDirectoryStream =
          Files.newDirectoryStream(this.mPm.getAppInstallDir().toPath());
      try {
        newDirectoryStream.forEach(
            new Consumer() { // from class:
              // com.android.server.pm.InitAppsHelper$$ExternalSyntheticLambda0
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                InitAppsHelper.lambda$fixInstalledAppDirMode$2((Path) obj);
              }
            });
        newDirectoryStream.close();
      } finally {
      }
    } catch (Exception e) {
      Slog.w("PackageManager", "Failed to walk the app install directory to fix the modes", e);
    }
  }

  public static /* synthetic */ void lambda$fixInstalledAppDirMode$2(Path path) {
    try {
      Os.chmod(path.toString(), 505);
    } catch (ErrnoException e) {
      Slog.w("PackageManager", "Failed to fix an installed app dir mode", e);
    }
  }

  public void initNonSystemApps(PackageParser2 packageParser2, int[] iArr, long j) {
    Slog.i("PackageManager", "!@Boot_EBS_F: boot_progress_pms_data_scan_start");
    EventLog.writeEvent(3080, SystemClock.uptimeMillis());
    if ((this.mScanFlags & IInstalld.FLAG_USE_QUOTA) == 4096) {
      fixInstalledAppDirMode();
    }
    scanDirTracedLI(
        this.mPm.getAppInstallDir(),
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
    fixSystemPackages(iArr, packageParser2);
    logNonSystemAppScanningTime(j);
    this.mExpectingBetter.clear();
    this.mPm.mSettings.pruneRenamedPackagesLPw();
  }

  public final void fixSystemPackages(int[] iArr, PackageParser2 packageParser2) {
    this.mInstallPackageHelper.cleanupDisabledPackageSettings(
        this.mPossiblyDeletedUpdatedSystemApps, iArr, this.mScanFlags);
    if (this.mPm.isFirstBoot()) {
      this.mInstallPackageHelper.updateDuplicatePreloadApps(
          0,
          this.mScanFlags | 128 | IInstalld.FLAG_USE_QUOTA,
          0L,
          packageParser2,
          this.mExpectingBetter);
    }
    this.mInstallPackageHelper.checkExistingBetterPackages(
        this.mExpectingBetter, this.mStubSystemApps, this.mSystemScanFlags, this.mSystemParseFlags);
    this.mInstallPackageHelper.installSystemStubPackages(this.mStubSystemApps, this.mScanFlags);
  }

  public final void logNonSystemAppScanningTime(long j) {
    int i = PackageCacher.sCachedPackageReadCount.get() - this.mCachedSystemApps;
    long uptimeMillis = (SystemClock.uptimeMillis() - this.mSystemScanTime) - j;
    int size = this.mPm.mPackages.size() - this.mSystemPackagesCount;
    Slog.i(
        "PackageManager",
        "!@Boot_EBS_F: Finished scanning non-system apps ("
            + uptimeMillis
            + " ms), packageCount: "
            + size
            + " , cached: "
            + i);
    StringBuilder sb = new StringBuilder();
    sb.append("Finished scanning non-system apps. Time: ");
    sb.append(uptimeMillis);
    sb.append(" ms, packageCount: ");
    sb.append(size);
    sb.append(" , timePerPackage: ");
    sb.append(size == 0 ? 0L : uptimeMillis / size);
    sb.append(" , cached: ");
    sb.append(i);
    Slog.i("PackageManager", sb.toString());
    PmLog.logFinishedScanningInfo("non-system", uptimeMillis, size, i, 3090);
    if (!this.mIsDeviceUpgrading || size <= 0) {
      return;
    }
    FrameworkStatsLog.write(
        FrameworkStatsLog.BOOT_TIME_EVENT_DURATION_REPORTED, 14, uptimeMillis / size);
  }

  public final void scanSystemDirs(PackageParser2 packageParser2, ExecutorService executorService) {
    File file = new File(Environment.getRootDirectory(), "framework");
    for (int size = this.mDirsToScanAsSystem.size() - 1; size >= 0; size--) {
      ScanPartition scanPartition = (ScanPartition) this.mDirsToScanAsSystem.get(size);
      if (scanPartition.getOverlayFolder() != null) {
        scanDirTracedLI(
            scanPartition.getOverlayFolder(),
            this.mSystemParseFlags,
            this.mSystemScanFlags | scanPartition.scanFlag,
            packageParser2,
            executorService,
            scanPartition.apexInfo);
      }
    }
    scanDirTracedLI(
        file,
        this.mSystemParseFlags,
        this.mSystemScanFlags | 1 | IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES,
        packageParser2,
        executorService,
        null);
    if (!this.mPm.mPackages.containsKey("android")) {
      throw new IllegalStateException("Failed to load frameworks package; check log for warnings");
    }
    int size2 = this.mDirsToScanAsSystem.size();
    for (int i = 0; i < size2; i++) {
      ScanPartition scanPartition2 = (ScanPartition) this.mDirsToScanAsSystem.get(i);
      if (scanPartition2.getPrivAppFolder() != null) {
        scanDirTracedLI(
            scanPartition2.getPrivAppFolder(),
            this.mSystemParseFlags,
            scanPartition2.scanFlag
                | this.mSystemScanFlags
                | IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES,
            packageParser2,
            executorService,
            scanPartition2.apexInfo);
      }
      scanDirTracedLI(
          scanPartition2.getAppFolder(),
          this.mSystemParseFlags,
          scanPartition2.scanFlag | this.mSystemScanFlags,
          packageParser2,
          executorService,
          scanPartition2.apexInfo);
    }
  }

  public final void updateStubSystemAppsList(List list) {
    int size = this.mPm.mPackages.size();
    for (int i = 0; i < size; i++) {
      AndroidPackage androidPackage = (AndroidPackage) this.mPm.mPackages.valueAt(i);
      if (androidPackage.isStub()) {
        list.add(androidPackage.getPackageName());
      }
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

  public boolean isExpectingBetter(String str) {
    return this.mExpectingBetter.containsKey(str);
  }

  public List getDirsToScanAsSystem() {
    return this.mDirsToScanAsSystem;
  }

  public int getSystemScanFlags() {
    return this.mSystemScanFlags;
  }
}
