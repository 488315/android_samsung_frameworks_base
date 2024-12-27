package com.android.systemui.biometrics;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

final class UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerToAodTransitions$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UdfpsKeyguardViewControllerLegacy this$0;

    public UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerToAodTransitions$2(UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy, Continuation continuation) {
        super(2, continuation);
        this.this$0 = udfpsKeyguardViewControllerLegacy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerToAodTransitions$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerToAodTransitions$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            KeyguardTransitionInteractor keyguardTransitionInteractor = this.this$0.transitionInteractor;
            Edge.Companion companion = Edge.Companion;
            KeyguardState keyguardState = KeyguardState.ALTERNATE_BOUNCER;
            KeyguardState keyguardState2 = KeyguardState.AOD;
            companion.getClass();
            Flow transition = keyguardTransitionInteractor.transition((Edge) new Edge.StateToState(keyguardState, keyguardState2));
            final UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerToAodTransitions$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    UdfpsKeyguardViewControllerLegacy.this.view.onDozeAmountChanged(((TransitionStep) obj2).value, 1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (transition.collect(flowCollector, this) == coroutineSingletons) {
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
