package com.samsung.android.sume.core.types;

public enum FlipType implements NumericEnum {
    NONE(0),
    HORIZONTAL(1),
    VERTICAL(2),
    ALL(3);

    private final int value;

    FlipType(int value) {
        this.value = value;
    }

    @Override // com.samsung.android.sume.core.types.NumericEnum
    public int getValue() {
        return this.value;
    }

    @Override // com.samsung.android.sume.core.types.NumericEnum
    public String stringfy() {
        return name() + ":" + this.value;
    }
}
