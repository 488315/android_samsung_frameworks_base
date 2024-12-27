package com.android.server.pm.permission;

import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DevicePermissionState {
    public final SparseArray mUserStates = new SparseArray();

    public final UserPermissionState getOrCreateUserState(int i) {
        UserPermissionState userPermissionState = (UserPermissionState) this.mUserStates.get(i);
        if (userPermissionState != null) {
            return userPermissionState;
        }
        UserPermissionState userPermissionState2 = new UserPermissionState();
        this.mUserStates.put(i, userPermissionState2);
        return userPermissionState2;
    }
}
