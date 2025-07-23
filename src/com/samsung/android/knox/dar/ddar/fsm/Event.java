package com.samsung.android.knox.dar.ddar.fsm;

import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes6.dex */
public enum Event {
    DDAR_WORKSPACE_CREATED,
    DEVICE_AUTH_SUCCESS,
    DEVICE_LOCKED,
    DATALOCK_TIMEOUT,
    DDAR_WORKSPACE_AUTH_SUCCESS,
    DDAR_WORKSPACE_REMOVED;

    @Override // java.lang.Enum
    public String toString() {
        int ordinal = ordinal();
        if (ordinal == 0) {
            return "DDAR_WORKSPACE_CREATED";
        }
        if (ordinal == 1) {
            return "DEVICE_AUTH_SUCCESS";
        }
        if (ordinal == 2) {
            return "DEVICE_LOCKED";
        }
        if (ordinal == 3) {
            return "DATALOCK_TIMEOUT";
        }
        if (ordinal == 4) {
            return "DDAR_WORKSPACE_AUTH_SUCCESS";
        }
        if (ordinal == 5) {
            return "DDAR_WORKSPACE_REMOVED";
        }
        return NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
    }
}
