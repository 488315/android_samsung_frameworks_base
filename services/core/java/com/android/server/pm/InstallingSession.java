package com.android.server.pm;

import android.apex.ApexInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.DataLoaderParams;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.PackageInfoLite;
import android.content.pm.PackageInstaller;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.PackageLite;
import android.os.Environment;
import android.os.Process;
import android.os.Trace;
import android.os.UserHandle;
import android.os.incremental.IncrementalManager;
import android.p005os.IInstalld;
import android.system.ErrnoException;
import android.system.Os;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import com.android.internal.content.F2fsUtils;
import com.android.internal.content.InstallLocationUtils;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.Preconditions;
import com.android.server.pm.parsing.PackageParser2;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import com.samsung.android.rune.PMRune;
import com.samsung.android.server.p025pm.PmLog;
import com.samsung.android.server.p025pm.install.ThermalInstallThrottlingUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import libcore.io.IoUtils;

/* loaded from: classes3.dex */
public class InstallingSession {
  public final List mAllowlistedRestrictedPermissions;
  public final boolean mApplicationEnabledSettingPersistent;
  public final int mAutoRevokePermissionsMode;
  public final int mDataLoaderType;
  public final boolean mForceQueryableOverride;
  public int mInstallFlags;
  public final InstallPackageHelper mInstallPackageHelper;
  public final int mInstallReason;
  public final int mInstallScenario;
  public final InstallSource mInstallSource;
  public final boolean mIsInherit;
  public boolean mIsMoveRequest;
  public final MoveInfo mMoveInfo;
  public final IPackageInstallObserver2 mObserver;
  public final OriginInfo mOriginInfo;
  public final String mPackageAbiOverride;
  public final PackageLite mPackageLite;
  public final int mPackageSource;
  public MultiPackageInstallingSession mParentInstallingSession;
  public final ArrayMap mPermissionStates;
  public final PackageManagerService mPm;
  public int mPreferredInstallLocation;
  public final RemovePackageHelper mRemovePackageHelper;
  public final int mRequireUserAction;
  public final long mRequiredInstalledVersionCode;
  public int mRet;
  public final int mSessionId;
  public final SigningDetails mSigningDetails;
  public int mTraceCookie;
  public String mTraceMethod;
  public final UserHandle mUser;
  public final String mVolumeUuid;

  public InstallingSession(
      OriginInfo originInfo,
      MoveInfo moveInfo,
      IPackageInstallObserver2 iPackageInstallObserver2,
      int i,
      InstallSource installSource,
      String str,
      UserHandle userHandle,
      String str2,
      int i2,
      PackageLite packageLite,
      PackageManagerService packageManagerService) {
    this.mIsMoveRequest = false;
    this.mPreferredInstallLocation = -1;
    this.mPm = packageManagerService;
    this.mUser = userHandle;
    this.mInstallPackageHelper = new InstallPackageHelper(packageManagerService);
    this.mRemovePackageHelper = new RemovePackageHelper(packageManagerService);
    this.mOriginInfo = originInfo;
    this.mMoveInfo = moveInfo;
    this.mObserver = iPackageInstallObserver2;
    this.mInstallFlags = i;
    this.mInstallSource = (InstallSource) Preconditions.checkNotNull(installSource);
    this.mVolumeUuid = str;
    this.mPackageAbiOverride = str2;
    this.mPermissionStates = new ArrayMap();
    this.mAllowlistedRestrictedPermissions = null;
    this.mAutoRevokePermissionsMode = 3;
    this.mSigningDetails = SigningDetails.UNKNOWN;
    this.mInstallReason = 0;
    this.mInstallScenario = 0;
    this.mForceQueryableOverride = false;
    this.mDataLoaderType = 0;
    this.mRequiredInstalledVersionCode = -1L;
    this.mPackageSource = i2;
    this.mPackageLite = packageLite;
    this.mIsInherit = false;
    this.mSessionId = -1;
    this.mRequireUserAction = 0;
    this.mApplicationEnabledSettingPersistent = false;
  }

  public InstallingSession(
      int i,
      File file,
      String str,
      IPackageInstallObserver2 iPackageInstallObserver2,
      PackageInstaller.SessionParams sessionParams,
      InstallSource installSource,
      UserHandle userHandle,
      SigningDetails signingDetails,
      int i2,
      PackageLite packageLite,
      PackageManagerService packageManagerService) {
    this.mIsMoveRequest = false;
    this.mPreferredInstallLocation = -1;
    this.mPm = packageManagerService;
    this.mUser = userHandle;
    this.mInstallPackageHelper = new InstallPackageHelper(packageManagerService);
    this.mRemovePackageHelper = new RemovePackageHelper(packageManagerService);
    if (file != null) {
      this.mOriginInfo = OriginInfo.fromStagedFile(file);
    } else {
      this.mOriginInfo = OriginInfo.fromStagedContainer(str);
    }
    this.mMoveInfo = null;
    this.mInstallReason =
        fixUpInstallReason(installSource.mInstallerPackageName, i2, sessionParams.installReason);
    this.mInstallScenario = sessionParams.installScenario;
    this.mObserver = iPackageInstallObserver2;
    this.mInstallFlags = sessionParams.installFlags;
    this.mInstallSource = installSource;
    this.mVolumeUuid = sessionParams.volumeUuid;
    this.mPackageAbiOverride = sessionParams.abiOverride;
    this.mPermissionStates = sessionParams.getPermissionStates();
    this.mAllowlistedRestrictedPermissions = sessionParams.whitelistedRestrictedPermissions;
    this.mAutoRevokePermissionsMode = sessionParams.autoRevokePermissionsMode;
    this.mSigningDetails = signingDetails;
    this.mForceQueryableOverride = sessionParams.forceQueryableOverride;
    DataLoaderParams dataLoaderParams = sessionParams.dataLoaderParams;
    this.mDataLoaderType = dataLoaderParams != null ? dataLoaderParams.getType() : 0;
    this.mRequiredInstalledVersionCode = sessionParams.requiredInstalledVersionCode;
    this.mPackageSource = sessionParams.packageSource;
    this.mPackageLite = packageLite;
    this.mIsInherit = sessionParams.mode == 2;
    this.mSessionId = i;
    this.mRequireUserAction = sessionParams.requireUserAction;
    this.mApplicationEnabledSettingPersistent = sessionParams.applicationEnabledSettingPersistent;
  }

  public String toString() {
    return "InstallingSession{"
        + Integer.toHexString(System.identityHashCode(this))
        + " file="
        + this.mOriginInfo.mFile
        + "}";
  }

  public final int overrideInstallLocation(String str, int i, int i2) {
    OriginInfo originInfo = this.mOriginInfo;
    if (originInfo.mStaged) {
      if (originInfo.mFile != null) {
        this.mInstallFlags = (this.mInstallFlags | 16) & (-9);
      } else if (originInfo.mCid != null) {
        this.mInstallFlags = (this.mInstallFlags | 8) & (-17);
      } else {
        throw new IllegalStateException("Invalid stage location");
      }
    }
    if (i < 0) {
      return InstallLocationUtils.getInstallationErrorCode(i);
    }
    PackageStateInternal packageStateInternal =
        this.mPm.snapshotComputer().getPackageStateInternal(str);
    AndroidPackage androidPackage =
        packageStateInternal == null ? null : packageStateInternal.getAndroidPackage();
    if (androidPackage != null) {
      i =
          InstallLocationUtils.installLocationPolicy(
              i2,
              i,
              this.mInstallFlags,
              packageStateInternal.isSystem(),
              androidPackage.isExternalStorage());
    }
    int i3 = this.mInstallFlags;
    boolean z = (i3 & 16) != 0;
    boolean z2 = (i3 & 8) != 0;
    boolean z3 = (i3 & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
    if (z && z2) {
      Slog.i(
          "PackageManager",
          "Conflicting flags specified for installing on both internal and external");
      return -19;
    }
    if (z2 && z3) {
      Slog.i("PackageManager", "Conflicting flags specified for installing ephemeral on external");
      return -19;
    }
    if (!z && !z2) {
      if (i == 2) {
        this.mInstallFlags = (i3 | 8) & (-17);
      } else {
        this.mInstallFlags = (i3 | 16) & (-9);
      }
    }
    return 1;
  }

  public final void handleStartCopy(InstallRequest installRequest) {
    AndroidPackage androidPackage;
    int i;
    int i2 = this.mInstallFlags;
    if ((131072 & i2) != 0) {
      this.mRet = 1;
      return;
    }
    PackageInfoLite minimalPackageInfo =
        PackageManagerServiceUtils.getMinimalPackageInfo(
            this.mPm.mContext,
            this.mPackageLite,
            this.mOriginInfo.mResolvedPath,
            i2,
            this.mPackageAbiOverride);
    int i3 = this.mInstallFlags;
    boolean z = false;
    if ((2097152 & i3) != 0) {
      int intValue =
          ((Integer)
                  this.mInstallPackageHelper.verifyReplacingVersionCode(
                          minimalPackageInfo, this.mRequiredInstalledVersionCode, i3)
                      .first)
              .intValue();
      this.mRet = intValue;
      if (intValue != 1) {
        installRequest.setError(intValue, "Failed to verify version code");
        return;
      }
    }
    boolean z2 = (this.mInstallFlags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
    if (PackageManagerService.DEBUG_INSTANT && z2) {
      Slog.v("PackageManager", "pkgLite for install: " + minimalPackageInfo);
    }
    OriginInfo originInfo = this.mOriginInfo;
    if (!originInfo.mStaged && (i = minimalPackageInfo.recommendedInstallLocation) == -1) {
      minimalPackageInfo.recommendedInstallLocation =
          this.mPm.freeCacheForInstallation(
              i,
              this.mPackageLite,
              originInfo.mResolvedPath,
              this.mPackageAbiOverride,
              this.mInstallFlags);
    }
    if (!this.mIsMoveRequest) {
      synchronized (this.mPm.mLock) {
        androidPackage = (AndroidPackage) this.mPm.mPackages.get(minimalPackageInfo.packageName);
      }
      if (androidPackage != null && AsecInstallHelper.isExternalAsec(androidPackage)) {
        Slog.i(
            "PackageManager",
            "Found on sdcard, keep current location: " + minimalPackageInfo.packageName);
        installRequest.setNeedToMove(true);
      }
    }
    if (PMRune.PM_INSTALL_TO_SDCARD && !this.mIsMoveRequest && !installRequest.getNeedToMove()) {
      ApplicationInfo applicationInfo =
          this.mPm.snapshotComputer().getApplicationInfo(minimalPackageInfo.packageName, 0L, 0);
      if (applicationInfo != null && (applicationInfo.flags & 1) != 0) {
        z = true;
      }
      if (!z
          && AsecInstallHelper.canInstallOnExternal(
              installRequest.getInstallerPackageName(),
              this.mPreferredInstallLocation,
              installRequest.getUserId())) {
        Slog.i("PackageManager", "Move the app to sdcard later: " + minimalPackageInfo.packageName);
        installRequest.setNeedToMove(true);
      }
    }
    int overrideInstallLocation =
        overrideInstallLocation(
            minimalPackageInfo.packageName,
            minimalPackageInfo.recommendedInstallLocation,
            minimalPackageInfo.installLocation);
    this.mRet = overrideInstallLocation;
    if (overrideInstallLocation != 1) {
      installRequest.setError(overrideInstallLocation, "Failed to override installation location");
    }
  }

  public final void handleReturnCode(InstallRequest installRequest) {
    processPendingInstall(installRequest);
  }

  public final void processPendingInstall(final InstallRequest installRequest) {
    if (this.mRet == 1) {
      this.mRet = copyApk(installRequest);
    }
    if (this.mRet == 1) {
      F2fsUtils.releaseCompressedBlocks(
          this.mPm.mContext.getContentResolver(), new File(installRequest.getCodePath()));
    }
    installRequest.setReturnCode(this.mRet);
    MultiPackageInstallingSession multiPackageInstallingSession = this.mParentInstallingSession;
    if (multiPackageInstallingSession != null) {
      multiPackageInstallingSession.tryProcessInstallRequest(installRequest);
    } else {
      this.mPm.mHandler.post(
          new Runnable() { // from class:
            // com.android.server.pm.InstallingSession$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              InstallingSession.this.lambda$processPendingInstall$0(installRequest);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$processPendingInstall$0(InstallRequest installRequest) {
    processInstallRequests(this.mRet == 1, Collections.singletonList(installRequest));
  }

  public final int copyApk(InstallRequest installRequest) {
    if (this.mMoveInfo != null) {
      return copyApkForMoveInstall(installRequest);
    }
    if (AsecInstallHelper.installOnExternalAsec(this.mInstallFlags)) {
      return copyApkForAsecInstall(installRequest);
    }
    return copyApkForFileInstall(installRequest);
  }

  public final int copyApkForFileInstall(InstallRequest installRequest) {
    Trace.traceBegin(262144L, "copyApk");
    try {
      OriginInfo originInfo = this.mOriginInfo;
      if (originInfo.mStaged) {
        installRequest.setCodeFile(originInfo.mFile);
        Trace.traceEnd(262144L);
        return 1;
      }
      try {
        installRequest.setCodeFile(
            this.mPm.mInstallerService.allocateStageDirLegacy(
                this.mVolumeUuid,
                (this.mInstallFlags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0));
        int copyPackage =
            PackageManagerServiceUtils.copyPackage(
                this.mOriginInfo.mFile.getAbsolutePath(), installRequest.getCodeFile());
        if (copyPackage != 1) {
          Slog.e("PackageManager", "Failed to copy package");
          installRequest.setError(copyPackage, "Failed to copy package");
          Trace.traceEnd(262144L);
          return copyPackage;
        }
        boolean isIncrementalPath =
            IncrementalManager.isIncrementalPath(installRequest.getCodeFile().getAbsolutePath());
        File file = new File(installRequest.getCodeFile(), "lib");
        NativeLibraryHelper.Handle handle = null;
        try {
          try {
            handle = NativeLibraryHelper.Handle.create(installRequest.getCodeFile());
            copyPackage =
                NativeLibraryHelper.copyNativeBinariesWithOverride(
                    handle, file, installRequest.getAbiOverride(), isIncrementalPath);
            if (copyPackage != 1) {
              installRequest.setError(copyPackage, "Failed to copy native libraries");
            }
          } catch (IOException e) {
            Slog.e("PackageManager", "Copying native libraries failed", e);
            installRequest.setError(
                PackageManagerException.ofInternalError("Copying native libraries failed", -1));
          }
          Trace.traceEnd(262144L);
          return copyPackage;
        } finally {
          IoUtils.closeQuietly(handle);
        }
      } catch (IOException e2) {
        Slog.w("PackageManager", "Failed to create copy file: " + e2);
        installRequest.setError(-4, "Failed to create copy file");
        Trace.traceEnd(262144L);
        return -4;
      }
    } catch (Throwable th) {
      Trace.traceEnd(262144L);
      throw th;
    }
  }

  public final int copyApkForMoveInstall(InstallRequest installRequest) {
    synchronized (this.mPm.mInstallLock) {
      try {
        Installer installer = this.mPm.mInstaller;
        MoveInfo moveInfo = this.mMoveInfo;
        installer.moveCompleteApp(
            moveInfo.mFromUuid,
            moveInfo.mToUuid,
            moveInfo.mPackageName,
            moveInfo.mAppId,
            moveInfo.mSeInfo,
            moveInfo.mTargetSdkVersion,
            moveInfo.mFromCodePath);
      } catch (Installer.InstallerException e) {
        installRequest.setError(PackageManagerException.ofInternalError("Failed to move app", -2));
        Slog.w("PackageManager", "Failed to move app", e);
        return -110;
      }
    }
    installRequest.setCodeFile(
        new File(
            Environment.getDataAppDirectory(this.mMoveInfo.mToUuid),
            new File(this.mMoveInfo.mFromCodePath).getName()));
    return 1;
  }

  public final int copyApkForAsecInstall(InstallRequest installRequest) {
    String str;
    OriginInfo originInfo = this.mOriginInfo;
    if (originInfo.mStaged && (str = originInfo.mCid) != null) {
      AsecInstallHelper.setMountPath(PackageHelperExt.getSdDir(str), installRequest);
      return 1;
    }
    String allocateExternalStageCidLegacy =
        this.mPm.mInstallerService.allocateExternalStageCidLegacy();
    boolean z =
        installRequest.getInstallerPackageName() == null
            && AsecInstallHelper.isPreloadApp(
                installRequest.getOriginInfo().mFile.getAbsolutePath());
    if (z) {
      try {
        Os.chmod(installRequest.getOriginInfo().mFile.getAbsolutePath(), 509);
      } catch (ErrnoException e) {
        installRequest.setError(PackageManagerException.ofInternalError("Failed to move app", -2));
        Slog.w("PackageManager", "Failed to move app", e);
        return -110;
      }
    }
    int copyPackage =
        this.mPm.getAsecInstallHelper().copyPackage(installRequest, allocateExternalStageCidLegacy);
    if (z) {
      try {
        Os.chmod(installRequest.getOriginInfo().mFile.getAbsolutePath(), 505);
      } catch (ErrnoException unused) {
      }
    }
    return copyPackage;
  }

  public final int fixUpInstallReason(String str, int i, int i2) {
    if (this.mPm.snapshotComputer().checkUidPermission("android.permission.INSTALL_PACKAGES", i)
        == 0) {
      return i2;
    }
    String deviceOwnerOrProfileOwnerPackage =
        this.mPm.mProtectedPackages.getDeviceOwnerOrProfileOwnerPackage(UserHandle.getUserId(i));
    if (deviceOwnerOrProfileOwnerPackage != null && deviceOwnerOrProfileOwnerPackage.equals(str)) {
      return 1;
    }
    if (i2 == 1) {
      return 0;
    }
    return i2;
  }

  public void installStage() {
    String str;
    setTraceMethod("installStage").setTraceCookie(System.identityHashCode(this));
    Trace.asyncTraceBegin(262144L, "installStage", System.identityHashCode(this));
    Trace.asyncTraceBegin(262144L, "queueInstall", System.identityHashCode(this));
    StringBuilder sb = new StringBuilder();
    sb.append("START INSTALL PACKAGE: observer{");
    IPackageInstallObserver2 iPackageInstallObserver2 = this.mObserver;
    sb.append(
        iPackageInstallObserver2 != null
            ? Integer.valueOf(iPackageInstallObserver2.hashCode())
            : "null");
    sb.append("}\n          stagedDir{");
    sb.append(this.mOriginInfo.mFile);
    sb.append("}\n");
    if (!PMRune.PM_INSTALL_TO_SDCARD || this.mOriginInfo.mCid == null) {
      str = "";
    } else {
      str = "          stagedCid{" + this.mOriginInfo.mCid + "}\n";
    }
    sb.append(str);
    sb.append("          pkg{");
    PackageLite packageLite = this.mPackageLite;
    sb.append(packageLite != null ? packageLite.getPackageName() : "null");
    sb.append("}\n          versionCode{");
    PackageLite packageLite2 = this.mPackageLite;
    sb.append(packageLite2 != null ? Integer.valueOf(packageLite2.getVersionCode()) : "null");
    sb.append("}\n          Request from{");
    sb.append(this.mInstallSource.mInstallerPackageName);
    sb.append("}");
    PmLog.logDebugInfoAndLogcat(sb.toString());
    this.mPm.mHandler.postDelayed(
        new Runnable() { // from class:
          // com.android.server.pm.InstallingSession$$ExternalSyntheticLambda5
          @Override // java.lang.Runnable
          public final void run() {
            InstallingSession.this.lambda$installStage$1();
          }
        },
        ThermalInstallThrottlingUtils.getInstallDelayByThermal(this.mDataLoaderType));
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$installStage$1() {
    new InstallingSession$$ExternalSyntheticLambda0(this).run();
  }

  public void installStage(List list) {
    final MultiPackageInstallingSession multiPackageInstallingSession =
        new MultiPackageInstallingSession(getUser(), list, this.mPm);
    setTraceMethod("installStageMultiPackage")
        .setTraceCookie(System.identityHashCode(multiPackageInstallingSession));
    Trace.asyncTraceBegin(
        262144L,
        "installStageMultiPackage",
        System.identityHashCode(multiPackageInstallingSession));
    Trace.asyncTraceBegin(
        262144L, "queueInstall", System.identityHashCode(multiPackageInstallingSession));
    StringBuilder sb = new StringBuilder("START INSTALL MULTI PACKAGE:\n");
    for (InstallingSession installingSession :
        multiPackageInstallingSession.mChildInstallingSessions) {
      StringBuilder sb2 = new StringBuilder();
      sb2.append("          observer{");
      IPackageInstallObserver2 iPackageInstallObserver2 = installingSession.mObserver;
      Object obj = "null";
      sb2.append(
          iPackageInstallObserver2 != null
              ? Integer.valueOf(iPackageInstallObserver2.hashCode())
              : "null");
      sb2.append("}\n");
      sb.append(sb2.toString());
      sb.append("          stagedDir{" + installingSession.mOriginInfo.mFile + "}\n");
      StringBuilder sb3 = new StringBuilder();
      sb3.append("          versionCode{");
      PackageLite packageLite = installingSession.mPackageLite;
      if (packageLite != null) {
        obj = Integer.valueOf(packageLite.getVersionCode());
      }
      sb3.append(obj);
      sb3.append("}\n");
      sb.append(sb3.toString());
      sb.append(
          "          Request from{"
              + installingSession.mInstallSource.mInstallerPackageName
              + "}\n");
    }
    Iterator it = list.iterator();
    while (it.hasNext()) {
      sb.append(
          "          pkg{" + ((InstallingSession) it.next()).mPackageLite.getPackageName() + "}\n");
    }
    PmLog.logDebugInfoAndLogcat(sb.toString());
    this.mPm.mHandler.postDelayed(
        new Runnable() { // from class:
          // com.android.server.pm.InstallingSession$$ExternalSyntheticLambda4
          @Override // java.lang.Runnable
          public final void run() {
            InstallingSession.lambda$installStage$2(
                InstallingSession.MultiPackageInstallingSession.this);
          }
        },
        ThermalInstallThrottlingUtils.getInstallDelayByThermal(this.mDataLoaderType));
  }

  public static /* synthetic */ void lambda$installStage$2(
      final MultiPackageInstallingSession multiPackageInstallingSession) {
    Objects.requireNonNull(multiPackageInstallingSession);
    new Runnable() { // from class:
      // com.android.server.pm.InstallingSession$$ExternalSyntheticLambda6
      @Override // java.lang.Runnable
      public final void run() {
        InstallingSession.MultiPackageInstallingSession.this.start();
      }
    }.run();
  }

  public void movePackage() {
    setTraceMethod("movePackage").setTraceCookie(System.identityHashCode(this));
    Trace.asyncTraceBegin(262144L, "movePackage", System.identityHashCode(this));
    Trace.asyncTraceBegin(262144L, "queueInstall", System.identityHashCode(this));
    this.mPm.mHandler.post(new InstallingSession$$ExternalSyntheticLambda0(this));
  }

  public UserHandle getUser() {
    return this.mUser;
  }

  public final void start() {
    Trace.asyncTraceEnd(262144L, "queueInstall", System.identityHashCode(this));
    Trace.traceBegin(262144L, "startInstall");
    InstallRequest installRequest = new InstallRequest(this);
    handleStartCopy(installRequest);
    handleReturnCode(installRequest);
    Trace.traceEnd(262144L);
  }

  public final InstallingSession setTraceMethod(String str) {
    this.mTraceMethod = str;
    return this;
  }

  public final void setTraceCookie(int i) {
    this.mTraceCookie = i;
  }

  public final void processInstallRequests(boolean z, List list) {
    final ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    Iterator it = list.iterator();
    while (it.hasNext()) {
      InstallRequest installRequest = (InstallRequest) it.next();
      if ((installRequest.getInstallFlags() & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES)
          != 0) {
        arrayList.add(installRequest);
      } else {
        arrayList2.add(installRequest);
      }
    }
    if (!arrayList.isEmpty() && !arrayList2.isEmpty()) {
      throw new IllegalStateException(
          "Attempted to do a multi package install of both APEXes and APKs");
    }
    if (arrayList.isEmpty()) {
      processApkInstallRequests(z, list);
    } else if (z) {
      new Thread(
              new Runnable() { // from class:
                // com.android.server.pm.InstallingSession$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                  InstallingSession.this.lambda$processInstallRequests$3(arrayList);
                }
              },
              "installApexPackages")
          .start();
    } else {
      this.mPm.notifyInstallObserver((InstallRequest) arrayList.get(0));
    }
  }

  public final void processApkInstallRequests(boolean z, List list) {
    if (!z) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        InstallRequest installRequest = (InstallRequest) it.next();
        if (installRequest.getReturnCode() != 1) {
          cleanUpForFailedInstall(installRequest);
        }
      }
    } else {
      Iterator it2 = list.iterator();
      while (it2.hasNext()) {
        InstallRequest installRequest2 = (InstallRequest) it2.next();
        if (AsecInstallHelper.installOnExternalAsec(installRequest2.getInstallFlags())) {
          doAsecPreInstall(installRequest2);
        }
      }
      this.mInstallPackageHelper.installPackagesTraced(list);
      Iterator it3 = list.iterator();
      while (it3.hasNext()) {
        InstallRequest installRequest3 = (InstallRequest) it3.next();
        installRequest3.onInstallCompleted();
        doPostInstall(installRequest3);
      }
    }
    Iterator it4 = list.iterator();
    while (it4.hasNext()) {
      this.mInstallPackageHelper.restoreAndPostInstall((InstallRequest) it4.next());
    }
  }

  public final void doPostInstall(InstallRequest installRequest) {
    if (this.mMoveInfo != null) {
      if (installRequest.getReturnCode() == 1) {
        RemovePackageHelper removePackageHelper = this.mRemovePackageHelper;
        MoveInfo moveInfo = this.mMoveInfo;
        removePackageHelper.cleanUpForMoveInstall(
            moveInfo.mFromUuid, moveInfo.mPackageName, moveInfo.mFromCodePath);
        return;
      } else {
        RemovePackageHelper removePackageHelper2 = this.mRemovePackageHelper;
        MoveInfo moveInfo2 = this.mMoveInfo;
        removePackageHelper2.cleanUpForMoveInstall(
            moveInfo2.mToUuid, moveInfo2.mPackageName, moveInfo2.mFromCodePath);
        return;
      }
    }
    if (AsecInstallHelper.installOnExternalAsec(installRequest.getInstallFlags())) {
      doAsecPostInstall(installRequest);
    } else if (installRequest.getReturnCode() != 1) {
      this.mRemovePackageHelper.removeCodePath(installRequest.getCodeFile());
    }
  }

  public final void doAsecPreInstall(InstallRequest installRequest) {
    String extractCidFromCodePath =
        AsecInstallHelper.extractCidFromCodePath(installRequest.getCodePath());
    if (installRequest.getReturnCode() != 1) {
      PackageHelperExt.destroySdDir(extractCidFromCodePath);
      return;
    }
    if (PackageHelperExt.isContainerMounted(extractCidFromCodePath)) {
      return;
    }
    String mountSdDir =
        PackageHelperExt.mountSdDir(
            extractCidFromCodePath, AsecInstallHelper.getEncryptKey(), 1000);
    if (mountSdDir != null) {
      AsecInstallHelper.setMountPath(mountSdDir, installRequest);
    } else {
      installRequest.setError(-18, "Error while mounting SdDir");
    }
  }

  public final void doAsecPostInstall(InstallRequest installRequest) {
    String extractCidFromCodePath =
        AsecInstallHelper.extractCidFromCodePath(installRequest.getCodePath());
    if (installRequest.getReturnCode() != 1) {
      PackageHelperExt.destroySdDir(extractCidFromCodePath);
      return;
    }
    if (this.mPm.getAsecInstallHelper().shouldAddDexOptOnAsec()) {
      if (!PackageHelperExt.isContainerMounted(extractCidFromCodePath)) {
        Slog.i(
            "PackageManager",
            "Mounting container to get the Occupied size" + extractCidFromCodePath);
        PackageHelperExt.mountSdDir(
            extractCidFromCodePath, AsecInstallHelper.getEncryptKey(), 1000);
      }
      int usedSpaceSecureContainer =
          PackageHelperExt.getUsedSpaceSecureContainer(extractCidFromCodePath);
      if (usedSpaceSecureContainer >= 0) {
        if (!PackageHelperExt.unMountSdDir(extractCidFromCodePath, true)) {
          String str = "Failed to unmount " + extractCidFromCodePath + " before trimming";
          Slog.e("PackageManager", str);
          PackageHelperExt.destroySdDir(extractCidFromCodePath);
          installRequest.setError(-18, str);
          return;
        }
        PackageHelperExt.trimSecureContainer(
            extractCidFromCodePath,
            (int) ((usedSpaceSecureContainer * 1.03d) + 1.0d),
            AsecInstallHelper.getEncryptKey());
      }
      if (!PackageHelperExt.isContainerMounted(extractCidFromCodePath)) {
        Slog.i("PackageManager", "Mounting container before fixPerms" + extractCidFromCodePath);
        PackageHelperExt.mountSdDir(
            extractCidFromCodePath, AsecInstallHelper.getEncryptKey(), 1000);
      }
      if (installRequest.getAppId() < 10000
          || !PackageHelperExt.fixSdPermissions(extractCidFromCodePath, -1, (String) null)) {
        String str2 = "Failed to finalize " + extractCidFromCodePath;
        Slog.e("PackageManager", str2);
        PackageHelperExt.destroySdDir(extractCidFromCodePath);
        installRequest.setError(-18, str2);
        return;
      }
    }
    if (PackageHelperExt.isContainerMounted(extractCidFromCodePath)) {
      return;
    }
    PackageHelperExt.mountSdDir(
        extractCidFromCodePath, AsecInstallHelper.getEncryptKey(), Process.myUid());
  }

  public final void cleanUpForFailedInstall(InstallRequest installRequest) {
    if (installRequest.isInstallMove()) {
      this.mRemovePackageHelper.cleanUpForMoveInstall(
          installRequest.getMoveToUuid(),
          installRequest.getMovePackageName(),
          installRequest.getMoveFromCodePath());
      return;
    }
    if (AsecInstallHelper.installOnExternalAsec(this.mInstallFlags)) {
      String extractCidFromCodePath =
          AsecInstallHelper.extractCidFromCodePath(installRequest.getCodePath());
      if (extractCidFromCodePath != null) {
        PackageHelperExt.destroySdDir(extractCidFromCodePath);
        return;
      }
      return;
    }
    this.mRemovePackageHelper.removeCodePath(installRequest.getCodeFile());
  }

  /* renamed from: installApexPackagesTraced, reason: merged with bridge method [inline-methods] */
  public final void lambda$processInstallRequests$3(List list) {
    try {
      Trace.traceBegin(262144L, "installApexPackages");
      installApexPackages(list);
    } finally {
      Trace.traceEnd(262144L);
    }
  }

  public final void installApexPackages(final List list) {
    if (list.isEmpty()) {
      return;
    }
    if (list.size() != 1) {
      throw new IllegalStateException("Only a non-staged install of a single APEX is supported");
    }
    InstallRequest installRequest = (InstallRequest) list.get(0);
    try {
      File file = installRequest.getOriginInfo().mResolvedFile;
      File[] listFiles = file.listFiles();
      if (listFiles == null) {
        throw PackageManagerException.ofInternalError(
            file.getAbsolutePath() + " is not a directory", -36);
      }
      if (listFiles.length != 1) {
        throw PackageManagerException.ofInternalError(
            "Expected exactly one .apex file under "
                + file.getAbsolutePath()
                + " got: "
                + listFiles.length,
            -37);
      }
      PackageParser2 scanningPackageParser = this.mPm.mInjector.getScanningPackageParser();
      try {
        ApexInfo installPackage = this.mPm.mApexManager.installPackage(listFiles[0]);
        installRequest.setApexInfo(installPackage);
        installRequest.setApexModuleName(installPackage.moduleName);
        this.mPm.mHandler.post(
            new Runnable() { // from class:
              // com.android.server.pm.InstallingSession$$ExternalSyntheticLambda3
              @Override // java.lang.Runnable
              public final void run() {
                InstallingSession.this.lambda$installApexPackages$4(list);
              }
            });
        if (scanningPackageParser != null) {
          scanningPackageParser.close();
        }
      } finally {
      }
    } catch (PackageManagerException e) {
      installRequest.setError("APEX installation failed", e);
      PackageManagerService.invalidatePackageInfoCache();
      this.mPm.notifyInstallObserver(installRequest);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$installApexPackages$4(List list) {
    processApkInstallRequests(true, list);
  }

  public class MultiPackageInstallingSession {
    public final List mChildInstallingSessions;
    public final Set mCurrentInstallRequests;
    public final PackageManagerService mPm;
    public final UserHandle mUser;

    public MultiPackageInstallingSession(
        UserHandle userHandle, List list, PackageManagerService packageManagerService) {
      if (list.size() == 0) {
        throw PackageManagerException.ofInternalError("No child sessions found!", -20);
      }
      this.mPm = packageManagerService;
      this.mUser = userHandle;
      this.mChildInstallingSessions = list;
      for (int i = 0; i < list.size(); i++) {
        ((InstallingSession) list.get(i)).mParentInstallingSession = this;
      }
      this.mCurrentInstallRequests = new ArraySet(this.mChildInstallingSessions.size());
    }

    public void start() {
      Trace.asyncTraceEnd(262144L, "queueInstall", System.identityHashCode(this));
      Trace.traceBegin(262144L, "start");
      int size = this.mChildInstallingSessions.size();
      ArrayList arrayList = new ArrayList(size);
      for (int i = 0; i < size; i++) {
        InstallingSession installingSession =
            (InstallingSession) this.mChildInstallingSessions.get(i);
        InstallRequest installRequest = new InstallRequest(installingSession);
        arrayList.add(installRequest);
        installingSession.handleStartCopy(installRequest);
      }
      for (int i2 = 0; i2 < size; i2++) {
        ((InstallingSession) this.mChildInstallingSessions.get(i2))
            .handleReturnCode((InstallRequest) arrayList.get(i2));
      }
      Trace.traceEnd(262144L);
    }

    public void tryProcessInstallRequest(InstallRequest installRequest) {
      final int i;
      this.mCurrentInstallRequests.add(installRequest);
      if (this.mCurrentInstallRequests.size() != this.mChildInstallingSessions.size()) {
        return;
      }
      Iterator it = this.mCurrentInstallRequests.iterator();
      while (true) {
        i = 1;
        if (!it.hasNext()) {
          break;
        }
        InstallRequest installRequest2 = (InstallRequest) it.next();
        if (installRequest2.getReturnCode() == 0) {
          return;
        }
        if (installRequest2.getReturnCode() != 1) {
          i = installRequest2.getReturnCode();
          break;
        }
      }
      final ArrayList arrayList = new ArrayList(this.mCurrentInstallRequests.size());
      for (InstallRequest installRequest3 : this.mCurrentInstallRequests) {
        installRequest3.setReturnCode(i);
        arrayList.add(installRequest3);
      }
      this.mPm.mHandler.post(
          new Runnable() { // from class:
            // com.android.server.pm.InstallingSession$MultiPackageInstallingSession$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              InstallingSession.MultiPackageInstallingSession.this
                  .lambda$tryProcessInstallRequest$0(i, arrayList);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$tryProcessInstallRequest$0(int i, List list) {
      InstallingSession.this.processInstallRequests(i == 1, list);
    }

    public String toString() {
      return "MultiPackageInstallingSession{"
          + Integer.toHexString(System.identityHashCode(this))
          + "}";
    }
  }
}
