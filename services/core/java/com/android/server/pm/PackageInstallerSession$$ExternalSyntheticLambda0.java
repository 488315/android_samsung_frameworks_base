package com.android.server.pm;

import android.apex.ApexSessionInfo;
import android.util.Slog;

import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PackageInstallerSession$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ PackageInstallerSession f$0;

    public /* synthetic */ PackageInstallerSession$$ExternalSyntheticLambda0(
            PackageInstallerSession packageInstallerSession) {
        this.f$0 = packageInstallerSession;
    }

    @Override // java.lang.Runnable
    public final void run() {
        StagingManager.StagedSession stagedSession;
        PackageInstallerSession packageInstallerSession = this.f$0;
        packageInstallerSession.assertNotLocked("abandonStaged");
        if (packageInstallerSession.params.isStaged && packageInstallerSession.mCommitted.get()) {
            StagingManager stagingManager = packageInstallerSession.mStagingManager;
            PackageInstallerSession.StagedSession stagedSession2 =
                    packageInstallerSession.mStagedSession;
            stagingManager.getClass();
            int i = PackageInstallerSession.this.sessionId;
            if (stagedSession2.isInTerminalState()) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "Cannot abort session in final state: ", "StagingManager");
            } else {
                if (!PackageInstallerSession.this.isDestroyed()) {
                    throw new IllegalStateException(
                            "Committed session must be destroyed before aborting it from"
                                + " StagingManager");
                }
                synchronized (stagingManager.mStagedSessions) {
                    stagedSession =
                            (StagingManager.StagedSession) stagingManager.mStagedSessions.get(i);
                }
                if (stagedSession == null) {
                    BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                            i, "Session ", " has been abandoned already", "StagingManager");
                } else {
                    if (stagedSession2.isSessionReady()) {
                        if (stagedSession2.containsApexSession()) {
                            PackageInstallerSession packageInstallerSession2 =
                                    PackageInstallerSession.this;
                            int i2 = packageInstallerSession2.sessionId;
                            ApexManager apexManager = stagingManager.mApexManager;
                            ApexSessionInfo stagedSessionInfo =
                                    apexManager.getStagedSessionInfo(i2);
                            if (stagedSessionInfo != null
                                    && !stagedSessionInfo.isUnknown
                                    && !stagedSessionInfo.isActivationFailed
                                    && !stagedSessionInfo.isSuccess
                                    && !stagedSessionInfo.isReverted) {
                                try {
                                    ((ApexManager.ApexManagerImpl) apexManager)
                                            .waitForApexService()
                                            .abortStagedSession(packageInstallerSession2.sessionId);
                                } catch (Exception e) {
                                    Slog.e("ApexManager", e.getMessage(), e);
                                    VaultKeeperService$$ExternalSyntheticOutline0.m(
                                            new StringBuilder("Failed to abort apex session "),
                                            PackageInstallerSession.this.sessionId,
                                            "StagingManager");
                                }
                            }
                        }
                        if (stagedSession2.containsApexSession()) {
                            stagingManager.notifyStagedApexObservers();
                        }
                    }
                    stagingManager.abortSession(stagedSession2);
                }
            }
        }
        packageInstallerSession.destroy("Session was abandoned");
        packageInstallerSession.dispatchSessionFinished(-115, "Session was abandoned", null);
        packageInstallerSession.maybeFinishChildSessions(
                -115, "Session was abandoned because the parent session is abandoned");
    }
}
