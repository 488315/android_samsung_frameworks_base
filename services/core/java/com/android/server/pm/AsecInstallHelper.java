package com.android.server.pm;

import android.app.ResourcesManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.storage.DiskInfo;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.p005os.IInstalld;
import android.provider.Settings;
import android.security.SystemKeyStore;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import com.android.internal.policy.AttributeCache;
import com.android.internal.util.ArrayUtils;
import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.android.server.pm.permission.PermissionManagerServiceInternal;
import com.android.server.pm.pkg.AndroidPackage;
import com.samsung.android.core.pm.containerservice.IContainerService;
import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import com.samsung.android.rune.PMRune;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import libcore.util.EmptyArray;

/* loaded from: classes3.dex */
public class AsecInstallHelper {
  public static Context mContext;
  public final BroadcastHelper mBroadcastHelper;
  public final DeletePackageHelper mDeletePackageHelper;
  public final PackageManagerServiceInjector mInjector;
  public final InstallPackageHelper mInstallPackageHelper;
  public final PackageInstallerService mInstallerService;
  public final PermissionManagerServiceInternal mPermissionManager;
  public final PackageManagerService mPm;
  public final UserManagerService mUserManager;
  public static final String ASEC_INTERNAL_PATH =
      new File(Environment.getDataDirectory(), "app-asec").getPath();
  public static boolean sPreMounted = false;
  public static final long UNBIND_DELAY = TimeUnit.MINUTES.toMillis(5);
  public static final ComponentName CONTAINER_COMPONENT =
      new ComponentName(
          "com.samsung.android.container", "com.samsung.android.container.ContainerService");
  public final Object mAvailableMountLock = new Object();
  public CountDownLatch mAvailableMountLatch = null;
  public boolean mMediaMounted = false;
  public AtomicLong mArtServiceVersion = new AtomicLong(-1);

  public static boolean installOnExternalAsec(int i) {
    return (i & 16) == 0 && (i & 8) != 0;
  }

  public AsecInstallHelper(PackageManagerService packageManagerService) {
    this.mPm = packageManagerService;
    PackageManagerServiceInjector packageManagerServiceInjector = packageManagerService.mInjector;
    this.mInjector = packageManagerServiceInjector;
    mContext = packageManagerServiceInjector.getContext();
    this.mInstallerService = packageManagerService.mInjector.getPackageInstallerService();
    this.mInstallPackageHelper = new InstallPackageHelper(packageManagerService);
    this.mBroadcastHelper = new BroadcastHelper(packageManagerService.mInjector);
    this.mDeletePackageHelper = new DeletePackageHelper(packageManagerService);
    this.mUserManager = packageManagerService.mInjector.getUserManagerService();
    this.mPermissionManager = packageManagerService.mInjector.getPermissionManagerServiceInternal();
  }

  public static Context getContext() {
    return mContext;
  }

  public VolumeInfo getMountedExternalVolume() {
    DiskInfo disk;
    for (VolumeInfo volumeInfo :
        ((StorageManager) this.mInjector.getSystemService(StorageManager.class)).getVolumes()) {
      if (volumeInfo.getType() == 0
          && (disk = volumeInfo.getDisk()) != null
          && disk.isSd()
          && volumeInfo.state == 2) {
        return volumeInfo;
      }
    }
    return null;
  }

  public void setMoveCallback(final InstallRequest installRequest) {
    final PackageManager packageManager = mContext.getPackageManager();
    packageManager.registerMoveCallback(
        new PackageManager.MoveCallback() { // from class: com.android.server.pm.AsecInstallHelper.1
          public void onStatusChanged(int i, int i2, long j) {
            if (i2 > 0) {
              return;
            }
            packageManager.unregisterMoveCallback(this);
            AsecInstallHelper.this.mPm.notifyInstallObserver(installRequest);
          }
        },
        this.mPm.mHandler);
  }

  public static boolean isAsecExternal(String str) {
    return !PackageHelperExt.getSdFilesystem(str).startsWith(ASEC_INTERNAL_PATH);
  }

  public static String getAsecPackageName(String str) {
    int lastIndexOf = str.lastIndexOf(PackageManagerShellCommandDataLoader.STDIN_PATH);
    return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
  }

  public static String getNextCodePath(String str) {
    String str2;
    SecureRandom secureRandom = new SecureRandom();
    do {
      StringBuilder sb = new StringBuilder(16);
      for (int i = 0; i < 16; i++) {
        int nextFloat = (int) (secureRandom.nextFloat() * 61.0f);
        if (nextFloat < 10) {
          sb.append((char) (nextFloat + 48));
        } else if (nextFloat < 36) {
          sb.append((char) (nextFloat + 55));
        } else {
          sb.append((char) (nextFloat + 61));
        }
      }
      str2 = str + PackageManagerShellCommandDataLoader.STDIN_PATH + sb.toString();
    } while (new File("/mnt/asec", str2).exists());
    Slog.i("PackageManager", "nextCodePath for ASEC: " + str2);
    return str2;
  }

  public static boolean isExternalAsec(AndroidPackage androidPackage) {
    return androidPackage.isExternalStorage() && TextUtils.isEmpty(androidPackage.getVolumeUuid());
  }

  public static boolean isExternal(PackageSetting packageSetting) {
    return (packageSetting.getFlags() & 262144) != 0;
  }

  public final void updateMediaStatus(int i, int i2, Set set) {
    Log.i("PackageManager", "Got message UPDATED_MEDIA_STATUS!");
    boolean z = i == 1;
    boolean z2 = i2 == 1;
    Log.i("PackageManager", "reportStatus: " + z + ", doGc: " + z2);
    if (z2) {
      Runtime.getRuntime().gc();
    }
    if (set != null) {
      Log.i("PackageManager", "Unloading all containers");
      unloadAllContainers(set);
    }
    if (z) {
      try {
        Log.i("PackageManager", "Invoking MountService call back");
        PackageHelperExt.getStorageManagerExt().finishMediaUpdate();
      } catch (RemoteException unused) {
        Log.e("PackageManager", "MountService not running?");
      }
    }
  }

  public void updatePreMountState(boolean z) {
    sPreMounted = z;
  }

  public static boolean getPreMountState() {
    return sPreMounted;
  }

  public void updateExternalMediaStatus(final boolean z, final boolean z2) {
    PackageManagerServiceUtils.enforceSystemOrRoot(
        "Media status can only be updated by the system");
    synchronized (this.mPm.mLock) {
      StringBuilder sb = new StringBuilder();
      sb.append("Updating external media status from ");
      sb.append(this.mMediaMounted ? "mounted" : "unmounted");
      sb.append(" to ");
      sb.append(z ? "mounted" : "unmounted");
      Log.i("PackageManager", sb.toString());
      if (z == this.mMediaMounted) {
        this.mPm.mHandler.sendMessage(this.mPm.mHandler.obtainMessage(12, z2 ? 1 : 0, -1));
      } else {
        this.mMediaMounted = z;
        this.mPm.mHandler.post(
            new Runnable() { // from class: com.android.server.pm.AsecInstallHelper.2
              @Override // java.lang.Runnable
              public void run() {
                try {
                  AsecInstallHelper.this.updateExternalMediaStatusInner(z, z2, true);
                } catch (RuntimeException e) {
                  Log.i("PackageManager", "updateExternalMediaStatus RuntimeException", e);
                  PackageManagerService.reportSettingsProblem(
                      5, "updateExternalMediaStatus runtime exception: is asec cmd timeout?");
                }
              }
            });
      }
    }
  }

  public boolean checkAvailableMount() {
    try {
      synchronized (this.mAvailableMountLock) {
        if (this.mAvailableMountLatch != null) {
          Log.i("PackageManager", "checkAvailableMount Waiting Latch");
          if (this.mAvailableMountLatch.await(240L, TimeUnit.SECONDS)) {
            Log.i("PackageManager", "checkAvailableMount Wake Latch");
            this.mAvailableMountLatch = null;
          } else {
            Log.i("PackageManager", "checkAvailableMount still wait......");
          }
        }
      }
      return true;
    } catch (InterruptedException unused) {
      Log.i("PackageManager", "checkAvailableMount Latch Exception");
      return true;
    }
  }

  public void setAvailableMountSync(boolean z) {
    if (z) {
      synchronized (this.mAvailableMountLock) {
        if (this.mAvailableMountLatch == null) {
          Log.i("PackageManager", "setAvailableMountSync Create Latch");
          this.mAvailableMountLatch = new CountDownLatch(1);
        } else {
          Log.i("PackageManager", "setAvailableMountSync Unknown Latch");
        }
      }
      return;
    }
    if (this.mAvailableMountLatch != null) {
      Log.i("PackageManager", "setAvailableMountSync Latch CountDown");
      this.mAvailableMountLatch.countDown();
    }
  }

  public final void updateExternalMediaStatusInner(boolean z, boolean z2, boolean z3) {
    boolean z4;
    ArrayMap arrayMap = new ArrayMap();
    int[] iArr = EmptyArray.INT;
    String[] secureContainerList = PackageHelperExt.getSecureContainerList();
    if (ArrayUtils.isEmpty(secureContainerList)) {
      Log.i("PackageManager", "No secure containers found");
    } else {
      synchronized (this.mPm.mLock) {
        Iterator it = new ArraySet(secureContainerList).iterator();
        while (it.hasNext()) {
          String str = (String) it.next();
          if (!PackageInstallerService.isStageName(str)) {
            String asecPackageName = getAsecPackageName(str);
            if (asecPackageName == null) {
              Slog.i("PackageManager", "Found stale container " + str + " with no package name");
            } else {
              PackageSetting packageSetting =
                  (PackageSetting) this.mPm.mSettings.mPackages.get(asecPackageName);
              if (packageSetting == null) {
                Slog.i(
                    "PackageManager",
                    "Found stale container " + str + " with no matching settings");
              } else if (!z3 || z || isExternal(packageSetting)) {
                Log.i("PackageManager", "isMounted: " + z + ", PreMounted: " + sPreMounted);
                if (z3 && z != (z4 = sPreMounted) && !z2 && !z4) {
                  Log.i("PackageManager", "Already unmounted!! " + z);
                  return;
                }
                AsecInstallArgs asecInstallArgs =
                    new AsecInstallArgs(
                        str,
                        InstructionSets.getAppDexInstructionSets(
                            packageSetting.getPrimaryCpuAbi(), packageSetting.getSecondaryCpuAbi()),
                        this.mPm);
                if (asecInstallArgs.isException && (z || !z2)) {
                  Log.i("PackageManager", "args has unknown exception!! " + z);
                  return;
                }
                if (packageSetting.getPathString() != null
                    && packageSetting.getPathString().startsWith(asecInstallArgs.getCodePath())) {
                  arrayMap.put(asecInstallArgs, packageSetting.getPathString());
                  int appId = packageSetting.getAppId();
                  if (appId != -1) {
                    iArr = ArrayUtils.appendInt(iArr, appId);
                  }
                } else {
                  Slog.i(
                      "PackageManager",
                      "Found stale container "
                          + str
                          + ": expected codePath="
                          + packageSetting.getPathString());
                }
              }
            }
          }
        }
        Arrays.sort(iArr);
      }
    }
    if (z) {
      loadMediaPackages(arrayMap, iArr, z3);
      this.mInstallerService.onSecureContainersAvailable();
    } else {
      unloadMediaPackages(arrayMap, iArr, z2);
    }
    if (!PMRune.PM_INSTALL_TO_SDCARD || z) {
      return;
    }
    synchronized (MovePackageHelper.sMoveIdMapForSd) {
      if (MovePackageHelper.sPendingMoves.size() > 0) {
        this.mPm.mMoveCallbacks.notifyStatusChanged(
            ((MovePackageHelper.SdcardParams) MovePackageHelper.sPendingMoves.get(0)).moveId, -1);
      }
      MovePackageHelper.sMoveIdMapForSd.clear();
      MovePackageHelper.sPendingMoves.clear();
    }
  }

  /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x0098 -> B:41:0x0103). Please report as a decompilation issue!!! */
  public final void loadMediaPackages(ArrayMap arrayMap, int[] iArr, boolean z) {
    String str;
    StringBuilder sb;
    AndroidPackage androidPackage;
    ArrayList arrayList = new ArrayList();
    Set keySet = arrayMap.keySet();
    checkAvailableMount();
    Iterator it = keySet.iterator();
    while (true) {
      boolean z2 = true;
      if (!it.hasNext()) {
        synchronized (this.mPm.mLock) {
          Settings.VersionInfo externalVersion =
              z ? this.mPm.mSettings.getExternalVersion() : this.mPm.mSettings.getInternalVersion();
          String str2 = z ? "primary_physical" : StorageManager.UUID_PRIVATE_INTERNAL;
          if (externalVersion.sdkVersion != this.mPm.getSdkVersion()) {
            PackageManagerServiceUtils.logCriticalInfo(
                4,
                "Platform changed from "
                    + externalVersion.sdkVersion
                    + " to "
                    + this.mPm.getSdkVersion()
                    + "; regranting permissions for external");
          }
          PermissionManagerServiceInternal permissionManagerServiceInternal =
              this.mPermissionManager;
          if (externalVersion.sdkVersion == this.mPm.getSdkVersion()) {
            z2 = false;
          }
          permissionManagerServiceInternal.onStorageVolumeMounted(str2, z2);
          externalVersion.forceCurrent();
          this.mPm.mSettings.writeLPr(this.mPm.snapshotComputer(), false);
        }
        if (arrayList.size() > 0) {
          BroadcastHelper broadcastHelper = this.mBroadcastHelper;
          PackageManagerService packageManagerService = this.mPm;
          Objects.requireNonNull(packageManagerService);
          broadcastHelper.sendResourcesChangedBroadcast(
              new AsecInstallHelper$$ExternalSyntheticLambda0(packageManagerService),
              true,
              false,
              (String[]) arrayList.toArray(new String[arrayList.size()]),
              iArr);
          return;
        }
        return;
      }
      AsecInstallArgs asecInstallArgs = (AsecInstallArgs) it.next();
      String str3 = (String) arrayMap.get(asecInstallArgs);
      int i = -18;
      try {
        try {
        } catch (Throwable th) {
          if (i != 1) {
            Log.i("PackageManager", "Container " + asecInstallArgs.cid + " is stale, retCode=" + i);
          }
          throw th;
        }
      } catch (RuntimeException unused) {
        PackageManagerService.reportSettingsProblem(
            5, "loadMediaPackage Exception: " + asecInstallArgs.cid);
        if (i != 1) {
          str = "PackageManager";
          sb = new StringBuilder();
        }
      }
      if (asecInstallArgs.doPreInstall(1) != 1) {
        Slog.e(
            "PackageManager",
            "Failed to mount cid: " + asecInstallArgs.cid + " when installing from sdcard");
        str = "PackageManager";
        sb = new StringBuilder();
      } else {
        if (str3 != null && str3.startsWith(asecInstallArgs.getCodePath())) {
          int defParseFlags = this.mPm.getDefParseFlags();
          if (asecInstallArgs.isExternalAsec()) {
            defParseFlags |= 8;
          }
          synchronized (this.mPm.mInstallLock) {
            try {
              try {
                androidPackage =
                    this.mInstallPackageHelper.initPackageTracedLI(
                        new File(str3),
                        defParseFlags,
                        IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
              } catch (Throwable th2) {
                th = th2;
                throw th;
              }
            } catch (PackageManagerException e) {
              Slog.i("PackageManager", "Failed to scan " + str3 + ": " + e.getMessage());
              androidPackage = null;
            }
            if (androidPackage != null) {
              synchronized (this.mPm.mLock) {
                try {
                  arrayList.add(androidPackage.getPackageName());
                  asecInstallArgs.doPostInstall(1, androidPackage.getUid());
                } catch (Throwable th3) {
                  try {
                    throw th3;
                  } catch (Throwable th4) {
                    th = th4;
                    i = 1;
                    throw th;
                  }
                }
              }
              i = 1;
            } else {
              Slog.i("PackageManager", "Failed to install pkg from  " + str3 + " from sdcard");
            }
          }
          if (i != 1) {
            str = "PackageManager";
            sb = new StringBuilder();
          }
        }
        Slog.e(
            "PackageManager",
            "Container "
                + asecInstallArgs.cid
                + " cachepath "
                + asecInstallArgs.getCodePath()
                + " does not match one in settings "
                + str3);
        str = "PackageManager";
        sb = new StringBuilder();
      }
      sb.append("Container ");
      sb.append(asecInstallArgs.cid);
      sb.append(" is stale, retCode=");
      sb.append(i);
      Log.i(str, sb.toString());
    }
  }

  public void unloadAllContainers(Set set) {
    Iterator it = set.iterator();
    while (it.hasNext()) {
      AsecInstallArgs asecInstallArgs = (AsecInstallArgs) it.next();
      synchronized (this.mPm.mInstallLock) {
        asecInstallArgs.doPostDeleteLI(false);
      }
    }
  }

  public final void unloadMediaPackages(ArrayMap arrayMap, int[] iArr, boolean z) {
    Object obj;
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    ArrayList arrayList3 = new ArrayList();
    Set keySet = arrayMap.keySet();
    Iterator it = keySet.iterator();
    while (it.hasNext()) {
      AsecInstallArgs asecInstallArgs = (AsecInstallArgs) it.next();
      String packageName = asecInstallArgs.getPackageName();
      PackageRemovedInfo packageRemovedInfo = new PackageRemovedInfo(this.mPm);
      Object obj2 = this.mPm.mInstallLock;
      synchronized (obj2) {
        try {
          obj = obj2;
          try {
            PackageFreezer freezePackageForDelete =
                this.mPm.freezePackageForDelete(packageName, -1, 1, "unloadMediaPackages", 13);
            try {
              Iterator it2 = it;
              boolean deletePackageLIF =
                  this.mDeletePackageHelper.deletePackageLIF(
                      packageName,
                      null,
                      false,
                      this.mUserManager.getUserIds(),
                      1,
                      packageRemovedInfo,
                      false);
              if (freezePackageForDelete != null) {
                freezePackageForDelete.close();
              }
              if (deletePackageLIF) {
                arrayList.add(packageName);
                arrayList2.add(asecInstallArgs.getCodePath());
              } else {
                Slog.e("PackageManager", "Failed to delete pkg from sdcard: " + packageName);
                arrayList3.add(asecInstallArgs);
              }
              Log.i("PackageManager", "!@Remove package from AttributeCache: " + packageName);
              AttributeCache.instance().removePackage(packageName);
              it = it2;
            } finally {
            }
          } catch (Throwable th) {
            th = th;
            throw th;
          }
        } catch (Throwable th2) {
          th = th2;
          obj = obj2;
        }
      }
    }
    synchronized (this.mPm.mLock) {
      this.mPm.mSettings.writeLPr(this.mPm.snapshotComputer(), false);
    }
    if (arrayList.size() > 0) {
      setAvailableMountSync(true);
      Log.i("PackageManager", "setAvailableMountSync true");
      BroadcastHelper broadcastHelper = this.mBroadcastHelper;
      PackageManagerService packageManagerService = this.mPm;
      Objects.requireNonNull(packageManagerService);
      broadcastHelper.sendResourcesChangedBroadcast(
          new AsecInstallHelper$$ExternalSyntheticLambda0(packageManagerService),
          false,
          false,
          (String[]) arrayList.toArray(new String[arrayList.size()]),
          iArr,
          new C20813(z, keySet));
      Iterator it3 = arrayList2.iterator();
      while (it3.hasNext()) {
        ResourcesManager.getInstance().invalidatePath((String) it3.next());
      }
      Runtime.getRuntime().gc();
      System.runFinalization();
      Log.i("PackageManager", "Flush ResourceManager path: " + arrayList2);
      return;
    }
    updateMediaStatus(z ? 1 : 0, -1, keySet);
  }

  /* renamed from: com.android.server.pm.AsecInstallHelper$3 */
  public class C20813 extends IIntentReceiver.Stub {
    public final /* synthetic */ Set val$keys;
    public final /* synthetic */ boolean val$reportStatus;

    public C20813(boolean z, Set set) {
      this.val$reportStatus = z;
      this.val$keys = set;
    }

    public void performReceive(
        Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
      Log.i("PackageManager", "do force gc after sending broadcast: " + intent);
      final boolean z3 = this.val$reportStatus;
      final Set set = this.val$keys;
      new Thread(
              new Runnable() { // from class:
                // com.android.server.pm.AsecInstallHelper$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  AsecInstallHelper.C20813.this.lambda$performReceive$0(z3, set);
                }
              })
          .start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performReceive$0(boolean z, Set set) {
      Runtime.getRuntime().gc();
      AsecInstallHelper.this.updateMediaStatus(z ? 1 : 0, -1, set);
      AsecInstallHelper.this.setAvailableMountSync(false);
      Log.i("PackageManager", "setAvailableMountSync false");
    }
  }

  public void doRenameAsec(InstallRequest installRequest, ParsedPackage parsedPackage) {
    int returnCode = installRequest.getReturnCode();
    String returnMsg = installRequest.getReturnMsg();
    if (returnCode != 1) {
      throw new PrepareFailure(returnCode, returnMsg);
    }
    if (!doRenameAsecLI(installRequest, parsedPackage)) {
      throw new PrepareFailure(-4, "Failed rename");
    }
  }

  public final boolean doRenameAsecLI(InstallRequest installRequest, ParsedPackage parsedPackage) {
    String sdDir;
    String nextCodePath = getNextCodePath(parsedPackage.getPackageName());
    String extractCidFromCodePath = extractCidFromCodePath(installRequest.getCodePath());
    if (PackageHelperExt.isContainerMounted(extractCidFromCodePath)
        && !PackageHelperExt.unMountSdDir(extractCidFromCodePath, true)) {
      Slog.i("PackageManager", "Failed to unmount " + extractCidFromCodePath + " before renaming");
      return false;
    }
    if (!PackageHelperExt.renameSdDir(extractCidFromCodePath, nextCodePath)) {
      Slog.e(
          "PackageManager",
          "Failed to rename "
              + extractCidFromCodePath
              + " to "
              + nextCodePath
              + " which might be stale. Will try to clean up");
      if (!PackageHelperExt.destroySdDir(nextCodePath)) {
        Slog.e("PackageManager", "Very strange. Cannot clean up stale container " + nextCodePath);
        return false;
      }
      if (!PackageHelperExt.renameSdDir(extractCidFromCodePath, nextCodePath)) {
        Slog.e(
            "PackageManager",
            "Failed to rename "
                + extractCidFromCodePath
                + " to "
                + nextCodePath
                + " inspite of cleaning it up");
        return false;
      }
    }
    if (!PackageHelperExt.isContainerMounted(nextCodePath)) {
      Slog.i("PackageManager", "Mounting container " + nextCodePath);
      sdDir = PackageHelperExt.mountSdDir(nextCodePath, getEncryptKey(), 1000);
    } else {
      sdDir = PackageHelperExt.getSdDir(nextCodePath);
    }
    if (sdDir == null) {
      Slog.i("PackageManager", "Failed to get cache path for  " + nextCodePath);
      return false;
    }
    Slog.i(
        "PackageManager",
        "Successfully renamed "
            + extractCidFromCodePath
            + " to "
            + nextCodePath
            + " at new path: "
            + sdDir);
    if (!shouldAddDexOptOnAsec()
        && !PackageHelperExt.fixSdPermissions(nextCodePath, -1, (String) null)) {
      String str = "Failed to fixPerms " + nextCodePath;
      Slog.e("PackageManager", str);
      PackageHelperExt.destroySdDir(nextCodePath);
      installRequest.setError(-18, str);
      return false;
    }
    File codeFile = installRequest.getCodeFile();
    setMountPath(sdDir, installRequest);
    File codeFile2 = installRequest.getCodeFile();
    parsedPackage.setPath(codeFile2.getAbsolutePath());
    parsedPackage.setBaseApkPath(
        FileUtils.rewriteAfterRename(codeFile, codeFile2, parsedPackage.getBaseApkPath()));
    parsedPackage.setSplitCodePaths(
        FileUtils.rewriteAfterRename(codeFile, codeFile2, parsedPackage.getSplitCodePaths()));
    return true;
  }

  public void cleanUpAsecResources(File file, String[] strArr) {
    new AsecInstallArgs(file.getAbsolutePath(), strArr, true, this.mPm).doPostDeleteLI(true);
  }

  public static String getEncryptKey() {
    try {
      String retrieveKeyHexString = SystemKeyStore.getInstance().retrieveKeyHexString("AppsOnSD");
      if (retrieveKeyHexString != null
          || (retrieveKeyHexString =
                  SystemKeyStore.getInstance().generateNewKeyHexString(128, "AES", "AppsOnSD"))
              != null) {
        return retrieveKeyHexString;
      }
      Slog.e("PackageManager", "Failed to create encryption keys");
      return null;
    } catch (IOException e) {
      Slog.e("PackageManager", "Failed to retrieve encryption keys with exception: " + e);
      return null;
    } catch (NoSuchAlgorithmException e2) {
      Slog.e("PackageManager", "Failed to create encryption keys with exception: " + e2);
      return null;
    }
  }

  public static String extractCidFromCodePath(String str) {
    if (str != null) {
      return str.substring(str.lastIndexOf("/") + 1, str.length());
    }
    return null;
  }

  public static void setMountPath(String str, InstallRequest installRequest) {
    File file = new File(str);
    File file2 = new File(file, "pkg.apk");
    if (file2.exists()) {
      installRequest.setCodeFile(file2);
    } else {
      installRequest.setCodeFile(file);
    }
  }

  public static boolean isExternalAsecPath(String str) {
    return str.startsWith("/mnt/asec");
  }

  /* JADX WARN: Multi-variable type inference failed */
  public int copyPackage(InstallRequest installRequest, String str) {
    String str2;
    Context context = getContext();
    MyServiceConnection myServiceConnection = MyServiceConnection.getInstance();
    myServiceConnection.cancelAutoUnbound(this.mPm.mHandler);
    myServiceConnection.bindAndWaitUntilBound(context);
    final IContainerService containerService = myServiceConnection.getContainerService();
    try {
      if (containerService == null) {
        return -18;
      }
      try {
        str2 =
            containerService.copyPackageToContainer(
                installRequest.getOriginInfo().mFile.getAbsolutePath(),
                str,
                getEncryptKey(),
                isExternalAsec(installRequest),
                PackageManagerServiceUtils.deriveAbiOverride(installRequest.getAbiOverride()),
                shouldAddDexOptOnAsec());
      } catch (RemoteException e) {
        e.printStackTrace();
        this.mPm.mHandler.post(
            new Runnable() { // from class:
              // com.android.server.pm.AsecInstallHelper$$ExternalSyntheticLambda1
              @Override // java.lang.Runnable
              public final void run() {
                AsecInstallHelper.lambda$copyPackage$0(containerService);
              }
            });
        Handler handler = this.mPm.mHandler;
        myServiceConnection.scheduleAutoUnbound(handler, context);
        str2 = null;
        this = handler;
      }
      if (str2 == null) {
        return -18;
      }
      setMountPath(str2, installRequest);
      return 1;
    } finally {
      this.mPm.mHandler.post(
          new Runnable() { // from class:
            // com.android.server.pm.AsecInstallHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              AsecInstallHelper.lambda$copyPackage$0(containerService);
            }
          });
      myServiceConnection.scheduleAutoUnbound(this.mPm.mHandler, context);
    }
  }

  public static /* synthetic */ void lambda$copyPackage$0(IContainerService iContainerService) {
    try {
      iContainerService.doForceGC();
    } catch (RemoteException e) {
      e.printStackTrace();
    }
  }

  public boolean isExternalAsec(InstallRequest installRequest) {
    return (installRequest.getInstallFlags() & 8) != 0;
  }

  public static boolean isPreloadApp(String str) {
    return str != null && str.split("/").length == 4;
  }

  public static boolean canInstallOnExternal(String str, int i, int i2) {
    if (i2 == 0
        && "com.android.vending".equals(str)
        && checkSettingsForDirectSdInstall()
        && isSdCardAvailableAndMounted(i2)) {
      return i == 0 || i == 2;
    }
    return false;
  }

  public static boolean checkSettingsForDirectSdInstall() {
    int i = Settings.System.getInt(mContext.getContentResolver(), "installToSdCardState", -1);
    Slog.d("PackageManager", "checkSettingsForDirectSdInstall value : " + i);
    return i == 1;
  }

  public static boolean isSdCardAvailableAndMounted(int i) {
    long clearCallingIdentity = Binder.clearCallingIdentity();
    try {
      StorageVolume[] volumeList = PackageHelperExt.getVolumeList(i, "android", 0);
      if (volumeList != null) {
        for (StorageVolume storageVolume : volumeList) {
          if ("sd".equals(storageVolume.getSubSystem())
              && storageVolume.isRemovable()
              && storageVolume.getState() == "mounted") {
            return true;
          }
        }
      }
      return false;
    } finally {
      Binder.restoreCallingIdentity(clearCallingIdentity);
    }
  }

  public boolean shouldAddDexOptOnAsec() {
    if (this.mArtServiceVersion.get() < 0) {
      PackageInfo packageInfo =
          this.mPm.snapshotComputer().getPackageInfo("com.google.android.art", 1073741824L, 0);
      if (packageInfo != null) {
        this.mArtServiceVersion.set(packageInfo.getLongVersionCode());
      } else {
        this.mArtServiceVersion.set(-1L);
      }
    }
    return this.mArtServiceVersion.get() < 340915012;
  }

  public void sendResourcesChangedBroadcast(boolean z, boolean z2, String[] strArr, int[] iArr) {
    BroadcastHelper broadcastHelper = this.mBroadcastHelper;
    PackageManagerService packageManagerService = this.mPm;
    Objects.requireNonNull(packageManagerService);
    broadcastHelper.sendResourcesChangedBroadcast(
        new AsecInstallHelper$$ExternalSyntheticLambda0(packageManagerService),
        z,
        z2,
        strArr,
        iArr);
  }

  public class MyServiceConnection implements ServiceConnection {
    public static MyServiceConnection sInstance;
    public boolean mBinding = false;
    public IContainerService mContainerService;
    public Runnable mUnbindingRunnable;

    public static MyServiceConnection getInstance() {
      MyServiceConnection myServiceConnection;
      synchronized (MyServiceConnection.class) {
        if (sInstance == null) {
          sInstance = new MyServiceConnection();
        }
        myServiceConnection = sInstance;
      }
      return myServiceConnection;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      synchronized (this) {
        this.mContainerService = IContainerService.Stub.asInterface(Binder.allowBlocking(iBinder));
        this.mBinding = false;
        notifyAll();
      }
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
      synchronized (this) {
        this.mBinding = false;
        this.mContainerService = null;
      }
    }

    public final boolean isBound() {
      return this.mContainerService != null;
    }

    public void cancelAutoUnbound(Handler handler) {
      Runnable runnable = this.mUnbindingRunnable;
      if (runnable == null) {
        return;
      }
      handler.removeCallbacks(runnable);
      this.mUnbindingRunnable = null;
    }

    public void bindAndWaitUntilBound(Context context) {
      if (isBound()) {
        return;
      }
      Intent component = new Intent().setComponent(AsecInstallHelper.CONTAINER_COMPONENT);
      synchronized (this) {
        boolean bindServiceAsUser =
            context.bindServiceAsUser(component, this, 1, UserHandle.SYSTEM);
        this.mBinding = bindServiceAsUser;
        if (bindServiceAsUser) {
          long uptimeMillis = SystemClock.uptimeMillis() + 5000;
          while (this.mContainerService == null && SystemClock.uptimeMillis() < uptimeMillis) {
            try {
              wait(100L);
            } catch (InterruptedException unused) {
            }
          }
        }
      }
      if (this.mBinding || !isBound()) {
        synchronized (this) {
          context.unbindService(this);
          this.mBinding = false;
          this.mContainerService = null;
        }
      }
    }

    public IContainerService getContainerService() {
      return this.mContainerService;
    }

    public void scheduleAutoUnbound(Handler handler, final Context context) {
      if (this.mUnbindingRunnable == null) {
        Runnable runnable = new Runnable() { // from class:
              // com.android.server.pm.AsecInstallHelper.MyServiceConnection.1
              @Override // java.lang.Runnable
              public void run() {
                if (MyServiceConnection.sInstance != null) {
                  synchronized (this) {
                    context.unbindService(MyServiceConnection.sInstance);
                    MyServiceConnection myServiceConnection = MyServiceConnection.this;
                    myServiceConnection.mContainerService = null;
                    myServiceConnection.mBinding = false;
                    myServiceConnection.mUnbindingRunnable = null;
                  }
                }
              }
            };
        this.mUnbindingRunnable = runnable;
        handler.postDelayed(runnable, AsecInstallHelper.UNBIND_DELAY);
      }
    }
  }
}
