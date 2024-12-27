package com.android.server.pm.parsing.library;

import com.android.internal.pm.parsing.pkg.ParsedPackage;

public class AndroidNetIpSecIkeUpdater extends PackageSharedLibraryUpdater {
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public final void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
        parsedPackage
                .removeUsesLibrary("android.net.ipsec.ike")
                .removeUsesOptionalLibrary("android.net.ipsec.ike");
    }
}
