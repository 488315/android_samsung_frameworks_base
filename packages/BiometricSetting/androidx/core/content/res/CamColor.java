package androidx.core.content.res;

import android.graphics.Color;
import androidx.core.graphics.ColorUtils;

/* loaded from: classes.dex */
public final class CamColor {
    private final float mAstar;
    private final float mBstar;
    private final float mChroma;
    private final float mHue;

    /* renamed from: mJ */
    private final float f13mJ;
    private final float mJstar;

    CamColor(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mHue = f;
        this.mChroma = f2;
        this.f13mJ = f3;
        this.mJstar = f4;
        this.mAstar = f5;
        this.mBstar = f6;
    }

    static CamColor fromColor(int i) {
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        float linearized = CamUtils.linearized(Color.red(i));
        float linearized2 = CamUtils.linearized(Color.green(i));
        float linearized3 = CamUtils.linearized(Color.blue(i));
        float[][] fArr = CamUtils.SRGB_TO_XYZ;
        float[] fArr2 = fArr[0];
        float f = (fArr2[2] * linearized3) + (fArr2[1] * linearized2) + (fArr2[0] * linearized);
        float[] fArr3 = fArr[1];
        float f2 = (fArr3[2] * linearized3) + (fArr3[1] * linearized2) + (fArr3[0] * linearized);
        float[] fArr4 = fArr[2];
        float f3 = (linearized3 * fArr4[2]) + (linearized2 * fArr4[1]) + (linearized * fArr4[0]);
        float[][] fArr5 = CamUtils.XYZ_TO_CAM16RGB;
        float[] fArr6 = fArr5[0];
        float f4 = (fArr6[2] * f3) + (fArr6[1] * f2) + (fArr6[0] * f);
        float[] fArr7 = fArr5[1];
        float f5 = (fArr7[2] * f3) + (fArr7[1] * f2) + (fArr7[0] * f);
        float[] fArr8 = fArr5[2];
        float f6 = (f3 * fArr8[2]) + (f2 * fArr8[1]) + (f * fArr8[0]);
        float f7 = viewingConditions.getRgbD()[0] * f4;
        float f8 = viewingConditions.getRgbD()[1] * f5;
        float f9 = viewingConditions.getRgbD()[2] * f6;
        float pow = (float) Math.pow((Math.abs(f7) * viewingConditions.getFl()) / 100.0d, 0.42d);
        float pow2 = (float) Math.pow((Math.abs(f8) * viewingConditions.getFl()) / 100.0d, 0.42d);
        float pow3 = (float) Math.pow((Math.abs(f9) * viewingConditions.getFl()) / 100.0d, 0.42d);
        float signum = ((Math.signum(f7) * 400.0f) * pow) / (pow + 27.13f);
        float signum2 = ((Math.signum(f8) * 400.0f) * pow2) / (pow2 + 27.13f);
        float signum3 = ((Math.signum(f9) * 400.0f) * pow3) / (pow3 + 27.13f);
        double d = signum3;
        float f10 = ((float) (((signum2 * (-12.0d)) + (signum * 11.0d)) + d)) / 11.0f;
        float f11 = ((float) ((signum + signum2) - (d * 2.0d))) / 9.0f;
        float f12 = signum2 * 20.0f;
        float f13 = ((21.0f * signum3) + ((signum * 20.0f) + f12)) / 20.0f;
        float f14 = (((signum * 40.0f) + f12) + signum3) / 20.0f;
        float atan2 = (((float) Math.atan2(f11, f10)) * 180.0f) / 3.1415927f;
        if (atan2 < 0.0f) {
            atan2 += 360.0f;
        } else if (atan2 >= 360.0f) {
            atan2 -= 360.0f;
        }
        float f15 = atan2;
        float f16 = (3.1415927f * f15) / 180.0f;
        float pow4 = ((float) Math.pow((f14 * viewingConditions.getNbb()) / viewingConditions.getAw(), viewingConditions.getC() * viewingConditions.getZ())) * 100.0f;
        Math.sqrt(pow4 / 100.0f);
        float sqrt = ((float) Math.sqrt(pow4 / 100.0d)) * ((float) Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d)) * ((float) Math.pow((((((((float) (Math.cos((((((double) f15) < 20.14d ? 360.0f + f15 : f15) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * viewingConditions.getNc()) * viewingConditions.getNcb()) * ((float) Math.sqrt((f11 * f11) + (f10 * f10)))) / (f13 + 0.305f), 0.9d));
        float flRoot = viewingConditions.getFlRoot() * sqrt;
        Math.sqrt((r3 * viewingConditions.getC()) / (viewingConditions.getAw() + 4.0f));
        float f17 = (1.7f * pow4) / ((0.007f * pow4) + 1.0f);
        float log = ((float) Math.log((flRoot * 0.0228f) + 1.0f)) * 43.85965f;
        double d2 = f16;
        return new CamColor(f15, sqrt, pow4, f17, ((float) Math.cos(d2)) * log, log * ((float) Math.sin(d2)));
    }

    private static CamColor fromJch(float f, float f2, float f3) {
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        viewingConditions.getC();
        Math.sqrt(f / 100.0d);
        float flRoot = viewingConditions.getFlRoot() * f2;
        Math.sqrt(((f2 / ((float) Math.sqrt(r1))) * viewingConditions.getC()) / (viewingConditions.getAw() + 4.0f));
        float f4 = (1.7f * f) / ((0.007f * f) + 1.0f);
        float log = ((float) Math.log((flRoot * 0.0228d) + 1.0d)) * 43.85965f;
        double d = (3.1415927f * f3) / 180.0f;
        return new CamColor(f3, f2, f, f4, log * ((float) Math.cos(d)), log * ((float) Math.sin(d)));
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0124  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0127  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int toColor(float f, float f2, float f3) {
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float cbrt;
        float f9;
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        float f10 = f2;
        if (f10 < 1.0d || Math.round(f3) <= 0.0d || Math.round(f3) >= 100.0d) {
            return CamUtils.intFromLStar(f3);
        }
        float f11 = 0.0f;
        float min = f < 0.0f ? 0.0f : Math.min(360.0f, f);
        char c = 1;
        float f12 = f10;
        float f13 = 0.0f;
        boolean z = true;
        CamColor camColor = null;
        while (Math.abs(f13 - f10) >= 0.4f) {
            float f14 = 1000.0f;
            float f15 = f11;
            float f16 = 1000.0f;
            float f17 = 100.0f;
            CamColor camColor2 = null;
            while (true) {
                if (Math.abs(f15 - f17) <= 0.01f) {
                    f4 = min;
                    f5 = f11;
                    f6 = f12;
                    break;
                }
                float f18 = ((f17 - f15) / 2.0f) + f15;
                int viewed = fromJch(f18, f12, min).viewed(ViewingConditions.DEFAULT);
                float linearized = CamUtils.linearized(Color.red(viewed));
                float linearized2 = CamUtils.linearized(Color.green(viewed));
                float linearized3 = CamUtils.linearized(Color.blue(viewed));
                float[] fArr = CamUtils.SRGB_TO_XYZ[c];
                float f19 = ((linearized3 * fArr[2]) + ((linearized2 * fArr[c]) + (linearized * fArr[0]))) / 100.0f;
                if (f19 <= 0.008856452f) {
                    cbrt = f19 * 903.2963f;
                    f8 = f14;
                } else {
                    f8 = f14;
                    cbrt = (((float) Math.cbrt(f19)) * 116.0f) - 16.0f;
                }
                float abs = Math.abs(f3 - cbrt);
                if (abs < 0.2f) {
                    CamColor fromColor = fromColor(viewed);
                    f9 = f18;
                    CamColor fromJch = fromJch(fromColor.f13mJ, fromColor.mChroma, min);
                    f4 = min;
                    float f20 = fromColor.mJstar - fromJch.mJstar;
                    f6 = f12;
                    float f21 = fromColor.mAstar - fromJch.mAstar;
                    float f22 = fromColor.mBstar - fromJch.mBstar;
                    float pow = (float) (Math.pow(Math.sqrt((f22 * f22) + (f21 * f21) + (f20 * f20)), 0.63d) * 1.41d);
                    if (pow <= 1.0f) {
                        camColor2 = fromColor;
                        f16 = pow;
                        f14 = abs;
                        f5 = 0.0f;
                        if (f14 != 0.0f && f16 == 0.0f) {
                            break;
                        }
                        if (cbrt >= f3) {
                            f15 = f9;
                        } else {
                            f17 = f9;
                        }
                        f11 = 0.0f;
                        min = f4;
                        f12 = f6;
                        c = 1;
                    }
                } else {
                    f4 = min;
                    f9 = f18;
                    f6 = f12;
                }
                f14 = f8;
                f5 = 0.0f;
                if (f14 != 0.0f) {
                }
                if (cbrt >= f3) {
                }
                f11 = 0.0f;
                min = f4;
                f12 = f6;
                c = 1;
            }
            if (!z) {
                if (camColor2 == null) {
                    f7 = f13;
                    f10 = f6;
                } else {
                    camColor = camColor2;
                    f7 = f6;
                }
                f13 = f7;
                f12 = ((f10 - f7) / 2.0f) + f7;
            } else {
                if (camColor2 != null) {
                    return camColor2.viewed(viewingConditions);
                }
                f12 = ((f10 - f13) / 2.0f) + f13;
                z = false;
            }
            f11 = f5;
            min = f4;
            c = 1;
        }
        return camColor == null ? CamUtils.intFromLStar(f3) : camColor.viewed(viewingConditions);
    }

    final float getChroma() {
        return this.mChroma;
    }

    final float getHue() {
        return this.mHue;
    }

    final int viewed(ViewingConditions viewingConditions) {
        float f;
        float f2 = this.mChroma;
        double d = f2;
        float f3 = this.f13mJ;
        if (d != 0.0d) {
            double d2 = f3;
            if (d2 != 0.0d) {
                f = f2 / ((float) Math.sqrt(d2 / 100.0d));
                float pow = (float) Math.pow(f / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d), 1.1111111111111112d);
                double d3 = (this.mHue * 3.1415927f) / 180.0f;
                float cos = ((float) (Math.cos(2.0d + d3) + 3.8d)) * 0.25f;
                float aw = viewingConditions.getAw() * ((float) Math.pow(f3 / 100.0d, (1.0d / viewingConditions.getC()) / viewingConditions.getZ()));
                float nc = cos * 3846.1538f * viewingConditions.getNc() * viewingConditions.getNcb();
                float nbb = aw / viewingConditions.getNbb();
                float sin = (float) Math.sin(d3);
                float cos2 = (float) Math.cos(d3);
                float f4 = (((0.305f + nbb) * 23.0f) * pow) / (((pow * 108.0f) * sin) + (((11.0f * pow) * cos2) + (nc * 23.0f)));
                float f5 = cos2 * f4;
                float f6 = f4 * sin;
                float f7 = nbb * 460.0f;
                float f8 = ((288.0f * f6) + ((451.0f * f5) + f7)) / 1403.0f;
                float f9 = ((f7 - (891.0f * f5)) - (261.0f * f6)) / 1403.0f;
                float f10 = ((f7 - (f5 * 220.0f)) - (f6 * 6300.0f)) / 1403.0f;
                float fl = (100.0f / viewingConditions.getFl()) * Math.signum(f8) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f8) * 27.13d) / (400.0d - Math.abs(f8))), 2.380952380952381d));
                float fl2 = (100.0f / viewingConditions.getFl()) * Math.signum(f9) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f9) * 27.13d) / (400.0d - Math.abs(f9))), 2.380952380952381d));
                float fl3 = (100.0f / viewingConditions.getFl()) * Math.signum(f10) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f10) * 27.13d) / (400.0d - Math.abs(f10))), 2.380952380952381d));
                float f11 = fl / viewingConditions.getRgbD()[0];
                float f12 = fl2 / viewingConditions.getRgbD()[1];
                float f13 = fl3 / viewingConditions.getRgbD()[2];
                float[][] fArr = CamUtils.CAM16RGB_TO_XYZ;
                float[] fArr2 = fArr[0];
                float f14 = (fArr2[2] * f13) + (fArr2[1] * f12) + (fArr2[0] * f11);
                float[] fArr3 = fArr[1];
                float f15 = (fArr3[2] * f13) + (fArr3[1] * f12) + (fArr3[0] * f11);
                float[] fArr4 = fArr[2];
                return ColorUtils.XYZToColor(f14, f15, (f13 * fArr4[2]) + (f12 * fArr4[1]) + (f11 * fArr4[0]));
            }
        }
        f = 0.0f;
        float pow2 = (float) Math.pow(f / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.getN()), 0.73d), 1.1111111111111112d);
        double d32 = (this.mHue * 3.1415927f) / 180.0f;
        float cos3 = ((float) (Math.cos(2.0d + d32) + 3.8d)) * 0.25f;
        float aw2 = viewingConditions.getAw() * ((float) Math.pow(f3 / 100.0d, (1.0d / viewingConditions.getC()) / viewingConditions.getZ()));
        float nc2 = cos3 * 3846.1538f * viewingConditions.getNc() * viewingConditions.getNcb();
        float nbb2 = aw2 / viewingConditions.getNbb();
        float sin2 = (float) Math.sin(d32);
        float cos22 = (float) Math.cos(d32);
        float f42 = (((0.305f + nbb2) * 23.0f) * pow2) / (((pow2 * 108.0f) * sin2) + (((11.0f * pow2) * cos22) + (nc2 * 23.0f)));
        float f52 = cos22 * f42;
        float f62 = f42 * sin2;
        float f72 = nbb2 * 460.0f;
        float f82 = ((288.0f * f62) + ((451.0f * f52) + f72)) / 1403.0f;
        float f92 = ((f72 - (891.0f * f52)) - (261.0f * f62)) / 1403.0f;
        float f102 = ((f72 - (f52 * 220.0f)) - (f62 * 6300.0f)) / 1403.0f;
        float fl4 = (100.0f / viewingConditions.getFl()) * Math.signum(f82) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f82) * 27.13d) / (400.0d - Math.abs(f82))), 2.380952380952381d));
        float fl22 = (100.0f / viewingConditions.getFl()) * Math.signum(f92) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f92) * 27.13d) / (400.0d - Math.abs(f92))), 2.380952380952381d));
        float fl32 = (100.0f / viewingConditions.getFl()) * Math.signum(f102) * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f102) * 27.13d) / (400.0d - Math.abs(f102))), 2.380952380952381d));
        float f112 = fl4 / viewingConditions.getRgbD()[0];
        float f122 = fl22 / viewingConditions.getRgbD()[1];
        float f132 = fl32 / viewingConditions.getRgbD()[2];
        float[][] fArr5 = CamUtils.CAM16RGB_TO_XYZ;
        float[] fArr22 = fArr5[0];
        float f142 = (fArr22[2] * f132) + (fArr22[1] * f122) + (fArr22[0] * f112);
        float[] fArr32 = fArr5[1];
        float f152 = (fArr32[2] * f132) + (fArr32[1] * f122) + (fArr32[0] * f112);
        float[] fArr42 = fArr5[2];
        return ColorUtils.XYZToColor(f142, f152, (f132 * fArr42[2]) + (f122 * fArr42[1]) + (f112 * fArr42[0]));
    }
}
