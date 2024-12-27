package com.android.server.pm.parsing.library;

import com.android.internal.pm.parsing.pkg.ParsedPackage;
import com.android.internal.util.ArrayUtils;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PackageSharedLibraryUpdater {
    public static void prefixImplicitDependency(ParsedPackage parsedPackage, String str) {
        List usesLibraries = parsedPackage.getUsesLibraries();
        List usesOptionalLibraries = parsedPackage.getUsesOptionalLibraries();
        if (ArrayUtils.contains(usesLibraries, str)
                || ArrayUtils.contains(usesOptionalLibraries, str)) {
            return;
        }
        if (ArrayUtils.contains(usesLibraries, "android.test.runner")) {
            parsedPackage.addUsesLibrary(0, str);
        } else if (ArrayUtils.contains(usesOptionalLibraries, "android.test.runner")) {
            parsedPackage.addUsesOptionalLibrary(0, str);
        }
    }

    public static void prefixRequiredLibrary(ParsedPackage parsedPackage, String str) {
        List usesLibraries = parsedPackage.getUsesLibraries();
        List usesOptionalLibraries = parsedPackage.getUsesOptionalLibraries();
        if (ArrayUtils.contains(usesLibraries, str)
                || ArrayUtils.contains(usesOptionalLibraries, str)) {
            return;
        }
        parsedPackage.addUsesLibrary(0, str);
    }

    public abstract void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2);
}
