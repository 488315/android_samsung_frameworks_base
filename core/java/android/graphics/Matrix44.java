package android.graphics;

import java.util.Arrays;

public class Matrix44 {
    final float[] mBackingArray;

    public Matrix44() {
        this.mBackingArray =
                new float[] {
                    1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
                    0.0f, 0.0f, 1.0f
                };
    }

    public Matrix44(Matrix mat) {
        float[] m = new float[9];
        mat.getValues(m);
        this.mBackingArray =
                new float[] {
                    m[0], m[1], 0.0f, m[2], m[3], m[4], 0.0f, m[5], 0.0f, 0.0f, 1.0f, 0.0f, m[6],
                    m[7], 0.0f, m[8]
                };
    }

    public void getValues(float[] dst) {
        if (dst.length == 16) {
            System.arraycopy(this.mBackingArray, 0, dst, 0, this.mBackingArray.length);
            return;
        }
        throw new IllegalArgumentException("Dst array must be of length 16");
    }

    public void setValues(float[] src) {
        if (src.length == 16) {
            System.arraycopy(src, 0, this.mBackingArray, 0, this.mBackingArray.length);
            return;
        }
        throw new IllegalArgumentException("Src array must be of length 16");
    }

    public float get(int row, int col) {
        if (row >= 0 && row < 4 && col >= 0 && col < 4) {
            return this.mBackingArray[(row * 4) + col];
        }
        throw new IllegalArgumentException("invalid row and column values");
    }

    public void set(int row, int col, float val) {
        if (row >= 0 && row < 4 && col >= 0 && col < 4) {
            this.mBackingArray[(row * 4) + col] = val;
            return;
        }
        throw new IllegalArgumentException("invalid row and column values");
    }

    public void reset() {
        for (int i = 0; i < this.mBackingArray.length; i++) {
            this.mBackingArray[i] = i % 4 == i / 4 ? 1.0f : 0.0f;
        }
    }

    public boolean invert() {
        float a00 = this.mBackingArray[0];
        float a01 = this.mBackingArray[1];
        float a02 = this.mBackingArray[2];
        float a03 = this.mBackingArray[3];
        float a10 = this.mBackingArray[4];
        float a11 = this.mBackingArray[5];
        float a12 = this.mBackingArray[6];
        float a13 = this.mBackingArray[7];
        float a20 = this.mBackingArray[8];
        float a21 = this.mBackingArray[9];
        float a22 = this.mBackingArray[10];
        float a23 = this.mBackingArray[11];
        float a30 = this.mBackingArray[12];
        float a31 = this.mBackingArray[13];
        float a32 = this.mBackingArray[14];
        float a33 = this.mBackingArray[15];
        float b00 = (a00 * a11) - (a01 * a10);
        float b01 = (a00 * a12) - (a02 * a10);
        float b02 = (a00 * a13) - (a03 * a10);
        float b03 = (a01 * a12) - (a02 * a11);
        float b04 = (a01 * a13) - (a03 * a11);
        float b05 = (a02 * a13) - (a03 * a12);
        float b06 = (a20 * a31) - (a21 * a30);
        float b07 = (a20 * a32) - (a22 * a30);
        float b08 = (a20 * a33) - (a23 * a30);
        float b09 = (a21 * a32) - (a22 * a31);
        float b10 = (a21 * a33) - (a23 * a31);
        float b11 = (a22 * a33) - (a23 * a32);
        float det =
                (((((b00 * b11) - (b01 * b10)) + (b02 * b09)) + (b03 * b08)) - (b04 * b07))
                        + (b05 * b06);
        if (det == 0.0f) {
            return false;
        }
        float invDet = 1.0f / det;
        this.mBackingArray[0] = (((a11 * b11) - (a12 * b10)) + (a13 * b09)) * invDet;
        this.mBackingArray[1] = ((((-a01) * b11) + (a02 * b10)) - (a03 * b09)) * invDet;
        this.mBackingArray[2] = (((a31 * b05) - (a32 * b04)) + (a33 * b03)) * invDet;
        this.mBackingArray[3] = ((((-a21) * b05) + (a22 * b04)) - (a23 * b03)) * invDet;
        this.mBackingArray[4] = ((((-a10) * b11) + (a12 * b08)) - (a13 * b07)) * invDet;
        this.mBackingArray[5] = (((a00 * b11) - (a02 * b08)) + (a03 * b07)) * invDet;
        this.mBackingArray[6] = ((((-a30) * b05) + (a32 * b02)) - (a33 * b01)) * invDet;
        this.mBackingArray[7] = (((a20 * b05) - (a22 * b02)) + (a23 * b01)) * invDet;
        this.mBackingArray[8] = (((a10 * b10) - (a11 * b08)) + (a13 * b06)) * invDet;
        this.mBackingArray[9] = ((((-a00) * b10) + (a01 * b08)) - (a03 * b06)) * invDet;
        this.mBackingArray[10] = (((a30 * b04) - (a31 * b02)) + (a33 * b00)) * invDet;
        this.mBackingArray[11] = ((((-a20) * b04) + (a21 * b02)) - (a23 * b00)) * invDet;
        this.mBackingArray[12] = ((((-a10) * b09) + (a11 * b07)) - (a12 * b06)) * invDet;
        this.mBackingArray[13] = (((a00 * b09) - (a01 * b07)) + (a02 * b06)) * invDet;
        this.mBackingArray[14] = ((((-a30) * b03) + (a31 * b01)) - (a32 * b00)) * invDet;
        this.mBackingArray[15] = (((a20 * b03) - (a21 * b01)) + (a22 * b00)) * invDet;
        return true;
    }

    public boolean isIdentity() {
        for (int i = 0; i < this.mBackingArray.length; i++) {
            float expected = i % 4 == i / 4 ? 1.0f : 0.0f;
            if (expected != this.mBackingArray[i]) {
                return false;
            }
        }
        return true;
    }

    private static float dot(Matrix44 a, Matrix44 b, int row, int col) {
        return (a.get(row, 0) * b.get(0, col))
                + (a.get(row, 1) * b.get(1, col))
                + (a.get(row, 2) * b.get(2, col))
                + (a.get(row, 3) * b.get(3, col));
    }

    private static float dot(
            float r0, float r1, float r2, float r3, float c0, float c1, float c2, float c3) {
        return (r0 * c0) + (r1 * c1) + (r2 * c2) + (r3 * c3);
    }

    public float[] map(float x, float y, float z, float w) {
        float[] dst = new float[4];
        map(x, y, z, w, dst);
        return dst;
    }

    public void map(float x, float y, float z, float w, float[] dst) {
        if (dst.length != 4) {
            throw new IllegalArgumentException("Dst array must be of length 4");
        }
        dst[0] =
                (this.mBackingArray[0] * x)
                        + (this.mBackingArray[1] * y)
                        + (this.mBackingArray[2] * z)
                        + (this.mBackingArray[3] * w);
        dst[1] =
                (this.mBackingArray[4] * x)
                        + (this.mBackingArray[5] * y)
                        + (this.mBackingArray[6] * z)
                        + (this.mBackingArray[7] * w);
        dst[2] =
                (this.mBackingArray[8] * x)
                        + (this.mBackingArray[9] * y)
                        + (this.mBackingArray[10] * z)
                        + (this.mBackingArray[11] * w);
        dst[3] =
                (this.mBackingArray[12] * x)
                        + (this.mBackingArray[13] * y)
                        + (this.mBackingArray[14] * z)
                        + (this.mBackingArray[15] * w);
    }

    public Matrix44 concat(Matrix44 b) {
        float val00 = dot(this, b, 0, 0);
        float val01 = dot(this, b, 0, 1);
        float val02 = dot(this, b, 0, 2);
        float val03 = dot(this, b, 0, 3);
        float val10 = dot(this, b, 1, 0);
        float val11 = dot(this, b, 1, 1);
        float val12 = dot(this, b, 1, 2);
        float val13 = dot(this, b, 1, 3);
        float val20 = dot(this, b, 2, 0);
        float val21 = dot(this, b, 2, 1);
        float val22 = dot(this, b, 2, 2);
        float val23 = dot(this, b, 2, 3);
        float val30 = dot(this, b, 3, 0);
        float val31 = dot(this, b, 3, 1);
        float val32 = dot(this, b, 3, 2);
        float val33 = dot(this, b, 3, 3);
        this.mBackingArray[0] = val00;
        this.mBackingArray[1] = val01;
        this.mBackingArray[2] = val02;
        this.mBackingArray[3] = val03;
        this.mBackingArray[4] = val10;
        this.mBackingArray[5] = val11;
        this.mBackingArray[6] = val12;
        this.mBackingArray[7] = val13;
        this.mBackingArray[8] = val20;
        this.mBackingArray[9] = val21;
        this.mBackingArray[10] = val22;
        this.mBackingArray[11] = val23;
        this.mBackingArray[12] = val30;
        this.mBackingArray[13] = val31;
        this.mBackingArray[14] = val32;
        this.mBackingArray[15] = val33;
        return this;
    }

    public Matrix44 rotate(float deg, float xComp, float yComp, float zComp) {
        float sum = xComp + yComp + zComp;
        float x = xComp / sum;
        float y = yComp / sum;
        float z = zComp / sum;
        float c = (float) Math.cos((deg * 3.141592653589793d) / 180.0d);
        float s = (float) Math.sin((deg * 3.141592653589793d) / 180.0d);
        float t = 1.0f - c;
        float rotVals00 = (t * x * x) + c;
        float rotVals01 = ((t * x) * y) - (s * z);
        float rotVals02 = (t * x * z) + (s * y);
        float rotVals10 = (t * x * y) + (s * z);
        float rotVals11 = (t * y * y) + c;
        float rotVals12 = ((t * y) * z) - (s * x);
        float rotVals20 = ((t * x) * z) - (s * y);
        float rotVals21 = (t * y * z) + (s * x);
        float rotVals22 = (t * z * z) + c;
        float v00 =
                dot(
                        this.mBackingArray[0],
                        this.mBackingArray[1],
                        this.mBackingArray[2],
                        this.mBackingArray[3],
                        rotVals00,
                        rotVals10,
                        rotVals20,
                        0.0f);
        float v01 =
                dot(
                        this.mBackingArray[0],
                        this.mBackingArray[1],
                        this.mBackingArray[2],
                        this.mBackingArray[3],
                        rotVals01,
                        rotVals11,
                        rotVals21,
                        0.0f);
        float v02 =
                dot(
                        this.mBackingArray[0],
                        this.mBackingArray[1],
                        this.mBackingArray[2],
                        this.mBackingArray[3],
                        rotVals02,
                        rotVals12,
                        rotVals22,
                        0.0f);
        float v03 =
                dot(
                        this.mBackingArray[0],
                        this.mBackingArray[1],
                        this.mBackingArray[2],
                        this.mBackingArray[3],
                        0.0f,
                        0.0f,
                        0.0f,
                        1.0f);
        float v10 =
                dot(
                        this.mBackingArray[4],
                        this.mBackingArray[5],
                        this.mBackingArray[6],
                        this.mBackingArray[7],
                        rotVals00,
                        rotVals10,
                        rotVals20,
                        0.0f);
        float v11 =
                dot(
                        this.mBackingArray[4],
                        this.mBackingArray[5],
                        this.mBackingArray[6],
                        this.mBackingArray[7],
                        rotVals01,
                        rotVals11,
                        rotVals21,
                        0.0f);
        float v12 =
                dot(
                        this.mBackingArray[4],
                        this.mBackingArray[5],
                        this.mBackingArray[6],
                        this.mBackingArray[7],
                        rotVals02,
                        rotVals12,
                        rotVals22,
                        0.0f);
        float v13 =
                dot(
                        this.mBackingArray[4],
                        this.mBackingArray[5],
                        this.mBackingArray[6],
                        this.mBackingArray[7],
                        0.0f,
                        0.0f,
                        0.0f,
                        1.0f);
        float v20 =
                dot(
                        this.mBackingArray[8],
                        this.mBackingArray[9],
                        this.mBackingArray[10],
                        this.mBackingArray[11],
                        rotVals00,
                        rotVals10,
                        rotVals20,
                        0.0f);
        float v21 =
                dot(
                        this.mBackingArray[8],
                        this.mBackingArray[9],
                        this.mBackingArray[10],
                        this.mBackingArray[11],
                        rotVals01,
                        rotVals11,
                        rotVals21,
                        0.0f);
        float v22 =
                dot(
                        this.mBackingArray[8],
                        this.mBackingArray[9],
                        this.mBackingArray[10],
                        this.mBackingArray[11],
                        rotVals02,
                        rotVals12,
                        rotVals22,
                        0.0f);
        float v23 =
                dot(
                        this.mBackingArray[8],
                        this.mBackingArray[9],
                        this.mBackingArray[10],
                        this.mBackingArray[11],
                        0.0f,
                        0.0f,
                        0.0f,
                        1.0f);
        float v30 =
                dot(
                        this.mBackingArray[12],
                        this.mBackingArray[13],
                        this.mBackingArray[14],
                        this.mBackingArray[15],
                        rotVals00,
                        rotVals10,
                        rotVals20,
                        0.0f);
        float v31 =
                dot(
                        this.mBackingArray[12],
                        this.mBackingArray[13],
                        this.mBackingArray[14],
                        this.mBackingArray[15],
                        rotVals01,
                        rotVals11,
                        rotVals21,
                        0.0f);
        float v32 =
                dot(
                        this.mBackingArray[12],
                        this.mBackingArray[13],
                        this.mBackingArray[14],
                        this.mBackingArray[15],
                        rotVals02,
                        rotVals12,
                        rotVals22,
                        0.0f);
        float v33 =
                dot(
                        this.mBackingArray[12],
                        this.mBackingArray[13],
                        this.mBackingArray[14],
                        this.mBackingArray[15],
                        0.0f,
                        0.0f,
                        0.0f,
                        1.0f);
        this.mBackingArray[0] = v00;
        this.mBackingArray[1] = v01;
        this.mBackingArray[2] = v02;
        this.mBackingArray[3] = v03;
        this.mBackingArray[4] = v10;
        this.mBackingArray[5] = v11;
        this.mBackingArray[6] = v12;
        this.mBackingArray[7] = v13;
        this.mBackingArray[8] = v20;
        this.mBackingArray[9] = v21;
        this.mBackingArray[10] = v22;
        this.mBackingArray[11] = v23;
        this.mBackingArray[12] = v30;
        this.mBackingArray[13] = v31;
        this.mBackingArray[14] = v32;
        this.mBackingArray[15] = v33;
        return this;
    }

    public Matrix44 scale(float x, float y, float z) {
        float[] fArr = this.mBackingArray;
        fArr[0] = fArr[0] * x;
        float[] fArr2 = this.mBackingArray;
        fArr2[4] = fArr2[4] * x;
        float[] fArr3 = this.mBackingArray;
        fArr3[8] = fArr3[8] * x;
        float[] fArr4 = this.mBackingArray;
        fArr4[12] = fArr4[12] * x;
        float[] fArr5 = this.mBackingArray;
        fArr5[1] = fArr5[1] * y;
        float[] fArr6 = this.mBackingArray;
        fArr6[5] = fArr6[5] * y;
        float[] fArr7 = this.mBackingArray;
        fArr7[9] = fArr7[9] * y;
        float[] fArr8 = this.mBackingArray;
        fArr8[13] = fArr8[13] * y;
        float[] fArr9 = this.mBackingArray;
        fArr9[2] = fArr9[2] * z;
        float[] fArr10 = this.mBackingArray;
        fArr10[6] = fArr10[6] * z;
        float[] fArr11 = this.mBackingArray;
        fArr11[10] = fArr11[10] * z;
        float[] fArr12 = this.mBackingArray;
        fArr12[14] = fArr12[14] * z;
        return this;
    }

    public Matrix44 translate(float x, float y, float z) {
        float newX =
                (this.mBackingArray[0] * x)
                        + (this.mBackingArray[1] * y)
                        + (this.mBackingArray[2] * z)
                        + this.mBackingArray[3];
        float newY =
                (this.mBackingArray[4] * x)
                        + (this.mBackingArray[5] * y)
                        + (this.mBackingArray[6] * z)
                        + this.mBackingArray[7];
        float newZ =
                (this.mBackingArray[8] * x)
                        + (this.mBackingArray[9] * y)
                        + (this.mBackingArray[10] * z)
                        + this.mBackingArray[11];
        float newW =
                (this.mBackingArray[12] * x)
                        + (this.mBackingArray[13] * y)
                        + (this.mBackingArray[14] * z)
                        + this.mBackingArray[15];
        this.mBackingArray[3] = newX;
        this.mBackingArray[7] = newY;
        this.mBackingArray[11] = newZ;
        this.mBackingArray[15] = newW;
        return this;
    }

    public String toString() {
        return String.format(
                "| %f %f %f %f |\n| %f %f %f %f |\n| %f %f %f %f |\n| %f %f %f %f |\n",
                Float.valueOf(this.mBackingArray[0]),
                Float.valueOf(this.mBackingArray[1]),
                Float.valueOf(this.mBackingArray[2]),
                Float.valueOf(this.mBackingArray[3]),
                Float.valueOf(this.mBackingArray[4]),
                Float.valueOf(this.mBackingArray[5]),
                Float.valueOf(this.mBackingArray[6]),
                Float.valueOf(this.mBackingArray[7]),
                Float.valueOf(this.mBackingArray[8]),
                Float.valueOf(this.mBackingArray[9]),
                Float.valueOf(this.mBackingArray[10]),
                Float.valueOf(this.mBackingArray[11]),
                Float.valueOf(this.mBackingArray[12]),
                Float.valueOf(this.mBackingArray[13]),
                Float.valueOf(this.mBackingArray[14]),
                Float.valueOf(this.mBackingArray[15]));
    }

    public boolean equals(Object obj) {
        if (obj instanceof Matrix44) {
            return Arrays.equals(this.mBackingArray, ((Matrix44) obj).mBackingArray);
        }
        return false;
    }

    public int hashCode() {
        return ((int) this.mBackingArray[0])
                + ((int) this.mBackingArray[1])
                + ((int) this.mBackingArray[2])
                + ((int) this.mBackingArray[3])
                + ((int) this.mBackingArray[4])
                + ((int) this.mBackingArray[5])
                + ((int) this.mBackingArray[6])
                + ((int) this.mBackingArray[7])
                + ((int) this.mBackingArray[8])
                + ((int) this.mBackingArray[9])
                + ((int) this.mBackingArray[10])
                + ((int) this.mBackingArray[11])
                + ((int) this.mBackingArray[12])
                + ((int) this.mBackingArray[13])
                + ((int) this.mBackingArray[14])
                + ((int) this.mBackingArray[15]);
    }
}
