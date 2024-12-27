package com.android.systemui.qs.tiles.impl.qr.domain.interactor;

import android.content.Intent;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.qs.tiles.impl.qr.domain.model.QRCodeScannerTileModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class QRCodeScannerTileDataInteractor$tileData$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QRCodeScannerTileDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QRCodeScannerTileDataInteractor$tileData$1(QRCodeScannerTileDataInteractor qRCodeScannerTileDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qRCodeScannerTileDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QRCodeScannerTileDataInteractor$tileData$1 qRCodeScannerTileDataInteractor$tileData$1 = new QRCodeScannerTileDataInteractor$tileData$1(this.this$0, continuation);
        qRCodeScannerTileDataInteractor$tileData$1.L$0 = obj;
        return qRCodeScannerTileDataInteractor$tileData$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QRCodeScannerTileDataInteractor$tileData$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qrcodescanner.controller.QRCodeScannerController$Callback, com.android.systemui.qs.tiles.impl.qr.domain.interactor.QRCodeScannerTileDataInteractor$tileData$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            this.this$0.qrController.registerQRCodeScannerChangeObservers(0);
            final QRCodeScannerTileDataInteractor qRCodeScannerTileDataInteractor = this.this$0;
            final ?? r1 = new QRCodeScannerController.Callback() { // from class: com.android.systemui.qs.tiles.impl.qr.domain.interactor.QRCodeScannerTileDataInteractor$tileData$1$callback$1
                @Override // com.android.systemui.qrcodescanner.controller.QRCodeScannerController.Callback
                public final void onQRCodeScannerActivityChanged() {
                    QRCodeScannerController qRCodeScannerController = qRCodeScannerTileDataInteractor.qrController;
                    Intent intent = qRCodeScannerController.mIntent;
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU((!qRCodeScannerController.isAbleToLaunchScannerActivity() || intent == null) ? QRCodeScannerTileModel.TemporarilyUnavailable.INSTANCE : new QRCodeScannerTileModel.Available(intent));
                }
            };
            this.this$0.qrController.addCallback((QRCodeScannerController.Callback) r1);
            final QRCodeScannerTileDataInteractor qRCodeScannerTileDataInteractor2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.qr.domain.interactor.QRCodeScannerTileDataInteractor$tileData$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    QRCodeScannerTileDataInteractor.this.qrController.removeCallback((QRCodeScannerController.Callback) r1);
                    QRCodeScannerTileDataInteractor.this.qrController.unregisterQRCodeScannerChangeObservers(0);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
