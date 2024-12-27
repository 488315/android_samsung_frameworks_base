package com.android.server.pm.parsing.library;

import com.android.internal.pm.parsing.pkg.ParsedPackage;

public class AndroidHidlUpdater extends PackageSharedLibraryUpdater {
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public final void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
        if (parsedPackage.getTargetSdkVersion() > 28 || !(z || z2)) {
            parsedPackage
                    .removeUsesLibrary("android.hidl.base-V1.0-java")
                    .removeUsesOptionalLibrary("android.hidl.base-V1.0-java");
            parsedPackage
                    .removeUsesLibrary("android.hidl.manager-V1.0-java")
                    .removeUsesOptionalLibrary("android.hidl.manager-V1.0-java");
        } else {
            PackageSharedLibraryUpdater.prefixRequiredLibrary(
                    parsedPackage, "android.hidl.base-V1.0-java");
            PackageSharedLibraryUpdater.prefixRequiredLibrary(
                    parsedPackage, "android.hidl.manager-V1.0-java");
        }
    }
}
