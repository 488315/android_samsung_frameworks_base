package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class KeyguardTransitionInteractor$currentKeyguardState$1 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;

    public KeyguardTransitionInteractor$currentKeyguardState$1(Continuation continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardTransitionInteractor$currentKeyguardState$1 keyguardTransitionInteractor$currentKeyguardState$1 = new KeyguardTransitionInteractor$currentKeyguardState$1(continuation);
        keyguardTransitionInteractor$currentKeyguardState$1.L$0 = obj;
        return keyguardTransitionInteractor$currentKeyguardState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardTransitionInteractor$currentKeyguardState$1) create((TransitionStep) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        TransitionStep transitionStep = (TransitionStep) this.L$0;
        return transitionStep.transitionState == TransitionState.FINISHED ? transitionStep.to : transitionStep.from;
    }
}
