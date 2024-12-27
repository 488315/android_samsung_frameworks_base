package com.android.server.pm.parsing.library;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class AndroidTestBaseUpdater extends PackageSharedLibraryUpdater {
    /* JADX WARN: Removed duplicated region for block: B:10:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0033  */
    @Override // com.android.server.pm.parsing.library.PackageSharedLibraryUpdater
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updatePackage(
            com.android.internal.pm.parsing.pkg.ParsedPackage r3, boolean r4, boolean r5) {
        /*
            r2 = this;
            if (r4 != 0) goto L24
            java.lang.String r2 = "platform_compat"
            android.os.IBinder r2 = android.os.ServiceManager.getService(r2)
            com.android.internal.compat.IPlatformCompat r2 = com.android.internal.compat.IPlatformCompat.Stub.asInterface(r2)
            r4 = r3
            com.android.internal.pm.parsing.pkg.AndroidPackageHidden r4 = (com.android.internal.pm.parsing.pkg.AndroidPackageHidden) r4     // Catch: java.lang.Throwable -> L1c
            android.content.pm.ApplicationInfo r4 = r4.toAppInfoWithoutState()     // Catch: java.lang.Throwable -> L1c
            r0 = 133396946(0x7f379d2, double:6.59068483E-316)
            boolean r2 = r2.isChangeEnabled(r0, r4)     // Catch: java.lang.Throwable -> L1c
            goto L2f
        L1c:
            r2 = move-exception
            java.lang.String r4 = "AndroidTestBaseUpdater"
            java.lang.String r5 = "Failed to get a response from PLATFORM_COMPAT_SERVICE"
            android.util.Log.e(r4, r5, r2)
        L24:
            int r2 = r3.getTargetSdkVersion()
            r4 = 29
            if (r2 <= r4) goto L2e
            r2 = 1
            goto L2f
        L2e:
            r2 = 0
        L2f:
            java.lang.String r4 = "android.test.base"
            if (r2 != 0) goto L37
            com.android.server.pm.parsing.library.PackageSharedLibraryUpdater.prefixRequiredLibrary(r3, r4)
            goto L3a
        L37:
            com.android.server.pm.parsing.library.PackageSharedLibraryUpdater.prefixImplicitDependency(r3, r4)
        L3a:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.parsing.library.AndroidTestBaseUpdater.updatePackage(com.android.internal.pm.parsing.pkg.ParsedPackage,"
                    + " boolean, boolean):void");
    }
}
