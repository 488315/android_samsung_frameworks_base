package com.android.systemui.qs.tiles.impl.hearingdevices.domain.interactor;

import com.android.systemui.accessibility.hearingaid.HearingDevicesChecker;
import com.android.systemui.qs.tiles.impl.hearingdevices.domain.model.HearingDevicesTileModel;
import com.android.systemui.statusbar.policy.BluetoothController;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class HearingDevicesTileDataInteractor$tileData$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ HearingDevicesTileDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HearingDevicesTileDataInteractor$tileData$1(HearingDevicesTileDataInteractor hearingDevicesTileDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = hearingDevicesTileDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        HearingDevicesTileDataInteractor$tileData$1 hearingDevicesTileDataInteractor$tileData$1 = new HearingDevicesTileDataInteractor$tileData$1(this.this$0, continuation);
        hearingDevicesTileDataInteractor$tileData$1.L$0 = obj;
        return hearingDevicesTileDataInteractor$tileData$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((HearingDevicesTileDataInteractor$tileData$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.tiles.impl.hearingdevices.domain.interactor.HearingDevicesTileDataInteractor$tileData$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final HearingDevicesTileDataInteractor hearingDevicesTileDataInteractor = this.this$0;
            final ?? r1 = new BluetoothController.Callback() { // from class: com.android.systemui.qs.tiles.impl.hearingdevices.domain.interactor.HearingDevicesTileDataInteractor$tileData$1$callback$1
                @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
                public final void onBluetoothDevicesChanged() {
                    HearingDevicesChecker hearingDevicesChecker = hearingDevicesTileDataInteractor.hearingDevicesChecker;
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(new HearingDevicesTileModel(hearingDevicesChecker.isAnyActiveHearingDevice(), hearingDevicesChecker.isAnyPairedHearingDevice()));
                }

                @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
                public final void onBluetoothStateChange(boolean z) {
                    HearingDevicesChecker hearingDevicesChecker = hearingDevicesTileDataInteractor.hearingDevicesChecker;
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(new HearingDevicesTileModel(hearingDevicesChecker.isAnyActiveHearingDevice(), hearingDevicesChecker.isAnyPairedHearingDevice()));
                }
            };
            this.this$0.bluetoothController.addCallback(r1);
            final HearingDevicesTileDataInteractor hearingDevicesTileDataInteractor2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.hearingdevices.domain.interactor.HearingDevicesTileDataInteractor$tileData$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    HearingDevicesTileDataInteractor.this.bluetoothController.removeCallback(r1);
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
