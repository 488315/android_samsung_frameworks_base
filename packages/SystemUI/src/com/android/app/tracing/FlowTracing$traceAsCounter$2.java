package com.android.app.tracing;

import android.os.Trace;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FlowTracing$traceAsCounter$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $counterName;
    final /* synthetic */ Function1 $valueToInt;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlowTracing$traceAsCounter$2(String str, Function1 function1, Continuation continuation) {
        super(2, continuation);
        this.$counterName = str;
        this.$valueToInt = function1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        FlowTracing$traceAsCounter$2 flowTracing$traceAsCounter$2 = new FlowTracing$traceAsCounter$2(this.$counterName, this.$valueToInt, continuation);
        flowTracing$traceAsCounter$2.L$0 = obj;
        return flowTracing$traceAsCounter$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FlowTracing$traceAsCounter$2) create((Number) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Number number = (Number) this.L$0;
        if (Trace.isEnabled()) {
            Trace.traceCounter(4096L, this.$counterName, ((Number) this.$valueToInt.mo779invoke(number)).intValue());
        }
        return Unit.INSTANCE;
    }
}
