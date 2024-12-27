package com.samsung.android.sume.core.types;

public enum HwUnit implements NumericEnum {
    NONE(-1),
    CPU(0),
    GPU(1),
    NPU(2),
    DSP(3),
    IP(4);

    private final int value;

    HwUnit(int value) {
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

    public static HwUnit from(int value) {
        return (HwUnit) NumericEnum.fromValue(HwUnit.class, value);
    }
}
