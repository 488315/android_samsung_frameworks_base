package com.android.server.pm;

import android.apex.ApexInfo;
import android.apex.ApexInfoList;
import android.apex.ApexSessionInfo;
import android.apex.ApexSessionParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageInstallObserver2;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.SigningDetails;
import android.content.pm.parsing.PackageLite;
import android.content.pm.parsing.result.ParseResult;
import android.content.pm.parsing.result.ParseTypeImpl;
import android.content.rollback.RollbackInfo;
import android.content.rollback.RollbackManager;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.util.IntArray;
import android.util.Slog;
import android.util.apk.ApkSignatureVerifier;

import com.android.internal.content.InstallLocationUtils;
import com.android.internal.pm.parsing.PackageParser2;
import com.android.internal.pm.parsing.PackageParserException;
import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SystemConfig;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.rollback.RollbackManagerInternal;
import com.android.server.rollback.RollbackManagerServiceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public final class PackageSessionVerifier {
    public final ApexManager mApexManager;
    public final Context mContext;
    public final Handler mHandler;
    public final Supplier mPackageParserSupplier;
    public final PackageManagerService mPm;
    public final List mStagedSessions;

    public PackageSessionVerifier() {
        this.mStagedSessions = new ArrayList();
        this.mContext = null;
        this.mPm = null;
        this.mApexManager = null;
        this.mPackageParserSupplier = null;
        this.mHandler = null;
    }

    public PackageSessionVerifier(
            Context context,
            PackageManagerService packageManagerService,
            ApexManager apexManager,
            PackageManagerService$$ExternalSyntheticLambda55
                    packageManagerService$$ExternalSyntheticLambda55,
            Looper looper) {
        this.mStagedSessions = new ArrayList();
        this.mContext = context;
        this.mPm = packageManagerService;
        this.mApexManager = apexManager;
        this.mPackageParserSupplier = packageManagerService$$ExternalSyntheticLambda55;
        this.mHandler = new Handler(looper);
    }

    public static boolean isRollback(StagingManager.StagedSession stagedSession) {
        return PackageInstallerSession.this.params.installReason == 5;
    }

    public void checkActiveSessions(boolean z) throws PackageManagerException {
        Iterator it = ((ArrayList) this.mStagedSessions).iterator();
        int i = 0;
        while (it.hasNext()) {
            StagingManager.StagedSession stagedSession = (StagingManager.StagedSession) it.next();
            if (!PackageInstallerSession.this.isDestroyed()
                    && !((PackageInstallerSession.StagedSession) stagedSession)
                            .isInTerminalState()) {
                i++;
            }
        }
        if (!z && i > 1) {
            throw new PackageManagerException(
                    -119, "Cannot stage multiple sessions without checkpoint support");
        }
    }

    public final void checkApexSignature(PackageInstallerSession packageInstallerSession) {
        SigningDetails signingDetails;
        if (packageInstallerSession.isApexSession()) {
            String packageName = packageInstallerSession.getPackageName();
            PackageInfo packageInfo =
                    this.mPm
                            .snapshotComputer()
                            .getPackageInfo(
                                    packageInstallerSession.getPackageName(), 1073741824L, 0);
            if (packageInfo == null) {
                throw new PackageManagerException(
                        -23,
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "Attempting to install new APEX package ", packageName));
            }
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            String str = applicationInfo.sourceDir;
            ParseResult verify =
                    ApkSignatureVerifier.verify(
                            ParseTypeImpl.forDefaultParsing(),
                            str,
                            ApkSignatureVerifier.getMinimumSignatureSchemeVersionForTargetSdk(
                                    applicationInfo.targetSdkVersion));
            if (verify.isError()) {
                StringBuilder m =
                        DumpUtils$$ExternalSyntheticOutline0.m(
                                "Failed to verify APEX package ", str, " : ");
                m.append(verify.getException());
                throw new PackageManagerException(-22, m.toString(), verify.getException());
            }
            SigningDetails signingDetails2 = (SigningDetails) verify.getResult();
            synchronized (packageInstallerSession.mLock) {
                signingDetails = packageInstallerSession.mSigningDetails;
            }
            if (!signingDetails.checkCapability(signingDetails2, 1)
                    && !signingDetails2.checkCapability(signingDetails, 8)) {
                throw new PackageManagerException(
                        -22,
                        XmlUtils$$ExternalSyntheticOutline0.m(
                                "APK container signature of APEX package ",
                                packageName,
                                " is not compatible with the one currently installed on device"));
            }
        }
    }

    public final void checkApexUpdateAllowed(PackageInstallerSession packageInstallerSession) {
        if (packageInstallerSession.isApexSession()
                && (packageInstallerSession.params.installFlags & 8388608) == 0) {
            String packageName = packageInstallerSession.getPackageName();
            String str = packageInstallerSession.getInstallSource().mInstallerPackageName;
            boolean z = false;
            if (this.mPm.mModuleInfoProvider.getModuleInfo(packageName, 0) != null) {
                String str2 = SystemConfig.getInstance().mModulesInstallerPackageName;
                if (str2 == null) {
                    Slog.w("PackageSessionVerifier", "No modules installer defined");
                } else {
                    z = str2.equals(str);
                }
            } else {
                String str3 =
                        (String) SystemConfig.getInstance().mAllowedVendorApexes.get(packageName);
                if (str3 != null) {
                    z = str3.equals(str);
                } else if ("com.sec.android.app.samsungapps".equals(str)) {
                    z = true;
                } else {
                    Slog.w("PackageSessionVerifier", packageName + " is not allowed to be updated");
                }
            }
            if (!z) {
                throw new PackageManagerException(
                        -22,
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                "Update of APEX package ",
                                packageName,
                                " is not allowed for ",
                                str));
            }
        }
    }

    public void checkOverlaps(
            StagingManager.StagedSession stagedSession, StagingManager.StagedSession stagedSession2)
            throws PackageManagerException {
        long j;
        long j2;
        PackageInstallerSession.StagedSession stagedSession3 =
                (PackageInstallerSession.StagedSession) stagedSession;
        if (PackageInstallerSession.this.isDestroyed() || stagedSession3.isInTerminalState()) {
            return;
        }
        PackageInstallerSession.StagedSession stagedSession4 =
                (PackageInstallerSession.StagedSession) stagedSession2;
        String packageName = PackageInstallerSession.this.getPackageName();
        if (packageName == null) {
            throw new PackageManagerException(
                    -22,
                    AmFmBandRange$$ExternalSyntheticOutline0.m(
                            PackageInstallerSession.this.sessionId,
                            new StringBuilder("Cannot stage session "),
                            " with package name null"));
        }
        Iterator it = ((ArrayList) this.mStagedSessions).iterator();
        while (it.hasNext()) {
            PackageInstallerSession.StagedSession stagedSession5 =
                    (PackageInstallerSession.StagedSession)
                            ((StagingManager.StagedSession) it.next());
            if (!PackageInstallerSession.this.isDestroyed()
                    && !stagedSession5.isInTerminalState()) {
                PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
                if (packageInstallerSession.sessionId == PackageInstallerSession.this.sessionId) {
                    continue;
                } else {
                    if (packageInstallerSession.sessionContains(
                            new PackageInstallerSession$$ExternalSyntheticLambda10(
                                    1,
                                    new PackageSessionVerifier$$ExternalSyntheticLambda1(
                                            packageName, 0)))) {
                        PackageInstallerSession packageInstallerSession2 =
                                PackageInstallerSession.this;
                        synchronized (packageInstallerSession2.mLock) {
                            j = packageInstallerSession2.committedMillis;
                        }
                        PackageInstallerSession packageInstallerSession3 =
                                PackageInstallerSession.this;
                        synchronized (packageInstallerSession3.mLock) {
                            j2 = packageInstallerSession3.committedMillis;
                        }
                        if (j < j2) {
                            StringBuilder m =
                                    DumpUtils$$ExternalSyntheticOutline0.m(
                                            "Package: ", packageName, " in session: ");
                            m.append(PackageInstallerSession.this.sessionId);
                            m.append(" has been staged already by session: ");
                            m.append(packageInstallerSession.sessionId);
                            throw new PackageManagerException(-119, m.toString());
                        }
                        StringBuilder m2 =
                                DumpUtils$$ExternalSyntheticOutline0.m(
                                        "Package: ", packageName, " in session: ");
                        m2.append(packageInstallerSession.sessionId);
                        m2.append(" has been staged already by session: ");
                        m2.append(PackageInstallerSession.this.sessionId);
                        stagedSession5.setSessionFailed(-119, m2.toString());
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    public void checkRebootlessApex(PackageInstallerSession packageInstallerSession)
            throws PackageManagerException {
        if (packageInstallerSession.params.isStaged || !packageInstallerSession.isApexSession()) {
            return;
        }
        String packageName = packageInstallerSession.getPackageName();
        if (packageName == null) {
            throw new PackageManagerException(
                    -22,
                    AmFmBandRange$$ExternalSyntheticOutline0.m(
                            packageInstallerSession.sessionId,
                            new StringBuilder("Invalid session "),
                            " with package name null"));
        }
        Iterator it = ((ArrayList) this.mStagedSessions).iterator();
        while (it.hasNext()) {
            PackageInstallerSession.StagedSession stagedSession =
                    (PackageInstallerSession.StagedSession)
                            ((StagingManager.StagedSession) it.next());
            if (!PackageInstallerSession.this.isDestroyed() && !stagedSession.isInTerminalState()) {
                PackageInstallerSession$$ExternalSyntheticLambda10
                        packageInstallerSession$$ExternalSyntheticLambda10 =
                                new PackageInstallerSession$$ExternalSyntheticLambda10(
                                        1,
                                        new PackageSessionVerifier$$ExternalSyntheticLambda1(
                                                packageName, 1));
                PackageInstallerSession packageInstallerSession2 = PackageInstallerSession.this;
                if (packageInstallerSession2.sessionContains(
                        packageInstallerSession$$ExternalSyntheticLambda10)) {
                    throw new PackageManagerException(
                            -22,
                            "Staged session "
                                    + packageInstallerSession2.sessionId
                                    + " already contains "
                                    + packageName);
                }
            }
        }
    }

    public void checkRollbacks(StagingManager.StagedSession stagedSession)
            throws PackageManagerException {
        if (PackageInstallerSession.this.isDestroyed()) {
            return;
        }
        PackageInstallerSession.StagedSession stagedSession2 =
                (PackageInstallerSession.StagedSession) stagedSession;
        if (stagedSession2.isInTerminalState()) {
            return;
        }
        Iterator it = ((ArrayList) this.mStagedSessions).iterator();
        while (it.hasNext()) {
            StagingManager.StagedSession stagedSession3 = (StagingManager.StagedSession) it.next();
            if (!PackageInstallerSession.this.isDestroyed()) {
                PackageInstallerSession.StagedSession stagedSession4 =
                        (PackageInstallerSession.StagedSession) stagedSession3;
                if (stagedSession4.isInTerminalState()) {
                    continue;
                } else {
                    boolean isRollback = isRollback(stagedSession);
                    PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
                    if (isRollback && !isRollback(stagedSession3)) {
                        if (!ensureActiveApexSessionIsAborted(stagedSession3)) {
                            VaultKeeperService$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("Failed to abort apex session "),
                                    packageInstallerSession.sessionId,
                                    "PackageSessionVerifier");
                        }
                        StringBuilder sb =
                                new StringBuilder("Session was failed by rollback session: ");
                        PackageInstallerSession packageInstallerSession2 =
                                PackageInstallerSession.this;
                        sb.append(packageInstallerSession2.sessionId);
                        stagedSession4.setSessionFailed(-119, sb.toString());
                        StringBuilder sb2 = new StringBuilder("Session ");
                        sb2.append(packageInstallerSession.sessionId);
                        sb2.append(" is marked failed due to rollback session: ");
                        SystemServiceManager$$ExternalSyntheticOutline0.m(
                                sb2, packageInstallerSession2.sessionId, "PackageSessionVerifier");
                    } else if (!isRollback(stagedSession) && isRollback(stagedSession3)) {
                        throw new PackageManagerException(
                                -119,
                                "Session was failed by rollback session: "
                                        + packageInstallerSession.sessionId);
                    }
                }
            }
        }
    }

    public final VerifyingSession createVerifyingSession(
            PackageInstallerSession packageInstallerSession, AnonymousClass1 anonymousClass1) {
        SigningDetails signingDetails;
        PackageLite packageLite;
        boolean z;
        UserHandle userHandle =
                (packageInstallerSession.params.installFlags & 64) != 0
                        ? UserHandle.ALL
                        : new UserHandle(packageInstallerSession.userId);
        File file = packageInstallerSession.stageDir;
        String str = packageInstallerSession.stageCid;
        PackageInstaller.SessionParams sessionParams = packageInstallerSession.params;
        InstallSource installSource = packageInstallerSession.getInstallSource();
        int installerUid = packageInstallerSession.getInstallerUid();
        synchronized (packageInstallerSession.mLock) {
            signingDetails = packageInstallerSession.mSigningDetails;
        }
        int i = packageInstallerSession.sessionId;
        synchronized (packageInstallerSession.mLock) {
            packageLite = packageInstallerSession.mPackageLite;
        }
        Boolean bool = packageInstallerSession.mUserActionRequired;
        if (bool != null) {
            z = bool.booleanValue();
        } else {
            Slog.wtf("PackageInstallerSession", "mUserActionRequired should not be null.");
            z = false;
        }
        return new VerifyingSession(
                userHandle,
                file,
                str,
                anonymousClass1,
                sessionParams,
                installSource,
                installerUid,
                signingDetails,
                i,
                packageLite,
                z,
                this.mPm);
    }

    public final void endVerification(StagingManager.StagedSession stagedSession) {
        try {
            if (InstallLocationUtils.getStorageManager().supportsCheckpoint()) {
                InstallLocationUtils.getStorageManager().startCheckpoint(2);
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Marking session "),
                    PackageInstallerSession.this.sessionId,
                    " as ready",
                    "PackageSessionVerifier");
            PackageInstallerSession.StagedSession stagedSession2 =
                    (PackageInstallerSession.StagedSession) stagedSession;
            PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
            packageInstallerSession.setSessionReady();
            if (stagedSession2.isSessionReady() && stagedSession2.containsApexSession()) {
                SystemProperties.set("sys.staged_apex.state", "staged");
                int i = packageInstallerSession.sessionId;
                ApexManager.ApexManagerImpl apexManagerImpl =
                        (ApexManager.ApexManagerImpl) this.mApexManager;
                apexManagerImpl.getClass();
                try {
                    apexManagerImpl.waitForApexService().markStagedSessionReady(i);
                } catch (RemoteException e) {
                    Slog.e("ApexManager", "Unable to contact apexservice", e);
                    throw new RuntimeException(e);
                } catch (Exception e2) {
                    throw new PackageManagerException(
                            -22, "Failed to mark apexd session as ready : " + e2.getMessage());
                }
            }
        } catch (Exception e3) {
            Slog.e("PackageSessionVerifier", "Failed to get hold of StorageManager", e3);
            throw new PackageManagerException(-110, "Failed to get hold of StorageManager");
        }
    }

    public final boolean ensureActiveApexSessionIsAborted(
            StagingManager.StagedSession stagedSession) {
        int i;
        ApexManager apexManager;
        ApexSessionInfo stagedSessionInfo;
        PackageInstallerSession.StagedSession stagedSession2 =
                (PackageInstallerSession.StagedSession) stagedSession;
        if (!stagedSession2.containsApexSession()
                || (stagedSessionInfo =
                                (apexManager = this.mApexManager)
                                        .getStagedSessionInfo(
                                                (i = PackageInstallerSession.this.sessionId)))
                        == null
                || stagedSessionInfo.isUnknown
                || stagedSessionInfo.isActivationFailed
                || stagedSessionInfo.isSuccess
                || stagedSessionInfo.isReverted) {
            return true;
        }
        try {
            ((ApexManager.ApexManagerImpl) apexManager).waitForApexService().abortStagedSession(i);
            return true;
        } catch (Exception e) {
            Slog.e("ApexManager", e.getMessage(), e);
            return false;
        }
    }

    public final void onVerificationFailure(
            StagingManager.StagedSession stagedSession,
            PackageInstallerSession$$ExternalSyntheticLambda8
                    packageInstallerSession$$ExternalSyntheticLambda8,
            int i,
            String str) {
        if (!ensureActiveApexSessionIsAborted(stagedSession)) {
            VaultKeeperService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Failed to abort apex session "),
                    PackageInstallerSession.this.sessionId,
                    "PackageSessionVerifier");
        }
        ((PackageInstallerSession.StagedSession) stagedSession).setSessionFailed(i, str);
        packageInstallerSession$$ExternalSyntheticLambda8.onResult(-22, str);
    }

    public void storeSession(StagingManager.StagedSession stagedSession) {
        if (stagedSession != null) {
            this.mStagedSessions.add(stagedSession);
        }
    }

    public final void verifyAPK(
            final PackageInstallerSession packageInstallerSession,
            final PackageInstallerSession$$ExternalSyntheticLambda8
                    packageInstallerSession$$ExternalSyntheticLambda8) {
        final VerifyingSession createVerifyingSession =
                createVerifyingSession(
                        packageInstallerSession,
                        new IPackageInstallObserver2
                                .Stub() { // from class:
                                          // com.android.server.pm.PackageSessionVerifier.1
                            public final void onPackageInstalled(
                                    String str, int i, String str2, Bundle bundle) {
                                PackageInstallerSession packageInstallerSession2 =
                                        packageInstallerSession;
                                if (packageInstallerSession2.params.isStaged && i == 1) {
                                    PackageSessionVerifier packageSessionVerifier =
                                            PackageSessionVerifier.this;
                                    PackageInstallerSession.StagedSession stagedSession =
                                            packageInstallerSession2.mStagedSession;
                                    PackageInstallerSession$$ExternalSyntheticLambda8
                                            packageInstallerSession$$ExternalSyntheticLambda82 =
                                                    packageInstallerSession$$ExternalSyntheticLambda8;
                                    packageSessionVerifier.getClass();
                                    DeviceIdleController$$ExternalSyntheticOutline0.m(
                                            new StringBuilder(
                                                    "Starting preRebootVerification for session "),
                                            PackageInstallerSession.this.sessionId,
                                            "PackageSessionVerifier");
                                    packageSessionVerifier.mHandler.post(
                                            new PackageSessionVerifier$$ExternalSyntheticLambda0(
                                                    packageSessionVerifier,
                                                    stagedSession,
                                                    packageInstallerSession$$ExternalSyntheticLambda82,
                                                    1));
                                    return;
                                }
                                if (i == 1) {
                                    packageInstallerSession2.setSessionReady();
                                    packageInstallerSession$$ExternalSyntheticLambda8.onResult(
                                            1, null);
                                } else {
                                    packageInstallerSession.setSessionFailed(
                                            i, PackageManager.installStatusToString(i, str2));
                                    packageInstallerSession$$ExternalSyntheticLambda8.onResult(
                                            i, str2);
                                }
                            }

                            public final void onUserActionRequired(Intent intent) {
                                throw new IllegalStateException();
                            }
                        });
        boolean z = packageInstallerSession.params.isMultiPackage;
        PackageManagerService packageManagerService = createVerifyingSession.mPm;
        if (!z) {
            Trace.asyncTraceBegin(
                    262144L, "queueVerify", System.identityHashCode(createVerifyingSession));
            final int i = 1;
            packageManagerService.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.pm.VerifyingSession$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            int i2 = i;
                            Object obj = createVerifyingSession;
                            switch (i2) {
                                case 0:
                                    MultiPackageVerifyingSession multiPackageVerifyingSession =
                                            (MultiPackageVerifyingSession) obj;
                                    multiPackageVerifyingSession.getClass();
                                    Trace.asyncTraceEnd(
                                            262144L,
                                            "queueVerify",
                                            System.identityHashCode(multiPackageVerifyingSession));
                                    Trace.traceBegin(262144L, "startVerify");
                                    Iterator it =
                                            multiPackageVerifyingSession.mChildVerifyingSessions
                                                    .iterator();
                                    while (it.hasNext()) {
                                        ((VerifyingSession) it.next()).handleStartVerify();
                                    }
                                    Iterator it2 =
                                            multiPackageVerifyingSession.mChildVerifyingSessions
                                                    .iterator();
                                    while (it2.hasNext()) {
                                        ((VerifyingSession) it2.next()).handleReturnCode();
                                    }
                                    Trace.traceEnd(262144L);
                                    break;
                                default:
                                    VerifyingSession verifyingSession = (VerifyingSession) obj;
                                    verifyingSession.getClass();
                                    Trace.asyncTraceEnd(
                                            262144L,
                                            "queueVerify",
                                            System.identityHashCode(verifyingSession));
                                    Trace.traceBegin(262144L, "start");
                                    verifyingSession.handleStartVerify();
                                    verifyingSession.handleReturnCode();
                                    Trace.traceEnd(262144L);
                                    break;
                            }
                        }
                    });
            return;
        }
        List childSessions = packageInstallerSession.getChildSessions();
        ArrayList arrayList = new ArrayList(childSessions.size());
        Iterator it = childSessions.iterator();
        while (it.hasNext()) {
            arrayList.add(createVerifyingSession((PackageInstallerSession) it.next(), null));
        }
        final MultiPackageVerifyingSession multiPackageVerifyingSession =
                new MultiPackageVerifyingSession(createVerifyingSession, arrayList);
        final int i2 = 0;
        packageManagerService.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.VerifyingSession$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        Object obj = multiPackageVerifyingSession;
                        switch (i22) {
                            case 0:
                                MultiPackageVerifyingSession multiPackageVerifyingSession2 =
                                        (MultiPackageVerifyingSession) obj;
                                multiPackageVerifyingSession2.getClass();
                                Trace.asyncTraceEnd(
                                        262144L,
                                        "queueVerify",
                                        System.identityHashCode(multiPackageVerifyingSession2));
                                Trace.traceBegin(262144L, "startVerify");
                                Iterator it2 =
                                        multiPackageVerifyingSession2.mChildVerifyingSessions
                                                .iterator();
                                while (it2.hasNext()) {
                                    ((VerifyingSession) it2.next()).handleStartVerify();
                                }
                                Iterator it22 =
                                        multiPackageVerifyingSession2.mChildVerifyingSessions
                                                .iterator();
                                while (it22.hasNext()) {
                                    ((VerifyingSession) it22.next()).handleReturnCode();
                                }
                                Trace.traceEnd(262144L);
                                break;
                            default:
                                VerifyingSession verifyingSession = (VerifyingSession) obj;
                                verifyingSession.getClass();
                                Trace.asyncTraceEnd(
                                        262144L,
                                        "queueVerify",
                                        System.identityHashCode(verifyingSession));
                                Trace.traceBegin(262144L, "start");
                                verifyingSession.handleStartVerify();
                                verifyingSession.handleReturnCode();
                                Trace.traceEnd(262144L);
                                break;
                        }
                    }
                });
    }

    public final void verifyApex(StagingManager.StagedSession stagedSession) {
        int rollbackId;
        String string;
        PackageInstallerSession.StagedSession stagedSession2 =
                (PackageInstallerSession.StagedSession) stagedSession;
        if ((PackageInstallerSession.this.params.installFlags & 262144) != 0) {
            try {
                rollbackId =
                        ((RollbackManagerServiceImpl)
                                        ((RollbackManagerInternal)
                                                LocalServices.getService(
                                                        RollbackManagerInternal.class)))
                                .notifyStagedSession(PackageInstallerSession.this.sessionId);
            } catch (RuntimeException e) {
                Slog.e(
                        "PackageSessionVerifier",
                        "Failed to notifyStagedSession for session: "
                                + PackageInstallerSession.this.sessionId,
                        e);
            }
        } else {
            if (isRollback(stagedSession)) {
                int i = PackageInstallerSession.this.sessionId;
                List recentlyCommittedRollbacks =
                        ((RollbackManager) this.mContext.getSystemService(RollbackManager.class))
                                .getRecentlyCommittedRollbacks();
                int size = recentlyCommittedRollbacks.size();
                for (int i2 = 0; i2 < size; i2++) {
                    RollbackInfo rollbackInfo = (RollbackInfo) recentlyCommittedRollbacks.get(i2);
                    if (rollbackInfo.getCommittedSessionId() == i) {
                        rollbackId = rollbackInfo.getRollbackId();
                    }
                }
                throw new PackageManagerException(
                        -22,
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                i, "Could not find rollback id for commit session: "));
            }
            rollbackId = -1;
        }
        if (stagedSession2.containsApexSession()) {
            IntArray intArray = new IntArray();
            PackageInstallerSession packageInstallerSession = PackageInstallerSession.this;
            if (packageInstallerSession.params.isMultiPackage) {
                Iterator it = stagedSession2.getChildSessions().iterator();
                while (it.hasNext()) {
                    PackageInstallerSession.StagedSession stagedSession3 =
                            (PackageInstallerSession.StagedSession)
                                    ((StagingManager.StagedSession) it.next());
                    if (stagedSession3.isApexSession()) {
                        intArray.add(PackageInstallerSession.this.sessionId);
                    }
                }
            }
            ApexSessionParams apexSessionParams = new ApexSessionParams();
            apexSessionParams.sessionId = packageInstallerSession.sessionId;
            apexSessionParams.childSessionIds = intArray.toArray();
            if (packageInstallerSession.params.installReason == 5) {
                apexSessionParams.isRollback = true;
                apexSessionParams.rollbackId = rollbackId;
            } else if (rollbackId != -1) {
                apexSessionParams.hasRollbackEnabled = true;
                apexSessionParams.rollbackId = rollbackId;
            }
            PowerManager.WakeLock newWakeLock =
                    ((PowerManager) this.mContext.getSystemService("power"))
                            .newWakeLock(1, "PackageSessionVerifier");
            try {
                newWakeLock.acquire();
                ApexInfoList submitStagedSession =
                        this.mApexManager.submitStagedSession(apexSessionParams);
                newWakeLock.release();
                ArrayList arrayList = new ArrayList();
                for (ApexInfo apexInfo : submitStagedSession.apexInfos) {
                    try {
                        PackageParser2 packageParser2 =
                                (PackageParser2) this.mPackageParserSupplier.get();
                        try {
                            ParsedPackage parsePackage =
                                    packageParser2.parsePackage(
                                            new File(apexInfo.modulePath), 1024, false);
                            packageParser2.close();
                            Bundle metaData = parsePackage.getMetaData();
                            if (metaData != null
                                    && (string =
                                                    metaData.getString(
                                                            "com.samsung.android.support.targets"))
                                            != null) {
                                String str = SystemProperties.get("ro.product.name", "");
                                for (String str2 : string.split(",")) {
                                    if (str.startsWith(str2)) {
                                        break;
                                    }
                                }
                                throw new PackageManagerException(
                                        -22,
                                        BootReceiver$$ExternalSyntheticOutline0.m(
                                                "Supported targets(",
                                                string,
                                                ") are specified but no matched one with ",
                                                str));
                            }
                            arrayList.add(parsePackage.getPackageName());
                        } catch (Throwable th) {
                            if (packageParser2 != null) {
                                try {
                                    packageParser2.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (PackageParserException e2) {
                        throw new PackageManagerException(
                                -22,
                                "Failed to parse APEX package " + apexInfo.modulePath + " : " + e2,
                                e2);
                    }
                }
                Slog.d(
                        "PackageSessionVerifier",
                        "Session "
                                + packageInstallerSession.sessionId
                                + " has following APEX packages: "
                                + arrayList);
            } catch (Throwable th3) {
                newWakeLock.release();
                throw th3;
            }
        }
    }
}
