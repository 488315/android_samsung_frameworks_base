package com.android.server.pm.parsing.library;

import com.android.server.pm.parsing.pkg.ParsedPackage;
import com.android.server.pm.pkg.AndroidPackage;

/* loaded from: classes3.dex */
public class OrgApacheHttpLegacyUpdater extends PackageSharedLibraryUpdater {
    public static boolean apkTargetsApiLevelLessThanOrEqualToOMR1(AndroidPackage androidPackage) {
        return androidPackage.getTargetSdkVersion() < 28;
    }

    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
        if (apkTargetsApiLevelLessThanOrEqualToOMR1(parsedPackage)) {
            prefixRequiredLibrary(parsedPackage, "org.apache.http.legacy");
        }
    }
}
