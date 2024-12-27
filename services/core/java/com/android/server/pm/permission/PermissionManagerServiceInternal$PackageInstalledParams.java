package com.android.server.pm.permission;

import android.util.ArrayMap;

import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
