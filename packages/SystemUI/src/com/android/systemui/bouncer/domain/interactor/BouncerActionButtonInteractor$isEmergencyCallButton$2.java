package com.android.systemui.bouncer.domain.interactor;

import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.authentication.shared.model.AuthenticationMethodModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class BouncerActionButtonInteractor$isEmergencyCallButton$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ BouncerActionButtonInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerActionButtonInteractor$isEmergencyCallButton$2(BouncerActionButtonInteractor bouncerActionButtonInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = bouncerActionButtonInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BouncerActionButtonInteractor$isEmergencyCallButton$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerActionButtonInteractor$isEmergencyCallButton$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AuthenticationInteractor authenticationInteractor = this.this$0.authenticationInteractor;
            this.label = 1;
            obj = authenticationInteractor.getAuthenticationMethod(this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Boolean.valueOf(((AuthenticationMethodModel) obj).isSecure);
    }
}
