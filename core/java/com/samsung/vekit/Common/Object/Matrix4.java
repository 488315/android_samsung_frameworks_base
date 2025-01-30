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
      for (int j = 0; j < 4; j++) {
        this.matrix[i][j] = matrix.matrix[i][j];
      }
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
      this.matrix[x][y] = data[index];
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
    this.matrix[x][y] = data;
  }

  public float get(int row, int column) {
    return (float) this.matrix[row][column];
  }

  public void set(int row, int col, float data) {
    this.matrix[row][col] = data;
  }

  public String toString() {
    String data = "";
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        data = data + this.matrix[i][j] + " ";
      }
    }
    return data;
  }

  public Vector4<Float> getRow(int row) {
    Float valueOf = Float.valueOf(0.0f);
    Vector4<Float> result = new Vector4<>(valueOf, valueOf, valueOf, valueOf);
    result.setX(Float.valueOf((float) this.matrix[row][0]));
    result.setY(Float.valueOf((float) this.matrix[row][1]));
    result.setZ(Float.valueOf((float) this.matrix[row][2]));
    result.setW(Float.valueOf((float) this.matrix[row][3]));
    return result;
  }

  public Vector4<Float> getColumn(int column) {
    Float valueOf = Float.valueOf(0.0f);
    Vector4<Float> result = new Vector4<>(valueOf, valueOf, valueOf, valueOf);
    result.setX(Float.valueOf((float) this.matrix[0][column]));
    result.setY(Float.valueOf((float) this.matrix[1][column]));
    result.setZ(Float.valueOf((float) this.matrix[2][column]));
    result.setW(Float.valueOf((float) this.matrix[3][column]));
    return result;
  }

  public float[] toArray() {
    float[] array = new float[16];
    int index = 0;
    for (int i = 0; i < 4; i++) {
      int j = 0;
      while (j < 4) {
        array[index] = (float) this.matrix[i][j];
        j++;
        index++;
      }
    }
    return array;
  }

  public Matrix4 multiply(Matrix4 other) {
    double[][] data = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 4, 4);
    double[] dArr = data[0];
    double[][] dArr2 = other.matrix;
    double[] dArr3 = dArr2[0];
    double d = dArr3[0];
    double[][] dArr4 = this.matrix;
    double[] dArr5 = dArr4[0];
    double d2 = d * dArr5[0];
    double d3 = dArr3[1];
    double[] dArr6 = dArr4[1];
    double d4 = d2 + (dArr6[0] * d3);
    double d5 = dArr3[2];
    double[] dArr7 = dArr4[2];
    double d6 = d4 + (dArr7[0] * d5);
    double d7 = dArr3[3];
    double[] dArr8 = dArr4[3];
    dArr[0] = d6 + (dArr8[0] * d7);
    double[] dArr9 = data[0];
    double d8 = dArr3[0];
    dArr9[1] = (dArr5[1] * d8) + (d3 * dArr6[1]) + (dArr7[1] * d5) + (dArr8[1] * d7);
    double[] dArr10 = data[0];
    double d9 = dArr5[2] * d8;
    double d10 = dArr3[1];
    dArr10[2] = d9 + (dArr6[2] * d10) + (d5 * dArr7[2]) + (dArr8[2] * d7);
    data[0][3] = (d8 * dArr5[3]) + (d10 * dArr6[3]) + (dArr3[2] * dArr7[3]) + (d7 * dArr8[3]);
    double[] dArr11 = data[1];
    double[] dArr12 = dArr2[1];
    double d11 = dArr12[0] * dArr5[0];
    double d12 = dArr12[1];
    double d13 = d11 + (dArr6[0] * d12);
    double d14 = dArr12[2];
    double d15 = d13 + (dArr7[0] * d14);
    double d16 = dArr12[3];
    dArr11[0] = d15 + (dArr8[0] * d16);
    double[] dArr13 = data[1];
    double d17 = dArr12[0];
    dArr13[1] = (dArr5[1] * d17) + (d12 * dArr6[1]) + (dArr7[1] * d14) + (dArr8[1] * d16);
    double[] dArr14 = data[1];
    double d18 = dArr5[2] * d17;
    double d19 = dArr12[1];
    dArr14[2] = d18 + (dArr6[2] * d19) + (d14 * dArr7[2]) + (dArr8[2] * d16);
    data[1][3] = (d17 * dArr5[3]) + (d19 * dArr6[3]) + (dArr12[2] * dArr7[3]) + (d16 * dArr8[3]);
    double[] dArr15 = data[2];
    double[] dArr16 = dArr2[2];
    double d20 = dArr16[0] * dArr5[0];
    double d21 = dArr16[1];
    double d22 = d20 + (dArr6[0] * d21);
    double d23 = dArr16[2];
    double d24 = d22 + (dArr7[0] * d23);
    double d25 = dArr16[3];
    dArr15[0] = d24 + (dArr8[0] * d25);
    double[] dArr17 = data[2];
    double d26 = dArr16[0];
    dArr17[1] = (dArr5[1] * d26) + (d21 * dArr6[1]) + (dArr7[1] * d23) + (dArr8[1] * d25);
    double[] dArr18 = data[2];
    double d27 = dArr5[2] * d26;
    double d28 = dArr16[1];
    dArr18[2] = d27 + (dArr6[2] * d28) + (d23 * dArr7[2]) + (dArr8[2] * d25);
    data[2][3] = (d26 * dArr5[3]) + (d28 * dArr6[3]) + (dArr16[2] * dArr7[3]) + (d25 * dArr8[3]);
    double[] dArr19 = data[3];
    double[] dArr20 = dArr2[3];
    double d29 = dArr20[0] * dArr5[0];
    double d30 = dArr20[1];
    double d31 = d29 + (dArr6[0] * d30);
    double d32 = dArr20[2];
    double d33 = d31 + (dArr7[0] * d32);
    double d34 = dArr20[3];
    dArr19[0] = d33 + (dArr8[0] * d34);
    double[] dArr21 = data[3];
    double d35 = dArr20[0];
    dArr21[1] = (dArr5[1] * d35) + (d30 * dArr6[1]) + (dArr7[1] * d32) + (dArr8[1] * d34);
    double[] dArr22 = data[3];
    double d36 = dArr5[2] * d35;
    double d37 = dArr20[1];
    dArr22[2] = d36 + (dArr6[2] * d37) + (d32 * dArr7[2]) + (dArr8[2] * d34);
    data[3][3] = (d35 * dArr5[3]) + (d37 * dArr6[3]) + (dArr20[2] * dArr7[3]) + (d34 * dArr8[3]);
    return new Matrix4(data);
  }

  public Matrix4 divide(double scalar) {
    if (scalar == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
      Log.m96e("Matrix4", "Non zero divider required");
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
    switch (C53781.$SwitchMap$com$samsung$vekit$Common$Type$AxisType[axisType.ordinal()]) {
      case 1:
        x = 1.0f;
        break;
      case 2:
        y = 1.0f;
        break;
      case 3:
        z = 1.0f;
        break;
    }
    Quaternion quaternion =
        new Quaternion(new Vector3(Float.valueOf(x), Float.valueOf(y), Float.valueOf(z)), angle);
    Matrix4 rotationMatrix = quaternion.getMatrix();
    setMatrix(rotationMatrix.multiply(this));
    return this;
  }

  /* renamed from: com.samsung.vekit.Common.Object.Matrix4$1 */
  static /* synthetic */ class C53781 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$vekit$Common$Type$AxisType;

    static {
      int[] iArr = new int[AxisType.values().length];
      $SwitchMap$com$samsung$vekit$Common$Type$AxisType = iArr;
      try {
        iArr[AxisType.X.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$AxisType[AxisType.Y.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        $SwitchMap$com$samsung$vekit$Common$Type$AxisType[AxisType.Z.ordinal()] = 3;
      } catch (NoSuchFieldError e3) {
      }
    }
  }

  public Matrix4 rotate(float eulerX, float eulerY, float eulerZ) {
    Quaternion quaternion = new Quaternion();
    quaternion.setRotation(eulerX, eulerY, eulerZ);
    setMatrix(quaternion.getMatrix().multiply(this));
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
    double[][] dArr = this.matrix;
    double d = dArr[0][0];
    double d2 = dArr[1][1];
    double d3 = dArr[2][2];
    float trace = (float) (d + d2 + d3);
    if (trace > 0.0f) {
      float s = 0.5f / ((float) Math.sqrt(1.0f + trace));
      quaternion.f3100w = 0.25f / s;
      double[][] dArr2 = this.matrix;
      quaternion.f3101x = ((float) (dArr2[2][1] - dArr2[1][2])) * s;
      double[][] dArr3 = this.matrix;
      quaternion.f3102y = ((float) (dArr3[2][0] - dArr3[0][2])) * s;
      double[][] dArr4 = this.matrix;
      quaternion.f3103z = ((float) (dArr4[0][1] - dArr4[1][0])) * s;
    } else if (d > d2 && d > d3) {
      float s2 = ((float) Math.sqrt(((d + 1.0d) - d2) - d3)) * 2.0f;
      double[][] dArr5 = this.matrix;
      quaternion.f3100w = ((float) (dArr5[1][2] - dArr5[2][1])) / s2;
      quaternion.f3101x = 0.25f * s2;
      double[][] dArr6 = this.matrix;
      quaternion.f3102y = ((float) (dArr6[1][0] + dArr6[0][1])) / s2;
      double[][] dArr7 = this.matrix;
      quaternion.f3103z = ((float) (dArr7[2][0] + dArr7[0][2])) / s2;
    } else if (d2 > d3) {
      float s3 = ((float) Math.sqrt(((d2 + 1.0d) - d) - d3)) * 2.0f;
      double[][] dArr8 = this.matrix;
      quaternion.f3100w = ((float) (dArr8[2][0] - dArr8[0][2])) / s3;
      double[][] dArr9 = this.matrix;
      quaternion.f3101x = ((float) (dArr9[1][0] + dArr9[0][1])) / s3;
      quaternion.f3102y = 0.25f * s3;
      double[][] dArr10 = this.matrix;
      quaternion.f3103z = ((float) (dArr10[2][1] + dArr10[1][2])) / s3;
    } else {
      float s4 = ((float) Math.sqrt(((d3 + 1.0d) - d) - d2)) * 2.0f;
      double[][] dArr11 = this.matrix;
      quaternion.f3100w = ((float) (dArr11[0][1] - dArr11[1][0])) / s4;
      double[][] dArr12 = this.matrix;
      quaternion.f3101x = ((float) (dArr12[2][0] + dArr12[0][2])) / s4;
      double[][] dArr13 = this.matrix;
      quaternion.f3102y = ((float) (dArr13[2][1] + dArr13[1][2])) / s4;
      quaternion.f3103z = 0.25f * s4;
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
    return inverseMatrix.multiply(rotationMatrix);
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
    double[][] dArr = this.matrix;
    double[] dArr2 = dArr[0];
    double d = dArr2[2];
    double[] dArr3 = dArr[1];
    double d2 = dArr3[3];
    double d3 = dArr3[2];
    double d4 = dArr2[3];
    double a0 = (d * d2) - (d3 * d4);
    double[] dArr4 = dArr[2];
    double d5 = dArr4[3];
    double d6 = dArr4[2];
    double a1 = (d * d5) - (d6 * d4);
    double[] dArr5 = dArr[3];
    double d7 = dArr5[3];
    double d8 = dArr5[2];
    double a2 = (d * d7) - (d4 * d8);
    double a3 = (d3 * d5) - (d6 * d2);
    double a4 = (d3 * d7) - (d2 * d8);
    double a5 = (d6 * d7) - (d8 * d5);
    double d9 = dArr3[1];
    double d10 = dArr4[1];
    double d11 = dArr5[1];
    double b0 = ((a5 * d9) - (a4 * d10)) + (a3 * d11);
    double d12 = dArr2[1];
    double b1 = -(((a5 * d12) - (a2 * d10)) + (a1 * d11));
    double b2 = ((a4 * d12) - (a2 * d9)) + (d11 * a0);
    double b3 = -(((d12 * a3) - (d9 * a1)) + (d10 * a0));
    double invDeterminant =
        1.0d / ((((dArr2[0] * b0) + (dArr3[0] * b1)) + (dArr4[0] * b2)) + (dArr5[0] * b3));
    outMatrix.set(0, (float) (b0 * invDeterminant));
    outMatrix.set(1, (float) (b1 * invDeterminant));
    outMatrix.set(2, (float) (b2 * invDeterminant));
    outMatrix.set(3, (float) (b3 * invDeterminant));
    double[][] dArr6 = this.matrix;
    outMatrix.set(
        4,
        (float)
            ((-(((dArr6[1][0] * a5) - (dArr6[2][0] * a4)) + (dArr6[3][0] * a3))) * invDeterminant));
    double[][] dArr7 = this.matrix;
    outMatrix.set(
        5,
        (float)
            ((((dArr7[0][0] * a5) - (dArr7[2][0] * a2)) + (dArr7[3][0] * a1)) * invDeterminant));
    double[][] dArr8 = this.matrix;
    outMatrix.set(
        6,
        (float)
            ((-(((dArr8[0][0] * a4) - (dArr8[1][0] * a2)) + (dArr8[3][0] * a0))) * invDeterminant));
    double[][] dArr9 = this.matrix;
    outMatrix.set(
        7,
        (float)
            ((((dArr9[0][0] * a3) - (dArr9[1][0] * a1)) + (dArr9[2][0] * a0)) * invDeterminant));
    double[][] dArr10 = this.matrix;
    double[] dArr11 = dArr10[0];
    double d13 = dArr11[1];
    double[] dArr12 = dArr10[1];
    double d14 = dArr12[3];
    double d15 = dArr12[1];
    double d16 = dArr11[3];
    double a02 = (d13 * d14) - (d15 * d16);
    double[] dArr13 = dArr10[2];
    double d17 = dArr13[3];
    double d18 = dArr13[1];
    double a12 = (d13 * d17) - (d18 * d16);
    double[] dArr14 = dArr10[3];
    double d19 = dArr14[3];
    double d20 = dArr14[1];
    double a22 = (d13 * d19) - (d16 * d20);
    double a32 = (d15 * d17) - (d18 * d14);
    double a42 = (d15 * d19) - (d14 * d20);
    double a52 = (d18 * d19) - (d20 * d17);
    outMatrix.set(
        8,
        (float) ((((dArr12[0] * a52) - (dArr13[0] * a42)) + (dArr14[0] * a32)) * invDeterminant));
    double[][] dArr15 = this.matrix;
    outMatrix.set(
        9,
        (float)
            ((-(((dArr15[0][0] * a52) - (dArr15[2][0] * a22)) + (dArr15[3][0] * a12)))
                * invDeterminant));
    double[][] dArr16 = this.matrix;
    outMatrix.set(
        10,
        (float)
            ((((dArr16[0][0] * a42) - (dArr16[1][0] * a22)) + (dArr16[3][0] * a02))
                * invDeterminant));
    double[][] dArr17 = this.matrix;
    outMatrix.set(
        11,
        (float)
            ((-(((dArr17[0][0] * a32) - (dArr17[1][0] * a12)) + (dArr17[2][0] * a02)))
                * invDeterminant));
    double[][] dArr18 = this.matrix;
    double[] dArr19 = dArr18[1];
    double d21 = dArr19[2];
    double[] dArr20 = dArr18[0];
    double d22 = dArr20[1];
    double d23 = dArr20[2];
    double d24 = dArr19[1];
    double a03 = (d21 * d22) - (d23 * d24);
    double[] dArr21 = dArr18[2];
    double d25 = dArr21[2];
    double d26 = dArr21[1];
    double a13 = (d25 * d22) - (d23 * d26);
    double[] dArr22 = dArr18[3];
    double d27 = dArr22[2];
    double d28 = dArr22[1];
    double a23 = (d22 * d27) - (d23 * d28);
    double a33 = (d25 * d24) - (d21 * d26);
    double a43 = (d24 * d27) - (d21 * d28);
    double a53 = (d27 * d26) - (d25 * d28);
    outMatrix.set(
        12,
        (float)
            ((-(((dArr19[0] * a53) - (dArr21[0] * a43)) + (dArr22[0] * a33))) * invDeterminant));
    double[][] dArr23 = this.matrix;
    outMatrix.set(
        13,
        (float)
            ((((dArr23[0][0] * a53) - (dArr23[2][0] * a23)) + (dArr23[3][0] * a13))
                * invDeterminant));
    double[][] dArr24 = this.matrix;
    outMatrix.set(
        14,
        (float)
            ((-(((dArr24[0][0] * a43) - (dArr24[1][0] * a23)) + (dArr24[3][0] * a03)))
                * invDeterminant));
    double[][] dArr25 = this.matrix;
    outMatrix.set(
        15,
        (float)
            ((((dArr25[0][0] * a33) - (dArr25[1][0] * a13)) + (dArr25[2][0] * a03))
                * invDeterminant));
    return outMatrix;
  }
}
