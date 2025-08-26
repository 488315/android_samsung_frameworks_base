package android.graphics;

import java.util.Arrays;

/* loaded from: classes.dex */
public class Matrix44 {
    final float[] mBackingArray;

    private static float dot(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        return (f * f5) + (f2 * f6) + (f3 * f7) + (f4 * f8);
    }

    public Matrix44() {
        this.mBackingArray = new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    }

    public Matrix44(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        this.mBackingArray = new float[]{fArr[0], fArr[1], 0.0f, fArr[2], fArr[3], fArr[4], 0.0f, fArr[5], 0.0f, 0.0f, 1.0f, 0.0f, fArr[6], fArr[7], 0.0f, fArr[8]};
    }

    public void getValues(float[] fArr) {
        if (fArr.length == 16) {
            float[] fArr2 = this.mBackingArray;
            System.arraycopy(fArr2, 0, fArr, 0, fArr2.length);
            return;
        }
        throw new IllegalArgumentException("Dst array must be of length 16");
    }

    public void setValues(float[] fArr) {
        if (fArr.length == 16) {
            float[] fArr2 = this.mBackingArray;
            System.arraycopy(fArr, 0, fArr2, 0, fArr2.length);
            return;
        }
        throw new IllegalArgumentException("Src array must be of length 16");
    }

    public float get(int i, int i2) {
        if (i >= 0 && i < 4 && i2 >= 0 && i2 < 4) {
            return this.mBackingArray[(i * 4) + i2];
        }
        throw new IllegalArgumentException("invalid row and column values");
    }

    public void set(int i, int i2, float f) {
        if (i >= 0 && i < 4 && i2 >= 0 && i2 < 4) {
            this.mBackingArray[(i * 4) + i2] = f;
            return;
        }
        throw new IllegalArgumentException("invalid row and column values");
    }

    public void reset() {
        int i = 0;
        while (true) {
            float[] fArr = this.mBackingArray;
            if (i >= fArr.length) {
                return;
            }
            fArr[i] = i % 4 == i / 4 ? 1.0f : 0.0f;
            i++;
        }
    }

    public boolean invert() {
        float[] fArr = this.mBackingArray;
        float f = fArr[0];
        float f2 = fArr[1];
        float f3 = fArr[2];
        float f4 = fArr[3];
        float f5 = fArr[4];
        float f6 = fArr[5];
        float f7 = fArr[6];
        float f8 = fArr[7];
        float f9 = fArr[8];
        float f10 = fArr[9];
        float f11 = fArr[10];
        float f12 = fArr[11];
        float f13 = fArr[12];
        float f14 = fArr[13];
        float f15 = fArr[14];
        float f16 = fArr[15];
        float f17 = (f * f6) - (f2 * f5);
        float f18 = (f * f7) - (f3 * f5);
        float f19 = (f * f8) - (f4 * f5);
        float f20 = (f2 * f7) - (f3 * f6);
        float f21 = (f2 * f8) - (f4 * f6);
        float f22 = (f3 * f8) - (f4 * f7);
        float f23 = (f9 * f14) - (f10 * f13);
        float f24 = (f9 * f15) - (f11 * f13);
        float f25 = (f9 * f16) - (f12 * f13);
        float f26 = (f10 * f15) - (f11 * f14);
        float f27 = (f10 * f16) - (f12 * f14);
        float f28 = (f11 * f16) - (f12 * f15);
        float f29 = (((((f17 * f28) - (f18 * f27)) + (f19 * f26)) + (f20 * f25)) - (f21 * f24)) + (f22 * f23);
        if (f29 == 0.0f) {
            return false;
        }
        float f30 = 1.0f / f29;
        fArr[0] = (((f6 * f28) - (f7 * f27)) + (f8 * f26)) * f30;
        fArr[1] = ((((-f2) * f28) + (f3 * f27)) - (f4 * f26)) * f30;
        fArr[2] = (((f14 * f22) - (f15 * f21)) + (f16 * f20)) * f30;
        fArr[3] = ((((-f10) * f22) + (f11 * f21)) - (f12 * f20)) * f30;
        float f31 = -f5;
        fArr[4] = (((f31 * f28) + (f7 * f25)) - (f8 * f24)) * f30;
        fArr[5] = (((f28 * f) - (f3 * f25)) + (f4 * f24)) * f30;
        float f32 = -f13;
        fArr[6] = (((f32 * f22) + (f15 * f19)) - (f16 * f18)) * f30;
        fArr[7] = (((f22 * f9) - (f11 * f19)) + (f12 * f18)) * f30;
        fArr[8] = (((f5 * f27) - (f6 * f25)) + (f8 * f23)) * f30;
        fArr[9] = ((((-f) * f27) + (f25 * f2)) - (f4 * f23)) * f30;
        fArr[10] = (((f13 * f21) - (f14 * f19)) + (f16 * f17)) * f30;
        fArr[11] = ((((-f9) * f21) + (f19 * f10)) - (f12 * f17)) * f30;
        fArr[12] = (((f31 * f26) + (f6 * f24)) - (f7 * f23)) * f30;
        fArr[13] = (((f * f26) - (f2 * f24)) + (f3 * f23)) * f30;
        fArr[14] = (((f32 * f20) + (f14 * f18)) - (f15 * f17)) * f30;
        fArr[15] = (((f9 * f20) - (f10 * f18)) + (f11 * f17)) * f30;
        return true;
    }

    public boolean isIdentity() {
        int i = 0;
        while (true) {
            float[] fArr = this.mBackingArray;
            if (i >= fArr.length) {
                return true;
            }
            if ((i % 4 == i / 4 ? 1.0f : 0.0f) != fArr[i]) {
                return false;
            }
            i++;
        }
    }

    private static float dot(Matrix44 matrix44, Matrix44 matrix442, int i, int i2) {
        return (matrix44.get(i, 0) * matrix442.get(0, i2)) + (matrix44.get(i, 1) * matrix442.get(1, i2)) + (matrix44.get(i, 2) * matrix442.get(2, i2)) + (matrix44.get(i, 3) * matrix442.get(3, i2));
    }

    public float[] map(float f, float f2, float f3, float f4) {
        float[] fArr = new float[4];
        map(f, f2, f3, f4, fArr);
        return fArr;
    }

    public void map(float f, float f2, float f3, float f4, float[] fArr) {
        if (fArr.length != 4) {
            throw new IllegalArgumentException("Dst array must be of length 4");
        }
        float[] fArr2 = this.mBackingArray;
        fArr[0] = (fArr2[0] * f) + (fArr2[1] * f2) + (fArr2[2] * f3) + (fArr2[3] * f4);
        fArr[1] = (fArr2[4] * f) + (fArr2[5] * f2) + (fArr2[6] * f3) + (fArr2[7] * f4);
        fArr[2] = (fArr2[8] * f) + (fArr2[9] * f2) + (fArr2[10] * f3) + (fArr2[11] * f4);
        fArr[3] = (f * fArr2[12]) + (f2 * fArr2[13]) + (f3 * fArr2[14]) + (f4 * fArr2[15]);
    }

    public Matrix44 concat(Matrix44 matrix44) {
        float fDot = dot(this, matrix44, 0, 0);
        float fDot2 = dot(this, matrix44, 0, 1);
        float fDot3 = dot(this, matrix44, 0, 2);
        float fDot4 = dot(this, matrix44, 0, 3);
        float fDot5 = dot(this, matrix44, 1, 0);
        float fDot6 = dot(this, matrix44, 1, 1);
        float fDot7 = dot(this, matrix44, 1, 2);
        float fDot8 = dot(this, matrix44, 1, 3);
        float fDot9 = dot(this, matrix44, 2, 0);
        float fDot10 = dot(this, matrix44, 2, 1);
        float fDot11 = dot(this, matrix44, 2, 2);
        float fDot12 = dot(this, matrix44, 2, 3);
        float fDot13 = dot(this, matrix44, 3, 0);
        float fDot14 = dot(this, matrix44, 3, 1);
        float fDot15 = dot(this, matrix44, 3, 2);
        float fDot16 = dot(this, matrix44, 3, 3);
        float[] fArr = this.mBackingArray;
        fArr[0] = fDot;
        fArr[1] = fDot2;
        fArr[2] = fDot3;
        fArr[3] = fDot4;
        fArr[4] = fDot5;
        fArr[5] = fDot6;
        fArr[6] = fDot7;
        fArr[7] = fDot8;
        fArr[8] = fDot9;
        fArr[9] = fDot10;
        fArr[10] = fDot11;
        fArr[11] = fDot12;
        fArr[12] = fDot13;
        fArr[13] = fDot14;
        fArr[14] = fDot15;
        fArr[15] = fDot16;
        return this;
    }

    public Matrix44 rotate(float f, float f2, float f3, float f4) {
        float f5 = f2 + f3 + f4;
        float f6 = f2 / f5;
        float f7 = f3 / f5;
        float f8 = f4 / f5;
        double d = (f * 3.141592653589793d) / 180.0d;
        float fCos = (float) Math.cos(d);
        float fSin = (float) Math.sin(d);
        float f9 = 1.0f - fCos;
        float f10 = f9 * f6;
        float f11 = (f10 * f6) + fCos;
        float f12 = f10 * f7;
        float f13 = fSin * f8;
        float f14 = f12 - f13;
        float f15 = f10 * f8;
        float f16 = fSin * f7;
        float f17 = f15 + f16;
        float f18 = f12 + f13;
        float f19 = f9 * f7;
        float f20 = (f7 * f19) + fCos;
        float f21 = f19 * f8;
        float f22 = fSin * f6;
        float f23 = f21 - f22;
        float f24 = f15 - f16;
        float f25 = f21 + f22;
        float f26 = (f9 * f8 * f8) + fCos;
        float[] fArr = this.mBackingArray;
        float fDot = dot(fArr[0], fArr[1], fArr[2], fArr[3], f11, f18, f24, 0.0f);
        float[] fArr2 = this.mBackingArray;
        float fDot2 = dot(fArr2[0], fArr2[1], fArr2[2], fArr2[3], f14, f20, f25, 0.0f);
        float[] fArr3 = this.mBackingArray;
        float fDot3 = dot(fArr3[0], fArr3[1], fArr3[2], fArr3[3], f17, f23, f26, 0.0f);
        float[] fArr4 = this.mBackingArray;
        float fDot4 = dot(fArr4[0], fArr4[1], fArr4[2], fArr4[3], 0.0f, 0.0f, 0.0f, 1.0f);
        float[] fArr5 = this.mBackingArray;
        float fDot5 = dot(fArr5[4], fArr5[5], fArr5[6], fArr5[7], f11, f18, f24, 0.0f);
        float[] fArr6 = this.mBackingArray;
        float fDot6 = dot(fArr6[4], fArr6[5], fArr6[6], fArr6[7], f14, f20, f25, 0.0f);
        float[] fArr7 = this.mBackingArray;
        float fDot7 = dot(fArr7[4], fArr7[5], fArr7[6], fArr7[7], f17, f23, f26, 0.0f);
        float[] fArr8 = this.mBackingArray;
        float fDot8 = dot(fArr8[4], fArr8[5], fArr8[6], fArr8[7], 0.0f, 0.0f, 0.0f, 1.0f);
        float[] fArr9 = this.mBackingArray;
        float fDot9 = dot(fArr9[8], fArr9[9], fArr9[10], fArr9[11], f11, f18, f24, 0.0f);
        float[] fArr10 = this.mBackingArray;
        float fDot10 = dot(fArr10[8], fArr10[9], fArr10[10], fArr10[11], f14, f20, f25, 0.0f);
        float[] fArr11 = this.mBackingArray;
        float fDot11 = dot(fArr11[8], fArr11[9], fArr11[10], fArr11[11], f17, f23, f26, 0.0f);
        float[] fArr12 = this.mBackingArray;
        float fDot12 = dot(fArr12[8], fArr12[9], fArr12[10], fArr12[11], 0.0f, 0.0f, 0.0f, 1.0f);
        float[] fArr13 = this.mBackingArray;
        float fDot13 = dot(fArr13[12], fArr13[13], fArr13[14], fArr13[15], f11, f18, f24, 0.0f);
        float[] fArr14 = this.mBackingArray;
        float fDot14 = dot(fArr14[12], fArr14[13], fArr14[14], fArr14[15], f14, f20, f25, 0.0f);
        float[] fArr15 = this.mBackingArray;
        float fDot15 = dot(fArr15[12], fArr15[13], fArr15[14], fArr15[15], f17, f23, f26, 0.0f);
        float[] fArr16 = this.mBackingArray;
        float fDot16 = dot(fArr16[12], fArr16[13], fArr16[14], fArr16[15], 0.0f, 0.0f, 0.0f, 1.0f);
        float[] fArr17 = this.mBackingArray;
        fArr17[0] = fDot;
        fArr17[1] = fDot2;
        fArr17[2] = fDot3;
        fArr17[3] = fDot4;
        fArr17[4] = fDot5;
        fArr17[5] = fDot6;
        fArr17[6] = fDot7;
        fArr17[7] = fDot8;
        fArr17[8] = fDot9;
        fArr17[9] = fDot10;
        fArr17[10] = fDot11;
        fArr17[11] = fDot12;
        fArr17[12] = fDot13;
        fArr17[13] = fDot14;
        fArr17[14] = fDot15;
        fArr17[15] = fDot16;
        return this;
    }

    public Matrix44 scale(float f, float f2, float f3) {
        float[] fArr = this.mBackingArray;
        fArr[0] = fArr[0] * f;
        fArr[4] = fArr[4] * f;
        fArr[8] = fArr[8] * f;
        fArr[12] = fArr[12] * f;
        fArr[1] = fArr[1] * f2;
        fArr[5] = fArr[5] * f2;
        fArr[9] = fArr[9] * f2;
        fArr[13] = fArr[13] * f2;
        fArr[2] = fArr[2] * f3;
        fArr[6] = fArr[6] * f3;
        fArr[10] = fArr[10] * f3;
        fArr[14] = fArr[14] * f3;
        return this;
    }

    public Matrix44 translate(float f, float f2, float f3) {
        float[] fArr = this.mBackingArray;
        float f4 = (fArr[0] * f) + (fArr[1] * f2) + (fArr[2] * f3) + fArr[3];
        float f5 = (fArr[4] * f) + (fArr[5] * f2) + (fArr[6] * f3) + fArr[7];
        float f6 = (fArr[8] * f) + (fArr[9] * f2) + (fArr[10] * f3) + fArr[11];
        float f7 = (f * fArr[12]) + (f2 * fArr[13]) + (f3 * fArr[14]) + fArr[15];
        fArr[3] = f4;
        fArr[7] = f5;
        fArr[11] = f6;
        fArr[15] = f7;
        return this;
    }

    public String toString() {
        return String.format("| %f %f %f %f |\n| %f %f %f %f |\n| %f %f %f %f |\n| %f %f %f %f |\n", Float.valueOf(this.mBackingArray[0]), Float.valueOf(this.mBackingArray[1]), Float.valueOf(this.mBackingArray[2]), Float.valueOf(this.mBackingArray[3]), Float.valueOf(this.mBackingArray[4]), Float.valueOf(this.mBackingArray[5]), Float.valueOf(this.mBackingArray[6]), Float.valueOf(this.mBackingArray[7]), Float.valueOf(this.mBackingArray[8]), Float.valueOf(this.mBackingArray[9]), Float.valueOf(this.mBackingArray[10]), Float.valueOf(this.mBackingArray[11]), Float.valueOf(this.mBackingArray[12]), Float.valueOf(this.mBackingArray[13]), Float.valueOf(this.mBackingArray[14]), Float.valueOf(this.mBackingArray[15]));
    }

    public boolean equals(Object obj) {
        if (obj instanceof Matrix44) {
            return Arrays.equals(this.mBackingArray, ((Matrix44) obj).mBackingArray);
        }
        return false;
    }

    public int hashCode() {
        float[] fArr = this.mBackingArray;
        return ((int) fArr[0]) + ((int) fArr[1]) + ((int) fArr[2]) + ((int) fArr[3]) + ((int) fArr[4]) + ((int) fArr[5]) + ((int) fArr[6]) + ((int) fArr[7]) + ((int) fArr[8]) + ((int) fArr[9]) + ((int) fArr[10]) + ((int) fArr[11]) + ((int) fArr[12]) + ((int) fArr[13]) + ((int) fArr[14]) + ((int) fArr[15]);
    }
}
