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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WifiDisplayDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Context context;
    public final DataStore dataStore;
    public final DisplayManager displayManager;
    public final DisplayManagerWrapper displayManagerWrapper;
    public boolean isSupportDisplayDeviceVolumeControl;
    public final LocalBluetoothManager localBluetoothManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                Flow debounce = FlowKt.debounce(FlowKt.buffer$default(FlowKt.callbackFlow(new WifiDisplayDeviceController$Companion$activeDeviceChanges$1(pair, null)), -1), 50L);
                final WifiDisplayDeviceController wifiDisplayDeviceController2 = WifiDisplayDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object access$updateDevices = WifiDisplayDeviceController.access$updateDevices(WifiDisplayDeviceController.this, continuation);
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                final Flow debounce = FlowKt.debounce(FlowKt.buffer$default(FlowKt.callbackFlow(new WifiDisplayDeviceController$Companion$activeVolumeChanges$1(displayManagerWrapper, null)), -1), 50L);
                final WifiDisplayDeviceController wifiDisplayDeviceController = WifiDisplayDeviceController.this;
                Flow flow = new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ WifiDisplayDeviceController this$0;

                        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
    public static final java.lang.Object access$updateDevices(final com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController r4, kotlin.coroutines.Continuation r5) {
        /*
            r4.getClass()
            boolean r0 = r5 instanceof com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$1
            if (r0 == 0) goto L16
            r0 = r5
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
            r0.<init>(r4, r5)
        L1b:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r4 = r0.L$0
            java.util.List r4 = (java.util.List) r4
            kotlin.ResultKt.throwOnFailure(r5)
            goto L76
        L2e:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L36:
            kotlin.ResultKt.throwOnFailure(r5)
            android.media.AudioManager r5 = r4.audioManager
            r2 = 2
            android.media.AudioDeviceInfo[] r5 = r5.getDevices(r2)
            kotlin.sequences.Sequence r5 = kotlin.collections.ArraysKt___ArraysKt.asSequence(r5)
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$2 r2 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$2
                static {
                    /*
                        com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$2 r0 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$2
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$2) com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$2.INSTANCE com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$2
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$2.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$2.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        android.media.AudioDeviceInfo r1 = (android.media.AudioDeviceInfo) r1
                        int r0 = r1.getType()
                        r1 = 25
                        if (r0 != r1) goto Lc
                        r0 = 1
                        goto Ld
                    Lc:
                        r0 = 0
                    Ld:
                        java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$2.invoke(java.lang.Object):java.lang.Object");
                }
            }
            kotlin.sequences.FilteringSequence r5 = kotlin.sequences.SequencesKt___SequencesKt.filter(r5, r2)
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$3 r2 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$3
                static {
                    /*
                        com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$3 r0 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$3
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$3) com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$3.INSTANCE com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$3
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$3.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$3.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r1) {
                    /*
                        r0 = this;
                        android.media.AudioDeviceInfo r1 = (android.media.AudioDeviceInfo) r1
                        java.lang.String r0 = r1.getAddress()
                        java.lang.String r1 = "0"
                        boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
                        java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$3.invoke(java.lang.Object):java.lang.Object");
                }
            }
            kotlin.sequences.FilteringSequence r5 = kotlin.sequences.SequencesKt___SequencesKt.filter(r5, r2)
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$4 r2 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$4
                static {
                    /*
                        com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$4 r0 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$4
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$4) com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$4.INSTANCE com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$4
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$4.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$4.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r2) {
                    /*
                        r1 = this;
                        android.media.AudioDeviceInfo r2 = (android.media.AudioDeviceInfo) r2
                        com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt r1 = com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt.INSTANCE
                        kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
                        r1.getClass()
                        java.lang.String r1 = com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt.toLogText(r2)
                        java.lang.String r2 = "\t"
                        java.lang.String r0 = "WifiDisplayDeviceController"
                        android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(r2, r1, r0)
                        kotlin.Unit r1 = kotlin.Unit.INSTANCE
                        return r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$4.invoke(java.lang.Object):java.lang.Object");
                }
            }
            kotlin.sequences.TransformingSequence r5 = kotlin.sequences.SequencesKt___SequencesKt.onEach(r5, r2)
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$5 r2 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$5
            r2.<init>()
            kotlin.sequences.FilteringSequence r5 = kotlin.sequences.SequencesKt___SequencesKt.mapNotNull(r5, r2)
            com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$6 r2 = new kotlin.jvm.functions.Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$6
                static {
                    /*
                        com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$6 r0 = new com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$6
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$6) com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$6.INSTANCE com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$6
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$6.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 1
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$6.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function1
                public final java.lang.Object invoke(java.lang.Object r2) {
                    /*
                        r1 = this;
                        com.android.systemui.media.mediaoutput.entity.AudioDevice r2 = (com.android.systemui.media.mediaoutput.entity.AudioDevice) r2
                        java.lang.String r1 = "\t"
                        java.lang.String r0 = "WifiDisplayDeviceController"
                        com.android.systemui.media.mediaoutput.controller.device.AudioMirroringDeviceController$$ExternalSyntheticOutline0.m(r1, r2, r0)
                        kotlin.Unit r1 = kotlin.Unit.INSTANCE
                        return r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$updateDevices$6.invoke(java.lang.Object):java.lang.Object");
                }
            }
            kotlin.sequences.TransformingSequence r5 = kotlin.sequences.SequencesKt___SequencesKt.onEach(r5, r2)
            java.util.List r5 = kotlin.sequences.SequencesKt___SequencesKt.toList(r5)
            kotlinx.coroutines.flow.SharedFlowImpl r4 = r4.devicesFlow
            r0.L$0 = r5
            r0.label = r3
            java.lang.Object r4 = r4.emit(r5, r0)
            if (r4 != r1) goto L76
            goto L78
        L76:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
        L78:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController.access$updateDevices(com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("WifiDisplayDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        if (audioDevice instanceof SmartViewDevice ? true : audioDevice instanceof DexDevice) {
            if (this.isSupportDisplayDeviceVolumeControl) {
                this.audioManager.adjustVolume(audioDevice.getVolume() >= i ? -1 : 1, 0);
            } else {
                this.audioManager.semSetFineVolume(3, i, 0);
            }
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void cancel(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("cancel() - ", audioDevice, "WifiDisplayDeviceController");
        if (audioDevice instanceof SmartViewDevice ? true : audioDevice instanceof DexDevice) {
            this.displayManager.semDisconnectWifiDisplay();
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("WifiDisplayDeviceController", "close()");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void transfer(AudioDevice audioDevice) {
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
            if (localBluetoothManager == null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) == null || (bluetoothA2dp = a2dpProfile.mService) == null) {
                return;
            }
            bluetoothA2dp.setDualPlayMode(false);
        }
    }
}
