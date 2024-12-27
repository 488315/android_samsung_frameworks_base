package com.android.server.pm;

import com.android.server.pm.pkg.PackageStateInternal;

import java.util.function.ToLongFunction;

public final /* synthetic */ class OtaDexoptService$$ExternalSyntheticLambda1
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((PackageStateInternal) obj)
                .getTransientState()
                .getLatestForegroundPackageUseTimeInMills();
    }
}
