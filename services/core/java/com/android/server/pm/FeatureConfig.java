package com.android.server.pm;

import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
interface FeatureConfig {
    void enableLogging(int i, boolean z);

    boolean isGloballyEnabled();

    boolean isLoggingEnabled(int i);

    void onSystemReady();

    boolean packageIsEnabled(AndroidPackage androidPackage);

    AppsFilterImpl.FeatureConfigImpl snapshot();

    void updatePackageState(PackageStateInternal packageStateInternal, boolean z);
}
