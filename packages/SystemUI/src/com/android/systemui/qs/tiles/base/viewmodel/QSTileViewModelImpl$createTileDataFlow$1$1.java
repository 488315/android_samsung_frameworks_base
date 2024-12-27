package com.android.systemui.qs.tiles.base.viewmodel;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class QSTileViewModelImpl$createTileDataFlow$1$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    int label;

    public QSTileViewModelImpl$createTileDataFlow$1$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        QSTileViewModelImpl$createTileDataFlow$1$1 qSTileViewModelImpl$createTileDataFlow$1$1 = new QSTileViewModelImpl$createTileDataFlow$1$1((Continuation) obj3);
        qSTileViewModelImpl$createTileDataFlow$1$1.L$0 = obj;
        return qSTileViewModelImpl$createTileDataFlow$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.L$0;
    }
}
