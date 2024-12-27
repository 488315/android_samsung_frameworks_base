package com.android.server.pm;

import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;

interface FeatureConfig {
    void enableLogging(int i, boolean z);

    boolean isGloballyEnabled();

    boolean isLoggingEnabled(int i);

    void onSystemReady();

    boolean packageIsEnabled(AndroidPackage androidPackage);

    AppsFilterImpl.FeatureConfigImpl snapshot();

    void updatePackageState(PackageStateInternal packageStateInternal, boolean z);
}
