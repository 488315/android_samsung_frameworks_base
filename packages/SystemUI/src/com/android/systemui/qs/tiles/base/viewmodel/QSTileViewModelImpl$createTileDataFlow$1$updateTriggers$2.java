package com.android.systemui.qs.tiles.base.viewmodel;

import com.android.systemui.qs.tiles.base.interactor.DataUpdateTrigger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ QSTileViewModelImpl this$0;

    public QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$2(QSTileViewModelImpl qSTileViewModelImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSTileViewModelImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$2) create((DataUpdateTrigger.ForceUpdate) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        QSTileViewModelImpl qSTileViewModelImpl = this.this$0;
        qSTileViewModelImpl.qsTileLogger.logForceUpdate(qSTileViewModelImpl.config.tileSpec);
        return Unit.INSTANCE;
    }
}
