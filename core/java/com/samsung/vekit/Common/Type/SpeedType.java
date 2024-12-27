package com.samsung.vekit.Common.Type;

/* loaded from: classes6.dex */
public enum SpeedType {
    SPEED_1(0),
    SPEED_1_2(1),
    SPEED_1_4(2),
    SPEED_1_8(3),
    SPEED_1_16(4),
    SPEED_1_32(5);

    private final int value;

    SpeedType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
