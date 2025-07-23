package com.android.systemui.theme;

import android.content.om.OverlayInfo;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ThemeOverlayApplier$$ExternalSyntheticLambda6 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean z = ThemeOverlayApplier.DEBUG;
        return ((OverlayInfo) obj).isEnabled();
    }
}
