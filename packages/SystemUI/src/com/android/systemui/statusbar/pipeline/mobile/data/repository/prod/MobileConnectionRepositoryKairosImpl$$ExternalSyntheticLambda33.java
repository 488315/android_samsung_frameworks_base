package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import java.util.Set;
import kotlin.Pair;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileConnectionRepositoryKairosImpl$$ExternalSyntheticLambda33 implements Function3 {
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        Pair pair = (Pair) obj2;
        return SetsKt___SetsKt.plus(SetsKt___SetsKt.minus((Set) obj3, (Iterable) pair.component2()), (Iterable) pair.component1());
    }
}
