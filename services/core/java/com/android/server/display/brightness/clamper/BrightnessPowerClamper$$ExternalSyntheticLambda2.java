package com.android.server.display.brightness.clamper;

import com.android.server.display.DisplayDeviceConfig;
import java.util.List;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BrightnessPowerClamper$$ExternalSyntheticLambda2 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return DisplayDeviceConfig.PowerThrottlingData.create((List) obj);
    }
}
