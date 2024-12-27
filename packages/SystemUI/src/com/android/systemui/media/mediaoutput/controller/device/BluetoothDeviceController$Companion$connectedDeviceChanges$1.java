package com.android.systemui.media.mediaoutput.controller.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class BluetoothDeviceController$Companion$connectedDeviceChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LocalBluetoothManager $this_connectedDeviceChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BluetoothDeviceController$Companion$connectedDeviceChanges$1(LocalBluetoothManager localBluetoothManager, Continuation continuation) {
        super(2, continuation);
        this.$this_connectedDeviceChanges = localBluetoothManager;
    }

    public static final Job invokeSuspend$updateDevices(ProducerScope producerScope, LocalBluetoothManager localBluetoothManager) {
        return BuildersKt.launch$default(producerScope, null, null, new BluetoothDeviceController$Companion$connectedDeviceChanges$1$updateDevices$1(producerScope, localBluetoothManager, null), 3);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BluetoothDeviceController$Companion$connectedDeviceChanges$1 bluetoothDeviceController$Companion$connectedDeviceChanges$1 = new BluetoothDeviceController$Companion$connectedDeviceChanges$1(this.$this_connectedDeviceChanges, continuation);
        bluetoothDeviceController$Companion$connectedDeviceChanges$1.L$0 = obj;
        return bluetoothDeviceController$Companion$connectedDeviceChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BluetoothDeviceController$Companion$connectedDeviceChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.bluetooth.BluetoothCallback, com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$Companion$connectedDeviceChanges$1$callback$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.content.BroadcastReceiver, com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$Companion$connectedDeviceChanges$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final LocalBluetoothManager localBluetoothManager = this.$this_connectedDeviceChanges;
            final ?? r1 = new BluetoothCallback() { // from class: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$Companion$connectedDeviceChanges$1$callback$1
                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i2) {
                    Log.d("BluetoothDeviceController", "onAclConnectionStateChanged() - " + cachedBluetoothDevice + " - " + i2);
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i2) {
                    Log.d("BluetoothDeviceController", "onActiveDeviceChanged() - " + cachedBluetoothDevice + " - " + i2);
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onAudioModeChanged() {
                    Log.d("BluetoothDeviceController", "onAudioModeChanged()");
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onAutoOnStateChanged(int i2) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "onAutoOnStateChanged() - ", "BluetoothDeviceController");
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onBluetoothStateChanged(int i2) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "onBluetoothStateChanged() - ", "BluetoothDeviceController");
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i2) {
                    Log.d("BluetoothDeviceController", "onConnectionStateChanged() - " + cachedBluetoothDevice + " - " + i2);
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
                    Log.d("BluetoothDeviceController", "onDeviceAdded() - " + cachedBluetoothDevice);
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i2) {
                    Log.d("BluetoothDeviceController", "onDeviceBondStateChanged() - " + cachedBluetoothDevice + " - " + i2);
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
                    Log.d("BluetoothDeviceController", "onDeviceDeleted() - " + cachedBluetoothDevice);
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i2, int i3) {
                    StringBuilder sb = new StringBuilder("onProfileConnectionStateChanged() - ");
                    sb.append(cachedBluetoothDevice);
                    sb.append(" - ");
                    sb.append(i2);
                    sb.append(" - ");
                    RecyclerView$$ExternalSyntheticOutline0.m(i3, "BluetoothDeviceController", sb);
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onScanningStateChanged(boolean z) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onScanningStateChanged() - ", "BluetoothDeviceController", z);
                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }
            };
            final LocalBluetoothManager localBluetoothManager2 = this.$this_connectedDeviceChanges;
            final ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$Companion$connectedDeviceChanges$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Log.d("BluetoothDeviceController", "onReceive() - " + intent);
                    String action = intent.getAction();
                    if (action != null) {
                        switch (action.hashCode()) {
                            case -1940635523:
                                if (!action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                                    return;
                                }
                                break;
                            case -1513304802:
                                if (action.equals("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED")) {
                                    BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager2);
                                    return;
                                }
                                return;
                            case -1315844839:
                                if (!action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) {
                                    return;
                                }
                                break;
                            case 1920758225:
                                if (!action.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                                    return;
                                }
                                break;
                            default:
                                return;
                        }
                        if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
                            BluetoothDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager2);
                        }
                    }
                }
            };
            this.$this_connectedDeviceChanges.mEventManager.registerCallback(r1);
            Context context = this.$this_connectedDeviceChanges.mContext;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
            Unit unit = Unit.INSTANCE;
            context.registerReceiver(r3, intentFilter);
            this.$this_connectedDeviceChanges.mEventManager.readPairedDevices();
            invokeSuspend$updateDevices(producerScope, this.$this_connectedDeviceChanges);
            final LocalBluetoothManager localBluetoothManager3 = this.$this_connectedDeviceChanges;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$Companion$connectedDeviceChanges$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Log.d("BluetoothDeviceController", "unregisterCallback");
                    BluetoothEventManager bluetoothEventManager = LocalBluetoothManager.this.mEventManager;
                    ((CopyOnWriteArrayList) bluetoothEventManager.mCallbacks).remove(r1);
                    LocalBluetoothManager.this.mContext.unregisterReceiver(r3);
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
