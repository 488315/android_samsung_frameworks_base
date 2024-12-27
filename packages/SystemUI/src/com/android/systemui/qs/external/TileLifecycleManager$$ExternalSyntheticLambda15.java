package com.android.systemui.qs.external;

import java.util.function.Function;
import java.util.function.Predicate;

public final /* synthetic */ class TileLifecycleManager$$ExternalSyntheticLambda15 implements Function {
    public final /* synthetic */ Predicate f$0;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(this.f$0.test((QSTileServiceWrapper) obj));
    }
}
