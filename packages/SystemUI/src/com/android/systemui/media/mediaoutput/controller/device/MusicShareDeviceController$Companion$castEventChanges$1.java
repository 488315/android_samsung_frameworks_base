package com.android.systemui.media.mediaoutput.controller.device;

import android.util.Log;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class MusicShareDeviceController$Companion$castEventChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LocalBluetoothManager $this_castEventChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MusicShareDeviceController$Companion$castEventChanges$1(LocalBluetoothManager localBluetoothManager, Continuation continuation) {
        super(2, continuation);
        this.$this_castEventChanges = localBluetoothManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MusicShareDeviceController$Companion$castEventChanges$1 musicShareDeviceController$Companion$castEventChanges$1 = new MusicShareDeviceController$Companion$castEventChanges$1(this.$this_castEventChanges, continuation);
        musicShareDeviceController$Companion$castEventChanges$1.L$0 = obj;
        return musicShareDeviceController$Companion$castEventChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MusicShareDeviceController$Companion$castEventChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$Companion$castEventChanges$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final LocalBluetoothManager localBluetoothManager = this.$this_castEventChanges;
            final ?? r1 = new BluetoothCastCallback() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$Companion$castEventChanges$1$callback$1
                @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
                public final void onCastDeviceAdded() {
                    Log.d("MusicShareDeviceController", "onCastDeviceAdded()");
                    BuildersKt.launch$default(r0, null, null, new MusicShareDeviceController$Companion$castEventChanges$1$updateDevices$1(ProducerScope.this, localBluetoothManager, null), 3);
                }

                @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
                public final void onCastDeviceRemoved() {
                    Log.d("MusicShareDeviceController", "onCastDeviceRemoved()");
                    BuildersKt.launch$default(r0, null, null, new MusicShareDeviceController$Companion$castEventChanges$1$updateDevices$1(ProducerScope.this, localBluetoothManager, null), 3);
                }

                @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
                public final void onCastDiscoveryStateChanged(boolean z) {
                    KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onCastDiscoveryStateChanged() - ", "MusicShareDeviceController", z);
                    BuildersKt.launch$default(r3, null, null, new MusicShareDeviceController$Companion$castEventChanges$1$updateDevices$1(ProducerScope.this, localBluetoothManager, null), 3);
                }

                @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
                public final void onCastProfileStateChanged(CachedBluetoothCastDevice cachedBluetoothCastDevice) {
                    Log.d("MusicShareDeviceController", "onCastProfileStateChanged()");
                    BuildersKt.launch$default(r2, null, null, new MusicShareDeviceController$Companion$castEventChanges$1$updateDevices$1(ProducerScope.this, localBluetoothManager, null), 3);
                }
            };
            BluetoothCastEventManager bluetoothCastEventManager = this.$this_castEventChanges.mCastEventManager;
            synchronized (bluetoothCastEventManager.mCallbacks) {
                ((ArrayList) bluetoothCastEventManager.mCallbacks).add(r1);
            }
            final LocalBluetoothManager localBluetoothManager2 = this.$this_castEventChanges;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$Companion$castEventChanges$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Log.d("MusicShareDeviceController", "unregisterCallback");
                    BluetoothCastEventManager bluetoothCastEventManager2 = LocalBluetoothManager.this.mCastEventManager;
                    MusicShareDeviceController$Companion$castEventChanges$1$callback$1 musicShareDeviceController$Companion$castEventChanges$1$callback$1 = r1;
                    synchronized (bluetoothCastEventManager2.mCallbacks) {
                        ((ArrayList) bluetoothCastEventManager2.mCallbacks).remove(musicShareDeviceController$Companion$castEventChanges$1$callback$1);
                    }
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
