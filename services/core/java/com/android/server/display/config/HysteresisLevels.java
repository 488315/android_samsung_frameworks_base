package com.android.server.display.config;

import android.util.Pair;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.utils.DebugUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class HysteresisLevels {
    public final float[] mBrighteningThresholdLevels;
    public final float[] mBrighteningThresholdsPercentages;
    public final float[] mDarkeningThresholdLevels;
    public final float[] mDarkeningThresholdsPercentages;
    public final float mMinBrightening;
    public final float mMinDarkening;
    public static final float[] DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS = {100.0f};
    public static final float[] DEFAULT_AMBIENT_DARKENING_THRESHOLDS = {200.0f};
    public static final float[] DEFAULT_AMBIENT_THRESHOLD_LEVELS = {
        FullScreenMagnificationGestureHandler.MAX_SCALE
    };
    public static final float[] DEFAULT_SCREEN_THRESHOLD_LEVELS = {
        FullScreenMagnificationGestureHandler.MAX_SCALE
    };
    public static final float[] DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS = {100.0f};
    public static final float[] DEFAULT_SCREEN_DARKENING_THRESHOLDS = {200.0f};

    static {
        DebugUtils.isDebuggable("HysteresisLevels");
    }

    public HysteresisLevels(
            float[] fArr, float[] fArr2, float[] fArr3, float[] fArr4, float f, float f2) {
        if (fArr.length != fArr3.length || fArr2.length != fArr4.length) {
            throw new IllegalArgumentException("Mismatch between hysteresis array lengths.");
        }
        this.mBrighteningThresholdsPercentages = setArrayFormat(100.0f, fArr);
        this.mDarkeningThresholdsPercentages = setArrayFormat(100.0f, fArr2);
        this.mBrighteningThresholdLevels = setArrayFormat(1.0f, fArr3);
        this.mDarkeningThresholdLevels = setArrayFormat(1.0f, fArr4);
        this.mMinDarkening = f;
        this.mMinBrightening = f2;
    }

    public static HysteresisLevels createHysteresisLevels(
            Thresholds thresholds,
            int i,
            int i2,
            int i3,
            float[] fArr,
            float[] fArr2,
            float[] fArr3,
            boolean z) {
        BigDecimal bigDecimal;
        BigDecimal bigDecimal2;
        BrightnessThresholds brightnessThresholds =
                thresholds == null ? null : thresholds.brighteningThresholds;
        BrightnessThresholds brightnessThresholds2 =
                thresholds != null ? thresholds.darkeningThresholds : null;
        Pair brightnessLevelAndPercentage =
                getBrightnessLevelAndPercentage(brightnessThresholds, i, i2, fArr, fArr2, z);
        Pair brightnessLevelAndPercentage2 =
                getBrightnessLevelAndPercentage(brightnessThresholds2, i, i3, fArr, fArr3, z);
        float f = FullScreenMagnificationGestureHandler.MAX_SCALE;
        float floatValue =
                (brightnessThresholds == null
                                || (bigDecimal2 = brightnessThresholds.minimum) == null)
                        ? 0.0f
                        : bigDecimal2.floatValue();
        if (brightnessThresholds2 != null && (bigDecimal = brightnessThresholds2.minimum) != null) {
            f = bigDecimal.floatValue();
        }
        return new HysteresisLevels(
                (float[]) brightnessLevelAndPercentage.second,
                (float[]) brightnessLevelAndPercentage2.second,
                (float[]) brightnessLevelAndPercentage.first,
                (float[]) brightnessLevelAndPercentage2.first,
                f,
                floatValue);
    }

    public static Pair getBrightnessLevelAndPercentage(
            BrightnessThresholds brightnessThresholds,
            int i,
            int i2,
            float[] fArr,
            float[] fArr2,
            boolean z) {
        ThresholdPoints thresholdPoints;
        if (brightnessThresholds != null
                && (thresholdPoints = brightnessThresholds.brightnessThresholdPoints) != null) {
            if (thresholdPoints.brightnessThresholdPoint == null) {
                thresholdPoints.brightnessThresholdPoint = new ArrayList();
            }
            if (!((ArrayList) thresholdPoints.brightnessThresholdPoint).isEmpty()) {
                ThresholdPoints thresholdPoints2 = brightnessThresholds.brightnessThresholdPoints;
                if (thresholdPoints2.brightnessThresholdPoint == null) {
                    thresholdPoints2.brightnessThresholdPoint = new ArrayList();
                }
                ArrayList<ThresholdPoint> arrayList =
                        (ArrayList) thresholdPoints2.brightnessThresholdPoint;
                int size = arrayList.size();
                float[] fArr3 = new float[size];
                float[] fArr4 = new float[size];
                int i3 = 0;
                for (ThresholdPoint thresholdPoint : arrayList) {
                    fArr3[i3] = thresholdPoint.threshold.floatValue();
                    fArr4[i3] = thresholdPoint.percentage.floatValue();
                    i3++;
                }
                return new Pair(fArr3, fArr4);
            }
        }
        return new Pair(fArr, fArr2);
    }

    public static float[] setArrayFormat(float f, float[] fArr) {
        int length = fArr.length;
        float[] fArr2 = new float[length];
        for (int i = 0; length > i; i++) {
            fArr2[i] = fArr[i] / f;
        }
        return fArr2;
    }

    public float getBrighteningThreshold(float f) {
        throw null;
    }

    public float[] getBrighteningThresholdLevels() {
        return this.mBrighteningThresholdLevels;
    }

    public float[] getBrighteningThresholdsPercentages() {
        return this.mBrighteningThresholdsPercentages;
    }

    public float getDarkeningThreshold(float f) {
        throw null;
    }

    public float[] getDarkeningThresholdLevels() {
        return this.mDarkeningThresholdLevels;
    }

    public float[] getDarkeningThresholdsPercentages() {
        return this.mDarkeningThresholdsPercentages;
    }

    public float getMinBrightening() {
        return this.mMinBrightening;
    }

    public float getMinDarkening() {
        return this.mMinDarkening;
    }

    public String toString() {
        return "HysteresisLevels {\n    mBrighteningThresholdLevels="
                + Arrays.toString(this.mBrighteningThresholdLevels)
                + ",\n    mBrighteningThresholdsPercentages="
                + Arrays.toString(this.mBrighteningThresholdsPercentages)
                + ",\n    mMinBrightening="
                + this.mMinBrightening
                + ",\n    mDarkeningThresholdLevels="
                + Arrays.toString(this.mDarkeningThresholdLevels)
                + ",\n    mDarkeningThresholdsPercentages="
                + Arrays.toString(this.mDarkeningThresholdsPercentages)
                + ",\n    mMinDarkening="
                + this.mMinDarkening
                + "\n}";
    }
}
