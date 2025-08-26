package com.samsung.vekit.Common.Object;

import android.hardware.scontext.SContextConstants;
import android.util.Log;
import com.samsung.vekit.Common.Type.AxisType;
import java.lang.reflect.Array;

/* loaded from: classes6.dex */
public class Matrix4 {
    private final String TAG = "Matrix4";
    private double[][] matrix = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 4, 4);

    public Matrix4() {
        identity();
    }

    public Matrix4(Matrix4 matrix4) {
        setMatrix(matrix4);
    }

    public void setMatrix(Matrix4 matrix4) {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(matrix4.matrix[i], 0, this.matrix[i], 0, 4);
        }
    }

    public Matrix4(double[][] dArr) {
        set(dArr);
    }

    public Matrix4(float[] fArr) {
        set(fArr);
    }

    public void identity() {
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                if (i == i2) {
                    this.matrix[i][i2] = 1.0d;
                } else {
                    this.matrix[i][i2] = 0.0d;
                }
            }
        }
    }

    public void set(float[] fArr) {
        for (int i = 0; i < 16; i++) {
            this.matrix[i % 4][i / 4] = fArr[i];
        }
    }

    public void set(double[][] dArr) {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(dArr[i], 0, this.matrix[i], 0, 4);
        }
    }

    public void set(int i, float f) {
        this.matrix[i % 4][i / 4] = f;
    }

    public float get(int i, int i2) {
        return (float) this.matrix[i2][i];
    }

    public void set(int i, int i2, float f) {
        this.matrix[i2][i] = f;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                sb.append(this.matrix[i2][i]);
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public Vector4<Float> getRow(int i) {
        Float fValueOf = Float.valueOf(0.0f);
        Vector4<Float> vector4 = new Vector4<>(fValueOf, fValueOf, fValueOf, fValueOf);
        vector4.setX(Float.valueOf((float) this.matrix[0][i]));
        vector4.setY(Float.valueOf((float) this.matrix[1][i]));
        vector4.setZ(Float.valueOf((float) this.matrix[2][i]));
        vector4.setW(Float.valueOf((float) this.matrix[3][i]));
        return vector4;
    }

    public Vector4<Float> getColumn(int i) {
        Float fValueOf = Float.valueOf(0.0f);
        Vector4<Float> vector4 = new Vector4<>(fValueOf, fValueOf, fValueOf, fValueOf);
        vector4.setX(Float.valueOf((float) this.matrix[i][0]));
        vector4.setY(Float.valueOf((float) this.matrix[i][1]));
        vector4.setZ(Float.valueOf((float) this.matrix[i][2]));
        vector4.setW(Float.valueOf((float) this.matrix[i][3]));
        return vector4;
    }

    public float[] toArray() {
        float[] fArr = new float[16];
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            int i3 = 0;
            while (i3 < 4) {
                fArr[i] = (float) this.matrix[i3][i2];
                i3++;
                i++;
            }
        }
        return fArr;
    }

    public Matrix4 multiply(Matrix4 matrix4) {
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 4, 4);
        double[] dArr2 = dArr[0];
        double[][] dArr3 = matrix4.matrix;
        double[] dArr4 = dArr3[0];
        double d = dArr4[0];
        double[][] dArr5 = this.matrix;
        double[] dArr6 = dArr5[0];
        double d2 = d * dArr6[0];
        double[] dArr7 = dArr3[1];
        double d3 = dArr7[0];
        double d4 = dArr6[1];
        double d5 = d2 + (d3 * d4);
        double[] dArr8 = dArr3[2];
        double d6 = dArr8[0];
        double d7 = dArr6[2];
        double d8 = d5 + (d6 * d7);
        double[] dArr9 = dArr3[3];
        double d9 = dArr9[0];
        double d10 = dArr6[3];
        dArr2[0] = d8 + (d9 * d10);
        double[] dArr10 = dArr[1];
        double d11 = dArr4[0];
        double[] dArr11 = dArr5[1];
        double d12 = (d11 * dArr11[0]) + (dArr7[0] * dArr11[1]);
        double d13 = dArr8[0];
        double d14 = dArr11[2];
        double d15 = d12 + (d13 * d14);
        double d16 = dArr9[0];
        double d17 = dArr11[3];
        dArr10[0] = d15 + (d16 * d17);
        double[] dArr12 = dArr[2];
        double d18 = dArr4[0];
        double[] dArr13 = dArr5[2];
        double d19 = (d18 * dArr13[0]) + (dArr7[0] * dArr13[1]);
        double d20 = dArr8[0];
        double d21 = dArr13[2];
        double d22 = d19 + (d20 * d21);
        double d23 = dArr9[0];
        double d24 = dArr13[3];
        dArr12[0] = d22 + (d23 * d24);
        double[] dArr14 = dArr[3];
        double d25 = dArr4[0];
        double[] dArr15 = dArr5[3];
        double d26 = (d25 * dArr15[0]) + (dArr7[0] * dArr15[1]);
        double d27 = dArr8[0];
        double d28 = dArr15[2];
        double d29 = d26 + (d27 * d28);
        double d30 = dArr9[0];
        double d31 = dArr15[3];
        dArr14[0] = d29 + (d30 * d31);
        double d32 = dArr4[1];
        double d33 = dArr6[0];
        dArr2[1] = (d32 * d33) + (dArr7[1] * d4) + (dArr8[1] * d7) + (dArr9[1] * d10);
        double d34 = dArr4[1];
        double d35 = dArr11[0];
        dArr10[1] = (d34 * d35) + (dArr7[1] * dArr11[1]) + (dArr8[1] * d14) + (dArr9[1] * d17);
        double d36 = dArr4[1];
        double d37 = dArr13[0];
        dArr12[1] = (d36 * d37) + (dArr7[1] * dArr13[1]) + (dArr8[1] * d21) + (dArr9[1] * d24);
        double d38 = dArr4[1];
        double d39 = dArr15[0];
        dArr14[1] = (d38 * d39) + (dArr7[1] * dArr15[1]) + (dArr8[1] * d28) + (dArr9[1] * d31);
        double d40 = dArr4[2] * d33;
        double d41 = dArr7[2];
        double d42 = dArr6[1];
        dArr2[2] = d40 + (d41 * d42) + (dArr8[2] * d7) + (dArr9[2] * d10);
        double d43 = dArr4[2] * d35;
        double d44 = dArr7[2];
        double d45 = dArr11[1];
        dArr10[2] = d43 + (d44 * d45) + (dArr8[2] * dArr11[2]) + (dArr9[2] * d17);
        double d46 = dArr4[2] * d37;
        double d47 = dArr7[2];
        double d48 = dArr13[1];
        dArr12[2] = d46 + (d47 * d48) + (dArr8[2] * dArr13[2]) + (dArr9[2] * d24);
        double d49 = dArr4[2] * d39;
        double d50 = dArr7[2];
        double d51 = dArr15[1];
        dArr14[2] = d49 + (d50 * d51) + (dArr8[2] * dArr15[2]) + (dArr9[2] * d31);
        dArr2[3] = (dArr4[3] * d33) + (dArr7[3] * d42) + (dArr8[3] * dArr6[2]) + (dArr9[3] * d10);
        dArr10[3] = (dArr4[3] * d35) + (dArr7[3] * d45) + (dArr8[3] * dArr11[2]) + (dArr9[3] * dArr11[3]);
        dArr12[3] = (dArr4[3] * d37) + (dArr7[3] * d48) + (dArr8[3] * dArr13[2]) + (dArr9[3] * dArr13[3]);
        dArr14[3] = (dArr4[3] * d39) + (dArr7[3] * d51) + (dArr8[3] * dArr15[2]) + (dArr9[3] * dArr15[3]);
        return new Matrix4(dArr);
    }

    public Matrix4 divide(double d) {
        if (d == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            Log.e("Matrix4", "Non zero divider required");
            return new Matrix4();
        }
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 4, 4);
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                dArr[i][i2] = this.matrix[i][i2] / d;
            }
        }
        return new Matrix4(dArr);
    }

    public Matrix4 translate(float f, float f2, float f3) {
        Matrix4 matrix4 = new Matrix4();
        matrix4.set(3, 0, f);
        matrix4.set(3, 1, f2);
        matrix4.set(3, 2, f3);
        setMatrix(matrix4.multiply(this));
        return this;
    }

    /* renamed from: com.samsung.vekit.Common.Object.Matrix4$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$AxisType;

        static {
            int[] iArr = new int[AxisType.values().length];
            $SwitchMap$com$samsung$vekit$Common$Type$AxisType = iArr;
            try {
                iArr[AxisType.X.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AxisType[AxisType.Y.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$vekit$Common$Type$AxisType[AxisType.Z.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public Matrix4 rotate(AxisType axisType, float f) {
        float f2;
        int i = AnonymousClass1.$SwitchMap$com$samsung$vekit$Common$Type$AxisType[axisType.ordinal()];
        float f3 = 1.0f;
        float f4 = 0.0f;
        if (i != 1) {
            if (i == 2) {
                f2 = 0.0f;
                f4 = 1.0f;
            } else if (i != 3) {
                f2 = 0.0f;
            } else {
                f2 = 1.0f;
                f3 = 0.0f;
            }
            f3 = f2;
        } else {
            f2 = 0.0f;
        }
        setMatrix(new Quaternion(new Vector3(Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f2)), f).getMatrix().multiply(this));
        return this;
    }

    public Matrix4 rotate(float f, float f2, float f3) {
        Quaternion quaternion = new Quaternion();
        quaternion.setRotation(f, f2, f3);
        setMatrix(quaternion.getMatrix().multiply(this));
        return this;
    }

    public Matrix4 rotate(Quaternion quaternion) {
        setMatrix(quaternion.getMatrix().multiply(this));
        return this;
    }

    public Matrix4 scale(float f, float f2, float f3) {
        Matrix4 matrix4 = new Matrix4();
        matrix4.set(0, 0, f);
        matrix4.set(1, 1, f2);
        matrix4.set(2, 2, f3);
        setMatrix(matrix4.multiply(this));
        return this;
    }

    public Quaternion getQuaternion() {
        Quaternion quaternion = new Quaternion();
        double[][] dArr = this.matrix;
        double d = dArr[0][0];
        double d2 = dArr[1][1];
        double d3 = dArr[2][2];
        if (((float) (d + d2 + d3)) > 0.0f) {
            float fSqrt = (float) Math.sqrt(r1 + 1.0f);
            quaternion.w = fSqrt * 0.5f;
            float f = 0.5f / fSqrt;
            double[][] dArr2 = this.matrix;
            quaternion.x = ((float) (dArr2[2][1] - dArr2[1][2])) * f;
            double[][] dArr3 = this.matrix;
            quaternion.y = ((float) (dArr3[0][2] - dArr3[2][0])) * f;
            double[][] dArr4 = this.matrix;
            quaternion.z = ((float) (dArr4[1][0] - dArr4[0][1])) * f;
            return quaternion;
        }
        if (d > d2 && d > d3) {
            float fSqrt2 = (float) Math.sqrt(((d + 1.0d) - d2) - d3);
            quaternion.x = fSqrt2 * 0.5f;
            float f2 = 0.5f / fSqrt2;
            double[][] dArr5 = this.matrix;
            quaternion.y = ((float) (dArr5[0][1] + dArr5[1][0])) * f2;
            double[][] dArr6 = this.matrix;
            quaternion.z = ((float) (dArr6[0][2] + dArr6[2][0])) * f2;
            double[][] dArr7 = this.matrix;
            quaternion.w = ((float) (dArr7[2][1] - dArr7[1][2])) * f2;
            return quaternion;
        }
        if (d2 > d3) {
            float fSqrt3 = (float) Math.sqrt(((d2 + 1.0d) - d) - d3);
            quaternion.y = fSqrt3 * 0.5f;
            float f3 = 0.5f / fSqrt3;
            double[][] dArr8 = this.matrix;
            quaternion.x = ((float) (dArr8[0][1] + dArr8[1][0])) * f3;
            double[][] dArr9 = this.matrix;
            quaternion.z = ((float) (dArr9[1][2] + dArr9[2][1])) * f3;
            double[][] dArr10 = this.matrix;
            quaternion.w = ((float) (dArr10[0][2] - dArr10[2][0])) * f3;
            return quaternion;
        }
        float fSqrt4 = (float) Math.sqrt(((d3 + 1.0d) - d) - d2);
        quaternion.z = fSqrt4 * 0.5f;
        float f4 = 0.5f / fSqrt4;
        double[][] dArr11 = this.matrix;
        quaternion.x = ((float) (dArr11[0][2] + dArr11[2][0])) * f4;
        double[][] dArr12 = this.matrix;
        quaternion.y = ((float) (dArr12[1][2] + dArr12[2][1])) * f4;
        double[][] dArr13 = this.matrix;
        quaternion.w = ((float) (dArr13[1][0] - dArr13[0][1])) * f4;
        return quaternion;
    }

    public Vector3<Float> getPosition() {
        return new Vector3<>(Float.valueOf(get(3, 0)), Float.valueOf(get(3, 1)), Float.valueOf(get(3, 2)));
    }

    public Matrix4 getPureRotationMatrix() {
        double dFloatValue = getScale().getX().floatValue();
        double dFloatValue2 = getScale().getY().floatValue();
        double dFloatValue3 = getScale().getZ().floatValue();
        Matrix4 matrix4 = new Matrix4();
        matrix4.set(0, 0, (float) dFloatValue);
        matrix4.set(1, 1, (float) dFloatValue2);
        matrix4.set(2, 2, (float) dFloatValue3);
        Matrix4 matrix4Inverse = matrix4.inverse();
        Matrix4 matrix42 = new Matrix4(this.matrix);
        matrix42.set(3, 0, 0.0f);
        matrix42.set(3, 1, 0.0f);
        matrix42.set(3, 2, 0.0f);
        return matrix42.multiply(matrix4Inverse);
    }

    public Vector3<Float> getRotation() {
        return getPureRotationMatrix().getQuaternion().getRotation();
    }

    public Vector3<Float> getScale() {
        return new Vector3<>(Float.valueOf(getScale(getAxisX())), Float.valueOf(getScale(getAxisY())), Float.valueOf(getScale(getAxisZ())));
    }

    private float getScale(Vector3<Float> vector3) {
        return (float) Math.sqrt((vector3.getX().floatValue() * vector3.getX().floatValue()) + (vector3.getY().floatValue() * vector3.getY().floatValue()) + (vector3.getZ().floatValue() * vector3.getZ().floatValue()));
    }

    public Vector3<Float> getAxisX() {
        return new Vector3<>(Float.valueOf((float) this.matrix[0][0]), Float.valueOf((float) this.matrix[1][0]), Float.valueOf((float) this.matrix[2][0]));
    }

    public Vector3<Float> getAxisY() {
        return new Vector3<>(Float.valueOf((float) this.matrix[0][1]), Float.valueOf((float) this.matrix[1][1]), Float.valueOf((float) this.matrix[2][1]));
    }

    public Vector3<Float> getAxisZ() {
        return new Vector3<>(Float.valueOf((float) this.matrix[0][2]), Float.valueOf((float) this.matrix[1][2]), Float.valueOf((float) this.matrix[2][2]));
    }

    public Matrix4 inverse() {
        Matrix4 matrix4 = new Matrix4();
        double[][] dArr = this.matrix;
        double[] dArr2 = dArr[0];
        double d = dArr2[2];
        double[] dArr3 = dArr[1];
        double d2 = dArr3[3];
        double d3 = dArr3[2];
        double d4 = dArr2[3];
        double d5 = (d * d2) - (d3 * d4);
        double[] dArr4 = dArr[2];
        double d6 = dArr4[3];
        double d7 = dArr4[2];
        double d8 = (d * d6) - (d7 * d4);
        double[] dArr5 = dArr[3];
        double d9 = dArr5[3];
        double d10 = dArr5[2];
        double d11 = (d * d9) - (d4 * d10);
        double d12 = (d3 * d6) - (d7 * d2);
        double d13 = (d3 * d9) - (d2 * d10);
        double d14 = (d7 * d9) - (d10 * d6);
        double d15 = dArr3[1];
        double d16 = dArr4[1];
        double d17 = dArr5[1];
        double d18 = ((d14 * d15) - (d13 * d16)) + (d12 * d17);
        double d19 = dArr2[1];
        double d20 = -(((d14 * d19) - (d11 * d16)) + (d8 * d17));
        double d21 = ((d13 * d19) - (d11 * d15)) + (d17 * d5);
        double d22 = -(((d19 * d12) - (d8 * d15)) + (d16 * d5));
        double d23 = 1.0d / ((((dArr2[0] * d18) + (dArr3[0] * d20)) + (dArr4[0] * d21)) + (dArr5[0] * d22));
        matrix4.set(0, (float) (d18 * d23));
        matrix4.set(1, (float) (d20 * d23));
        matrix4.set(2, (float) (d21 * d23));
        matrix4.set(3, (float) (d22 * d23));
        double[][] dArr6 = this.matrix;
        matrix4.set(4, (float) ((-(((dArr6[1][0] * d14) - (dArr6[2][0] * d13)) + (dArr6[3][0] * d12))) * d23));
        double[][] dArr7 = this.matrix;
        matrix4.set(5, (float) ((((d14 * dArr7[0][0]) - (d11 * dArr7[2][0])) + (dArr7[3][0] * d8)) * d23));
        double[][] dArr8 = this.matrix;
        matrix4.set(6, (float) ((-(((d13 * dArr8[0][0]) - (d11 * dArr8[1][0])) + (d5 * dArr8[3][0]))) * d23));
        double[][] dArr9 = this.matrix;
        matrix4.set(7, (float) ((((d12 * dArr9[0][0]) - (d8 * dArr9[1][0])) + (d5 * dArr9[2][0])) * d23));
        double[][] dArr10 = this.matrix;
        double[] dArr11 = dArr10[0];
        double d24 = dArr11[1];
        double[] dArr12 = dArr10[1];
        double d25 = dArr12[3];
        double d26 = dArr12[1];
        double d27 = dArr11[3];
        double d28 = (d24 * d25) - (d26 * d27);
        double[] dArr13 = dArr10[2];
        double d29 = dArr13[3];
        double d30 = dArr13[1];
        double d31 = (d24 * d29) - (d30 * d27);
        double[] dArr14 = dArr10[3];
        double d32 = dArr14[3];
        double d33 = dArr14[1];
        double d34 = (d24 * d32) - (d27 * d33);
        double d35 = (d26 * d29) - (d30 * d25);
        double d36 = (d26 * d32) - (d25 * d33);
        double d37 = (d30 * d32) - (d33 * d29);
        matrix4.set(8, (float) ((((dArr12[0] * d37) - (dArr13[0] * d36)) + (dArr14[0] * d35)) * d23));
        double[][] dArr15 = this.matrix;
        matrix4.set(9, (float) ((-(((d37 * dArr15[0][0]) - (dArr15[2][0] * d34)) + (dArr15[3][0] * d31))) * d23));
        double[][] dArr16 = this.matrix;
        matrix4.set(10, (float) ((((d36 * dArr16[0][0]) - (d34 * dArr16[1][0])) + (dArr16[3][0] * d28)) * d23));
        double[][] dArr17 = this.matrix;
        matrix4.set(11, (float) ((-(((d35 * dArr17[0][0]) - (d31 * dArr17[1][0])) + (d28 * dArr17[2][0]))) * d23));
        double[][] dArr18 = this.matrix;
        double[] dArr19 = dArr18[1];
        double d38 = dArr19[2];
        double[] dArr20 = dArr18[0];
        double d39 = dArr20[1];
        double d40 = dArr20[2];
        double d41 = dArr19[1];
        double d42 = (d38 * d39) - (d40 * d41);
        double[] dArr21 = dArr18[2];
        double d43 = dArr21[2];
        double d44 = dArr21[1];
        double d45 = (d43 * d39) - (d40 * d44);
        double[] dArr22 = dArr18[3];
        double d46 = dArr22[2];
        double d47 = dArr22[1];
        double d48 = (d39 * d46) - (d40 * d47);
        double d49 = (d43 * d41) - (d38 * d44);
        double d50 = (d41 * d46) - (d38 * d47);
        double d51 = (d46 * d44) - (d43 * d47);
        matrix4.set(12, (float) ((-(((dArr19[0] * d51) - (dArr21[0] * d50)) + (dArr22[0] * d49))) * d23));
        double[][] dArr23 = this.matrix;
        matrix4.set(13, (float) ((((d51 * dArr23[0][0]) - (dArr23[2][0] * d48)) + (dArr23[3][0] * d45)) * d23));
        double[][] dArr24 = this.matrix;
        matrix4.set(14, (float) ((-(((d50 * dArr24[0][0]) - (d48 * dArr24[1][0])) + (dArr24[3][0] * d42))) * d23));
        double[][] dArr25 = this.matrix;
        matrix4.set(15, (float) ((((d49 * dArr25[0][0]) - (d45 * dArr25[1][0])) + (d42 * dArr25[2][0])) * d23));
        return matrix4.transpose();
    }

    public Matrix4 transpose() {
        double[][] dArr = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 4, 4);
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                dArr[i][i2] = this.matrix[i2][i];
            }
        }
        return new Matrix4(dArr);
    }
}
