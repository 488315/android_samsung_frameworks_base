package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import androidx.datastore.core.DataStore;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$4;
import com.android.systemui.media.mediaoutput.common.DataStoreExt;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$4;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.MusicShareDevice;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

public final class MusicShareDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Context context;
    public final DataStore dataStore;
    public boolean isShowMusicShareEnabled = true;
    public boolean isSupportSelectableBudsTogether;
    public boolean isTriggeredBudsTogether;
    public final LocalBluetoothManager localBluetoothManager;
    public Job updateJob;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MusicShareDeviceController.this.new AnonymousClass1(continuation);
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
                DataStoreExt dataStoreExt = DataStoreExt.INSTANCE;
                DataStore dataStore = MusicShareDeviceController.this.dataStore;
                dataStoreExt.getClass();
                DataStoreExt$special$$inlined$map$4 dataStoreExt$special$$inlined$map$4 = new DataStoreExt$special$$inlined$map$4(new DataStoreExt$special$$inlined$map$3(dataStore.getData()));
                final MusicShareDeviceController musicShareDeviceController = MusicShareDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        LocalBluetoothCastAdapter localBluetoothCastAdapter;
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        MusicShareDeviceController musicShareDeviceController2 = MusicShareDeviceController.this;
                        musicShareDeviceController2.isShowMusicShareEnabled = booleanValue;
                        LocalBluetoothManager localBluetoothManager = musicShareDeviceController2.localBluetoothManager;
                        if (localBluetoothManager != null && (localBluetoothCastAdapter = localBluetoothManager.mLocalCastAdapter) != null) {
                            if (booleanValue) {
                                localBluetoothCastAdapter.startDiscovery();
                            } else {
                                localBluetoothCastAdapter.cancelDiscovery();
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreExt$special$$inlined$map$4.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MusicShareDeviceController.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DataStoreDebugLabsExt dataStoreDebugLabsExt = DataStoreDebugLabsExt.INSTANCE;
                DataStore dataStore = MusicShareDeviceController.this.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$4 dataStoreDebugLabsExt$special$$inlined$map$4 = new DataStoreDebugLabsExt$special$$inlined$map$4(dataStore.getData());
                final MusicShareDeviceController musicShareDeviceController = MusicShareDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        MusicShareDeviceController.this.isSupportSelectableBudsTogether = ((Boolean) obj2).booleanValue();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreDebugLabsExt$special$$inlined$map$4.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MusicShareDeviceController.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Companion companion = MusicShareDeviceController.Companion;
                MusicShareDeviceController musicShareDeviceController = MusicShareDeviceController.this;
                AudioManager audioManager = musicShareDeviceController.audioManager;
                Context context = musicShareDeviceController.context;
                companion.getClass();
                Flow debounce = FlowKt.debounce(FlowKt.buffer$default(FlowKt.callbackFlow(new MusicShareDeviceController$Companion$castDeviceChanges$1(audioManager, context, null)), -1), 50L);
                final MusicShareDeviceController musicShareDeviceController2 = MusicShareDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        LocalBluetoothCastAdapter localBluetoothCastAdapter;
                        MusicShareDeviceController musicShareDeviceController3 = MusicShareDeviceController.this;
                        LocalBluetoothManager localBluetoothManager = musicShareDeviceController3.localBluetoothManager;
                        if (localBluetoothManager != null && (localBluetoothCastAdapter = localBluetoothManager.mLocalCastAdapter) != null) {
                            if (musicShareDeviceController3.isShowMusicShareEnabled) {
                                localBluetoothCastAdapter.startDiscovery();
                            } else {
                                localBluetoothCastAdapter.cancelDiscovery();
                            }
                        }
                        Object access$updateDevices = MusicShareDeviceController.access$updateDevices(musicShareDeviceController3, continuation);
                        return access$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? access$updateDevices : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (debounce.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MusicShareDeviceController.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LocalBluetoothManager localBluetoothManager = MusicShareDeviceController.this.localBluetoothManager;
                if (localBluetoothManager != null) {
                    MusicShareDeviceController.Companion.getClass();
                    Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new MusicShareDeviceController$Companion$castEventChanges$1(localBluetoothManager, null)), -1);
                    if (buffer$default != null) {
                        Flow debounce = FlowKt.debounce(buffer$default, 50L);
                        final MusicShareDeviceController musicShareDeviceController = MusicShareDeviceController.this;
                        FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController.4.1
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            public final Object emit(Object obj2, Continuation continuation) {
                                Object access$updateDevices = MusicShareDeviceController.access$updateDevices(MusicShareDeviceController.this, continuation);
                                return access$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? access$updateDevices : Unit.INSTANCE;
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

    public MusicShareDeviceController(Context context, AudioManager audioManager, LocalBluetoothManager localBluetoothManager, DataStore dataStore) {
        this.context = context;
        this.audioManager = audioManager;
        this.localBluetoothManager = localBluetoothManager;
        this.dataStore = dataStore;
        Log.d("MusicShareDeviceController", "init()");
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass2(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass3(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass4(null), 3);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updateDevices(com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController r29, kotlin.coroutines.Continuation r30) {
        /*
            Method dump skipped, instructions count: 514
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController.access$updateDevices(com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("MusicShareDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        if (audioDevice instanceof MusicShareDevice) {
            CachedBluetoothCastDevice cachedBluetoothCastDevice = ((MusicShareDevice) audioDevice).cachedBluetoothCastDevice;
            if (cachedBluetoothCastDevice == null) {
                this.audioManager.semSetFineVolume(3, i, 0);
                return;
            }
            int roundToInt = MathKt__MathJVMKt.roundToInt(i / 10);
            Iterator it = cachedBluetoothCastDevice.mCastProfiles.iterator();
            while (it.hasNext()) {
                LocalBluetoothCastProfile localBluetoothCastProfile = (LocalBluetoothCastProfile) it.next();
                if (localBluetoothCastProfile != null) {
                    SemBluetoothCastDevice semBluetoothCastDevice = cachedBluetoothCastDevice.mCastDevice;
                    AudioCastProfile audioCastProfile = (AudioCastProfile) localBluetoothCastProfile;
                    Log.d(audioCastProfile.TAG, "setAudioSharingDeviceVolume");
                    SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                    if (semBluetoothAudioCast == null) {
                        return;
                    }
                    semBluetoothAudioCast.setAudioSharingDeviceVolume(semBluetoothCastDevice, roundToInt);
                    return;
                }
            }
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void cancel(AudioDevice audioDevice) {
        CachedBluetoothCastDevice cachedBluetoothCastDevice;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("cancel() - ", audioDevice, "MusicShareDeviceController");
        if (!(audioDevice instanceof MusicShareDevice) || (cachedBluetoothCastDevice = ((MusicShareDevice) audioDevice).cachedBluetoothCastDevice) == null) {
            return;
        }
        cachedBluetoothCastDevice.disconnect();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        LocalBluetoothCastAdapter localBluetoothCastAdapter;
        super.close();
        Log.d("MusicShareDeviceController", "close()");
        LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothCastAdapter = localBluetoothManager.mLocalCastAdapter) == null) {
            return;
        }
        localBluetoothCastAdapter.cancelDiscovery();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void deselect(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("deselect() - ", audioDevice, "MusicShareDeviceController");
        if (audioDevice instanceof MusicShareDevice) {
            setAudioSharingEnabled(false);
        }
    }

    public final boolean isAudioSharingEnabled() {
        LocalBluetoothCastProfileManager localBluetoothCastProfileManager;
        AudioCastProfile audioCastProfile;
        LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothCastProfileManager = localBluetoothManager.mLocalCastProfileManager) == null || (audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) == null) {
            return false;
        }
        Log.d(audioCastProfile.TAG, "isAudioSharingEnabled");
        SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
        if (semBluetoothAudioCast == null) {
            return false;
        }
        return semBluetoothAudioCast.isAudioSharingEnabled();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void select(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("select() - ", audioDevice, "MusicShareDeviceController");
        if (audioDevice instanceof MusicShareDevice) {
            setAudioSharingEnabled(true);
        }
    }

    public final void setAudioSharingEnabled(boolean z) {
        LocalBluetoothCastProfileManager localBluetoothCastProfileManager;
        AudioCastProfile audioCastProfile;
        LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothCastProfileManager = localBluetoothManager.mLocalCastProfileManager) == null || (audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) == null) {
            return;
        }
        Log.d(audioCastProfile.TAG, "setAudioSharingEnabled");
        SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
        if (semBluetoothAudioCast == null) {
            return;
        }
        semBluetoothAudioCast.setAudioSharingEnabled(z);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void transfer(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "MusicShareDeviceController");
        if (audioDevice instanceof MusicShareDevice) {
            MusicShareDevice musicShareDevice = (MusicShareDevice) audioDevice;
            CachedBluetoothCastDevice cachedBluetoothCastDevice = musicShareDevice.cachedBluetoothCastDevice;
            if (cachedBluetoothCastDevice != null) {
                if (cachedBluetoothCastDevice.isConnected()) {
                    cachedBluetoothCastDevice = null;
                }
                if (cachedBluetoothCastDevice != null) {
                    this.isTriggeredBudsTogether = true;
                    Job job = this.updateJob;
                    if (job != null) {
                        job.cancel(null);
                    }
                    this.updateJob = BuildersKt.launch$default(getControllerScope(), null, null, new MusicShareDeviceController$transfer$2$1(this, null), 3);
                    cachedBluetoothCastDevice.connect();
                    return;
                }
            }
            AudioDeviceInfo audioDeviceInfo = musicShareDevice.audioDeviceInfo;
            if (audioDeviceInfo != null) {
                CachedBluetoothCastDevice cachedBluetoothCastDevice2 = musicShareDevice.cachedBluetoothCastDevice;
                if (cachedBluetoothCastDevice2 != null) {
                    Iterator it = cachedBluetoothCastDevice2.mCastProfiles.iterator();
                    while (it.hasNext()) {
                        LocalBluetoothCastProfile localBluetoothCastProfile = (LocalBluetoothCastProfile) it.next();
                        if (localBluetoothCastProfile != null) {
                            SemBluetoothCastDevice semBluetoothCastDevice = cachedBluetoothCastDevice2.mCastDevice;
                            AudioCastProfile audioCastProfile = (AudioCastProfile) localBluetoothCastProfile;
                            Log.d(audioCastProfile.TAG, "setActiveDevice");
                            SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                            if (semBluetoothAudioCast != null) {
                                semBluetoothAudioCast.setActiveDevice(semBluetoothCastDevice);
                            }
                        }
                    }
                }
                this.audioManager.semSetDeviceForced(audioDeviceInfo.semGetInternalType(), isAudioSharingEnabled() ? audioDeviceInfo.semGetAddress() : "0");
            }
        }
    }
}
