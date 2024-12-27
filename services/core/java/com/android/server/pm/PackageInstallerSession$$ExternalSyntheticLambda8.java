package com.android.server.pm;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PackageInstallerSession$$ExternalSyntheticLambda8 {
    public final /* synthetic */ PackageInstallerSession f$0;

    public final void onResult(final int i, final String str) {
        final PackageInstallerSession packageInstallerSession = this.f$0;
        packageInstallerSession.mHandler.post(
                new Runnable() { // from class:
                                 // com.android.server.pm.PackageInstallerSession$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        PackageInstallerSession packageInstallerSession2 =
                                PackageInstallerSession.this;
                        int i2 = i;
                        String str2 = str;
                        synchronized (packageInstallerSession2.mLock) {
                            try {
                                if (packageInstallerSession2.mStageDirInUse) {
                                    packageInstallerSession2.mStageDirInUse = false;
                                    PackageInstallerSession$$ExternalSyntheticLambda0
                                            packageInstallerSession$$ExternalSyntheticLambda0 =
                                                    packageInstallerSession2
                                                            .mPendingAbandonCallback;
                                    packageInstallerSession2.mPendingAbandonCallback = null;
                                    if (packageInstallerSession$$ExternalSyntheticLambda0 != null) {
                                        packageInstallerSession$$ExternalSyntheticLambda0.run();
                                        return;
                                    }
                                }
                            } finally {
                            }
                        }
                        if (i2 != 1) {
                            packageInstallerSession2.onSessionVerificationFailure(i2, str2);
                        } else if (!packageInstallerSession2.params.isStaged) {
                            packageInstallerSession2.install();
                        } else {
                            packageInstallerSession2.mStagingManager.commitSession(
                                    packageInstallerSession2.mStagedSession);
                            packageInstallerSession2.sendUpdateToRemoteStatusReceiver(
                                    "Session staged", null, 1, false);
                        }
                    }
                });
    }
}
