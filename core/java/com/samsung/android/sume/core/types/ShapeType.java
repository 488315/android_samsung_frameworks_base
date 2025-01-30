package com.samsung.android.sume.core.types;

/* loaded from: classes4.dex */
public enum ShapeType implements NumericEnum {
    NONE(0),
    NWHC(1),
    NHWC(2);

    private final int value;

    ShapeType(int value) {
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
