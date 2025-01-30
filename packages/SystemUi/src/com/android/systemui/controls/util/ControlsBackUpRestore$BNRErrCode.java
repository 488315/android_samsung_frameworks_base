package com.android.systemui.controls.util;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public enum ControlsBackUpRestore$BNRErrCode {
    SUCCESS(0),
    /* JADX INFO: Fake field, exist only in values array */
    UNKNOWN_ERROR(1),
    /* JADX INFO: Fake field, exist only in values array */
    STORAGE_FULL(2),
    INVALID_DATA(3),
    /* JADX INFO: Fake field, exist only in values array */
    PERMISSION_FAIL(4),
    /* JADX INFO: Fake field, exist only in values array */
    LOCKED(5),
    /* JADX INFO: Fake field, exist only in values array */
    PARTIAL_SUCCESS(6);

    private final int value;

    ControlsBackUpRestore$BNRErrCode(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
