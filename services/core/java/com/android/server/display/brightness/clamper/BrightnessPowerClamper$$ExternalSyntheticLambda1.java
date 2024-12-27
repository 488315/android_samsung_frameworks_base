package com.android.server.display.brightness.clamper;

import com.android.server.display.DisplayDeviceConfig;
import com.android.server.display.utils.DeviceConfigParsingUtils;

import java.util.function.BiFunction;

public final /* synthetic */ class BrightnessPowerClamper$$ExternalSyntheticLambda1
        implements BiFunction {
    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        String str = (String) obj2;
        try {
            return new DisplayDeviceConfig.PowerThrottlingData.ThrottlingLevel(
                    Float.parseFloat(str),
                    DeviceConfigParsingUtils.parseThermalStatus((String) obj));
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
