package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothA2dp;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import androidx.datastore.core.DataStore;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$5;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.DexDevice;
import com.android.systemui.media.mediaoutput.entity.SmartViewDevice;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class WifiDisplayDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Context context;
    public final DataStore dataStore;
    public final DisplayManager displayManager;
    public final DisplayManagerWrapper displayManagerWrapper;
    public boolean isSupportDisplayDeviceVolumeControl;
    public final LocalBluetoothManager localBluetoothManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WifiDisplayDeviceController.this.new AnonymousClass1(continuation);
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
                DataStoreDebugLabsExt dataStoreDebugLabsExt = DataStoreDebugLabsExt.INSTANCE;
                DataStore dataStore = WifiDisplayDeviceController.this.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$5 dataStoreDebugLabsExt$special$$inlined$map$5 = new DataStoreDebugLabsExt$special$$inlined$map$5(dataStore.getData());
                final WifiDisplayDeviceController wifiDisplayDeviceController = WifiDisplayDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        WifiDisplayDeviceController wifiDisplayDeviceController2 = WifiDisplayDeviceController.this;
                        wifiDisplayDeviceController2.isSupportDisplayDeviceVolumeControl = booleanValue;
                        Object access$updateDevices = WifiDisplayDeviceController.access$updateDevices(wifiDisplayDeviceController2, continuation);
                        return access$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? access$updateDevices : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreDebugLabsExt$special$$inlined$map$5.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WifiDisplayDeviceController.this.new AnonymousClass2(continuation);
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
                Companion companion = WifiDisplayDeviceController.Companion;
                WifiDisplayDeviceController wifiDisplayDeviceController = WifiDisplayDeviceController.this;
                Pair pair = new Pair(wifiDisplayDeviceController.context, wifiDisplayDeviceController.audioManager);
                companion.getClass();
                Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new WifiDisplayDeviceController$Companion$activeDeviceChanges$1(pair, null)), -1, 2);
                MediaOutputConst.INSTANCE.getClass();
                Flow m3462debounceHG0u8IE = FlowKt.m3462debounceHG0u8IE(buffer$default, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                final WifiDisplayDeviceController wifiDisplayDeviceController2 = WifiDisplayDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object access$updateDevices = WifiDisplayDeviceController.access$updateDevices(WifiDisplayDeviceController.this, continuation);
                        return access$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? access$updateDevices : Unit.INSTANCE;
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
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WifiDisplayDeviceController.this.new AnonymousClass3(continuation);
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
                Companion companion = WifiDisplayDeviceController.Companion;
                DisplayManagerWrapper displayManagerWrapper = WifiDisplayDeviceController.this.displayManagerWrapper;
                companion.getClass();
                final Flow debounce = FlowKt.debounce(FlowKt.buffer$default(FlowKt.callbackFlow(new WifiDisplayDeviceController$Companion$activeVolumeChanges$1(displayManagerWrapper, null)), -1, 2), 50L);
                final WifiDisplayDeviceController wifiDisplayDeviceController = WifiDisplayDeviceController.this;
                Flow flow = new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ WifiDisplayDeviceController this$0;

                        /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
                        public final class AnonymousClass1 extends ContinuationImpl {
                            Object L$0;
                            Object L$1;
                            int label;
                            /* synthetic */ Object result;

                            public AnonymousClass1(Continuation continuation) {
                                super(continuation);
                            }

                            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                            public final Object invokeSuspend(Object obj) {
                                this.result = obj;
                                this.label |= Integer.MIN_VALUE;
                                return AnonymousClass2.this.emit(null, this);
                            }
                        }

                        public AnonymousClass2(FlowCollector flowCollector, WifiDisplayDeviceController wifiDisplayDeviceController) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = wifiDisplayDeviceController;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                            /*
                                r4 = this;
                                boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1$2$1
                                r0.<init>(r6)
                            L18:
                                java.lang.Object r6 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r6)
                                goto L46
                            L27:
                                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                r4.<init>(r5)
                                throw r4
                            L2f:
                                kotlin.ResultKt.throwOnFailure(r6)
                                r6 = r5
                                kotlin.Unit r6 = (kotlin.Unit) r6
                                com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController r6 = r4.this$0
                                boolean r6 = r6.isSupportDisplayDeviceVolumeControl
                                if (r6 == 0) goto L46
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                java.lang.Object r4 = r4.emit(r5, r0)
                                if (r4 != r1) goto L46
                                return r1
                            L46:
                                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                return r4
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, wifiDisplayDeviceController), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final WifiDisplayDeviceController wifiDisplayDeviceController2 = WifiDisplayDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController.3.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object access$updateDevices = WifiDisplayDeviceController.access$updateDevices(WifiDisplayDeviceController.this, continuation);
                        return access$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? access$updateDevices : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

    public WifiDisplayDeviceController(Context context, AudioManager audioManager, DisplayManager displayManager, LocalBluetoothManager localBluetoothManager, DisplayManagerWrapper displayManagerWrapper, DataStore dataStore) {
        this.context = context;
        this.audioManager = audioManager;
        this.displayManager = displayManager;
        this.localBluetoothManager = localBluetoothManager;
        this.displayManagerWrapper = displayManagerWrapper;
        this.dataStore = dataStore;
        Log.d("WifiDisplayDeviceController", "init()");
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass2(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass3(null), 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updateDevices(final com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController r5, kotlin.coroutines.Continuation r6) {
        /*
            r5.getClass()
            boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$1
            if (r0 == 0) goto L16
            r0 = r6
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$1 r0 = (com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$1 r0 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$1
            r0.<init>(r5, r6)
        L1b:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r5 = r0.L$0
            java.util.List r5 = (java.util.List) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L92
        L2e:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L36:
            kotlin.ResultKt.throwOnFailure(r6)
            android.media.AudioManager r6 = r5.audioManager
            r2 = 2
            android.media.AudioDeviceInfo[] r6 = r6.getDevices(r2)
            kotlin.sequences.Sequence r6 = kotlin.collections.ArraysKt___ArraysKt.asSequence(r6)
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0 r2 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0
            r4 = 0
            r2.<init>()
            kotlin.sequences.FilteringSequence r6 = kotlin.sequences.SequencesKt___SequencesKt.filter(r6, r2)
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0 r2 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0
            r4 = 1
            r2.<init>()
            kotlin.sequences.FilteringSequence r6 = kotlin.sequences.SequencesKt___SequencesKt.filter(r6, r2)
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0 r2 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0
            r4 = 2
            r2.<init>()
            kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda2 r4 = new kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda2
            r4.<init>(r2)
            kotlin.sequences.TransformingSequence r2 = new kotlin.sequences.TransformingSequence
            r2.<init>(r6, r4)
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda3 r6 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda3
            r6.<init>()
            kotlin.sequences.FilteringSequence r6 = kotlin.sequences.SequencesKt___SequencesKt.mapNotNull(r2, r6)
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0 r2 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0
            r4 = 3
            r2.<init>()
            kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda2 r4 = new kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda2
            r4.<init>(r2)
            kotlin.sequences.TransformingSequence r2 = new kotlin.sequences.TransformingSequence
            r2.<init>(r6, r4)
            java.util.List r6 = kotlin.sequences.SequencesKt___SequencesKt.toList(r2)
            kotlinx.coroutines.flow.SharedFlowImpl r5 = r5.devicesFlow
            r0.L$0 = r6
            r0.label = r3
            java.lang.Object r5 = r5.emit(r6, r0)
            if (r5 != r1) goto L92
            return r1
        L92:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController.access$updateDevices(com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("WifiDisplayDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        if ((audioDevice instanceof SmartViewDevice) || (audioDevice instanceof DexDevice)) {
            if (this.isSupportDisplayDeviceVolumeControl) {
                this.audioManager.adjustVolume(audioDevice.getVolume() < i ? 1 : -1, 0);
            } else {
                this.audioManager.semSetFineVolume(3, i, 0);
            }
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit cancel(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("cancel() - ", audioDevice, "WifiDisplayDeviceController");
        if ((audioDevice instanceof SmartViewDevice) || (audioDevice instanceof DexDevice)) {
            this.displayManager.semDisconnectWifiDisplay();
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("WifiDisplayDeviceController", "close()");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        AudioDeviceInfo audioDeviceInfo;
        LocalBluetoothProfileManager localBluetoothProfileManager;
        A2dpProfile a2dpProfile;
        BluetoothA2dp bluetoothA2dp;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "WifiDisplayDeviceController");
        AudioDeviceInfo audioDeviceInfo2 = null;
        if (!(audioDevice instanceof SmartViewDevice) ? !(!(audioDevice instanceof DexDevice) || (audioDeviceInfo = ((DexDevice) audioDevice).audioDeviceInfo) == null) : (audioDeviceInfo = ((SmartViewDevice) audioDevice).audioDeviceInfo) != null) {
            audioDeviceInfo2 = audioDeviceInfo;
        }
        if (audioDeviceInfo2 != null) {
            AudioManagerExtKt.setDeviceForced(this.audioManager, audioDeviceInfo2);
            LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
            if (localBluetoothManager != null && (localBluetoothProfileManager = localBluetoothManager.mProfileManager) != null && (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) != null && (bluetoothA2dp = a2dpProfile.mService) != null) {
                bluetoothA2dp.setDualPlayMode(false);
            }
        }
        return Unit.INSTANCE;
    }
}
