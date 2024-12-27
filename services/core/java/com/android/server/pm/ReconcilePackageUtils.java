package com.android.server.pm;

import android.os.Build;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ReconcilePackageUtils {
    public static final boolean ALLOW_NON_PRELOADS_SYSTEM_SHAREDUIDS;

    static {
        ALLOW_NON_PRELOADS_SYSTEM_SHAREDUIDS =
                Build.IS_DEBUGGABLE || !Flags.restrictNonpreloadsSystemShareduids();
    }

    /* JADX WARN: Code restructure failed: missing block: B:207:0x0284, code lost:

       if (r9.mName.startsWith("com.samsung.") == false) goto L136;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:149:0x030b A[Catch: IllegalArgumentException -> 0x028c, PackageManagerException -> 0x02b3, TryCatch #4 {PackageManagerException -> 0x02b3, blocks: (B:145:0x02aa, B:149:0x030b, B:151:0x0315, B:152:0x031d, B:154:0x0323, B:156:0x032f, B:158:0x033f, B:165:0x034f, B:166:0x0353, B:168:0x0357, B:170:0x02ba, B:171:0x02f7), top: B:144:0x02aa }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0371  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x044a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x043a  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x043d A[SYNTHETIC] */
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
