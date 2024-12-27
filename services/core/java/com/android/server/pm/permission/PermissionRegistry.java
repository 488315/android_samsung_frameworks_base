package com.android.server.pm.permission;

import android.content.pm.PermissionInfo;
import android.os.UserHandle;
import android.util.ArrayMap;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import libcore.util.EmptyArray;

import java.util.Collection;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PermissionRegistry {
    public final ArrayMap mPermissions = new ArrayMap();
    public final ArrayMap mPermissionTrees = new ArrayMap();
    public final ArrayMap mPermissionGroups = new ArrayMap();
    public final ArrayMap mAppOpPermissionPackages = new ArrayMap();

    public final Permission enforcePermissionTree(int i, String str) {
        Permission findPermissionTree;
        Collection values = this.mPermissionTrees.values();
        if (str == null
                || (findPermissionTree = Permission.findPermissionTree(str, values)) == null
                || findPermissionTree.mUid != UserHandle.getAppId(i)) {
            throw new SecurityException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i,
                            "Calling uid ",
                            " is not allowed to add to or remove from the permission tree"));
        }
        return findPermissionTree;
    }

    public final Permission getPermission(String str) {
        return (Permission) this.mPermissions.get(str);
    }

    public final void transferPermissions(String str, String str2) {
        int i = 0;
        while (i < 2) {
            for (Permission permission :
                    (i == 0 ? this.mPermissionTrees : this.mPermissions).values()) {
                if (str.equals(permission.mPermissionInfo.packageName)) {
                    PermissionInfo permissionInfo = new PermissionInfo();
                    PermissionInfo permissionInfo2 = permission.mPermissionInfo;
                    permissionInfo.name = permissionInfo2.name;
                    permissionInfo.packageName = str2;
                    permissionInfo.protectionLevel = permissionInfo2.protectionLevel;
                    permission.mPermissionInfo = permissionInfo;
                    permission.mReconciled = false;
                    permission.mUid = 0;
                    permission.mGids = EmptyArray.INT;
                    permission.mGidsPerUser = false;
                }
            }
            i++;
        }
    }
}
