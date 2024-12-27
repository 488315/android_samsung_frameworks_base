package com.android.systemui.qs.external;

import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class TileLifecycleManager$$ExternalSyntheticLambda15 implements Function {
    public final /* synthetic */ Predicate f$0;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(this.f$0.test((QSTileServiceWrapper) obj));
    }
}
