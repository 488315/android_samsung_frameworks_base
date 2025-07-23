package com.android.systemui.theme;

import android.content.om.OverlayInfo;
import android.util.Pair;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ThemeOverlayApplier$$ExternalSyntheticLambda7 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        OverlayInfo overlayInfo = (OverlayInfo) obj;
        boolean z = ThemeOverlayApplier.DEBUG;
        return new Pair(overlayInfo.category, overlayInfo.packageName);
    }
}
