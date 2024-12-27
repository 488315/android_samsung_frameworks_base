package com.android.systemui.media.mediaoutput.controller.device;

import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDeviceManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

final class MusicShareDeviceController$Companion$castEventChanges$1$updateDevices$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ProducerScope $$this$callbackFlow;
    final /* synthetic */ LocalBluetoothManager $this_castEventChanges;
    int label;

    public MusicShareDeviceController$Companion$castEventChanges$1$updateDevices$1(ProducerScope producerScope, LocalBluetoothManager localBluetoothManager, Continuation continuation) {
        super(2, continuation);
        this.$$this$callbackFlow = producerScope;
        this.$this_castEventChanges = localBluetoothManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MusicShareDeviceController$Companion$castEventChanges$1$updateDevices$1(this.$$this$callbackFlow, this.$this_castEventChanges, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MusicShareDeviceController$Companion$castEventChanges$1$updateDevices$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            SendChannel sendChannel = this.$$this$callbackFlow;
            CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager = this.$this_castEventChanges.mCachedCastDeviceManager;
            if (cachedBluetoothCastDeviceManager == null || (obj2 = CollectionsKt___CollectionsKt.toList(cachedBluetoothCastDeviceManager.getCachedCastDevicesCopy())) == null) {
                obj2 = EmptyList.INSTANCE;
            }
            this.label = 1;
            if (((ChannelCoroutine) sendChannel)._channel.send(obj2, this) == coroutineSingletons) {
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
