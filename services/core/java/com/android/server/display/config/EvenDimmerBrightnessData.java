package com.android.server.display.config;

import android.util.Slog;
import android.util.Spline;

import java.util.ArrayList;
import java.util.Arrays;

public final class EvenDimmerBrightnessData {
    public final float[] mBacklight;
    public final Spline mBacklightToBrightness;
    public final Spline mBacklightToNits;
    public final float[] mBrightness;
    public final Spline mBrightnessToBacklight;
    public final Spline mMinLuxToNits;
    public final float[] mNits;
    public final Spline mNitsToBacklight;
    public final float mTransitionPoint;

    public EvenDimmerBrightnessData(
            float f,
            float[] fArr,
            float[] fArr2,
            float[] fArr3,
            Spline spline,
            Spline spline2,
            Spline spline3,
            Spline spline4,
            Spline spline5) {
        this.mTransitionPoint = f;
        this.mNits = fArr;
        this.mBacklight = fArr2;
        this.mBrightness = fArr3;
        this.mBacklightToNits = spline;
        this.mNitsToBacklight = spline2;
        this.mBrightnessToBacklight = spline3;
        this.mBacklightToBrightness = spline4;
        this.mMinLuxToNits = spline5;
    }

    public static EvenDimmerBrightnessData loadConfig(DisplayConfiguration displayConfiguration) {
        ComprehensiveBrightnessMap comprehensiveBrightnessMap;
        EvenDimmerMode evenDimmerMode = displayConfiguration.evenDimmer;
        if (evenDimmerMode == null) {
            return null;
        }
        Boolean bool = evenDimmerMode.enabled;
        int i = 0;
        if (!(bool == null ? false : bool.booleanValue())
                || (comprehensiveBrightnessMap = evenDimmerMode.brightnessMapping) == null) {
            return null;
        }
        String str = comprehensiveBrightnessMap.interpolation;
        if (comprehensiveBrightnessMap.brightnessPoint == null) {
            comprehensiveBrightnessMap.brightnessPoint = new ArrayList();
        }
        ArrayList arrayList = (ArrayList) comprehensiveBrightnessMap.brightnessPoint;
        if (arrayList.isEmpty()) {
            return null;
        }
        float[] fArr = new float[arrayList.size()];
        float[] fArr2 = new float[arrayList.size()];
        float[] fArr3 = new float[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            BrightnessPoint brightnessPoint = (BrightnessPoint) arrayList.get(i2);
            fArr[i2] = brightnessPoint.nits.floatValue();
            fArr2[i2] = brightnessPoint.backlight.floatValue();
            fArr3[i2] = brightnessPoint.brightness.floatValue();
        }
        float floatValue = evenDimmerMode.transitionPoint.floatValue();
        NitsMap nitsMap = evenDimmerMode.luxToMinimumNitsMap;
        if (nitsMap == null) {
            Slog.e("EvenDimmerBrightnessData", "Invalid min lux to nits mapping");
            return null;
        }
        if (nitsMap.point == null) {
            nitsMap.point = new ArrayList();
        }
        ArrayList<Point> arrayList2 = (ArrayList) nitsMap.point;
        int size = arrayList2.size();
        float[] fArr4 = new float[size];
        float[] fArr5 = new float[size];
        for (Point point : arrayList2) {
            fArr4[i] = point.value.floatValue();
            fArr5[i] = point.nits.floatValue();
            if (i > 0) {
                int i3 = i - 1;
                if (fArr4[i] < fArr4[i3]) {
                    Slog.e(
                            "EvenDimmerBrightnessData",
                            "minLuxToNitsSpline must be non-decreasing, ignoring rest  of"
                                + " configuration. Value: "
                                    + fArr4[i]
                                    + " < "
                                    + fArr4[i3]);
                }
                if (fArr5[i] < fArr5[i3]) {
                    Slog.e(
                            "EvenDimmerBrightnessData",
                            "minLuxToNitsSpline must be non-decreasing, ignoring rest  of"
                                + " configuration. Nits: "
                                    + fArr5[i]
                                    + " < "
                                    + fArr5[i3]);
                }
            }
            i++;
        }
        return "linear".equals(str)
                ? new EvenDimmerBrightnessData(
                        floatValue,
                        fArr,
                        fArr2,
                        fArr3,
                        new Spline.LinearSpline(fArr2, fArr),
                        new Spline.LinearSpline(fArr, fArr2),
                        new Spline.LinearSpline(fArr3, fArr2),
                        new Spline.LinearSpline(fArr2, fArr3),
                        new Spline.LinearSpline(fArr4, fArr5))
                : new EvenDimmerBrightnessData(
                        floatValue,
                        fArr,
                        fArr2,
                        fArr3,
                        Spline.createSpline(fArr2, fArr),
                        Spline.createSpline(fArr, fArr2),
                        Spline.createSpline(fArr3, fArr2),
                        Spline.createSpline(fArr2, fArr3),
                        Spline.createSpline(fArr4, fArr5));
    }

    public final String toString() {
        return "EvenDimmerBrightnessData {mTransitionPoint: "
                + this.mTransitionPoint
                + ", mNits: "
                + Arrays.toString(this.mNits)
                + ", mBacklight: "
                + Arrays.toString(this.mBacklight)
                + ", mBrightness: "
                + Arrays.toString(this.mBrightness)
                + ", mBacklightToNits: "
                + this.mBacklightToNits
                + ", mNitsToBacklight: "
                + this.mNitsToBacklight
                + ", mBrightnessToBacklight: "
                + this.mBrightnessToBacklight
                + ", mBacklightToBrightness: "
                + this.mBacklightToBrightness
                + ", mMinLuxToNits: "
                + this.mMinLuxToNits
                + "} ";
    }
}
