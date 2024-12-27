package com.android.server.pm.permission;

import android.util.SparseArray;

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
