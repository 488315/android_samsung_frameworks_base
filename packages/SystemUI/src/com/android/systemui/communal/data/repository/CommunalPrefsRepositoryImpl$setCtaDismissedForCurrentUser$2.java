package com.android.systemui.communal.data.repository;

import com.android.systemui.log.core.Logger;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

final class CommunalPrefsRepositoryImpl$setCtaDismissedForCurrentUser$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalPrefsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommunalPrefsRepositoryImpl$setCtaDismissedForCurrentUser$2(CommunalPrefsRepositoryImpl communalPrefsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalPrefsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalPrefsRepositoryImpl$setCtaDismissedForCurrentUser$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalPrefsRepositoryImpl$setCtaDismissedForCurrentUser$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CommunalPrefsRepositoryImpl communalPrefsRepositoryImpl = this.this$0;
        communalPrefsRepositoryImpl.getSharedPrefsForUser(((UserRepositoryImpl) communalPrefsRepositoryImpl.userRepository).getSelectedUserInfo()).edit().putBoolean("cta_dismissed", true).apply();
        Logger.i$default(this.this$0.logger, "Dismissed CTA tile", null, 2, null);
        return Unit.INSTANCE;
    }
}
