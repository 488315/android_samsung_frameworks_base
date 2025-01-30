package com.android.server.pm;

import android.content.Context;
import android.content.pm.PackageStats;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.p005os.CreateAppDataArgs;
import android.p005os.CreateAppDataResult;
import android.p005os.IInstalld;
import android.p005os.ReconcileSdkDataArgs;
import android.p005os.storage.CrateMetadata;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.server.SystemService;
import dalvik.system.BlockGuard;
import dalvik.system.VMRuntime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class Installer extends SystemService {
  public static final boolean LOW_DEBUG =
      SystemProperties.get("ro.boot.debug_level", "0x4f4c").equals("0x4f4c");
  public volatile boolean mDeferSetFirstBoot;
  public volatile IInstalld mInstalld;
  public volatile CountDownLatch mInstalldLatch;
  public final boolean mIsolated;
  public volatile Object mWarnIfHeld;

  public Installer(Context context) {
    this(context, false);
  }

  public Installer(Context context, boolean z) {
    super(context);
    this.mInstalld = null;
    this.mInstalldLatch = new CountDownLatch(1);
    this.mIsolated = z;
  }

  public void setWarnIfHeld(Object obj) {
    this.mWarnIfHeld = obj;
  }

  public boolean isIsolated() {
    return this.mIsolated;
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    if (this.mIsolated) {
      this.mInstalld = null;
      this.mInstalldLatch.countDown();
    } else {
      connect();
    }
  }

  public final void connect() {
    IBinder service = ServiceManager.getService("installd");
    if (service != null) {
      try {
        service.linkToDeath(
            new IBinder.DeathRecipient() { // from class:
              // com.android.server.pm.Installer$$ExternalSyntheticLambda0
              @Override // android.os.IBinder.DeathRecipient
              public final void binderDied() {
                Installer.this.lambda$connect$0();
              }
            },
            0);
      } catch (RemoteException unused) {
        service = null;
      }
    }
    if (service != null) {
      this.mInstalld = IInstalld.Stub.asInterface(service);
      this.mInstalldLatch.countDown();
      try {
        invalidateMounts();
        executeDeferredActions();
        return;
      } catch (InstallerException unused2) {
        return;
      }
    }
    Slog.w("Installer", "installd not found; trying again");
    BackgroundThread.getHandler()
        .postDelayed(
            new Runnable() { // from class:
              // com.android.server.pm.Installer$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                Installer.this.connect();
              }
            },
            1000L);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$connect$0() {
    Slog.w("Installer", "installd died; reconnecting");
    this.mInstalldLatch = new CountDownLatch(1);
    connect();
  }

  public final void executeDeferredActions() {
    if (this.mDeferSetFirstBoot) {
      setFirstBoot();
    }
  }

  public final boolean checkBeforeRemote() {
    if (this.mWarnIfHeld != null && Thread.holdsLock(this.mWarnIfHeld)) {
      Slog.wtf(
          "Installer",
          "Calling thread "
              + Thread.currentThread().getName()
              + " is holding 0x"
              + Integer.toHexString(System.identityHashCode(this.mWarnIfHeld)),
          new Throwable());
    }
    if (this.mIsolated) {
      Slog.i("Installer", "Ignoring request because this installer is isolated");
      return false;
    }
    try {
      if (this.mInstalldLatch.await(10000L, TimeUnit.MILLISECONDS)) {
        return true;
      }
      throw new InstallerException("time out waiting for the installer to be ready");
    } catch (InterruptedException unused) {
      return true;
    }
  }

  public static CreateAppDataArgs buildCreateAppDataArgs(
      String str, String str2, int i, int i2, int i3, String str3, int i4, boolean z) {
    CreateAppDataArgs createAppDataArgs = new CreateAppDataArgs();
    createAppDataArgs.uuid = str;
    createAppDataArgs.packageName = str2;
    createAppDataArgs.userId = i;
    createAppDataArgs.flags = i2;
    if (z) {
      createAppDataArgs.flags = i2 | 8;
    }
    createAppDataArgs.appId = i3;
    createAppDataArgs.seInfo = str3;
    createAppDataArgs.targetSdkVersion = i4;
    return createAppDataArgs;
  }

  public static CreateAppDataResult buildPlaceholderCreateAppDataResult() {
    CreateAppDataResult createAppDataResult = new CreateAppDataResult();
    createAppDataResult.ceDataInode = -1L;
    createAppDataResult.exceptionCode = 0;
    createAppDataResult.exceptionMessage = null;
    return createAppDataResult;
  }

  public static ReconcileSdkDataArgs buildReconcileSdkDataArgs(
      String str, String str2, List list, int i, int i2, String str3, int i3) {
    ReconcileSdkDataArgs reconcileSdkDataArgs = new ReconcileSdkDataArgs();
    reconcileSdkDataArgs.uuid = str;
    reconcileSdkDataArgs.packageName = str2;
    reconcileSdkDataArgs.subDirNames = list;
    reconcileSdkDataArgs.userId = i;
    reconcileSdkDataArgs.appId = i2;
    reconcileSdkDataArgs.previousAppId = 0;
    reconcileSdkDataArgs.seInfo = str3;
    reconcileSdkDataArgs.flags = i3;
    return reconcileSdkDataArgs;
  }

  public CreateAppDataResult createAppData(CreateAppDataArgs createAppDataArgs) {
    if (!checkBeforeRemote()) {
      return buildPlaceholderCreateAppDataResult();
    }
    createAppDataArgs.previousAppId = 0;
    try {
      return this.mInstalld.createAppData(createAppDataArgs);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public CreateAppDataResult[] createAppDataBatched(CreateAppDataArgs[] createAppDataArgsArr) {
    if (!checkBeforeRemote()) {
      CreateAppDataResult[] createAppDataResultArr =
          new CreateAppDataResult[createAppDataArgsArr.length];
      Arrays.fill(createAppDataResultArr, buildPlaceholderCreateAppDataResult());
      return createAppDataResultArr;
    }
    for (CreateAppDataArgs createAppDataArgs : createAppDataArgsArr) {
      createAppDataArgs.previousAppId = 0;
    }
    try {
      return this.mInstalld.createAppDataBatched(createAppDataArgsArr);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public void reconcileSdkData(ReconcileSdkDataArgs reconcileSdkDataArgs) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.reconcileSdkData(reconcileSdkDataArgs);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void setFirstBoot() {
    if (checkBeforeRemote()) {
      try {
        if (this.mInstalld != null) {
          this.mInstalld.setFirstBoot();
        } else {
          this.mDeferSetFirstBoot = true;
        }
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public class Batch {
    public boolean mExecuted;
    public final List mArgs = new ArrayList();
    public final List mFutures = new ArrayList();

    public synchronized CompletableFuture createAppData(CreateAppDataArgs createAppDataArgs) {
      CompletableFuture completableFuture;
      if (this.mExecuted) {
        throw new IllegalStateException();
      }
      completableFuture = new CompletableFuture();
      this.mArgs.add(createAppDataArgs);
      this.mFutures.add(completableFuture);
      return completableFuture;
    }

    public synchronized void execute(Installer installer) {
      if (this.mExecuted) {
        throw new IllegalStateException();
      }
      this.mExecuted = true;
      int size = this.mArgs.size();
      for (int i = 0; i < size; i += 256) {
        int min = Math.min(size - i, 256);
        CreateAppDataArgs[] createAppDataArgsArr = new CreateAppDataArgs[min];
        for (int i2 = 0; i2 < min; i2++) {
          createAppDataArgsArr[i2] = (CreateAppDataArgs) this.mArgs.get(i + i2);
        }
        CreateAppDataResult[] createAppDataBatched =
            installer.createAppDataBatched(createAppDataArgsArr);
        for (int i3 = 0; i3 < min; i3++) {
          CreateAppDataResult createAppDataResult = createAppDataBatched[i3];
          CompletableFuture completableFuture = (CompletableFuture) this.mFutures.get(i + i3);
          if (createAppDataResult.exceptionCode == 0) {
            completableFuture.complete(Long.valueOf(createAppDataResult.ceDataInode));
          } else {
            completableFuture.completeExceptionally(
                new InstallerException(createAppDataResult.exceptionMessage));
          }
        }
      }
    }
  }

  public void restoreconAppData(String str, String str2, int i, int i2, int i3, String str3) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.restoreconAppData(str, str2, i, i2, i3, str3);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void migrateAppData(String str, String str2, int i, int i2) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.migrateAppData(str, str2, i, i2);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void clearAppData(String str, String str2, int i, int i2, long j) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.clearAppData(str, str2, i, i2, j);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void destroyAppData(String str, String str2, int i, int i2, long j) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.destroyAppData(str, str2, i, i2, j);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void fixupAppData(String str, int i) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.fixupAppData(str, i);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void cleanupInvalidPackageDirs(String str, int i, int i2) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.cleanupInvalidPackageDirs(str, i, i2);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void moveCompleteApp(
      String str, String str2, String str3, int i, String str4, int i2, String str5) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.moveCompleteApp(str, str2, str3, i, str4, i2, str5);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void getAppSize(
      String str,
      String[] strArr,
      int i,
      int i2,
      int i3,
      long[] jArr,
      String[] strArr2,
      PackageStats packageStats) {
    long[] jArr2;
    if (checkBeforeRemote()) {
      if (strArr2 != null) {
        for (String str2 : strArr2) {
          BlockGuard.getVmPolicy().onPathAccess(str2);
        }
      }
      try {
        long[] appSize = this.mInstalld.getAppSize(str, strArr, i, i2, i3, jArr, strArr2);
        packageStats.codeSize += appSize[0];
        packageStats.dataSize += appSize[1];
        packageStats.cacheSize += appSize[2];
        packageStats.externalCodeSize += appSize[3];
        packageStats.externalDataSize += appSize[4];
        packageStats.externalCacheSize += appSize[5];
        if (str == null) {
          Iterator it =
              ((StorageManager) getContext().getSystemService(StorageManager.class))
                  .getVolumes()
                  .iterator();
          while (true) {
            if (!it.hasNext()) {
              jArr2 = null;
              break;
            }
            VolumeInfo volumeInfo = (VolumeInfo) it.next();
            if (volumeInfo.getDisk() != null
                && volumeInfo.getDisk().isSd()
                && volumeInfo.isMountedWritable()) {
              jArr2 =
                  this.mInstalld.getAppSize(
                      volumeInfo.getFsUuid(), strArr, i, i2, i3, jArr, strArr2);
              break;
            }
          }
          if (jArr2 != null) {
            packageStats.codeSize += jArr2[0];
            packageStats.dataSize += jArr2[1];
            packageStats.cacheSize += jArr2[2];
            packageStats.externalCodeSize += jArr2[3];
            packageStats.externalDataSize += jArr2[4];
            packageStats.externalCacheSize += jArr2[5];
          }
        }
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void getUserSize(String str, int i, int i2, int[] iArr, PackageStats packageStats) {
    if (checkBeforeRemote()) {
      try {
        long[] userSize = this.mInstalld.getUserSize(str, i, i2, iArr);
        packageStats.codeSize += userSize[0];
        packageStats.dataSize += userSize[1];
        packageStats.cacheSize += userSize[2];
        packageStats.externalCodeSize += userSize[3];
        packageStats.externalDataSize += userSize[4];
        packageStats.externalCacheSize += userSize[5];
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public long[] getExternalSize(String str, int i, int i2, int[] iArr) {
    if (!checkBeforeRemote()) {
      return new long[6];
    }
    try {
      return this.mInstalld.getExternalSize(str, i, i2, iArr);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public CrateMetadata[] getAppCrates(String str, String[] strArr, int i) {
    if (!checkBeforeRemote()) {
      return null;
    }
    try {
      return this.mInstalld.getAppCrates(str, strArr, i);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public CrateMetadata[] getUserCrates(String str, int i) {
    if (!checkBeforeRemote()) {
      return null;
    }
    try {
      return this.mInstalld.getUserCrates(str, i);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public void setAppQuota(String str, int i, int i2, long j) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.setAppQuota(str, i, i2, j);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public boolean dexopt(
      String str,
      int i,
      String str2,
      String str3,
      int i2,
      String str4,
      int i3,
      String str5,
      String str6,
      String str7,
      String str8,
      boolean z,
      int i4,
      String str9,
      String str10,
      String str11) {
    checkLegacyDexoptDisabled();
    assertValidInstructionSet(str3);
    BlockGuard.getVmPolicy().onPathAccess(str);
    BlockGuard.getVmPolicy().onPathAccess(str4);
    BlockGuard.getVmPolicy().onPathAccess(str10);
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.dexopt(
          str, i, str2, str3, i2, str4, i3, str5, str6, str7, str8, z, i4, str9, str10, str11);
    } catch (Exception e) {
      if (!LOW_DEBUG) {
        DexOptHelper.saveDexOptLog();
      }
      throw InstallerException.from(e);
    }
  }

  public void controlDexOptBlocking(boolean z) {
    checkLegacyDexoptDisabled();
    try {
      this.mInstalld.controlDexOptBlocking(z);
    } catch (Exception e) {
      Slog.w("Installer", "blockDexOpt failed", e);
    }
  }

  public int mergeProfiles(int i, String str, String str2) {
    checkLegacyDexoptDisabled();
    if (!checkBeforeRemote()) {
      return 2;
    }
    try {
      return this.mInstalld.mergeProfiles(i, str, str2);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean dumpProfiles(int i, String str, String str2, String str3, boolean z) {
    checkLegacyDexoptDisabled();
    if (!checkBeforeRemote()) {
      return false;
    }
    BlockGuard.getVmPolicy().onPathAccess(str3);
    try {
      return this.mInstalld.dumpProfiles(i, str, str2, str3, z);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean copySystemProfile(String str, int i, String str2, String str3) {
    checkLegacyDexoptDisabled();
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.copySystemProfile(str, i, str2, str3);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public void rmdex(String str, String str2) {
    checkLegacyDexoptDisabled();
    assertValidInstructionSet(str2);
    if (checkBeforeRemote()) {
      BlockGuard.getVmPolicy().onPathAccess(str);
      try {
        this.mInstalld.rmdex(str, str2);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void rmPackageDir(String str, String str2) {
    if (checkBeforeRemote()) {
      BlockGuard.getVmPolicy().onPathAccess(str2);
      try {
        this.mInstalld.rmPackageDir(str, str2);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void clearAppProfiles(String str, String str2) {
    checkLegacyDexoptDisabled();
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.clearAppProfiles(str, str2);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void destroyAppProfiles(String str) {
    checkLegacyDexoptDisabled();
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.destroyAppProfiles(str);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void deleteReferenceProfile(String str, String str2) {
    checkLegacyDexoptDisabled();
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.deleteReferenceProfile(str, str2);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void createUserData(String str, int i, int i2, int i3) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.createUserData(str, i, i2, i3);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void destroyUserData(String str, int i, int i2) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.destroyUserData(str, i, i2);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void freeCache(String str, long j, int i) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.freeCache(str, j, i);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void linkNativeLibraryDirectory(String str, String str2, String str3, int i) {
    if (checkBeforeRemote()) {
      BlockGuard.getVmPolicy().onPathAccess(str3);
      try {
        this.mInstalld.linkNativeLibraryDirectory(str, str2, str3, i);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void createOatDir(String str, String str2, String str3) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.createOatDir(str, str2, str3);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void linkFile(String str, String str2, String str3, String str4) {
    if (checkBeforeRemote()) {
      BlockGuard.getVmPolicy().onPathAccess(str3);
      BlockGuard.getVmPolicy().onPathAccess(str4);
      try {
        this.mInstalld.linkFile(str, str2, str3, str4);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void moveAb(String str, String str2, String str3, String str4) {
    if (checkBeforeRemote()) {
      BlockGuard.getVmPolicy().onPathAccess(str2);
      BlockGuard.getVmPolicy().onPathAccess(str4);
      try {
        this.mInstalld.moveAb(str, str2, str3, str4);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public long deleteOdex(String str, String str2, String str3, String str4) {
    checkLegacyDexoptDisabled();
    if (!checkBeforeRemote()) {
      return -1L;
    }
    BlockGuard.getVmPolicy().onPathAccess(str2);
    BlockGuard.getVmPolicy().onPathAccess(str4);
    try {
      return this.mInstalld.deleteOdex(str, str2, str3, str4);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean reconcileSecondaryDexFile(
      String str, String str2, int i, String[] strArr, String str3, int i2) {
    checkLegacyDexoptDisabled();
    for (String str4 : strArr) {
      assertValidInstructionSet(str4);
    }
    if (!checkBeforeRemote()) {
      return false;
    }
    BlockGuard.getVmPolicy().onPathAccess(str);
    try {
      return this.mInstalld.reconcileSecondaryDexFile(str, str2, i, strArr, str3, i2);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public byte[] hashSecondaryDexFile(String str, String str2, int i, String str3, int i2) {
    if (!checkBeforeRemote()) {
      return new byte[0];
    }
    BlockGuard.getVmPolicy().onPathAccess(str);
    try {
      return this.mInstalld.hashSecondaryDexFile(str, str2, i, str3, i2);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean createProfileSnapshot(int i, String str, String str2, String str3) {
    checkLegacyDexoptDisabled();
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.createProfileSnapshot(i, str, str2, str3);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public void destroyProfileSnapshot(String str, String str2) {
    checkLegacyDexoptDisabled();
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.destroyProfileSnapshot(str, str2);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void invalidateMounts() {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.invalidateMounts();
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public boolean isQuotaSupported(String str) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.isQuotaSupported(str);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public void tryMountDataMirror(String str) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.tryMountDataMirror(str);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public void onPrivateVolumeRemoved(String str) {
    if (checkBeforeRemote()) {
      try {
        this.mInstalld.onPrivateVolumeRemoved(str);
      } catch (Exception e) {
        throw InstallerException.from(e);
      }
    }
  }

  public boolean prepareAppProfile(
      String str, int i, int i2, String str2, String str3, String str4) {
    checkLegacyDexoptDisabled();
    if (!checkBeforeRemote()) {
      return false;
    }
    BlockGuard.getVmPolicy().onPathAccess(str3);
    BlockGuard.getVmPolicy().onPathAccess(str4);
    try {
      return this.mInstalld.prepareAppProfile(str, i, i2, str2, str3, str4);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean snapshotAppData(String str, int i, int i2, int i3) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      this.mInstalld.snapshotAppData(null, str, i, i2, i3);
      return true;
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean restoreAppDataSnapshot(String str, int i, String str2, int i2, int i3, int i4) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      this.mInstalld.restoreAppDataSnapshot(null, str, i, str2, i2, i3, i4);
      return true;
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean destroyAppDataSnapshot(String str, int i, int i2, int i3) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      this.mInstalld.destroyAppDataSnapshot(null, str, i, 0L, i2, i3);
      return true;
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean destroyCeSnapshotsNotSpecified(int i, int[] iArr) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      this.mInstalld.destroyCeSnapshotsNotSpecified(null, i, iArr);
      return true;
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean migrateLegacyObbData() {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      this.mInstalld.migrateLegacyObbData();
      return true;
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public static void assertValidInstructionSet(String str) {
    for (String str2 : Build.SUPPORTED_ABIS) {
      if (VMRuntime.getInstructionSet(str2).equals(str)) {
        return;
      }
    }
    throw new InstallerException("Invalid instruction set: " + str);
  }

  public boolean compileLayouts(String str, String str2, String str3, int i) {
    try {
      return this.mInstalld.compileLayouts(str, str2, str3, i);
    } catch (RemoteException unused) {
      return false;
    }
  }

  public boolean isInstalldConnected() {
    return this.mInstalld != null;
  }

  public boolean removeNotTargetedPreloadApksIfNeeded() {
    try {
      return this.mInstalld.removeNotTargetedPreloadApksIfNeeded();
    } catch (Exception unused) {
      return false;
    }
  }

  public int getOdexVisibility(String str, String str2, String str3, String str4) {
    checkLegacyDexoptDisabled();
    if (!checkBeforeRemote()) {
      return -1;
    }
    BlockGuard.getVmPolicy().onPathAccess(str2);
    BlockGuard.getVmPolicy().onPathAccess(str4);
    try {
      return this.mInstalld.getOdexVisibility(str, str2, str3, str4);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public class InstallerException extends Exception {
    public InstallerException(String str) {
      super(str);
    }

    public static InstallerException from(Exception exc) {
      throw new InstallerException(exc.toString());
    }
  }

  public class LegacyDexoptDisabledException extends Exception {
    public LegacyDexoptDisabledException() {
      super("Invalid call to legacy dexopt method while ART Service is in use.");
    }
  }

  public static void checkLegacyDexoptDisabled() {
    if (DexOptHelper.useArtService()) {
      throw new LegacyDexoptDisabledException();
    }
  }

  public boolean copyKnoxAppData(String str, int i, String str2, int i2, int i3) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.copyKnoxAppData(str, i, str2, i2, i3);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public int copyKnoxChunks(
      String str, int i, String str2, int i2, int i3, long j, long j2, long j3) {
    if (!checkBeforeRemote()) {
      return -1;
    }
    try {
      return this.mInstalld.copyKnoxChunks(str, i, str2, i2, i3, j, j2, j3);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean copyKnoxCancel(String str, long j) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.copyKnoxCancel(str, j);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public long[] getKnoxFileInfo(String str) {
    if (!checkBeforeRemote()) {
      return new long[] {-1};
    }
    try {
      return this.mInstalld.getKnoxFileInfo(str);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean getKnoxScanDir(String str, long j, List list) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.getKnoxScanDir(str, j, list);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean deleteKnoxFile(String str) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.deleteKnoxFile(str);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean createEncAppData(String str, int i, int i2, int i3) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.createEncAppData(str, i, i2, i3);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean removeEncPkgDir(int i, String str) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.removeEncPkgDir(i, str);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean removeEncUserDir(int i) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.removeEncUserDir(i);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean migrateSdpDb(String str, int i) {
    if (!checkBeforeRemote()) {
      return false;
    }
    try {
      return this.mInstalld.migrateSdpDb(str, i);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean setEviction(int i, boolean z) {
    try {
      return this.mInstalld.setEviction(i, z);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean setDualDARPolicyDir(int i, int i2, String str) {
    try {
      return this.mInstalld.setDualDARPolicyDir(i, i2, str);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean setDualDARPolicyDirRecursively(int i, int i2, String str) {
    try {
      return this.mInstalld.setDualDARPolicyDirRecursively(i, i2, str);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean hasDualDARPolicy(String str) {
    try {
      return this.mInstalld.hasDualDARPolicy(str);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean hasDualDARPolicyRecursively(String str, List list) {
    try {
      return this.mInstalld.hasDualDARPolicyRecursively(str, list);
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }

  public boolean getDualDARLockstate() {
    try {
      return this.mInstalld.getDualDARLockstate();
    } catch (Exception e) {
      throw InstallerException.from(e);
    }
  }
}
