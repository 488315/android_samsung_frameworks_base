package com.android.systemui.media.mediaoutput.controller.device;

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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class DisconnectedDeviceController$Companion$connectedDeviceChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LocalBluetoothManager $this_connectedDeviceChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DisconnectedDeviceController$Companion$connectedDeviceChanges$1(LocalBluetoothManager localBluetoothManager, Continuation continuation) {
        super(2, continuation);
        this.$this_connectedDeviceChanges = localBluetoothManager;
    }

    public static final Job invokeSuspend$updateDevices(ProducerScope producerScope, LocalBluetoothManager localBluetoothManager) {
        return BuildersKt.launch$default(producerScope, null, null, new DisconnectedDeviceController$Companion$connectedDeviceChanges$1$updateDevices$1(producerScope, localBluetoothManager, null), 3);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DisconnectedDeviceController$Companion$connectedDeviceChanges$1 disconnectedDeviceController$Companion$connectedDeviceChanges$1 = new DisconnectedDeviceController$Companion$connectedDeviceChanges$1(this.$this_connectedDeviceChanges, continuation);
        disconnectedDeviceController$Companion$connectedDeviceChanges$1.L$0 = obj;
        return disconnectedDeviceController$Companion$connectedDeviceChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DisconnectedDeviceController$Companion$connectedDeviceChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.bluetooth.BluetoothCallback, com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$Companion$connectedDeviceChanges$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final LocalBluetoothManager localBluetoothManager = this.$this_connectedDeviceChanges;
            final ?? r1 = new BluetoothCallback() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$Companion$connectedDeviceChanges$1$callback$1
                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i2) {
                    Log.d("DisconnectedDeviceController", "onAclConnectionStateChanged() - " + cachedBluetoothDevice + " - " + i2);
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i2) {
                    Log.d("DisconnectedDeviceController", "onActiveDeviceChanged() - " + cachedBluetoothDevice + " - " + i2);
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onAudioModeChanged() {
                    Log.d("DisconnectedDeviceController", "onAudioModeChanged()");
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onAutoOnStateChanged(int i2) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "onAutoOnStateChanged() - ", "DisconnectedDeviceController");
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onBluetoothStateChanged(int i2) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(i2, "onBluetoothStateChanged() - ", "DisconnectedDeviceController");
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i2) {
                    Log.d("DisconnectedDeviceController", "onConnectionStateChanged() - " + cachedBluetoothDevice + " - " + i2);
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
                    Log.d("DisconnectedDeviceController", "onDeviceAdded() - " + cachedBluetoothDevice);
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i2) {
                    Log.d("DisconnectedDeviceController", "onDeviceBondStateChanged() - " + cachedBluetoothDevice + " - " + i2);
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
                    Log.d("DisconnectedDeviceController", "onDeviceDeleted() - " + cachedBluetoothDevice);
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i2, int i3) {
                    StringBuilder sb = new StringBuilder("onProfileConnectionStateChanged() - ");
                    sb.append(cachedBluetoothDevice);
                    sb.append(" - ");
                    sb.append(i2);
                    sb.append(" - ");
                    RecyclerView$$ExternalSyntheticOutline0.m(i3, "DisconnectedDeviceController", sb);
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }

                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onScanningStateChanged(boolean z) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onScanningStateChanged() - ", "DisconnectedDeviceController", z);
                    DisconnectedDeviceController$Companion$connectedDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, localBluetoothManager);
                }
            };
            invokeSuspend$updateDevices(producerScope, this.$this_connectedDeviceChanges);
            this.$this_connectedDeviceChanges.mEventManager.registerCallback(r1);
            final LocalBluetoothManager localBluetoothManager2 = this.$this_connectedDeviceChanges;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$Companion$connectedDeviceChanges$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Log.d("DisconnectedDeviceController", "unregisterCallback");
                    BluetoothEventManager bluetoothEventManager = LocalBluetoothManager.this.mEventManager;
                    ((CopyOnWriteArrayList) bluetoothEventManager.mCallbacks).remove(r1);
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
