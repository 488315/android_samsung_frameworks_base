package com.android.server.power;

import java.text.SimpleDateFormat;
import java.util.function.ToIntFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class PowerManagerService$$ExternalSyntheticLambda9
        implements ToIntFunction {
    @Override // java.util.function.ToIntFunction
    public final int applyAsInt(Object obj) {
        SimpleDateFormat simpleDateFormat = PowerManagerService.DATE_FORMAT;
        return ((PowerManagerService.HdrBrightnessLimitLock) obj).mUpperLimit;
    }
}
