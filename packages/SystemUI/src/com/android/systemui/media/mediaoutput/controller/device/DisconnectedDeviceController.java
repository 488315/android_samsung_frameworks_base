package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.DisconnectedDevice;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class DisconnectedDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final LocalBluetoothManager localBluetoothManager;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DisconnectedDeviceController.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LocalBluetoothManager localBluetoothManager = DisconnectedDeviceController.this.localBluetoothManager;
                if (localBluetoothManager != null) {
                    DisconnectedDeviceController.Companion.getClass();
                    Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new DisconnectedDeviceController$Companion$connectedDeviceChanges$1(localBluetoothManager, null)), -1);
                    if (buffer$default != null) {
                        Flow debounce = FlowKt.debounce(buffer$default, 50L);
                        final DisconnectedDeviceController disconnectedDeviceController = DisconnectedDeviceController.this;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Companion companion = DisconnectedDeviceController.Companion;
                                Object updateDevices$2 = DisconnectedDeviceController.this.updateDevices$2((List) obj2, continuation);
                                return updateDevices$2 == CoroutineSingletons.COROUTINE_SUSPENDED ? updateDevices$2 : Unit.INSTANCE;
                            }
                        };
                        this.label = 1;
                        if (debounce.collect(flowCollector, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
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

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DisconnectedDeviceController(LocalBluetoothManager localBluetoothManager) {
        this.localBluetoothManager = localBluetoothManager;
        Log.d("DisconnectedDeviceController", "init()");
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("DisconnectedDeviceController", "close()");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void transfer(AudioDevice audioDevice) {
        LocalBluetoothManager localBluetoothManager;
        LocalBluetoothProfileManager localBluetoothProfileManager;
        A2dpProfile a2dpProfile;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "DisconnectedDeviceController");
        if (!(audioDevice instanceof DisconnectedDevice) || (localBluetoothManager = this.localBluetoothManager) == null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) == null) {
            return;
        }
        CachedBluetoothDevice cachedBluetoothDevice = ((DisconnectedDevice) audioDevice).cachedBluetoothDevice;
        if (cachedBluetoothDevice == null) {
            cachedBluetoothDevice = null;
        }
        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
        BluetoothA2dp bluetoothA2dp = a2dpProfile.mService;
        if (bluetoothA2dp == null) {
            return;
        }
        bluetoothA2dp.semConnect(bluetoothDevice);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00cc A[LOOP:1: B:31:0x00c6->B:33:0x00cc, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0112 A[LOOP:2: B:36:0x010c->B:38:0x0112, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0138 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARN: Type inference failed for: r7v0, types: [kotlin.collections.EmptyList] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r7v7, types: [java.util.ArrayList] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateDevices$2(java.util.List r21, kotlin.coroutines.Continuation r22) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController.updateDevices$2(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
