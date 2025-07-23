package com.android.systemui.kairos.internal;

import com.android.systemui.kairos.internal.store.MutableMapK;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class StateImplKt$$ExternalSyntheticLambda0 implements Function3 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        EvalScope evalScope = (EvalScope) obj;
        switch (this.$r8$classId) {
            case 0:
                ((Integer) obj3).intValue();
                return ((StateImpl) obj2).store.getCurrentWithEpoch(evalScope).getFirst();
            default:
                ((Integer) obj3).intValue();
                return CollectionsKt___CollectionsKt.toList(((MutableMapK) obj2).values());
        }
    }
}
