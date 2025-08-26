package com.android.systemui.theme;

import android.content.om.OverlayInfo;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public final /* synthetic */ class ThemeOverlayApplier$$ExternalSyntheticLambda6 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        boolean z = ThemeOverlayApplier.DEBUG;
        return ((OverlayInfo) obj).isEnabled();
    }
}
