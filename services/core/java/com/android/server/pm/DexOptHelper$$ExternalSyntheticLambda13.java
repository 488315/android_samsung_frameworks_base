package com.android.server.pm;

import com.android.server.pm.pkg.PackageStateInternal;

import java.util.Comparator;

public final /* synthetic */ class DexOptHelper$$ExternalSyntheticLambda13 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Long.compare(
                ((PackageStateInternal) obj2)
                        .getTransientState()
                        .getLatestForegroundPackageUseTimeInMills(),
                ((PackageStateInternal) obj)
                        .getTransientState()
                        .getLatestForegroundPackageUseTimeInMills());
    }
}
