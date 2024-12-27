package com.android.server.pm;

import android.os.Build;
import android.util.ArraySet;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PackageManagerServiceTestParams {
    public final Set initialNonStoppedSystemPackages;
    public final ChangedPackagesTracker changedPackagesTracker = new ChangedPackagesTracker();
    public final int priorSdkVersion = -1;

    public PackageManagerServiceTestParams() {
        String str = Build.VERSION.INCREMENTAL;
        this.initialNonStoppedSystemPackages = new ArraySet();
    }
}
