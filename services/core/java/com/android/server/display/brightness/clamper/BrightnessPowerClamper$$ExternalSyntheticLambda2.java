package com.android.server.display.brightness.clamper;

import com.android.server.display.DisplayDeviceConfig;

import java.util.List;
import java.util.function.Function;

public final /* synthetic */ class BrightnessPowerClamper$$ExternalSyntheticLambda2
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return DisplayDeviceConfig.PowerThrottlingData.create((List) obj);
    }
}
