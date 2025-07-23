package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.util.Log;
import androidx.datastore.core.DataStore;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$6;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$7;
import com.android.systemui.media.mediaoutput.common.DataStoreExt;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$1;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.GroupDevice;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class RouteDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Context context;
    public final DataStore dataStore;
    public boolean isCastingPriority;
    public boolean isSupportForTransferDuringRouting;
    public boolean isSupportTransferableRoutesWhileConnecting;
    public final Lazy routeDevices$delegate;
    public final Lazy router2Manager$delegate;
    public boolean isSpotifyCastingPriority = true;
    public final Lazy bluetoothAdapter$delegate = LazyKt__LazyJVMKt.lazy(new RouteDeviceController$$ExternalSyntheticLambda0());
    public final StateFlowImpl refreshRoutes = StateFlowKt.MutableStateFlow(0L);
    public String packageName = "";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RouteDeviceController.this.new AnonymousClass1(continuation);
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
                DataStore dataStore = RouteDeviceController.this.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$6 dataStoreDebugLabsExt$special$$inlined$map$6 = new DataStoreDebugLabsExt$special$$inlined$map$6(dataStore.getData());
                final RouteDeviceController routeDeviceController = RouteDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        RouteDeviceController routeDeviceController2 = RouteDeviceController.this;
                        routeDeviceController2.isSupportTransferableRoutesWhileConnecting = booleanValue;
                        routeDeviceController2.refreshRoutes.updateState(null, new Long(System.currentTimeMillis()));
                        Unit unit = Unit.INSTANCE;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        return unit;
                    }
                };
                this.label = 1;
                if (dataStoreDebugLabsExt$special$$inlined$map$6.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RouteDeviceController.this.new AnonymousClass2(continuation);
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
                DataStore dataStore = RouteDeviceController.this.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$7 dataStoreDebugLabsExt$special$$inlined$map$7 = new DataStoreDebugLabsExt$special$$inlined$map$7(dataStore.getData());
                final RouteDeviceController routeDeviceController = RouteDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        RouteDeviceController routeDeviceController2 = RouteDeviceController.this;
                        routeDeviceController2.isSupportForTransferDuringRouting = booleanValue;
                        routeDeviceController2.refreshRoutes.updateState(null, new Long(System.currentTimeMillis()));
                        Unit unit = Unit.INSTANCE;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        return unit;
                    }
                };
                this.label = 1;
                if (dataStoreDebugLabsExt$special$$inlined$map$7.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RouteDeviceController.this.new AnonymousClass3(continuation);
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
                DataStoreExt dataStoreExt = DataStoreExt.INSTANCE;
                DataStore dataStore = RouteDeviceController.this.dataStore;
                dataStoreExt.getClass();
                DataStoreExt$special$$inlined$map$2 dataStoreExt$special$$inlined$map$2 = new DataStoreExt$special$$inlined$map$2(new DataStoreExt$special$$inlined$map$1(dataStore.getData()));
                final RouteDeviceController routeDeviceController = RouteDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        RouteDeviceController routeDeviceController2 = RouteDeviceController.this;
                        routeDeviceController2.isCastingPriority = booleanValue;
                        routeDeviceController2.refreshRoutes.updateState(null, new Long(System.currentTimeMillis()));
                        Unit unit = Unit.INSTANCE;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        return unit;
                    }
                };
                this.label = 1;
                if (dataStoreExt$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RouteDeviceController.this.new AnonymousClass4(continuation);
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
                DataStoreExt dataStoreExt = DataStoreExt.INSTANCE;
                DataStore dataStore = RouteDeviceController.this.dataStore;
                dataStoreExt.getClass();
                DataStoreExt$special$$inlined$map$3 dataStoreExt$special$$inlined$map$3 = new DataStoreExt$special$$inlined$map$3(dataStore.getData());
                final RouteDeviceController routeDeviceController = RouteDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        RouteDeviceController routeDeviceController2 = RouteDeviceController.this;
                        routeDeviceController2.isSpotifyCastingPriority = booleanValue;
                        routeDeviceController2.refreshRoutes.updateState(null, new Long(System.currentTimeMillis()));
                        Unit unit = Unit.INSTANCE;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        return unit;
                    }
                };
                this.label = 1;
                if (dataStoreExt$special$$inlined$map$3.collect(flowCollector, this) == coroutineSingletons) {
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
    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass5(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return RouteDeviceController.this.new AnonymousClass5(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final Flow flow = (Flow) RouteDeviceController.this.routeDevices$delegate.getValue();
                Flow flow2 = new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$5$invokeSuspend$$inlined$map$1

                    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$5$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$5$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r8 instanceof com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$5$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r8
                                com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$5$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$5$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$5$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$5$invokeSuspend$$inlined$map$1$2$1
                                r0.<init>(r8)
                            L18:
                                java.lang.Object r8 = r0.result
                                kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                int r2 = r0.label
                                r3 = 1
                                if (r2 == 0) goto L2f
                                if (r2 != r3) goto L27
                                kotlin.ResultKt.throwOnFailure(r8)
                                goto L59
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
                                if (r2 == 0) goto L4e
                                java.lang.Object r2 = r8.next()
                                com.android.systemui.media.mediaoutput.entity.AudioDevice r2 = (com.android.systemui.media.mediaoutput.entity.AudioDevice) r2
                                java.lang.String r4 = "\t"
                                java.lang.String r5 = "RouteDeviceController"
                                com.android.systemui.media.mediaoutput.controller.device.AudioMirroringDeviceController$$ExternalSyntheticOutline0.m(r4, r2, r5)
                                goto L3a
                            L4e:
                                r0.label = r3
                                kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                                java.lang.Object r6 = r6.emit(r7, r0)
                                if (r6 != r1) goto L59
                                return r1
                            L59:
                                kotlin.Unit r6 = kotlin.Unit.INSTANCE
                                return r6
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$5$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final RouteDeviceController routeDeviceController = RouteDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController.5.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object emit = RouteDeviceController.this.devicesFlow.emit((List) obj2, continuation);
                        return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
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
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ControllerType.values().length];
            try {
                iArr[ControllerType.AudioMirroring.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ControllerType.ChromeCast.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public RouteDeviceController(Context context, AudioManager audioManager, DataStore dataStore) {
        this.context = context;
        this.audioManager = audioManager;
        this.dataStore = dataStore;
        final int i = 1;
        this.router2Manager$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$$ExternalSyntheticLambda2
            public final /* synthetic */ RouteDeviceController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final RouteDeviceController routeDeviceController = this.f$0;
                switch (i) {
                    case 0:
                        RouteDeviceController.Companion companion = RouteDeviceController.Companion;
                        MediaRouter2Manager router2Manager$1 = routeDeviceController.getRouter2Manager$1();
                        RouteDeviceController.Companion.getClass();
                        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.buffer$default(FlowKt.callbackFlow(new RouteDeviceController$Companion$routeDeviceChanges$1(router2Manager$1, null)), -1, 2), routeDeviceController.refreshRoutes, new RouteDeviceController$routeDevices$2$1(routeDeviceController, null));
                        MediaOutputConst.INSTANCE.getClass();
                        final Flow m3462debounceHG0u8IE = FlowKt.m3462debounceHG0u8IE(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                        return new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ RouteDeviceController this$0;

                                /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1$2$1, reason: invalid class name */
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

                                public AnonymousClass2(FlowCollector flowCollector, RouteDeviceController routeDeviceController) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = routeDeviceController;
                                }

                                /* JADX WARN: Code restructure failed: missing block: B:265:0x0663, code lost:
                                
                                    if (r8 != 0) goto L214;
                                 */
                                /* JADX WARN: Multi-variable type inference failed */
                                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                                /* JADX WARN: Type inference failed for: r0v1, types: [kotlinx.coroutines.flow.FlowCollector] */
                                /* JADX WARN: Type inference failed for: r11v21, types: [T, java.util.Set] */
                                /* JADX WARN: Type inference failed for: r11v30, types: [T, java.util.Set] */
                                /* JADX WARN: Type inference failed for: r11v39, types: [T, java.util.Set] */
                                /* JADX WARN: Type inference failed for: r11v7, types: [T, kotlin.collections.EmptySet] */
                                /* JADX WARN: Type inference failed for: r1v31, types: [T, java.util.Set] */
                                /* JADX WARN: Type inference failed for: r8v10, types: [java.lang.Object] */
                                /* JADX WARN: Type inference failed for: r8v14, types: [java.lang.Iterable, java.util.ArrayList] */
                                /* JADX WARN: Type inference failed for: r8v15 */
                                /* JADX WARN: Type inference failed for: r8v16 */
                                /* JADX WARN: Type inference failed for: r8v17, types: [java.util.List] */
                                /* JADX WARN: Type inference failed for: r8v6, types: [java.util.List] */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                                */
                                public final java.lang.Object emit(java.lang.Object r31, kotlin.coroutines.Continuation r32) {
                                    /*
                                        Method dump skipped, instructions count: 1829
                                        To view this dump change 'Code comments level' option to 'DEBUG'
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, routeDeviceController), continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                            }
                        };
                    default:
                        return MediaRouter2Manager.getInstance(routeDeviceController.context);
                }
            }
        });
        final int i2 = 0;
        this.routeDevices$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$$ExternalSyntheticLambda2
            public final /* synthetic */ RouteDeviceController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                final RouteDeviceController routeDeviceController = this.f$0;
                switch (i2) {
                    case 0:
                        RouteDeviceController.Companion companion = RouteDeviceController.Companion;
                        MediaRouter2Manager router2Manager$1 = routeDeviceController.getRouter2Manager$1();
                        RouteDeviceController.Companion.getClass();
                        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.buffer$default(FlowKt.callbackFlow(new RouteDeviceController$Companion$routeDeviceChanges$1(router2Manager$1, null)), -1, 2), routeDeviceController.refreshRoutes, new RouteDeviceController$routeDevices$2$1(routeDeviceController, null));
                        MediaOutputConst.INSTANCE.getClass();
                        final Flow m3462debounceHG0u8IE = FlowKt.m3462debounceHG0u8IE(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                        return new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1

                            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                            /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                                public final /* synthetic */ RouteDeviceController this$0;

                                /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1$2$1, reason: invalid class name */
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

                                public AnonymousClass2(FlowCollector flowCollector, RouteDeviceController routeDeviceController) {
                                    this.$this_unsafeFlow = flowCollector;
                                    this.this$0 = routeDeviceController;
                                }

                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj, Continuation continuation) {
                                    /*
                                        Method dump skipped, instructions count: 1829
                                        To view this dump change 'Code comments level' option to 'DEBUG'
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, routeDeviceController), continuation);
                                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                            }
                        };
                    default:
                        return MediaRouter2Manager.getInstance(routeDeviceController.context);
                }
            }
        });
        Log.d("RouteDeviceController", "init()");
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass2(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass3(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass4(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass5(null), 3);
    }

    public static final boolean access$getFeatureAvailable(RouteDeviceController routeDeviceController, MediaRoute2Info mediaRoute2Info) {
        routeDeviceController.getClass();
        List<String> features = mediaRoute2Info.getFeatures();
        if ((features instanceof Collection) && features.isEmpty()) {
            return true;
        }
        Iterator<T> it = features.iterator();
        while (it.hasNext()) {
            if (Arrays.asList("android.media.route.feature.EMPTY", "com.google.android.gms.cast.CATEGORY_CAST_DYNAMIC_SESSION").contains((String) it.next())) {
                return false;
            }
        }
        return true;
    }

    public static final RouteListingPreference.Item access$routeDevices_delegate$lambda$51$lambda$50$getPreferenceItems(String str, List list) {
        Object obj;
        Iterator it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((RouteListingPreference.Item) obj).getRouteId(), str)) {
                break;
            }
        }
        return (RouteListingPreference.Item) obj;
    }

    public static /* synthetic */ RouteDevice createRouteDevice$default(RouteDeviceController routeDeviceController, MediaRoute2Info mediaRoute2Info, boolean z, boolean z2, RouteListingPreference.Item item, int i) {
        State state = State.CONNECTED;
        if ((i & 4) != 0) {
            z = false;
        }
        boolean z3 = z;
        if ((i & 16) != 0) {
            z2 = true;
        }
        return routeDeviceController.createRouteDevice(mediaRoute2Info, state, z3, false, z2, false, item);
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x008d, code lost:
    
        if (r2 != null) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00bf A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object transfer$suspendImpl(com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController r7, com.android.systemui.media.mediaoutput.entity.AudioDevice r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController.transfer$suspendImpl(com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController, com.android.systemui.media.mediaoutput.entity.AudioDevice, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("RouteDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        if (audioDevice instanceof RouteDevice) {
            getRouter2Manager$1().setRouteVolume(((RouteDevice) audioDevice).getMediaRoute2Info(), i);
        } else if (audioDevice instanceof GroupDevice) {
            MediaRouter2Manager router2Manager$1 = getRouter2Manager$1();
            RoutingSessionInfo routingSessionInfo = ((GroupDevice) audioDevice).routingSessionInfo;
            if (routingSessionInfo == null) {
                routingSessionInfo = null;
            }
            router2Manager$1.setSessionVolume(routingSessionInfo, i);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit cancel(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("cancel() - ", audioDevice, "RouteDeviceController");
        Object obj = null;
        if ((audioDevice instanceof RouteDevice ? ((RouteDevice) audioDevice).getMediaRoute2Info() : null) != null) {
            List remoteSessions = getRouter2Manager$1().getRemoteSessions();
            ListIterator listIterator = remoteSessions.listIterator(remoteSessions.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    break;
                }
                Object previous = listIterator.previous();
                if (Intrinsics.areEqual(((RoutingSessionInfo) previous).getClientPackageName(), getPackageName())) {
                    obj = previous;
                    break;
                }
            }
            RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) obj;
            if (routingSessionInfo != null) {
                getRouter2Manager$1().releaseSession(routingSessionInfo);
            }
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("RouteDeviceController", "close()");
    }

    public abstract RouteDevice createRouteDevice(MediaRoute2Info mediaRoute2Info, State state, boolean z, boolean z2, boolean z3, boolean z4, RouteListingPreference.Item item);

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object deselect(AudioDevice audioDevice, ContinuationImpl continuationImpl) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("deselect() - ", audioDevice, "RouteDeviceController");
        Object obj = null;
        MediaRoute2Info mediaRoute2Info = audioDevice instanceof RouteDevice ? ((RouteDevice) audioDevice).getMediaRoute2Info() : null;
        if (mediaRoute2Info != null) {
            Iterator it = CollectionsKt___CollectionsKt.reversed(getRouter2Manager$1().getRoutingSessions(getPackageName())).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (((RoutingSessionInfo) next).getSelectableRoutes().contains(mediaRoute2Info.getId())) {
                    obj = next;
                    break;
                }
            }
            RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) obj;
            if (routingSessionInfo != null) {
                getRouter2Manager$1().deselectRoute(routingSessionInfo, mediaRoute2Info);
                MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
                SaEvent.WifiSpeakerCheckBox wifiSpeakerCheckBox = SaEvent.WifiSpeakerCheckBox.INSTANCE;
                SaCustom[] saCustomArr = {new SaCustom.App(getPackageName())};
                moSaLogging.getClass();
                MoSaLogging.send(wifiSpeakerCheckBox, saCustomArr);
                MoSaLogging.send(SaEvent.StreamExpansionRemove.INSTANCE, new SaCustom.Number(routingSessionInfo.getSelectedRoutes().size() - 1));
            }
        }
        return Unit.INSTANCE;
    }

    public RoutingSessionInfo getAvailableSession(List list) {
        return (RoutingSessionInfo) CollectionsKt___CollectionsKt.lastOrNull(list);
    }

    public abstract ControllerType getControllerType();

    public String getPackageName() {
        return this.packageName;
    }

    public final MediaRouter2Manager getRouter2Manager$1() {
        return (MediaRouter2Manager) this.router2Manager$delegate.getValue();
    }

    public List getTransferableRoutes() {
        List transferableRoutes;
        String packageName = getPackageName();
        if (!(packageName.startsWith("com.spotify.music") ? this.isSpotifyCastingPriority : this.isCastingPriority)) {
            packageName = null;
        }
        return (packageName == null || (transferableRoutes = getRouter2Manager$1().getTransferableRoutes(packageName)) == null) ? EmptyList.INSTANCE : transferableRoutes;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public Unit select(AudioDevice audioDevice, ContinuationImpl continuationImpl) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("select() - ", audioDevice, "RouteDeviceController");
        Object obj = null;
        MediaRoute2Info mediaRoute2Info = audioDevice instanceof RouteDevice ? ((RouteDevice) audioDevice).getMediaRoute2Info() : null;
        if (mediaRoute2Info != null) {
            Iterator it = CollectionsKt___CollectionsKt.reversed(getRouter2Manager$1().getRoutingSessions(getPackageName())).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Object next = it.next();
                if (((RoutingSessionInfo) next).getSelectableRoutes().contains(mediaRoute2Info.getId())) {
                    obj = next;
                    break;
                }
            }
            RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) obj;
            if (routingSessionInfo != null) {
                getRouter2Manager$1().selectRoute(routingSessionInfo, mediaRoute2Info);
                MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
                SaEvent.WifiSpeakerCheckBox wifiSpeakerCheckBox = SaEvent.WifiSpeakerCheckBox.INSTANCE;
                SaCustom[] saCustomArr = {new SaCustom.App(getPackageName())};
                moSaLogging.getClass();
                MoSaLogging.send(wifiSpeakerCheckBox, saCustomArr);
                MoSaLogging.send(SaEvent.StreamExpansionAdd.INSTANCE, new SaCustom.Number(routingSessionInfo.getSelectedRoutes().size() + 1));
            }
        }
        return Unit.INSTANCE;
    }

    public void setPackageName(String str) {
        if (Intrinsics.areEqual(this.packageName, str)) {
            return;
        }
        MediaSessions$H$$ExternalSyntheticOutline0.m("packageName changed : ", this.packageName, " -> ", str, "RouteDeviceController");
        if (!StringsKt__StringsKt.contains(str, ".", false)) {
            str = null;
        }
        if (str == null) {
            str = "";
        }
        this.packageName = str;
        BuildersKt.launch$default(getControllerScope(), null, null, new RouteDeviceController$packageName$2(this, null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public Object transfer(AudioDevice audioDevice, Continuation continuation) {
        return transfer$suspendImpl(this, audioDevice, (ContinuationImpl) continuation);
    }
}
