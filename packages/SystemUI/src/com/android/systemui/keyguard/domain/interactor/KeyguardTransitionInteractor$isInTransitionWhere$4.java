package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class KeyguardTransitionInteractor$isInTransitionWhere$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ Function1 $fromStatePredicate;
    final /* synthetic */ Function1 $toStatePredicate;
    /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionInteractor$isInTransitionWhere$4(Function1 function1, Function1 function12, Continuation continuation) {
        super(2, continuation);
        this.$fromStatePredicate = function1;
        this.$toStatePredicate = function12;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        KeyguardTransitionInteractor$isInTransitionWhere$4 keyguardTransitionInteractor$isInTransitionWhere$4 = new KeyguardTransitionInteractor$isInTransitionWhere$4(this.$fromStatePredicate, this.$toStatePredicate, continuation);
        keyguardTransitionInteractor$isInTransitionWhere$4.L$0 = obj;
        return keyguardTransitionInteractor$isInTransitionWhere$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((KeyguardTransitionInteractor$isInTransitionWhere$4) create((TransitionStep) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        TransitionStep transitionStep = (TransitionStep) this.L$0;
        return Boolean.valueOf(transitionStep.transitionState != TransitionState.FINISHED && ((Boolean) this.$fromStatePredicate.mo779invoke(transitionStep.from)).booleanValue() && ((Boolean) this.$toStatePredicate.mo779invoke(transitionStep.to)).booleanValue());
    }
}
