package com.android.server.pm.permission;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.SparseArray;

public final class UserPermissionState {
    public final ArraySet mInstallPermissionsFixed = new ArraySet();
    public final SparseArray mUidStates = new SparseArray();

    public static void checkAppId(int i) {
        if (UserHandle.getUserId(i) != 0) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid app ID "));
        }
    }

    public final UidPermissionState getOrCreateUidState(int i) {
        checkAppId(i);
        UidPermissionState uidPermissionState = (UidPermissionState) this.mUidStates.get(i);
        if (uidPermissionState != null) {
            return uidPermissionState;
        }
        UidPermissionState uidPermissionState2 = new UidPermissionState();
        this.mUidStates.put(i, uidPermissionState2);
        return uidPermissionState2;
    }

    public final UidPermissionState getUidState(int i) {
        checkAppId(i);
        return (UidPermissionState) this.mUidStates.get(i);
    }

    public final void setInstallPermissionsFixed(String str, boolean z) {
        if (z) {
            this.mInstallPermissionsFixed.add(str);
        } else {
            this.mInstallPermissionsFixed.remove(str);
        }
    }
}
