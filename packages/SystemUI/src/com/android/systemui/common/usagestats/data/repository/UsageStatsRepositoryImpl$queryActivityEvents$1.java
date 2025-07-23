package com.android.systemui.common.usagestats.data.repository;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class UsageStatsRepositoryImpl$queryActivityEvents$1 extends ContinuationImpl {
    int label;
    /* synthetic */ Object result;
    final /* synthetic */ UsageStatsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UsageStatsRepositoryImpl$queryActivityEvents$1(UsageStatsRepositoryImpl usageStatsRepositoryImpl, Continuation continuation) {
        super(continuation);
        this.this$0 = usageStatsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.queryActivityEvents(null, this);
    }
}
