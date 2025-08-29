package com.android.systemui.qs.tiles.impl.qr.domain.interactor;

import android.content.Intent;
import android.os.UserHandle;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.qr.domain.model.QRCodeScannerTileModel;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class QRCodeScannerTileDataInteractor implements QSTileDataInteractor {
    public final CoroutineContext bgCoroutineContext;
    public final QRCodeScannerController qrController;

    /* renamed from: com.android.systemui.qs.tiles.impl.qr.domain.interactor.QRCodeScannerTileDataInteractor$tileData$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = QRCodeScannerTileDataInteractor.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                QRCodeScannerTileDataInteractor.this.qrController.registerQRCodeScannerChangeObservers(0);
                final QRCodeScannerTileDataInteractor qRCodeScannerTileDataInteractor = QRCodeScannerTileDataInteractor.this;
                final ?? r1 = new QRCodeScannerController.Callback() { // from class: com.android.systemui.qs.tiles.impl.qr.domain.interactor.QRCodeScannerTileDataInteractor$tileData$1$callback$1
                    @Override // com.android.systemui.qrcodescanner.controller.QRCodeScannerController.Callback
                    public final void onQRCodeScannerActivityChanged() {
                        QRCodeScannerController qRCodeScannerController = qRCodeScannerTileDataInteractor.qrController;
                        Intent intent = qRCodeScannerController.mIntent;
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU((!qRCodeScannerController.isAbleToLaunchScannerActivity() || intent == null) ? QRCodeScannerTileModel.TemporarilyUnavailable.INSTANCE : new QRCodeScannerTileModel.Available(intent));
                    }
                };
                QRCodeScannerTileDataInteractor.this.qrController.addCallback((QRCodeScannerController.Callback) r1);
                final QRCodeScannerTileDataInteractor qRCodeScannerTileDataInteractor2 = QRCodeScannerTileDataInteractor.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.qr.domain.interactor.QRCodeScannerTileDataInteractor$tileData$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        QRCodeScannerTileDataInteractor qRCodeScannerTileDataInteractor3 = qRCodeScannerTileDataInteractor2;
                        qRCodeScannerTileDataInteractor3.qrController.removeCallback((QRCodeScannerController.Callback) r1);
                        qRCodeScannerTileDataInteractor3.qrController.unregisterQRCodeScannerChangeObservers(0);
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

    /* renamed from: com.android.systemui.qs.tiles.impl.qr.domain.interactor.QRCodeScannerTileDataInteractor$tileData$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = QRCodeScannerTileDataInteractor.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                QRCodeScannerController qRCodeScannerController = QRCodeScannerTileDataInteractor.this.qrController;
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

    public QRCodeScannerTileDataInteractor(CoroutineContext coroutineContext, CoroutineScope coroutineScope, QRCodeScannerController qRCodeScannerController) {
        this.bgCoroutineContext = coroutineContext;
        this.qrController = qRCodeScannerController;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.valueOf(this.qrController.isCameraAvailable()));
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(null), FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(null))), this.bgCoroutineContext);
    }
}
