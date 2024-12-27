package com.android.server.pm;

import android.os.Build;
import android.util.ArraySet;

import java.util.Set;

public final class PackageManagerServiceTestParams {
    public final Set initialNonStoppedSystemPackages;
    public final ChangedPackagesTracker changedPackagesTracker = new ChangedPackagesTracker();
    public final int priorSdkVersion = -1;

    public PackageManagerServiceTestParams() {
        String str = Build.VERSION.INCREMENTAL;
        this.initialNonStoppedSystemPackages = new ArraySet();
    }
}
