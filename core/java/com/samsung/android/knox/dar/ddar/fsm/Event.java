package com.samsung.android.knox.dar.ddar.fsm;

import com.android.internal.content.NativeLibraryHelper;

public enum Event {
    DDAR_WORKSPACE_CREATED,
    DEVICE_AUTH_SUCCESS,
    DEVICE_LOCKED,
    DATALOCK_TIMEOUT,
    DDAR_WORKSPACE_AUTH_SUCCESS,
    DDAR_WORKSPACE_REMOVED;

    @Override // java.lang.Enum
    public String toString() {
        switch (this) {
            case DDAR_WORKSPACE_CREATED:
                return "DDAR_WORKSPACE_CREATED";
            case DEVICE_AUTH_SUCCESS:
                return "DEVICE_AUTH_SUCCESS";
            case DEVICE_LOCKED:
                return "DEVICE_LOCKED";
            case DATALOCK_TIMEOUT:
                return "DATALOCK_TIMEOUT";
            case DDAR_WORKSPACE_AUTH_SUCCESS:
                return "DDAR_WORKSPACE_AUTH_SUCCESS";
            case DDAR_WORKSPACE_REMOVED:
                return "DDAR_WORKSPACE_REMOVED";
            default:
                return NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
    }
}
