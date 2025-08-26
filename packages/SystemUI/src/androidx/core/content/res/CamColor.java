package androidx.core.content.res;

import android.graphics.Color;
import androidx.appcompat.animation.SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0;
import androidx.core.graphics.ColorUtils;

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
        float fLinearized = CamUtils.linearized(Color.red(i));
        float fLinearized2 = CamUtils.linearized(Color.green(i));
        float fLinearized3 = CamUtils.linearized(Color.blue(i));
        float[][] fArr = CamUtils.SRGB_TO_XYZ;
        float[] fArr2 = fArr[0];
        float f = (fArr2[2] * fLinearized3) + (fArr2[1] * fLinearized2) + (fArr2[0] * fLinearized);
        float[] fArr3 = fArr[1];
        float f2 = (fArr3[2] * fLinearized3) + (fArr3[1] * fLinearized2) + (fArr3[0] * fLinearized);
        float[] fArr4 = fArr[2];
        float f3 = (fLinearized3 * fArr4[2]) + (fLinearized2 * fArr4[1]) + (fLinearized * fArr4[0]);
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
        float fAbs = Math.abs(f7);
        float f10 = viewingConditions.mFl;
        float fPow = (float) Math.pow((fAbs * f10) / 100.0d, 0.42d);
        float fPow2 = (float) Math.pow((Math.abs(f8) * f10) / 100.0d, 0.42d);
        float fPow3 = (float) Math.pow((Math.abs(f9) * f10) / 100.0d, 0.42d);
        float fSignum = ((Math.signum(f7) * 400.0f) * fPow) / (fPow + 27.13f);
        float fSignum2 = ((Math.signum(f8) * 400.0f) * fPow2) / (fPow2 + 27.13f);
        float fSignum3 = ((Math.signum(f9) * 400.0f) * fPow3) / (fPow3 + 27.13f);
        double d = fSignum3;
        float f11 = ((float) (((fSignum2 * (-12.0d)) + (fSignum * 11.0d)) + d)) / 11.0f;
        float f12 = ((float) ((fSignum + fSignum2) - (d * 2.0d))) / 9.0f;
        float f13 = fSignum2 * 20.0f;
        float f14 = ((21.0f * fSignum3) + ((fSignum * 20.0f) + f13)) / 20.0f;
        float f15 = (((fSignum * 40.0f) + f13) + fSignum3) / 20.0f;
        float fAtan2 = (((float) Math.atan2(f12, f11)) * 180.0f) / 3.1415927f;
        if (fAtan2 < 0.0f) {
            fAtan2 += 360.0f;
        } else if (fAtan2 >= 360.0f) {
            fAtan2 -= 360.0f;
        }
        float f16 = (3.1415927f * fAtan2) / 180.0f;
        float f17 = f15 * viewingConditions.mNbb;
        float f18 = viewingConditions.mAw;
        double d2 = f17 / f18;
        float f19 = viewingConditions.mZ;
        float f20 = viewingConditions.mC;
        float fPow4 = ((float) Math.pow(d2, f19 * f20)) * 100.0f;
        float fSqrt = (4.0f / f20) * ((float) Math.sqrt(fPow4 / 100.0f)) * (f18 + 4.0f);
        float f21 = viewingConditions.mFlRoot;
        float f22 = fSqrt * f21;
        float fSqrt2 = ((float) Math.sqrt(fPow4 / 100.0d)) * ((float) Math.pow(1.64d - Math.pow(0.29d, viewingConditions.mN), 0.73d)) * ((float) Math.pow((((((((float) (Math.cos((((((double) fAtan2) < 20.14d ? fAtan2 + 360.0f : fAtan2) * 3.141592653589793d) / 180.0d) + 2.0d) + 3.8d)) * 0.25f) * 3846.1538f) * viewingConditions.mNc) * viewingConditions.mNcb) * ((float) Math.sqrt((f12 * f12) + (f11 * f11)))) / (f14 + 0.305f), 0.9d));
        float f23 = fSqrt2 * f21;
        float fSqrt3 = ((float) Math.sqrt((r1 * f20) / r3)) * 50.0f;
        float f24 = (1.7f * fPow4) / ((0.007f * fPow4) + 1.0f);
        float fLog = ((float) Math.log((0.0228f * f23) + 1.0f)) * 43.85965f;
        double d3 = f16;
        float fCos = fLog * ((float) Math.cos(d3));
        float fSin = fLog * ((float) Math.sin(d3));
        fArr5[0] = fAtan2;
        fArr5[1] = fSqrt2;
        return new CamColor(fArr5[0], fArr5[1], fPow4, f22, f23, fSqrt3, f24, fCos, fSin);
    }

    public static CamColor fromJch(float f, float f2, float f3) {
        ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
        float fSqrt = (4.0f / viewingConditions.mC) * ((float) Math.sqrt(f / 100.0d));
        float f4 = viewingConditions.mAw + 4.0f;
        float f5 = viewingConditions.mFlRoot;
        float f6 = fSqrt * f4 * f5;
        float f7 = f5 * f2;
        float fSqrt2 = ((float) Math.sqrt(((f2 / ((float) Math.sqrt(r4))) * viewingConditions.mC) / f4)) * 50.0f;
        float f8 = (1.7f * f) / ((0.007f * f) + 1.0f);
        float fLog = ((float) Math.log((f7 * 0.0228d) + 1.0d)) * 43.85965f;
        double d = (3.1415927f * f3) / 180.0f;
        return new CamColor(f3, f2, f, f6, f7, fSqrt2, f8, ((float) Math.cos(d)) * fLog, fLog * ((float) Math.sin(d)));
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int viewed(ViewingConditions viewingConditions) {
        float fSqrt;
        float f = this.mChroma;
        double d = f;
        float f2 = this.mJ;
        if (d != 0.0d) {
            double d2 = f2;
            fSqrt = d2 == 0.0d ? 0.0f : f / ((float) Math.sqrt(d2 / 100.0d));
        }
        float fPow = (float) Math.pow(fSqrt / Math.pow(1.64d - Math.pow(0.29d, viewingConditions.mN), 0.73d), 1.1111111111111112d);
        double d3 = (this.mHue * 3.1415927f) / 180.0f;
        float fCos = ((float) (Math.cos(2.0d + d3) + 3.8d)) * 0.25f;
        float fPow2 = viewingConditions.mAw * ((float) Math.pow(f2 / 100.0d, (1.0d / viewingConditions.mC) / viewingConditions.mZ));
        float f3 = fCos * 3846.1538f * viewingConditions.mNc * viewingConditions.mNcb;
        float f4 = fPow2 / viewingConditions.mNbb;
        float fSin = (float) Math.sin(d3);
        float fCos2 = (float) Math.cos(d3);
        float f5 = (((0.305f + f4) * 23.0f) * fPow) / (((fPow * 108.0f) * fSin) + (((11.0f * fPow) * fCos2) + (f3 * 23.0f)));
        float f6 = fCos2 * f5;
        float f7 = f5 * fSin;
        float f8 = f4 * 460.0f;
        float f9 = ((288.0f * f7) + ((451.0f * f6) + f8)) / 1403.0f;
        float fM = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f7, 261.0f, f8 - (891.0f * f6), 1403.0f);
        float fM2 = SeslRecoilAnimator$Holder$$ExternalSyntheticOutline0.m(f7, 6300.0f, f8 - (f6 * 220.0f), 1403.0f);
        float fMax = (float) Math.max(0.0d, (Math.abs(f9) * 27.13d) / (400.0d - Math.abs(f9)));
        float fSignum = Math.signum(f9);
        float f10 = 100.0f / viewingConditions.mFl;
        float fPow3 = fSignum * f10 * ((float) Math.pow(fMax, 2.380952380952381d));
        float fSignum2 = Math.signum(fM) * f10 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(fM) * 27.13d) / (400.0d - Math.abs(fM))), 2.380952380952381d));
        float fSignum3 = Math.signum(fM2) * f10 * ((float) Math.pow((float) Math.max(0.0d, (Math.abs(fM2) * 27.13d) / (400.0d - Math.abs(fM2))), 2.380952380952381d));
        float[] fArr = viewingConditions.mRgbD;
        float f11 = fPow3 / fArr[0];
        float f12 = fSignum2 / fArr[1];
        float f13 = fSignum3 / fArr[2];
        float[][] fArr2 = CamUtils.CAM16RGB_TO_XYZ;
        float[] fArr3 = fArr2[0];
        float f14 = (fArr3[2] * f13) + (fArr3[1] * f12) + (fArr3[0] * f11);
        float[] fArr4 = fArr2[1];
        float f15 = (fArr4[2] * f13) + (fArr4[1] * f12) + (fArr4[0] * f11);
        float[] fArr5 = fArr2[2];
        return ColorUtils.XYZToColor(f14, f15, (f13 * fArr5[2]) + (f12 * fArr5[1]) + (f11 * fArr5[0]));
    }
}
