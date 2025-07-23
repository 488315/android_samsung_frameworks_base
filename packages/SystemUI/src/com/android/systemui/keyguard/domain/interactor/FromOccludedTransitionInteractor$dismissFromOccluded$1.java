package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class FromOccludedTransitionInteractor$dismissFromOccluded$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ FromOccludedTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromOccludedTransitionInteractor$dismissFromOccluded$1(FromOccludedTransitionInteractor fromOccludedTransitionInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromOccludedTransitionInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromOccludedTransitionInteractor$dismissFromOccluded$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromOccludedTransitionInteractor$dismissFromOccluded$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FromOccludedTransitionInteractor fromOccludedTransitionInteractor = this.this$0;
            KeyguardState keyguardState = KeyguardState.GONE;
            this.label = 1;
            if (TransitionInteractor.startTransitionTo$default(fromOccludedTransitionInteractor, keyguardState, null, null, "Dismiss from occluded", this, 6) == coroutineSingletons) {
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
