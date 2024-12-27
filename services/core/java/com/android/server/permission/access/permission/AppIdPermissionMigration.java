package com.android.server.permission.access.permission;

import android.content.pm.PermissionInfo;
import android.util.ArrayMap;
import com.android.server.permission.access.immutable.MutableIndexedMap;
import com.android.server.pm.permission.PermissionMigrationHelper$LegacyPermission;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppIdPermissionMigration {
    public static void migratePermissions(MutableIndexedMap mutableIndexedMap, Map map) {
        Iterator it = ((ArrayMap) map).entrySet().iterator();
        while (it.hasNext()) {
            PermissionMigrationHelper$LegacyPermission permissionMigrationHelper$LegacyPermission = (PermissionMigrationHelper$LegacyPermission) ((Map.Entry) it.next()).getValue();
            PermissionInfo permissionInfo = permissionMigrationHelper$LegacyPermission.mPermissionInfo;
            mutableIndexedMap.put(permissionInfo.name, new Permission(permissionInfo, false, permissionMigrationHelper$LegacyPermission.mType, 0));
        }
    }
}
