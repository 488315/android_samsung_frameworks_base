package com.samsung.android.sume.core.types;

public enum CodecType implements NumericEnum {
    NONE(0),
    HEIF(1),
    JPEG_SQ(2),
    JPEG_QURAM(3);

    private boolean useInternalThumbnail = false;
    private final int value;

    CodecType(int value) {
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

    public boolean isUseInternalThumbnail() {
        return this.useInternalThumbnail;
    }

    public boolean setUseInternalThumbnail(boolean useInternalThumbnail) {
        this.useInternalThumbnail = useInternalThumbnail;
        return this.useInternalThumbnail;
    }
}
