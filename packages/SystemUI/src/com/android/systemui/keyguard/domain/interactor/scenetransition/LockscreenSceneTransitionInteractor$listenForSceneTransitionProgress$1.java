package com.android.systemui.keyguard.domain.interactor.scenetransition;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.WithPrev;
import java.util.UUID;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

final class LockscreenSceneTransitionInteractor$listenForSceneTransitionProgress$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ LockscreenSceneTransitionInteractor this$0;

    public LockscreenSceneTransitionInteractor$listenForSceneTransitionProgress$1(LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = lockscreenSceneTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new LockscreenSceneTransitionInteractor$listenForSceneTransitionProgress$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LockscreenSceneTransitionInteractor$listenForSceneTransitionProgress$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow pairwise = FlowKt.pairwise(this.this$0.sceneInteractor.transitionState, new ObservableTransitionState.Idle(Scenes.Lockscreen));
            final LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$listenForSceneTransitionProgress$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object finishReversedTransitionTo;
                    WithPrev withPrev = (WithPrev) obj2;
                    ObservableTransitionState observableTransitionState = (ObservableTransitionState) withPrev.component1();
                    ObservableTransitionState observableTransitionState2 = (ObservableTransitionState) withPrev.component2();
                    boolean z = observableTransitionState2 instanceof ObservableTransitionState.Idle;
                    LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor2 = LockscreenSceneTransitionInteractor.this;
                    if (!z) {
                        if (!(observableTransitionState2 instanceof ObservableTransitionState.Transition)) {
                            return Unit.INSTANCE;
                        }
                        Object access$handleTransition = LockscreenSceneTransitionInteractor.access$handleTransition((ObservableTransitionState.Transition) observableTransitionState2, lockscreenSceneTransitionInteractor2, continuation);
                        return access$handleTransition == CoroutineSingletons.COROUTINE_SUSPENDED ? access$handleTransition : Unit.INSTANCE;
                    }
                    ObservableTransitionState.Idle idle = (ObservableTransitionState.Idle) observableTransitionState2;
                    if (lockscreenSceneTransitionInteractor2.currentTransitionId == null) {
                        finishReversedTransitionTo = Unit.INSTANCE;
                    } else if (observableTransitionState instanceof ObservableTransitionState.Transition) {
                        boolean areEqual = Intrinsics.areEqual(idle.currentScene, ((ObservableTransitionState.Transition) observableTransitionState).toScene);
                        KeyguardTransitionInteractor keyguardTransitionInteractor = lockscreenSceneTransitionInteractor2.transitionInteractor;
                        if (areEqual) {
                            UUID uuid = lockscreenSceneTransitionInteractor2.currentTransitionId;
                            Intrinsics.checkNotNull(uuid);
                            ((KeyguardTransitionRepositoryImpl) keyguardTransitionInteractor.repository).updateTransition(uuid, 1.0f, TransitionState.FINISHED);
                            lockscreenSceneTransitionInteractor2.resetTransitionData();
                            finishReversedTransitionTo = Unit.INSTANCE;
                        } else {
                            finishReversedTransitionTo = lockscreenSceneTransitionInteractor2.finishReversedTransitionTo(Intrinsics.areEqual(idle.currentScene, Scenes.Lockscreen) ? (KeyguardState) CollectionsKt___CollectionsKt.last(keyguardTransitionInteractor.startedKeyguardFromState.$$delegate_0.getReplayCache()) : KeyguardState.UNDEFINED, continuation);
                            if (finishReversedTransitionTo != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                finishReversedTransitionTo = Unit.INSTANCE;
                            }
                        }
                    } else {
                        finishReversedTransitionTo = Unit.INSTANCE;
                    }
                    return finishReversedTransitionTo == CoroutineSingletons.COROUTINE_SUSPENDED ? finishReversedTransitionTo : Unit.INSTANCE;
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
