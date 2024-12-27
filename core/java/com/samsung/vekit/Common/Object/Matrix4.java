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

    public Matrix4(Matrix4 matrix) {
        setMatrix(matrix);
    }

    public void setMatrix(Matrix4 matrix) {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(matrix.matrix[i], 0, this.matrix[i], 0, 4);
        }
    }

    public Matrix4(double[][] array) {
        set(array);
    }

    public Matrix4(float[] array) {
        set(array);
    }

    public void identity() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j) {
                    this.matrix[i][j] = 1.0d;
                } else {
                    this.matrix[i][j] = 0.0d;
                }
            }
        }
    }

    public void set(float[] data) {
        for (int index = 0; index < 16; index++) {
            int x = index / 4;
            int y = index % 4;
            this.matrix[y][x] = data[index];
        }
    }

    public void set(double[][] data) {
        for (int i = 0; i < 4; i++) {
            System.arraycopy(data[i], 0, this.matrix[i], 0, 4);
        }
    }

    public void set(int index, float data) {
        int x = index / 4;
        int y = index % 4;
        this.matrix[y][x] = data;
    }

    public float get(int row, int column) {
        return (float) this.matrix[column][row];
    }

    public void set(int row, int col, float data) {
        this.matrix[col][row] = data;
    }

    public String toString() {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                data.append(this.matrix[j][i]).append(" ");
            }
        }
        return data.toString();
    }

    public Vector4<Float> getRow(int row) {
        Float valueOf = Float.valueOf(0.0f);
        Vector4<Float> result = new Vector4<>(valueOf, valueOf, valueOf, valueOf);
        result.setX(Float.valueOf((float) this.matrix[0][row]));
        result.setY(Float.valueOf((float) this.matrix[1][row]));
        result.setZ(Float.valueOf((float) this.matrix[2][row]));
        result.setW(Float.valueOf((float) this.matrix[3][row]));
        return result;
    }

    public Vector4<Float> getColumn(int column) {
        Float valueOf = Float.valueOf(0.0f);
        Vector4<Float> result = new Vector4<>(valueOf, valueOf, valueOf, valueOf);
        result.setX(Float.valueOf((float) this.matrix[column][0]));
        result.setY(Float.valueOf((float) this.matrix[column][1]));
        result.setZ(Float.valueOf((float) this.matrix[column][2]));
        result.setW(Float.valueOf((float) this.matrix[column][3]));
        return result;
    }

    public float[] toArray() {
        float[] array = new float[16];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            int j = 0;
            while (j < 4) {
                array[index] = (float) this.matrix[j][i];
                j++;
                index++;
            }
        }
        return array;
    }

    public Matrix4 multiply(Matrix4 other) {
        double[][] data = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 4, 4);
        data[0][0] =
                (other.matrix[0][0] * this.matrix[0][0])
                        + (other.matrix[1][0] * this.matrix[0][1])
                        + (other.matrix[2][0] * this.matrix[0][2])
                        + (other.matrix[3][0] * this.matrix[0][3]);
        data[1][0] =
                (other.matrix[0][0] * this.matrix[1][0])
                        + (other.matrix[1][0] * this.matrix[1][1])
                        + (other.matrix[2][0] * this.matrix[1][2])
                        + (other.matrix[3][0] * this.matrix[1][3]);
        data[2][0] =
                (other.matrix[0][0] * this.matrix[2][0])
                        + (other.matrix[1][0] * this.matrix[2][1])
                        + (other.matrix[2][0] * this.matrix[2][2])
                        + (other.matrix[3][0] * this.matrix[2][3]);
        data[3][0] =
                (other.matrix[0][0] * this.matrix[3][0])
                        + (other.matrix[1][0] * this.matrix[3][1])
                        + (other.matrix[2][0] * this.matrix[3][2])
                        + (other.matrix[3][0] * this.matrix[3][3]);
        data[0][1] =
                (other.matrix[0][1] * this.matrix[0][0])
                        + (other.matrix[1][1] * this.matrix[0][1])
                        + (other.matrix[2][1] * this.matrix[0][2])
                        + (other.matrix[3][1] * this.matrix[0][3]);
        data[1][1] =
                (other.matrix[0][1] * this.matrix[1][0])
                        + (other.matrix[1][1] * this.matrix[1][1])
                        + (other.matrix[2][1] * this.matrix[1][2])
                        + (other.matrix[3][1] * this.matrix[1][3]);
        data[2][1] =
                (other.matrix[0][1] * this.matrix[2][0])
                        + (other.matrix[1][1] * this.matrix[2][1])
                        + (other.matrix[2][1] * this.matrix[2][2])
                        + (other.matrix[3][1] * this.matrix[2][3]);
        data[3][1] =
                (other.matrix[0][1] * this.matrix[3][0])
                        + (other.matrix[1][1] * this.matrix[3][1])
                        + (other.matrix[2][1] * this.matrix[3][2])
                        + (other.matrix[3][1] * this.matrix[3][3]);
        data[0][2] =
                (other.matrix[0][2] * this.matrix[0][0])
                        + (other.matrix[1][2] * this.matrix[0][1])
                        + (other.matrix[2][2] * this.matrix[0][2])
                        + (other.matrix[3][2] * this.matrix[0][3]);
        data[1][2] =
                (other.matrix[0][2] * this.matrix[1][0])
                        + (other.matrix[1][2] * this.matrix[1][1])
                        + (other.matrix[2][2] * this.matrix[1][2])
                        + (other.matrix[3][2] * this.matrix[1][3]);
        data[2][2] =
                (other.matrix[0][2] * this.matrix[2][0])
                        + (other.matrix[1][2] * this.matrix[2][1])
                        + (other.matrix[2][2] * this.matrix[2][2])
                        + (other.matrix[3][2] * this.matrix[2][3]);
        data[3][2] =
                (other.matrix[0][2] * this.matrix[3][0])
                        + (other.matrix[1][2] * this.matrix[3][1])
                        + (other.matrix[2][2] * this.matrix[3][2])
                        + (other.matrix[3][2] * this.matrix[3][3]);
        data[0][3] =
                (other.matrix[0][3] * this.matrix[0][0])
                        + (other.matrix[1][3] * this.matrix[0][1])
                        + (other.matrix[2][3] * this.matrix[0][2])
                        + (other.matrix[3][3] * this.matrix[0][3]);
        data[1][3] =
                (other.matrix[0][3] * this.matrix[1][0])
                        + (other.matrix[1][3] * this.matrix[1][1])
                        + (other.matrix[2][3] * this.matrix[1][2])
                        + (other.matrix[3][3] * this.matrix[1][3]);
        data[2][3] =
                (other.matrix[0][3] * this.matrix[2][0])
                        + (other.matrix[1][3] * this.matrix[2][1])
                        + (other.matrix[2][3] * this.matrix[2][2])
                        + (other.matrix[3][3] * this.matrix[2][3]);
        data[3][3] =
                (other.matrix[0][3] * this.matrix[3][0])
                        + (other.matrix[1][3] * this.matrix[3][1])
                        + (other.matrix[2][3] * this.matrix[3][2])
                        + (other.matrix[3][3] * this.matrix[3][3]);
        return new Matrix4(data);
    }

    public Matrix4 divide(double scalar) {
        if (scalar == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            Log.e("Matrix4", "Non zero divider required");
            return new Matrix4();
        }
        double[][] data = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 4, 4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                data[i][j] = this.matrix[i][j] / scalar;
            }
        }
        return new Matrix4(data);
    }

    public Matrix4 translate(float x, float y, float z) {
        Matrix4 translationMatrix = new Matrix4();
        translationMatrix.set(3, 0, x);
        translationMatrix.set(3, 1, y);
        translationMatrix.set(3, 2, z);
        setMatrix(translationMatrix.multiply(this));
        return this;
    }

    public Matrix4 rotate(AxisType axisType, float angle) {
        float x = 0.0f;
        float y = 0.0f;
        float z = 0.0f;
        switch (axisType) {
            case X:
                x = 1.0f;
                break;
            case Y:
                y = 1.0f;
                break;
            case Z:
                z = 1.0f;
                break;
        }
        Quaternion quaternion =
                new Quaternion(
                        new Vector3(Float.valueOf(x), Float.valueOf(y), Float.valueOf(z)), angle);
        Matrix4 rotationMatrix = quaternion.getMatrix();
        setMatrix(rotationMatrix.multiply(this));
        return this;
    }

    public Matrix4 rotate(float eulerX, float eulerY, float eulerZ) {
        Quaternion quaternion = new Quaternion();
        quaternion.setRotation(eulerX, eulerY, eulerZ);
        Matrix4 rotationMatrix = quaternion.getMatrix();
        setMatrix(rotationMatrix.multiply(this));
        return this;
    }

    public Matrix4 rotate(Quaternion quaternion) {
        setMatrix(quaternion.getMatrix().multiply(this));
        return this;
    }

    public Matrix4 scale(float x, float y, float z) {
        Matrix4 scaleMatrix = new Matrix4();
        scaleMatrix.set(0, 0, x);
        scaleMatrix.set(1, 1, y);
        scaleMatrix.set(2, 2, z);
        setMatrix(scaleMatrix.multiply(this));
        return this;
    }

    public Quaternion getQuaternion() {
        Quaternion quaternion = new Quaternion();
        float trace = (float) (this.matrix[0][0] + this.matrix[1][1] + this.matrix[2][2]);
        if (trace > 0.0f) {
            float s = (float) Math.sqrt(1.0f + trace);
            quaternion.w = s * 0.5f;
            float s2 = 0.5f / s;
            quaternion.x = ((float) (this.matrix[2][1] - this.matrix[1][2])) * s2;
            quaternion.y = ((float) (this.matrix[0][2] - this.matrix[2][0])) * s2;
            quaternion.z = ((float) (this.matrix[1][0] - this.matrix[0][1])) * s2;
        } else if (this.matrix[0][0] > this.matrix[1][1] && this.matrix[0][0] > this.matrix[2][2]) {
            float s3 =
                    (float)
                            Math.sqrt(
                                    ((this.matrix[0][0] + 1.0d) - this.matrix[1][1])
                                            - this.matrix[2][2]);
            quaternion.x = s3 * 0.5f;
            float s4 = 0.5f / s3;
            quaternion.y = ((float) (this.matrix[0][1] + this.matrix[1][0])) * s4;
            quaternion.z = ((float) (this.matrix[0][2] + this.matrix[2][0])) * s4;
            quaternion.w = ((float) (this.matrix[2][1] - this.matrix[1][2])) * s4;
        } else if (this.matrix[1][1] > this.matrix[2][2]) {
            float s5 =
                    (float)
                            Math.sqrt(
                                    ((this.matrix[1][1] + 1.0d) - this.matrix[0][0])
                                            - this.matrix[2][2]);
            quaternion.y = s5 * 0.5f;
            float s6 = 0.5f / s5;
            quaternion.x = ((float) (this.matrix[0][1] + this.matrix[1][0])) * s6;
            quaternion.z = ((float) (this.matrix[1][2] + this.matrix[2][1])) * s6;
            quaternion.w = ((float) (this.matrix[0][2] - this.matrix[2][0])) * s6;
        } else {
            float s7 =
                    (float)
                            Math.sqrt(
                                    ((this.matrix[2][2] + 1.0d) - this.matrix[0][0])
                                            - this.matrix[1][1]);
            quaternion.z = s7 * 0.5f;
            float s8 = 0.5f / s7;
            quaternion.x = ((float) (this.matrix[0][2] + this.matrix[2][0])) * s8;
            quaternion.y = ((float) (this.matrix[1][2] + this.matrix[2][1])) * s8;
            quaternion.w = ((float) (this.matrix[1][0] - this.matrix[0][1])) * s8;
        }
        return quaternion;
    }

    public Vector3<Float> getPosition() {
        return new Vector3<>(
                Float.valueOf(get(3, 0)), Float.valueOf(get(3, 1)), Float.valueOf(get(3, 2)));
    }

    public Matrix4 getPureRotationMatrix() {
        double scaleX = getScale().getX().floatValue();
        double scaleY = getScale().getY().floatValue();
        double scaleZ = getScale().getZ().floatValue();
        Matrix4 scaleMatrix = new Matrix4();
        scaleMatrix.set(0, 0, (float) scaleX);
        scaleMatrix.set(1, 1, (float) scaleY);
        scaleMatrix.set(2, 2, (float) scaleZ);
        Matrix4 inverseMatrix = scaleMatrix.inverse();
        Matrix4 rotationMatrix = new Matrix4(this.matrix);
        rotationMatrix.set(3, 0, 0.0f);
        rotationMatrix.set(3, 1, 0.0f);
        rotationMatrix.set(3, 2, 0.0f);
        return rotationMatrix.multiply(inverseMatrix);
    }

    public Vector3<Float> getRotation() {
        Matrix4 pureRotation = getPureRotationMatrix();
        Quaternion quaternion = pureRotation.getQuaternion();
        return quaternion.getRotation();
    }

    public Vector3<Float> getScale() {
        return new Vector3<>(
                Float.valueOf(getScale(getAxisX())),
                Float.valueOf(getScale(getAxisY())),
                Float.valueOf(getScale(getAxisZ())));
    }

    private float getScale(Vector3<Float> data) {
        return (float)
                Math.sqrt(
                        (data.getX().floatValue() * data.getX().floatValue())
                                + (data.getY().floatValue() * data.getY().floatValue())
                                + (data.getZ().floatValue() * data.getZ().floatValue()));
    }

    public Vector3<Float> getAxisX() {
        return new Vector3<>(
                Float.valueOf((float) this.matrix[0][0]),
                Float.valueOf((float) this.matrix[1][0]),
                Float.valueOf((float) this.matrix[2][0]));
    }

    public Vector3<Float> getAxisY() {
        return new Vector3<>(
                Float.valueOf((float) this.matrix[0][1]),
                Float.valueOf((float) this.matrix[1][1]),
                Float.valueOf((float) this.matrix[2][1]));
    }

    public Vector3<Float> getAxisZ() {
        return new Vector3<>(
                Float.valueOf((float) this.matrix[0][2]),
                Float.valueOf((float) this.matrix[1][2]),
                Float.valueOf((float) this.matrix[2][2]));
    }

    public Matrix4 inverse() {
        Matrix4 outMatrix = new Matrix4();
        double a0 =
                (this.matrix[0][2] * this.matrix[1][3]) - (this.matrix[1][2] * this.matrix[0][3]);
        double a1 =
                (this.matrix[0][2] * this.matrix[2][3]) - (this.matrix[2][2] * this.matrix[0][3]);
        double a2 =
                (this.matrix[0][2] * this.matrix[3][3]) - (this.matrix[3][2] * this.matrix[0][3]);
        double a3 =
                (this.matrix[1][2] * this.matrix[2][3]) - (this.matrix[2][2] * this.matrix[1][3]);
        double a4 =
                (this.matrix[1][2] * this.matrix[3][3]) - (this.matrix[3][2] * this.matrix[1][3]);
        double a5 =
                (this.matrix[2][2] * this.matrix[3][3]) - (this.matrix[3][2] * this.matrix[2][3]);
        double b0 =
                ((this.matrix[1][1] * a5) - (this.matrix[2][1] * a4)) + (this.matrix[3][1] * a3);
        double b1 =
                -(((this.matrix[0][1] * a5) - (this.matrix[2][1] * a2)) + (this.matrix[3][1] * a1));
        double b2 =
                ((this.matrix[0][1] * a4) - (this.matrix[1][1] * a2)) + (this.matrix[3][1] * a0);
        double b3 =
                -(((this.matrix[0][1] * a3) - (this.matrix[1][1] * a1)) + (this.matrix[2][1] * a0));
        double invDeterminant =
                1.0d
                        / ((((this.matrix[0][0] * b0) + (this.matrix[1][0] * b1))
                                        + (this.matrix[2][0] * b2))
                                + (this.matrix[3][0] * b3));
        outMatrix.set(0, (float) (b0 * invDeterminant));
        outMatrix.set(1, (float) (b1 * invDeterminant));
        outMatrix.set(2, (float) (b2 * invDeterminant));
        outMatrix.set(3, (float) (b3 * invDeterminant));
        outMatrix.set(
                4,
                (float)
                        ((-(((this.matrix[1][0] * a5) - (this.matrix[2][0] * a4))
                                        + (this.matrix[3][0] * a3)))
                                * invDeterminant));
        outMatrix.set(
                5,
                (float)
                        ((((a5 * this.matrix[0][0]) - (this.matrix[2][0] * a2))
                                        + (this.matrix[3][0] * a1))
                                * invDeterminant));
        outMatrix.set(
                6,
                (float)
                        ((-(((this.matrix[0][0] * a4) - (this.matrix[1][0] * a2))
                                        + (this.matrix[3][0] * a0)))
                                * invDeterminant));
        outMatrix.set(
                7,
                (float)
                        ((((this.matrix[0][0] * a3) - (this.matrix[1][0] * a1))
                                        + (this.matrix[2][0] * a0))
                                * invDeterminant));
        double a02 =
                (this.matrix[0][1] * this.matrix[1][3]) - (this.matrix[1][1] * this.matrix[0][3]);
        double a12 =
                (this.matrix[0][1] * this.matrix[2][3]) - (this.matrix[2][1] * this.matrix[0][3]);
        double a22 =
                (this.matrix[3][3] * this.matrix[0][1]) - (this.matrix[3][1] * this.matrix[0][3]);
        double a32 =
                (this.matrix[1][1] * this.matrix[2][3]) - (this.matrix[2][1] * this.matrix[1][3]);
        double a42 =
                (this.matrix[1][1] * this.matrix[3][3]) - (this.matrix[3][1] * this.matrix[1][3]);
        double a52 =
                (this.matrix[2][1] * this.matrix[3][3]) - (this.matrix[3][1] * this.matrix[2][3]);
        outMatrix.set(
                8,
                (float)
                        ((((this.matrix[1][0] * a52) - (this.matrix[2][0] * a42))
                                        + (this.matrix[3][0] * a32))
                                * invDeterminant));
        outMatrix.set(
                9,
                (float)
                        ((-(((this.matrix[0][0] * a52) - (this.matrix[2][0] * a22))
                                        + (this.matrix[3][0] * a12)))
                                * invDeterminant));
        outMatrix.set(
                10,
                (float)
                        ((((this.matrix[0][0] * a42) - (this.matrix[1][0] * a22))
                                        + (this.matrix[3][0] * a02))
                                * invDeterminant));
        outMatrix.set(
                11,
                (float)
                        ((-(((this.matrix[0][0] * a32) - (this.matrix[1][0] * a12))
                                        + (this.matrix[2][0] * a02)))
                                * invDeterminant));
        double a03 =
                (this.matrix[1][2] * this.matrix[0][1]) - (this.matrix[0][2] * this.matrix[1][1]);
        double a13 =
                (this.matrix[2][2] * this.matrix[0][1]) - (this.matrix[0][2] * this.matrix[2][1]);
        double a23 =
                (this.matrix[3][2] * this.matrix[0][1]) - (this.matrix[0][2] * this.matrix[3][1]);
        double a33 =
                (this.matrix[2][2] * this.matrix[1][1]) - (this.matrix[1][2] * this.matrix[2][1]);
        double a43 =
                (this.matrix[3][2] * this.matrix[1][1]) - (this.matrix[1][2] * this.matrix[3][1]);
        double a53 =
                (this.matrix[2][1] * this.matrix[3][2]) - (this.matrix[2][2] * this.matrix[3][1]);
        outMatrix.set(
                12,
                (float)
                        ((-(((this.matrix[1][0] * a53) - (this.matrix[2][0] * a43))
                                        + (this.matrix[3][0] * a33)))
                                * invDeterminant));
        outMatrix.set(
                13,
                (float)
                        ((((this.matrix[0][0] * a53) - (this.matrix[2][0] * a23))
                                        + (this.matrix[3][0] * a13))
                                * invDeterminant));
        outMatrix.set(
                14,
                (float)
                        ((-(((this.matrix[0][0] * a43) - (this.matrix[1][0] * a23))
                                        + (this.matrix[3][0] * a03)))
                                * invDeterminant));
        outMatrix.set(
                15,
                (float)
                        ((((this.matrix[0][0] * a33) - (this.matrix[1][0] * a13))
                                        + (this.matrix[2][0] * a03))
                                * invDeterminant));
        return outMatrix.transpose();
    }

    public Matrix4 transpose() {
        double[][] data = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 4, 4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                data[i][j] = this.matrix[j][i];
            }
        }
        return new Matrix4(data);
    }
}
