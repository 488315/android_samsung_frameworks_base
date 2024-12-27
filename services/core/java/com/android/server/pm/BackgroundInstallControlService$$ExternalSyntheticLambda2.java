package com.android.server.pm;

import android.content.pm.PackageInstaller;

import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class BackgroundInstallControlService$$ExternalSyntheticLambda2
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((PackageInstaller.SessionInfo) obj).getCreatedMillis();
    }
}
