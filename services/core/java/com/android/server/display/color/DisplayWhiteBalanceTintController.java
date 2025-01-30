package com.android.server.display.color;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.DisplayManagerInternal;
import android.opengl.Matrix;
import android.util.Slog;
import android.view.SurfaceControl;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public final class DisplayWhiteBalanceTintController extends ColorTemperatureTintController {
  public int mAppliedCct;
  public CctEvaluator mCctEvaluator;
  public float[] mChromaticAdaptationMatrix;
  int mCurrentColorTemperature;
  public float[] mCurrentColorTemperatureXYZ;
  ColorSpace.Rgb mDisplayColorSpaceRGB;
  public final DisplayManagerInternal mDisplayManagerInternal;
  public int mDisplayNominalWhiteCct;
  public Boolean mIsAvailable;
  public int mTargetCct;
  public int mTemperatureDefault;
  int mTemperatureMax;
  int mTemperatureMin;
  public long mTransitionDuration;
  public final Object mLock = new Object();
  float[] mDisplayNominalWhiteXYZ = new float[3];
  boolean mSetUp = false;
  public final float[] mMatrixDisplayWhiteBalance = new float[16];
  public boolean mIsAllowed = true;

  @Override // com.android.server.display.color.TintController
  public int getLevel() {
    return 125;
  }

  public DisplayWhiteBalanceTintController(DisplayManagerInternal displayManagerInternal) {
    this.mDisplayManagerInternal = displayManagerInternal;
  }

  public void setUp(Context context, boolean z) {
    this.mSetUp = false;
    Resources resources = context.getResources();
    setAllowed(resources.getBoolean(R.bool.config_defaultEmergencyGestureEnabled));
    ColorSpace.Rgb displayColorSpaceFromSurfaceControl = getDisplayColorSpaceFromSurfaceControl();
    if (displayColorSpaceFromSurfaceControl == null) {
      Slog.w(
          "ColorDisplayService",
          "Failed to get display color space from SurfaceControl, trying res");
      displayColorSpaceFromSurfaceControl = getDisplayColorSpaceFromResources(resources);
      if (displayColorSpaceFromSurfaceControl == null) {
        Slog.e("ColorDisplayService", "Failed to get display color space from resources");
        return;
      }
    }
    if (!isColorMatrixValid(displayColorSpaceFromSurfaceControl.getTransform())) {
      Slog.e("ColorDisplayService", "Invalid display color space RGB-to-XYZ transform");
      return;
    }
    if (!isColorMatrixValid(displayColorSpaceFromSurfaceControl.getInverseTransform())) {
      Slog.e("ColorDisplayService", "Invalid display color space XYZ-to-RGB transform");
      return;
    }
    String[] stringArray = resources.getStringArray(R.array.config_udfps_touch_detection_options);
    float[] fArr = new float[3];
    for (int i = 0; i < stringArray.length; i++) {
      fArr[i] = Float.parseFloat(stringArray[i]);
    }
    int integer = resources.getInteger(R.integer.config_dreamsBatteryLevelMinimumWhenPowered);
    int integer2 = resources.getInteger(R.integer.config_dreamOverlayReconnectTimeoutMs);
    if (integer2 <= 0) {
      Slog.e(
          "ColorDisplayService",
          "Display white balance minimum temperature must be greater than 0");
      return;
    }
    int integer3 = resources.getInteger(R.integer.config_dreamOverlayMaxReconnectAttempts);
    if (integer3 < integer2) {
      Slog.e(
          "ColorDisplayService", "Display white balance max temp must be greater or equal to min");
      return;
    }
    int integer4 = resources.getInteger(R.integer.config_dreamCloseAnimationDuration);
    this.mTransitionDuration =
        resources.getInteger(R.integer.config_dynamicPowerSavingsDefaultDisableThreshold);
    int[] intArray = resources.getIntArray(R.array.config_virtualKeyVibePattern);
    int[] intArray2 = resources.getIntArray(R.array.config_vvmSmsFilterRegexes);
    synchronized (this.mLock) {
      this.mDisplayColorSpaceRGB = displayColorSpaceFromSurfaceControl;
      this.mDisplayNominalWhiteXYZ = fArr;
      this.mDisplayNominalWhiteCct = integer;
      this.mTargetCct = integer;
      this.mAppliedCct = integer;
      this.mTemperatureMin = integer2;
      this.mTemperatureMax = integer3;
      this.mTemperatureDefault = integer4;
      this.mSetUp = true;
      this.mCctEvaluator = new CctEvaluator(integer2, integer3, intArray, intArray2);
    }
    setMatrix(this.mTemperatureDefault);
  }

  @Override // com.android.server.display.color.TintController
  public float[] getMatrix() {
    if (!this.mSetUp || !isActivated()) {
      return ColorDisplayService.MATRIX_IDENTITY;
    }
    computeMatrixForCct(this.mAppliedCct);
    return this.mMatrixDisplayWhiteBalance;
  }

  @Override // com.android.server.display.color.ColorTemperatureTintController
  public int getTargetCct() {
    return this.mTargetCct;
  }

  public static float[] mul3x3(float[] fArr, float[] fArr2) {
    float f = fArr[0] * fArr2[0];
    float f2 = fArr[3];
    float f3 = fArr2[1];
    float f4 = fArr[6];
    float f5 = fArr2[2];
    float f6 = fArr[1];
    float f7 = fArr2[0];
    float f8 = fArr[4];
    float f9 = fArr[7];
    float f10 = fArr[2] * f7;
    float f11 = fArr[5];
    float f12 = f10 + (fArr2[1] * f11);
    float f13 = fArr[8];
    float f14 = fArr[0];
    float f15 = fArr2[3] * f14;
    float f16 = fArr2[4];
    float f17 = f15 + (f2 * f16);
    float f18 = fArr2[5];
    float f19 = fArr[1];
    float f20 = fArr2[3];
    float f21 = fArr[2];
    float f22 = f14 * fArr2[6];
    float f23 = fArr[3];
    float f24 = fArr2[7];
    float f25 = f22 + (f23 * f24);
    float f26 = fArr2[8];
    float f27 = fArr2[6];
    return new float[] {
      f + (f2 * f3) + (f4 * f5),
      (f6 * f7) + (f3 * f8) + (f9 * f5),
      f12 + (f5 * f13),
      f17 + (f4 * f18),
      (f19 * f20) + (f8 * f16) + (f9 * f18),
      (f20 * f21) + (f11 * fArr2[4]) + (f18 * f13),
      f25 + (f4 * f26),
      (f19 * f27) + (fArr[4] * f24) + (f9 * f26),
      (f21 * f27) + (fArr[5] * fArr2[7]) + (f13 * f26)
    };
  }

  @Override // com.android.server.display.color.TintController
  public void setMatrix(int i) {
    setTargetCct(i);
    computeMatrixForCct(this.mTargetCct);
  }

  public void setTargetCct(int i) {
    if (!this.mSetUp) {
      Slog.w("ColorDisplayService", "Can't set display white balance temperature: uninitialized");
      return;
    }
    if (i < this.mTemperatureMin) {
      Slog.w("ColorDisplayService", "Requested display color temperature is below allowed minimum");
      this.mTargetCct = this.mTemperatureMin;
    } else if (i > this.mTemperatureMax) {
      Slog.w("ColorDisplayService", "Requested display color temperature is above allowed maximum");
      this.mTargetCct = this.mTemperatureMax;
    } else {
      this.mTargetCct = i;
    }
  }

  @Override // com.android.server.display.color.ColorTemperatureTintController
  public int getDisabledCct() {
    return this.mDisplayNominalWhiteCct;
  }

  @Override // com.android.server.display.color.ColorTemperatureTintController
  public float[] computeMatrixForCct(int i) {
    float[] fArr;
    if (!this.mSetUp || i == 0) {
      Slog.w("ColorDisplayService", "Couldn't compute matrix for cct=" + i);
      return ColorDisplayService.MATRIX_IDENTITY;
    }
    synchronized (this.mLock) {
      this.mCurrentColorTemperature = i;
      if (i == this.mDisplayNominalWhiteCct && !isActivated()) {
        Matrix.setIdentityM(this.mMatrixDisplayWhiteBalance, 0);
      } else {
        computeMatrixForCctLocked(i);
      }
      Slog.d(
          "ColorDisplayService",
          "computeDisplayWhiteBalanceMatrix: cct ="
              + i
              + " matrix ="
              + TintController.matrixToString(this.mMatrixDisplayWhiteBalance, 16));
      fArr = this.mMatrixDisplayWhiteBalance;
    }
    return fArr;
  }

  public final void computeMatrixForCctLocked(int i) {
    float[] cctToXyz = ColorSpace.cctToXyz(i);
    this.mCurrentColorTemperatureXYZ = cctToXyz;
    float[] chromaticAdaptation =
        ColorSpace.chromaticAdaptation(
            ColorSpace.Adaptation.BRADFORD, this.mDisplayNominalWhiteXYZ, cctToXyz);
    this.mChromaticAdaptationMatrix = chromaticAdaptation;
    float[] mul3x3 =
        mul3x3(
            this.mDisplayColorSpaceRGB.getInverseTransform(),
            mul3x3(chromaticAdaptation, this.mDisplayColorSpaceRGB.getTransform()));
    float max =
        Math.max(
            Math.max(mul3x3[0] + mul3x3[3] + mul3x3[6], mul3x3[1] + mul3x3[4] + mul3x3[7]),
            mul3x3[2] + mul3x3[5] + mul3x3[8]);
    Matrix.setIdentityM(this.mMatrixDisplayWhiteBalance, 0);
    for (int i2 = 0; i2 < mul3x3.length; i2++) {
      float f = mul3x3[i2] / max;
      mul3x3[i2] = f;
      if (!isColorMatrixCoeffValid(f)) {
        Slog.e("ColorDisplayService", "Invalid DWB color matrix");
        return;
      }
    }
    System.arraycopy(mul3x3, 0, this.mMatrixDisplayWhiteBalance, 0, 3);
    System.arraycopy(mul3x3, 3, this.mMatrixDisplayWhiteBalance, 4, 3);
    System.arraycopy(mul3x3, 6, this.mMatrixDisplayWhiteBalance, 8, 3);
  }

  @Override // com.android.server.display.color.ColorTemperatureTintController
  public int getAppliedCct() {
    return this.mAppliedCct;
  }

  @Override // com.android.server.display.color.ColorTemperatureTintController
  public void setAppliedCct(int i) {
    this.mAppliedCct = i;
  }

  @Override // com.android.server.display.color.ColorTemperatureTintController
  public CctEvaluator getEvaluator() {
    return this.mCctEvaluator;
  }

  @Override // com.android.server.display.color.TintController
  public boolean isAvailable(Context context) {
    if (this.mIsAvailable == null) {
      this.mIsAvailable =
          Boolean.valueOf(ColorDisplayManager.isDisplayWhiteBalanceAvailable(context));
    }
    return this.mIsAvailable.booleanValue();
  }

  @Override // com.android.server.display.color.TintController
  public long getTransitionDurationMilliseconds() {
    return this.mTransitionDuration;
  }

  public void dump(PrintWriter printWriter) {
    synchronized (this.mLock) {
      printWriter.println("    mSetUp = " + this.mSetUp);
      if (this.mSetUp) {
        printWriter.println("    mTemperatureMin = " + this.mTemperatureMin);
        printWriter.println("    mTemperatureMax = " + this.mTemperatureMax);
        printWriter.println("    mTemperatureDefault = " + this.mTemperatureDefault);
        printWriter.println("    mDisplayNominalWhiteCct = " + this.mDisplayNominalWhiteCct);
        printWriter.println("    mCurrentColorTemperature = " + this.mCurrentColorTemperature);
        printWriter.println("    mTargetCct = " + this.mTargetCct);
        printWriter.println("    mAppliedCct = " + this.mAppliedCct);
        printWriter.println(
            "    mCurrentColorTemperatureXYZ = "
                + TintController.matrixToString(this.mCurrentColorTemperatureXYZ, 3));
        printWriter.println(
            "    mDisplayColorSpaceRGB RGB-to-XYZ = "
                + TintController.matrixToString(this.mDisplayColorSpaceRGB.getTransform(), 3));
        printWriter.println(
            "    mChromaticAdaptationMatrix = "
                + TintController.matrixToString(this.mChromaticAdaptationMatrix, 3));
        printWriter.println(
            "    mDisplayColorSpaceRGB XYZ-to-RGB = "
                + TintController.matrixToString(
                    this.mDisplayColorSpaceRGB.getInverseTransform(), 3));
        printWriter.println(
            "    mMatrixDisplayWhiteBalance = "
                + TintController.matrixToString(this.mMatrixDisplayWhiteBalance, 4));
        printWriter.println("    mIsAllowed = " + this.mIsAllowed);
      }
    }
  }

  public void setAllowed(boolean z) {
    this.mIsAllowed = z;
  }

  public boolean isAllowed() {
    return this.mIsAllowed;
  }

  public final ColorSpace.Rgb makeRgbColorSpaceFromXYZ(float[] fArr, float[] fArr2) {
    return new ColorSpace.Rgb("Display Color Space", fArr, fArr2, 2.200000047683716d);
  }

  public final ColorSpace.Rgb getDisplayColorSpaceFromSurfaceControl() {
    SurfaceControl.CieXyz cieXyz;
    SurfaceControl.CieXyz cieXyz2;
    SurfaceControl.CieXyz cieXyz3;
    SurfaceControl.CieXyz cieXyz4;
    SurfaceControl.DisplayPrimaries displayNativePrimaries =
        this.mDisplayManagerInternal.getDisplayNativePrimaries(0);
    if (displayNativePrimaries == null
        || (cieXyz = displayNativePrimaries.red) == null
        || (cieXyz2 = displayNativePrimaries.green) == null
        || (cieXyz3 = displayNativePrimaries.blue) == null
        || (cieXyz4 = displayNativePrimaries.white) == null) {
      return null;
    }
    return makeRgbColorSpaceFromXYZ(
        new float[] {
          cieXyz.X, cieXyz.Y, cieXyz.Z, cieXyz2.X, cieXyz2.Y, cieXyz2.Z, cieXyz3.X, cieXyz3.Y,
          cieXyz3.Z
        },
        new float[] {cieXyz4.X, cieXyz4.Y, cieXyz4.Z});
  }

  public final ColorSpace.Rgb getDisplayColorSpaceFromResources(Resources resources) {
    String[] stringArray = resources.getStringArray(R.array.config_usbHostDenylist);
    float[] fArr = new float[9];
    float[] fArr2 = new float[3];
    for (int i = 0; i < 9; i++) {
      fArr[i] = Float.parseFloat(stringArray[i]);
    }
    for (int i2 = 0; i2 < 3; i2++) {
      fArr2[i2] = Float.parseFloat(stringArray[9 + i2]);
    }
    return makeRgbColorSpaceFromXYZ(fArr, fArr2);
  }

  public final boolean isColorMatrixCoeffValid(float f) {
    return (Float.isNaN(f) || Float.isInfinite(f)) ? false : true;
  }

  public final boolean isColorMatrixValid(float[] fArr) {
    if (fArr == null || fArr.length != 9) {
      return false;
    }
    for (float f : fArr) {
      if (!isColorMatrixCoeffValid(f)) {
        return false;
      }
    }
    return true;
  }
}
