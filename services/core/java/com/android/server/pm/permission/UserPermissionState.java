package com.android.server.pm.permission;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UserPermissionState {
    public final ArraySet mInstallPermissionsFixed = new ArraySet();
    public final SparseArray mUidStates = new SparseArray();

    public static void checkAppId(int i) {
        if (UserHandle.getUserId(i) != 0) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid app ID "));
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
