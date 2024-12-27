package com.android.server.pm.pkg;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PackageUserStateUtils {
    public static boolean isAvailable(PackageUserState packageUserState, long j) {
        boolean z = (4194304 & j) != 0;
        boolean z2 =
                (((8192 & j) > 0L ? 1 : ((8192 & j) == 0L ? 0 : -1)) != 0)
                        || (((j & 4294967296L) > 0L ? 1 : ((j & 4294967296L) == 0L ? 0 : -1)) != 0);
        if (z) {
            return true;
        }
        if (!packageUserState.isInstalled()) {
            return z2 && packageUserState.dataExists();
        }
        if (packageUserState.isHidden()) {
            return z2;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0073, code lost:

       if ((r11 & 32768) == 0) goto L39;
    */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0080  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isEnabled(
            com.android.server.pm.pkg.PackageUserStateInternal r7,
            boolean r8,
            boolean r9,
            java.lang.String r10,
            long r11) {
        /*
            r0 = 512(0x200, double:2.53E-321)
            long r0 = r0 & r11
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 1
            if (r0 == 0) goto Lb
            return r1
        Lb:
            r4 = 8589934592(0x200000000, double:4.243991582E-314)
            long r4 = r4 & r11
            int r0 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            r4 = 0
            if (r0 != 0) goto L1d
            boolean r0 = r7.isQuarantined()
            if (r0 == 0) goto L1d
            return r4
        L1d:
            int r0 = r7.getEnabledState()
            if (r0 == 0) goto L76
            r5 = 2
            if (r0 == r5) goto L75
            r5 = 3
            if (r0 == r5) goto L75
            r5 = 4
            if (r0 == r5) goto L2d
            goto L79
        L2d:
            r5 = 536870912(0x20000000, double:2.652494739E-315)
            long r5 = r5 & r11
            int r0 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r0 == 0) goto L6d
            java.lang.String r0 = r7.getLastDisableAppCaller()
            if (r0 == 0) goto L6d
            java.lang.String r0 = "auto_disabler"
            java.lang.String r5 = r7.getLastDisableAppCaller()
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L6d
            if (r10 == 0) goto L6d
            android.util.ArraySet r8 = r7.getEnabledComponents()
            if (r8 == 0) goto L5b
            android.util.ArraySet r8 = r7.getEnabledComponents()
            boolean r8 = r8.contains(r10)
            if (r8 == 0) goto L5b
            r9 = r1
            goto L6c
        L5b:
            android.util.ArraySet r8 = r7.getDisabledComponents()
            if (r8 == 0) goto L6c
            android.util.ArraySet r7 = r7.getDisabledComponents()
            boolean r7 = r7.contains(r10)
            if (r7 == 0) goto L6c
            r9 = r4
        L6c:
            return r9
        L6d:
            r5 = 32768(0x8000, double:1.61895E-319)
            long r11 = r11 & r5
            int r11 = (r11 > r2 ? 1 : (r11 == r2 ? 0 : -1))
            if (r11 != 0) goto L76
        L75:
            return r4
        L76:
            if (r8 != 0) goto L79
            return r4
        L79:
            boolean r8 = r7.isComponentEnabled(r10)
            if (r8 == 0) goto L80
            return r1
        L80:
            boolean r7 = r7.isComponentDisabled(r10)
            if (r7 == 0) goto L87
            return r4
        L87:
            return r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.pkg.PackageUserStateUtils.isEnabled(com.android.server.pm.pkg.PackageUserStateInternal,"
                    + " boolean, boolean, java.lang.String, long):boolean");
    }

    public static boolean isMatch(
            PackageUserStateInternal packageUserStateInternal,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            String str,
            long j) {
        boolean z5 = (4202496 & j) != 0;
        if ((!isAvailable(packageUserStateInternal, j) && (!z || !z5))
                || !isEnabled(packageUserStateInternal, z2, z3, str, j)) {
            return false;
        }
        if ((1048576 & j) == 0 || z) {
            return (((262144 & j) > 0L ? 1 : ((262144 & j) == 0L ? 0 : -1)) != 0 && !z4)
                    || (((524288 & j) > 0L ? 1 : ((524288 & j) == 0L ? 0 : -1)) != 0 && z4);
        }
        return false;
    }
}
