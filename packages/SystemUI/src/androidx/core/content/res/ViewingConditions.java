package androidx.core.content.res;

import androidx.compose.material3.internal.colorUtil.Frame$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewingConditions {
    public static final ViewingConditions DEFAULT;
    public final float mAw;
    public final float mC;
    public final float mFl;
    public final float mFlRoot;
    public final float mN;
    public final float mNbb;
    public final float mNc;
    public final float mNcb;
    public final float[] mRgbD;
    public final float mZ;

    static {
        float[] fArr = CamUtils.WHITE_POINT_D65;
        float yFromLStar = (float) ((CamUtils.yFromLStar() * 63.66197723675813d) / 100.0d);
        float[][] fArr2 = CamUtils.XYZ_TO_CAM16RGB;
        float f = fArr[0];
        float[] fArr3 = fArr2[0];
        float f2 = fArr3[0] * f;
        float f3 = fArr[1];
        float f4 = (fArr3[1] * f3) + f2;
        float f5 = fArr[2];
        float f6 = (fArr3[2] * f5) + f4;
        float[] fArr4 = fArr2[1];
        float f7 = (fArr4[2] * f5) + (fArr4[1] * f3) + (fArr4[0] * f);
        float[] fArr5 = fArr2[2];
        float f8 = (f5 * fArr5[2]) + (f3 * fArr5[1]) + (f * fArr5[0]);
        float f9 = ((double) 1.0f) >= 0.9d ? 0.69f : 0.655f;
        float m = Frame$$ExternalSyntheticOutline0.m((float) Math.exp(((-yFromLStar) - 42.0f) / 92.0f), 0.2777778f, 1.0f, 1.0f);
        double d = m;
        if (d > 1.0d) {
            m = 1.0f;
        } else if (d < 0.0d) {
            m = 0.0f;
        }
        float[] fArr6 = {(((100.0f / f6) * m) + 1.0f) - m, (((100.0f / f7) * m) + 1.0f) - m, (((100.0f / f8) * m) + 1.0f) - m};
        float f10 = 1.0f / ((5.0f * yFromLStar) + 1.0f);
        float f11 = f10 * f10 * f10 * f10;
        float f12 = 1.0f - f11;
        float cbrt = (0.1f * f12 * f12 * ((float) Math.cbrt(yFromLStar * 5.0d))) + (f11 * yFromLStar);
        float yFromLStar2 = CamUtils.yFromLStar() / fArr[1];
        double d2 = yFromLStar2;
        float sqrt = ((float) Math.sqrt(d2)) + 1.48f;
        float pow = 0.725f / ((float) Math.pow(d2, 0.2d));
        float[] fArr7 = {(float) Math.pow(((fArr6[0] * cbrt) * f6) / 100.0d, 0.42d), (float) Math.pow(((fArr6[1] * cbrt) * f7) / 100.0d, 0.42d), (float) Math.pow(((fArr6[2] * cbrt) * f8) / 100.0d, 0.42d)};
        float f13 = fArr7[0];
        float f14 = (f13 * 400.0f) / (f13 + 27.13f);
        float f15 = fArr7[1];
        float f16 = (f15 * 400.0f) / (f15 + 27.13f);
        float f17 = fArr7[2];
        float[] fArr8 = {f14, f16, (400.0f * f17) / (f17 + 27.13f)};
        DEFAULT = new ViewingConditions(yFromLStar2, ((fArr8[2] * 0.05f) + (fArr8[0] * 2.0f) + fArr8[1]) * pow, pow, pow, f9, 1.0f, fArr6, cbrt, (float) Math.pow(cbrt, 0.25d), sqrt);
    }

    private ViewingConditions(float f, float f2, float f3, float f4, float f5, float f6, float[] fArr, float f7, float f8, float f9) {
        this.mN = f;
        this.mAw = f2;
        this.mNbb = f3;
        this.mNcb = f4;
        this.mC = f5;
        this.mNc = f6;
        this.mRgbD = fArr;
        this.mFl = f7;
        this.mFlRoot = f8;
        this.mZ = f9;
    }
}
