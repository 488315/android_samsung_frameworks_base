package com.android.systemui.education.ui.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ContextualEduViewModel$timeout$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ Object $emitAfterTimeout$inlined;
    final /* synthetic */ long $timeoutMillis$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ContextualEduViewModel$timeout$$inlined$flatMapLatest$1(Continuation continuation, long j, Object obj) {
        super(3, continuation);
        this.$timeoutMillis$inlined = j;
        this.$emitAfterTimeout$inlined = obj;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        ContextualEduViewModel$timeout$$inlined$flatMapLatest$1 contextualEduViewModel$timeout$$inlined$flatMapLatest$1 = new ContextualEduViewModel$timeout$$inlined$flatMapLatest$1((Continuation) obj3, this.$timeoutMillis$inlined, this.$emitAfterTimeout$inlined);
        contextualEduViewModel$timeout$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        contextualEduViewModel$timeout$$inlined$flatMapLatest$1.L$1 = obj2;
        return contextualEduViewModel$timeout$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            SafeFlow safeFlow = new SafeFlow(new ContextualEduViewModel$timeout$1$1(this.L$1, this.$timeoutMillis$inlined, this.$emitAfterTimeout$inlined, null));
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, safeFlow, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
