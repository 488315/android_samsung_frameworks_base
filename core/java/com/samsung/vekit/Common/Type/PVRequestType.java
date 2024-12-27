package com.samsung.vekit.Common.Type;

/* loaded from: classes6.dex */
public enum PVRequestType {
    SET_KEY_FRAMES(6),
    SET_DELETE_KEY_FRAME(7);

    private final int value;

    PVRequestType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
