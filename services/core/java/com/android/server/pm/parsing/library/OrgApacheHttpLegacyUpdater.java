package com.android.server.pm.parsing.library;

import com.android.internal.pm.parsing.pkg.ParsedPackage;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class OrgApacheHttpLegacyUpdater extends PackageSharedLibraryUpdater {
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public final void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
        if (parsedPackage.getTargetSdkVersion() < 28) {
            PackageSharedLibraryUpdater.prefixRequiredLibrary(
                    parsedPackage, "org.apache.http.legacy");
        }
    }
}
