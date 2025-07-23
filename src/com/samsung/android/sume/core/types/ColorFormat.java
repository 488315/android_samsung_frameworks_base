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

    ColorFormat(int i) {
        this.value = i;
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
        switch (AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$types$ColorFormat[ordinal()]) {
            case 1:
            case 2:
                return 1.0f;
            case 3:
            case 4:
            case 5:
                return 1.5f;
            case 6:
            case 7:
                return 3.0f;
            case 8:
            case 9:
            case 10:
                return 4.0f;
            default:
                throw new UnsupportedOperationException("not support");
        }
    }

    public boolean isPlanar() {
        return isYuv();
    }

    public boolean isYuv() {
        return this == NV12 || this == NV21 || this == YUV420 || this == P010 || this == P010_ZIPPED;
    }

    public static float bytePerPixel(ColorFormat colorFormat) {
        return colorFormat.bytePerPixel();
    }

    public static ColorFormat from(int i) {
        return (ColorFormat) NumericEnum.fromValue(ColorFormat.class, i);
    }

    public int numberOfPlanes() {
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$types$ColorFormat[ordinal()];
        if (i == 3 || i == 4) {
            return 2;
        }
        return (i == 5 || i == 11 || i == 12) ? 3 : 1;
    }

    public int numberOfChromaChannels() {
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$types$ColorFormat[ordinal()];
        return (i == 3 || i == 4) ? 2 : 1;
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
        return (this == BGRA || this == ABGR) ? BGR : this;
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
