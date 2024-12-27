package com.android.server.display.mode;

import com.android.server.display.utils.DeviceConfigParsingUtils;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayModeDirector$BrightnessObserver$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int[] iArr = (int[]) obj;
        switch (this.$r8$classId) {
            case 0:
                return DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(iArr);
            default:
                return DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(iArr);
        }
    }
}
