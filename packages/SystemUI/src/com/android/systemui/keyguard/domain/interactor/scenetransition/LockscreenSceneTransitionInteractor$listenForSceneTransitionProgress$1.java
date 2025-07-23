package com.android.systemui.keyguard.domain.interactor.scenetransition;

import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.NoWhenBranchMatchedException;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class LockscreenSceneTransitionInteractor$listenForSceneTransitionProgress$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ LockscreenSceneTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
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
            Flow pairwise = FlowKt.pairwise(this.this$0.sceneInteractor.transitionState, new ObservableTransitionState.Idle(Scenes.Lockscreen, null, 2, null));
            final LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.scenetransition.LockscreenSceneTransitionInteractor$listenForSceneTransitionProgress$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Object finishCurrentTransition$1;
                    KeyguardState keyguardState;
                    WithPrev withPrev = (WithPrev) obj2;
                    ObservableTransitionState observableTransitionState = (ObservableTransitionState) withPrev.component1();
                    ObservableTransitionState observableTransitionState2 = (ObservableTransitionState) withPrev.component2();
                    boolean z = observableTransitionState2 instanceof ObservableTransitionState.Idle;
                    LockscreenSceneTransitionInteractor lockscreenSceneTransitionInteractor2 = LockscreenSceneTransitionInteractor.this;
                    if (!z) {
                        if (!(observableTransitionState2 instanceof ObservableTransitionState.Transition)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        Object access$handleTransition = LockscreenSceneTransitionInteractor.access$handleTransition((ObservableTransitionState.Transition) observableTransitionState2, lockscreenSceneTransitionInteractor2, continuation);
                        return access$handleTransition == CoroutineSingletons.COROUTINE_SUSPENDED ? access$handleTransition : Unit.INSTANCE;
                    }
                    ObservableTransitionState.Idle idle = (ObservableTransitionState.Idle) observableTransitionState2;
                    if (lockscreenSceneTransitionInteractor2.currentTransitionId == null) {
                        finishCurrentTransition$1 = Unit.INSTANCE;
                    } else if (observableTransitionState instanceof ObservableTransitionState.Transition) {
                        ObservableTransitionState.Transition transition = (ObservableTransitionState.Transition) observableTransitionState;
                        if (Intrinsics.areEqual(idle.currentScene, transition.toContent) || CollectionsKt___CollectionsKt.contains(idle.currentOverlays, transition.toContent)) {
                            finishCurrentTransition$1 = lockscreenSceneTransitionInteractor2.finishCurrentTransition$1(continuation);
                            if (finishCurrentTransition$1 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                finishCurrentTransition$1 = Unit.INSTANCE;
                            }
                        } else {
                            if (Intrinsics.areEqual(idle.currentScene, Scenes.Lockscreen)) {
                                keyguardState = (KeyguardState) lockscreenSceneTransitionInteractor2.repository.nextLockscreenTargetState.getValue();
                                if (keyguardState == null) {
                                    keyguardState = ((TransitionStep) lockscreenSceneTransitionInteractor2.transitionInteractor.startedKeyguardTransitionStep.$$delegate_0.getValue()).from;
                                }
                            } else {
                                keyguardState = KeyguardState.UNDEFINED;
                            }
                            finishCurrentTransition$1 = lockscreenSceneTransitionInteractor2.finishReversedTransitionTo$1(keyguardState, continuation);
                            if (finishCurrentTransition$1 != CoroutineSingletons.COROUTINE_SUSPENDED) {
                                finishCurrentTransition$1 = Unit.INSTANCE;
                            }
                        }
                    } else {
                        finishCurrentTransition$1 = Unit.INSTANCE;
                    }
                    return finishCurrentTransition$1 == CoroutineSingletons.COROUTINE_SUSPENDED ? finishCurrentTransition$1 : Unit.INSTANCE;
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
