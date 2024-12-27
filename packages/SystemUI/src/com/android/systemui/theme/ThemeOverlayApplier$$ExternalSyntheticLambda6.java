package com.android.systemui.theme;

import android.content.om.OverlayInfo;
import java.util.function.Predicate;

public final /* synthetic */ class ThemeOverlayApplier$$ExternalSyntheticLambda6 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((OverlayInfo) obj).isEnabled();
    }
}
