package com.android.systemui.theme;

import android.content.om.OverlayInfo;
import android.util.Pair;
import java.util.function.Function;

public final /* synthetic */ class ThemeOverlayApplier$$ExternalSyntheticLambda7 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        OverlayInfo overlayInfo = (OverlayInfo) obj;
        return new Pair(overlayInfo.category, overlayInfo.packageName);
    }
}
