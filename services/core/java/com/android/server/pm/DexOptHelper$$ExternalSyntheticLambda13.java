package com.android.server.pm;

import com.android.server.pm.pkg.PackageStateInternal;
import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DexOptHelper$$ExternalSyntheticLambda13 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Long.compare(((PackageStateInternal) obj2).getTransientState().getLatestForegroundPackageUseTimeInMills(), ((PackageStateInternal) obj).getTransientState().getLatestForegroundPackageUseTimeInMills());
    }
}
