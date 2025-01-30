package com.android.server.pm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.InstantAppRequest;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import java.io.IOException;
import java.util.Set;

/* loaded from: classes3.dex */
public final class PackageHandler extends Handler {
  public final InstallPackageHelper mInstallPackageHelper;
  public final PackageManagerService mPm;
  public final RemovePackageHelper mRemovePackageHelper;

  public final int getDefaultIntegrityVerificationResponse() {
    return -1;
  }

  public PackageHandler(Looper looper, PackageManagerService packageManagerService) {
    super(looper);
    this.mPm = packageManagerService;
    this.mInstallPackageHelper = new InstallPackageHelper(packageManagerService);
    this.mRemovePackageHelper = new RemovePackageHelper(packageManagerService);
  }

  @Override // android.os.Handler
  public void handleMessage(Message message) {
    try {
      doHandleMessage(message);
    } finally {
      Process.setThreadPriority(0);
    }
  }

  public void doHandleMessage(Message message) {
    int i = message.what;
    if (i == 1) {
      this.mInstallPackageHelper.sendPendingBroadcasts();
      return;
    }
    if (i == 9) {
      InstallRequest installRequest = (InstallRequest) this.mPm.mRunningInstalls.get(message.arg1);
      if (installRequest != null
          ? this.mPm.isLocaleOptimizedPackage(installRequest.getName())
          : false) {
        this.mPm.updateLocaleOverlaysForPackage(message);
        return;
      } else {
        doHandlePostInstall(message);
        return;
      }
    }
    if (i == 10) {
      doHandlePostInstall(message);
      return;
    }
    if (i == 12) {
      boolean z = message.arg1 == 1;
      if (message.arg2 == 1) {
        Runtime.getRuntime().gc();
      }
      Object obj = message.obj;
      if (obj != null) {
        this.mPm.getAsecInstallHelper().unloadAllContainers((Set) obj);
      }
      if (z) {
        try {
          PackageHelperExt.getStorageManager().finishMediaUpdate();
          return;
        } catch (RemoteException unused) {
          Log.e("PackageManager", "StorageManagerService not running?");
          return;
        }
      }
      return;
    }
    if (i == 13) {
      this.mPm.writeSettings(false);
      return;
    }
    if (i == 15) {
      int i2 = message.arg1;
      PackageVerificationState packageVerificationState =
          (PackageVerificationState) this.mPm.mPendingVerification.get(i2);
      if (packageVerificationState == null) {
        Slog.w(
            "PackageManager",
            "Verification with id "
                + i2
                + " not found. It may be invalid or overridden by integrity verification");
        return;
      }
      if (packageVerificationState.isVerificationComplete()) {
        Slog.w("PackageManager", "Verification with id " + i2 + " already complete.");
        return;
      }
      VerificationUtils.processVerificationResponse(
          i2, packageVerificationState, (PackageVerificationResponse) message.obj, this.mPm);
      return;
    }
    if (i != 16) {
      switch (i) {
        case 19:
          this.mPm.writePackageList(message.arg1);
          break;
        case 20:
          PackageManagerService packageManagerService = this.mPm;
          Context context = packageManagerService.mContext;
          Computer snapshotComputer = packageManagerService.snapshotComputer();
          PackageManagerService packageManagerService2 = this.mPm;
          InstantAppResolver.doInstantAppResolutionPhaseTwo(
              context,
              snapshotComputer,
              packageManagerService2.mUserManager,
              packageManagerService2.mInstantAppResolverConnection,
              (InstantAppRequest) message.obj,
              packageManagerService2.mInstantAppInstallerActivity,
              packageManagerService2.mHandler);
          break;
        case 21:
          int i3 = message.arg1;
          int i4 = message.arg2;
          VerifyingSession verifyingSession =
              (VerifyingSession) this.mPm.mPendingEnableRollback.get(i3);
          if (verifyingSession == null) {
            Slog.w("PackageManager", "Invalid rollback enabled token " + i3 + " received");
            break;
          } else {
            this.mPm.mPendingEnableRollback.remove(i3);
            if (i4 != 1) {
              Uri fromFile = Uri.fromFile(verifyingSession.mOriginInfo.mResolvedFile);
              Slog.w("PackageManager", "Failed to enable rollback for " + fromFile);
              Slog.w("PackageManager", "Continuing with installation of " + fromFile);
            }
            Trace.asyncTraceEnd(262144L, "enable_rollback", i3);
            verifyingSession.handleRollbackEnabled();
            break;
          }
        case 22:
          int i5 = message.arg1;
          int i6 = message.arg2;
          VerifyingSession verifyingSession2 =
              (VerifyingSession) this.mPm.mPendingEnableRollback.get(i5);
          if (verifyingSession2 != null) {
            Uri fromFile2 = Uri.fromFile(verifyingSession2.mOriginInfo.mResolvedFile);
            Slog.w("PackageManager", "Enable rollback timed out for " + fromFile2);
            this.mPm.mPendingEnableRollback.remove(i5);
            Slog.w("PackageManager", "Continuing with installation of " + fromFile2);
            Trace.asyncTraceEnd(262144L, "enable_rollback", i5);
            verifyingSession2.handleRollbackEnabled();
            Intent intent = new Intent("android.intent.action.CANCEL_ENABLE_ROLLBACK");
            intent.putExtra("android.content.pm.extra.ENABLE_ROLLBACK_SESSION_ID", i6);
            intent.addFlags(335544320);
            this.mPm.mContext.sendBroadcastAsUser(
                intent, UserHandle.SYSTEM, "android.permission.PACKAGE_ROLLBACK_AGENT");
            break;
          }
          break;
        case 23:
          InstallArgs installArgs = (InstallArgs) message.obj;
          if (installArgs != null) {
            this.mRemovePackageHelper.cleanUpResources(
                installArgs.mCodeFile, installArgs.mInstructionSets);
            break;
          }
          break;
        case 24:
        case 29:
          String str = (String) message.obj;
          if (str != null) {
            this.mPm.notifyInstallObserver(str, i == 29);
            break;
          }
          break;
        case 25:
          int i7 = message.arg1;
          PackageVerificationState packageVerificationState2 =
              (PackageVerificationState) this.mPm.mPendingVerification.get(i7);
          if (packageVerificationState2 == null) {
            Slog.w(
                "PackageManager",
                "Integrity verification with id "
                    + i7
                    + " not found. It may be invalid or overridden by verifier");
            break;
          } else {
            int intValue = ((Integer) message.obj).intValue();
            VerifyingSession verifyingSession3 = packageVerificationState2.getVerifyingSession();
            Uri fromFile3 = Uri.fromFile(verifyingSession3.mOriginInfo.mResolvedFile);
            packageVerificationState2.setIntegrityVerificationResult(intValue);
            if (intValue == 1) {
              Slog.i("PackageManager", "Integrity check passed for " + fromFile3);
            } else {
              verifyingSession3.setReturnCode(-22, "Integrity check failed for " + fromFile3);
            }
            if (packageVerificationState2.areAllVerificationsComplete()) {
              this.mPm.mPendingVerification.remove(i7);
            }
            Trace.asyncTraceEnd(262144L, "integrity_verification", i7);
            verifyingSession3.handleIntegrityVerificationFinished();
            break;
          }
        case 26:
          int i8 = message.arg1;
          PackageVerificationState packageVerificationState3 =
              (PackageVerificationState) this.mPm.mPendingVerification.get(i8);
          if (packageVerificationState3 != null
              && !packageVerificationState3.isIntegrityVerificationComplete()) {
            VerifyingSession verifyingSession4 = packageVerificationState3.getVerifyingSession();
            Uri fromFile4 = Uri.fromFile(verifyingSession4.mOriginInfo.mResolvedFile);
            String str2 = "Integrity verification timed out for " + fromFile4;
            Slog.i("PackageManager", str2);
            packageVerificationState3.setIntegrityVerificationResult(
                getDefaultIntegrityVerificationResponse());
            if (getDefaultIntegrityVerificationResponse() == 1) {
              Slog.i("PackageManager", "Integrity check times out, continuing with " + fromFile4);
            } else {
              verifyingSession4.setReturnCode(-22, str2);
            }
            if (packageVerificationState3.areAllVerificationsComplete()) {
              this.mPm.mPendingVerification.remove(i8);
            }
            Trace.asyncTraceEnd(262144L, "integrity_verification", i8);
            verifyingSession4.handleIntegrityVerificationFinished();
            break;
          }
          break;
        case 27:
          this.mPm.mDomainVerificationManager.runMessage(message.arg1, message.obj);
          break;
        case 28:
          try {
            this.mPm
                .mInjector
                .getSharedLibrariesImpl()
                .pruneUnusedStaticSharedLibraries(
                    this.mPm.snapshotComputer(),
                    Long.MAX_VALUE,
                    Settings.Global.getLong(
                        this.mPm.mContext.getContentResolver(),
                        "unused_static_shared_lib_min_cache_period",
                        PackageManagerService.DEFAULT_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD));
            break;
          } catch (IOException e) {
            Log.w(
                "PackageManager",
                "Failed to prune unused static shared libraries :" + e.getMessage());
            return;
          }
      }
      return;
    }
    int i9 = message.arg1;
    boolean z2 = message.arg2 != 0;
    PackageVerificationState packageVerificationState4 =
        (PackageVerificationState) this.mPm.mPendingVerification.get(i9);
    if (packageVerificationState4 == null || packageVerificationState4.isVerificationComplete()) {
      return;
    }
    PackageVerificationResponse packageVerificationResponse =
        (PackageVerificationResponse) message.obj;
    if (z2 || !packageVerificationState4.timeoutExtended(packageVerificationResponse.callerUid)) {
      VerificationUtils.processVerificationResponseOnTimeout(
          i9, packageVerificationState4, packageVerificationResponse, this.mPm);
    }
  }

  public final void doHandlePostInstall(Message message) {
    InstallRequest installRequest = (InstallRequest) this.mPm.mRunningInstalls.get(message.arg1);
    boolean z = message.arg2 != 0;
    this.mPm.mRunningInstalls.delete(message.arg1);
    if (installRequest == null) {
      Log.w("PackageManager", "InstallRequest is null for " + message.what + " " + message.arg1);
      return;
    }
    installRequest.closeFreezer();
    installRequest.runPostInstallRunnable();
    if (!installRequest.isInstallExistingForUser()) {
      this.mInstallPackageHelper.handlePackagePostInstall(installRequest, z);
    }
    Trace.asyncTraceEnd(262144L, "postInstall", message.arg1);
  }
}
