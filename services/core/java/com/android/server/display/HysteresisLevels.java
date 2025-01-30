package com.android.server.display;

import android.util.Spline;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class HysteresisLevels {
  public Spline mHysteresisBrightSpline;
  public Spline mHysteresisDarkSpline;

  public HysteresisLevels(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4) {
    if (iArr.length != iArr2.length || iArr3.length != iArr4.length) {
      throw new IllegalArgumentException("Mismatch between hysteresis array lengths.");
    }
    float[] convertArrayFromIntToFloat = convertArrayFromIntToFloat(iArr);
    float[] convertArrayFromIntToFloat2 = convertArrayFromIntToFloat(iArr2);
    float[] convertArrayFromIntToFloat3 = convertArrayFromIntToFloat(iArr3);
    float[] convertArrayFromIntToFloat4 = convertArrayFromIntToFloat(iArr4);
    this.mHysteresisBrightSpline =
        Spline.createSpline(convertArrayFromIntToFloat, convertArrayFromIntToFloat2);
    this.mHysteresisDarkSpline =
        Spline.createSpline(convertArrayFromIntToFloat3, convertArrayFromIntToFloat4);
  }

  public final float[] convertArrayFromIntToFloat(int[] iArr) {
    float[] fArr = new float[iArr.length];
    for (int i = 0; i < iArr.length; i++) {
      fArr[i] = iArr[i];
    }
    return fArr;
  }

  public float getBrighteningThreshold(float f) {
    return Math.round(this.mHysteresisBrightSpline.interpolate(f));
  }

  public float getDarkeningThreshold(float f) {
    if (this.mHysteresisDarkSpline.interpolate(f) >= DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      return Math.round(r0);
    }
    return -1.0f;
  }

  public void dump(PrintWriter printWriter) {
    printWriter.println("HysteresisLevels");
    printWriter.println("  mHysteresisBrightSpline=" + this.mHysteresisBrightSpline);
    printWriter.println("  mHysteresisDarkSpline=" + this.mHysteresisDarkSpline);
  }
}
