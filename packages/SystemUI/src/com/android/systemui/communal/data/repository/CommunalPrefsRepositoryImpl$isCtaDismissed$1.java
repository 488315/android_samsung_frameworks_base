package com.android.systemui.communal.data.repository;

import com.android.systemui.log.core.Logger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class CommunalPrefsRepositoryImpl$isCtaDismissed$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CommunalPrefsRepositoryImpl this$0;

    public CommunalPrefsRepositoryImpl$isCtaDismissed$1(CommunalPrefsRepositoryImpl communalPrefsRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = communalPrefsRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CommunalPrefsRepositoryImpl$isCtaDismissed$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CommunalPrefsRepositoryImpl$isCtaDismissed$1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Logger.i$default(this.this$0.logger, "Restored state for communal preferences.", null, 2, null);
        return Unit.INSTANCE;
    }
}
