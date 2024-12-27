package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.MusicShareDevice;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BluetoothDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final ContentResolver cr;
    public final boolean isDualAudioSupported;
    public final LocalBluetoothManager localBluetoothManager;
    public Job updateJob;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BluetoothDeviceController.this.new AnonymousClass1(continuation);
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
                LocalBluetoothManager localBluetoothManager = BluetoothDeviceController.this.localBluetoothManager;
                if (localBluetoothManager != null) {
                    BluetoothDeviceController.Companion.getClass();
                    Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new BluetoothDeviceController$Companion$connectedDeviceChanges$1(localBluetoothManager, null)), -1);
                    if (buffer$default != null) {
                        Flow debounce = FlowKt.debounce(buffer$default, 50L);
                        final BluetoothDeviceController bluetoothDeviceController = BluetoothDeviceController.this;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController.1.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Companion companion = BluetoothDeviceController.Companion;
                                Object updateDevices = BluetoothDeviceController.this.updateDevices((List) obj2, false, continuation);
                                return updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? updateDevices : Unit.INSTANCE;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BluetoothDeviceController(ContentResolver contentResolver, AudioManager audioManager, LocalBluetoothManager localBluetoothManager) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        A2dpProfile a2dpProfile;
        BluetoothAdapter bluetoothAdapter;
        this.cr = contentResolver;
        this.audioManager = audioManager;
        this.localBluetoothManager = localBluetoothManager;
        boolean z = false;
        if (localBluetoothManager != null && (localBluetoothProfileManager = localBluetoothManager.mProfileManager) != null && (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) != null && (bluetoothAdapter = a2dpProfile.mBluetoothAdapter) != null) {
            z = bluetoothAdapter.semIsDualPlaySupported();
        }
        this.isDualAudioSupported = z;
        Log.d("BluetoothDeviceController", "init() - isDualAudioSupported = " + z);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void adjustVolume(AudioDevice audioDevice, int i) {
        CachedBluetoothDevice cachedBluetoothDevice;
        Log.d("BluetoothDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        BluetoothDevice bluetoothDevice = null;
        bluetoothDevice = null;
        bluetoothDevice = null;
        if (audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) {
            com.android.systemui.media.mediaoutput.entity.BluetoothDevice bluetoothDevice2 = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice;
            CachedBluetoothDevice cachedBluetoothDevice2 = bluetoothDevice2.cachedBluetoothDevice;
            if (cachedBluetoothDevice2 == null) {
                cachedBluetoothDevice2 = null;
            }
            if (cachedBluetoothDevice2.isConnectedA2dpDevice()) {
                CachedBluetoothDevice cachedBluetoothDevice3 = bluetoothDevice2.cachedBluetoothDevice;
                bluetoothDevice = (cachedBluetoothDevice3 != null ? cachedBluetoothDevice3 : null).mDevice;
            } else {
                this.audioManager.semSetFineVolume(3, i, 0);
            }
        } else if ((audioDevice instanceof MusicShareDevice) && (cachedBluetoothDevice = ((MusicShareDevice) audioDevice).cachedBluetoothDevice) != null) {
            bluetoothDevice = cachedBluetoothDevice.mDevice;
        }
        if (bluetoothDevice != null) {
            this.audioManager.semSetFineVolume(bluetoothDevice, 3, i, 0);
            DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
            ContentResolver contentResolver = this.cr;
            boolean needEarProtect = audioDevice.getNeedEarProtect();
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$adjustVolume$1$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BluetoothDeviceController bluetoothDeviceController = BluetoothDeviceController.this;
                    LocalBluetoothManager localBluetoothManager = bluetoothDeviceController.localBluetoothManager;
                    if (localBluetoothManager != null) {
                        Job job = bluetoothDeviceController.updateJob;
                        if (job != null) {
                            job.cancel(null);
                        }
                        bluetoothDeviceController.updateJob = BuildersKt.launch$default(bluetoothDeviceController.getControllerScope(), null, null, new BluetoothDeviceController$adjustVolume$1$1$1$1(bluetoothDeviceController, localBluetoothManager, null), 3);
                    }
                    return Unit.INSTANCE;
                }
            };
            deviceUtils.getClass();
            DeviceUtils.checkVolumeLimiter(contentResolver, needEarProtect, i, function0);
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void cancel(AudioDevice audioDevice) {
        LocalBluetoothManager localBluetoothManager;
        LocalBluetoothCastProfileManager localBluetoothCastProfileManager;
        AudioCastProfile audioCastProfile;
        if (!(audioDevice instanceof MusicShareDevice) || (localBluetoothManager = this.localBluetoothManager) == null || (localBluetoothCastProfileManager = localBluetoothManager.mLocalCastProfileManager) == null || (audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) == null) {
            return;
        }
        List connectedDevices = audioCastProfile.getConnectedDevices();
        ArrayList<SemBluetoothCastDevice> arrayList = new ArrayList();
        for (Object obj : connectedDevices) {
            if (((SemBluetoothCastDevice) obj).getConnectionState() == 2) {
                arrayList.add(obj);
            }
        }
        for (SemBluetoothCastDevice semBluetoothCastDevice : arrayList) {
            Log.d(audioCastProfile.TAG, "disconnectGuest");
            SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
            if (semBluetoothAudioCast != null) {
                semBluetoothAudioCast.disconnectGuest(semBluetoothCastDevice);
            }
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("BluetoothDeviceController", "close()");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void deselect(AudioDevice audioDevice) {
        BluetoothA2dp bluetoothA2dp;
        Object obj;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("deselect() - ", audioDevice, "BluetoothDeviceController");
        if (audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) {
            List list = ((com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice).activeDevices;
            if (list != null) {
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        obj = null;
                        break;
                    } else {
                        obj = it.next();
                        if (!Intrinsics.areEqual(((com.android.systemui.media.mediaoutput.entity.BluetoothDevice) obj).id, ((com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice).id)) {
                            break;
                        }
                    }
                }
                com.android.systemui.media.mediaoutput.entity.BluetoothDevice bluetoothDevice = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) obj;
                if (bluetoothDevice != null) {
                    Log.i("BluetoothDeviceController", "deselect(" + audioDevice + ") - " + bluetoothDevice);
                    transfer(bluetoothDevice);
                }
            }
            LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
            if (localBluetoothManager != null) {
                A2dpProfile a2dpProfile = localBluetoothManager.mProfileManager.mA2dpProfile;
                if (a2dpProfile != null && (bluetoothA2dp = a2dpProfile.mService) != null) {
                    bluetoothA2dp.setDualPlayMode(false);
                }
                BuildersKt.launch$default(getControllerScope(), null, null, new BluetoothDeviceController$deselect$3$1(this, localBluetoothManager, null), 3);
            }
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void select(AudioDevice audioDevice) {
        LocalBluetoothManager localBluetoothManager;
        BluetoothA2dp bluetoothA2dp;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("select() - ", audioDevice, "BluetoothDeviceController");
        if (!(audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) || (localBluetoothManager = this.localBluetoothManager) == null) {
            return;
        }
        A2dpProfile a2dpProfile = localBluetoothManager.mProfileManager.mA2dpProfile;
        if (a2dpProfile != null && (bluetoothA2dp = a2dpProfile.mService) != null) {
            bluetoothA2dp.setDualPlayMode(true);
        }
        BuildersKt.launch$default(getControllerScope(), null, null, new BluetoothDeviceController$select$1$1(this, localBluetoothManager, null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void transfer(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "BluetoothDeviceController");
        Object obj = null;
        if (audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) {
            com.android.systemui.media.mediaoutput.entity.BluetoothDevice bluetoothDevice = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice;
            Object obj2 = bluetoothDevice.audioDeviceInfo;
            if (obj2 == null) {
                Object obj3 = bluetoothDevice.cachedBluetoothDevice;
                if (obj3 != null) {
                    obj = obj3;
                }
            } else {
                obj = obj2;
            }
        } else if (audioDevice instanceof MusicShareDevice) {
            obj = ((MusicShareDevice) audioDevice).audioDeviceInfo;
        }
        if (obj != null) {
            if (obj instanceof AudioDeviceInfo) {
                AudioManagerExtKt.setDeviceForced(this.audioManager, (AudioDeviceInfo) obj);
            } else if (obj instanceof CachedBluetoothDevice) {
                ((CachedBluetoothDevice) obj).setActive();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01c9  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x0591  */
    /* JADX WARN: Removed duplicated region for block: B:293:0x05ee A[LOOP:12: B:291:0x05e8->B:293:0x05ee, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:297:0x060d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r13v17, types: [com.android.systemui.media.mediaoutput.entity.MusicShareDevice] */
    /* JADX WARN: Type inference failed for: r5v26, types: [com.android.systemui.media.mediaoutput.entity.BluetoothDevice] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateDevices(java.util.List r32, boolean r33, kotlin.coroutines.Continuation r34) {
        /*
            Method dump skipped, instructions count: 1553
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController.updateDevices(java.util.List, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
