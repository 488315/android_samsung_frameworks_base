package com.android.server.wm;

import java.util.function.Predicate;

public final /* synthetic */ class FlexPanelController$$ExternalSyntheticLambda0
        implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return ((Task) obj).inFullscreenWindowingMode();
    }
}
