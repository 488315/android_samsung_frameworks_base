package com.android.systemui.qs.tiles.impl.qr.domain.interactor;

import android.content.Intent;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.qs.tiles.impl.qr.domain.model.QRCodeScannerTileModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class QRCodeScannerTileDataInteractor$tileData$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QRCodeScannerTileDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QRCodeScannerTileDataInteractor$tileData$2(QRCodeScannerTileDataInteractor qRCodeScannerTileDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qRCodeScannerTileDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QRCodeScannerTileDataInteractor$tileData$2 qRCodeScannerTileDataInteractor$tileData$2 = new QRCodeScannerTileDataInteractor$tileData$2(this.this$0, continuation);
        qRCodeScannerTileDataInteractor$tileData$2.L$0 = obj;
        return qRCodeScannerTileDataInteractor$tileData$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QRCodeScannerTileDataInteractor$tileData$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            QRCodeScannerController qRCodeScannerController = this.this$0.qrController;
            Intent intent = qRCodeScannerController.mIntent;
            Object available = (!qRCodeScannerController.isAbleToLaunchScannerActivity() || intent == null) ? QRCodeScannerTileModel.TemporarilyUnavailable.INSTANCE : new QRCodeScannerTileModel.Available(intent);
            this.label = 1;
            if (flowCollector.emit(available, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
