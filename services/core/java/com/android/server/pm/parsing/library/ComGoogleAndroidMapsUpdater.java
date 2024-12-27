package com.android.server.pm.parsing.library;

import com.android.internal.pm.parsing.pkg.ParsedPackage;

public class ComGoogleAndroidMapsUpdater extends PackageSharedLibraryUpdater {
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public final void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
        parsedPackage.removeUsesLibrary("com.google.android.maps");
        parsedPackage.removeUsesOptionalLibrary("com.google.android.maps");
    }
}
