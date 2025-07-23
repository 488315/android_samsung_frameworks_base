package com.samsung.android.knox.dar.ddar.fsm;

import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes6.dex */
public enum State {
    IDLE,
    DEVICE_UNLOCK_DATA_UNLOCK,
    DEVICE_LOCK_DATA_UNLOCK,
    DEVICE_LOCK_DATA_LOCK,
    DEVICE_UNLOCK_DATA_LOCK;

    @Override // java.lang.Enum
    public String toString() {
        int ordinal = ordinal();
        if (ordinal == 0) {
            return "IDLE";
        }
        if (ordinal == 1) {
            return "DEVICE_UNLOCK_DATA_UNLOCK";
        }
        if (ordinal == 2) {
            return "DEVICE_LOCK_DATA_UNLOCK";
        }
        if (ordinal == 3) {
            return "DEVICE_LOCK_DATA_LOCK";
        }
        if (ordinal == 4) {
            return "DEVICE_UNLOCK_DATA_LOCK";
        }
        return NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
    }
}
