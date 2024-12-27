package com.android.server.wm;

import java.util.function.Function;

public final /* synthetic */ class AppCompatReachabilityOverrides$$ExternalSyntheticLambda0
        implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return ((ActivityRecord) obj).getScreenResolvedBounds();
    }
}
