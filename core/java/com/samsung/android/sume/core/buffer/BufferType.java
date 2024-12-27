package com.samsung.android.sume.core.buffer;

import com.samsung.android.sume.core.types.NumericEnum;

/* loaded from: classes6.dex */
public enum BufferType implements NumericEnum {
    NONE(0),
    HEAP(1),
    SHARED(2),
    PROPRIETARY(3),
    LIST(4);

    private final int value;

    BufferType(int value) {
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

    public static BufferType from(int value) {
        return (BufferType) NumericEnum.fromValue(BufferType.class, value);
    }
}
