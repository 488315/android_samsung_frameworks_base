package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.BuildScopeImpl$events$1$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class BuildScope$toEvents$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Flow $this_toEvents;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuildScope$toEvents$1(Flow flow, Continuation continuation) {
        super(2, continuation);
        this.$this_toEvents = flow;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BuildScope$toEvents$1 buildScope$toEvents$1 = new BuildScope$toEvents$1(this.$this_toEvents, continuation);
        buildScope$toEvents$1.L$0 = obj;
        return buildScope$toEvents$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuildScope$toEvents$1) create((EventProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final EventProducerScope eventProducerScope = (EventProducerScope) this.L$0;
            Flow flow = this.$this_toEvents;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.kairos.BuildScope$toEvents$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object emit = ((BuildScopeImpl$events$1$1) EventProducerScope.this).$events.emit(obj2, continuation);
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (emit != coroutineSingletons2) {
                        emit = Unit.INSTANCE;
                    }
                    return emit == coroutineSingletons2 ? emit : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
