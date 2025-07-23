package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothA2dp;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import java.util.List;
import kotlin.Lazy;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class BuiltInDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Context context;
    public final LocalBluetoothManager localBluetoothManager;
    public StandaloneCoroutine updateJob;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BuiltInDeviceController.this.new AnonymousClass1(continuation);
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
                Companion companion = BuiltInDeviceController.Companion;
                BuiltInDeviceController builtInDeviceController = BuiltInDeviceController.this;
                Pair pair = new Pair(builtInDeviceController.context, builtInDeviceController.audioManager);
                companion.getClass();
                Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new BuiltInDeviceController$Companion$deviceStateChanges$1(pair, null)), -1, 2);
                MediaOutputConst.INSTANCE.getClass();
                Flow m3462debounceHG0u8IE = FlowKt.m3462debounceHG0u8IE(buffer$default, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                final BuiltInDeviceController builtInDeviceController2 = BuiltInDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Companion companion2 = BuiltInDeviceController.Companion;
                        Object updateDevices$1 = BuiltInDeviceController.this.updateDevices$1((List) obj2, false, continuation);
                        return updateDevices$1 == CoroutineSingletons.COROUTINE_SUSPENDED ? updateDevices$1 : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (m3462debounceHG0u8IE.collect(flowCollector, this) == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public BuiltInDeviceController(Context context, AudioManager audioManager, LocalBluetoothManager localBluetoothManager) {
        this.context = context;
        this.audioManager = audioManager;
        this.localBluetoothManager = localBluetoothManager;
        Lazy lazy = AudioManagerExtKt.mediaStrategy$delegate;
        Log.d("BuiltInDeviceController", "init() - currentDeviceType = " + audioManager.semGetCurrentDeviceType());
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("BuiltInDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        if (audioDevice instanceof BuiltInDevice) {
            this.audioManager.semSetFineVolume(3, i, 0);
            DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
            ContentResolver contentResolver = this.context.getContentResolver();
            boolean needEarProtect = ((BuiltInDevice) audioDevice).getNeedEarProtect();
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BuiltInDeviceController builtInDeviceController = BuiltInDeviceController.this;
                    StandaloneCoroutine standaloneCoroutine = builtInDeviceController.updateJob;
                    if (standaloneCoroutine != null) {
                        standaloneCoroutine.cancel(null);
                    }
                    builtInDeviceController.updateJob = BuildersKt.launch$default(builtInDeviceController.getControllerScope(), null, null, new BuiltInDeviceController$adjustVolume$2$1(builtInDeviceController, null), 3);
                    return Unit.INSTANCE;
                }
            };
            deviceUtils.getClass();
            DeviceUtils.checkVolumeLimiter(contentResolver, needEarProtect, i, function0);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("BuiltInDeviceController", "close()");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        A2dpProfile a2dpProfile;
        BluetoothA2dp bluetoothA2dp;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "BuiltInDeviceController");
        if (audioDevice instanceof BuiltInDevice) {
            AudioManager audioManager = this.audioManager;
            AudioDeviceInfo audioDeviceInfo = ((BuiltInDevice) audioDevice).audioDeviceInfo;
            if (audioDeviceInfo == null) {
                audioDeviceInfo = null;
            }
            AudioManagerExtKt.setDeviceForced(audioManager, audioDeviceInfo);
            LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
            if (localBluetoothManager != null && (localBluetoothProfileManager = localBluetoothManager.mProfileManager) != null && (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) != null && (bluetoothA2dp = a2dpProfile.mService) != null) {
                bluetoothA2dp.setDualPlayMode(false);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:134:0x045f  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0464 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0437 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x04b4  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x04fa  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x050f  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0512  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x052d  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x03a1  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x03a6  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x03a9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateDevices$1(java.util.List r36, boolean r37, kotlin.coroutines.Continuation r38) {
        /*
            Method dump skipped, instructions count: 1438
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController.updateDevices$1(java.util.List, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
