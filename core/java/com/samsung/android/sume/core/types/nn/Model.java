package com.samsung.android.sume.core.types.nn;

import com.samsung.android.sume.core.types.NumericEnum;

/* loaded from: classes6.dex */
public enum Model implements NumericEnum {
    NONE(0),
    IMAGE_UPSCALER_X4(1),
    MIRACLE_ESTIMATOR(2),
    MIRACLE_FILTER(3),
    OLD_PHOTO_ESTIMATOR(4),
    OLD_PHOTO_ENHANCER(5),
    OLD_PHOTO_FACE_ENHANCER(6),
    VIDEO_UPSCALER_X4(7);

    private final int value;

    Model(int value) {
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

    public static Model from(int value) {
        return (Model) NumericEnum.fromValue(Model.class, value);
    }
}
