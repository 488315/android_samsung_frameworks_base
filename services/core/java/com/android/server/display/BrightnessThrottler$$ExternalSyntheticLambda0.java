package com.android.server.display;

import com.android.server.display.utils.DeviceConfigParsingUtils;

import java.util.function.BiFunction;

public final /* synthetic */ class BrightnessThrottler$$ExternalSyntheticLambda0
        implements BiFunction {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        String str = (String) obj2;
        try {
            return new DisplayDeviceConfig.ThermalBrightnessThrottlingData.ThrottlingLevel(
                    DeviceConfigParsingUtils.parseBrightness(str),
                    DeviceConfigParsingUtils.parseThermalStatus((String) obj));
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
