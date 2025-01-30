package com.android.server.pm;

import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.DataLoaderParams;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.PackageInfoLite;
import android.content.pm.PackageInstaller;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.SigningDetails;
import android.content.pm.VerifierInfo;
import android.content.pm.parsing.PackageLite;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.incremental.IncrementalManager;
import android.p005os.IInstalld;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Slog;
import com.android.server.DeviceIdleInternal;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.knox.ContainerDependencyWrapper;
import com.samsung.android.knox.container.IKnoxContainerManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class VerifyingSession {
  public final int mDataLoaderType;
  public final int mInstallFlags;
  public final InstallPackageHelper mInstallPackageHelper;
  public final InstallSource mInstallSource;
  public final boolean mIsInherit;
  public final boolean mIsStaged;
  public final IPackageInstallObserver2 mObserver;
  public final OriginInfo mOriginInfo;
  public final String mPackageAbiOverride;
  public final PackageLite mPackageLite;
  public MultiPackageVerifyingSession mParentVerifyingSession;
  public final PackageManagerService mPm;
  public final long mRequiredInstalledVersionCode;
  public final int mSessionId;
  public final SigningDetails mSigningDetails;
  public final UserHandle mUser;
  public final boolean mUserActionRequired;
  public final int mUserActionRequiredType;
  public final VerificationInfo mVerificationInfo;
  public boolean mWaitForEnableRollbackToComplete;
  public boolean mWaitForIntegrityVerificationToComplete;
  public boolean mWaitForVerificationToComplete;
  public int sessionFlags;
  public int mRet = 1;
  public String mErrorMessage = null;

  public final boolean isIntegrityVerificationEnabled() {
    return true;
  }

  public VerifyingSession(
      UserHandle userHandle,
      File file,
      String str,
      IPackageInstallObserver2 iPackageInstallObserver2,
      PackageInstaller.SessionParams sessionParams,
      InstallSource installSource,
      int i,
      SigningDetails signingDetails,
      int i2,
      PackageLite packageLite,
      boolean z,
      PackageManagerService packageManagerService) {
    this.sessionFlags = 0;
    this.mPm = packageManagerService;
    this.mUser = userHandle;
    this.mInstallPackageHelper = new InstallPackageHelper(packageManagerService);
    if (file != null) {
      this.mOriginInfo = OriginInfo.fromStagedFile(file);
    } else {
      this.mOriginInfo = OriginInfo.fromStagedContainer(str);
    }
    this.mObserver = iPackageInstallObserver2;
    this.mInstallFlags = sessionParams.installFlags;
    this.mInstallSource = installSource;
    this.mPackageAbiOverride = sessionParams.abiOverride;
    this.mVerificationInfo =
        new VerificationInfo(
            sessionParams.originatingUri,
            sessionParams.referrerUri,
            sessionParams.originatingUid,
            i);
    this.mSigningDetails = signingDetails;
    this.mRequiredInstalledVersionCode = sessionParams.requiredInstalledVersionCode;
    DataLoaderParams dataLoaderParams = sessionParams.dataLoaderParams;
    this.mDataLoaderType = dataLoaderParams != null ? dataLoaderParams.getType() : 0;
    this.mSessionId = i2;
    this.sessionFlags = sessionParams.sessionFlags;
    this.mPackageLite = packageLite;
    this.mUserActionRequired = z;
    this.mUserActionRequiredType = sessionParams.requireUserAction;
    this.mIsInherit = sessionParams.mode == 2;
    this.mIsStaged = sessionParams.isStaged;
  }

  public String toString() {
    return "VerifyingSession{"
        + Integer.toHexString(System.identityHashCode(this))
        + " file="
        + this.mOriginInfo.mFile
        + "}";
  }

  public void handleStartVerify() {
    PackageInfoLite minimalPackageInfo =
        PackageManagerServiceUtils.getMinimalPackageInfo(
            this.mPm.mContext,
            this.mPackageLite,
            this.mOriginInfo.mResolvedPath,
            this.mInstallFlags,
            this.mPackageAbiOverride);
    Pair verifyReplacingVersionCode =
        this.mInstallPackageHelper.verifyReplacingVersionCode(
            minimalPackageInfo, this.mRequiredInstalledVersionCode, this.mInstallFlags);
    setReturnCode(
        ((Integer) verifyReplacingVersionCode.first).intValue(),
        (String) verifyReplacingVersionCode.second);
    if (this.mRet == 1 && !this.mOriginInfo.mExisting) {
      if (!isApex()) {
        sendApkVerificationRequest(minimalPackageInfo);
      }
      if ((this.mInstallFlags & 262144) != 0) {
        sendEnableRollbackRequest();
      }
    }
  }

  public final void sendApkVerificationRequest(PackageInfoLite packageInfoLite) {
    PackageManagerService packageManagerService = this.mPm;
    int i = packageManagerService.mPendingVerificationToken;
    packageManagerService.mPendingVerificationToken = i + 1;
    PackageVerificationState packageVerificationState = new PackageVerificationState(this);
    this.mPm.mPendingVerification.append(i, packageVerificationState);
    sendIntegrityVerificationRequest(i, packageInfoLite, packageVerificationState);
    sendPackageVerificationRequest(i, packageInfoLite, packageVerificationState);
    if (packageVerificationState.areAllVerificationsComplete()) {
      this.mPm.mPendingVerification.remove(i);
    }
    UserHandle user = getUser();
    if (user == UserHandle.ALL) {
      user = UserHandle.SYSTEM;
    }
    try {
      Intent intent = new Intent("com.samsung.android.intent.action.PACKAGE_INSTALL_STARTED");
      intent.putExtra("packageName", packageInfoLite.packageName);
      intent.putExtra("userID", user.getIdentifier());
      this.mPm.mContext.sendBroadcastAsUser(
          intent, UserHandle.CURRENT, "android.permission.HARDWARE_TEST");
      Slog.d("PackageManager", "sendBroadcastAsUser. PACKAGE_INSTALL_STARTED");
    } catch (IllegalStateException e) {
      Slog.w("PackageManager", "Failed to send an intent for PACKAGE_INSTALL_STARTED: ", e);
    }
  }

  public void sendEnableRollbackRequest() {
    PackageManagerService packageManagerService = this.mPm;
    int i = packageManagerService.mPendingEnableRollbackToken;
    packageManagerService.mPendingEnableRollbackToken = i + 1;
    Trace.asyncTraceBegin(262144L, "enable_rollback", i);
    this.mPm.mPendingEnableRollback.append(i, this);
    Intent intent = new Intent("android.intent.action.PACKAGE_ENABLE_ROLLBACK");
    intent.putExtra("android.content.pm.extra.ENABLE_ROLLBACK_TOKEN", i);
    intent.putExtra("android.content.pm.extra.ENABLE_ROLLBACK_SESSION_ID", this.mSessionId);
    intent.setType("application/vnd.android.package-archive");
    intent.addFlags(268435457);
    intent.addFlags(67108864);
    this.mPm.mContext.sendBroadcastAsUser(
        intent, UserHandle.SYSTEM, "android.permission.PACKAGE_ROLLBACK_AGENT");
    this.mWaitForEnableRollbackToComplete = true;
    long j = DeviceConfig.getLong("rollback", "enable_rollback_timeout", 10000L);
    long j2 = j >= 0 ? j : 10000L;
    Message obtainMessage = this.mPm.mHandler.obtainMessage(22);
    obtainMessage.arg1 = i;
    obtainMessage.arg2 = this.mSessionId;
    this.mPm.mHandler.sendMessageDelayed(obtainMessage, j2);
  }

  public void sendIntegrityVerificationRequest(
      final int i,
      PackageInfoLite packageInfoLite,
      PackageVerificationState packageVerificationState) {
    if (!isIntegrityVerificationEnabled()) {
      packageVerificationState.setIntegrityVerificationResult(1);
      return;
    }
    InstallSource installSource = this.mInstallSource;
    if (installSource != null
        && this.mVerificationInfo != null
        && "PrePackageInstaller".equals(installSource.mInitiatingPackageName)
        && this.mVerificationInfo.mInstallerUid == 1000) {
      packageVerificationState.setIntegrityVerificationResult(1);
      return;
    }
    Intent intent = new Intent("android.intent.action.PACKAGE_NEEDS_INTEGRITY_VERIFICATION");
    intent.setDataAndType(
        Uri.fromFile(new File(this.mOriginInfo.mResolvedPath)),
        "application/vnd.android.package-archive");
    intent.addFlags(1342177281);
    intent.putExtra("android.content.pm.extra.VERIFICATION_ID", i);
    intent.putExtra("android.intent.extra.PACKAGE_NAME", packageInfoLite.packageName);
    intent.putExtra("android.intent.extra.VERSION_CODE", packageInfoLite.versionCode);
    intent.putExtra("android.intent.extra.LONG_VERSION_CODE", packageInfoLite.getLongVersionCode());
    populateInstallerExtras(intent);
    intent.setPackage("android");
    this.mPm.mContext.sendOrderedBroadcastAsUser(
        intent,
        UserHandle.SYSTEM,
        null,
        -1,
        BroadcastOptions.makeBasic().toBundle(),
        new BroadcastReceiver() { // from class: com.android.server.pm.VerifyingSession.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context, Intent intent2) {
            Message obtainMessage = VerifyingSession.this.mPm.mHandler.obtainMessage(26);
            obtainMessage.arg1 = i;
            VerifyingSession.this.mPm.mHandler.sendMessageDelayed(
                obtainMessage, VerifyingSession.this.getIntegrityVerificationTimeout());
          }
        },
        null,
        0,
        null,
        null);
    Trace.asyncTraceBegin(262144L, "integrity_verification", i);
    this.mWaitForIntegrityVerificationToComplete = true;
  }

  public final long getIntegrityVerificationTimeout() {
    return Math.max(
        Settings.Global.getLong(
            this.mPm.mContext.getContentResolver(), "app_integrity_verification_timeout", 30000L),
        30000L);
  }

  /* JADX WARN: Removed duplicated region for block: B:95:0x033c  */
  /* JADX WARN: Removed duplicated region for block: B:98:0x035d  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void sendPackageVerificationRequest(
      final int i,
      PackageInfoLite packageInfoLite,
      PackageVerificationState packageVerificationState) {
    ArrayList<String> arrayList;
    boolean z;
    String str;
    int i2;
    String str2;
    boolean z2;
    long j;
    String str3;
    ArrayList arrayList2;
    String str4;
    String str5;
    int i3;
    Object obj;
    String str6;
    Intent intent;
    String str7;
    String str8;
    String str9;
    String str10;
    String str11;
    String str12;
    boolean z3;
    long j2;
    PackageVerificationResponse packageVerificationResponse;
    UserHandle user = getUser();
    if (user == UserHandle.ALL) {
      user = UserHandle.of(this.mPm.mUserManager.getCurrentUserId());
    }
    UserHandle userHandle = user;
    int identifier = userHandle.getIdentifier();
    ArrayList arrayList3 = new ArrayList(Arrays.asList(this.mPm.mRequiredVerifierPackages));
    int i4 = this.mInstallFlags;
    if ((i4 & 32) != 0 && (i4 & 524288) == 0) {
      String str13 = SystemProperties.get("debug.pm.adb_verifier_override_packages", "");
      if (!TextUtils.isEmpty(str13)) {
        String[] split = str13.split(KnoxVpnFirewallHelper.DELIMITER);
        ArrayList arrayList4 = new ArrayList();
        for (String str14 : split) {
          if (!TextUtils.isEmpty(str14) && packageExists(str14)) {
            arrayList4.add(str14);
          }
        }
        if (arrayList4.size() > 0 && !isAdbVerificationEnabled(packageInfoLite, identifier, true)) {
          arrayList = arrayList4;
          z = true;
          if (!this.mOriginInfo.mExisting
              || !isVerificationEnabled(packageInfoLite, identifier, arrayList)) {
            packageVerificationState.passRequiredVerification();
          }
          Computer snapshotComputer = this.mPm.snapshotComputer();
          int size = arrayList.size() - 1;
          while (true) {
            str = "PackageManager";
            if (size < 0) {
              break;
            }
            if (!snapshotComputer.isApplicationEffectivelyEnabled(
                (String) arrayList.get(size), userHandle)) {
              Slog.w(
                  "PackageManager",
                  "Required verifier: " + ((String) arrayList.get(size)) + " is disabled");
              arrayList.remove(size);
            }
            size--;
          }
          Iterator it = arrayList.iterator();
          while (true) {
            i2 = identifier;
            if (!it.hasNext()) {
              break;
            }
            packageVerificationState.addRequiredVerifierUid(
                snapshotComputer.getPackageUid((String) it.next(), 268435456L, i2));
            identifier = i2;
          }
          int i5 = i2;
          String str15 = "android.intent.action.PACKAGE_NEEDS_VERIFICATION";
          Intent intent2 = new Intent("android.intent.action.PACKAGE_NEEDS_VERIFICATION");
          intent2.addFlags(268435456);
          String str16 = "application/vnd.android.package-archive";
          intent2.setDataAndType(
              Uri.fromFile(new File(this.mOriginInfo.mResolvedPath)),
              "application/vnd.android.package-archive");
          intent2.addFlags(1);
          ParceledListSlice queryIntentReceivers =
              this.mPm.queryIntentReceivers(
                  snapshotComputer, intent2, "application/vnd.android.package-archive", 0L, i5);
          intent2.putExtra("android.content.pm.extra.VERIFICATION_ID", i);
          intent2.putExtra(
              "android.content.pm.extra.VERIFICATION_INSTALL_FLAGS", this.mInstallFlags);
          intent2.putExtra(
              "android.content.pm.extra.VERIFICATION_PACKAGE_NAME", packageInfoLite.packageName);
          intent2.putExtra(
              "android.content.pm.extra.VERIFICATION_VERSION_CODE", packageInfoLite.versionCode);
          intent2.putExtra(
              "android.content.pm.extra.VERIFICATION_LONG_VERSION_CODE",
              packageInfoLite.getLongVersionCode());
          String baseApkPath = this.mPackageLite.getBaseApkPath();
          String[] splitApkPaths = this.mPackageLite.getSplitApkPaths();
          String str17 = "android.content.pm.extra.VERIFICATION_ID";
          if (IncrementalManager.isIncrementalPath(baseApkPath)) {
            String buildVerificationRootHashString =
                PackageManagerServiceUtils.buildVerificationRootHashString(
                    baseApkPath, splitApkPaths);
            intent2.putExtra(
                "android.content.pm.extra.VERIFICATION_ROOT_HASH", buildVerificationRootHashString);
            str2 = buildVerificationRootHashString;
          } else {
            str2 = null;
          }
          intent2.putExtra("android.content.pm.extra.DATA_LOADER_TYPE", this.mDataLoaderType);
          String str18 = "android.content.pm.extra.SESSION_ID";
          intent2.putExtra("android.content.pm.extra.SESSION_ID", this.mSessionId);
          String str19 = "android.content.pm.extra.VERIFICATION_ROOT_HASH";
          intent2.putExtra(
              "android.content.pm.extra.USER_ACTION_REQUIRED", this.mUserActionRequired);
          populateInstallerExtras(intent2);
          boolean z4 =
              this.mDataLoaderType == 2
                  && this.mSigningDetails.getSignatureSchemeVersion() == 4
                  && getDefaultVerificationResponse() == 1;
          long verificationTimeout =
              VerificationUtils.getVerificationTimeout(this.mPm.mContext, z4);
          String str20 = "android.content.pm.extra.DATA_LOADER_TYPE";
          String str21 = str2;
          long verificationTimeoutSamsung =
              VerificationUtils.getVerificationTimeoutSamsung(this.mPm.mContext, z4);
          if (verificationTimeoutSamsung > verificationTimeout) {
            StringBuilder sb = new StringBuilder();
            z2 = z4;
            sb.append("Samsung verification timeout applied. Set verification timeout to ");
            sb.append(verificationTimeoutSamsung);
            Slog.i("PackageManager", sb.toString());
            j = verificationTimeoutSamsung;
          } else {
            z2 = z4;
            j = verificationTimeout;
          }
          List matchVerifiers =
              matchVerifiers(
                  packageInfoLite, queryIntentReceivers.getList(), packageVerificationState);
          if (packageInfoLite.isSdkLibrary) {
            if (matchVerifiers == null) {
              matchVerifiers = new ArrayList();
            }
            matchVerifiers.add(
                new ComponentName(
                    "android", "com.android.server.sdksandbox.SdkSandboxVerifierReceiver"));
            packageVerificationState.addSufficientVerifier(Process.myUid());
          }
          DeviceIdleInternal deviceIdleInternal =
              (DeviceIdleInternal) this.mPm.mInjector.getLocalService(DeviceIdleInternal.class);
          BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
          makeBasic.setTemporaryAppAllowlist(j, 0, 305, "");
          if (matchVerifiers != null) {
            int size2 = matchVerifiers.size();
            if (size2 == 0) {
              Slog.i("PackageManager", "Additional verifiers required, but none installed.");
              setReturnCode(-22, "Additional verifiers required, but none installed.");
            } else {
              int i6 = 0;
              while (i6 < size2) {
                ComponentName componentName = (ComponentName) matchVerifiers.get(i6);
                deviceIdleInternal.addPowerSaveTempWhitelistApp(
                    Process.myUid(),
                    componentName.getPackageName(),
                    j,
                    i5,
                    false,
                    305,
                    "package verifier");
                Intent intent3 = new Intent(intent2);
                intent3.setComponent(componentName);
                this.mPm.mContext.sendBroadcastAsUser(
                    intent3, userHandle, null, makeBasic.toBundle());
                i6++;
                size2 = size2;
              }
            }
          }
          Object obj2 = null;
          if (arrayList.size() == 0) {
            Slog.e("PackageManager", "No required verifiers");
            return;
          }
          int i7 = getDefaultVerificationResponse() == 1 ? 2 : -1;
          for (String str22 : arrayList) {
            int packageUid = snapshotComputer.getPackageUid(str22, 268435456L, i5);
            if (z) {
              arrayList2 = arrayList;
              if (arrayList.size() == 1) {
                str3 = str18;
                str4 = str17;
                str5 = str19;
                obj = null;
                i3 = i;
              } else {
                Intent intent4 = new Intent(str15);
                intent4.addFlags(1);
                intent4.addFlags(268435456);
                intent4.addFlags(32);
                intent4.setDataAndType(
                    Uri.fromFile(new File(this.mOriginInfo.mResolvedPath)), str16);
                intent4.putExtra(str18, this.mSessionId);
                String str23 = str20;
                intent4.putExtra(str23, this.mDataLoaderType);
                str5 = str19;
                String str24 = str21;
                if (str21 != null) {
                  intent4.putExtra(str5, str24);
                }
                intent4.setPackage(str22);
                str21 = str24;
                str3 = str18;
                obj = null;
                i3 = i;
                str20 = str23;
                str4 = str17;
                intent4.putExtra(str4, -i3);
                intent = intent4;
                str6 = null;
                deviceIdleInternal.addPowerSaveTempWhitelistApp(
                    Process.myUid(), str22, j, i5, false, 305, "package verifier");
                PackageVerificationResponse packageVerificationResponse2 =
                    new PackageVerificationResponse(i7, packageUid);
                if (z2) {
                  str7 = str4;
                  str8 = str15;
                  str9 = str16;
                  str10 = str5;
                  str11 = str21;
                  str12 = str20;
                  z3 = z2;
                  j2 = 268435456;
                  packageVerificationResponse = packageVerificationResponse2;
                } else {
                  str7 = str4;
                  str10 = str5;
                  str11 = str21;
                  j2 = 268435456;
                  packageVerificationResponse = packageVerificationResponse2;
                  str12 = str20;
                  z3 = z2;
                  str8 = str15;
                  str9 = str16;
                  startVerificationTimeoutCountdown(i, z2, packageVerificationResponse, j);
                }
                Slog.d(
                    str,
                    "Sending PACKAGE_NEEDS_VERIFICATION to "
                        + str22
                        + ", vid: "
                        + i3
                        + ", sid: "
                        + this.mSessionId);
                final boolean z5 = z3;
                final PackageVerificationResponse packageVerificationResponse3 =
                    packageVerificationResponse;
                final long j3 = j;
                this.mPm.mContext.sendOrderedBroadcastAsUser(
                    intent,
                    userHandle,
                    str6,
                    -1,
                    makeBasic.toBundle(),
                    new BroadcastReceiver() { // from class:
                      // com.android.server.pm.VerifyingSession.2
                      @Override // android.content.BroadcastReceiver
                      public void onReceive(Context context, Intent intent5) {
                        boolean z6 = z5;
                        if (z6) {
                          return;
                        }
                        VerifyingSession.this.startVerificationTimeoutCountdown(
                            i, z6, packageVerificationResponse3, j3);
                      }
                    },
                    null,
                    0,
                    null,
                    null);
                i5 = i5;
                str = str;
                snapshotComputer = snapshotComputer;
                z2 = z3;
                str16 = str9;
                arrayList = arrayList2;
                str15 = str8;
                str20 = str12;
                str18 = str3;
                obj2 = obj;
                i7 = i7;
                userHandle = userHandle;
                intent2 = intent2;
                str19 = str10;
                str21 = str11;
                str17 = str7;
              }
            } else {
              str3 = str18;
              arrayList2 = arrayList;
              str4 = str17;
              str5 = str19;
              i3 = i;
              obj = obj2;
            }
            Intent intent5 = new Intent(intent2);
            if (!z) {
              intent5.setComponent(
                  matchComponentForVerifier(str22, queryIntentReceivers.getList()));
            } else {
              intent5.setPackage(str22);
            }
            intent = intent5;
            str6 = "android.permission.PACKAGE_VERIFICATION_AGENT";
            deviceIdleInternal.addPowerSaveTempWhitelistApp(
                Process.myUid(), str22, j, i5, false, 305, "package verifier");
            PackageVerificationResponse packageVerificationResponse22 =
                new PackageVerificationResponse(i7, packageUid);
            if (z2) {}
            Slog.d(
                str,
                "Sending PACKAGE_NEEDS_VERIFICATION to "
                    + str22
                    + ", vid: "
                    + i3
                    + ", sid: "
                    + this.mSessionId);
            final boolean z52 = z3;
            final PackageVerificationResponse packageVerificationResponse32 =
                packageVerificationResponse;
            final long j32 = j;
            this.mPm.mContext.sendOrderedBroadcastAsUser(
                intent,
                userHandle,
                str6,
                -1,
                makeBasic.toBundle(),
                new BroadcastReceiver() { // from class: com.android.server.pm.VerifyingSession.2
                  @Override // android.content.BroadcastReceiver
                  public void onReceive(Context context, Intent intent52) {
                    boolean z6 = z52;
                    if (z6) {
                      return;
                    }
                    VerifyingSession.this.startVerificationTimeoutCountdown(
                        i, z6, packageVerificationResponse32, j32);
                  }
                },
                null,
                0,
                null,
                null);
            i5 = i5;
            str = str;
            snapshotComputer = snapshotComputer;
            z2 = z3;
            str16 = str9;
            arrayList = arrayList2;
            str15 = str8;
            str20 = str12;
            str18 = str3;
            obj2 = obj;
            i7 = i7;
            userHandle = userHandle;
            intent2 = intent2;
            str19 = str10;
            str21 = str11;
            str17 = str7;
          }
          Trace.asyncTraceBegin(262144L, "verification", i);
          this.mWaitForVerificationToComplete = true;
          return;
        }
      }
    }
    arrayList = arrayList3;
    z = false;
    if (!this.mOriginInfo.mExisting) {}
    packageVerificationState.passRequiredVerification();
  }

  public final void startVerificationTimeoutCountdown(
      int i, boolean z, PackageVerificationResponse packageVerificationResponse, long j) {
    Message obtainMessage = this.mPm.mHandler.obtainMessage(16);
    obtainMessage.arg1 = i;
    obtainMessage.arg2 = z ? 1 : 0;
    obtainMessage.obj = packageVerificationResponse;
    this.mPm.mHandler.sendMessageDelayed(obtainMessage, j);
  }

  public int getDefaultVerificationResponse() {
    if (this.mPm.mUserManager.hasUserRestriction("ensure_verify_apps", getUser().getIdentifier())) {
      return -1;
    }
    return Settings.Global.getInt(
        this.mPm.mContext.getContentResolver(), "verifier_default_response", 1);
  }

  public final boolean packageExists(String str) {
    return this.mPm.snapshotComputer().getPackageStateInternal(str) != null;
  }

  public final boolean isAdbVerificationEnabled(PackageInfoLite packageInfoLite, int i, boolean z) {
    boolean z2 =
        Settings.Global.getInt(
                this.mPm.mContext.getContentResolver(), "verifier_verify_adb_installs", 1)
            != 0;
    if (this.mPm.isUserRestricted(i, "ensure_verify_apps")) {
      if (!z2) {
        Slog.w("PackageManager", "Force verification of ADB install because of user restriction.");
      }
      return true;
    }
    if (!z2) {
      return false;
    }
    if (z && packageExists(packageInfoLite.packageName)) {
      return !packageInfoLite.debuggable;
    }
    return true;
  }

  public final boolean isVerificationEnabled(PackageInfoLite packageInfoLite, int i, List list) {
    ActivityInfo activityInfo;
    List packagesFromInstallWhiteList;
    VerificationInfo verificationInfo = this.mVerificationInfo;
    int i2 = verificationInfo == null ? -1 : verificationInfo.mInstallerUid;
    boolean z = (this.mInstallFlags & 524288) != 0;
    try {
      packagesFromInstallWhiteList =
          IKnoxContainerManager.Stub.asInterface(ServiceManager.getService("mum_container_policy"))
              .getPackagesFromInstallWhiteList(
                  ContainerDependencyWrapper.getContextInfo(
                      ContainerDependencyWrapper.getOwnerUidFromEdm(this.mPm.mContext, i), i));
      Iterator it = packagesFromInstallWhiteList.iterator();
      while (it.hasNext()) {
        Slog.i(
            "PackageManager",
            "isVerificationEnabled :: approvedInstaller : " + ((String) it.next()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (packagesFromInstallWhiteList.contains(".*")) {
      Slog.i("PackageManager", "isVerificationEnabled :: installer policy contains *.");
      if ((this.mInstallFlags & 32) != 0) {
        return isAdbVerificationEnabled(packageInfoLite, i, z);
      }
      if (z) {
        return false;
      }
      if (isInstant() && (activityInfo = this.mPm.mInstantAppInstallerActivity) != null) {
        String str = activityInfo.packageName;
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
          String str2 = (String) it2.next();
          if (str.equals(str2)) {
            try {
              ((AppOpsManager) this.mPm.mInjector.getSystemService(AppOpsManager.class))
                  .checkPackage(i2, str2);
              return false;
            } catch (SecurityException unused) {
              continue;
            }
          }
        }
      }
      return (this.sessionFlags & 33554432) == 0;
    }
    Slog.i("PackageManager", "isVerificationEnabled :: installer policy exits.");
    return false;
  }

  public final List matchVerifiers(
      PackageInfoLite packageInfoLite,
      List list,
      PackageVerificationState packageVerificationState) {
    int uidForVerifier;
    VerifierInfo[] verifierInfoArr = packageInfoLite.verifiers;
    if (verifierInfoArr == null || verifierInfoArr.length == 0) {
      return null;
    }
    int length = verifierInfoArr.length;
    ArrayList arrayList = new ArrayList(length + 1);
    for (int i = 0; i < length; i++) {
      VerifierInfo verifierInfo = packageInfoLite.verifiers[i];
      ComponentName matchComponentForVerifier =
          matchComponentForVerifier(verifierInfo.packageName, list);
      if (matchComponentForVerifier != null
          && (uidForVerifier = this.mInstallPackageHelper.getUidForVerifier(verifierInfo)) != -1) {
        arrayList.add(matchComponentForVerifier);
        packageVerificationState.addSufficientVerifier(uidForVerifier);
      }
    }
    return arrayList;
  }

  public static ComponentName matchComponentForVerifier(String str, List list) {
    ActivityInfo activityInfo;
    int size = list.size();
    int i = 0;
    while (true) {
      if (i >= size) {
        activityInfo = null;
        break;
      }
      ResolveInfo resolveInfo = (ResolveInfo) list.get(i);
      ActivityInfo activityInfo2 = resolveInfo.activityInfo;
      if (activityInfo2 != null && str.equals(activityInfo2.packageName)) {
        activityInfo = resolveInfo.activityInfo;
        break;
      }
      i++;
    }
    if (activityInfo == null) {
      return null;
    }
    return new ComponentName(activityInfo.packageName, activityInfo.name);
  }

  public void populateInstallerExtras(Intent intent) {
    intent.putExtra(
        "android.content.pm.extra.VERIFICATION_INSTALLER_PACKAGE",
        this.mInstallSource.mInitiatingPackageName);
    VerificationInfo verificationInfo = this.mVerificationInfo;
    if (verificationInfo != null) {
      Uri uri = verificationInfo.mOriginatingUri;
      if (uri != null) {
        intent.putExtra("android.intent.extra.ORIGINATING_URI", uri);
      }
      Uri uri2 = this.mVerificationInfo.mReferrer;
      if (uri2 != null) {
        intent.putExtra("android.intent.extra.REFERRER", uri2);
      }
      int i = this.mVerificationInfo.mOriginatingUid;
      if (i >= 0) {
        intent.putExtra("android.intent.extra.ORIGINATING_UID", i);
      }
      int i2 = this.mVerificationInfo.mInstallerUid;
      if (i2 >= 0) {
        intent.putExtra("android.content.pm.extra.VERIFICATION_INSTALLER_UID", i2);
      }
    }
  }

  public void setReturnCode(int i, String str) {
    if (this.mRet == 1) {
      this.mRet = i;
      this.mErrorMessage = str;
    }
  }

  public void handleVerificationFinished() {
    this.mWaitForVerificationToComplete = false;
    handleReturnCode();
  }

  public void handleIntegrityVerificationFinished() {
    this.mWaitForIntegrityVerificationToComplete = false;
    handleReturnCode();
  }

  public void handleRollbackEnabled() {
    this.mWaitForEnableRollbackToComplete = false;
    handleReturnCode();
  }

  public void handleReturnCode() {
    if (this.mWaitForVerificationToComplete
        || this.mWaitForIntegrityVerificationToComplete
        || this.mWaitForEnableRollbackToComplete) {
      return;
    }
    sendVerificationCompleteNotification();
    if (this.mRet != 1) {
      PackageMetrics.onVerificationFailed(this);
    }
  }

  public final void sendVerificationCompleteNotification() {
    MultiPackageVerifyingSession multiPackageVerifyingSession = this.mParentVerifyingSession;
    if (multiPackageVerifyingSession != null) {
      multiPackageVerifyingSession.trySendVerificationCompleteNotification(this);
      return;
    }
    try {
      this.mObserver.onPackageInstalled((String) null, this.mRet, this.mErrorMessage, new Bundle());
    } catch (RemoteException unused) {
      Slog.i("PackageManager", "Observer no longer exists.");
    }
  }

  public final void start() {
    Trace.asyncTraceEnd(262144L, "queueVerify", System.identityHashCode(this));
    Trace.traceBegin(262144L, "start");
    handleStartVerify();
    handleReturnCode();
    Trace.traceEnd(262144L);
  }

  public void verifyStage() {
    Trace.asyncTraceBegin(262144L, "queueVerify", System.identityHashCode(this));
    this.mPm.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.pm.VerifyingSession$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            VerifyingSession.this.start();
          }
        });
  }

  public void verifyStage(List list) {
    final MultiPackageVerifyingSession multiPackageVerifyingSession =
        new MultiPackageVerifyingSession(this, list);
    this.mPm.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.pm.VerifyingSession$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            MultiPackageVerifyingSession.this.start();
          }
        });
  }

  public int getRet() {
    return this.mRet;
  }

  public String getErrorMessage() {
    return this.mErrorMessage;
  }

  public UserHandle getUser() {
    return this.mUser;
  }

  public int getSessionId() {
    return this.mSessionId;
  }

  public int getDataLoaderType() {
    return this.mDataLoaderType;
  }

  public int getUserActionRequiredType() {
    return this.mUserActionRequiredType;
  }

  public boolean isInstant() {
    return (this.mInstallFlags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0;
  }

  public boolean isInherit() {
    return this.mIsInherit;
  }

  public int getInstallerPackageUid() {
    return this.mInstallSource.mInstallerPackageUid;
  }

  public boolean isApex() {
    return (this.mInstallFlags & IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES) != 0;
  }

  public boolean isStaged() {
    return this.mIsStaged;
  }
}
