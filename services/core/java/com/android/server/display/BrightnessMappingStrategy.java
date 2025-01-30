package com.android.server.display;

import android.R;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.hardware.display.BrightnessConfiguration;
import android.hardware.display.BrightnessCorrection;
import android.util.MathUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.Spline;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.util.Preconditions;
import com.android.server.display.utils.Plog;
import com.android.server.display.whitebalance.DisplayWhiteBalanceController;
import com.android.server.power.PowerManagerUtil;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
public abstract class BrightnessMappingStrategy {
  public static int sScreenExtendedBrightnessRangeMaximum;
  public static final Plog PLOG = Plog.createSystemPlog("BrightnessMappingStrategy");
  public static float LUX_NO_PERCEPTIBLE = 3.0f;
  public boolean mLoggingEnabled;
  public UserOffsetManager mUserOffsetManager = new UserOffsetManager(this.mLoggingEnabled);
  public final int MIN_SHORT_TERM_MODEL_THRESHOLD = 30;

  public abstract void addUserDataPoint(float f, float f2);

  public abstract void clearUserDataPoints();

  public abstract float convertToAdjustedNits(float f);

  public abstract int convertToBrightness(float f);

  public abstract float convertToFloatScale(float f);

  public abstract float convertToNits(float f);

  public abstract void dump(PrintWriter printWriter, float f);

  public abstract BrightnessConfiguration getAppliedBackupConfig(
      BrightnessConfiguration brightnessConfiguration);

  public abstract float getAutoBrightnessAdjustment();

  public abstract float getBrightness(float f, String str, int i);

  public abstract BrightnessConfiguration getBrightnessConfiguration();

  public abstract float getBrightnessForSpline(float f, Spline spline);

  public abstract Spline getBrightnessSpline();

  public abstract BrightnessConfiguration getDefaultConfig();

  public abstract long getShortTermModelTimeout();

  public abstract float getUserBrightness();

  public abstract float getUserLux();

  public abstract boolean hasUserDataPoints();

  public abstract boolean isDefaultConfig();

  public abstract boolean isForIdleMode();

  public abstract void recalculateSplines(boolean z, float[] fArr);

  public abstract boolean setAutoBrightnessAdjustment(float f);

  public abstract boolean setBrightnessConfiguration(
      BrightnessConfiguration brightnessConfiguration);

  public static BrightnessMappingStrategy create(
      Resources resources,
      DisplayDeviceConfig displayDeviceConfig,
      DisplayWhiteBalanceController displayWhiteBalanceController) {
    return create(resources, displayDeviceConfig, false, displayWhiteBalanceController);
  }

  public static BrightnessMappingStrategy createForIdleMode(
      Resources resources,
      DisplayDeviceConfig displayDeviceConfig,
      DisplayWhiteBalanceController displayWhiteBalanceController) {
    return create(resources, displayDeviceConfig, true, displayWhiteBalanceController);
  }

  public static BrightnessMappingStrategy create(
      Resources resources,
      DisplayDeviceConfig displayDeviceConfig,
      boolean z,
      DisplayWhiteBalanceController displayWhiteBalanceController) {
    float[] autoBrightnessBrighteningLevelsNits;
    float[] autoBrightnessBrighteningLevelsLux;
    if (z) {
      autoBrightnessBrighteningLevelsNits =
          getFloatArray(resources.obtainTypedArray(R.array.config_networkSupportedKeepaliveCount));
      autoBrightnessBrighteningLevelsLux =
          getLuxLevels(resources.getIntArray(R.array.config_notificationFallbackVibeWaveform));
    } else {
      autoBrightnessBrighteningLevelsNits =
          displayDeviceConfig.getAutoBrightnessBrighteningLevelsNits();
      autoBrightnessBrighteningLevelsLux =
          displayDeviceConfig.getAutoBrightnessBrighteningLevelsLux();
    }
    float[] fArr = autoBrightnessBrighteningLevelsLux;
    int[] intArray =
        resources.getIntArray(R.array.config_nightDisplayColorTemperatureCoefficientsNative);
    float fraction =
        resources.getFraction(R.fraction.config_autoBrightnessAdjustmentMaxGamma, 1, 1);
    float[] nits = displayDeviceConfig.getNits();
    float[] brightness = displayDeviceConfig.getBrightness();
    sScreenExtendedBrightnessRangeMaximum =
        resources.getInteger(R.integer.config_screenTimeoutOverride);
    if (isValidMapping(nits, brightness)
        && isValidMapping(fArr, autoBrightnessBrighteningLevelsNits)) {
      Slog.d("BrightnessMappingStrategy", "Use PhysicalMappingStrategy");
      if (PowerManagerUtil.SEC_FEATURE_SUPPORT_LEGACY_PERFORMANCE_MODE) {
        for (int i = 0;
            i < autoBrightnessBrighteningLevelsNits.length
                && (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
                    || fArr[i] < PowerManagerUtil.HBM_LUX);
            i++) {
          autoBrightnessBrighteningLevelsNits[i] = autoBrightnessBrighteningLevelsNits[i] * 0.885f;
        }
      }
      BrightnessConfiguration.Builder builder =
          new BrightnessConfiguration.Builder(fArr, autoBrightnessBrighteningLevelsNits);
      builder.setShortTermModelTimeoutMillis(600000L);
      builder.setShortTermModelLowerLuxMultiplier(0.6f);
      builder.setShortTermModelUpperLuxMultiplier(0.6f);
      return new PhysicalMappingStrategy(
          builder.build(), nits, brightness, fraction, z, displayWhiteBalanceController);
    }
    if (!isValidMapping(fArr, intArray) || z) {
      return null;
    }
    Slog.d("BrightnessMappingStrategy", "Use SimpleMappingStrategy");
    return new SimpleMappingStrategy(fArr, intArray, fraction, 600000L);
  }

  public static float[] getLuxLevels(int[] iArr) {
    float[] fArr = new float[iArr.length + 1];
    int i = 0;
    while (i < iArr.length) {
      int i2 = i + 1;
      fArr[i2] = iArr[i];
      i = i2;
    }
    return fArr;
  }

  public static float[] getFloatArray(TypedArray typedArray) {
    int length = typedArray.length();
    float[] fArr = new float[length];
    for (int i = 0; i < length; i++) {
      fArr[i] = typedArray.getFloat(i, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
    }
    typedArray.recycle();
    return fArr;
  }

  public static boolean isValidMapping(float[] fArr, float[] fArr2) {
    if (fArr == null || fArr2 == null || fArr.length == 0 || fArr2.length == 0) {
      StringBuilder sb = new StringBuilder();
      sb.append("x: ");
      sb.append(fArr == null ? "null" : Integer.valueOf(fArr.length));
      sb.append(" y: ");
      sb.append(fArr2 != null ? Integer.valueOf(fArr2.length) : "null");
      Slog.d("BrightnessMappingStrategy", sb.toString());
      return false;
    }
    if (fArr.length != fArr2.length) {
      Slog.d(
          "BrightnessMappingStrategy", "x.length: " + fArr.length + " y.length: " + fArr2.length);
      return false;
    }
    int length = fArr.length;
    float f = fArr[0];
    float f2 = fArr2[0];
    if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON
        || f2 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON
        || Float.isNaN(f)
        || Float.isNaN(f2)) {
      Slog.d("BrightnessMappingStrategy", "prevX: " + f + " prevY: " + f2);
      return false;
    }
    for (int i = 1; i < length; i++) {
      float f3 = fArr[i];
      if (f >= f3 || f2 > fArr2[i]) {
        Slog.d(
            "BrightnessMappingStrategy",
            "prevX: "
                + f
                + " x["
                + i
                + "]: "
                + fArr[i]
                + " prevY: "
                + f2
                + " y["
                + i
                + "]: "
                + fArr2[i]);
        return false;
      }
      if (Float.isNaN(f3) || Float.isNaN(fArr2[i])) {
        Slog.d(
            "BrightnessMappingStrategy", "x[" + i + "]: " + fArr[i] + " y[" + i + "]: " + fArr2[i]);
        return false;
      }
      f = fArr[i];
      f2 = fArr2[i];
    }
    return true;
  }

  public static boolean isValidMapping(float[] fArr, int[] iArr) {
    if (fArr == null || iArr == null || fArr.length == 0 || iArr.length == 0) {
      StringBuilder sb = new StringBuilder();
      sb.append("x: ");
      sb.append(fArr == null ? "null" : Integer.valueOf(fArr.length));
      sb.append(" y: ");
      sb.append(iArr != null ? Integer.valueOf(iArr.length) : "null");
      Slog.d("BrightnessMappingStrategy", sb.toString());
      return false;
    }
    if (fArr.length != iArr.length) {
      Slog.d("BrightnessMappingStrategy", "x.length: " + fArr.length + " y.length: " + iArr.length);
      return false;
    }
    int length = fArr.length;
    float f = fArr[0];
    int i = iArr[0];
    if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || i < 0 || Float.isNaN(f)) {
      Slog.d("BrightnessMappingStrategy", "prevX: " + f + " prevY: " + i);
      return false;
    }
    for (int i2 = 1; i2 < length; i2++) {
      float f2 = fArr[i2];
      if (f >= f2 || i > iArr[i2]) {
        Slog.d(
            "BrightnessMappingStrategy",
            "prevX: "
                + f
                + " x["
                + i2
                + "]: "
                + fArr[i2]
                + " prevY: "
                + i
                + " y["
                + i2
                + "]: "
                + iArr[i2]);
        return false;
      }
      if (Float.isNaN(f2)) {
        Slog.d("BrightnessMappingStrategy", "x[" + i2 + "]: " + fArr[i2]);
        return false;
      }
      f = fArr[i2];
      i = iArr[i2];
    }
    return true;
  }

  public boolean setLoggingEnabled(boolean z) {
    if (this.mLoggingEnabled == z) {
      return false;
    }
    this.mLoggingEnabled = z;
    UserOffsetManager.setLoggingEnabled(z);
    return true;
  }

  public float getBrightness(float f) {
    return getBrightness(f, null, -1);
  }

  public boolean shouldResetShortTermModel(float f, float f2) {
    BrightnessConfiguration brightnessConfiguration = getBrightnessConfiguration();
    if (brightnessConfiguration != null) {
      if (!Float.isNaN(brightnessConfiguration.getShortTermModelLowerLuxMultiplier())) {
        brightnessConfiguration.getShortTermModelLowerLuxMultiplier();
      }
      if (!Float.isNaN(brightnessConfiguration.getShortTermModelUpperLuxMultiplier())) {
        brightnessConfiguration.getShortTermModelUpperLuxMultiplier();
      }
    }
    float max = Math.max(30.0f, 0.6f * f2);
    float f3 = f2 - max;
    float f4 = f2 + max;
    if (f3 < f && f <= f4) {
      if (!this.mLoggingEnabled) {
        return false;
      }
      Slog.d(
          "BrightnessMappingStrategy",
          "ShortTermModel: re-validate user data, ambient lux is " + f3 + " < " + f + " < " + f4);
      return false;
    }
    Slog.d(
        "BrightnessMappingStrategy",
        "ShortTermModel: reset data, ambient lux is " + f + "(" + f3 + ", " + f4 + ")");
    return true;
  }

  public static float normalizeAbsoluteBrightness(int i) {
    return BrightnessSynchronizer.brightnessIntToFloat(i);
  }

  public Pair getOffsetAdjustedCurve(float[] fArr, float[] fArr2) {
    float[] copyOf = Arrays.copyOf(fArr2, fArr2.length);
    if (this.mLoggingEnabled) {
      PLOG.logCurve("curve before adjust offset", fArr, copyOf);
    }
    if (this.mUserOffsetManager.hasUserDataPoints()) {
      Spline createSpline =
          Spline.createSpline(
              Arrays.copyOf(fArr, fArr.length), Arrays.copyOf(copyOf, copyOf.length));
      float[] plusOffsetBrightness = plusOffsetBrightness(fArr, copyOf);
      if (this.mLoggingEnabled) {
        PLOG.logCurve("curve after offset plus", fArr, plusOffsetBrightness);
      }
      Pair insertOffsetPoints = insertOffsetPoints(fArr, plusOffsetBrightness, createSpline);
      float[] fArr3 = (float[]) insertOffsetPoints.first;
      float[] fArr4 = (float[]) insertOffsetPoints.second;
      if (this.mLoggingEnabled) {
        PLOG.logCurve("curve after insert user point", fArr3, fArr4);
      }
      copyOf = fArr4;
      fArr = fArr3;
    }
    return Pair.create(fArr, copyOf);
  }

  public float[] plusOffsetBrightness(float[] fArr, float[] fArr2) {
    float[] copyOf = Arrays.copyOf(fArr2, fArr2.length);
    Pair curve = this.mUserOffsetManager.getCurve();
    if (curve != null) {
      Spline createLinearSpline =
          Spline.createLinearSpline((float[]) curve.first, (float[]) curve.second);
      for (int i = 0; i < copyOf.length; i++) {
        float f = copyOf[i];
        if (f > 1.0f) {
          break;
        }
        float interpolate = f + createLinearSpline.interpolate(fArr[i]);
        copyOf[i] = interpolate;
        if (interpolate > 1.0f) {
          copyOf[i] = 1.0f;
        }
      }
    }
    return copyOf;
  }

  public Pair insertOffsetPoints(float[] fArr, float[] fArr2, Spline spline) {
    Pair create = Pair.create(fArr, fArr2);
    Pair curve = this.mUserOffsetManager.getCurve();
    if (curve != null) {
      float[] fArr3 = (float[]) curve.first;
      float[] fArr4 = (float[]) curve.second;
      for (int i = 0; i < fArr3.length; i++) {
        float f = fArr3[i];
        if (DisplayPowerController2.RATE_FROM_DOZE_TO_ON <= f
            && (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM || f < PowerManagerUtil.HBM_LUX)) {
          create =
              insertControlPoint(
                  (float[]) create.first,
                  (float[]) create.second,
                  f,
                  spline.interpolate(f) + fArr4[i]);
        }
      }
      smoothCurve(
          (float[]) create.first,
          (float[]) create.second,
          findInsertionPoint((float[]) create.first, this.mUserOffsetManager.getLastAddedLux()));
    }
    return create;
  }

  public final Pair insertControlPoint(float[] fArr, float[] fArr2, float f, float f2) {
    float[] fArr3;
    float[] fArr4;
    int findInsertionPoint = findInsertionPoint(fArr, f);
    if (findInsertionPoint == fArr.length) {
      fArr4 = Arrays.copyOf(fArr, fArr.length + 1);
      fArr3 = Arrays.copyOf(fArr2, fArr2.length + 1);
      fArr4[findInsertionPoint] = f;
      fArr3[findInsertionPoint] = f2;
    } else if (fArr[findInsertionPoint] == f) {
      fArr4 = Arrays.copyOf(fArr, fArr.length);
      fArr3 = Arrays.copyOf(fArr2, fArr2.length);
      fArr3[findInsertionPoint] = f2;
    } else {
      float[] copyOf = Arrays.copyOf(fArr, fArr.length + 1);
      int i = findInsertionPoint + 1;
      System.arraycopy(copyOf, findInsertionPoint, copyOf, i, fArr.length - findInsertionPoint);
      copyOf[findInsertionPoint] = f;
      float[] copyOf2 = Arrays.copyOf(fArr2, fArr2.length + 1);
      System.arraycopy(copyOf2, findInsertionPoint, copyOf2, i, fArr2.length - findInsertionPoint);
      copyOf2[findInsertionPoint] = f2;
      fArr3 = copyOf2;
      fArr4 = copyOf;
    }
    return Pair.create(fArr4, fArr3);
  }

  public final int findInsertionPoint(float[] fArr, float f) {
    for (int i = 0; i < fArr.length; i++) {
      if (f <= fArr[i]) {
        return i;
      }
    }
    return fArr.length;
  }

  public final void smoothCurve(float[] fArr, float[] fArr2, int i) {
    if (this.mLoggingEnabled) {
      PLOG.logCurve("unsmoothed curve", fArr, fArr2);
    }
    float f = fArr[i];
    float f2 = fArr2[i];
    int i2 = i + 1;
    while (i2 < fArr.length) {
      float f3 = fArr[i2];
      float f4 = fArr2[i2];
      if (PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM && f3 >= PowerManagerUtil.HBM_LUX) {
        break;
      }
      float max = Math.max(f4, f2 * permissibleMinimumRatio(f3, f));
      f2 = 1.0f;
      if (max <= 1.0f) {
        f2 = max;
      }
      fArr2[i2] = f2;
      i2++;
      f = f3;
    }
    float f5 = fArr[i];
    float f6 = fArr2[i];
    int i3 = i - 1;
    while (i3 >= 0) {
      float f7 = fArr[i3];
      float f8 = fArr2[i3];
      if (f7 < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        break;
      }
      f6 = Math.min(f8, f6 * permissibleMinimumRatio(f7, f5));
      fArr2[i3] = f6;
      i3--;
      f5 = f7;
    }
    if (this.mLoggingEnabled) {
      PLOG.logCurve("smoothed curve", fArr, fArr2);
    }
  }

  public static float permissibleRatio(float f, float f2) {
    return MathUtils.pow((f + 0.25f) / (f2 + 0.25f), 1.0f);
  }

  public static float permissibleMinimumRatio(float f, float f2) {
    float f3 = LUX_NO_PERCEPTIBLE;
    if (f >= f3 || f2 >= f3) {
      return MathUtils.exp((MathUtils.log(f + 0.25f) - MathUtils.log(f2 + 0.25f)) * 0.05f);
    }
    return 1.0f;
  }

  public static boolean isValidUserLux(float f) {
    return DisplayPowerController2.RATE_FROM_DOZE_TO_ON > f
        || !PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
        || f < ((float) PowerManagerUtil.HBM_LUX);
  }

  public float inferAutoBrightnessAdjustment(float f, float f2, float f3) {
    float f4;
    float f5 = Float.NaN;
    if (f3 <= 0.1f || f3 >= 0.9f) {
      f4 = f2 - f3;
    } else if (f2 == DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
      f4 = -1.0f;
    } else if (f2 == 1.0f) {
      f4 = 1.0f;
    } else {
      f5 = MathUtils.log(f2) / MathUtils.log(f3);
      f4 = (-MathUtils.log(f5)) / MathUtils.log(f);
    }
    float constrain = MathUtils.constrain(f4, -1.0f, 1.0f);
    if (this.mLoggingEnabled) {
      StringBuilder sb = new StringBuilder();
      sb.append("inferAutoBrightnessAdjustment: ");
      sb.append(f);
      sb.append("^");
      float f6 = -constrain;
      sb.append(f6);
      sb.append("=");
      sb.append(MathUtils.pow(f, f6));
      sb.append(" == ");
      sb.append(f5);
      Slog.d("BrightnessMappingStrategy", sb.toString());
      Slog.d(
          "BrightnessMappingStrategy",
          "inferAutoBrightnessAdjustment: "
              + f3
              + "^"
              + f5
              + "="
              + MathUtils.pow(f3, f5)
              + " == "
              + f2);
    }
    return constrain;
  }

  public class UserOffsetManager {
    public static boolean sDebugLogging;
    public Pair mCurve;
    public float mLastAddedLux;
    public ArrayList mUserPoints;

    public UserOffsetManager(boolean z) {
      synchronized (this) {
        this.mCurve = null;
        this.mUserPoints = new ArrayList();
      }
    }

    public void addPoint(float f, float f2, float f3) {
      synchronized (this) {
        UserPoint userPoint = new UserPoint(f, f2, f3);
        removeRedundantUserPoint(userPoint);
        this.mUserPoints.add(userPoint);
        this.mLastAddedLux = f;
        for (int i = 0; i < this.mUserPoints.size(); i++) {
          Slog.d(
              "BrightnessMappingStrategy",
              "addUserDataPoint: [" + i + "] " + this.mUserPoints.get(i));
        }
        updateCurve();
      }
    }

    public void clear() {
      synchronized (this) {
        if (this.mCurve != null) {
          Plog start = BrightnessMappingStrategy.PLOG.start("clear user offset curve");
          Pair pair = this.mCurve;
          start.logCurve("offset curve", (float[]) pair.first, (float[]) pair.second);
        }
        this.mCurve = null;
        this.mUserPoints.clear();
      }
    }

    public Pair getCurve() {
      Pair pair;
      synchronized (this) {
        Pair pair2 = this.mCurve;
        if (pair2 != null) {
          Object obj = pair2.first;
          float[] copyOf = Arrays.copyOf((float[]) obj, ((float[]) obj).length);
          Object obj2 = this.mCurve.second;
          pair = Pair.create(copyOf, Arrays.copyOf((float[]) obj2, ((float[]) obj2).length));
        } else {
          pair = null;
        }
      }
      return pair;
    }

    public boolean hasUserDataPoints() {
      boolean z;
      synchronized (this) {
        z = this.mUserPoints.size() > 0;
      }
      return z;
    }

    public float getLastAddedLux() {
      float f;
      synchronized (this) {
        f = this.mLastAddedLux;
      }
      return f;
    }

    public void dump(PrintWriter printWriter) {
      printWriter.println("UserOffsetManager:");
      if (this.mCurve != null) {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        Pair pair = this.mCurve;
        sb.append(formatCurve((float[]) pair.first, (float[]) pair.second));
        printWriter.println(sb.toString());
      }
      for (int i = 0; i < this.mUserPoints.size(); i++) {
        printWriter.println("  mUserPoints[" + i + "] " + this.mUserPoints.get(i));
      }
      printWriter.println();
      printWriter.println("  sDebugLogging: " + sDebugLogging);
    }

    public static boolean setLoggingEnabled(boolean z) {
      if (sDebugLogging == z) {
        return false;
      }
      sDebugLogging = z;
      return true;
    }

    public final String formatCurve(float[] fArr, float[] fArr2) {
      StringBuilder sb = new StringBuilder();
      sb.append("curve: [");
      int length = fArr.length <= fArr2.length ? fArr.length : fArr2.length;
      for (int i = 0; i < length; i++) {
        sb.append("(" + fArr[i] + "," + fArr2[i] + "),");
      }
      sb.append("]");
      return sb.toString();
    }

    public final void updateCurve() {
      ArrayList arrayList = new ArrayList(this.mUserPoints);
      arrayList.sort(Comparator.naturalOrder());
      ArrayList arrayList2 = new ArrayList();
      ArrayList arrayList3 = new ArrayList();
      int size = arrayList.size();
      int i = 0;
      while (i < size) {
        UserPoint userPoint = (UserPoint) arrayList.get(i);
        boolean z = true;
        if (i <= 0
            || !((UserPoint) arrayList.get(i + (-1))).isInSameBoundary(userPoint.mLowerBoundary)) {
          arrayList2.add(Float.valueOf(userPoint.mLowerBoundary));
          arrayList3.add(Float.valueOf(DisplayPowerController2.RATE_FROM_DOZE_TO_ON));
        }
        arrayList2.add(Float.valueOf(userPoint.mLux));
        arrayList3.add(Float.valueOf(userPoint.mBrightnessOffset));
        if (i < size - 1
            && ((UserPoint) arrayList.get(i + 1)).isInSameBoundary(userPoint.mUpperBoundary)) {
          z = false;
        }
        if (z) {
          arrayList2.add(Float.valueOf(userPoint.mUpperBoundary));
          arrayList3.add(Float.valueOf(DisplayPowerController2.RATE_FROM_DOZE_TO_ON));
        }
        i++;
      }
      float[] convertFloatArrayListToArray = convertFloatArrayListToArray(arrayList2);
      float[] convertFloatArrayListToArray2 = convertFloatArrayListToArray(arrayList3);
      this.mCurve = Pair.create(convertFloatArrayListToArray, convertFloatArrayListToArray2);
      if (sDebugLogging) {
        BrightnessMappingStrategy.PLOG.logCurve(
            "offset curve", convertFloatArrayListToArray, convertFloatArrayListToArray2);
      }
    }

    public final void removeRedundantUserPoint(UserPoint userPoint) {
      Iterator it = this.mUserPoints.iterator();
      while (it.hasNext()) {
        if (userPoint.isInSameBoundary((UserPoint) it.next())) {
          it.remove();
        }
      }
    }

    public final float[] convertFloatArrayListToArray(ArrayList arrayList) {
      float[] fArr = new float[arrayList.size()];
      Iterator it = arrayList.iterator();
      int i = 0;
      while (it.hasNext()) {
        Float f = (Float) it.next();
        int i2 = i + 1;
        fArr[i] = f != null ? f.floatValue() : Float.NaN;
        i = i2;
      }
      return fArr;
    }

    public class UserPoint implements Comparable {
      public final float mBrightness;
      public final float mBrightnessOffset;
      public final float mLowerBoundary;
      public final float mLux;
      public final float mUpperBoundary;

      public UserPoint(float f, float f2, float f3) {
        this.mLux = f;
        this.mBrightness = f2;
        this.mBrightnessOffset = f2 - f3;
        this.mLowerBoundary = Math.min(f / 2.5f, f - 10.0f);
        this.mUpperBoundary = Math.max(2.5f * f, f + 10.0f);
      }

      public boolean isInSameBoundary(float f) {
        return this.mLowerBoundary <= f && f <= this.mUpperBoundary;
      }

      public boolean isInSameBoundary(UserPoint userPoint) {
        float f = this.mLowerBoundary;
        float f2 = userPoint.mLux;
        return f <= f2 && f2 <= this.mUpperBoundary;
      }

      @Override // java.lang.Comparable
      public int compareTo(UserPoint userPoint) {
        float f = this.mLux;
        float f2 = userPoint.mLux;
        if (f > f2) {
          return 1;
        }
        return f < f2 ? -1 : 0;
      }

      public String toString() {
        return String.format(
            "%5.1f -> %5.1f (%+6.1f) @ %6.1f < %6.1f < %6.1f",
            Float.valueOf((this.mBrightness - this.mBrightnessOffset) * 255.0f),
            Float.valueOf(this.mBrightness * 255.0f),
            Float.valueOf(this.mBrightnessOffset * 255.0f),
            Float.valueOf(this.mLowerBoundary),
            Float.valueOf(this.mLux),
            Float.valueOf(this.mUpperBoundary));
      }
    }
  }

  public class SimpleMappingStrategy extends BrightnessMappingStrategy {
    public float mAutoBrightnessAdjustment;
    public final float[] mBrightness;
    public final float[] mLux;
    public float mMaxGamma;
    public long mShortTermModelTimeout;
    public Spline mSpline;
    public float mUserBrightness;
    public float mUserLux;

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float convertToAdjustedNits(float f) {
      return -1.0f;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public int convertToBrightness(float f) {
      return -1;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float convertToFloatScale(float f) {
      return Float.NaN;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float convertToNits(float f) {
      return -1.0f;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public BrightnessConfiguration getAppliedBackupConfig(
        BrightnessConfiguration brightnessConfiguration) {
      return null;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public BrightnessConfiguration getBrightnessConfiguration() {
      return null;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float getBrightnessForSpline(float f, Spline spline) {
      return -1.0f;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public Spline getBrightnessSpline() {
      return null;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public BrightnessConfiguration getDefaultConfig() {
      return null;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public boolean isDefaultConfig() {
      return true;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public boolean isForIdleMode() {
      return false;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public void recalculateSplines(boolean z, float[] fArr) {}

    @Override // com.android.server.display.BrightnessMappingStrategy
    public boolean setBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration) {
      return false;
    }

    public SimpleMappingStrategy(float[] fArr, int[] iArr, float f, long j) {
      Preconditions.checkArgument(
          (fArr.length == 0 || iArr.length == 0) ? false : true,
          "Lux and brightness arrays must not be empty!");
      Preconditions.checkArgument(
          fArr.length == iArr.length, "Lux and brightness arrays must be the same length!");
      Preconditions.checkArrayElementsInRange(
          fArr, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, Float.MAX_VALUE, "lux");
      Preconditions.checkArrayElementsInRange(iArr, 0, Integer.MAX_VALUE, "brightness");
      int length = iArr.length;
      this.mLux = new float[length];
      this.mBrightness = new float[length];
      for (int i = 0; i < length; i++) {
        this.mLux[i] = fArr[i];
        this.mBrightness[i] = BrightnessMappingStrategy.normalizeAbsoluteBrightness(iArr[i]);
      }
      this.mMaxGamma = f;
      this.mAutoBrightnessAdjustment = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      if (this.mLoggingEnabled) {
        BrightnessMappingStrategy.PLOG.start("simple mapping strategy");
      }
      computeSpline();
      this.mShortTermModelTimeout = j;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public long getShortTermModelTimeout() {
      return this.mShortTermModelTimeout;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float getBrightness(float f, String str, int i) {
      float interpolate = this.mSpline.interpolate(f);
      if (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
          || Float.compare(interpolate, 1.0f) <= 0
          || Float.compare(f, PowerManagerUtil.HBM_LUX) >= 0) {
        return interpolate;
      }
      return 1.0f;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float getAutoBrightnessAdjustment() {
      return this.mAutoBrightnessAdjustment;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public boolean setAutoBrightnessAdjustment(float f) {
      float constrain = MathUtils.constrain(f, -1.0f, 1.0f);
      if (constrain == this.mAutoBrightnessAdjustment) {
        return false;
      }
      if (this.mLoggingEnabled) {
        Slog.d(
            "BrightnessMappingStrategy",
            "setAutoBrightnessAdjustment: " + this.mAutoBrightnessAdjustment + " => " + constrain);
        BrightnessMappingStrategy.PLOG.start("auto-brightness adjustment");
      }
      this.mAutoBrightnessAdjustment = constrain;
      computeSpline();
      return true;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public void addUserDataPoint(float f, float f2) {
      if (!BrightnessMappingStrategy.isValidUserLux(f)) {
        Slog.e("BrightnessMappingStrategy", "addUserDataPoint: invalid user lux: " + f);
        return;
      }
      float unadjustedBrightness = getUnadjustedBrightness(f);
      if (this.mLoggingEnabled) {
        Slog.d("BrightnessMappingStrategy", "addUserDataPoint: (" + f + "," + f2 + ")");
        BrightnessMappingStrategy.PLOG
            .start("add user data point")
            .logPoint("user data point", f, f2)
            .logPoint("current brightness", f, unadjustedBrightness);
      }
      float inferAutoBrightnessAdjustment =
          inferAutoBrightnessAdjustment(this.mMaxGamma, f2, unadjustedBrightness);
      if (this.mLoggingEnabled) {
        Slog.d(
            "BrightnessMappingStrategy",
            "addUserDataPoint: "
                + this.mAutoBrightnessAdjustment
                + " => "
                + inferAutoBrightnessAdjustment);
      }
      this.mAutoBrightnessAdjustment = inferAutoBrightnessAdjustment;
      this.mUserOffsetManager.addPoint(f, f2, unadjustedBrightness);
      computeSpline();
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public void clearUserDataPoints() {
      if (hasUserDataPoints()) {
        if (this.mLoggingEnabled) {
          Slog.d(
              "BrightnessMappingStrategy",
              "clearUserDataPoints: " + this.mAutoBrightnessAdjustment + " => 0");
          BrightnessMappingStrategy.PLOG.start("clear user data points");
        }
        this.mAutoBrightnessAdjustment = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mUserOffsetManager.clear();
        computeSpline();
      }
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public boolean hasUserDataPoints() {
      return this.mUserOffsetManager.hasUserDataPoints();
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public void dump(PrintWriter printWriter, float f) {
      printWriter.println("SimpleMappingStrategy");
      printWriter.println("  mSpline=" + this.mSpline);
      printWriter.println("  mMaxGamma=" + this.mMaxGamma);
      printWriter.println("  mAutoBrightnessAdjustment=" + this.mAutoBrightnessAdjustment);
      this.mUserOffsetManager.dump(printWriter);
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float getUserLux() {
      return this.mUserLux;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float getUserBrightness() {
      return this.mUserBrightness;
    }

    public final void computeSpline() {
      Pair offsetAdjustedCurve = getOffsetAdjustedCurve(this.mLux, this.mBrightness);
      this.mSpline =
          Spline.createSpline(
              (float[]) offsetAdjustedCurve.first, (float[]) offsetAdjustedCurve.second);
    }

    public final float getUnadjustedBrightness(float f) {
      return Spline.createSpline(this.mLux, this.mBrightness).interpolate(f);
    }
  }

  class PhysicalMappingStrategy extends BrightnessMappingStrategy {
    public Spline mAdjustedNitsToBrightnessSpline;
    public float mAutoBrightnessAdjustment;
    public final float[] mBrightness;
    public boolean mBrightnessRangeAdjustmentApplied;
    public Spline mBrightnessSpline;
    public Spline mBrightnessToAdjustedNitsSpline;
    public Spline mBrightnessToNitsSpline;
    public BrightnessConfiguration mConfig;
    public Spline mDefaultBrightnessToNitsSpline;
    public final BrightnessConfiguration mDefaultConfig;
    public Spline mDefaultNitsToBrightnessSpline;
    public final boolean mIsForIdleMode;
    public final float mMaxGamma;
    public final float[] mNits;
    public Spline mNitsToBrightnessSpline;
    public float mUserBrightness;
    public float mUserLux;

    @Override // com.android.server.display.BrightnessMappingStrategy
    public void recalculateSplines(boolean z, float[] fArr) {}

    public PhysicalMappingStrategy(
        BrightnessConfiguration brightnessConfiguration,
        float[] fArr,
        float[] fArr2,
        float f,
        boolean z,
        DisplayWhiteBalanceController displayWhiteBalanceController) {
      Preconditions.checkArgument(
          (fArr.length == 0 || fArr2.length == 0) ? false : true,
          "Nits and brightness arrays must not be empty!");
      Preconditions.checkArgument(
          fArr.length == fArr2.length, "Nits and brightness arrays must be the same length!");
      Objects.requireNonNull(brightnessConfiguration);
      Preconditions.checkArrayElementsInRange(
          fArr, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, Float.MAX_VALUE, "nits");
      Preconditions.checkArrayElementsInRange(
          fArr2,
          DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
          BrightnessMappingStrategy.sScreenExtendedBrightnessRangeMaximum / 255.0f,
          "brightness");
      this.mIsForIdleMode = z;
      this.mMaxGamma = f;
      this.mAutoBrightnessAdjustment = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
      this.mNits = fArr;
      this.mBrightness = fArr2;
      computeNitsBrightnessSplines(fArr);
      this.mAdjustedNitsToBrightnessSpline = this.mNitsToBrightnessSpline;
      this.mBrightnessToAdjustedNitsSpline = this.mBrightnessToNitsSpline;
      this.mDefaultNitsToBrightnessSpline = Spline.createSpline(fArr, fArr2);
      this.mDefaultBrightnessToNitsSpline = Spline.createSpline(fArr2, fArr);
      this.mDefaultConfig = brightnessConfiguration;
      if (this.mLoggingEnabled) {
        BrightnessMappingStrategy.PLOG.start("physical mapping strategy");
      }
      this.mConfig = brightnessConfiguration;
      computeSpline();
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public long getShortTermModelTimeout() {
      if (this.mConfig.getShortTermModelTimeoutMillis() >= 0) {
        return this.mConfig.getShortTermModelTimeoutMillis();
      }
      return this.mDefaultConfig.getShortTermModelTimeoutMillis();
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public boolean setBrightnessConfiguration(BrightnessConfiguration brightnessConfiguration) {
      if (brightnessConfiguration == null) {
        Slog.d("BrightnessMappingStrategy", "setBrightnessConfiguration: config is null");
        brightnessConfiguration = this.mDefaultConfig;
      }
      if (brightnessConfiguration.equals(this.mConfig)) {
        return false;
      }
      Slog.d("BrightnessMappingStrategy", "setBrightnessConfiguration: " + brightnessConfiguration);
      if (this.mLoggingEnabled) {
        BrightnessMappingStrategy.PLOG.start("brightness configuration");
      }
      this.mConfig = brightnessConfiguration;
      computeSpline();
      return true;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public BrightnessConfiguration getBrightnessConfiguration() {
      return this.mConfig;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float getBrightness(float f, String str, int i) {
      return getBrightness(f, str, i, this.mBrightnessSpline);
    }

    public final float getBrightness(float f, String str, int i, Spline spline) {
      float interpolate = this.mAdjustedNitsToBrightnessSpline.interpolate(spline.interpolate(f));
      if (!hasUserDataPoints()) {
        interpolate = correctBrightness(interpolate, str, i);
      } else if (this.mLoggingEnabled) {
        Slog.d("BrightnessMappingStrategy", "user point set, correction not applied");
      }
      if (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
          || Float.compare(interpolate, 1.0f) <= 0
          || Float.compare(f, PowerManagerUtil.HBM_LUX) >= 0) {
        return interpolate;
      }
      return 1.0f;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public Spline getBrightnessSpline() {
      return this.mBrightnessSpline;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float getBrightnessForSpline(float f, Spline spline) {
      if (spline == null) {
        Slog.e("BrightnessMappingStrategy", "getBrightnessForSpline: null");
        return getBrightness(f, null, -1);
      }
      return getBrightness(f, null, -1, spline);
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float getAutoBrightnessAdjustment() {
      return this.mAutoBrightnessAdjustment;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public boolean setAutoBrightnessAdjustment(float f) {
      Slog.d("BrightnessMappingStrategy", "setAutoBrightnessAdjustment: " + f);
      float constrain = MathUtils.constrain(f, -1.0f, 1.0f);
      if (constrain == this.mAutoBrightnessAdjustment) {
        return false;
      }
      if (this.mLoggingEnabled) {
        Slog.d(
            "BrightnessMappingStrategy",
            "setAutoBrightnessAdjustment: " + this.mAutoBrightnessAdjustment + " => " + constrain);
        BrightnessMappingStrategy.PLOG.start("auto-brightness adjustment");
      }
      this.mAutoBrightnessAdjustment = constrain;
      computeSpline();
      return true;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float convertToNits(float f) {
      return this.mBrightnessToNitsSpline.interpolate(f);
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float convertToAdjustedNits(float f) {
      return this.mBrightnessToAdjustedNitsSpline.interpolate(f);
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float convertToFloatScale(float f) {
      return this.mNitsToBrightnessSpline.interpolate(f);
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public int convertToBrightness(float f) {
      return MathUtils.constrain(
          BrightnessSynchronizer.brightnessFloatToInt(this.mNitsToBrightnessSpline.interpolate(f)),
          0,
          BrightnessMappingStrategy.sScreenExtendedBrightnessRangeMaximum);
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public void addUserDataPoint(float f, float f2) {
      if (!BrightnessMappingStrategy.isValidUserLux(f)) {
        Slog.e("BrightnessMappingStrategy", "addUserDataPoint: invalid user lux: " + f);
        return;
      }
      float unadjustedBrightness = getUnadjustedBrightness(f);
      if (this.mLoggingEnabled) {
        Slog.d("BrightnessMappingStrategy", "addUserDataPoint: (" + f + "," + f2 + ")");
        BrightnessMappingStrategy.PLOG
            .start("add user data point")
            .logPoint("user data point", f, f2)
            .logPoint("current brightness", f, unadjustedBrightness);
      }
      float inferAutoBrightnessAdjustment =
          inferAutoBrightnessAdjustment(this.mMaxGamma, f2, unadjustedBrightness);
      if (this.mLoggingEnabled) {
        Slog.d(
            "BrightnessMappingStrategy",
            "addUserDataPoint: "
                + this.mAutoBrightnessAdjustment
                + " => "
                + inferAutoBrightnessAdjustment);
      }
      this.mAutoBrightnessAdjustment = inferAutoBrightnessAdjustment;
      this.mUserOffsetManager.addPoint(f, f2, unadjustedBrightness);
      computeSpline();
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public void clearUserDataPoints() {
      if (hasUserDataPoints()) {
        if (this.mLoggingEnabled) {
          Slog.d(
              "BrightnessMappingStrategy",
              "clearUserDataPoints: " + this.mAutoBrightnessAdjustment + " => 0");
          BrightnessMappingStrategy.PLOG.start("clear user data points");
        }
        this.mAutoBrightnessAdjustment = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mUserOffsetManager.clear();
        computeSpline();
      }
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public boolean hasUserDataPoints() {
      return this.mUserOffsetManager.hasUserDataPoints();
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public boolean isDefaultConfig() {
      return this.mDefaultConfig.equals(this.mConfig);
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public BrightnessConfiguration getDefaultConfig() {
      return this.mDefaultConfig;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public BrightnessConfiguration getAppliedBackupConfig(
        BrightnessConfiguration brightnessConfiguration) {
      Pair curve = this.mConfig.getCurve();
      float[] fArr = (float[]) curve.first;
      Object obj = curve.second;
      float[] copyOf = Arrays.copyOf((float[]) obj, ((float[]) obj).length);
      Pair curve2 = brightnessConfiguration.getCurve();
      float[] fArr2 = (float[]) curve2.first;
      float[] fArr3 = (float[]) curve2.second;
      Spline createSpline = Spline.createSpline(fArr2, fArr3);
      if (Float.compare(fArr[0], DisplayPowerController2.RATE_FROM_DOZE_TO_ON) == 0
          && Float.compare(fArr2[0], DisplayPowerController2.RATE_FROM_DOZE_TO_ON) == 0
          && Float.compare(copyOf[0], fArr3[0]) > 0) {
        Slog.d("BrightnessMappingStrategy", "defulat brightness is higher than backup brightness");
        return null;
      }
      for (int i = 0; Float.compare(fArr[i], 30.0f) < 0; i++) {
        copyOf[i] = createSpline.interpolate(fArr[i]);
      }
      for (int i2 = 1; i2 < fArr.length; i2++) {
        if (!PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM
            || Float.compare(fArr[i2], PowerManagerUtil.HBM_LUX) < 0) {
          int i3 = i2 - 1;
          if (Float.compare(copyOf[i3], copyOf[i2]) > 0) {
            copyOf[i2] = copyOf[i3];
          }
        }
      }
      BrightnessConfiguration.Builder builder = new BrightnessConfiguration.Builder(fArr, copyOf);
      builder.setDescription(brightnessConfiguration.getDescription());
      return builder.build();
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public void dump(PrintWriter printWriter, float f) {
      printWriter.println("PhysicalMappingStrategy");
      printWriter.println("  mConfig=" + this.mConfig);
      printWriter.println("  mBrightnessSpline=" + this.mBrightnessSpline);
      printWriter.println(
          "  mNitsToBrightnessSpline="
              + PowerManagerUtil.floatArrayPairToString(this.mNits, this.mBrightness));
      printWriter.println("  mMaxGamma=" + this.mMaxGamma);
      printWriter.println("  mAutoBrightnessAdjustment=" + this.mAutoBrightnessAdjustment);
      if (this.mBrightnessRangeAdjustmentApplied) {
        printWriter.println(
            "  mDefaultNitsToBrightnessSpline=" + this.mDefaultNitsToBrightnessSpline);
        printWriter.println(
            "  mDefaultBrightnessToNitsSpline=" + this.mDefaultBrightnessToNitsSpline);
      }
      this.mUserOffsetManager.dump(printWriter);
      printWriter.println("  mDefaultConfig=" + this.mDefaultConfig);
      printWriter.println(
          "  mBrightnessRangeAdjustmentApplied=" + this.mBrightnessRangeAdjustmentApplied);
      printWriter.println(
          "  SEC_FEATURE_SUPPORT_LEGACY_PERFORMANCE_MODE="
              + PowerManagerUtil.SEC_FEATURE_SUPPORT_LEGACY_PERFORMANCE_MODE);
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public boolean isForIdleMode() {
      return this.mIsForIdleMode;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float getUserLux() {
      return this.mUserLux;
    }

    @Override // com.android.server.display.BrightnessMappingStrategy
    public float getUserBrightness() {
      return this.mUserBrightness;
    }

    public final void computeNitsBrightnessSplines(float[] fArr) {
      this.mNitsToBrightnessSpline = Spline.createSpline(fArr, this.mBrightness);
      this.mBrightnessToNitsSpline = Spline.createSpline(this.mBrightness, fArr);
    }

    public final void computeSpline() {
      Pair curve = this.mConfig.getCurve();
      float[] fArr = (float[]) curve.first;
      float[] fArr2 = (float[]) curve.second;
      int length = fArr2.length;
      float[] fArr3 = new float[length];
      for (int i = 0; i < length; i++) {
        fArr3[i] = this.mAdjustedNitsToBrightnessSpline.interpolate(fArr2[i]);
      }
      if (PowerManagerUtil.SEC_FEATURE_SUPPORT_HBM && !isDefaultConfig()) {
        Pair curve2 = this.mDefaultConfig.getCurve();
        float[] fArr4 = (float[]) curve2.first;
        float[] fArr5 = (float[]) curve2.second;
        int i2 = 0;
        for (float f : fArr) {
          if (f < PowerManagerUtil.HBM_LUX) {
            i2++;
          }
        }
        int i3 = 0;
        for (float f2 : fArr4) {
          if (f2 >= PowerManagerUtil.HBM_LUX) {
            i3++;
          }
        }
        int i4 = i2 + i3;
        float[] fArr6 = new float[i4];
        float[] fArr7 = new float[i4];
        System.arraycopy(fArr, 0, fArr6, 0, i2);
        System.arraycopy(fArr2, 0, fArr7, 0, i2);
        System.arraycopy(fArr4, fArr4.length - i3, fArr6, i2, i3);
        System.arraycopy(fArr5, fArr5.length - i3, fArr7, i2, i3);
        float[] fArr8 = new float[i4];
        for (int i5 = 0; i5 < i4; i5++) {
          fArr8[i5] = this.mNitsToBrightnessSpline.interpolate(fArr7[i5]);
        }
        fArr = (float[]) fArr6.clone();
        fArr3 = (float[]) fArr8.clone();
      }
      Pair offsetAdjustedCurve = getOffsetAdjustedCurve(fArr, fArr3);
      float[] fArr9 = (float[]) offsetAdjustedCurve.first;
      float[] fArr10 = (float[]) offsetAdjustedCurve.second;
      int length2 = fArr10.length;
      float[] fArr11 = new float[length2];
      for (int i6 = 0; i6 < length2; i6++) {
        fArr11[i6] = this.mBrightnessToAdjustedNitsSpline.interpolate(fArr10[i6]);
      }
      this.mBrightnessSpline = Spline.createSpline(fArr9, fArr11);
      Slog.d(
          "BrightnessMappingStrategy",
          "computeSpline: mBrightnessSpline: " + this.mBrightnessSpline);
    }

    public final float getUnadjustedBrightness(float f) {
      Pair curve = this.mConfig.getCurve();
      return this.mAdjustedNitsToBrightnessSpline.interpolate(
          Spline.createSpline((float[]) curve.first, (float[]) curve.second).interpolate(f));
    }

    public final float correctBrightness(float f, String str, int i) {
      BrightnessCorrection correctionByCategory;
      BrightnessCorrection correctionByPackageName;
      if (str == null
          || (correctionByPackageName = this.mConfig.getCorrectionByPackageName(str)) == null) {
        return (i == -1 || (correctionByCategory = this.mConfig.getCorrectionByCategory(i)) == null)
            ? f
            : correctionByCategory.apply(f);
      }
      return correctionByPackageName.apply(f);
    }
  }
}
