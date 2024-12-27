package com.android.systemui.theme;

import android.content.om.OverlayInfo;
import android.util.Pair;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ThemeOverlayApplier$$ExternalSyntheticLambda7 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        OverlayInfo overlayInfo = (OverlayInfo) obj;
        return new Pair(overlayInfo.category, overlayInfo.packageName);
    }
}
