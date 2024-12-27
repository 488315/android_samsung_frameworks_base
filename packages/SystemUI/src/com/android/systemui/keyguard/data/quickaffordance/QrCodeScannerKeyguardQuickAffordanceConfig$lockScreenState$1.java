package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QrCodeScannerKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1(QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qrCodeScannerKeyguardQuickAffordanceConfig;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1 qrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1 = new QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        qrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return qrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1, com.android.systemui.qrcodescanner.controller.QRCodeScannerController$Callback] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig = this.this$0;
            final ?? r1 = new QRCodeScannerController.Callback() { // from class: com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1
                @Override // com.android.systemui.qrcodescanner.controller.QRCodeScannerController.Callback
                public final void onQRCodeScannerActivityChanged() {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, QrCodeScannerKeyguardQuickAffordanceConfig.access$state(qrCodeScannerKeyguardQuickAffordanceConfig), "QrCodeScannerKeyguardQuickAffordanceConfig");
                }

                @Override // com.android.systemui.qrcodescanner.controller.QRCodeScannerController.Callback
                public final void onQRCodeScannerPreferenceChanged() {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, ProducerScope.this, QrCodeScannerKeyguardQuickAffordanceConfig.access$state(qrCodeScannerKeyguardQuickAffordanceConfig), "QrCodeScannerKeyguardQuickAffordanceConfig");
                }
            };
            this.this$0.controller.addCallback((QRCodeScannerController.Callback) r1);
            this.this$0.controller.registerQRCodeScannerChangeObservers(0, 1);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            KeyguardQuickAffordanceConfig.LockScreenState access$state = QrCodeScannerKeyguardQuickAffordanceConfig.access$state(this.this$0);
            channelExt.getClass();
            ChannelExt.trySendWithFailureLogging(producerScope, access$state, "initial state", "QrCodeScannerKeyguardQuickAffordanceConfig");
            final QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    QrCodeScannerKeyguardQuickAffordanceConfig.this.controller.unregisterQRCodeScannerChangeObservers(0, 1);
                    QrCodeScannerKeyguardQuickAffordanceConfig.this.controller.removeCallback((QRCodeScannerController.Callback) r1);
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
