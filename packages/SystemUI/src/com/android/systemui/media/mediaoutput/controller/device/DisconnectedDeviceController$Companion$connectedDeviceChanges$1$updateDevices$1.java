package com.android.systemui.media.mediaoutput.controller.device;

import com.android.settingslib.bluetooth.LocalBluetoothManager;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

final class DisconnectedDeviceController$Companion$connectedDeviceChanges$1$updateDevices$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$callbackFlow;
    final /* synthetic */ LocalBluetoothManager $this_connectedDeviceChanges;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisconnectedDeviceController$Companion$connectedDeviceChanges$1$updateDevices$1(ProducerScope producerScope, LocalBluetoothManager localBluetoothManager, Continuation continuation) {
        super(2, continuation);
        this.$$this$callbackFlow = producerScope;
        this.$this_connectedDeviceChanges = localBluetoothManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DisconnectedDeviceController$Companion$connectedDeviceChanges$1$updateDevices$1(this.$$this$callbackFlow, this.$this_connectedDeviceChanges, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisconnectedDeviceController$Companion$connectedDeviceChanges$1$updateDevices$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SendChannel sendChannel = this.$$this$callbackFlow;
            List list = CollectionsKt___CollectionsKt.toList(this.$this_connectedDeviceChanges.mCachedDeviceManager.getCachedDevicesCopy());
            this.label = 1;
            if (((ChannelCoroutine) sendChannel)._channel.send(list, this) == coroutineSingletons) {
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
