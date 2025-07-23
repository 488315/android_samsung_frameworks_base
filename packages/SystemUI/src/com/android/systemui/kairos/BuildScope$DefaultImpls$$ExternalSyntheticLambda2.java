package com.android.systemui.kairos;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class BuildScope$DefaultImpls$$ExternalSyntheticLambda2 implements Function2 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BuildScope$DefaultImpls$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                return ((TransactionScope) obj).sample((State) this.f$0);
            default:
                ((StateFlowImpl) this.f$0).updateState(null, DeferredValueKt.deferredOf(obj2));
                return Unit.INSTANCE;
        }
    }
}
