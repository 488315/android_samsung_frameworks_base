package com.android.server.display.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HdrBrightnessData {
    public final long mBrightnessDecreaseDebounceMillis;
    public final long mBrightnessIncreaseDebounceMillis;
    public final Map mMaxBrightnessLimits;
    public final float mScreenBrightnessRampDecrease;
    public final float mScreenBrightnessRampIncrease;

    public HdrBrightnessData(Map map, long j, float f, long j2, float f2) {
        this.mMaxBrightnessLimits = map;
        this.mBrightnessIncreaseDebounceMillis = j;
        this.mScreenBrightnessRampIncrease = f;
        this.mBrightnessDecreaseDebounceMillis = j2;
        this.mScreenBrightnessRampDecrease = f2;
    }

    public static HdrBrightnessData loadConfig(DisplayConfiguration displayConfiguration) {
        HdrBrightnessConfig hdrBrightnessConfig = displayConfiguration.hdrBrightnessConfig;
        if (hdrBrightnessConfig == null) {
            return null;
        }
        List<NonNegativeFloatToFloatPoint> point = hdrBrightnessConfig.brightnessMap.getPoint();
        HashMap hashMap = new HashMap();
        for (NonNegativeFloatToFloatPoint nonNegativeFloatToFloatPoint : point) {
            hashMap.put(
                    Float.valueOf(nonNegativeFloatToFloatPoint.first.floatValue()),
                    Float.valueOf(nonNegativeFloatToFloatPoint.second.floatValue()));
        }
        return new HdrBrightnessData(
                hashMap,
                hdrBrightnessConfig.brightnessIncreaseDebounceMillis.longValue(),
                hdrBrightnessConfig.screenBrightnessRampIncrease.floatValue(),
                hdrBrightnessConfig.brightnessDecreaseDebounceMillis.longValue(),
                hdrBrightnessConfig.screenBrightnessRampDecrease.floatValue());
    }

    public final String toString() {
        return "HdrBrightnessData {mMaxBrightnessLimits: "
                + this.mMaxBrightnessLimits
                + ", mBrightnessIncreaseDebounceMillis: "
                + this.mBrightnessIncreaseDebounceMillis
                + ", mScreenBrightnessRampIncrease: "
                + this.mScreenBrightnessRampIncrease
                + ", mBrightnessDecreaseDebounceMillis: "
                + this.mBrightnessDecreaseDebounceMillis
                + ", mScreenBrightnessRampDecrease: "
                + this.mScreenBrightnessRampDecrease
                + "} ";
    }
}
