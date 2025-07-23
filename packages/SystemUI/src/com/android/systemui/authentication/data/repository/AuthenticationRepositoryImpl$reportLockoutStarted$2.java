package com.android.systemui.authentication.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AuthenticationRepositoryImpl$reportLockoutStarted$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $durationMs;
    int label;
    final /* synthetic */ AuthenticationRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthenticationRepositoryImpl$reportLockoutStarted$2(AuthenticationRepositoryImpl authenticationRepositoryImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = authenticationRepositoryImpl;
        this.$durationMs = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AuthenticationRepositoryImpl$reportLockoutStarted$2(this.this$0, this.$durationMs, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AuthenticationRepositoryImpl$reportLockoutStarted$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AuthenticationRepositoryImpl authenticationRepositoryImpl = this.this$0;
        authenticationRepositoryImpl.lockPatternUtils.reportPasswordLockout(this.$durationMs, authenticationRepositoryImpl.getSelectedUserId());
        return Unit.INSTANCE;
    }
}
