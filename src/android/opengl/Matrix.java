package android.opengl;

/* loaded from: classes3.dex */
public class Matrix {
    private static final ThreadLocal<float[]> ThreadTmp = new ThreadLocal() { // from class: android.opengl.Matrix.1
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public float[] initialValue() {
            return new float[32];
        }
    };

    private static boolean overlap(float[] fArr, int i, int i2, float[] fArr2, int i3, int i4) {
        int i5;
        int i6;
        if (fArr != fArr2) {
            return false;
        }
        if (i == i3 || (i5 = i2 + i) == (i6 = i4 + i3)) {
            return true;
        }
        if (i < i3 && i3 < i5) {
            return true;
        }
        if (i < i6 && i6 < i5) {
            return true;
        }
        if (i3 >= i || i >= i6) {
            return i3 < i5 && i5 < i6;
        }
        return true;
    }

    @Deprecated
    public Matrix() {
    }

    public static void multiplyMM(float[] fArr, int i, float[] fArr2, int i2, float[] fArr3, int i3) {
        if (fArr == null) {
            throw new IllegalArgumentException("result == null");
        }
        if (fArr2 == null) {
            throw new IllegalArgumentException("lhs == null");
        }
        if (fArr3 == null) {
            throw new IllegalArgumentException("rhs == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("resultOffset < 0");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("lhsOffset < 0");
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("rhsOffset < 0");
        }
        if (fArr.length < i + 16) {
            throw new IllegalArgumentException("result.length < resultOffset + 16");
        }
        if (fArr2.length < i2 + 16) {
            throw new IllegalArgumentException("lhs.length < lhsOffset + 16");
        }
        if (fArr3.length < i3 + 16) {
            throw new IllegalArgumentException("rhs.length < rhsOffset + 16");
        }
        int i4 = 0;
        if (!overlap(fArr, i, 16, fArr2, i2, 16) && !overlap(fArr, i, 16, fArr3, i3, 16)) {
            while (i4 < 4) {
                int i5 = i4 * 4;
                float f = fArr3[i5 + i3];
                float f2 = fArr2[i2] * f;
                float f3 = fArr2[i2 + 1] * f;
                float f4 = fArr2[i2 + 2] * f;
                float f5 = fArr2[i2 + 3] * f;
                for (int i6 = 1; i6 < 4; i6++) {
                    float f6 = fArr3[i5 + i6 + i3];
                    int i7 = i6 * 4;
                    f2 += fArr2[i7 + i2] * f6;
                    f3 += fArr2[i7 + 1 + i2] * f6;
                    f4 += fArr2[i7 + 2 + i2] * f6;
                    f5 += fArr2[i7 + 3 + i2] * f6;
                }
                fArr[i5 + i] = f2;
                fArr[i5 + 1 + i] = f3;
                fArr[i5 + 2 + i] = f4;
                fArr[i5 + 3 + i] = f5;
                i4++;
            }
            return;
        }
        float[] fArr4 = ThreadTmp.get();
        for (int i8 = 0; i8 < 4; i8++) {
            int i9 = i8 * 4;
            float f7 = fArr3[i9 + i3];
            float f8 = fArr2[i2] * f7;
            float f9 = fArr2[i2 + 1] * f7;
            float f10 = fArr2[i2 + 2] * f7;
            float f11 = fArr2[i2 + 3] * f7;
            for (int i10 = 1; i10 < 4; i10++) {
                float f12 = fArr3[i9 + i10 + i3];
                int i11 = i10 * 4;
                f8 += fArr2[i11 + i2] * f12;
                f9 += fArr2[i11 + 1 + i2] * f12;
                f10 += fArr2[i11 + 2 + i2] * f12;
                f11 += fArr2[i11 + 3 + i2] * f12;
            }
            fArr4[i9] = f8;
            fArr4[i9 + 1] = f9;
            fArr4[i9 + 2] = f10;
            fArr4[i9 + 3] = f11;
        }
        while (i4 < 16) {
            fArr[i4 + i] = fArr4[i4];
            i4++;
        }
    }

    public static void multiplyMV(float[] fArr, int i, float[] fArr2, int i2, float[] fArr3, int i3) {
        if (fArr == null) {
            throw new IllegalArgumentException("resultVec == null");
        }
        if (fArr2 == null) {
            throw new IllegalArgumentException("lhsMat == null");
        }
        if (fArr3 == null) {
            throw new IllegalArgumentException("rhsVec == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("resultVecOffset < 0");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException("lhsMatOffset < 0");
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("rhsVecOffset < 0");
        }
        if (fArr.length < i + 4) {
            throw new IllegalArgumentException("resultVec.length < resultVecOffset + 4");
        }
        if (fArr2.length < i2 + 16) {
            throw new IllegalArgumentException("lhsMat.length < lhsMatOffset + 16");
        }
        if (fArr3.length < i3 + 4) {
            throw new IllegalArgumentException("rhsVec.length < rhsVecOffset + 4");
        }
        float f = fArr2[i2];
        float f2 = fArr3[i3];
        float f3 = fArr2[i2 + 4];
        float f4 = fArr3[i3 + 1];
        float f5 = (f * f2) + (f3 * f4);
        float f6 = fArr2[i2 + 8];
        float f7 = fArr3[i3 + 2];
        float f8 = f5 + (f6 * f7);
        float f9 = fArr2[i2 + 12];
        float f10 = fArr3[i3 + 3];
        float f11 = f8 + (f9 * f10);
        float f12 = (fArr2[i2 + 1] * f2) + (fArr2[i2 + 5] * f4) + (fArr2[i2 + 9] * f7) + (fArr2[i2 + 13] * f10);
        float f13 = (fArr2[i2 + 2] * f2) + (fArr2[i2 + 6] * f4) + (fArr2[i2 + 10] * f7) + (fArr2[i2 + 14] * f10);
        float f14 = (fArr2[i2 + 3] * f2) + (fArr2[i2 + 7] * f4) + (fArr2[i2 + 11] * f7) + (fArr2[i2 + 15] * f10);
        fArr[i] = f11;
        fArr[i + 1] = f12;
        fArr[i + 2] = f13;
        fArr[i + 3] = f14;
    }

    public static void transposeM(float[] fArr, int i, float[] fArr2, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            int i4 = (i3 * 4) + i2;
            fArr[i3 + i] = fArr2[i4];
            fArr[i3 + 4 + i] = fArr2[i4 + 1];
            fArr[i3 + 8 + i] = fArr2[i4 + 2];
            fArr[i3 + 12 + i] = fArr2[i4 + 3];
        }
    }

    public static boolean invertM(float[] fArr, int i, float[] fArr2, int i2) {
        float f = fArr2[i2];
        float f2 = fArr2[i2 + 1];
        float f3 = fArr2[i2 + 2];
        float f4 = fArr2[i2 + 3];
        float f5 = fArr2[i2 + 4];
        float f6 = fArr2[i2 + 5];
        float f7 = fArr2[i2 + 6];
        float f8 = fArr2[i2 + 7];
        float f9 = fArr2[i2 + 8];
        float f10 = fArr2[i2 + 9];
        float f11 = fArr2[i2 + 10];
        float f12 = fArr2[i2 + 11];
        float f13 = fArr2[i2 + 12];
        float f14 = fArr2[i2 + 13];
        float f15 = fArr2[i2 + 14];
        float f16 = fArr2[i2 + 15];
        float f17 = f11 * f16;
        float f18 = f15 * f12;
        float f19 = f7 * f16;
        float f20 = f15 * f8;
        float f21 = f7 * f12;
        float f22 = f11 * f8;
        float f23 = f3 * f16;
        float f24 = f15 * f4;
        float f25 = f3 * f12;
        float f26 = f11 * f4;
        float f27 = f3 * f8;
        float f28 = f7 * f4;
        float f29 = (((f17 * f6) + (f20 * f10)) + (f21 * f14)) - (((f18 * f6) + (f19 * f10)) + (f22 * f14));
        float f30 = (((f18 * f2) + (f23 * f10)) + (f26 * f14)) - (((f17 * f2) + (f24 * f10)) + (f25 * f14));
        float f31 = (((f19 * f2) + (f24 * f6)) + (f27 * f14)) - (((f20 * f2) + (f23 * f6)) + (f28 * f14));
        float f32 = (((f22 * f2) + (f25 * f6)) + (f28 * f10)) - (((f21 * f2) + (f26 * f6)) + (f27 * f10));
        float f33 = (((f18 * f5) + (f19 * f9)) + (f22 * f13)) - (((f17 * f5) + (f20 * f9)) + (f21 * f13));
        float f34 = (((f17 * f) + (f24 * f9)) + (f25 * f13)) - (((f18 * f) + (f23 * f9)) + (f26 * f13));
        float f35 = (((f20 * f) + (f23 * f5)) + (f28 * f13)) - (((f19 * f) + (f24 * f5)) + (f27 * f13));
        float f36 = (((f21 * f) + (f26 * f5)) + (f27 * f9)) - (((f22 * f) + (f25 * f5)) + (f28 * f9));
        float f37 = f9 * f14;
        float f38 = f13 * f10;
        float f39 = f5 * f14;
        float f40 = f13 * f6;
        float f41 = f5 * f10;
        float f42 = f9 * f6;
        float f43 = f14 * f;
        float f44 = f13 * f2;
        float f45 = f10 * f;
        float f46 = f9 * f2;
        float f47 = f6 * f;
        float f48 = f2 * f5;
        float f49 = (((f37 * f8) + (f40 * f12)) + (f41 * f16)) - (((f38 * f8) + (f39 * f12)) + (f42 * f16));
        float f50 = (((f38 * f4) + (f43 * f12)) + (f46 * f16)) - (((f37 * f4) + (f44 * f12)) + (f45 * f16));
        float f51 = (((f39 * f4) + (f44 * f8)) + (f47 * f16)) - (((f40 * f4) + (f43 * f8)) + (f16 * f48));
        float f52 = (((f42 * f4) + (f45 * f8)) + (f48 * f12)) - (((f4 * f41) + (f8 * f46)) + (f12 * f47));
        float f53 = (((f39 * f11) + (f42 * f15)) + (f38 * f7)) - (((f41 * f15) + (f37 * f7)) + (f40 * f11));
        float f54 = (((f45 * f15) + (f37 * f3)) + (f44 * f11)) - (((f43 * f11) + (f46 * f15)) + (f38 * f3));
        float f55 = (((f43 * f7) + (f48 * f15)) + (f40 * f3)) - (((f15 * f47) + (f39 * f3)) + (f44 * f7));
        float f56 = (((f47 * f11) + (f41 * f3)) + (f46 * f7)) - (((f45 * f7) + (f48 * f11)) + (f42 * f3));
        float f57 = (f * f29) + (f5 * f30) + (f9 * f31) + (f13 * f32);
        if (f57 == 0.0f) {
            return false;
        }
        float f58 = 1.0f / f57;
        fArr[i] = f29 * f58;
        fArr[i + 1] = f30 * f58;
        fArr[i + 2] = f31 * f58;
        fArr[i + 3] = f32 * f58;
        fArr[i + 4] = f33 * f58;
        fArr[i + 5] = f34 * f58;
        fArr[i + 6] = f35 * f58;
        fArr[i + 7] = f36 * f58;
        fArr[i + 8] = f49 * f58;
        fArr[i + 9] = f50 * f58;
        fArr[i + 10] = f51 * f58;
        fArr[i + 11] = f52 * f58;
        fArr[i + 12] = f53 * f58;
        fArr[i + 13] = f54 * f58;
        fArr[i + 14] = f55 * f58;
        fArr[i + 15] = f56 * f58;
        return true;
    }

    public static void orthoM(float[] fArr, int i, float f, float f2, float f3, float f4, float f5, float f6) {
        if (f == f2) {
            throw new IllegalArgumentException("left == right");
        }
        if (f3 == f4) {
            throw new IllegalArgumentException("bottom == top");
        }
        if (f5 == f6) {
            throw new IllegalArgumentException("near == far");
        }
        float f7 = 1.0f / (f2 - f);
        float f8 = 1.0f / (f4 - f3);
        float f9 = 1.0f / (f6 - f5);
        float f10 = (-(f2 + f)) * f7;
        float f11 = (-(f4 + f3)) * f8;
        fArr[i] = f7 * 2.0f;
        fArr[i + 5] = 2.0f * f8;
        fArr[i + 10] = (-2.0f) * f9;
        fArr[i + 12] = f10;
        fArr[i + 13] = f11;
        fArr[i + 14] = (-(f6 + f5)) * f9;
        fArr[i + 15] = 1.0f;
        fArr[i + 1] = 0.0f;
        fArr[i + 2] = 0.0f;
        fArr[i + 3] = 0.0f;
        fArr[i + 4] = 0.0f;
        fArr[i + 6] = 0.0f;
        fArr[i + 7] = 0.0f;
        fArr[i + 8] = 0.0f;
        fArr[i + 9] = 0.0f;
        fArr[i + 11] = 0.0f;
    }

    public static void frustumM(float[] fArr, int i, float f, float f2, float f3, float f4, float f5, float f6) {
        if (f == f2) {
            throw new IllegalArgumentException("left == right");
        }
        if (f4 == f3) {
            throw new IllegalArgumentException("top == bottom");
        }
        if (f5 == f6) {
            throw new IllegalArgumentException("near == far");
        }
        if (f5 <= 0.0f) {
            throw new IllegalArgumentException("near <= 0.0f");
        }
        if (f6 <= 0.0f) {
            throw new IllegalArgumentException("far <= 0.0f");
        }
        float f7 = 1.0f / (f2 - f);
        float f8 = 1.0f / (f4 - f3);
        float f9 = 1.0f / (f5 - f6);
        float f10 = (f2 + f) * f7;
        float f11 = (f4 + f3) * f8;
        fArr[i] = f5 * f7 * 2.0f;
        fArr[i + 5] = f5 * f8 * 2.0f;
        fArr[i + 8] = f10;
        fArr[i + 9] = f11;
        fArr[i + 10] = (f6 + f5) * f9;
        fArr[i + 14] = f6 * f5 * f9 * 2.0f;
        fArr[i + 11] = -1.0f;
        fArr[i + 1] = 0.0f;
        fArr[i + 2] = 0.0f;
        fArr[i + 3] = 0.0f;
        fArr[i + 4] = 0.0f;
        fArr[i + 6] = 0.0f;
        fArr[i + 7] = 0.0f;
        fArr[i + 12] = 0.0f;
        fArr[i + 13] = 0.0f;
        fArr[i + 15] = 0.0f;
    }

    public static void perspectiveM(float[] fArr, int i, float f, float f2, float f3, float f4) {
        float fTan = 1.0f / ((float) Math.tan(f * 0.008726646259971648d));
        float f5 = 1.0f / (f3 - f4);
        fArr[i] = fTan / f2;
        fArr[i + 1] = 0.0f;
        fArr[i + 2] = 0.0f;
        fArr[i + 3] = 0.0f;
        fArr[i + 4] = 0.0f;
        fArr[i + 5] = fTan;
        fArr[i + 6] = 0.0f;
        fArr[i + 7] = 0.0f;
        fArr[i + 8] = 0.0f;
        fArr[i + 9] = 0.0f;
        fArr[i + 10] = (f4 + f3) * f5;
        fArr[i + 11] = -1.0f;
        fArr[i + 12] = 0.0f;
        fArr[i + 13] = 0.0f;
        fArr[i + 14] = f4 * 2.0f * f3 * f5;
        fArr[i + 15] = 0.0f;
    }

    public static float length(float f, float f2, float f3) {
        return (float) Math.sqrt((f * f) + (f2 * f2) + (f3 * f3));
    }

    public static void setIdentityM(float[] fArr, int i) {
        for (int i2 = 0; i2 < 16; i2++) {
            fArr[i + i2] = 0.0f;
        }
        for (int i3 = 0; i3 < 16; i3 += 5) {
            fArr[i + i3] = 1.0f;
        }
    }

    public static void scaleM(float[] fArr, int i, float[] fArr2, int i2, float f, float f2, float f3) {
        for (int i3 = 0; i3 < 4; i3++) {
            int i4 = i + i3;
            int i5 = i2 + i3;
            fArr[i4] = fArr2[i5] * f;
            fArr[i4 + 4] = fArr2[i5 + 4] * f2;
            fArr[i4 + 8] = fArr2[i5 + 8] * f3;
            fArr[i4 + 12] = fArr2[i5 + 12];
        }
    }

    public static void scaleM(float[] fArr, int i, float f, float f2, float f3) {
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = i + i2;
            fArr[i3] = fArr[i3] * f;
            int i4 = i3 + 4;
            fArr[i4] = fArr[i4] * f2;
            int i5 = i3 + 8;
            fArr[i5] = fArr[i5] * f3;
        }
    }

    public static void translateM(float[] fArr, int i, float[] fArr2, int i2, float f, float f2, float f3) {
        for (int i3 = 0; i3 < 12; i3++) {
            fArr[i + i3] = fArr2[i2 + i3];
        }
        for (int i4 = 0; i4 < 4; i4++) {
            int i5 = i2 + i4;
            fArr[i + i4 + 12] = (fArr2[i5] * f) + (fArr2[i5 + 4] * f2) + (fArr2[i5 + 8] * f3) + fArr2[i5 + 12];
        }
    }

    public static void translateM(float[] fArr, int i, float f, float f2, float f3) {
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = i + i2;
            int i4 = i3 + 12;
            fArr[i4] = fArr[i4] + (fArr[i3] * f) + (fArr[i3 + 4] * f2) + (fArr[i3 + 8] * f3);
        }
    }

    public static void rotateM(float[] fArr, int i, float[] fArr2, int i2, float f, float f2, float f3, float f4) {
        float[] fArr3 = ThreadTmp.get();
        setRotateM(fArr3, 16, f, f2, f3, f4);
        multiplyMM(fArr, i, fArr2, i2, fArr3, 16);
    }

    public static void rotateM(float[] fArr, int i, float f, float f2, float f3, float f4) {
        rotateM(fArr, i, fArr, i, f, f2, f3, f4);
    }

    public static void setRotateM(float[] fArr, int i, float f, float f2, float f3, float f4) {
        fArr[i + 3] = 0.0f;
        fArr[i + 7] = 0.0f;
        fArr[i + 11] = 0.0f;
        fArr[i + 12] = 0.0f;
        fArr[i + 13] = 0.0f;
        fArr[i + 14] = 0.0f;
        fArr[i + 15] = 1.0f;
        double d = f * 0.017453292f;
        float fSin = (float) Math.sin(d);
        float fCos = (float) Math.cos(d);
        if (1.0f == f2 && 0.0f == f3 && 0.0f == f4) {
            fArr[i + 5] = fCos;
            fArr[i + 10] = fCos;
            fArr[i + 6] = fSin;
            fArr[i + 9] = -fSin;
            fArr[i + 1] = 0.0f;
            fArr[i + 2] = 0.0f;
            fArr[i + 4] = 0.0f;
            fArr[i + 8] = 0.0f;
            fArr[i] = 1.0f;
            return;
        }
        if (0.0f == f2 && 1.0f == f3 && 0.0f == f4) {
            fArr[i] = fCos;
            fArr[i + 10] = fCos;
            fArr[i + 8] = fSin;
            fArr[i + 2] = -fSin;
            fArr[i + 1] = 0.0f;
            fArr[i + 4] = 0.0f;
            fArr[i + 6] = 0.0f;
            fArr[i + 9] = 0.0f;
            fArr[i + 5] = 1.0f;
            return;
        }
        if (0.0f == f2 && 0.0f == f3 && 1.0f == f4) {
            fArr[i] = fCos;
            fArr[i + 5] = fCos;
            fArr[i + 1] = fSin;
            fArr[i + 4] = -fSin;
            fArr[i + 2] = 0.0f;
            fArr[i + 6] = 0.0f;
            fArr[i + 8] = 0.0f;
            fArr[i + 9] = 0.0f;
            fArr[i + 10] = 1.0f;
            return;
        }
        float length = length(f2, f3, f4);
        if (1.0f != length) {
            float f5 = 1.0f / length;
            f2 *= f5;
            f3 *= f5;
            f4 *= f5;
        }
        float f6 = 1.0f - fCos;
        float f7 = f2 * fSin;
        float f8 = f3 * fSin;
        float f9 = fSin * f4;
        fArr[i] = (f2 * f2 * f6) + fCos;
        float f10 = f2 * f3 * f6;
        fArr[i + 4] = f10 - f9;
        float f11 = f4 * f2 * f6;
        fArr[i + 8] = f11 + f8;
        fArr[i + 1] = f10 + f9;
        fArr[i + 5] = (f3 * f3 * f6) + fCos;
        float f12 = f3 * f4 * f6;
        fArr[i + 9] = f12 - f7;
        fArr[i + 2] = f11 - f8;
        fArr[i + 6] = f12 + f7;
        fArr[i + 10] = (f4 * f4 * f6) + fCos;
    }

    @Deprecated
    public static void setRotateEulerM(float[] fArr, int i, float f, float f2, float f3) {
        double d = f * 0.017453292f;
        float fCos = (float) Math.cos(d);
        float fSin = (float) Math.sin(d);
        double d2 = f2 * 0.017453292f;
        float fCos2 = (float) Math.cos(d2);
        float fSin2 = (float) Math.sin(d2);
        double d3 = f3 * 0.017453292f;
        float fCos3 = (float) Math.cos(d3);
        float fSin3 = (float) Math.sin(d3);
        float f4 = fCos * fSin2;
        float f5 = fSin * fSin2;
        fArr[i] = fCos2 * fCos3;
        fArr[i + 1] = (-fCos2) * fSin3;
        fArr[i + 2] = fSin2;
        fArr[i + 3] = 0.0f;
        fArr[i + 4] = (f4 * fCos3) + (fCos * fSin3);
        fArr[i + 5] = ((-f4) * fSin3) + (fCos * fCos3);
        fArr[i + 6] = (-fSin) * fCos2;
        fArr[i + 7] = 0.0f;
        fArr[i + 8] = ((-f5) * fCos3) + (fSin * fSin3);
        fArr[i + 9] = (f5 * fSin3) + (fSin * fCos3);
        fArr[i + 10] = fCos * fCos2;
        fArr[i + 11] = 0.0f;
        fArr[i + 12] = 0.0f;
        fArr[i + 13] = 0.0f;
        fArr[i + 14] = 0.0f;
        fArr[i + 15] = 1.0f;
    }

    public static void setRotateEulerM2(float[] fArr, int i, float f, float f2, float f3) {
        if (fArr == null) {
            throw new IllegalArgumentException("rm == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("rmOffset < 0");
        }
        if (fArr.length < i + 16) {
            throw new IllegalArgumentException("rm.length < rmOffset + 16");
        }
        double d = f * 0.017453292f;
        float fCos = (float) Math.cos(d);
        float fSin = (float) Math.sin(d);
        double d2 = f2 * 0.017453292f;
        float fCos2 = (float) Math.cos(d2);
        float fSin2 = (float) Math.sin(d2);
        double d3 = f3 * 0.017453292f;
        float fCos3 = (float) Math.cos(d3);
        float fSin3 = (float) Math.sin(d3);
        float f4 = fCos * fSin2;
        float f5 = fSin * fSin2;
        fArr[i] = fCos2 * fCos3;
        fArr[i + 1] = (-fCos2) * fSin3;
        fArr[i + 2] = fSin2;
        fArr[i + 3] = 0.0f;
        fArr[i + 4] = (f5 * fCos3) + (fCos * fSin3);
        fArr[i + 5] = ((-f5) * fSin3) + (fCos * fCos3);
        fArr[i + 6] = (-fSin) * fCos2;
        fArr[i + 7] = 0.0f;
        fArr[i + 8] = ((-f4) * fCos3) + (fSin * fSin3);
        fArr[i + 9] = (f4 * fSin3) + (fSin * fCos3);
        fArr[i + 10] = fCos * fCos2;
        fArr[i + 11] = 0.0f;
        fArr[i + 12] = 0.0f;
        fArr[i + 13] = 0.0f;
        fArr[i + 14] = 0.0f;
        fArr[i + 15] = 1.0f;
    }

    public static void setLookAtM(float[] fArr, int i, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        float f10 = f4 - f;
        float f11 = f5 - f2;
        float f12 = f6 - f3;
        float length = 1.0f / length(f10, f11, f12);
        float f13 = f10 * length;
        float f14 = f11 * length;
        float f15 = f12 * length;
        float f16 = (f14 * f9) - (f15 * f8);
        float f17 = (f15 * f7) - (f9 * f13);
        float f18 = (f8 * f13) - (f7 * f14);
        float length2 = 1.0f / length(f16, f17, f18);
        float f19 = f16 * length2;
        float f20 = f17 * length2;
        float f21 = f18 * length2;
        fArr[i] = f19;
        fArr[i + 1] = (f20 * f15) - (f21 * f14);
        fArr[i + 2] = -f13;
        fArr[i + 3] = 0.0f;
        fArr[i + 4] = f20;
        fArr[i + 5] = (f21 * f13) - (f19 * f15);
        fArr[i + 6] = -f14;
        fArr[i + 7] = 0.0f;
        fArr[i + 8] = f21;
        fArr[i + 9] = (f19 * f14) - (f20 * f13);
        fArr[i + 10] = -f15;
        fArr[i + 11] = 0.0f;
        fArr[i + 12] = 0.0f;
        fArr[i + 13] = 0.0f;
        fArr[i + 14] = 0.0f;
        fArr[i + 15] = 1.0f;
        translateM(fArr, i, -f, -f2, -f3);
    }
}
