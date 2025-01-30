package androidx.core.content.res;

import android.graphics.Color;
import androidx.core.graphics.ColorUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CamColor {
    public final float mAstar;
    public final float mBstar;
    public final float mChroma;
    public final float mHue;

    /* renamed from: mJ */
    public final float f33mJ;
    public final float mJstar;

    public CamColor(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.mHue = f;
        this.mChroma = f2;
        this.f33mJ = f3;
        this.mJstar = f7;
        this.mAstar = f8;
        this.mBstar = f9;
    }

    public static CamColor fromColor(int i) {
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
        float[] fArr9 = viewingConditions.mRgbD;
        float f7 = fArr9[0] * f4;
        float f8 = fArr9[1] * f5;
        float f9 = fArr9[2] * f6;
        float abs = Math.abs(f7);
        float f10 = viewingConditions.mFl;
        float pow = (float) Math.pow((abs * f10) / 100.0d, 0.42d);
        float pow2 = (float) Math.pow((Math.abs(f8) * f10) / 100.0d, 0.42d);
        float pow3 = (float) Math.pow((Math.abs(f9) * f10) / 100.0d, 0.42d);
        float signum = ((Math.signum(f7) * 400.0f) * pow) / (pow + 27.13f);
        float signum2 = ((Math.signum(f8) * 400.0f) * pow2) / (pow2 + 27.13f);
        float signum3 = ((Math.signum(f9) * 400.0f) * pow3) / (pow3 + 27.13f);
        double d = signum3;
        float f11 = ((float) (((signum2 * (-12.0d)) + (signum * 11.0d)) + d)) / 11.0f;
        float f12 = ((float) ((signum + signum2) - (d * 2.0d))) / 9.0f;
        float f13 = signum2 * 20.0f;
        float f14 = ((21.0f * signum3) + ((signum * 20.0f) + f13)) / 20.0f;
        float f15 = (((signum * 40.0f) + f13) + signum3) / 20.0f;
        float atan2 = (((float) Math.atan2(f12, f11)) * 180.0f) / 3.1415927f;
        if (atan2 < 0.0f) {
            atan2 += 360.0f;
        } else if (atan2 >= 360.0f) {
            atan2 -= 360.0f;
        }
        float f16 = atan2;
        float f17 = (3.1415927f * f16) / 180.0f;
        float f18 = f15 * viewingConditions.mNbb;
        float f19 = viewingConditions.mAw;
        double d2 = f18 / f19;
        float f20 = viewingConditions.f36mZ;
        float f21 = viewingConditions.f34mC;
        float pow4 = ((float) Math.pow(d2, f20 * f21)) * 100.0f;
        float sqrt = (4.0f / f21) * ((float) Math.sqrt(pow4 / 100.0f)) * (f19 + 4.0f);
        float f22 = viewingConditions.mFlRoot;
        float f23 = sqrt * f22;
        float pow5 = ((float) Math.pow(1.64d - Math.pow(0.29d, viewingConditions.f35mN), 0.73d)) * ((float) Math.pow((((((((float) (Math.cos((((((double) f16) < 20.14d ? 360.0f + f16 : f16) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * viewingConditions.mNc) * viewingConditions.mNcb) * ((float) Math.sqrt((f12 * f12) + (f11 * f11)))) / (f14 + 0.305f), 0.9d)) * ((float) Math.sqrt(pow4 / 100.0d));
        float f24 = pow5 * f22;
        float sqrt2 = ((float) Math.sqrt((r0 * f21) / r1)) * 50.0f;
        float f25 = (1.7f * pow4) / ((0.007f * pow4) + 1.0f);
        float log = ((float) Math.log((0.0228f * f24) + 1.0f)) * 43.85965f;
        double d3 = f17;
        return new CamColor(f16, pow5, pow4, f23, f24, sqrt2, f25, log * ((float) Math.cos(d3)), log * ((float) Math.sin(d3)));
    }

    public static CamColor fromJch(float f, float f2, float f3) {
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        float sqrt = (4.0f / viewingConditions.f34mC) * ((float) Math.sqrt(f / 100.0d));
        float f4 = viewingConditions.mAw + 4.0f;
        float f5 = viewingConditions.mFlRoot;
        float f6 = sqrt * f4 * f5;
        float f7 = f2 * f5;
        float sqrt2 = ((float) Math.sqrt(((f2 / ((float) Math.sqrt(r4))) * viewingConditions.f34mC) / f4)) * 50.0f;
        float f8 = (1.7f * f) / ((0.007f * f) + 1.0f);
        float log = ((float) Math.log((f7 * 0.0228d) + 1.0d)) * 43.85965f;
        double d = (3.1415927f * f3) / 180.0f;
        return new CamColor(f3, f2, f, f6, f7, sqrt2, f8, log * ((float) Math.cos(d)), log * ((float) Math.sin(d)));
    }

    public final int viewed(ViewingConditions viewingConditions) {
        float f;
        float f2 = this.mChroma;
        double d = f2;
        float f3 = this.f33mJ;
        if (d != 0.0d) {
            double d2 = f3;
            if (d2 != 0.0d) {
                f = f2 / ((float) Math.sqrt(d2 / 100.0d));
                float pow = (float) Math.pow(f / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.f35mN), 0.73d), 1.1111111111111112d);
                double d3 = (this.mHue * 3.1415927f) / 180.0f;
                float cos = ((float) (Math.cos(2.0d + d3) + 3.8d)) * 0.25f;
                float pow2 = viewingConditions.mAw * ((float) Math.pow(f3 / 100.0d, (1.0d / viewingConditions.f34mC) / viewingConditions.f36mZ));
                float f4 = cos * 3846.1538f * viewingConditions.mNc * viewingConditions.mNcb;
                float f5 = pow2 / viewingConditions.mNbb;
                float sin = (float) Math.sin(d3);
                float cos2 = (float) Math.cos(d3);
                float f6 = (((0.305f + f5) * 23.0f) * pow) / (((pow * 108.0f) * sin) + (((11.0f * pow) * cos2) + (f4 * 23.0f)));
                float f7 = cos2 * f6;
                float f8 = f6 * sin;
                float f9 = f5 * 460.0f;
                float f10 = ((288.0f * f8) + ((451.0f * f7) + f9)) / 1403.0f;
                float f11 = ((f9 - (891.0f * f7)) - (261.0f * f8)) / 1403.0f;
                float f12 = ((f9 - (f7 * 220.0f)) - (f8 * 6300.0f)) / 1403.0f;
                float max = (float) Math.max(0.0d, (Math.abs(f10) * 27.13d) / (400.0d - Math.abs(f10)));
                float signum = Math.signum(f10);
                float f13 = 100.0f / viewingConditions.mFl;
                float pow3 = signum * f13 * ((float) Math.pow(max, 2.380952380952381d));
                float signum2 = Math.signum(f11) * f13 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f11) * 27.13d) / (400.0d - Math.abs(f11))), 2.380952380952381d));
                float signum3 = Math.signum(f12) * f13 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f12) * 27.13d) / (400.0d - Math.abs(f12))), 2.380952380952381d));
                float[] fArr = viewingConditions.mRgbD;
                float f14 = pow3 / fArr[0];
                float f15 = signum2 / fArr[1];
                float f16 = signum3 / fArr[2];
                float[][] fArr2 = CamUtils.CAM16RGB_TO_XYZ;
                float[] fArr3 = fArr2[0];
                float f17 = (fArr3[2] * f16) + (fArr3[1] * f15) + (fArr3[0] * f14);
                float[] fArr4 = fArr2[1];
                float f18 = (fArr4[2] * f16) + (fArr4[1] * f15) + (fArr4[0] * f14);
                float[] fArr5 = fArr2[2];
                return ColorUtils.XYZToColor(f17, f18, (f16 * fArr5[2]) + (f15 * fArr5[1]) + (f14 * fArr5[0]));
            }
        }
        f = 0.0f;
        float pow4 = (float) Math.pow(f / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.f35mN), 0.73d), 1.1111111111111112d);
        double d32 = (this.mHue * 3.1415927f) / 180.0f;
        float cos3 = ((float) (Math.cos(2.0d + d32) + 3.8d)) * 0.25f;
        float pow22 = viewingConditions.mAw * ((float) Math.pow(f3 / 100.0d, (1.0d / viewingConditions.f34mC) / viewingConditions.f36mZ));
        float f42 = cos3 * 3846.1538f * viewingConditions.mNc * viewingConditions.mNcb;
        float f52 = pow22 / viewingConditions.mNbb;
        float sin2 = (float) Math.sin(d32);
        float cos22 = (float) Math.cos(d32);
        float f62 = (((0.305f + f52) * 23.0f) * pow4) / (((pow4 * 108.0f) * sin2) + (((11.0f * pow4) * cos22) + (f42 * 23.0f)));
        float f72 = cos22 * f62;
        float f82 = f62 * sin2;
        float f92 = f52 * 460.0f;
        float f102 = ((288.0f * f82) + ((451.0f * f72) + f92)) / 1403.0f;
        float f112 = ((f92 - (891.0f * f72)) - (261.0f * f82)) / 1403.0f;
        float f122 = ((f92 - (f72 * 220.0f)) - (f82 * 6300.0f)) / 1403.0f;
        float max2 = (float) Math.max(0.0d, (Math.abs(f102) * 27.13d) / (400.0d - Math.abs(f102)));
        float signum4 = Math.signum(f102);
        float f132 = 100.0f / viewingConditions.mFl;
        float pow32 = signum4 * f132 * ((float) Math.pow(max2, 2.380952380952381d));
        float signum22 = Math.signum(f112) * f132 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f112) * 27.13d) / (400.0d - Math.abs(f112))), 2.380952380952381d));
        float signum32 = Math.signum(f122) * f132 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(f122) * 27.13d) / (400.0d - Math.abs(f122))), 2.380952380952381d));
        float[] fArr6 = viewingConditions.mRgbD;
        float f142 = pow32 / fArr6[0];
        float f152 = signum22 / fArr6[1];
        float f162 = signum32 / fArr6[2];
        float[][] fArr22 = CamUtils.CAM16RGB_TO_XYZ;
        float[] fArr32 = fArr22[0];
        float f172 = (fArr32[2] * f162) + (fArr32[1] * f152) + (fArr32[0] * f142);
        float[] fArr42 = fArr22[1];
        float f182 = (fArr42[2] * f162) + (fArr42[1] * f152) + (fArr42[0] * f142);
        float[] fArr52 = fArr22[2];
        return ColorUtils.XYZToColor(f172, f182, (f162 * fArr52[2]) + (f152 * fArr52[1]) + (f142 * fArr52[0]));
    }
}
