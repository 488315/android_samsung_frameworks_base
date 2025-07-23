package com.android.systemui.authentication.data.repository;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.authentication.shared.model.AuthenticationResultModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AuthenticationRepositoryImpl$checkCredential$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ LockscreenCredential $credential;
    int label;
    final /* synthetic */ AuthenticationRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AuthenticationRepositoryImpl$checkCredential$2(AuthenticationRepositoryImpl authenticationRepositoryImpl, LockscreenCredential lockscreenCredential, Continuation continuation) {
        super(2, continuation);
        this.this$0 = authenticationRepositoryImpl;
        this.$credential = lockscreenCredential;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AuthenticationRepositoryImpl$checkCredential$2(this.this$0, this.$credential, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AuthenticationRepositoryImpl$checkCredential$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        try {
            AuthenticationRepositoryImpl authenticationRepositoryImpl = this.this$0;
            return new AuthenticationResultModel(authenticationRepositoryImpl.lockPatternUtils.checkCredential(this.$credential, authenticationRepositoryImpl.getSelectedUserId(), new LockPatternUtils.CheckCredentialProgressCallback() { // from class: com.android.systemui.authentication.data.repository.AuthenticationRepositoryImpl$checkCredential$2$matched$1
                public final void onEarlyMatched() {
                }
            }), 0);
        } catch (LockPatternUtils.RequestThrottledException e) {
            return new AuthenticationResultModel(false, e.getTimeoutMs());
        }
    }
}
