package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.os.Process;
import android.util.Log;
import androidx.datastore.core.DataStore;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$6;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$7;
import com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.AudioMirroringDevice;
import com.android.systemui.media.mediaoutput.entity.BluetoothDevice;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.entity.ChromeCastDevice;
import com.android.systemui.media.mediaoutput.entity.GroupDevice;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
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

public abstract class RouteDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Context context;
    public final DataStore dataStore;
    public boolean isSupportTransferableRoutesWhileConnecting;
    public boolean isSupportForTransferDuringRouting = true;
    public final Lazy router2Manager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$router2Manager$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return MediaRouter2Manager.getInstance(RouteDeviceController.this.context);
        }
    });
    public final StateFlowImpl refreshRoutes = StateFlowKt.MutableStateFlow("");
    public String packageName = "";
    public final Lazy routeDevices$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices$2

        /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function3 {
            int label;
            final /* synthetic */ RouteDeviceController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(RouteDeviceController routeDeviceController, Continuation continuation) {
                super(3, continuation);
                this.this$0 = routeDeviceController;
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return new AnonymousClass1(this.this$0, (Continuation) obj3).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return this.this$0.getRouter2Manager().getRemoteSessions();
            }
        }

        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            RouteDeviceController.Companion companion = RouteDeviceController.Companion;
            MediaRouter2Manager router2Manager = RouteDeviceController.this.getRouter2Manager();
            companion.getClass();
            Flow buffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new RouteDeviceController$Companion$routeDeviceChanges$1(router2Manager, null)), -1);
            RouteDeviceController routeDeviceController = RouteDeviceController.this;
            final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(buffer$default, routeDeviceController.refreshRoutes, new AnonymousClass1(routeDeviceController, null));
            final RouteDeviceController routeDeviceController2 = RouteDeviceController.this;
            return new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices$2$invoke$$inlined$map$1

                /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices$2$invoke$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ RouteDeviceController this$0;

                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices$2$invoke$$inlined$map$1$2$1, reason: invalid class name */
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

                    /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                    /* JADX WARN: Type inference failed for: r11v0, types: [T, kotlin.collections.EmptySet] */
                    /* JADX WARN: Type inference failed for: r1v19, types: [T, java.util.Set] */
                    /* JADX WARN: Type inference failed for: r1v26, types: [T, java.util.Set] */
                    /* JADX WARN: Type inference failed for: r1v33, types: [T, java.util.Set] */
                    /* JADX WARN: Type inference failed for: r1v53, types: [T, java.util.Set] */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r21, kotlin.coroutines.Continuation r22) {
                        /*
                            Method dump skipped, instructions count: 1046
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices$2$invoke$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, routeDeviceController2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
        }
    });

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
                        routeDeviceController2.refreshRoutes.setValue(routeDeviceController2.getPackageName());
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
                        routeDeviceController2.refreshRoutes.setValue(routeDeviceController2.getPackageName());
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
                final Flow debounce = FlowKt.debounce((Flow) RouteDeviceController.this.routeDevices$delegate.getValue(), 50L);
                Flow flow = new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$3$invokeSuspend$$inlined$map$1

                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$3$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$3$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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
                                boolean r0 = r8 instanceof com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$3$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                if (r0 == 0) goto L13
                                r0 = r8
                                com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$3$invokeSuspend$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$3$invokeSuspend$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                int r1 = r0.label
                                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                r3 = r1 & r2
                                if (r3 == 0) goto L13
                                int r1 = r1 - r2
                                r0.label = r1
                                goto L18
                            L13:
                                com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$3$invokeSuspend$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$3$invokeSuspend$$inlined$map$1$2$1
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
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$3$invokeSuspend$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                    }
                };
                final RouteDeviceController routeDeviceController = RouteDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController.3.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object emit = RouteDeviceController.this.devicesFlow.emit((List) obj2, continuation);
                        return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
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

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public RouteDeviceController(Context context, AudioManager audioManager, DataStore dataStore) {
        this.context = context;
        this.audioManager = audioManager;
        this.dataStore = dataStore;
        Log.d("RouteDeviceController", "init()");
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass2(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass3(null), 3);
    }

    public static final boolean access$getFeatureAvailable(RouteDeviceController routeDeviceController, MediaRoute2Info mediaRoute2Info) {
        routeDeviceController.getClass();
        List<String> features = mediaRoute2Info.getFeatures();
        if ((features instanceof Collection) && features.isEmpty()) {
            return true;
        }
        for (String str : features) {
            if (Intrinsics.areEqual(str, "android.media.route.feature.EMPTY") || Intrinsics.areEqual(str, "com.google.android.gms.cast.CATEGORY_CAST_DYNAMIC_SESSION")) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ AudioDevice createRouteDevice$default(RouteDeviceController routeDeviceController, MediaRoute2Info mediaRoute2Info, boolean z, boolean z2, int i) {
        State state = State.CONNECTED;
        if ((i & 4) != 0) {
            z = false;
        }
        boolean z3 = z;
        if ((i & 16) != 0) {
            z2 = true;
        }
        return routeDeviceController.createRouteDevice(mediaRoute2Info, state, z3, false, z2, false);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("RouteDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        if (audioDevice instanceof ChromeCastDevice) {
            MediaRouter2Manager router2Manager = getRouter2Manager();
            MediaRoute2Info mediaRoute2Info = ((ChromeCastDevice) audioDevice).mediaRoute2Info;
            router2Manager.setRouteVolume(mediaRoute2Info != null ? mediaRoute2Info : null, i);
        } else if (audioDevice instanceof AudioMirroringDevice) {
            MediaRouter2Manager router2Manager2 = getRouter2Manager();
            MediaRoute2Info mediaRoute2Info2 = ((AudioMirroringDevice) audioDevice).mediaRoute2Info;
            router2Manager2.setRouteVolume(mediaRoute2Info2 != null ? mediaRoute2Info2 : null, i);
        } else if (audioDevice instanceof GroupDevice) {
            MediaRouter2Manager router2Manager3 = getRouter2Manager();
            RoutingSessionInfo routingSessionInfo = ((GroupDevice) audioDevice).routingSessionInfo;
            router2Manager3.setSessionVolume(routingSessionInfo != null ? routingSessionInfo : null, i);
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void cancel(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("cancel() - ", audioDevice, "RouteDeviceController");
        Object obj = null;
        if ((audioDevice instanceof RouteDevice ? ((RouteDevice) audioDevice).getMediaRoute2Info() : null) != null) {
            List remoteSessions = getRouter2Manager().getRemoteSessions();
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
                getRouter2Manager().releaseSession(routingSessionInfo);
            }
        }
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("RouteDeviceController", "close()");
    }

    public abstract GroupDevice createGroupDevice(RoutingSessionInfo routingSessionInfo, List list);

    public abstract AudioDevice createRouteDevice(MediaRoute2Info mediaRoute2Info, State state, boolean z, boolean z2, boolean z3, boolean z4);

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void deselect(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("deselect() - ", audioDevice, "RouteDeviceController");
        Object obj = null;
        MediaRoute2Info mediaRoute2Info = audioDevice instanceof RouteDevice ? ((RouteDevice) audioDevice).getMediaRoute2Info() : null;
        if (mediaRoute2Info != null) {
            Iterator it = CollectionsKt___CollectionsKt.reversed(getRouter2Manager().getRoutingSessions(getPackageName())).iterator();
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
                getRouter2Manager().deselectRoute(routingSessionInfo, mediaRoute2Info);
            }
        }
    }

    public RoutingSessionInfo getAvailableSession(List list) {
        return (RoutingSessionInfo) CollectionsKt___CollectionsKt.lastOrNull(list);
    }

    public String getPackageName() {
        return this.packageName;
    }

    public final MediaRouter2Manager getRouter2Manager() {
        return (MediaRouter2Manager) this.router2Manager$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public void select(AudioDevice audioDevice) {
        MediaRoute2Info mediaRoute2Info;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("select() - ", audioDevice, "RouteDeviceController");
        Object obj = null;
        if (!(audioDevice instanceof ChromeCastDevice) ? !(audioDevice instanceof AudioMirroringDevice) || (mediaRoute2Info = ((AudioMirroringDevice) audioDevice).mediaRoute2Info) == null : (mediaRoute2Info = ((ChromeCastDevice) audioDevice).mediaRoute2Info) == null) {
            mediaRoute2Info = null;
        }
        if (mediaRoute2Info != null) {
            Iterator it = CollectionsKt___CollectionsKt.reversed(getRouter2Manager().getRoutingSessions(getPackageName())).iterator();
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
                getRouter2Manager().selectRoute(routingSessionInfo, mediaRoute2Info);
            }
        }
    }

    public void setPackageName(String str) {
        if (Intrinsics.areEqual(this.packageName, str)) {
            return;
        }
        MWBixbyController$$ExternalSyntheticOutline0.m("packageName changed : ", this.packageName, " -> ", str, "RouteDeviceController");
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
    public void transfer(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "RouteDeviceController");
        Pair pair = null;
        if (audioDevice instanceof BuiltInDevice) {
            BuiltInDevice builtInDevice = (BuiltInDevice) audioDevice;
            RouteDevice routeDevice = builtInDevice.routeDevice;
            MediaRoute2Info mediaRoute2Info = routeDevice != null ? routeDevice.getMediaRoute2Info() : null;
            AudioDeviceInfo audioDeviceInfo = builtInDevice.audioDeviceInfo;
            pair = new Pair(mediaRoute2Info, audioDeviceInfo != null ? audioDeviceInfo : null);
        } else if (audioDevice instanceof BluetoothDevice) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice) audioDevice;
            RouteDevice routeDevice2 = bluetoothDevice.routeDevice;
            pair = new Pair(routeDevice2 != null ? routeDevice2.getMediaRoute2Info() : null, bluetoothDevice.audioDeviceInfo);
        }
        if (pair != null) {
            MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) pair.component1();
            AudioDeviceInfo audioDeviceInfo2 = (AudioDeviceInfo) pair.component2();
            if (mediaRoute2Info2 != null) {
                getRouter2Manager().transfer(getPackageName(), mediaRoute2Info2, Process.myUserHandle());
                Iterator it = getRouter2Manager().getRoutingSessions(getPackageName()).iterator();
                while (it.hasNext()) {
                    getRouter2Manager().releaseSession((RoutingSessionInfo) it.next());
                }
            }
            if (audioDeviceInfo2 != null) {
                AudioManagerExtKt.setDeviceForced(this.audioManager, audioDeviceInfo2);
            }
        }
    }
}
