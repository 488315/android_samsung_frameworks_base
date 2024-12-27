package com.android.server.pm.permission;

import android.content.pm.PackageManager;
import android.util.ArrayMap;
import android.util.ArraySet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UidPermissionState {
    public boolean mMissing;
    public ArrayMap mPermissions;

    public UidPermissionState(UidPermissionState uidPermissionState) {
        this.mMissing = uidPermissionState.mMissing;
        if (uidPermissionState.mPermissions != null) {
            this.mPermissions = new ArrayMap();
            int size = uidPermissionState.mPermissions.size();
            for (int i = 0; i < size; i++) {
                String str = (String) uidPermissionState.mPermissions.keyAt(i);
                PermissionState permissionState =
                        (PermissionState) uidPermissionState.mPermissions.valueAt(i);
                ArrayMap arrayMap = this.mPermissions;
                PermissionState permissionState2 = new PermissionState(permissionState.mPermission);
                permissionState2.mGranted = permissionState.mGranted;
                permissionState2.mFlags = permissionState.mFlags;
                arrayMap.put(str, permissionState2);
            }
        }
    }

    public final Set getGrantedPermissions() {
        if (this.mPermissions == null) {
            return Collections.emptySet();
        }
        ArraySet arraySet = new ArraySet(this.mPermissions.size());
        int size = this.mPermissions.size();
        for (int i = 0; i < size; i++) {
            PermissionState permissionState = (PermissionState) this.mPermissions.valueAt(i);
            if (permissionState.isGranted()) {
                arraySet.add(permissionState.getName());
            }
        }
        return arraySet;
    }

    public final PermissionState getOrCreatePermissionState(Permission permission) {
        if (this.mPermissions == null) {
            this.mPermissions = new ArrayMap();
        }
        String str = permission.mPermissionInfo.name;
        PermissionState permissionState = (PermissionState) this.mPermissions.get(str);
        if (permissionState != null) {
            return permissionState;
        }
        PermissionState permissionState2 = new PermissionState(permission);
        this.mPermissions.put(str, permissionState2);
        return permissionState2;
    }

    public final int getPermissionFlags(String str) {
        PermissionState permissionState = getPermissionState(str);
        if (permissionState == null) {
            return 0;
        }
        return permissionState.getFlags();
    }

    public final PermissionState getPermissionState(String str) {
        ArrayMap arrayMap = this.mPermissions;
        if (arrayMap == null) {
            return null;
        }
        return (PermissionState) arrayMap.get(str);
    }

    public final List getPermissionStates() {
        return this.mPermissions == null
                ? Collections.emptyList()
                : new ArrayList(this.mPermissions.values());
    }

    public final boolean grantPermission(Permission permission) {
        return getOrCreatePermissionState(permission).grant();
    }

    public final boolean hasPermissionState(String str) {
        ArrayMap arrayMap = this.mPermissions;
        return arrayMap != null && arrayMap.containsKey(str);
    }

    public final boolean isPermissionGranted(String str) {
        PermissionState permissionState = getPermissionState(str);
        return permissionState != null && permissionState.isGranted();
    }

    public final boolean removePermissionState(String str) {
        ArrayMap arrayMap = this.mPermissions;
        if (arrayMap == null) {
            return false;
        }
        boolean z = arrayMap.remove(str) != null;
        if (z && this.mPermissions.isEmpty()) {
            this.mPermissions = null;
        }
        return z;
    }

    public final boolean revokePermission(Permission permission) {
        String str = permission.mPermissionInfo.name;
        PermissionState permissionState = getPermissionState(str);
        boolean z = false;
        if (permissionState == null) {
            return false;
        }
        synchronized (permissionState.mLock) {
            try {
                if (permissionState.mGranted) {
                    permissionState.mGranted = false;
                    PackageManager.invalidatePackageInfoCache();
                    z = true;
                }
            } finally {
            }
        }
        if (z && permissionState.isDefault()) {
            removePermissionState(str);
        }
        return z;
    }

    public final boolean updatePermissionFlags(Permission permission, int i, int i2) {
        if (i == 0) {
            return false;
        }
        PermissionState orCreatePermissionState = getOrCreatePermissionState(permission);
        boolean updateFlags = orCreatePermissionState.updateFlags(i, i2);
        if (updateFlags && orCreatePermissionState.isDefault()) {
            removePermissionState(permission.mPermissionInfo.name);
        }
        return updateFlags;
    }
}
