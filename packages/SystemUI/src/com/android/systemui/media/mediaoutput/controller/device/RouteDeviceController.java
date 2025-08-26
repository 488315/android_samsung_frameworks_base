package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.os.Process;
import android.util.Log;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.datastore.core.DataStore;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$6;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$7;
import com.android.systemui.media.mediaoutput.common.DataStoreExt;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$2;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$3;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.BluetoothRoute;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.entity.GroupDevice;
import com.android.systemui.media.mediaoutput.entity.MusicShareDevice;
import com.android.systemui.media.mediaoutput.entity.RouteDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.MediaRoute2InfoExtKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
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
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

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
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        RouteDeviceController routeDeviceController2 = routeDeviceController;
                        routeDeviceController2.isSupportTransferableRoutesWhileConnecting = zBooleanValue;
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
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        RouteDeviceController routeDeviceController2 = routeDeviceController;
                        routeDeviceController2.isSupportForTransferDuringRouting = zBooleanValue;
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
                DataStoreExt$special$$inlined$map$2 dataStoreExt$special$$inlined$map$2IsCastingPriority = DataStoreExt.isCastingPriority(dataStore);
                final RouteDeviceController routeDeviceController = RouteDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        RouteDeviceController routeDeviceController2 = routeDeviceController;
                        routeDeviceController2.isCastingPriority = zBooleanValue;
                        routeDeviceController2.refreshRoutes.updateState(null, new Long(System.currentTimeMillis()));
                        Unit unit = Unit.INSTANCE;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        return unit;
                    }
                };
                this.label = 1;
                if (dataStoreExt$special$$inlined$map$2IsCastingPriority.collect(flowCollector, this) == coroutineSingletons) {
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
                DataStoreExt$special$$inlined$map$3 dataStoreExt$special$$inlined$map$3 = new DataStoreExt$special$$inlined$map$3(dataStore.getData(), dataStore);
                final RouteDeviceController routeDeviceController = RouteDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        RouteDeviceController routeDeviceController2 = routeDeviceController;
                        routeDeviceController2.isSpotifyCastingPriority = zBooleanValue;
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

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            if (continuation instanceof AnonymousClass1) {
                                anonymousClass1 = (AnonymousClass1) continuation;
                                int i = anonymousClass1.label;
                                if ((i & Integer.MIN_VALUE) != 0) {
                                    anonymousClass1.label = i - Integer.MIN_VALUE;
                                } else {
                                    anonymousClass1 = new AnonymousClass1(continuation);
                                }
                            }
                            Object obj2 = anonymousClass1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i2 = anonymousClass1.label;
                            if (i2 == 0) {
                                ResultKt.throwOnFailure(obj2);
                                List list = (List) obj;
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("\t", (AudioDevice) it.next(), "RouteDeviceController");
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(list, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i2 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                final RouteDeviceController routeDeviceController = RouteDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController.5.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                        Object objEmit = routeDeviceController.devicesFlow.emit((List) obj2, continuation);
                        return objEmit == CoroutineSingletons.COROUTINE_SUSPENDED ? objEmit : Unit.INSTANCE;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$transfer$1, reason: invalid class name and case insensitive filesystem */
    final class C09401 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        public C09401(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return RouteDeviceController.transfer$suspendImpl(RouteDeviceController.this, null, this);
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
                        final Flow flowM3482debounceHG0u8IE = FlowKt.m3482debounceHG0u8IE(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                        return new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1

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

                                /* JADX WARN: Multi-variable type inference failed */
                                /* JADX WARN: Removed duplicated region for block: B:215:0x0668  */
                                /* JADX WARN: Removed duplicated region for block: B:240:0x0721 A[RETURN] */
                                /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
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
                                */
                                public final Object emit(Object obj, Continuation continuation) {
                                    AnonymousClass1 anonymousClass1;
                                    List list;
                                    List<RouteListingPreference.Item> items;
                                    ?? SortedWith;
                                    RoutingSessionInfo availableSession;
                                    ImageVectorConverterPainter converter;
                                    int i;
                                    int i2;
                                    double volume;
                                    RoutingSessionInfo systemRoutingSession;
                                    List transferableRoutes;
                                    Object obj2;
                                    RouteDevice routeDeviceCreateRouteDevice$default;
                                    boolean z;
                                    boolean z2;
                                    if (continuation instanceof AnonymousClass1) {
                                        anonymousClass1 = (AnonymousClass1) continuation;
                                        int i3 = anonymousClass1.label;
                                        if ((i3 & Integer.MIN_VALUE) != 0) {
                                            anonymousClass1.label = i3 - Integer.MIN_VALUE;
                                        } else {
                                            anonymousClass1 = new AnonymousClass1(continuation);
                                        }
                                    }
                                    Object obj3 = anonymousClass1.result;
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i4 = anonymousClass1.label;
                                    if (i4 == 0) {
                                        ResultKt.throwOnFailure(obj3);
                                        List list2 = (List) obj;
                                        RouteDeviceController routeDeviceController = this.this$0;
                                        MediaRouter2Manager router2Manager$1 = routeDeviceController.getRouter2Manager$1();
                                        if (StringsKt__StringsKt.isBlank(routeDeviceController.getPackageName())) {
                                            router2Manager$1 = null;
                                        }
                                        RouteListingPreference routeListingPreference = router2Manager$1 != null ? router2Manager$1.getRouteListingPreference(routeDeviceController.getPackageName()) : null;
                                        if (routeListingPreference == null || (items = routeListingPreference.getItems()) == null) {
                                            list = list2;
                                            items = EmptyList.INSTANCE;
                                        } else {
                                            List<RouteListingPreference.Item> list3 = items;
                                            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                                            for (RouteListingPreference.Item item : list3) {
                                                String routeId = item.getRouteId();
                                                int subText = item.getSubText();
                                                int flags = item.getFlags();
                                                int selectionBehavior = item.getSelectionBehavior();
                                                CharSequence customSubtextMessage = item.getCustomSubtextMessage();
                                                List list4 = list2;
                                                StringBuilder sbM890m = ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m890m(subText, "routeId=", routeId, ", subText=", ", flags=");
                                                ViewPager$$ExternalSyntheticOutline0.m(sbM890m, flags, ", selectionBehavior=", selectionBehavior, ", customSubtextMessage=");
                                                sbM890m.append((Object) customSubtextMessage);
                                                arrayList.add(sbM890m.toString());
                                                list2 = list4;
                                            }
                                            list = list2;
                                            Log.e("RouteDeviceController", CollectionsKt___CollectionsKt.joinToString$default(arrayList, "\n", null, null, null, 62));
                                        }
                                        List<RouteListingPreference.Item> list5 = items;
                                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10));
                                        Iterator it = list5.iterator();
                                        while (it.hasNext()) {
                                            arrayList2.add(((RouteListingPreference.Item) it.next()).getRouteId());
                                        }
                                        List list6 = list;
                                        Iterator it2 = list6.iterator();
                                        while (it2.hasNext()) {
                                            Log.d("RouteDeviceController", "before - " + ((RoutingSessionInfo) it2.next()));
                                        }
                                        ArrayList arrayList3 = new ArrayList();
                                        for (Object obj4 : list6) {
                                            if (Intrinsics.areEqual(((RoutingSessionInfo) obj4).getClientPackageName(), routeDeviceController.getPackageName())) {
                                                arrayList3.add(obj4);
                                            }
                                        }
                                        if (arrayList3.isEmpty()) {
                                            arrayList3 = null;
                                        }
                                        if (arrayList3 == null || (availableSession = routeDeviceController.getAvailableSession(arrayList3)) == null) {
                                            List transferableRoutes2 = routeDeviceController.getTransferableRoutes();
                                            ArrayList arrayList4 = new ArrayList();
                                            for (Object obj5 : transferableRoutes2) {
                                                MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) obj5;
                                                if (arrayList2.isEmpty() || arrayList2.contains(mediaRoute2Info.getId())) {
                                                    arrayList4.add(obj5);
                                                }
                                            }
                                            ArrayList arrayList5 = new ArrayList();
                                            int size = arrayList4.size();
                                            int i5 = 0;
                                            while (i5 < size) {
                                                Object obj6 = arrayList4.get(i5);
                                                i5++;
                                                if (!((MediaRoute2Info) obj6).isSystemRoute()) {
                                                    arrayList5.add(obj6);
                                                }
                                            }
                                            ArrayList arrayList6 = new ArrayList();
                                            int size2 = arrayList5.size();
                                            int i6 = 0;
                                            while (i6 < size2) {
                                                Object obj7 = arrayList5.get(i6);
                                                i6++;
                                                if (RouteDeviceController.access$getFeatureAvailable(routeDeviceController, (MediaRoute2Info) obj7)) {
                                                    arrayList6.add(obj7);
                                                }
                                            }
                                            List<MediaRoute2Info> listSortedByIds = MediaRoute2InfoExtKt.sortedByIds(arrayList6, arrayList2);
                                            ArrayList arrayList7 = new ArrayList();
                                            for (MediaRoute2Info mediaRoute2Info2 : listSortedByIds) {
                                                arrayList7.add(RouteDeviceController.createRouteDevice$default(this.this$0, mediaRoute2Info2, false, false, RouteDeviceController.access$routeDevices_delegate$lambda$51$lambda$50$getPreferenceItems(mediaRoute2Info2.getId(), items), 62));
                                            }
                                            SortedWith = CollectionsKt___CollectionsKt.sortedWith(arrayList7, 
                                            /*  JADX ERROR: Method code generation error
                                                jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0711: INVOKE (r8v6 'SortedWith' ?? I:java.util.List) = 
                                                  (r2v2 'arrayList7' java.util.ArrayList)
                                                  (wrap:java.util.Comparator:0x070e: CONSTRUCTOR  A[MD:():void (m), WRAPPED] (LINE:1807) call: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$lambda$50$$inlined$sortedByDescending$1.<init>():void type: CONSTRUCTOR)
                                                 STATIC call: kotlin.collections.CollectionsKt___CollectionsKt.sortedWith(java.lang.Iterable, java.util.Comparator):java.util.List A[MD:(java.lang.Iterable, java.util.Comparator):java.util.List (m)] (LINE:1810) in method: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes2.dex
                                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                                	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                                                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                                                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$lambda$50$$inlined$sortedByDescending$1, state: NOT_LOADED
                                                	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                                                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                                                	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
                                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                                	... 27 more
                                                */
                                            /*
                                                Method dump skipped, instructions count: 1829
                                                To view this dump add '--comments-level debug' option
                                            */
                                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                        }
                                    }

                                    @Override // kotlinx.coroutines.flow.Flow
                                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                        Object objCollect = flowM3482debounceHG0u8IE.collect(new AnonymousClass2(flowCollector, routeDeviceController), continuation);
                                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
                                final Flow flowM3482debounceHG0u8IE = FlowKt.m3482debounceHG0u8IE(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                                return new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1

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

                                        /*  JADX ERROR: Method code generation error
                                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0711: INVOKE (r8v6 'SortedWith' ?? I:java.util.List) = 
                                              (r2v2 'arrayList7' java.util.ArrayList)
                                              (wrap:java.util.Comparator:0x070e: CONSTRUCTOR  A[MD:():void (m), WRAPPED] (LINE:1807) call: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$lambda$50$$inlined$sortedByDescending$1.<init>():void type: CONSTRUCTOR)
                                             STATIC call: kotlin.collections.CollectionsKt___CollectionsKt.sortedWith(java.lang.Iterable, java.util.Comparator):java.util.List A[MD:(java.lang.Iterable, java.util.Comparator):java.util.List (m)] (LINE:1810) in method: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes2.dex
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                            	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                            	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$lambda$50$$inlined$sortedByDescending$1, state: NOT_LOADED
                                            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                            	... 22 more
                                            */
                                        @Override // kotlinx.coroutines.flow.FlowCollector
                                        public final java.lang.Object emit(java.lang.Object r31, kotlin.coroutines.Continuation r32) {
                                            /*
                                                Method dump skipped, instructions count: 1829
                                                To view this dump add '--comments-level debug' option
                                            */
                                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.RouteDeviceController$routeDevices_delegate$lambda$51$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                                        }
                                    }

                                    @Override // kotlinx.coroutines.flow.Flow
                                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                        Object objCollect = flowM3482debounceHG0u8IE.collect(new AnonymousClass2(flowCollector, routeDeviceController), continuation);
                                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
                Object next;
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    if (Intrinsics.areEqual(((RouteListingPreference.Item) next).getRouteId(), str)) {
                        break;
                    }
                }
                return (RouteListingPreference.Item) next;
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

            /* JADX WARN: Removed duplicated region for block: B:32:0x009a  */
            /* JADX WARN: Removed duplicated region for block: B:38:0x00bf A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:48:0x00eb  */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public static Object transfer$suspendImpl(RouteDeviceController routeDeviceController, AudioDevice audioDevice, ContinuationImpl continuationImpl) {
                C09401 c09401;
                Pair pair;
                AudioDeviceInfo audioDeviceInfo;
                CachedBluetoothDevice cachedBluetoothDevice;
                BluetoothDevice bluetoothDevice;
                if (continuationImpl instanceof C09401) {
                    c09401 = (C09401) continuationImpl;
                    int i = c09401.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        c09401.label = i - Integer.MIN_VALUE;
                    } else {
                        c09401 = routeDeviceController.new C09401(continuationImpl);
                    }
                }
                Object obj = c09401.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = c09401.label;
                Pair pair2 = null;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Log.d("RouteDeviceController", "transfer() - " + audioDevice);
                    if (audioDevice instanceof BuiltInDevice) {
                        RouteDevice routeDevice = ((BuiltInDevice) audioDevice).routeDevice;
                        pair2 = new Pair(routeDevice != null ? routeDevice.getMediaRoute2Info() : null, new RouteDeviceController$$ExternalSyntheticLambda3(0, audioDevice, routeDeviceController));
                    } else if (audioDevice instanceof BluetoothRoute) {
                        audioDeviceInfo = ((BluetoothRoute) audioDevice).getAudioDeviceInfo();
                        if (audioDeviceInfo == null) {
                            if (audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) {
                                cachedBluetoothDevice = ((com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice).cachedBluetoothDevice;
                                if (cachedBluetoothDevice == null) {
                                    cachedBluetoothDevice = null;
                                }
                                if (cachedBluetoothDevice != null && (bluetoothDevice = cachedBluetoothDevice.mDevice) != null) {
                                    ((BluetoothAdapter) routeDeviceController.bluetoothAdapter$delegate.getValue()).setActiveDevice(bluetoothDevice, 0);
                                    c09401.L$0 = routeDeviceController;
                                    c09401.L$1 = audioDevice;
                                    c09401.L$2 = audioDeviceInfo;
                                    c09401.L$3 = bluetoothDevice;
                                    c09401.label = 1;
                                    if (DelayKt.delay(300L, c09401) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                }
                            } else {
                                if (audioDevice instanceof MusicShareDevice) {
                                    cachedBluetoothDevice = ((MusicShareDevice) audioDevice).cachedBluetoothDevice;
                                }
                                if (cachedBluetoothDevice != null) {
                                    ((BluetoothAdapter) routeDeviceController.bluetoothAdapter$delegate.getValue()).setActiveDevice(bluetoothDevice, 0);
                                    c09401.L$0 = routeDeviceController;
                                    c09401.L$1 = audioDevice;
                                    c09401.L$2 = audioDeviceInfo;
                                    c09401.L$3 = bluetoothDevice;
                                    c09401.label = 1;
                                    if (DelayKt.delay(300L, c09401) == coroutineSingletons) {
                                    }
                                }
                            }
                        }
                    } else if (audioDevice instanceof RouteDevice) {
                        pair = new Pair(((RouteDevice) audioDevice).getMediaRoute2Info(), null);
                        pair2 = pair;
                    }
                    if (pair2 != null) {
                        MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) pair2.component1();
                        Function0 function0 = (Function0) pair2.component2();
                        if (mediaRoute2Info != null) {
                            routeDeviceController.getRouter2Manager$1().transfer(routeDeviceController.getPackageName(), mediaRoute2Info, Process.myUserHandle());
                        }
                        if (function0 != null) {
                            function0.invoke();
                        }
                    }
                    return Unit.INSTANCE;
                }
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                AudioDeviceInfo audioDeviceInfo2 = (AudioDeviceInfo) c09401.L$2;
                audioDevice = (AudioDevice) c09401.L$1;
                RouteDeviceController routeDeviceController2 = (RouteDeviceController) c09401.L$0;
                ResultKt.throwOnFailure(obj);
                audioDeviceInfo = audioDeviceInfo2;
                routeDeviceController = routeDeviceController2;
                RouteDevice routeDevice2 = ((BluetoothRoute) audioDevice).getRouteDevice();
                pair = new Pair(routeDevice2 != null ? routeDevice2.getMediaRoute2Info() : null, new RouteDeviceController$$ExternalSyntheticLambda3(1, audioDeviceInfo, routeDeviceController));
                pair2 = pair;
                if (pair2 != null) {
                }
                return Unit.INSTANCE;
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
                        Object objPrevious = listIterator.previous();
                        if (Intrinsics.areEqual(((RoutingSessionInfo) objPrevious).getClientPackageName(), getPackageName())) {
                            obj = objPrevious;
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
