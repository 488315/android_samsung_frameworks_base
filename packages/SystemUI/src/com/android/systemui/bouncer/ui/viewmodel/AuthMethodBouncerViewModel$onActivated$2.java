package com.android.systemui.bouncer.ui.viewmodel;

import com.android.systemui.authentication.domain.interactor.AuthenticationResult;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.ui.viewmodel.AuthMethodBouncerViewModel;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AuthMethodBouncerViewModel$onActivated$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AuthMethodBouncerViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthMethodBouncerViewModel$onActivated$2(AuthMethodBouncerViewModel authMethodBouncerViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = authMethodBouncerViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AuthMethodBouncerViewModel$onActivated$2 authMethodBouncerViewModel$onActivated$2 = new AuthMethodBouncerViewModel$onActivated$2(this.this$0, continuation);
        authMethodBouncerViewModel$onActivated$2.L$0 = obj;
        return authMethodBouncerViewModel$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AuthMethodBouncerViewModel$onActivated$2) create((AuthMethodBouncerViewModel.AuthenticationRequest) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        AuthMethodBouncerViewModel.AuthenticationRequest authenticationRequest;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AuthMethodBouncerViewModel.AuthenticationRequest authenticationRequest2 = (AuthMethodBouncerViewModel.AuthenticationRequest) this.L$0;
            if (!((Boolean) this.this$0.isInputEnabled.getValue()).booleanValue()) {
                return Unit.INSTANCE;
            }
            BouncerInteractor bouncerInteractor = this.this$0.interactor;
            List list = authenticationRequest2.input;
            this.L$0 = authenticationRequest2;
            this.label = 1;
            Object authenticate = bouncerInteractor.authenticate(list, authenticationRequest2.useAutoConfirm, this);
            if (authenticate == coroutineSingletons) {
                return coroutineSingletons;
            }
            authenticationRequest = authenticationRequest2;
            obj = authenticate;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            authenticationRequest = (AuthMethodBouncerViewModel.AuthenticationRequest) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        AuthenticationResult authenticationResult = (AuthenticationResult) obj;
        if (authenticationResult == AuthenticationResult.SKIPPED && authenticationRequest.useAutoConfirm) {
            return Unit.INSTANCE;
        }
        this.this$0.getClass();
        StateFlowImpl stateFlowImpl = this.this$0._animateFailure;
        AuthenticationResult authenticationResult2 = AuthenticationResult.SUCCEEDED;
        stateFlowImpl.updateState(null, Boolean.valueOf(authenticationResult != authenticationResult2));
        this.this$0.clearInput();
        if (authenticationResult == authenticationResult2) {
            this.this$0.onSuccessfulAuthentication();
        }
        return Unit.INSTANCE;
    }
}
