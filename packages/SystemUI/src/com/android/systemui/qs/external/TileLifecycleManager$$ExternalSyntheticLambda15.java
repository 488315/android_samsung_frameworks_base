package com.android.systemui.qs.external;

import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class TileLifecycleManager$$ExternalSyntheticLambda15 implements Function {
    public final /* synthetic */ Predicate f$0;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(this.f$0.test((QSTileServiceWrapper) obj));
    }
}
