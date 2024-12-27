package com.android.server.power;

import java.text.SimpleDateFormat;
import java.util.function.ToIntFunction;

public final /* synthetic */ class PowerManagerService$$ExternalSyntheticLambda9
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
        return ((PowerManagerService.HdrBrightnessLimitLock) obj).mUpperLimit;
    }
}
