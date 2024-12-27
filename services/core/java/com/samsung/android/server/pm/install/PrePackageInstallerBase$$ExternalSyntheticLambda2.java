package com.samsung.android.server.pm.install;

import java.util.function.Consumer;

public final /* synthetic */ class PrePackageInstallerBase$$ExternalSyntheticLambda2
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((PrePackageInstallerBase.ApkFile) obj).removeCacheFile();
    }
}
