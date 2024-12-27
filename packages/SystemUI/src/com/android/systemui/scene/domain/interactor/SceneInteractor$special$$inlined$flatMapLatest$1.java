package com.android.systemui.scene.domain.interactor;

import com.android.compose.animation.scene.ObservableTransitionState;
import kotlin.NoWhenBranchMatchedException;
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

public final class SceneInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public SceneInteractor$special$$inlined$flatMapLatest$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        SceneInteractor$special$$inlined$flatMapLatest$1 sceneInteractor$special$$inlined$flatMapLatest$1 = new SceneInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3);
        sceneInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        sceneInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return sceneInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ObservableTransitionState observableTransitionState = (ObservableTransitionState) this.L$1;
            if (observableTransitionState instanceof ObservableTransitionState.Transition) {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = ((ObservableTransitionState.Transition) observableTransitionState).isUserInputOngoing;
            } else {
                if (!(observableTransitionState instanceof ObservableTransitionState.Idle)) {
                    throw new NoWhenBranchMatchedException();
                }
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
