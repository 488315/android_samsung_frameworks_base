package com.samsung.android.sume.core.types;

import android.graphics.Bitmap;

import com.samsung.android.sume.core.Def;

public enum ColorSpace implements NumericEnum {
    NONE(0),
    BT601(1),
    BT709(2),
    BT2020(3),
    SRGB(4),
    DISPLAY_P3(5),
    BT601_LR(makeColorSpace(BT601, ColorSpaceRange.LIMITED)),
    BT601_FR(makeColorSpace(BT601, ColorSpaceRange.FULL)),
    BT709_LR(makeColorSpace(BT709, ColorSpaceRange.LIMITED)),
    BT709_FR(makeColorSpace(BT709, ColorSpaceRange.FULL)),
    BT2020_LR(makeColorSpace(BT2020, ColorSpaceRange.LIMITED)),
    BT2020_FR(makeColorSpace(BT2020, ColorSpaceRange.FULL));

    static final int CS_RANGE_SHIFT = 4;
    static final int CS_SET_MASK = 15;
    private static final int CS_SET_MAX = 16;
    private final int value;

    ColorSpace(int value) {
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

    private static int makeColorSpace(ColorSpace colorSpace, ColorSpaceRange range) {
        Def.require(
                (colorSpace == NONE || range == ColorSpaceRange.NONE) ? false : true,
                "color-space is none",
                new Object[0]);
        return (colorSpace.getValue() & 15) + (range.getValue() << 4);
    }

    public boolean isLimitedRange() {
        return (this.value >> 4) == ColorSpaceRange.LIMITED.value;
    }

    public boolean isFullRange() {
        return (this.value >> 4) == ColorSpaceRange.FULL.value;
    }

    public enum ColorSpaceRange implements NumericEnum {
        NONE(0),
        LIMITED(1),
        FULL(2);

        private final int value;

        ColorSpaceRange(int value) {
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

    public static ColorSpace of(Bitmap bitmap) {
        android.graphics.ColorSpace colorSpace = bitmap.getColorSpace();
        if (colorSpace == null) {
            return NONE;
        }
        switch (colorSpace.getId()) {
            case 0:
                break;
            case 4:
                break;
            case 5:
                break;
            case 7:
                break;
        }
        return NONE;
    }
}
