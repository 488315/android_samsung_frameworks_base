package com.android.server.pm.parsing.library;

import com.android.internal.pm.parsing.pkg.ParsedPackage;

public class PackageBackwardCompatibility extends PackageSharedLibraryUpdater {
    public static final PackageBackwardCompatibility INSTANCE;
    public final boolean mBootClassPathContainsATB;
    public final PackageSharedLibraryUpdater[] mPackageUpdaters;

    public class AndroidTestRunnerSplitUpdater extends PackageSharedLibraryUpdater {
        @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
        public final void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
            PackageSharedLibraryUpdater.prefixImplicitDependency(
                    parsedPackage, "android.test.mock");
        }
    }

    public class RemoveUnnecessaryAndroidTestBaseLibrary extends PackageSharedLibraryUpdater {
        @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
        public final void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
            parsedPackage
                    .removeUsesLibrary("android.test.base")
                    .removeUsesOptionalLibrary("android.test.base");
        }
    }

    public class RemoveUnnecessaryOrgApacheHttpLegacyLibrary extends PackageSharedLibraryUpdater {
        @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
        public final void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
            parsedPackage
                    .removeUsesLibrary("org.apache.http.legacy")
                    .removeUsesOptionalLibrary("org.apache.http.legacy");
        }
    }

    static {
        /*
            java.lang.String r0 = "PackageBackwardCompatibility"
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            com.android.server.pm.parsing.library.AndroidNetIpSecIkeUpdater r2 = new com.android.server.pm.parsing.library.AndroidNetIpSecIkeUpdater
            r2.<init>()
            r1.add(r2)
            com.android.server.pm.parsing.library.ComGoogleAndroidMapsUpdater r2 = new com.android.server.pm.parsing.library.ComGoogleAndroidMapsUpdater
            r2.<init>()
            r1.add(r2)
            com.android.server.pm.parsing.library.OrgApacheHttpLegacyUpdater r2 = new com.android.server.pm.parsing.library.OrgApacheHttpLegacyUpdater
            r2.<init>()
            r1.add(r2)
            com.android.server.pm.parsing.library.AndroidHidlUpdater r2 = new com.android.server.pm.parsing.library.AndroidHidlUpdater
            r2.<init>()
            r1.add(r2)
            com.android.server.pm.parsing.library.PackageBackwardCompatibility$AndroidTestRunnerSplitUpdater r2 = new com.android.server.pm.parsing.library.PackageBackwardCompatibility$AndroidTestRunnerSplitUpdater
            r2.<init>()
            r1.add(r2)
            java.lang.String r2 = "android.content.pm.AndroidTestBaseUpdater"
            r3 = 1
            r4 = 0
            java.lang.Class<com.android.internal.pm.pkg.parsing.ParsingPackage> r5 = com.android.internal.pm.pkg.parsing.ParsingPackage.class
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch: java.lang.ClassNotFoundException -> L48
            java.lang.Class r2 = r5.loadClass(r2)     // Catch: java.lang.ClassNotFoundException -> L48
            if (r2 == 0) goto L41
            r2 = r3
            goto L42
        L41:
            r2 = r4
        L42:
            java.lang.String r5 = "Loaded android.content.pm.AndroidTestBaseUpdater"
            android.util.Log.i(r0, r5)     // Catch: java.lang.ClassNotFoundException -> L49
            goto L4e
        L48:
            r2 = r4
        L49:
            java.lang.String r5 = "Could not find android.content.pm.AndroidTestBaseUpdater, ignoring"
            android.util.Log.i(r0, r5)
        L4e:
            if (r2 == 0) goto L59
            com.android.server.pm.parsing.library.AndroidTestBaseUpdater r0 = new com.android.server.pm.parsing.library.AndroidTestBaseUpdater
            r0.<init>()
            r1.add(r0)
            goto L61
        L59:
            com.android.server.pm.parsing.library.PackageBackwardCompatibility$RemoveUnnecessaryAndroidTestBaseLibrary r0 = new com.android.server.pm.parsing.library.PackageBackwardCompatibility$RemoveUnnecessaryAndroidTestBaseLibrary
            r0.<init>()
            r1.add(r0)
        L61:
            r0 = r2 ^ 1
            com.android.server.pm.parsing.library.ApexSharedLibraryUpdater r2 = new com.android.server.pm.parsing.library.ApexSharedLibraryUpdater
            com.android.server.SystemConfig r3 = com.android.server.SystemConfig.getInstance()
            android.util.ArrayMap r3 = r3.mSharedLibraries
            r2.<init>(r3)
            r1.add(r2)
            com.android.server.pm.parsing.library.PackageSharedLibraryUpdater[] r2 = new com.android.server.pm.parsing.library.PackageSharedLibraryUpdater[r4]
            java.lang.Object[] r1 = r1.toArray(r2)
            com.android.server.pm.parsing.library.PackageSharedLibraryUpdater[] r1 = (com.android.server.pm.parsing.library.PackageSharedLibraryUpdater[]) r1
            com.android.server.pm.parsing.library.PackageBackwardCompatibility r2 = new com.android.server.pm.parsing.library.PackageBackwardCompatibility
            r2.<init>(r0, r1)
            com.android.server.pm.parsing.library.PackageBackwardCompatibility.INSTANCE = r2
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.parsing.library.PackageBackwardCompatibility.<clinit>():void");
    }

    public PackageBackwardCompatibility(
            boolean z, PackageSharedLibraryUpdater[] packageSharedLibraryUpdaterArr) {
        this.mBootClassPathContainsATB = z;
        this.mPackageUpdaters = packageSharedLibraryUpdaterArr;
    }

    public static boolean bootClassPathContainsATB() {
        return INSTANCE.mBootClassPathContainsATB;
    }

    public static PackageSharedLibraryUpdater getInstance() {
        return INSTANCE;
    }

    public static void modifySharedLibraries(ParsedPackage parsedPackage, boolean z, boolean z2) {
        INSTANCE.updatePackage(parsedPackage, z, z2);
    }

    public PackageSharedLibraryUpdater[] getPackageUpdaters() {
        return this.mPackageUpdaters;
    }

    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    public final void updatePackage(ParsedPackage parsedPackage, boolean z, boolean z2) {
        for (PackageSharedLibraryUpdater packageSharedLibraryUpdater : this.mPackageUpdaters) {
            packageSharedLibraryUpdater.updatePackage(parsedPackage, z, z2);
        }
    }
}
