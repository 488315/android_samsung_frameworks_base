package androidx.core.content.res;

import android.graphics.Color;
import androidx.appcompat.animation.SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CamColor {
    public final float mAstar;
    public final float mBstar;
    public final float mChroma;
    public final float mHue;
    public final float mJ;
    public final float mJstar;

    public CamColor(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.mHue = f;
        this.mChroma = f2;
        this.mJ = f3;
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
        float[] fArr5 = {f, f2, f3};
        float[][] fArr6 = CamUtils.XYZ_TO_CAM16RGB;
        float[] fArr7 = fArr6[0];
        float f4 = (fArr7[2] * f3) + (fArr7[1] * f2) + (fArr7[0] * f);
        float[] fArr8 = fArr6[1];
        float f5 = (fArr8[2] * f3) + (fArr8[1] * f2) + (fArr8[0] * f);
        float[] fArr9 = fArr6[2];
        float f6 = (f3 * fArr9[2]) + (f2 * fArr9[1]) + (f * fArr9[0]);
        float[] fArr10 = viewingConditions.mRgbD;
        float f7 = fArr10[0] * f4;
        float f8 = fArr10[1] * f5;
        float f9 = fArr10[2] * f6;
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
        float f16 = (3.1415927f * atan2) / 180.0f;
        float f17 = f15 * viewingConditions.mNbb;
        float f18 = viewingConditions.mAw;
        double d2 = f17 / f18;
        float f19 = viewingConditions.mZ;
        float f20 = viewingConditions.mC;
        float pow4 = ((float) Math.pow(d2, f19 * f20)) * 100.0f;
        float sqrt = (4.0f / f20) * ((float) Math.sqrt(pow4 / 100.0f)) * (f18 + 4.0f);
        float f21 = viewingConditions.mFlRoot;
        float f22 = sqrt * f21;
        float sqrt2 = ((float) Math.sqrt(pow4 / 100.0d)) * ((float) Math.pow(1.64d - Math.pow(0.29d, viewingConditions.mN), 0.73d)) * ((float) Math.pow((((((((float) (Math.cos((((((double) atan2) < 20.14d ? atan2 + 360.0f : atan2) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * viewingConditions.mNc) * viewingConditions.mNcb) * ((float) Math.sqrt((f12 * f12) + (f11 * f11)))) / (f14 + 0.305f), 0.9d));
        float f23 = sqrt2 * f21;
        float sqrt3 = ((float) Math.sqrt((r1 * f20) / r3)) * 50.0f;
        float f24 = (1.7f * pow4) / ((0.007f * pow4) + 1.0f);
        float log = ((float) Math.log((0.0228f * f23) + 1.0f)) * 43.85965f;
        double d3 = f16;
        float cos = log * ((float) Math.cos(d3));
        float sin = log * ((float) Math.sin(d3));
        fArr5[0] = atan2;
        fArr5[1] = sqrt2;
        return new CamColor(fArr5[0], fArr5[1], pow4, f22, f23, sqrt3, f24, cos, sin);
    }

    public static CamColor fromJch(float f, float f2, float f3) {
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        float sqrt = (4.0f / viewingConditions.mC) * ((float) Math.sqrt(f / 100.0d));
        float f4 = viewingConditions.mAw + 4.0f;
        float f5 = viewingConditions.mFlRoot;
        float f6 = sqrt * f4 * f5;
        float f7 = f5 * f2;
        float sqrt2 = ((float) Math.sqrt(((f2 / ((float) Math.sqrt(r4))) * viewingConditions.mC) / f4)) * 50.0f;
        float f8 = (1.7f * f) / ((0.007f * f) + 1.0f);
        float log = ((float) Math.log((f7 * 0.0228d) + 1.0d)) * 43.85965f;
        double d = (3.1415927f * f3) / 180.0f;
        return new CamColor(f3, f2, f, f6, f7, sqrt2, f8, ((float) Math.cos(d)) * log, log * ((float) Math.sin(d)));
    }

    public final int viewed(ViewingConditions viewingConditions) {
        float f;
        float f2 = this.mChroma;
        double d = f2;
        float f3 = this.mJ;
        if (d != 0.0d) {
            double d2 = f3;
            if (d2 != 0.0d) {
                f = f2 / ((float) Math.sqrt(d2 / 100.0d));
                float pow = (float) Math.pow(f / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.mN), 0.73d), 1.1111111111111112d);
                double d3 = (this.mHue * 3.1415927f) / 180.0f;
                float cos = ((float) (Math.cos(2.0d + d3) + 3.8d)) * 0.25f;
                float pow2 = viewingConditions.mAw * ((float) Math.pow(f3 / 100.0d, (1.0d / viewingConditions.mC) / viewingConditions.mZ));
                float f4 = cos * 3846.1538f * viewingConditions.mNc * viewingConditions.mNcb;
                float f5 = pow2 / viewingConditions.mNbb;
                float sin = (float) Math.sin(d3);
                float cos2 = (float) Math.cos(d3);
                float f6 = (((0.305f + f5) * 23.0f) * pow) / (((pow * 108.0f) * sin) + (((11.0f * pow) * cos2) + (f4 * 23.0f)));
                float f7 = cos2 * f6;
                float f8 = f6 * sin;
                float f9 = f5 * 460.0f;
                float f10 = ((288.0f * f8) + ((451.0f * f7) + f9)) / 1403.0f;
                float m = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f8, 261.0f, f9 - (891.0f * f7), 1403.0f);
                float m2 = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f8, 6300.0f, f9 - (f7 * 220.0f), 1403.0f);
                float max = (float) Math.max(0.0d, (Math.abs(f10) * 27.13d) / (400.0d - Math.abs(f10)));
                float signum = Math.signum(f10);
                float f11 = 100.0f / viewingConditions.mFl;
                float pow3 = signum * f11 * ((float) Math.pow(max, 2.380952380952381d));
                float signum2 = Math.signum(m) * f11 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(m) * 27.13d) / (400.0d - Math.abs(m))), 2.380952380952381d));
                float signum3 = Math.signum(m2) * f11 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(m2) * 27.13d) / (400.0d - Math.abs(m2))), 2.380952380952381d));
                float[] fArr = viewingConditions.mRgbD;
                float f12 = pow3 / fArr[0];
                float f13 = signum2 / fArr[1];
                float f14 = signum3 / fArr[2];
                float[][] fArr2 = CamUtils.CAM16RGB_TO_XYZ;
                float[] fArr3 = fArr2[0];
                float f15 = (fArr3[2] * f14) + (fArr3[1] * f13) + (fArr3[0] * f12);
                float[] fArr4 = fArr2[1];
                float f16 = (fArr4[2] * f14) + (fArr4[1] * f13) + (fArr4[0] * f12);
                float[] fArr5 = fArr2[2];
                return ColorUtils.XYZToColor(f15, f16, (f14 * fArr5[2]) + (f13 * fArr5[1]) + (f12 * fArr5[0]));
            }
        }
        f = 0.0f;
        float pow4 = (float) Math.pow(f / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.mN), 0.73d), 1.1111111111111112d);
        double d32 = (this.mHue * 3.1415927f) / 180.0f;
        float cos3 = ((float) (Math.cos(2.0d + d32) + 3.8d)) * 0.25f;
        float pow22 = viewingConditions.mAw * ((float) Math.pow(f3 / 100.0d, (1.0d / viewingConditions.mC) / viewingConditions.mZ));
        float f42 = cos3 * 3846.1538f * viewingConditions.mNc * viewingConditions.mNcb;
        float f52 = pow22 / viewingConditions.mNbb;
        float sin2 = (float) Math.sin(d32);
        float cos22 = (float) Math.cos(d32);
        float f62 = (((0.305f + f52) * 23.0f) * pow4) / (((pow4 * 108.0f) * sin2) + (((11.0f * pow4) * cos22) + (f42 * 23.0f)));
        float f72 = cos22 * f62;
        float f82 = f62 * sin2;
        float f92 = f52 * 460.0f;
        float f102 = ((288.0f * f82) + ((451.0f * f72) + f92)) / 1403.0f;
        float m3 = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f82, 261.0f, f92 - (891.0f * f72), 1403.0f);
        float m22 = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f82, 6300.0f, f92 - (f72 * 220.0f), 1403.0f);
        float max2 = (float) Math.max(0.0d, (Math.abs(f102) * 27.13d) / (400.0d - Math.abs(f102)));
        float signum4 = Math.signum(f102);
        float f112 = 100.0f / viewingConditions.mFl;
        float pow32 = signum4 * f112 * ((float) Math.pow(max2, 2.380952380952381d));
        float signum22 = Math.signum(m3) * f112 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(m3) * 27.13d) / (400.0d - Math.abs(m3))), 2.380952380952381d));
        float signum32 = Math.signum(m22) * f112 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(m22) * 27.13d) / (400.0d - Math.abs(m22))), 2.380952380952381d));
        float[] fArr6 = viewingConditions.mRgbD;
        float f122 = pow32 / fArr6[0];
        float f132 = signum22 / fArr6[1];
        float f142 = signum32 / fArr6[2];
        float[][] fArr22 = CamUtils.CAM16RGB_TO_XYZ;
        float[] fArr32 = fArr22[0];
        float f152 = (fArr32[2] * f142) + (fArr32[1] * f132) + (fArr32[0] * f122);
        float[] fArr42 = fArr22[1];
        float f162 = (fArr42[2] * f142) + (fArr42[1] * f132) + (fArr42[0] * f122);
        float[] fArr52 = fArr22[2];
        return ColorUtils.XYZToColor(f152, f162, (f142 * fArr52[2]) + (f132 * fArr52[1]) + (f122 * fArr52[0]));
    }
}
