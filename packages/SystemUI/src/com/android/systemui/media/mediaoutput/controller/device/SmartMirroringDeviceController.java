package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import androidx.core.os.BundleKt;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.SmartMirroringDevice;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.google.gson.Gson;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SmartMirroringDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final LocalBluetoothManager localBluetoothManager;
    public final Lazy smartMirroringClient$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ AudioManager $audioManager;
        final /* synthetic */ Ref$BooleanRef $isInitialized;
        int label;
        final /* synthetic */ SmartMirroringDeviceController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(AudioManager audioManager, SmartMirroringDeviceController smartMirroringDeviceController, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
            super(2, continuation);
            this.$audioManager = audioManager;
            this.this$0 = smartMirroringDeviceController;
            this.$isInitialized = ref$BooleanRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$audioManager, this.this$0, this.$isInitialized, continuation);
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
                Flow deviceChanged = AudioManagerExtKt.getDeviceChanged(this.$audioManager);
                final AudioManager audioManager = this.$audioManager;
                final SmartMirroringDeviceController smartMirroringDeviceController = this.this$0;
                final Ref$BooleanRef ref$BooleanRef = this.$isInitialized;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Number) obj2).longValue();
                        SmartMirroringDeviceController.access$_init_$updateScan(audioManager, smartMirroringDeviceController, ref$BooleanRef);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (deviceChanged.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ AudioManager $audioManager;
        final /* synthetic */ Ref$BooleanRef $isInitialized;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(AudioManager audioManager, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
            super(2, continuation);
            this.$audioManager = audioManager;
            this.$isInitialized = ref$BooleanRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SmartMirroringDeviceController.this.new AnonymousClass2(this.$audioManager, this.$isInitialized, continuation);
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
                SmartMirroringDeviceController smartMirroringDeviceController = SmartMirroringDeviceController.this;
                Companion companion = SmartMirroringDeviceController.Companion;
                final Flow flow = smartMirroringDeviceController.getSmartMirroringClient().connectionFlow;
                Flow flow2 = new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
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
                                boolean r0 = r6 instanceof com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r6
                                com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1$2$1
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
                                java.lang.Boolean r6 = (java.lang.Boolean) r6
                                boolean r6 = r6.booleanValue()
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
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final SmartMirroringDeviceController smartMirroringDeviceController2 = SmartMirroringDeviceController.this;
                final AudioManager audioManager = this.$audioManager;
                final Ref$BooleanRef ref$BooleanRef = this.$isInitialized;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController.2.2

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$2, reason: invalid class name and collision with other inner class name */
                    public final class C02262 implements FlowCollector {
                        public final /* synthetic */ SmartMirroringDeviceController this$0;

                        public C02262(SmartMirroringDeviceController smartMirroringDeviceController) {
                            this.this$0 = smartMirroringDeviceController;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:15:0x0038  */
                        /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final java.lang.Object emit(java.util.List r23, kotlin.coroutines.Continuation r24) {
                            /*
                                Method dump skipped, instructions count: 274
                                To view this dump change 'Code comments level' option to 'DEBUG'
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController.AnonymousClass2.C02252.C02262.emit(java.util.List, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).getClass();
                        AudioManager audioManager2 = audioManager;
                        Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
                        SmartMirroringDeviceController smartMirroringDeviceController3 = SmartMirroringDeviceController.this;
                        SmartMirroringDeviceController.access$_init_$updateScan(audioManager2, smartMirroringDeviceController3, ref$BooleanRef2);
                        SmartMirroringClient smartMirroringClient = smartMirroringDeviceController3.getSmartMirroringClient();
                        smartMirroringClient.getClass();
                        final CallbackFlowBuilder callbackFlow = FlowKt.callbackFlow(new SmartMirroringClient$registerClient$1(smartMirroringClient, null));
                        Flow flow3 = new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1$2$1, reason: invalid class name */
                                public final class AnonymousClass1 extends ContinuationImpl {
                                    Object L$0;
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

                                public AnonymousClass2(FlowCollector flowCollector) {
                                    this.$this_unsafeFlow = flowCollector;
                                }

                                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                                    /*
                                        r6 = this;
                                        boolean r0 = r8 instanceof com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                        if (r0 == 0) goto L13
                                        r0 = r8
                                        com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                        int r1 = r0.label
                                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                        r3 = r1 & r2
                                        if (r3 == 0) goto L13
                                        int r1 = r1 - r2
                                        r0.label = r1
                                        goto L18
                                    L13:
                                        com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1$2$1
                                        r0.<init>(r8)
                                    L18:
                                        java.lang.Object r8 = r0.result
                                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                        int r2 = r0.label
                                        r3 = 1
                                        if (r2 == 0) goto L2f
                                        if (r2 != r3) goto L27
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        goto L65
                                    L27:
                                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                                        r6.<init>(r7)
                                        throw r6
                                    L2f:
                                        kotlin.ResultKt.throwOnFailure(r8)
                                        java.util.List r7 = (java.util.List) r7
                                        java.lang.Iterable r7 = (java.lang.Iterable) r7
                                        java.util.Iterator r8 = r7.iterator()
                                    L3a:
                                        boolean r2 = r8.hasNext()
                                        if (r2 == 0) goto L5a
                                        java.lang.Object r2 = r8.next()
                                        com.android.systemui.media.mediaoutput.controller.device.DeviceInfo r2 = (com.android.systemui.media.mediaoutput.controller.device.DeviceInfo) r2
                                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                                        java.lang.String r5 = "\t"
                                        r4.<init>(r5)
                                        r4.append(r2)
                                        java.lang.String r2 = r4.toString()
                                        java.lang.String r4 = "SmartMirroringDeviceController"
                                        android.util.Log.d(r4, r2)
                                        goto L3a
                                    L5a:
                                        r0.label = r3
                                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                        java.lang.Object r6 = r6.emit(r7, r0)
                                        if (r6 != r1) goto L65
                                        return r1
                                    L65:
                                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                        return r6
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector2, Continuation continuation2) {
                                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation2);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                            }
                        };
                        MediaOutputConst.INSTANCE.getClass();
                        Object collect = FlowKt.m3462debounceHG0u8IE(flow3, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT).collect(new C02262(smartMirroringDeviceController3), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow2.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Context $context;
        int label;
        final /* synthetic */ SmartMirroringDeviceController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Context context, SmartMirroringDeviceController smartMirroringDeviceController, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.this$0 = smartMirroringDeviceController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$context, this.this$0, continuation);
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
                Companion companion = SmartMirroringDeviceController.Companion;
                Context context = this.$context;
                companion.getClass();
                CallbackFlowBuilder callbackFlow = FlowKt.callbackFlow(new SmartMirroringDeviceController$Companion$castDeviceStateChanges$1(context, null));
                final SmartMirroringDeviceController smartMirroringDeviceController = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (((Number) obj2).intValue() != 0) {
                            Companion companion2 = SmartMirroringDeviceController.Companion;
                            SmartMirroringDeviceController.this.getSmartMirroringClient().stopScanMirroring();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (callbackFlow.collect(flowCollector, this) == coroutineSingletons) {
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

    public SmartMirroringDeviceController(Context context, AudioManager audioManager, LocalBluetoothManager localBluetoothManager) {
        this.localBluetoothManager = localBluetoothManager;
        this.smartMirroringClient$delegate = LazyKt__LazyJVMKt.lazy(new SmartMirroringDeviceController$$ExternalSyntheticLambda0(0, context, audioManager));
        Log.d("SmartMirroringDeviceController", "init()");
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(audioManager, this, ref$BooleanRef, null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass2(audioManager, ref$BooleanRef, null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass3(context, this, null), 3);
    }

    public static final void access$_init_$updateScan(AudioManager audioManager, SmartMirroringDeviceController smartMirroringDeviceController, Ref$BooleanRef ref$BooleanRef) {
        List list;
        Object failure;
        LocalBluetoothCastProfileManager localBluetoothCastProfileManager;
        AudioCastProfile audioCastProfile;
        AudioDeviceInfo[] devices = audioManager.getDevices(2);
        ArrayList arrayList = new ArrayList();
        for (AudioDeviceInfo audioDeviceInfo : devices) {
            if (audioDeviceInfo.getType() == 25) {
                arrayList.add(audioDeviceInfo);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (Intrinsics.areEqual(((AudioDeviceInfo) obj).getAddress(), "0")) {
                arrayList2.add(obj);
            }
        }
        LocalBluetoothManager localBluetoothManager = smartMirroringDeviceController.localBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothCastProfileManager = localBluetoothManager.mLocalCastProfileManager) == null || (audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) == null || (list = audioCastProfile.getConnectedDevices()) == null) {
            list = EmptyList.INSTANCE;
        }
        if (!arrayList2.isEmpty() || !list.isEmpty()) {
            smartMirroringDeviceController.getSmartMirroringClient().stopScanMirroring();
        } else if (!ref$BooleanRef.element) {
            SmartMirroringClient smartMirroringClient = smartMirroringDeviceController.getSmartMirroringClient();
            smartMirroringClient.getClass();
            Log.d("SmartMirroringClient", "startScanMirroring()");
            Messenger messenger = smartMirroringClient.service;
            if (messenger != null) {
                AudioDeviceInfo[] devices2 = smartMirroringClient.audioManager.getDevices(2);
                int length = devices2.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (devices2[i2].getType() == 25) {
                        messenger = null;
                        break;
                    }
                    i2++;
                }
                if (messenger != null) {
                    try {
                        int i3 = Result.$r8$clinit;
                        smartMirroringClient.isScanStarted = true;
                        messenger.send(Message.obtain(null, 3, 0, 0));
                        failure = Unit.INSTANCE;
                    } catch (Throwable th) {
                        int i4 = Result.$r8$clinit;
                        failure = new Result.Failure(th);
                    }
                    Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                    if (m3422exceptionOrNullimpl != null) {
                        m3422exceptionOrNullimpl.printStackTrace();
                    }
                    Result.m3421boximpl(failure);
                }
            }
        }
        ref$BooleanRef.element = true;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit cancel(AudioDevice audioDevice) {
        Object failure;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("cancel() - ", audioDevice, "SmartMirroringDeviceController");
        if (audioDevice instanceof SmartMirroringDevice) {
            SmartMirroringClient smartMirroringClient = getSmartMirroringClient();
            smartMirroringClient.getClass();
            Log.d("SmartMirroringClient", "disconnect()");
            Messenger messenger = smartMirroringClient.service;
            if (messenger != null) {
                try {
                    int i = Result.$r8$clinit;
                    messenger.send(Message.obtain(null, 6, 0, 0));
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
                }
                Result.m3421boximpl(failure);
            }
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("SmartMirroringDeviceController", "close()");
        SmartMirroringClient smartMirroringClient = getSmartMirroringClient();
        smartMirroringClient.getClass();
        Log.d("SmartMirroringClient", "close()");
        smartMirroringClient.stopScanMirroring();
    }

    public final SmartMirroringClient getSmartMirroringClient() {
        return (SmartMirroringClient) this.smartMirroringClient$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        Object failure;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "SmartMirroringDeviceController");
        if (audioDevice instanceof SmartMirroringDevice) {
            SmartMirroringClient smartMirroringClient = getSmartMirroringClient();
            DeviceInfo deviceInfo = ((SmartMirroringDevice) audioDevice).deviceInfo;
            if (deviceInfo == null) {
                deviceInfo = null;
            }
            smartMirroringClient.getClass();
            Log.d("SmartMirroringClient", "connect() - " + deviceInfo);
            Messenger messenger = smartMirroringClient.service;
            if (messenger != null) {
                try {
                    int i = Result.$r8$clinit;
                    Message obtain = Message.obtain(null, 5, 0, 0);
                    obtain.setData(BundleKt.bundleOf(new Pair("deviceInfo", new Gson().toJson(deviceInfo))));
                    messenger.send(obtain);
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    m3422exceptionOrNullimpl.printStackTrace();
                }
                Result.m3421boximpl(failure);
            }
        }
        return Unit.INSTANCE;
    }
}
