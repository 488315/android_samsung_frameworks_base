package com.android.server.compat.overrides;

import android.app.compat.PackageOverride;
import android.content.pm.PackageManager;

import java.util.Comparator;
import java.util.regex.Pattern;

public final class AppCompatOverridesParser {
    public static final Pattern BOOLEAN_PATTERN = Pattern.compile("true|false", 2);
    public final PackageManager mPackageManager;

    public abstract class PackageOverrideComparator implements Comparator {
        public static long getVersionProximity(PackageOverride packageOverride, long j) {
            if (isVersionAfterRange(packageOverride, j)) {
                return j - packageOverride.getMaxVersionCode();
            }
            if (packageOverride.getMinVersionCode() > j) {
                return packageOverride.getMinVersionCode() - j;
            }
            return 0L;
        }

        public static boolean isVersionAfterRange(PackageOverride packageOverride, long j) {
            return packageOverride.getMaxVersionCode() < j;
        }

        public static boolean isVersionInRange(PackageOverride packageOverride, long j) {
            return packageOverride.getMinVersionCode() <= j
                    && j <= packageOverride.getMaxVersionCode();
        }
    }

    public AppCompatOverridesParser(PackageManager packageManager) {
        this.mPackageManager = packageManager;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Map parsePackageOverrides(
            java.lang.String r18, java.lang.String r19, long r20, java.util.Set r22) {
        /*
            Method dump skipped, instructions count: 431
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.compat.overrides.AppCompatOverridesParser.parsePackageOverrides(java.lang.String,"
                    + " java.lang.String, long, java.util.Set):java.util.Map");
    }
}
