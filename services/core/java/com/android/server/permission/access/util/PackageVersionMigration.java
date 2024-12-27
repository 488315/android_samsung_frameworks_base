package com.android.server.permission.access.util;

import com.android.server.LocalServices;
import com.android.server.appop.AppOpMigrationHelperImpl;
import com.android.server.pm.permission.PermissionMigrationHelperImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PackageVersionMigration {
    public static int
            getVersion$frameworks__base__services__permission__android_common__services_permission_pre_jarjar(
                    int i) {
        int legacyPermissionStateVersion =
                ((PermissionMigrationHelperImpl)
                                LocalServices.getService(PermissionMigrationHelperImpl.class))
                        .getLegacyPermissionStateVersion(i);
        int legacyAppOpVersion =
                ((AppOpMigrationHelperImpl)
                                LocalServices.getService(AppOpMigrationHelperImpl.class))
                        .getLegacyAppOpVersion();
        if (legacyPermissionStateVersion == -1 && legacyAppOpVersion == -1) {
            throw new IllegalStateException(
                    "getVersion() called when there are no legacy files".toString());
        }
        if (legacyPermissionStateVersion >= 11 && legacyAppOpVersion >= 3) {
            return 15;
        }
        int i2 = 10;
        if (legacyPermissionStateVersion >= 10 && legacyAppOpVersion >= 3) {
            return 14;
        }
        if (legacyPermissionStateVersion >= 10 && legacyAppOpVersion >= 1) {
            return 13;
        }
        int i3 = 9;
        if (legacyPermissionStateVersion >= 9 && legacyAppOpVersion >= 1) {
            return 12;
        }
        int i4 = 8;
        if (legacyPermissionStateVersion >= 8 && legacyAppOpVersion >= 1) {
            return 11;
        }
        if (legacyPermissionStateVersion < 7 || legacyAppOpVersion < 1) {
            i2 = 6;
            if (legacyPermissionStateVersion < 6 || legacyAppOpVersion < 1) {
                i3 = 5;
                if (legacyPermissionStateVersion < 5 || legacyAppOpVersion < 1) {
                    i4 = 4;
                    if (legacyPermissionStateVersion >= 4 && legacyAppOpVersion >= 1) {
                        return 7;
                    }
                    if (legacyPermissionStateVersion < 3 || legacyAppOpVersion < 1) {
                        if (legacyPermissionStateVersion < 2 || legacyAppOpVersion < 1) {
                            if (legacyPermissionStateVersion < 1 || legacyAppOpVersion < 1) {
                                if (legacyPermissionStateVersion >= 0 && legacyAppOpVersion >= 1) {
                                    return 3;
                                }
                                if (legacyPermissionStateVersion < 0 || legacyAppOpVersion < 0) {
                                    return (legacyPermissionStateVersion < -1
                                                    || legacyAppOpVersion < 0)
                                            ? 0
                                            : 1;
                                }
                                return 2;
                            }
                        }
                    }
                }
                return i4;
            }
            return i3;
        }
        return i2;
    }
}
