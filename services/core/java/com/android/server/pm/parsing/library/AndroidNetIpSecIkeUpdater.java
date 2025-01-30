package com.android.server.pm.parsing.library;

import com.android.server.pm.parsing.pkg.ParsedPackage;

/* loaded from: classes3.dex */
public class AndroidNetIpSecIkeUpdater extends PackageSharedLibraryUpdater {
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
        PackageSharedLibraryUpdater.removeLibrary(parsedPackage, "android.net.ipsec.ike");
    }
}
