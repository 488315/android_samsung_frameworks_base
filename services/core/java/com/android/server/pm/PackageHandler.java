package com.android.server.pm;

import android.app.IInstantAppResolver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.InstantAppRequest;
import android.content.pm.InstantAppRequestInfo;
import android.content.pm.InstantAppResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.CollectionUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.pm.verify.domain.DomainVerificationService;

import com.samsung.android.core.pm.containerservice.PackageHelperExt;
import com.samsung.android.rune.PMRune;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeoutException;

public final class PackageHandler extends Handler {
    public final PackageManagerService mPm;

    public PackageHandler(Looper looper, PackageManagerService packageManagerService) {
        super(looper);
        this.mPm = packageManagerService;
    }

    public final void doHandleMessage(Message message) {
        ArrayList arrayList;
        int i = message.what;
        if (i == 1) {
            InstallPackageHelper installPackageHelper = this.mPm.mInstallPackageHelper;
            synchronized (installPackageHelper.mPm.mLock) {
                try {
                    SparseArray copiedMap = installPackageHelper.mPm.mPendingBroadcasts.copiedMap();
                    int size = copiedMap.size();
                    int i2 = 0;
                    for (int i3 = 0; i3 < size; i3++) {
                        i2 += ((ArrayMap) copiedMap.valueAt(i3)).size();
                    }
                    if (i2 == 0) {
                        return;
                    }
                    String[] strArr = new String[i2];
                    ArrayList[] arrayListArr = new ArrayList[i2];
                    int[] iArr = new int[i2];
                    int i4 = 0;
                    for (int i5 = 0; i5 < size; i5++) {
                        int keyAt = copiedMap.keyAt(i5);
                        ArrayMap arrayMap = (ArrayMap) copiedMap.valueAt(i5);
                        int size2 = CollectionUtils.size(arrayMap);
                        for (int i6 = 0; i6 < size2; i6++) {
                            strArr[i4] = (String) arrayMap.keyAt(i6);
                            arrayListArr[i4] = (ArrayList) arrayMap.valueAt(i6);
                            PackageSetting packageLPr =
                                    installPackageHelper.mPm.mSettings.getPackageLPr(strArr[i4]);
                            iArr[i4] =
                                    packageLPr != null
                                            ? UserHandle.getUid(keyAt, packageLPr.mAppId)
                                            : -1;
                            i4++;
                        }
                    }
                    PendingPackageBroadcasts pendingPackageBroadcasts =
                            installPackageHelper.mPm.mPendingBroadcasts;
                    synchronized (pendingPackageBroadcasts.mLock) {
                        pendingPackageBroadcasts.mUidMap.clear();
                    }
                    Computer snapshotComputer = installPackageHelper.mPm.snapshotComputer();
                    for (int i7 = 0; i7 < i4; i7++) {
                        if (PMRune.PM_WA_WORK_COMP_CHANGED
                                && (arrayList = arrayListArr[i7]) != null
                                && arrayList.size() == 1
                                && arrayListArr[i7].contains(
                                        "androidx.work.impl.background.systemalarm.RescheduleReceiver")) {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Don't send PACKAGE_CHANGED for "),
                                    strArr[i7],
                                    " by WorkManager",
                                    "PackageManager");
                        } else {
                            installPackageHelper.mBroadcastHelper.sendPackageChangedBroadcast(
                                    snapshotComputer,
                                    strArr[i7],
                                    true,
                                    arrayListArr[i7],
                                    iArr[i7],
                                    null);
                        }
                    }
                    return;
                } finally {
                }
            }
        }
        if (i == 9) {
            InstallRequest installRequest =
                    (InstallRequest) this.mPm.mRunningInstalls.get(message.arg1);
            if (!(installRequest != null
                    ? this.mPm.isLocaleOptimizedPackage(0, installRequest.mName)
                    : false)) {
                doHandlePostInstall(message);
                return;
            }
            PackageManagerService packageManagerService = this.mPm;
            packageManagerService.getClass();
            Slog.d(
                    "PackageManager",
                    "updateLocaleOverlaysForPackage() called with: msg = [" + message + "]");
            int i8 = message.arg1;
            int i9 = message.arg2;
            InstallRequest installRequest2 =
                    (InstallRequest) packageManagerService.mRunningInstalls.get(i8);
            PackageManagerService.InstallLocaleOverlaysType installLocaleOverlaysType =
                    PackageManagerService.InstallLocaleOverlaysType.PACKAGE_INSTALL;
            if (installRequest2 == null) {
                packageManagerService.overlaysInstallComplete(
                        i8, i9, installLocaleOverlaysType, -1, null, null);
                return;
            } else {
                packageManagerService.updateLocaleOverlaysForPackage(
                        i8, i9, installLocaleOverlaysType, -1, installRequest2.mName);
                return;
            }
        }
        if (i == 10) {
            doHandlePostInstall(message);
            return;
        }
        if (i == 12) {
            byte b = message.arg1 == 1 ? (byte) 1 : (byte) 0;
            if (message.arg2 == 1) {
                Runtime.getRuntime().gc();
            }
            Object obj = message.obj;
            if (obj != null) {
                this.mPm.mCustomInjector.getAsecInstallHelper().unloadAllContainers((Set) obj);
            }
            if (b != 0) {
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
            int i10 = message.arg1;
            PackageVerificationState packageVerificationState =
                    (PackageVerificationState) this.mPm.mPendingVerification.get(i10);
            if (packageVerificationState == null) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                        i10,
                        "Verification with id ",
                        " not found. It may be invalid or overridden by integrity verification",
                        "PackageManager");
                return;
            } else if (packageVerificationState.isVerificationComplete()) {
                BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                        i10, "Verification with id ", " already complete.", "PackageManager");
                return;
            } else {
                VerificationUtils.processVerificationResponse(
                        i10,
                        packageVerificationState,
                        (PackageVerificationResponse) message.obj,
                        this.mPm);
                return;
            }
        }
        if (i == 16) {
            int i11 = message.arg1;
            boolean z = message.arg2 != 0;
            PackageVerificationState packageVerificationState2 =
                    (PackageVerificationState) this.mPm.mPendingVerification.get(i11);
            if (packageVerificationState2 == null
                    || packageVerificationState2.isVerificationComplete()) {
                return;
            }
            PackageVerificationResponse packageVerificationResponse =
                    (PackageVerificationResponse) message.obj;
            if (!z) {
                if (packageVerificationState2.mExtendedTimeoutUids.get(
                        packageVerificationResponse.callerUid, false)) {
                    return;
                }
            }
            VerificationUtils.processVerificationResponseOnTimeout(
                    i11, packageVerificationState2, packageVerificationResponse, this.mPm);
            return;
        }
        switch (i) {
            case 19:
                PackageManagerService packageManagerService2 = this.mPm;
                int i12 = message.arg1;
                synchronized (packageManagerService2.mLock) {
                    packageManagerService2.mHandler.removeMessages(19);
                    packageManagerService2.mSettings.writePackageListLPr(i12);
                }
                return;
            case 20:
                PackageManagerService packageManagerService3 = this.mPm;
                Context context = packageManagerService3.mContext;
                Computer snapshotComputer2 = packageManagerService3.snapshotComputer();
                PackageManagerService packageManagerService4 = this.mPm;
                UserManagerService userManagerService = packageManagerService4.mUserManager;
                InstantAppResolverConnection instantAppResolverConnection =
                        packageManagerService4.mInstantAppResolverConnection;
                InstantAppRequest instantAppRequest = (InstantAppRequest) message.obj;
                ActivityInfo activityInfo = packageManagerService4.mInstantAppInstallerActivity;
                Handler handler = packageManagerService4.mHandler;
                boolean z2 = InstantAppResolver.DEBUG_INSTANT;
                long currentTimeMillis = System.currentTimeMillis();
                String str = instantAppRequest.token;
                boolean z3 = InstantAppResolver.DEBUG_INSTANT;
                if (z3) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            "[", str, "] Phase2; resolving", "PackageManager");
                }
                Intent intent = instantAppRequest.origIntent;
                InstantAppResolver.AnonymousClass1 anonymousClass1 =
                        new InstantAppResolver.AnonymousClass1(
                                snapshotComputer2,
                                userManagerService,
                                intent,
                                str,
                                instantAppRequest,
                                InstantAppResolver.sanitizeIntent(intent),
                                activityInfo,
                                context);
                try {
                    InstantAppRequestInfo instantAppRequestInfo =
                            new InstantAppRequestInfo(
                                    InstantAppResolver.sanitizeIntent(instantAppRequest.origIntent),
                                    instantAppRequest.hostDigestPrefixSecure,
                                    UserHandle.of(instantAppRequest.userId),
                                    instantAppRequest.isRequesterInstantApp,
                                    instantAppRequest.token);
                    instantAppResolverConnection.getClass();
                    InstantAppResolverConnection.AnonymousClass1 anonymousClass12 =
                            new IRemoteCallback
                                    .Stub() { // from class:
                                              // com.android.server.pm.InstantAppResolverConnection.1
                                public final /* synthetic */ InstantAppResolver.AnonymousClass1
                                        val$callback;
                                public final /* synthetic */ Handler val$callbackHandler;
                                public final /* synthetic */ long val$startTime;

                                public AnonymousClass1(
                                        Handler handler2,
                                        InstantAppResolver.AnonymousClass1 anonymousClass13,
                                        long currentTimeMillis2) {
                                    r1 = handler2;
                                    r2 = anonymousClass13;
                                    r3 = currentTimeMillis2;
                                }

                                public final void sendResult(Bundle bundle) {
                                    final ArrayList parcelableArrayList =
                                            bundle.getParcelableArrayList(
                                                    "android.app.extra.RESOLVE_INFO",
                                                    InstantAppResolveInfo.class);
                                    Handler handler2 = r1;
                                    final InstantAppResolver.AnonymousClass1 anonymousClass13 = r2;
                                    final long j = r3;
                                    handler2.post(
                                            new Runnable() { // from class:
                                                             // com.android.server.pm.InstantAppResolverConnection$1$$ExternalSyntheticLambda0
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    InstantAppResolver.AnonymousClass1
                                                            anonymousClass14 =
                                                                    InstantAppResolver
                                                                            .AnonymousClass1.this;
                                                    ArrayList arrayList2 = parcelableArrayList;
                                                    long j2 = j;
                                                    Intent intent2 = null;
                                                    if (arrayList2 != null) {
                                                        anonymousClass14.getClass();
                                                        if (arrayList2.size() > 0) {
                                                            Intent intent3 =
                                                                    anonymousClass14.val$origIntent;
                                                            AuxiliaryResolveInfo
                                                                    filterInstantAppIntent =
                                                                            InstantAppResolver
                                                                                    .filterInstantAppIntent(
                                                                                            anonymousClass14
                                                                                                    .val$computer,
                                                                                            anonymousClass14
                                                                                                    .val$userManager,
                                                                                            arrayList2,
                                                                                            intent3,
                                                                                            null,
                                                                                            0,
                                                                                            intent3
                                                                                                    .getPackage(),
                                                                                            anonymousClass14
                                                                                                    .val$token,
                                                                                            anonymousClass14
                                                                                                    .val$requestObj
                                                                                                    .hostDigestPrefixSecure);
                                                            if (filterInstantAppIntent != null) {
                                                                intent2 =
                                                                        filterInstantAppIntent
                                                                                .failureIntent;
                                                            }
                                                        }
                                                    }
                                                    Intent intent4 = intent2;
                                                    InstantAppRequest instantAppRequest2 =
                                                            anonymousClass14.val$requestObj;
                                                    Intent intent5 = instantAppRequest2.origIntent;
                                                    Intent intent6 =
                                                            anonymousClass14.val$sanitizedIntent;
                                                    String str2 = instantAppRequest2.callingPackage;
                                                    String str3 =
                                                            instantAppRequest2.callingFeatureId;
                                                    Bundle bundle2 =
                                                            instantAppRequest2.verificationBundle;
                                                    String str4 = instantAppRequest2.resolvedType;
                                                    int i13 = instantAppRequest2.userId;
                                                    AuxiliaryResolveInfo auxiliaryResolveInfo =
                                                            instantAppRequest2.responseObj;
                                                    Intent buildEphemeralInstallerIntent =
                                                            InstantAppResolver
                                                                    .buildEphemeralInstallerIntent(
                                                                            intent5,
                                                                            intent6,
                                                                            intent4,
                                                                            str2,
                                                                            str3,
                                                                            bundle2,
                                                                            str4,
                                                                            i13,
                                                                            auxiliaryResolveInfo
                                                                                    .installFailureActivity,
                                                                            anonymousClass14
                                                                                    .val$token,
                                                                            false,
                                                                            auxiliaryResolveInfo
                                                                                    .filters);
                                                    ActivityInfo activityInfo2 =
                                                            anonymousClass14
                                                                    .val$instantAppInstaller;
                                                    buildEphemeralInstallerIntent.setComponent(
                                                            new ComponentName(
                                                                    activityInfo2.packageName,
                                                                    activityInfo2.name));
                                                    InstantAppResolver.logMetrics(
                                                            FrameworkStatsLog
                                                                    .CAMERA_FEATURE_COMBINATION_QUERY_EVENT,
                                                            anonymousClass14
                                                                                    .val$requestObj
                                                                                    .responseObj
                                                                                    .filters
                                                                            != null
                                                                    ? 0
                                                                    : 1,
                                                            j2,
                                                            anonymousClass14.val$token);
                                                    anonymousClass14.val$context.startActivity(
                                                            buildEphemeralInstallerIntent);
                                                }
                                            });
                                }
                            };
                    try {
                        String token = instantAppRequestInfo.getToken();
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            IInstantAppResolver bind = instantAppResolverConnection.bind(token);
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            bind.getInstantAppIntentFilterList(
                                    instantAppRequestInfo, anonymousClass12);
                            return;
                        } catch (Throwable th) {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                            throw th;
                        }
                    } catch (RemoteException unused2) {
                        return;
                    } catch (InterruptedException unused3) {
                        throw new InstantAppResolverConnection.ConnectionException(3);
                    } catch (TimeoutException unused4) {
                        throw new InstantAppResolverConnection.ConnectionException(1);
                    }
                } catch (InstantAppResolverConnection.ConnectionException e) {
                    int i13 = e.failure == 1 ? 2 : 1;
                    InstantAppResolver.logMetrics(
                            FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT,
                            i13,
                            currentTimeMillis2,
                            str);
                    if (z3) {
                        if (i13 == 2) {
                            DualAppManagerService$$ExternalSyntheticOutline0.m(
                                    "[", str, "] Phase2; bind timed out", "PackageManager");
                            return;
                        } else {
                            DualAppManagerService$$ExternalSyntheticOutline0.m(
                                    "[",
                                    str,
                                    "] Phase2; service connection error",
                                    "PackageManager");
                            return;
                        }
                    }
                    return;
                }
            case 21:
                int i14 = message.arg1;
                int i15 = message.arg2;
                VerifyingSession verifyingSession =
                        (VerifyingSession) this.mPm.mPendingEnableRollback.get(i14);
                if (verifyingSession == null) {
                    BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                            i14, "Invalid rollback enabled token ", " received", "PackageManager");
                    return;
                }
                this.mPm.mPendingEnableRollback.remove(i14);
                if (i15 != 1) {
                    Uri fromFile = Uri.fromFile(verifyingSession.mOriginInfo.mResolvedFile);
                    Slog.w("PackageManager", "Failed to enable rollback for " + fromFile);
                    Slog.w("PackageManager", "Continuing with installation of " + fromFile);
                }
                Trace.asyncTraceEnd(262144L, "enable_rollback", i14);
                verifyingSession.mWaitForEnableRollbackToComplete = false;
                verifyingSession.handleReturnCode();
                return;
            case 22:
                int i16 = message.arg1;
                int i17 = message.arg2;
                VerifyingSession verifyingSession2 =
                        (VerifyingSession) this.mPm.mPendingEnableRollback.get(i16);
                if (verifyingSession2 != null) {
                    Uri fromFile2 = Uri.fromFile(verifyingSession2.mOriginInfo.mResolvedFile);
                    Slog.w("PackageManager", "Enable rollback timed out for " + fromFile2);
                    this.mPm.mPendingEnableRollback.remove(i16);
                    Slog.w("PackageManager", "Continuing with installation of " + fromFile2);
                    Trace.asyncTraceEnd(262144L, "enable_rollback", i16);
                    verifyingSession2.mWaitForEnableRollbackToComplete = false;
                    verifyingSession2.handleReturnCode();
                    Intent intent2 = new Intent("android.intent.action.CANCEL_ENABLE_ROLLBACK");
                    intent2.putExtra("android.content.pm.extra.ENABLE_ROLLBACK_SESSION_ID", i17);
                    intent2.addFlags(335544320);
                    this.mPm.mContext.sendBroadcastAsUser(
                            intent2,
                            UserHandle.SYSTEM,
                            "android.permission.PACKAGE_ROLLBACK_AGENT");
                    return;
                }
                return;
            case 23:
                CleanUpArgs cleanUpArgs = (CleanUpArgs) message.obj;
                if (cleanUpArgs != null) {
                    this.mPm.mRemovePackageHelper.cleanUpResources(
                            cleanUpArgs.mPackageName,
                            cleanUpArgs.mCodeFile,
                            cleanUpArgs.mInstructionSets);
                    return;
                }
                return;
            case 24:
            case 29:
                String str2 = (String) message.obj;
                if (str2 != null) {
                    this.mPm.notifyInstallObserver(str2, i == 29);
                    return;
                }
                return;
            case 25:
                int i18 = message.arg1;
                PackageVerificationState packageVerificationState3 =
                        (PackageVerificationState) this.mPm.mPendingVerification.get(i18);
                if (packageVerificationState3 == null) {
                    BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                            i18,
                            "Integrity verification with id ",
                            " not found. It may be invalid or overridden by verifier",
                            "PackageManager");
                    return;
                }
                int intValue = ((Integer) message.obj).intValue();
                VerifyingSession verifyingSession3 = packageVerificationState3.mVerifyingSession;
                Uri fromFile3 = Uri.fromFile(verifyingSession3.mOriginInfo.mResolvedFile);
                packageVerificationState3.mIntegrityVerificationComplete = true;
                if (intValue == 1) {
                    Slog.i("PackageManager", "Integrity check passed for " + fromFile3);
                } else {
                    verifyingSession3.setReturnCode(-22, "Integrity check failed for " + fromFile3);
                }
                if (packageVerificationState3.areAllVerificationsComplete()) {
                    this.mPm.mPendingVerification.remove(i18);
                }
                Trace.asyncTraceEnd(262144L, "integrity_verification", i18);
                verifyingSession3.mWaitForIntegrityVerificationToComplete = false;
                verifyingSession3.handleReturnCode();
                return;
            case 26:
                int i19 = message.arg1;
                PackageVerificationState packageVerificationState4 =
                        (PackageVerificationState) this.mPm.mPendingVerification.get(i19);
                if (packageVerificationState4 == null
                        || packageVerificationState4.mIntegrityVerificationComplete) {
                    return;
                }
                VerifyingSession verifyingSession4 = packageVerificationState4.mVerifyingSession;
                String str3 =
                        "Integrity verification timed out for "
                                + Uri.fromFile(verifyingSession4.mOriginInfo.mResolvedFile);
                Slog.i("PackageManager", str3);
                packageVerificationState4.mIntegrityVerificationComplete = true;
                verifyingSession4.setReturnCode(-22, str3);
                if (packageVerificationState4.areAllVerificationsComplete()) {
                    this.mPm.mPendingVerification.remove(i19);
                }
                Trace.asyncTraceEnd(262144L, "integrity_verification", i19);
                verifyingSession4.mWaitForIntegrityVerificationToComplete = false;
                verifyingSession4.handleReturnCode();
                return;
            case 27:
                ((DomainVerificationService) this.mPm.mDomainVerificationManager)
                        .mProxy.runMessage(message.arg1, message.obj);
                return;
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
                                            PackageManagerService
                                                    .DEFAULT_UNUSED_STATIC_SHARED_LIB_MIN_CACHE_PERIOD));
                    return;
                } catch (IOException e2) {
                    Log.w(
                            "PackageManager",
                            "Failed to prune unused static shared libraries :" + e2.getMessage());
                    return;
                }
            default:
                return;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:324:0x0641, code lost:

       if (r7 == r0) goto L285;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void doHandlePostInstall(android.os.Message r38) {
        /*
            Method dump skipped, instructions count: 2224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.PackageHandler.doHandlePostInstall(android.os.Message):void");
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        try {
            doHandleMessage(message);
        } finally {
            Process.setThreadPriority(0);
        }
    }
}
