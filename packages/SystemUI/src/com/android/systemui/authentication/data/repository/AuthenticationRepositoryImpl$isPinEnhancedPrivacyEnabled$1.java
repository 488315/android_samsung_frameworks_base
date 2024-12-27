package com.android.systemui.authentication.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class AuthenticationRepositoryImpl$isPinEnhancedPrivacyEnabled$1 extends SuspendLambda implements Function2 {
    /* synthetic */ int I$0;
    int label;
    final /* synthetic */ AuthenticationRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthenticationRepositoryImpl$isPinEnhancedPrivacyEnabled$1(AuthenticationRepositoryImpl authenticationRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = authenticationRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AuthenticationRepositoryImpl$isPinEnhancedPrivacyEnabled$1 authenticationRepositoryImpl$isPinEnhancedPrivacyEnabled$1 = new AuthenticationRepositoryImpl$isPinEnhancedPrivacyEnabled$1(this.this$0, continuation);
        authenticationRepositoryImpl$isPinEnhancedPrivacyEnabled$1.I$0 = ((Number) obj).intValue();
        return authenticationRepositoryImpl$isPinEnhancedPrivacyEnabled$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AuthenticationRepositoryImpl$isPinEnhancedPrivacyEnabled$1) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return Boolean.valueOf(this.this$0.lockPatternUtils.isPinEnhancedPrivacyEnabled(this.I$0));
    }
}
