package com.android.systemui.communal.domain.interactor;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import java.util.UUID;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalSceneTransitionInteractor$collectProgress$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ObservableTransitionState.Transition $transition;
    int label;
    final /* synthetic */ CommunalSceneTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalSceneTransitionInteractor$collectProgress$1(ObservableTransitionState.Transition transition, CommunalSceneTransitionInteractor communalSceneTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.$transition = transition;
        this.this$0 = communalSceneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalSceneTransitionInteractor$collectProgress$1(this.$transition, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalSceneTransitionInteractor$collectProgress$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = this.$transition.progress;
            final CommunalSceneTransitionInteractor communalSceneTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.communal.domain.interactor.CommunalSceneTransitionInteractor$collectProgress$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object updateTransition;
                    float floatValue = ((Number) obj2).floatValue();
                    CommunalSceneTransitionInteractor communalSceneTransitionInteractor2 = CommunalSceneTransitionInteractor.this;
                    UUID uuid = communalSceneTransitionInteractor2.currentTransitionId;
                    if (uuid == null) {
                        updateTransition = Unit.INSTANCE;
                    } else {
                        updateTransition = communalSceneTransitionInteractor2.internalTransitionInteractor.updateTransition(uuid, RangesKt___RangesKt.coerceIn(floatValue, 0.0f, 1.0f), TransitionState.RUNNING, continuation);
                        if (updateTransition != CoroutineSingletons.COROUTINE_SUSPENDED) {
                            updateTransition = Unit.INSTANCE;
                        }
                    }
                    return updateTransition == CoroutineSingletons.COROUTINE_SUSPENDED ? updateTransition : Unit.INSTANCE;
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
