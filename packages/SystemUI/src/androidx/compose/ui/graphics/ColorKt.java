package androidx.compose.ui.graphics;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.colorspace.ColorModel;
import androidx.compose.ui.graphics.colorspace.ColorSpace;
import androidx.compose.ui.graphics.colorspace.ColorSpaces;
import androidx.compose.ui.graphics.colorspace.Oklab;
import androidx.compose.ui.graphics.colorspace.Rgb;
import androidx.compose.ui.graphics.colorspace.Rgb$$ExternalSyntheticLambda0;
import androidx.compose.ui.util.MathHelpersKt;
import kotlin.ULong;

/* loaded from: classes.dex */
public abstract class ColorKt {
    /* JADX WARN: Removed duplicated region for block: B:108:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final long Color(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        int i;
        int i2;
        int i3;
        float minValue;
        float maxValue;
        int i4;
        int i5;
        int i6;
        int i7;
        float minValue2;
        float maxValue2;
        int i8;
        int i9;
        int i10;
        int i11 = 31;
        if (colorSpace.isSrgb()) {
            float f5 = f4 < 0.0f ? 0.0f : f4;
            if (f5 > 1.0f) {
                f5 = 1.0f;
            }
            int i12 = ((int) ((f5 * 255.0f) + 0.5f)) << 24;
            float f6 = f < 0.0f ? 0.0f : f;
            if (f6 > 1.0f) {
                f6 = 1.0f;
            }
            int i13 = i12 | (((int) ((f6 * 255.0f) + 0.5f)) << 16);
            float f7 = f2 < 0.0f ? 0.0f : f2;
            if (f7 > 1.0f) {
                f7 = 1.0f;
            }
            int i14 = i13 | (((int) ((f7 * 255.0f) + 0.5f)) << 8);
            float f8 = f3 >= 0.0f ? f3 : 0.0f;
            float f9 = f8 <= 1.0f ? f8 : 1.0f;
            int i15 = ULong.$r8$clinit;
            long j = (i14 | ((int) ((f9 * 255.0f) + 0.5f))) << 32;
            Color.Companion companion = Color.Companion;
            return j;
        }
        ColorModel.Companion companion2 = ColorModel.Companion;
        if (((int) (colorSpace.model >> 32)) != 3) {
            InlineClassHelperKt.throwIllegalArgumentException("Color only works with ColorSpaces with 3 components");
        }
        int i16 = colorSpace.id;
        if (i16 == -1) {
            InlineClassHelperKt.throwIllegalArgumentException("Unknown color space, please use a color space in ColorSpaces");
        }
        float minValue3 = colorSpace.getMinValue(0);
        float maxValue3 = colorSpace.getMaxValue(0);
        if (f >= minValue3) {
            minValue3 = f;
        }
        if (minValue3 <= maxValue3) {
            maxValue3 = minValue3;
        }
        int iFloatToRawIntBits = Float.floatToRawIntBits(maxValue3);
        int i17 = iFloatToRawIntBits >>> 31;
        int i18 = (iFloatToRawIntBits >>> 23) & 255;
        int i19 = iFloatToRawIntBits & 8388607;
        if (i18 == 255) {
            i2 = i19 != 0 ? 512 : 0;
            i = 31;
        } else {
            i = i18 - 112;
            if (i >= 31) {
                i = 49;
                i2 = 0;
            } else if (i <= 0) {
                if (i >= -10) {
                    int i20 = (i19 | 8388608) >> (1 - i);
                    if ((i20 & 4096) != 0) {
                        i20 += 8192;
                    }
                    i2 = i20 >> 13;
                } else {
                    i2 = 0;
                }
                i = 0;
            } else {
                int i21 = i19 >> 13;
                if ((iFloatToRawIntBits & 4096) != 0) {
                    i3 = (((i << 10) | i21) + 1) | (i17 << 15);
                    short s = (short) i3;
                    minValue = colorSpace.getMinValue(1);
                    maxValue = colorSpace.getMaxValue(1);
                    if (f2 >= minValue) {
                        minValue = f2;
                    }
                    if (minValue <= maxValue) {
                        maxValue = minValue;
                    }
                    int iFloatToRawIntBits2 = Float.floatToRawIntBits(maxValue);
                    int i22 = iFloatToRawIntBits2 >>> 31;
                    i4 = (iFloatToRawIntBits2 >>> 23) & 255;
                    int i23 = iFloatToRawIntBits2 & 8388607;
                    if (i4 != 255) {
                        i6 = i23 != 0 ? 512 : 0;
                        i5 = 31;
                    } else {
                        i5 = i4 - 112;
                        if (i5 >= 31) {
                            i5 = 49;
                            i6 = 0;
                        } else if (i5 <= 0) {
                            if (i5 >= -10) {
                                int i24 = (i23 | 8388608) >> (1 - i5);
                                if ((i24 & 4096) != 0) {
                                    i24 += 8192;
                                }
                                i6 = i24 >> 13;
                            } else {
                                i6 = 0;
                            }
                            i5 = 0;
                        } else {
                            int i25 = i23 >> 13;
                            if ((iFloatToRawIntBits2 & 4096) != 0) {
                                i7 = (((i5 << 10) | i25) + 1) | (i22 << 15);
                                short s2 = (short) i7;
                                minValue2 = colorSpace.getMinValue(2);
                                maxValue2 = colorSpace.getMaxValue(2);
                                if (f3 >= minValue2) {
                                    minValue2 = f3;
                                }
                                if (minValue2 <= maxValue2) {
                                    maxValue2 = minValue2;
                                }
                                int iFloatToRawIntBits3 = Float.floatToRawIntBits(maxValue2);
                                int i26 = iFloatToRawIntBits3 >>> 31;
                                i8 = (iFloatToRawIntBits3 >>> 23) & 255;
                                int i27 = 8388607 & iFloatToRawIntBits3;
                                if (i8 == 255) {
                                    i9 = i27 != 0 ? 512 : 0;
                                    i10 = (i26 << 15) | (i11 << 10) | i9;
                                } else {
                                    int i28 = i8 - 112;
                                    if (i28 >= 31) {
                                        i11 = 49;
                                    } else {
                                        if (i28 > 0) {
                                            i9 = i27 >> 13;
                                            if ((iFloatToRawIntBits3 & 4096) != 0) {
                                                i10 = (((i28 << 10) | i9) + 1) | (i26 << 15);
                                            } else {
                                                i11 = i28;
                                            }
                                        } else if (i28 >= -10) {
                                            int i29 = (i27 | 8388608) >> (1 - i28);
                                            if ((i29 & 4096) != 0) {
                                                i29 += 8192;
                                            }
                                            i9 = i29 >> 13;
                                            i11 = 0;
                                        } else {
                                            i11 = 0;
                                        }
                                        i10 = (i26 << 15) | (i11 << 10) | i9;
                                    }
                                    i10 = (i26 << 15) | (i11 << 10) | i9;
                                }
                                short s3 = (short) i10;
                                long j2 = ((((int) ((((f4 >= 0.0f ? f4 : 0.0f) <= 1.0f ? r8 : 1.0f) * 1023.0f) + 0.5f)) & 1023) << 6) | ((s & 65535) << 48) | ((s2 & 65535) << 32) | ((s3 & 65535) << 16) | (i16 & 63);
                                int i30 = ULong.$r8$clinit;
                                Color.Companion companion3 = Color.Companion;
                                return j2;
                            }
                            i6 = i25;
                        }
                    }
                    i7 = i6 | (i22 << 15) | (i5 << 10);
                    short s22 = (short) i7;
                    minValue2 = colorSpace.getMinValue(2);
                    maxValue2 = colorSpace.getMaxValue(2);
                    if (f3 >= minValue2) {
                    }
                    if (minValue2 <= maxValue2) {
                    }
                    int iFloatToRawIntBits32 = Float.floatToRawIntBits(maxValue2);
                    int i262 = iFloatToRawIntBits32 >>> 31;
                    i8 = (iFloatToRawIntBits32 >>> 23) & 255;
                    int i272 = 8388607 & iFloatToRawIntBits32;
                    if (i8 == 255) {
                    }
                    short s32 = (short) i10;
                    if (f4 >= 0.0f) {
                    }
                    long j22 = ((((int) ((((f4 >= 0.0f ? f4 : 0.0f) <= 1.0f ? r8 : 1.0f) * 1023.0f) + 0.5f)) & 1023) << 6) | ((s & 65535) << 48) | ((s22 & 65535) << 32) | ((s32 & 65535) << 16) | (i16 & 63);
                    int i302 = ULong.$r8$clinit;
                    Color.Companion companion32 = Color.Companion;
                    return j22;
                }
                i2 = i21;
            }
        }
        i3 = i2 | (i17 << 15) | (i << 10);
        short s4 = (short) i3;
        minValue = colorSpace.getMinValue(1);
        maxValue = colorSpace.getMaxValue(1);
        if (f2 >= minValue) {
        }
        if (minValue <= maxValue) {
        }
        int iFloatToRawIntBits22 = Float.floatToRawIntBits(maxValue);
        int i222 = iFloatToRawIntBits22 >>> 31;
        i4 = (iFloatToRawIntBits22 >>> 23) & 255;
        int i232 = iFloatToRawIntBits22 & 8388607;
        if (i4 != 255) {
        }
        i7 = i6 | (i222 << 15) | (i5 << 10);
        short s222 = (short) i7;
        minValue2 = colorSpace.getMinValue(2);
        maxValue2 = colorSpace.getMaxValue(2);
        if (f3 >= minValue2) {
        }
        if (minValue2 <= maxValue2) {
        }
        int iFloatToRawIntBits322 = Float.floatToRawIntBits(maxValue2);
        int i2622 = iFloatToRawIntBits322 >>> 31;
        i8 = (iFloatToRawIntBits322 >>> 23) & 255;
        int i2722 = 8388607 & iFloatToRawIntBits322;
        if (i8 == 255) {
        }
        short s322 = (short) i10;
        if (f4 >= 0.0f) {
        }
        long j222 = ((((int) ((((f4 >= 0.0f ? f4 : 0.0f) <= 1.0f ? r8 : 1.0f) * 1023.0f) + 0.5f)) & 1023) << 6) | ((s4 & 65535) << 48) | ((s222 & 65535) << 32) | ((s322 & 65535) << 16) | (i16 & 63);
        int i3022 = ULong.$r8$clinit;
        Color.Companion companion322 = Color.Companion;
        return j222;
    }

    public static long Color$default(float f, float f2, float f3, int i) {
        float f4 = (i & 8) != 0 ? 1.0f : 0.2f;
        ColorSpaces.INSTANCE.getClass();
        return Color(f, f2, f3, f4, ColorSpaces.Srgb);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final long UncheckedColor(float f, float f2, float f3, float f4, ColorSpace colorSpace) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10 = 31;
        if (colorSpace.isSrgb()) {
            int i11 = ULong.$r8$clinit;
            long j = ((((((int) ((f4 * 255.0f) + 0.5f)) << 24) | (((int) ((f * 255.0f) + 0.5f)) << 16)) | (((int) ((f2 * 255.0f) + 0.5f)) << 8)) | ((int) ((255.0f * f3) + 0.5f))) << 32;
            Color.Companion companion = Color.Companion;
            return j;
        }
        int iFloatToRawIntBits = Float.floatToRawIntBits(f);
        int i12 = iFloatToRawIntBits >>> 31;
        int i13 = (iFloatToRawIntBits >>> 23) & 255;
        int i14 = iFloatToRawIntBits & 8388607;
        int i15 = 0;
        if (i13 == 255) {
            i2 = i14 != 0 ? 512 : 0;
            i = 31;
        } else {
            i = i13 - 112;
            if (i >= 31) {
                i = 49;
                i2 = 0;
            } else if (i > 0) {
                int i16 = i14 >> 13;
                if ((iFloatToRawIntBits & 4096) != 0) {
                    i3 = (((i << 10) | i16) + 1) | (i12 << 15);
                    short s = (short) i3;
                    int iFloatToRawIntBits2 = Float.floatToRawIntBits(f2);
                    int i17 = iFloatToRawIntBits2 >>> 31;
                    i4 = (iFloatToRawIntBits2 >>> 23) & 255;
                    int i18 = iFloatToRawIntBits2 & 8388607;
                    if (i4 != 255) {
                        i6 = i18 != 0 ? 512 : 0;
                        i5 = 31;
                    } else {
                        i5 = i4 - 112;
                        if (i5 >= 31) {
                            i5 = 49;
                            i6 = 0;
                        } else if (i5 > 0) {
                            int i19 = i18 >> 13;
                            if ((iFloatToRawIntBits2 & 4096) != 0) {
                                i7 = (((i5 << 10) | i19) + 1) | (i17 << 15);
                                short s2 = (short) i7;
                                int iFloatToRawIntBits3 = Float.floatToRawIntBits(f3);
                                int i20 = iFloatToRawIntBits3 >>> 31;
                                i8 = (iFloatToRawIntBits3 >>> 23) & 255;
                                int i21 = 8388607 & iFloatToRawIntBits3;
                                if (i8 == 255) {
                                    i15 = i21 == 0 ? 0 : 512;
                                } else {
                                    int i22 = i8 - 112;
                                    if (i22 >= 31) {
                                        i10 = 49;
                                    } else if (i22 > 0) {
                                        i15 = i21 >> 13;
                                        if ((iFloatToRawIntBits3 & 4096) != 0) {
                                            i9 = (((i22 << 10) | i15) + 1) | (i20 << 15);
                                            long jMax = ((s & 65535) << 48) | ((s2 & 65535) << 32) | ((((short) i9) & 65535) << 16) | ((((int) ((Math.max(0.0f, Math.min(f4, 1.0f)) * 1023.0f) + 0.5f)) & 1023) << 6) | (colorSpace.id & 63);
                                            int i23 = ULong.$r8$clinit;
                                            Color.Companion companion2 = Color.Companion;
                                            return jMax;
                                        }
                                        i10 = i22;
                                    } else if (i22 >= -10) {
                                        int i24 = (i21 | 8388608) >> (1 - i22);
                                        if ((i24 & 4096) != 0) {
                                            i24 += 8192;
                                        }
                                        i15 = i24 >> 13;
                                        i10 = 0;
                                    } else {
                                        i10 = 0;
                                    }
                                }
                                i9 = (i10 << 10) | (i20 << 15) | i15;
                                long jMax2 = ((s & 65535) << 48) | ((s2 & 65535) << 32) | ((((short) i9) & 65535) << 16) | ((((int) ((Math.max(0.0f, Math.min(f4, 1.0f)) * 1023.0f) + 0.5f)) & 1023) << 6) | (colorSpace.id & 63);
                                int i232 = ULong.$r8$clinit;
                                Color.Companion companion22 = Color.Companion;
                                return jMax2;
                            }
                            i6 = i19;
                        } else if (i5 >= -10) {
                            int i25 = (i18 | 8388608) >> (1 - i5);
                            if ((i25 & 4096) != 0) {
                                i25 += 8192;
                            }
                            i6 = i25 >> 13;
                            i5 = 0;
                        } else {
                            i6 = 0;
                            i5 = 0;
                        }
                    }
                    i7 = i6 | (i17 << 15) | (i5 << 10);
                    short s22 = (short) i7;
                    int iFloatToRawIntBits32 = Float.floatToRawIntBits(f3);
                    int i202 = iFloatToRawIntBits32 >>> 31;
                    i8 = (iFloatToRawIntBits32 >>> 23) & 255;
                    int i212 = 8388607 & iFloatToRawIntBits32;
                    if (i8 == 255) {
                    }
                    i9 = (i10 << 10) | (i202 << 15) | i15;
                    long jMax22 = ((s & 65535) << 48) | ((s22 & 65535) << 32) | ((((short) i9) & 65535) << 16) | ((((int) ((Math.max(0.0f, Math.min(f4, 1.0f)) * 1023.0f) + 0.5f)) & 1023) << 6) | (colorSpace.id & 63);
                    int i2322 = ULong.$r8$clinit;
                    Color.Companion companion222 = Color.Companion;
                    return jMax22;
                }
                i2 = i16;
            } else if (i >= -10) {
                int i26 = (i14 | 8388608) >> (1 - i);
                if ((i26 & 4096) != 0) {
                    i26 += 8192;
                }
                i2 = i26 >> 13;
                i = 0;
            } else {
                i2 = 0;
                i = 0;
            }
        }
        i3 = i2 | (i12 << 15) | (i << 10);
        short s3 = (short) i3;
        int iFloatToRawIntBits22 = Float.floatToRawIntBits(f2);
        int i172 = iFloatToRawIntBits22 >>> 31;
        i4 = (iFloatToRawIntBits22 >>> 23) & 255;
        int i182 = iFloatToRawIntBits22 & 8388607;
        if (i4 != 255) {
        }
        i7 = i6 | (i172 << 15) | (i5 << 10);
        short s222 = (short) i7;
        int iFloatToRawIntBits322 = Float.floatToRawIntBits(f3);
        int i2022 = iFloatToRawIntBits322 >>> 31;
        i8 = (iFloatToRawIntBits322 >>> 23) & 255;
        int i2122 = 8388607 & iFloatToRawIntBits322;
        if (i8 == 255) {
        }
        i9 = (i10 << 10) | (i2022 << 15) | i15;
        long jMax222 = ((s3 & 65535) << 48) | ((s222 & 65535) << 32) | ((((short) i9) & 65535) << 16) | ((((int) ((Math.max(0.0f, Math.min(f4, 1.0f)) * 1023.0f) + 0.5f)) & 1023) << 6) | (colorSpace.id & 63);
        int i23222 = ULong.$r8$clinit;
        Color.Companion companion2222 = Color.Companion;
        return jMax222;
    }

    /* renamed from: compositeOver--OWjLjI, reason: not valid java name */
    public static final long m466compositeOverOWjLjI(long j, long j2) {
        float f;
        float f2;
        long jM457convertvNxB06k = Color.m457convertvNxB06k(j, Color.m461getColorSpaceimpl(j2));
        float fM459getAlphaimpl = Color.m459getAlphaimpl(j2);
        float fM459getAlphaimpl2 = Color.m459getAlphaimpl(jM457convertvNxB06k);
        float f3 = 1.0f - fM459getAlphaimpl2;
        float f4 = (fM459getAlphaimpl * f3) + fM459getAlphaimpl2;
        float fM463getRedimpl = Color.m463getRedimpl(jM457convertvNxB06k);
        float fM463getRedimpl2 = Color.m463getRedimpl(j2);
        float f5 = 0.0f;
        if (f4 == 0.0f) {
            f = 0.0f;
        } else {
            f = (((fM463getRedimpl2 * fM459getAlphaimpl) * f3) + (fM463getRedimpl * fM459getAlphaimpl2)) / f4;
        }
        float fM462getGreenimpl = Color.m462getGreenimpl(jM457convertvNxB06k);
        float fM462getGreenimpl2 = Color.m462getGreenimpl(j2);
        if (f4 == 0.0f) {
            f2 = 0.0f;
        } else {
            f2 = (((fM462getGreenimpl2 * fM459getAlphaimpl) * f3) + (fM462getGreenimpl * fM459getAlphaimpl2)) / f4;
        }
        float fM460getBlueimpl = Color.m460getBlueimpl(jM457convertvNxB06k);
        float fM460getBlueimpl2 = Color.m460getBlueimpl(j2);
        if (f4 != 0.0f) {
            f5 = (((fM460getBlueimpl2 * fM459getAlphaimpl) * f3) + (fM460getBlueimpl * fM459getAlphaimpl2)) / f4;
        }
        return UncheckedColor(f, f2, f5, f4, Color.m461getColorSpaceimpl(j2));
    }

    /* renamed from: lerp-jxsXWHM, reason: not valid java name */
    public static final long m467lerpjxsXWHM(long j, long j2, float f) {
        ColorSpaces.INSTANCE.getClass();
        Oklab oklab = ColorSpaces.Oklab;
        long jM457convertvNxB06k = Color.m457convertvNxB06k(j, oklab);
        long jM457convertvNxB06k2 = Color.m457convertvNxB06k(j2, oklab);
        float fM459getAlphaimpl = Color.m459getAlphaimpl(jM457convertvNxB06k);
        float fM463getRedimpl = Color.m463getRedimpl(jM457convertvNxB06k);
        float fM462getGreenimpl = Color.m462getGreenimpl(jM457convertvNxB06k);
        float fM460getBlueimpl = Color.m460getBlueimpl(jM457convertvNxB06k);
        float fM459getAlphaimpl2 = Color.m459getAlphaimpl(jM457convertvNxB06k2);
        float fM463getRedimpl2 = Color.m463getRedimpl(jM457convertvNxB06k2);
        float fM462getGreenimpl2 = Color.m462getGreenimpl(jM457convertvNxB06k2);
        float fM460getBlueimpl2 = Color.m460getBlueimpl(jM457convertvNxB06k2);
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        return Color.m457convertvNxB06k(UncheckedColor(MathHelpersKt.lerp(fM463getRedimpl, fM463getRedimpl2, f), MathHelpersKt.lerp(fM462getGreenimpl, fM462getGreenimpl2, f), MathHelpersKt.lerp(fM460getBlueimpl, fM460getBlueimpl2, f), MathHelpersKt.lerp(fM459getAlphaimpl, fM459getAlphaimpl2, f), oklab), Color.m461getColorSpaceimpl(j2));
    }

    /* renamed from: luminance-8_81llA, reason: not valid java name */
    public static final float m468luminance8_81llA(long j) {
        ColorSpace colorSpaceM461getColorSpaceimpl = Color.m461getColorSpaceimpl(j);
        long j2 = colorSpaceM461getColorSpaceimpl.model;
        ColorModel.Companion.getClass();
        if (!ColorModel.m510equalsimpl0(j2, ColorModel.Rgb)) {
            InlineClassHelperKt.throwIllegalArgumentException("The specified color must be encoded in an RGB color space. The supplied color space is " + ((Object) ColorModel.m511toStringimpl(colorSpaceM461getColorSpaceimpl.model)));
        }
        double dM463getRedimpl = Color.m463getRedimpl(j);
        Rgb$$ExternalSyntheticLambda0 rgb$$ExternalSyntheticLambda0 = ((Rgb) colorSpaceM461getColorSpaceimpl).eotfFunc;
        double dInvoke = rgb$$ExternalSyntheticLambda0.invoke(dM463getRedimpl);
        float fInvoke = (float) ((rgb$$ExternalSyntheticLambda0.invoke(Color.m460getBlueimpl(j)) * 0.0722d) + (rgb$$ExternalSyntheticLambda0.invoke(Color.m462getGreenimpl(j)) * 0.7152d) + (dInvoke * 0.2126d));
        if (fInvoke < 0.0f) {
            fInvoke = 0.0f;
        }
        if (fInvoke > 1.0f) {
            return 1.0f;
        }
        return fInvoke;
    }

    /* renamed from: toArgb-8_81llA, reason: not valid java name */
    public static final int m469toArgb8_81llA(long j) {
        ColorSpaces.INSTANCE.getClass();
        long jM457convertvNxB06k = Color.m457convertvNxB06k(j, ColorSpaces.Srgb) >>> 32;
        int i = ULong.$r8$clinit;
        return (int) jM457convertvNxB06k;
    }

    public static long Color$default(int i, int i2, int i3) {
        return Color(((i & 255) << 16) | (-16777216) | ((i2 & 255) << 8) | (i3 & 255));
    }

    public static final long Color(int i) {
        long j = i;
        int i2 = ULong.$r8$clinit;
        long j2 = j << 32;
        Color.Companion companion = Color.Companion;
        return j2;
    }

    public static final long Color(long j) {
        long j2 = j << 32;
        int i = ULong.$r8$clinit;
        Color.Companion companion = Color.Companion;
        return j2;
    }
}
