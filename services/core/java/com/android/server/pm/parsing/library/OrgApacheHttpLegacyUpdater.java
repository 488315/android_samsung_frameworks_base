package com.android.server.pm.parsing.library;

import com.android.internal.pm.parsing.pkg.ParsedPackage;

public class OrgApacheHttpLegacyUpdater extends PackageSharedLibraryUpdater {
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public final void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
        if (parsedPackage.getTargetSdkVersion() < 28) {
            PackageSharedLibraryUpdater.prefixRequiredLibrary(
                    parsedPackage, "org.apache.http.legacy");
        }
    }
}
