package com.android.server.pm;

import com.android.server.pm.pkg.PackageStateInternal;

import java.util.function.ToLongFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class OtaDexoptService$$ExternalSyntheticLambda1
        implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((PackageStateInternal) obj)
                .getTransientState()
                .getLatestForegroundPackageUseTimeInMills();
    }
}
