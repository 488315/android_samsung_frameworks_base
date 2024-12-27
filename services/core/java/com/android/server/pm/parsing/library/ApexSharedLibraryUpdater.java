package com.android.server.pm.parsing.library;

import android.util.ArrayMap;

import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.modules.utils.build.UnboundedSdkLevel;
import com.android.server.SystemConfig;

public class ApexSharedLibraryUpdater extends PackageSharedLibraryUpdater {
    public final ArrayMap mSharedLibraries;

    public ApexSharedLibraryUpdater(ArrayMap arrayMap) {
        this.mSharedLibraries = arrayMap;
    }

    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public final void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
        int size = this.mSharedLibraries.size();
        for (int i = 0; i < size; i++) {
            SystemConfig.SharedLibraryEntry sharedLibraryEntry =
                    (SystemConfig.SharedLibraryEntry) this.mSharedLibraries.valueAt(i);
            String str = sharedLibraryEntry.onBootclasspathBefore;
            String str2 = sharedLibraryEntry.name;
            if (str != null) {
                int targetSdkVersion = parsedPackage.getTargetSdkVersion();
                String str3 = sharedLibraryEntry.onBootclasspathBefore;
                if (str3.length() == 0) {
                    throw new IllegalArgumentException();
                }
                if (!Character.isUpperCase(str3.charAt(0))
                        ? targetSdkVersion < Integer.parseInt(str3)
                        : targetSdkVersion < 10000) {
                    if (UnboundedSdkLevel.isAtLeast(str3)) {
                        PackageSharedLibraryUpdater.prefixRequiredLibrary(parsedPackage, str2);
                    }
                }
            }
            if (sharedLibraryEntry.canBeSafelyIgnored) {
                parsedPackage.removeUsesLibrary(str2).removeUsesOptionalLibrary(str2);
            }
        }
    }
}
