package com.android.server.pm.permission;

import android.util.ArrayMap;

import java.util.Collections;
import java.util.List;

public final class PermissionManagerServiceInternal$PackageInstalledParams {
    public static final PermissionManagerServiceInternal$PackageInstalledParams DEFAULT =
            new PermissionManagerServiceInternal$PackageInstalledParams(
                    new ArrayMap(), Collections.emptyList(), 3);
    public final List mAllowlistedRestrictedPermissions;
    public final int mAutoRevokePermissionsMode;
    public final ArrayMap mPermissionStates;

    public PermissionManagerServiceInternal$PackageInstalledParams(
            ArrayMap arrayMap, List list, int i) {
        this.mPermissionStates = arrayMap;
        this.mAllowlistedRestrictedPermissions = list;
        this.mAutoRevokePermissionsMode = i;
    }
}
