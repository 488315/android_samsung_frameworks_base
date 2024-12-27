package com.android.server.display;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class NormalBrightnessModeController {
    public float mAmbientLux;
    public boolean mAutoBrightnessEnabled;
    public float mMaxBrightness;
    public Map mMaxBrightnessLimits;

    public final boolean recalculateMaxBrightness() {
        Map map =
                this.mAutoBrightnessEnabled
                        ? (Map)
                                this.mMaxBrightnessLimits.get(
                                        DisplayDeviceConfig.BrightnessLimitMapType.ADAPTIVE)
                        : null;
        if (this.mAutoBrightnessEnabled && map == null) {
            map =
                    (Map)
                            this.mMaxBrightnessLimits.get(
                                    DisplayDeviceConfig.BrightnessLimitMapType.DEFAULT);
        }
        float f = 1.0f;
        if (map != null) {
            float f2 = Float.MAX_VALUE;
            for (Map.Entry entry : map.entrySet()) {
                float floatValue = ((Float) entry.getKey()).floatValue();
                if (floatValue > this.mAmbientLux && floatValue < f2) {
                    f = ((Float) entry.getValue()).floatValue();
                    f2 = floatValue;
                }
            }
        }
        if (this.mMaxBrightness == f) {
            return false;
        }
        this.mMaxBrightness = f;
        return true;
    }
}
