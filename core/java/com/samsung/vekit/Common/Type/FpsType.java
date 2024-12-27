package com.samsung.vekit.Common.Type;

public enum FpsType {
    FPS_30(0),
    FPS_60(1),
    FPS_120(2),
    FPS_240(3),
    FPS_480(4),
    FPS_960(5);

    private final int value;

    FpsType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
