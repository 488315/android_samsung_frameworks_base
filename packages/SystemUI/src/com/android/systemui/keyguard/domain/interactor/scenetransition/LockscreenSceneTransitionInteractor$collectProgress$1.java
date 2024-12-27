package com.android.systemui.keyguard.domain.interactor.scenetransition;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class LockscreenSceneTransitionInteractor$collectProgress$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ObservableTransitionState.Transition $transition;
    int label;
    final /* synthetic */ LockscreenSceneTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockscreenSceneTransitionInteractor$collectProgress$1(ObservableTransitionState.Transition transition, LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.$transition = transition;
        this.this$0 = lockscreenSceneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LockscreenSceneTransitionInteractor$collectProgress$1(this.$transition, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LockscreenSceneTransitionInteractor$collectProgress$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = this.$transition.progress;
            final LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$collectProgress$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    float floatValue = ((Number) obj2).floatValue();
                    LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor2 = LockscreenSceneTransitionInteractor.this;
                    UUID uuid = lockscreenSceneTransitionInteractor2.currentTransitionId;
                    if (uuid != null) {
                        ((KeyguardTransitionRepositoryImpl) lockscreenSceneTransitionInteractor2.transitionInteractor.repository).updateTransition(uuid, RangesKt___RangesKt.coerceIn(floatValue, 0.0f, 1.0f), TransitionState.RUNNING);
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
