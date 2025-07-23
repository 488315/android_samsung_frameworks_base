package com.android.systemui.communal.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CommunalInteractor$setDisclaimerDismissed$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalInteractor$setDisclaimerDismissed$1(CommunalInteractor communalInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalInteractor$setDisclaimerDismissed$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalInteractor$setDisclaimerDismissed$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.this$0._isDisclaimerDismissed.updateState(null, Boolean.TRUE);
            CommunalInteractor.Companion.getClass();
            long j = CommunalInteractor.DISCLAIMER_RESET_MILLIS;
            this.label = 1;
            if (DelayKt.m3449delayVtjQ1oo(j, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        this.this$0._isDisclaimerDismissed.updateState(null, Boolean.FALSE);
        return Unit.INSTANCE;
    }
}
