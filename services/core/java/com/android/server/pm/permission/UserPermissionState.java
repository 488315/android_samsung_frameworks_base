package com.android.server.pm.permission;

import android.os.UserHandle;
import android.util.ArraySet;
import android.util.SparseArray;

/* loaded from: classes3.dex */
public final class UserPermissionState {
    public final ArraySet mInstallPermissionsFixed = new ArraySet();
    public final SparseArray mUidStates = new SparseArray();

    public boolean areInstallPermissionsFixed(String str) {
        return this.mInstallPermissionsFixed.contains(str);
    }

    public void setInstallPermissionsFixed(String str, boolean z) {
        if (z) {
            this.mInstallPermissionsFixed.add(str);
        } else {
            this.mInstallPermissionsFixed.remove(str);
        }
    }

    public UidPermissionState getUidState(int i) {
        checkAppId(i);
        return (UidPermissionState) this.mUidStates.get(i);
    }

    public UidPermissionState getOrCreateUidState(int i) {
        checkAppId(i);
        UidPermissionState uidPermissionState = (UidPermissionState) this.mUidStates.get(i);
        if (uidPermissionState != null) {
            return uidPermissionState;
        }
        UidPermissionState uidPermissionState2 = new UidPermissionState();
        this.mUidStates.put(i, uidPermissionState2);
        return uidPermissionState2;
    }

    public UidPermissionState createUidStateWithExisting(int i, UidPermissionState uidPermissionState) {
        checkAppId(i);
        UidPermissionState uidPermissionState2 = new UidPermissionState(uidPermissionState);
        this.mUidStates.put(i, uidPermissionState2);
        return uidPermissionState2;
    }

    public void removeUidState(int i) {
        checkAppId(i);
        this.mUidStates.delete(i);
    }

    public final void checkAppId(int i) {
        if (UserHandle.getUserId(i) == 0) {
            return;
        }
        throw new IllegalArgumentException("Invalid app ID " + i);
    }
}
