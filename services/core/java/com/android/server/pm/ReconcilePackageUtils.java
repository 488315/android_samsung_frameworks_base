package com.android.server.pm;

import android.os.Build;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;

public abstract class ReconcilePackageUtils {
    public static final boolean ALLOW_NON_PRELOADS_SYSTEM_SHAREDUIDS;

    static {
        ALLOW_NON_PRELOADS_SYSTEM_SHAREDUIDS =
                Build.IS_DEBUGGABLE || !Flags.restrictNonpreloadsSystemShareduids();
    }

    /* JADX WARN: Code restructure failed: missing block: B:207:0x0284, code lost:

       if (r9.mName.startsWith("com.samsung.") == false) goto L136;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.List reconcilePackages(
            java.util.List r29,
            java.util.Map r30,
            java.util.Map r31,
            com.android.server.pm.SharedLibrariesImpl r32,
            com.android.server.pm.KeySetManagerService r33,
            com.android.server.pm.Settings r34,
            com.android.server.SystemConfig r35) {
        /*
            Method dump skipped, instructions count: 1116
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.ReconcilePackageUtils.reconcilePackages(java.util.List,"
                    + " java.util.Map, java.util.Map, com.android.server.pm.SharedLibrariesImpl,"
                    + " com.android.server.pm.KeySetManagerService, com.android.server.pm.Settings,"
                    + " com.android.server.SystemConfig):java.util.List");
    }
}
