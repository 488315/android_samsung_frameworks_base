package com.samsung.android.sume.core.types;

/* loaded from: classes6.dex */
public enum ColorFormat implements NumericEnum {
    NONE(0),
    OPAQUE(1),
    GRAY(2),
    NV12(3),
    NV21(4),
    YUV420(5),
    P010(6),
    P010_ZIPPED(7),
    RGB(8),
    RGBA(9),
    ARGB(10),
    BGR(11),
    BGRA(12),
    ABGR(13);

    private final int value;

    ColorFormat(int value) {
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

    public float bytePerPixel() {
        switch (this) {
            case NONE:
            case GRAY:
                return 1.0f;
            case NV12:
            case NV21:
            case YUV420:
                return 1.5f;
            case RGB:
            case BGR:
                return 3.0f;
            case RGBA:
            case ARGB:
            case BGRA:
                return 4.0f;
            default:
                throw new UnsupportedOperationException("not support");
        }
    }

    public boolean isPlanar() {
        return isYuv();
    }

    public boolean isYuv() {
        return this == NV12
                || this == NV21
                || this == YUV420
                || this == P010
                || this == P010_ZIPPED;
    }

    public static float bytePerPixel(ColorFormat format) {
        return format.bytePerPixel();
    }

    public static ColorFormat from(int value) {
        return (ColorFormat) NumericEnum.fromValue(ColorFormat.class, value);
    }

    public int numberOfPlanes() {
        switch (this) {
            case NV12:
            case NV21:
                return 2;
            case YUV420:
            case P010:
            case P010_ZIPPED:
                return 3;
            default:
                return 1;
        }
    }

    public int numberOfChromaChannels() {
        switch (this) {
            case NV12:
            case NV21:
                return 2;
            default:
                return 1;
        }
    }

    public boolean hasAlpha() {
        return hasFrontAlpha() || this == RGBA || this == BGRA;
    }

    public boolean hasFrontAlpha() {
        return this == ARGB || this == ABGR;
    }

    public ColorFormat getOpaque() {
        if (this == RGBA || this == ARGB) {
            return RGB;
        }
        if (this == BGRA || this == ABGR) {
            return BGR;
        }
        return this;
    }

    public int getChannels() {
        switch (this) {
            case GRAY:
            case NV12:
            case NV21:
            case YUV420:
                return 1;
            case RGB:
            case BGR:
            case P010:
            case P010_ZIPPED:
                return 3;
            case RGBA:
            case ARGB:
            case BGRA:
            case ABGR:
                return 4;
            default:
                return 0;
        }
    }
}
