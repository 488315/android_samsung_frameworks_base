package com.android.systemui.keyboard.stickykeys.data.repository;

import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class StickyKeysRepositoryImpl$stickyKeys$3 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ StickyKeysRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public StickyKeysRepositoryImpl$stickyKeys$3(StickyKeysRepositoryImpl stickyKeysRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = stickyKeysRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        StickyKeysRepositoryImpl$stickyKeys$3 stickyKeysRepositoryImpl$stickyKeys$3 = new StickyKeysRepositoryImpl$stickyKeys$3(this.this$0, continuation);
        stickyKeysRepositoryImpl$stickyKeys$3.L$0 = obj;
        return stickyKeysRepositoryImpl$stickyKeys$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((StickyKeysRepositoryImpl$stickyKeys$3) create((LinkedHashMap) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        this.this$0.stickyKeysLogger.logNewStickyKeysReceived((LinkedHashMap) this.L$0);
        return Unit.INSTANCE;
    }
}
