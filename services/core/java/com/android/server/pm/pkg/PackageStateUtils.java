package com.android.server.pm.pkg;

import android.util.SparseArray;

import com.android.internal.pm.parsing.pkg.AndroidPackageInternal;
import com.android.internal.pm.pkg.component.ParsedMainComponent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PackageStateUtils {
    public static long getEarliestFirstInstallTime(SparseArray sparseArray) {
        if (sparseArray == null || sparseArray.size() == 0) {
            return 0L;
        }
        long j = Long.MAX_VALUE;
        for (int i = 0; i < sparseArray.size(); i++) {
            long firstInstallTimeMillis =
                    ((PackageUserStateInternal) sparseArray.valueAt(i)).getFirstInstallTimeMillis();
            if (firstInstallTimeMillis != 0 && firstInstallTimeMillis < j) {
                j = firstInstallTimeMillis;
            }
        }
        if (j == Long.MAX_VALUE) {
            return 0L;
        }
        return j;
    }

    public static boolean isEnabledAndMatches(
            PackageStateInternal packageStateInternal,
            ParsedMainComponent parsedMainComponent,
            long j,
            int i) {
        AndroidPackageInternal pkg = packageStateInternal.getPkg();
        if (pkg == null) {
            return false;
        }
        return PackageUserStateUtils.isMatch(
                packageStateInternal.getUserStateOrDefault(i),
                packageStateInternal.isSystem(),
                pkg.isEnabled(),
                parsedMainComponent.isEnabled(),
                parsedMainComponent.isDirectBootAware(),
                parsedMainComponent.getName(),
                j);
    }

    public static int[] queryInstalledUsers(PackageStateInternal packageStateInternal, int[] iArr) {
        int i = 0;
        for (int i2 : iArr) {
            if (packageStateInternal.getUserStateOrDefault(i2).isInstalled()) {
                i++;
            }
        }
        int[] iArr2 = new int[i];
        int i3 = 0;
        for (int i4 : iArr) {
            if (packageStateInternal.getUserStateOrDefault(i4).isInstalled()) {
                iArr2[i3] = i4;
                i3++;
            }
        }
        return iArr2;
    }
}
