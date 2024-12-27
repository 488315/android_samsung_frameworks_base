package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class KeyguardTransitionInteractor$isInTransitionWhere$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function2 $fromToStatePredicate;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionInteractor$isInTransitionWhere$3(Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$fromToStatePredicate = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardTransitionInteractor$isInTransitionWhere$3 keyguardTransitionInteractor$isInTransitionWhere$3 = new KeyguardTransitionInteractor$isInTransitionWhere$3(this.$fromToStatePredicate, continuation);
        keyguardTransitionInteractor$isInTransitionWhere$3.L$0 = obj;
        return keyguardTransitionInteractor$isInTransitionWhere$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardTransitionInteractor$isInTransitionWhere$3) create((TransitionStep) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        TransitionStep transitionStep = (TransitionStep) this.L$0;
        return Boolean.valueOf(transitionStep.transitionState != TransitionState.FINISHED && ((Boolean) this.$fromToStatePredicate.invoke(transitionStep.from, transitionStep.to)).booleanValue());
    }
}
