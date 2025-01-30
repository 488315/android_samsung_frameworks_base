package com.android.server.accessibility;

import android.util.Slog;

/* loaded from: classes.dex */
public class CVDCalculator {
  public double CVDSeverity;
  public double CVDStrength;
  public double c_index;
  public ColorTransferTable mColorTransferTable;
  public double majorAngle;
  public double majorRadius;
  public double minorRadius;
  public double s_index;
  public double tes;

  /* renamed from: u */
  public double[] f1621u = {
    -21.54d, -23.26d, -22.41d, -23.11d, -22.45d, -21.76d, -14.08d, -2.72d, 14.84d, 23.87d, 31.82d,
    31.42d, 29.79d, 26.64d, 22.92d, 11.2d
  };

  /* renamed from: v */
  public double[] f1622v = {
    -38.39d, -25.56d, -15.53d, -7.45d, 1.1d, 7.35d, 18.74d, 28.13d, 31.13d, 26.35d, 14.76d, 6.99d,
    0.1d, -9.38d, -18.65d, -24.61d
  };
  public double[] SpotsU = new double[16];
  public double[] SpotsV = new double[16];
  public int[] mInputNums = null;
  public int CVDMethod = 0;
  public int CVDType = 3;

  public CVDCalculator() {
    this.mColorTransferTable = null;
    this.mColorTransferTable = new ColorTransferTable();
  }

  public boolean setNum(int[] iArr) {
    this.mInputNums = iArr;
    return InitMakeUV();
  }

  public void calculate() {
    Calc();
  }

  public int getCVDType() {
    return this.CVDType;
  }

  public double getCVDSeverity() {
    return roundHalfUp(this.CVDSeverity);
  }

  public final boolean InitMakeUV() {
    this.SpotsU[0] = this.f1621u[0];
    this.SpotsV[0] = this.f1622v[0];
    int i = 0;
    while (i < 15) {
      try {
        int i2 = this.mInputNums[i];
        i++;
        this.SpotsU[i] = this.f1621u[i2];
        this.SpotsV[i] = this.f1622v[i2];
      } catch (ArrayIndexOutOfBoundsException unused) {
        return false;
      }
    }
    return true;
  }

  public final void Calc() {
    double[] dArr = new double[15];
    double[] dArr2 = new double[15];
    char c = 0;
    int i = 0;
    while (i < 15) {
      double[] dArr3 = this.SpotsU;
      int i2 = i + 1;
      dArr[i] = dArr3[i2] - dArr3[i];
      double[] dArr4 = this.SpotsV;
      dArr2[i] = dArr4[i2] - dArr4[i];
      i = i2;
    }
    int i3 = 0;
    double d = 0.0d;
    double d2 = 0.0d;
    for (int i4 = 15; i3 < i4; i4 = 15) {
      double d3 = dArr[i3];
      d += d3 * 2.0d * dArr2[i3];
      d2 += Math.pow(d3, 2.0d) - Math.pow(dArr2[i3], 2.0d);
      i3++;
      dArr = dArr;
    }
    double[] dArr5 = dArr;
    double atan = Math.atan(d / d2) / 2.0d;
    double[] dArr6 = {0.0d, 0.0d};
    double d4 = atan < 0.0d ? 1.5707963267948966d + atan : atan - 1.5707963267948966d;
    int i5 = 0;
    while (i5 < 15) {
      dArr6[c] =
          dArr6[c] + Math.pow((dArr2[i5] * Math.cos(atan)) - (dArr5[i5] * Math.sin(atan)), 2.0d);
      dArr6[1] = dArr6[1] + Math.pow((dArr2[i5] * Math.cos(d4)) - (dArr5[i5] * Math.sin(d4)), 2.0d);
      i5++;
      c = 0;
    }
    dArr6[c] = Math.sqrt(dArr6[c] / 15.0d);
    double sqrt = Math.sqrt(dArr6[1] / 15.0d);
    dArr6[1] = sqrt;
    double d5 = dArr6[c];
    if (d5 > sqrt) {
      this.majorRadius = d5;
      this.minorRadius = sqrt;
      this.majorAngle = (d4 * 180.0d) / 3.141592653589793d;
    } else {
      this.majorRadius = sqrt;
      this.minorRadius = d5;
      this.majorAngle = (atan * 180.0d) / 3.141592653589793d;
    }
    double d6 = this.majorRadius;
    this.c_index = d6 / 9.23705d;
    this.s_index = d6 / this.minorRadius;
    this.tes = Math.sqrt(Math.pow(d6, 2.0d) + Math.pow(this.minorRadius, 2.0d));
    double d7 = this.c_index;
    if (d7 <= 1.6d) {
      this.CVDType = 3;
    } else {
      double d8 = this.majorAngle;
      if (d8 >= 0.7d) {
        this.CVDType = 0;
      } else if (d8 < -65.0d) {
        this.CVDType = 2;
      } else {
        this.CVDType = 1;
      }
    }
    if (d7 < 1.6d) {
      d7 = 1.6d;
    }
    if (d7 > 4.2d) {
      d7 = 4.2d;
    }
    double d9 = (d7 - 1.6d) / 2.6d;
    this.CVDStrength = d9;
    this.CVDSeverity = d9 < 0.1d ? d9 * 5.0d : (((d9 - 0.1d) * 5.0d) / 9.0d) + 0.5d;
  }

  public int[] getRGBCMY(int i, int i2, double d, double d2) {
    int[] iArr = new int[12];
    Slog.d(
        "CVDCalculator",
        "getRGBCMY " + i + ", CVDType : " + i2 + ", Severity : " + d + ", user : " + d2);
    if (i == 0) {
      if (i2 == 0 || i2 == 1 || i2 == 2) {
        iArr[0] =
            (this.mColorTransferTable.getColorTransferValue_DMC(1, 1, i2, d, d2) * 256)
                + this.mColorTransferTable.getColorTransferValue_DMC(4, 1, i2, d, d2);
        iArr[1] =
            (this.mColorTransferTable.getColorTransferValue_DMC(1, 3, i2, d, d2) * 256)
                + this.mColorTransferTable.getColorTransferValue_DMC(4, 3, i2, d, d2);
        iArr[2] =
            (this.mColorTransferTable.getColorTransferValue_DMC(1, 5, i2, d, d2) * 256)
                + this.mColorTransferTable.getColorTransferValue_DMC(4, 5, i2, d, d2);
        iArr[3] =
            (this.mColorTransferTable.getColorTransferValue_DMC(3, 1, i2, d, d2) * 256)
                + this.mColorTransferTable.getColorTransferValue_DMC(6, 1, i2, d, d2);
        iArr[4] =
            (this.mColorTransferTable.getColorTransferValue_DMC(3, 3, i2, d, d2) * 256)
                + this.mColorTransferTable.getColorTransferValue_DMC(6, 3, i2, d, d2);
        iArr[5] =
            (this.mColorTransferTable.getColorTransferValue_DMC(3, 5, i2, d, d2) * 256)
                + this.mColorTransferTable.getColorTransferValue_DMC(6, 5, i2, d, d2);
        iArr[6] =
            (this.mColorTransferTable.getColorTransferValue_DMC(5, 1, i2, d, d2) * 256)
                + this.mColorTransferTable.getColorTransferValue_DMC(2, 1, i2, d, d2);
        iArr[7] =
            (this.mColorTransferTable.getColorTransferValue_DMC(5, 3, i2, d, d2) * 256)
                + this.mColorTransferTable.getColorTransferValue_DMC(2, 3, i2, d, d2);
        iArr[8] =
            (this.mColorTransferTable.getColorTransferValue_DMC(5, 5, i2, d, d2) * 256)
                + this.mColorTransferTable.getColorTransferValue_DMC(2, 5, i2, d, d2);
        iArr[9] = 255;
        iArr[10] = 255;
        iArr[11] = 255;
      } else {
        iArr[0] = 65280;
        iArr[1] = 255;
        iArr[2] = 255;
        iArr[3] = 255;
        iArr[4] = 65280;
        iArr[5] = 255;
        iArr[6] = 255;
        iArr[7] = 255;
        iArr[8] = 65280;
        iArr[9] = 255;
        iArr[10] = 255;
        iArr[11] = 255;
      }
    } else if (i != 1) {
      iArr[0] = 65280;
      iArr[1] = 255;
      iArr[2] = 255;
      iArr[3] = 255;
      iArr[4] = 65280;
      iArr[5] = 255;
      iArr[6] = 255;
      iArr[7] = 255;
      iArr[8] = 65280;
      iArr[9] = 255;
      iArr[10] = 255;
      iArr[11] = 255;
    } else if (i2 == 0 || i2 == 1 || i2 == 2) {
      iArr[0] =
          (this.mColorTransferTable.getColorTransferValue_Hybrid(1, 1, i2, d, d2) * 256)
              + this.mColorTransferTable.getColorTransferValue_Hybrid(4, 1, i2, d, d2);
      iArr[1] =
          (this.mColorTransferTable.getColorTransferValue_Hybrid(1, 3, i2, d, d2) * 256)
              + this.mColorTransferTable.getColorTransferValue_Hybrid(4, 3, i2, d, d2);
      iArr[2] =
          (this.mColorTransferTable.getColorTransferValue_Hybrid(1, 5, i2, d, d2) * 256)
              + this.mColorTransferTable.getColorTransferValue_Hybrid(4, 5, i2, d, d2);
      iArr[3] =
          (this.mColorTransferTable.getColorTransferValue_Hybrid(3, 1, i2, d, d2) * 256)
              + this.mColorTransferTable.getColorTransferValue_Hybrid(6, 1, i2, d, d2);
      iArr[4] =
          (this.mColorTransferTable.getColorTransferValue_Hybrid(3, 3, i2, d, d2) * 256)
              + this.mColorTransferTable.getColorTransferValue_Hybrid(6, 3, i2, d, d2);
      iArr[5] =
          (this.mColorTransferTable.getColorTransferValue_Hybrid(3, 5, i2, d, d2) * 256)
              + this.mColorTransferTable.getColorTransferValue_Hybrid(6, 5, i2, d, d2);
      iArr[6] =
          (this.mColorTransferTable.getColorTransferValue_Hybrid(5, 1, i2, d, d2) * 256)
              + this.mColorTransferTable.getColorTransferValue_Hybrid(2, 1, i2, d, d2);
      iArr[7] =
          (this.mColorTransferTable.getColorTransferValue_Hybrid(5, 3, i2, d, d2) * 256)
              + this.mColorTransferTable.getColorTransferValue_Hybrid(2, 3, i2, d, d2);
      iArr[8] =
          (this.mColorTransferTable.getColorTransferValue_Hybrid(5, 5, i2, d, d2) * 256)
              + this.mColorTransferTable.getColorTransferValue_Hybrid(2, 5, i2, d, d2);
      if (((int) Math.round(10.0d * d)) < 6) {
        iArr[9] = 204;
        iArr[10] = 204;
        iArr[11] = 204;
      } else {
        iArr[9] = 255;
        iArr[10] = 255;
        iArr[11] = 255;
      }
    } else {
      iArr[0] = 65280;
      iArr[1] = 255;
      iArr[2] = 255;
      iArr[3] = 255;
      iArr[4] = 65280;
      iArr[5] = 255;
      iArr[6] = 255;
      iArr[7] = 255;
      iArr[8] = 65280;
      iArr[9] = 255;
      iArr[10] = 255;
      iArr[11] = 255;
    }
    return iArr;
  }

  public double[] getPredefinedServerityAndUserParameter(int i, int i2) {
    double[] predefinedValueForEachType =
        this.mColorTransferTable.getPredefinedValueForEachType(i2, i);
    if (predefinedValueForEachType != null) {
      return predefinedValueForEachType;
    }
    return null;
  }

  public final double roundHalfUp(double d) {
    return ((int) Math.round(d * 10.0d)) / 10.0d;
  }
}
