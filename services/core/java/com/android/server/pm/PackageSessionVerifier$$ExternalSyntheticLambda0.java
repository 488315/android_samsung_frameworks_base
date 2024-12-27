package com.android.server.pm;

import android.content.pm.PackageManager;
import android.os.RemoteException;

import com.android.internal.content.InstallLocationUtils;

import java.util.Iterator;

public final /* synthetic */ class PackageSessionVerifier$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PackageSessionVerifier f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ PackageInstallerSession$$ExternalSyntheticLambda8 f$2;

    public /* synthetic */ PackageSessionVerifier$$ExternalSyntheticLambda0(
            PackageSessionVerifier packageSessionVerifier,
            Object obj,
            PackageInstallerSession$$ExternalSyntheticLambda8
                    packageInstallerSession$$ExternalSyntheticLambda8,
            int i) {
        this.$r8$classId = i;
        this.f$0 = packageSessionVerifier;
        this.f$1 = obj;
        this.f$2 = packageInstallerSession$$ExternalSyntheticLambda8;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                PackageSessionVerifier packageSessionVerifier = this.f$0;
                StagingManager.StagedSession stagedSession =
                        (StagingManager.StagedSession) this.f$1;
                PackageInstallerSession$$ExternalSyntheticLambda8
                        packageInstallerSession$$ExternalSyntheticLambda8 = this.f$2;
                packageSessionVerifier.getClass();
                try {
                    packageSessionVerifier.endVerification(stagedSession);
                    packageInstallerSession$$ExternalSyntheticLambda8.onResult(1, null);
                    return;
                } catch (PackageManagerException e) {
                    packageSessionVerifier.onVerificationFailure(
                            stagedSession,
                            packageInstallerSession$$ExternalSyntheticLambda8,
                            e.error,
                            e.getMessage());
                    return;
                }
            case 1:
                PackageSessionVerifier packageSessionVerifier2 = this.f$0;
                StagingManager.StagedSession stagedSession2 =
                        (StagingManager.StagedSession) this.f$1;
                PackageInstallerSession$$ExternalSyntheticLambda8
                        packageInstallerSession$$ExternalSyntheticLambda82 = this.f$2;
                packageSessionVerifier2.getClass();
                try {
                    try {
                        packageSessionVerifier2.checkActiveSessions(
                                InstallLocationUtils.getStorageManager().supportsCheckpoint());
                        packageSessionVerifier2.checkRollbacks(stagedSession2);
                        PackageInstallerSession.StagedSession stagedSession3 =
                                (PackageInstallerSession.StagedSession) stagedSession2;
                        if (PackageInstallerSession.this.params.isMultiPackage) {
                            Iterator it = stagedSession3.getChildSessions().iterator();
                            while (it.hasNext()) {
                                packageSessionVerifier2.checkOverlaps(
                                        stagedSession3, (StagingManager.StagedSession) it.next());
                            }
                        } else {
                            packageSessionVerifier2.checkOverlaps(stagedSession3, stagedSession3);
                        }
                        packageSessionVerifier2.mHandler.post(
                                new PackageSessionVerifier$$ExternalSyntheticLambda0(
                                        packageSessionVerifier2,
                                        stagedSession3,
                                        packageInstallerSession$$ExternalSyntheticLambda82,
                                        2));
                        return;
                    } catch (RemoteException e2) {
                        throw new PackageManagerException(
                                -110, "Can't query fs-checkpoint status : " + e2);
                    }
                } catch (PackageManagerException e3) {
                    packageSessionVerifier2.onVerificationFailure(
                            stagedSession2,
                            packageInstallerSession$$ExternalSyntheticLambda82,
                            e3.error,
                            e3.getMessage());
                    return;
                }
            case 2:
                PackageSessionVerifier packageSessionVerifier3 = this.f$0;
                StagingManager.StagedSession stagedSession4 =
                        (StagingManager.StagedSession) this.f$1;
                PackageInstallerSession$$ExternalSyntheticLambda8
                        packageInstallerSession$$ExternalSyntheticLambda83 = this.f$2;
                packageSessionVerifier3.getClass();
                try {
                    packageSessionVerifier3.verifyApex(stagedSession4);
                    packageSessionVerifier3.mHandler.post(
                            new PackageSessionVerifier$$ExternalSyntheticLambda0(
                                    packageSessionVerifier3,
                                    stagedSession4,
                                    packageInstallerSession$$ExternalSyntheticLambda83,
                                    0));
                    return;
                } catch (PackageManagerException e4) {
                    packageSessionVerifier3.onVerificationFailure(
                            stagedSession4,
                            packageInstallerSession$$ExternalSyntheticLambda83,
                            e4.error,
                            e4.getMessage());
                    return;
                }
            default:
                PackageSessionVerifier packageSessionVerifier4 = this.f$0;
                PackageInstallerSession packageInstallerSession =
                        (PackageInstallerSession) this.f$1;
                PackageInstallerSession$$ExternalSyntheticLambda8
                        packageInstallerSession$$ExternalSyntheticLambda84 = this.f$2;
                packageSessionVerifier4.getClass();
                try {
                    packageSessionVerifier4.storeSession(packageInstallerSession.mStagedSession);
                    if (packageInstallerSession.params.isMultiPackage) {
                        for (PackageInstallerSession packageInstallerSession2 :
                                packageInstallerSession.getChildSessions()) {
                            packageSessionVerifier4.checkApexUpdateAllowed(
                                    packageInstallerSession2);
                            packageSessionVerifier4.checkRebootlessApex(packageInstallerSession2);
                            packageSessionVerifier4.checkApexSignature(packageInstallerSession2);
                        }
                    } else {
                        packageSessionVerifier4.checkApexUpdateAllowed(packageInstallerSession);
                        packageSessionVerifier4.checkRebootlessApex(packageInstallerSession);
                        packageSessionVerifier4.checkApexSignature(packageInstallerSession);
                    }
                    packageSessionVerifier4.verifyAPK(
                            packageInstallerSession,
                            packageInstallerSession$$ExternalSyntheticLambda84);
                    return;
                } catch (PackageManagerException e5) {
                    packageInstallerSession.setSessionFailed(
                            e5.error,
                            PackageManager.installStatusToString(e5.error, e5.getMessage()));
                    packageInstallerSession$$ExternalSyntheticLambda84.onResult(
                            e5.error, e5.getMessage());
                    return;
                }
        }
    }
}
