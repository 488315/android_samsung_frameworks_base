package com.android.systemui.communal.domain.interactor;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.communal.shared.model.CommunalScenes;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.NoWhenBranchMatchedException;
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
/* loaded from: classes2.dex */
final class CommunalSceneTransitionInteractor$listenForSceneTransitionProgress$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalSceneTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSceneTransitionInteractor$listenForSceneTransitionProgress$1(CommunalSceneTransitionInteractor communalSceneTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalSceneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalSceneTransitionInteractor$listenForSceneTransitionProgress$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSceneTransitionInteractor$listenForSceneTransitionProgress$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow pairwise = FlowKt.pairwise(this.this$0.sceneInteractor.transitionState, new ObservableTransitionState.Idle(CommunalScenes.Blank, null, 2, null));
            final CommunalSceneTransitionInteractor communalSceneTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$listenForSceneTransitionProgress$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    WithPrev withPrev = (WithPrev) obj2;
                    ObservableTransitionState observableTransitionState = (ObservableTransitionState) withPrev.component1();
                    ObservableTransitionState observableTransitionState2 = (ObservableTransitionState) withPrev.component2();
                    boolean z = observableTransitionState2 instanceof ObservableTransitionState.Idle;
                    CommunalSceneTransitionInteractor communalSceneTransitionInteractor2 = CommunalSceneTransitionInteractor.this;
                    if (z) {
                        Object access$handleIdle = CommunalSceneTransitionInteractor.access$handleIdle(communalSceneTransitionInteractor2, observableTransitionState, (ObservableTransitionState.Idle) observableTransitionState2, continuation);
                        return access$handleIdle == CoroutineSingletons.COROUTINE_SUSPENDED ? access$handleIdle : Unit.INSTANCE;
                    }
                    if (!(observableTransitionState2 instanceof ObservableTransitionState.Transition)) {
                        throw new NoWhenBranchMatchedException();
                    }
                    Object access$handleTransition = CommunalSceneTransitionInteractor.access$handleTransition(communalSceneTransitionInteractor2, observableTransitionState, (ObservableTransitionState.Transition) observableTransitionState2, continuation);
                    return access$handleTransition == CoroutineSingletons.COROUTINE_SUSPENDED ? access$handleTransition : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (pairwise.collect(flowCollector, this) == coroutineSingletons) {
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
