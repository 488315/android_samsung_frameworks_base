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
        int iOrdinal = ordinal();
        if (iOrdinal == 0) {
            return "DDAR_WORKSPACE_CREATED";
        }
        if (iOrdinal == 1) {
            return "DEVICE_AUTH_SUCCESS";
        }
        if (iOrdinal == 2) {
            return "DEVICE_LOCKED";
        }
        if (iOrdinal == 3) {
            return "DATALOCK_TIMEOUT";
        }
        if (iOrdinal == 4) {
            return "DDAR_WORKSPACE_AUTH_SUCCESS";
        }
        if (iOrdinal == 5) {
            return "DDAR_WORKSPACE_REMOVED";
        }
        return NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
    }
}
