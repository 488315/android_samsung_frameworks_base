package com.android.server.pm;

import android.content.pm.PackageInstaller;

import java.util.function.ToLongFunction;

public final /* synthetic */ class BackgroundInstallControlService$$ExternalSyntheticLambda2
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((PackageInstaller.SessionInfo) obj).getCreatedMillis();
    }
}
