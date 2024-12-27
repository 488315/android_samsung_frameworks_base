package com.android.systemui.qs.tiles.base.viewmodel;

import com.android.systemui.qs.tiles.base.interactor.DataUpdateTrigger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$3 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QSTileViewModelImpl this$0;

    public QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$3(QSTileViewModelImpl qSTileViewModelImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSTileViewModelImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$3 qSTileViewModelImpl$createTileDataFlow$1$updateTriggers$3 = new QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$3(this.this$0, continuation);
        qSTileViewModelImpl$createTileDataFlow$1$updateTriggers$3.L$0 = obj;
        return qSTileViewModelImpl$createTileDataFlow$1$updateTriggers$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSTileViewModelImpl$createTileDataFlow$1$updateTriggers$3) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            DataUpdateTrigger.InitialRequest initialRequest = DataUpdateTrigger.InitialRequest.INSTANCE;
            this.label = 1;
            if (flowCollector.emit(initialRequest, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        QSTileViewModelImpl qSTileViewModelImpl = this.this$0;
        qSTileViewModelImpl.qsTileLogger.logInitialRequest(qSTileViewModelImpl.config.tileSpec);
        return Unit.INSTANCE;
    }
}
