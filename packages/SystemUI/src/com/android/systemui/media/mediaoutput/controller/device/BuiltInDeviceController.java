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
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import java.util.List;
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
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BuiltInDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Context context;
    public final LocalBluetoothManager localBluetoothManager;
    public Job updateJob;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                Flow debounce = FlowKt.debounce(FlowKt.buffer$default(FlowKt.callbackFlow(new BuiltInDeviceController$Companion$deviceStateChanges$1(pair, null)), -1), 50L);
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BuiltInDeviceController(Context context, AudioManager audioManager, LocalBluetoothManager localBluetoothManager) {
        this.context = context;
        this.audioManager = audioManager;
        this.localBluetoothManager = localBluetoothManager;
        Log.d("BuiltInDeviceController", "init() - currentDeviceType = " + audioManager.semGetCurrentDeviceType());
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("BuiltInDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        if (audioDevice instanceof BuiltInDevice) {
            this.audioManager.semSetFineVolume(3, i, 0);
            DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
            ContentResolver contentResolver = this.context.getContentResolver();
            boolean needEarProtect = ((BuiltInDevice) audioDevice).getNeedEarProtect();
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$adjustVolume$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$adjustVolume$1$1, reason: invalid class name */
                final class AnonymousClass1 extends SuspendLambda implements Function2 {
                    int label;
                    final /* synthetic */ BuiltInDeviceController this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(BuiltInDeviceController builtInDeviceController, Continuation continuation) {
                        super(2, continuation);
                        this.this$0 = builtInDeviceController;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Continuation create(Object obj, Continuation continuation) {
                        return new AnonymousClass1(this.this$0, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                    }

                    /* JADX WARN: Removed duplicated region for block: B:16:0x006d A[RETURN] */
                    /* JADX WARN: Removed duplicated region for block: B:20:0x0057 A[RETURN] */
                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
                        /*
                            r8 = this;
                            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r1 = r8.label
                            r2 = 4
                            r3 = 3
                            r4 = 1
                            r5 = 2
                            if (r1 == 0) goto L2a
                            if (r1 == r4) goto L26
                            if (r1 == r5) goto L22
                            if (r1 == r3) goto L1e
                            if (r1 != r2) goto L16
                            kotlin.ResultKt.throwOnFailure(r9)
                            goto L6e
                        L16:
                            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                            r8.<init>(r9)
                            throw r8
                        L1e:
                            kotlin.ResultKt.throwOnFailure(r9)
                            goto L58
                        L22:
                            kotlin.ResultKt.throwOnFailure(r9)
                            goto L4d
                        L26:
                            kotlin.ResultKt.throwOnFailure(r9)
                            goto L38
                        L2a:
                            kotlin.ResultKt.throwOnFailure(r9)
                            r8.label = r4
                            r6 = 300(0x12c, double:1.48E-321)
                            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r6, r8)
                            if (r9 != r0) goto L38
                            return r0
                        L38:
                            com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController r9 = r8.this$0
                            android.media.AudioManager r1 = r9.audioManager
                            android.media.AudioDeviceInfo[] r1 = r1.getDevices(r5)
                            java.util.List r1 = kotlin.collections.ArraysKt___ArraysKt.toList(r1)
                            r8.label = r5
                            java.lang.Object r9 = r9.updateDevices$1(r1, r4, r8)
                            if (r9 != r0) goto L4d
                            return r0
                        L4d:
                            r8.label = r3
                            r3 = 200(0xc8, double:9.9E-322)
                            java.lang.Object r9 = kotlinx.coroutines.DelayKt.delay(r3, r8)
                            if (r9 != r0) goto L58
                            return r0
                        L58:
                            com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController r9 = r8.this$0
                            android.media.AudioManager r1 = r9.audioManager
                            android.media.AudioDeviceInfo[] r1 = r1.getDevices(r5)
                            java.util.List r1 = kotlin.collections.ArraysKt___ArraysKt.toList(r1)
                            r8.label = r2
                            r2 = 0
                            java.lang.Object r9 = r9.updateDevices$1(r1, r2, r8)
                            if (r9 != r0) goto L6e
                            return r0
                        L6e:
                            com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController r8 = r8.this$0
                            r9 = 0
                            r8.updateJob = r9
                            kotlin.Unit r8 = kotlin.Unit.INSTANCE
                            return r8
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$adjustVolume$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
                    }
                }

                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Job job = BuiltInDeviceController.this.updateJob;
                    if (job != null) {
                        job.cancel(null);
                    }
                    BuiltInDeviceController builtInDeviceController = BuiltInDeviceController.this;
                    builtInDeviceController.updateJob = BuildersKt.launch$default(builtInDeviceController.getControllerScope(), null, null, new AnonymousClass1(BuiltInDeviceController.this, null), 3);
                    return Unit.INSTANCE;
                }
            };
            deviceUtils.getClass();
            DeviceUtils.checkVolumeLimiter(contentResolver, needEarProtect, i, function0);
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("BuiltInDeviceController", "close()");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void transfer(AudioDevice audioDevice) {
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
            if (localBluetoothManager == null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) == null || (bluetoothA2dp = a2dpProfile.mService) == null) {
                return;
            }
            bluetoothA2dp.setDualPlayMode(false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x03a8  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x03ad  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x03b0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateDevices$1(java.util.List r41, boolean r42, kotlin.coroutines.Continuation r43) {
        /*
            Method dump skipped, instructions count: 1106
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController.updateDevices$1(java.util.List, boolean, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
