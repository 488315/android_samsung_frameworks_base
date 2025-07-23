package com.android.systemui.aiagent;

import com.android.systemui.aiagent.AiAgentEffect;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AiAgentEffect$registerListener$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AiAgentEffect this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AiAgentEffect$registerListener$2(AiAgentEffect aiAgentEffect, Continuation continuation) {
        super(2, continuation);
        this.this$0 = aiAgentEffect;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AiAgentEffect$registerListener$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AiAgentEffect$registerListener$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final AiAgentEffect aiAgentEffect = this.this$0;
            Flow flow = aiAgentEffect.powerInteractor.isInteractive;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.aiagent.AiAgentEffect$registerListener$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    AiAgentEffect aiAgentEffect2 = AiAgentEffect.this;
                    synchronized (aiAgentEffect2.lock) {
                        aiAgentEffect2.setState(AiAgentEffect.State.copy$default(aiAgentEffect2.state, false, false, false, false, false, false, false, booleanValue, 127));
                    }
                    return Unit.INSTANCE;
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
