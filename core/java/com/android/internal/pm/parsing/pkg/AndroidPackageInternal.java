package com.android.internal.pm.parsing.pkg;

import com.android.internal.content.om.OverlayConfig;
import com.android.server.pm.pkg.AndroidPackage;

public interface AndroidPackageInternal
        extends AndroidPackage, OverlayConfig.PackageProvider.Package {
    String[] getUsesLibrariesSorted();

    String[] getUsesOptionalLibrariesSorted();

    String[] getUsesSdkLibrariesSorted();

    String[] getUsesStaticLibrariesSorted();
}
