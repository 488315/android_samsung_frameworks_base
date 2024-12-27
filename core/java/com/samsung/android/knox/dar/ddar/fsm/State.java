package com.samsung.android.knox.dar.ddar.fsm;

import com.android.internal.content.NativeLibraryHelper;

public enum State {
    IDLE,
    DEVICE_UNLOCK_DATA_UNLOCK,
    DEVICE_LOCK_DATA_UNLOCK,
    DEVICE_LOCK_DATA_LOCK,
    DEVICE_UNLOCK_DATA_LOCK;

    @Override // java.lang.Enum
    public String toString() {
        switch (this) {
            case IDLE:
                return "IDLE";
            case DEVICE_UNLOCK_DATA_UNLOCK:
                return "DEVICE_UNLOCK_DATA_UNLOCK";
            case DEVICE_LOCK_DATA_UNLOCK:
                return "DEVICE_LOCK_DATA_UNLOCK";
            case DEVICE_LOCK_DATA_LOCK:
                return "DEVICE_LOCK_DATA_LOCK";
            case DEVICE_UNLOCK_DATA_LOCK:
                return "DEVICE_UNLOCK_DATA_LOCK";
            default:
                return NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
        }
    }
}
