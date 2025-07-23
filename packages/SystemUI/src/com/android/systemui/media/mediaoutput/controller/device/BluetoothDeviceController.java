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
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.MusicShareDevice;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BluetoothDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Lazy bluetoothAdapter$delegate;
    public final ContentResolver cr;
    public final boolean isDualAudioSupported;
    public final LocalBluetoothManager localBluetoothManager;
    public boolean musicShareEventLogged;
    public StandaloneCoroutine updateJob;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new BluetoothDeviceController$Companion$connectedDeviceChanges$1(localBluetoothManager, null)), -1, 2);
                    if (buffer$default != null) {
                        MediaOutputConst.INSTANCE.getClass();
                        Flow m3462debounceHG0u8IE = FlowKt.m3462debounceHG0u8IE(buffer$default, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                        if (m3462debounceHG0u8IE != null) {
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
                            if (m3462debounceHG0u8IE.collect(flowCollector, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        this.bluetoothAdapter$delegate = LazyKt__LazyJVMKt.lazy(new BluetoothDeviceController$$ExternalSyntheticLambda0());
        Log.d("BluetoothDeviceController", "init() - isDualAudioSupported = " + z);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit adjustVolume(AudioDevice audioDevice, int i) {
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
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BluetoothDeviceController bluetoothDeviceController = BluetoothDeviceController.this;
                    LocalBluetoothManager localBluetoothManager = bluetoothDeviceController.localBluetoothManager;
                    if (localBluetoothManager != null) {
                        StandaloneCoroutine standaloneCoroutine = bluetoothDeviceController.updateJob;
                        if (standaloneCoroutine != null) {
                            standaloneCoroutine.cancel(null);
                        }
                        bluetoothDeviceController.updateJob = BuildersKt.launch$default(bluetoothDeviceController.getControllerScope(), null, null, new BluetoothDeviceController$adjustVolume$2$1$1$1(bluetoothDeviceController, localBluetoothManager, null), 3);
                    }
                    return Unit.INSTANCE;
                }
            };
            deviceUtils.getClass();
            DeviceUtils.checkVolumeLimiter(contentResolver, needEarProtect, i, function0);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit cancel(AudioDevice audioDevice) {
        LocalBluetoothCastProfileManager localBluetoothCastProfileManager;
        AudioCastProfile audioCastProfile;
        if (audioDevice instanceof MusicShareDevice) {
            LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
            if (localBluetoothManager != null && (localBluetoothCastProfileManager = localBluetoothManager.mLocalCastProfileManager) != null && (audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) != null) {
                List connectedDevices = audioCastProfile.getConnectedDevices();
                ArrayList arrayList = new ArrayList();
                for (Object obj : connectedDevices) {
                    if (((SemBluetoothCastDevice) obj).getConnectionState() == 2) {
                        arrayList.add(obj);
                    }
                }
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj2 = arrayList.get(i);
                    i++;
                    SemBluetoothCastDevice semBluetoothCastDevice = (SemBluetoothCastDevice) obj2;
                    Log.d(audioCastProfile.TAG, "disconnectGuest");
                    SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                    if (semBluetoothAudioCast != null) {
                        semBluetoothAudioCast.disconnectGuest(semBluetoothCastDevice);
                    }
                }
            }
            MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.EndMusicShareHost.INSTANCE);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("BluetoothDeviceController", "close()");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object deselect(com.android.systemui.media.mediaoutput.entity.AudioDevice r10, kotlin.coroutines.jvm.internal.ContinuationImpl r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$deselect$1
            if (r0 == 0) goto L13
            r0 = r11
            com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$deselect$1 r0 = (com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$deselect$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$deselect$1 r0 = new com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$deselect$1
            r0.<init>(r9, r11)
        L18:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L38
            if (r2 != r4) goto L30
            java.lang.Object r9 = r0.L$1
            com.android.systemui.media.mediaoutput.entity.BluetoothDevice r9 = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) r9
            java.lang.Object r9 = r0.L$0
            com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController r9 = (com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController) r9
            kotlin.ResultKt.throwOnFailure(r11)
            goto La0
        L30:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L38:
            kotlin.ResultKt.throwOnFailure(r11)
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r2 = "deselect() - "
            r11.<init>(r2)
            r11.append(r10)
            java.lang.String r11 = r11.toString()
            java.lang.String r2 = "BluetoothDeviceController"
            android.util.Log.d(r2, r11)
            boolean r11 = r10 instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice
            if (r11 == 0) goto Lc0
            r11 = r10
            com.android.systemui.media.mediaoutput.entity.BluetoothDevice r11 = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) r11
            java.util.List r5 = r11.activeDevices
            if (r5 == 0) goto La0
            java.util.Iterator r5 = r5.iterator()
        L5d:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L75
            java.lang.Object r6 = r5.next()
            r7 = r6
            com.android.systemui.media.mediaoutput.entity.BluetoothDevice r7 = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) r7
            java.lang.String r7 = r7.id
            java.lang.String r8 = r11.id
            boolean r7 = kotlin.jvm.internal.Intrinsics.areEqual(r7, r8)
            if (r7 != 0) goto L5d
            goto L76
        L75:
            r6 = r3
        L76:
            com.android.systemui.media.mediaoutput.entity.BluetoothDevice r6 = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) r6
            if (r6 == 0) goto La0
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            java.lang.String r5 = "deselect("
            r11.<init>(r5)
            r11.append(r10)
            java.lang.String r10 = ") - "
            r11.append(r10)
            r11.append(r6)
            java.lang.String r10 = r11.toString()
            android.util.Log.i(r2, r10)
            r0.L$0 = r9
            r0.L$1 = r6
            r0.label = r4
            java.lang.Object r10 = r9.transfer(r6, r0)
            if (r10 != r1) goto La0
            return r1
        La0:
            com.android.settingslib.bluetooth.LocalBluetoothManager r10 = r9.localBluetoothManager
            if (r10 == 0) goto Lc0
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r11 = r10.mProfileManager
            com.android.settingslib.bluetooth.A2dpProfile r11 = r11.mA2dpProfile
            if (r11 == 0) goto Lb3
            android.bluetooth.BluetoothA2dp r11 = r11.mService
            if (r11 != 0) goto Laf
            goto Lb3
        Laf:
            r0 = 0
            r11.setDualPlayMode(r0)
        Lb3:
            kotlinx.coroutines.CoroutineScope r11 = r9.getControllerScope()
            com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$deselect$4$1 r0 = new com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$deselect$4$1
            r0.<init>(r9, r10, r3)
            r9 = 3
            kotlinx.coroutines.BuildersKt.launch$default(r11, r3, r3, r0, r9)
        Lc0:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController.deselect(com.android.systemui.media.mediaoutput.entity.AudioDevice, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit select(AudioDevice audioDevice, ContinuationImpl continuationImpl) {
        LocalBluetoothManager localBluetoothManager;
        BluetoothA2dp bluetoothA2dp;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("select() - ", audioDevice, "BluetoothDeviceController");
        if ((audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) && (localBluetoothManager = this.localBluetoothManager) != null) {
            A2dpProfile a2dpProfile = localBluetoothManager.mProfileManager.mA2dpProfile;
            if (a2dpProfile != null && (bluetoothA2dp = a2dpProfile.mService) != null) {
                bluetoothA2dp.setDualPlayMode(true);
            }
            BuildersKt.launch$default(getControllerScope(), null, null, new BluetoothDeviceController$select$2$1(this, localBluetoothManager, null), 3);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        Object obj;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "BluetoothDeviceController");
        Object obj2 = null;
        if (audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) {
            com.android.systemui.media.mediaoutput.entity.BluetoothDevice bluetoothDevice = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice;
            AudioDeviceInfo audioDeviceInfo = bluetoothDevice.audioDeviceInfo;
            if (audioDeviceInfo != null) {
                AudioManager audioManager = this.audioManager;
                Lazy lazy = AudioManagerExtKt.mediaStrategy$delegate;
                AudioDeviceInfo[] devices = audioManager.getDevices(2);
                ArrayList arrayList = new ArrayList();
                for (AudioDeviceInfo audioDeviceInfo2 : devices) {
                    if (audioDeviceInfo2.semGetInternalType() == audioDeviceInfo.semGetInternalType()) {
                        arrayList.add(audioDeviceInfo2);
                    }
                }
                int size = arrayList.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        obj = null;
                        break;
                    }
                    obj = arrayList.get(i);
                    i++;
                    if (Intrinsics.areEqual(((AudioDeviceInfo) obj).semGetAddress(), audioDeviceInfo.semGetAddress())) {
                        break;
                    }
                }
                Object obj3 = (AudioDeviceInfo) obj;
                if (obj3 != null) {
                    obj2 = obj3;
                }
            }
            Object obj4 = bluetoothDevice.cachedBluetoothDevice;
            if (obj4 != null) {
                obj2 = obj4;
            }
        } else if (audioDevice instanceof MusicShareDevice) {
            obj2 = ((MusicShareDevice) audioDevice).audioDeviceInfo;
        }
        if (obj2 != null) {
            if (obj2 instanceof AudioDeviceInfo) {
                AudioManagerExtKt.setDeviceForced(this.audioManager, (AudioDeviceInfo) obj2);
            } else if (obj2 instanceof CachedBluetoothDevice) {
                ((BluetoothAdapter) this.bluetoothAdapter$delegate.getValue()).setActiveDevice(((CachedBluetoothDevice) obj2).mDevice, 0);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:85:0x010d, code lost:
    
        if (r10 != null) goto L49;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0489  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x048c  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x03f9  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x045a A[LOOP:15: B:253:0x0458->B:254:0x045a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:258:0x0483 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r11v25, types: [com.android.systemui.media.mediaoutput.entity.BluetoothDevice] */
    /* JADX WARN: Type inference failed for: r11v27, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v34, types: [com.android.systemui.media.mediaoutput.entity.MusicShareDevice] */
    /* JADX WARN: Type inference failed for: r5v27, types: [com.android.systemui.media.mediaoutput.entity.BluetoothDevice] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateDevices(java.util.List r35, boolean r36, kotlin.coroutines.Continuation r37) {
        /*
            Method dump skipped, instructions count: 1230
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController.updateDevices(java.util.List, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
