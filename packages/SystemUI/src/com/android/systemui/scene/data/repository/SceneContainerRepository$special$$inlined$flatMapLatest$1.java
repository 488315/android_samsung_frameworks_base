package com.android.systemui.scene.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SceneContainerRepository$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ SceneContainerRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SceneContainerRepository$special$$inlined$flatMapLatest$1(Continuation continuation, SceneContainerRepository sceneContainerRepository) {
        super(3, continuation);
        this.this$0 = sceneContainerRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SceneContainerRepository$special$$inlined$flatMapLatest$1 sceneContainerRepository$special$$inlined$flatMapLatest$1 = new SceneContainerRepository$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        sceneContainerRepository$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        sceneContainerRepository$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return sceneContainerRepository$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flow = (Flow) this.L$1;
            if (flow == null) {
                flow = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(this.this$0.defaultTransitionState);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
