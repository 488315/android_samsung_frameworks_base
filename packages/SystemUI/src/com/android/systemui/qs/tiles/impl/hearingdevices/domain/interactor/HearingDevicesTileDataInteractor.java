package com.android.systemui.qs.tiles.impl.hearingdevices.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.accessibility.hearingaid.HearingDevicesChecker;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.hearingdevices.domain.model.HearingDevicesTileModel;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class HearingDevicesTileDataInteractor implements QSTileDataInteractor {
    public final CoroutineContext backgroundContext;
    public final BluetoothController bluetoothController;
    public final HearingDevicesChecker hearingDevicesChecker;

    /* renamed from: com.android.systemui.qs.tiles.impl.hearingdevices.domain.interactor.HearingDevicesTileDataInteractor$tileData$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = HearingDevicesTileDataInteractor.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                final HearingDevicesTileDataInteractor hearingDevicesTileDataInteractor = HearingDevicesTileDataInteractor.this;
                final ?? r1 = new BluetoothController.Callback() { // from class: com.android.systemui.qs.tiles.impl.hearingdevices.domain.interactor.HearingDevicesTileDataInteractor$tileData$1$callback$1
                    @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
                    public final void onBluetoothDevicesChanged() {
                        HearingDevicesChecker hearingDevicesChecker = hearingDevicesTileDataInteractor.hearingDevicesChecker;
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(new HearingDevicesTileModel(hearingDevicesChecker.isAnyActiveHearingDevice(), hearingDevicesChecker.isAnyPairedHearingDevice()));
                    }

                    @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
                    public final void onBluetoothStateChange(boolean z) {
                        HearingDevicesChecker hearingDevicesChecker = hearingDevicesTileDataInteractor.hearingDevicesChecker;
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(new HearingDevicesTileModel(hearingDevicesChecker.isAnyActiveHearingDevice(), hearingDevicesChecker.isAnyPairedHearingDevice()));
                    }
                };
                HearingDevicesTileDataInteractor.this.bluetoothController.addCallback(r1);
                final HearingDevicesTileDataInteractor hearingDevicesTileDataInteractor2 = HearingDevicesTileDataInteractor.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.hearingdevices.domain.interactor.HearingDevicesTileDataInteractor$tileData$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        hearingDevicesTileDataInteractor2.bluetoothController.removeCallback(r1);
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

    public HearingDevicesTileDataInteractor(CoroutineContext coroutineContext, BluetoothController bluetoothController, HearingDevicesChecker hearingDevicesChecker) {
        this.backgroundContext = coroutineContext;
        this.bluetoothController = bluetoothController;
        this.hearingDevicesChecker = hearingDevicesChecker;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        return FlowKt.distinctUntilChanged(FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1(null)), this.backgroundContext));
    }
}
