package com.android.systemui.qs.tiles.base.ui.viewmodel;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.tiles.base.domain.model.DataUpdateTrigger;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QSTileViewModelImpl$createTileDataFlow$1$1$updateTriggers$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ QSTileViewModelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSTileViewModelImpl$createTileDataFlow$1$1$updateTriggers$2(QSTileViewModelImpl qSTileViewModelImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSTileViewModelImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new QSTileViewModelImpl$createTileDataFlow$1$1$updateTriggers$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSTileViewModelImpl$createTileDataFlow$1$1$updateTriggers$2) create((DataUpdateTrigger.ForceUpdate) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        QSTileViewModelImpl qSTileViewModelImpl = this.this$0;
        QSTileLogger qSTileLogger = qSTileViewModelImpl.qsTileLogger;
        TileSpec tileSpec = qSTileViewModelImpl.config.tileSpec;
        LogBuffer logBuffer = qSTileLogger.getLogBuffer(tileSpec);
        logBuffer.commit(logBuffer.obtain(QSTileLogger.getLogTag(tileSpec), LogLevel.DEBUG, new QSTileLogger$$ExternalSyntheticLambda0(3), null));
        return Unit.INSTANCE;
    }
}
