package com.android.systemui.authentication.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AuthenticationRepositoryImpl$reportAuthenticationAttempt$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isSuccessful;
    Object L$0;
    int label;
    final /* synthetic */ AuthenticationRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthenticationRepositoryImpl$reportAuthenticationAttempt$2(boolean z, AuthenticationRepositoryImpl authenticationRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.$isSuccessful = z;
        this.this$0 = authenticationRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AuthenticationRepositoryImpl$reportAuthenticationAttempt$2(this.$isSuccessful, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AuthenticationRepositoryImpl$reportAuthenticationAttempt$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        MutableStateFlow mutableStateFlow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            if (this.$isSuccessful) {
                AuthenticationRepositoryImpl authenticationRepositoryImpl = this.this$0;
                authenticationRepositoryImpl.lockPatternUtils.userPresent(authenticationRepositoryImpl.getSelectedUserId());
                AuthenticationRepositoryImpl authenticationRepositoryImpl2 = this.this$0;
                authenticationRepositoryImpl2.lockPatternUtils.reportSuccessfulPasswordAttempt(authenticationRepositoryImpl2.getSelectedUserId());
                this.this$0._hasLockoutOccurred.updateState(null, Boolean.FALSE);
            } else {
                AuthenticationRepositoryImpl authenticationRepositoryImpl3 = this.this$0;
                authenticationRepositoryImpl3.lockPatternUtils.reportFailedPasswordAttempt(authenticationRepositoryImpl3.getSelectedUserId());
            }
            AuthenticationRepositoryImpl authenticationRepositoryImpl4 = this.this$0;
            StateFlowImpl stateFlowImpl = authenticationRepositoryImpl4._failedAuthenticationAttempts;
            this.L$0 = stateFlowImpl;
            this.label = 1;
            obj = BuildersKt.withContext(authenticationRepositoryImpl4.backgroundDispatcher, new AuthenticationRepositoryImpl$getFailedAuthenticationAttemptCount$2(authenticationRepositoryImpl4, null), this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
            mutableStateFlow = stateFlowImpl;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            mutableStateFlow = (MutableStateFlow) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        mutableStateFlow.setValue(obj);
        return Unit.INSTANCE;
    }
}
