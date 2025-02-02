package androidx.core.content.res;

/* loaded from: classes.dex */
final class ViewingConditions {
    static final ViewingConditions DEFAULT;
    private final float mAw;

    /* renamed from: mC */
    private final float f14mC;
    private final float mFl;
    private final float mFlRoot;

    /* renamed from: mN */
    private final float f15mN;
    private final float mNbb;
    private final float mNc;
    private final float mNcb;
    private final float[] mRgbD;

    /* renamed from: mZ */
    private final float f16mZ;

    static {
        float f;
        float f2;
        float[] fArr = CamUtils.WHITE_POINT_D65;
        float yFromLStar = (float) ((CamUtils.yFromLStar() * 63.66197723675813d) / 100.0d);
        float[][] fArr2 = CamUtils.XYZ_TO_CAM16RGB;
        float f3 = fArr[0];
        float[] fArr3 = fArr2[0];
        float f4 = fArr3[0] * f3;
        float f5 = fArr[1];
        float f6 = (fArr3[1] * f5) + f4;
        float f7 = fArr[2];
        float f8 = (fArr3[2] * f7) + f6;
        float[] fArr4 = fArr2[1];
        float f9 = (fArr4[2] * f7) + (fArr4[1] * f5) + (fArr4[0] * f3);
        float[] fArr5 = fArr2[2];
        float f10 = (f7 * fArr5[2]) + (f5 * fArr5[1]) + (f3 * fArr5[0]);
        if (1.0f >= 0.9d) {
            f = 0.100000046f;
            f2 = 0.59f;
        } else {
            f = 0.12999998f;
            f2 = 0.525f;
        }
        float f11 = f + f2;
        float exp = (1.0f - (((float) Math.exp(((-yFromLStar) - 42.0f) / 92.0f)) * 0.2777778f)) * 1.0f;
        double d = exp;
        if (d > 1.0d) {
            exp = 1.0f;
        } else if (d < 0.0d) {
            exp = 0.0f;
        }
        float[] fArr6 = {(((100.0f / f8) * exp) + 1.0f) - exp, (((100.0f / f9) * exp) + 1.0f) - exp, (((100.0f / f10) * exp) + 1.0f) - exp};
        float f12 = 1.0f / ((5.0f * yFromLStar) + 1.0f);
        float f13 = f12 * f12 * f12 * f12;
        float f14 = 1.0f - f13;
        float cbrt = (0.1f * f14 * f14 * ((float) Math.cbrt(yFromLStar * 5.0d))) + (f13 * yFromLStar);
        float yFromLStar2 = CamUtils.yFromLStar() / fArr[1];
        double d2 = yFromLStar2;
        float sqrt = ((float) Math.sqrt(d2)) + 1.48f;
        float pow = 0.725f / ((float) Math.pow(d2, 0.2d));
        float pow2 = (float) Math.pow(((fArr6[2] * cbrt) * f10) / 100.0d, 0.42d);
        float[] fArr7 = {(float) Math.pow(((fArr6[0] * cbrt) * f8) / 100.0d, 0.42d), (float) Math.pow(((fArr6[1] * cbrt) * f9) / 100.0d, 0.42d), pow2};
        float f15 = fArr7[0];
        float f16 = fArr7[1];
        DEFAULT = new ViewingConditions(yFromLStar2, ((((400.0f * pow2) / (pow2 + 27.13f)) * 0.05f) + (((f15 * 400.0f) / (f15 + 27.13f)) * 2.0f) + ((f16 * 400.0f) / (f16 + 27.13f))) * pow, pow, pow, f11, 1.0f, fArr6, cbrt, (float) Math.pow(cbrt, 0.25d), sqrt);
    }

    private ViewingConditions(float f, float f2, float f3, float f4, float f5, float f6, float[] fArr, float f7, float f8, float f9) {
        this.f15mN = f;
        this.mAw = f2;
        this.mNbb = f3;
        this.mNcb = f4;
        this.f14mC = f5;
        this.mNc = f6;
        this.mRgbD = fArr;
        this.mFl = f7;
        this.mFlRoot = f8;
        this.f16mZ = f9;
    }

    final float getAw() {
        return this.mAw;
    }

    final float getC() {
        return this.f14mC;
    }

    final float getFl() {
        return this.mFl;
    }

    final float getFlRoot() {
        return this.mFlRoot;
    }

    final float getN() {
        return this.f15mN;
    }

    final float getNbb() {
        return this.mNbb;
    }

    final float getNc() {
        return this.mNc;
    }

    final float getNcb() {
        return this.mNcb;
    }

    final float[] getRgbD() {
        return this.mRgbD;
    }

    final float getZ() {
        return this.f16mZ;
    }
}
