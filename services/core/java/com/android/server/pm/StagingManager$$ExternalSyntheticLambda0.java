package com.android.server.pm;

import com.android.server.rollback.WatchdogRollbackLogger;

import java.util.ArrayList;

public final /* synthetic */ class StagingManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StagingManager$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                StagingManager stagingManager = (StagingManager) obj;
                synchronized (stagingManager.mFailedPackageNames) {
                    try {
                        if (!((ArrayList) stagingManager.mFailedPackageNames).isEmpty()) {
                            WatchdogRollbackLogger.logApexdRevert(
                                    stagingManager.mContext,
                                    stagingManager.mFailedPackageNames,
                                    stagingManager.mNativeFailureReason);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            default:
                ((PackageInstallerSession.StagedSession) ((StagingManager.StagedSession) obj))
                        .verifySession();
                return;
        }
    }
}
