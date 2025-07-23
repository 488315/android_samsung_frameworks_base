package com.android.systemui.authentication.domain.interactor;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AuthenticationInteractor$reportUnlockAttempt$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $isMatched;
    int label;
    final /* synthetic */ AuthenticationInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthenticationInteractor$reportUnlockAttempt$1(AuthenticationInteractor authenticationInteractor, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = authenticationInteractor;
        this.$isMatched = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AuthenticationInteractor$reportUnlockAttempt$1(this.this$0, this.$isMatched, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AuthenticationInteractor$reportUnlockAttempt$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0040, code lost:
    
        if (r5.emit(r1, r4) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0042, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002d, code lost:
    
        if (((com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r5).reportAuthenticationAttemptFromPrimaryBouncer(r1, r4) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r5)
            goto L43
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L30
        L1c:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r5 = r4.this$0
            com.android.systemui.authentication.data.repository.AuthenticationRepository r5 = r5.repository
            boolean r1 = r4.$isMatched
            r4.label = r3
            com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl r5 = (com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl) r5
            java.lang.Object r5 = r5.reportAuthenticationAttemptFromPrimaryBouncer(r1, r4)
            if (r5 != r0) goto L30
            goto L42
        L30:
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r5 = r4.this$0
            kotlinx.coroutines.flow.SharedFlowImpl r5 = r5._onPrimaryBouncerAuthenticationResult
            boolean r1 = r4.$isMatched
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            r4.label = r2
            java.lang.Object r4 = r5.emit(r1, r4)
            if (r4 != r0) goto L43
        L42:
            return r0
        L43:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.authentication.domain.interactor.AuthenticationInteractor$reportUnlockAttempt$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
