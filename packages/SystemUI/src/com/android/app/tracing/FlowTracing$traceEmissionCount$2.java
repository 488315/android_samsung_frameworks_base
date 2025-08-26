package com.android.app.tracing;

import android.os.Trace;
import kotlin.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$IntRef;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class FlowTracing$traceEmissionCount$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$IntRef $count;
    final /* synthetic */ Lazy $trackName$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowTracing$traceEmissionCount$2(Ref$IntRef ref$IntRef, Lazy lazy, Continuation continuation) {
        super(2, continuation);
        this.$count = ref$IntRef;
        this.$trackName$delegate = lazy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FlowTracing$traceEmissionCount$2(this.$count, this.$trackName$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowTracing$traceEmissionCount$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.$count.element++;
        if (Trace.isEnabled()) {
            Lazy lazy = this.$trackName$delegate;
            FlowTracing flowTracing = FlowTracing.INSTANCE;
            Trace.traceCounter(4096L, (String) lazy.getValue(), this.$count.element);
        }
        return Unit.INSTANCE;
    }
}
