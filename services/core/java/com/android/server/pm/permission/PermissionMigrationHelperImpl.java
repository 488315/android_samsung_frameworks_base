package com.android.server.pm.permission;

import android.content.pm.PackageManagerInternal;
import android.util.ArrayMap;

import com.android.permission.persistence.RuntimePermissionsState;
import com.android.server.LocalServices;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.Settings;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PermissionMigrationHelperImpl {
    public static Map toLegacyPermissionStates(List list) {
        ArrayMap arrayMap = new ArrayMap();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            RuntimePermissionsState.PermissionState permissionState =
                    (RuntimePermissionsState.PermissionState) list.get(i);
            arrayMap.put(
                    permissionState.getName(),
                    new PermissionMigrationHelper$LegacyPermissionState(
                            permissionState.getFlags(), permissionState.isGranted()));
        }
        return arrayMap;
    }

    public final int getLegacyPermissionStateVersion(int i) {
        int i2;
        PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl =
                (PackageManagerService.PackageManagerInternalImpl)
                        ((PackageManagerInternal)
                                LocalServices.getService(PackageManagerInternal.class));
        PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
        boolean z = PackageManagerService.DEBUG_COMPRESSION;
        synchronized (packageManagerTracedLock) {
            try {
                Settings.RuntimePermissionPersistence runtimePermissionPersistence =
                        PackageManagerService.this.mSettings.mRuntimePermissionsPersistence;
                synchronized (runtimePermissionPersistence.mLock) {
                    i2 = runtimePermissionPersistence.mVersions.get(i, 0);
                }
            } catch (Throwable th) {
                boolean z2 = PackageManagerService.DEBUG_COMPRESSION;
                throw th;
            }
        }
        if (i2 == -1) {
            return 0;
        }
        if (i2 != 0) {
            return i2;
        }
        return -1;
    }
}
