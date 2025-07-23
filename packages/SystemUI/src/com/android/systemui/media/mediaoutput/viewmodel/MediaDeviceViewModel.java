package com.android.systemui.media.mediaoutput.viewmodel;

import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSession;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController;
import com.android.systemui.media.mediaoutput.dagger.DeviceSessionControllerFactory;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaDeviceViewModel extends ViewModel implements MediaInteraction {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _sessionControllersFlow;
    public final MediaDeviceViewModel$special$$inlined$map$1 currentSessionController;
    public final DataStore dataStore;
    public final String deviceId;
    public final SmartThingsMediaSdkManager mediaSdkManager;
    public final DeviceSessionControllerFactory sessionControllerFactory;
    public final ReadonlyStateFlow sessionControllersFlow;
    public final StateFlowImpl updateCurrent;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r3v6, types: [com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1] */
    public MediaDeviceViewModel(SmartThingsMediaSdkManager smartThingsMediaSdkManager, DataStore dataStore, DeviceSessionControllerFactory deviceSessionControllerFactory, SavedStateHandle savedStateHandle) {
        this.mediaSdkManager = smartThingsMediaSdkManager;
        this.dataStore = dataStore;
        this.sessionControllerFactory = deviceSessionControllerFactory;
        this.deviceId = savedStateHandle != null ? (String) savedStateHandle.get("deviceId") : null;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._sessionControllersFlow = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.sessionControllersFlow = asStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(0L);
        this.updateCurrent = MutableStateFlow2;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(MutableStateFlow2, asStateFlow, new MediaDeviceViewModel$currentSessionController$1(null));
        this.currentSessionController = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaDeviceViewModel this$0;

                /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaDeviceViewModel mediaDeviceViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaDeviceViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L64
                    L27:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r7)
                        java.util.List r6 = (java.util.List) r6
                        java.lang.Iterable r6 = (java.lang.Iterable) r6
                        java.util.Iterator r6 = r6.iterator()
                    L3a:
                        boolean r7 = r6.hasNext()
                        if (r7 == 0) goto L58
                        java.lang.Object r7 = r6.next()
                        r2 = r7
                        com.android.systemui.media.mediaoutput.controller.media.DeviceSession r2 = (com.android.systemui.media.mediaoutput.controller.media.DeviceSession) r2
                        com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController r2 = (com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController) r2
                        java.lang.String r2 = r2.getId()
                        com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel r4 = r5.this$0
                        java.lang.String r4 = r4.deviceId
                        boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r4)
                        if (r2 == 0) goto L3a
                        goto L59
                    L58:
                        r7 = 0
                    L59:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                        java.lang.Object r5 = r5.emit(r7, r0)
                        if (r5 != r1) goto L64
                        return r1
                    L64:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Log.d("MediaDeviceViewModel", "init()");
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction
    public final Flow getCurrentSessionController() {
        return this.currentSessionController;
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("MediaDeviceViewModel", "onCleared()");
        this.mediaSdkManager.mediaSdkOperationManager.mediaOutputDeviceOperationImpl.stopCloudSync();
        Iterator it = ((Iterable) this._sessionControllersFlow.getValue()).iterator();
        while (it.hasNext()) {
            ((DeviceSessionController) ((DeviceSession) it.next())).close();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaDeviceViewModel.this.new AnonymousClass1(continuation);
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
                ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(FlowKt.asStateFlow(MediaDeviceViewModel.this.mediaSdkManager.supportServiceClientStateManager.mediaSdkSupportServiceClient._serviceConnectedStateFlow), new MediaDeviceViewModel$1$invokeSuspend$$inlined$flatMapLatest$1(null, MediaDeviceViewModel.this));
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(MediaDeviceViewModel.this);
                this.label = 1;
                if (transformLatest.collect(anonymousClass2, this) == coroutineSingletons) {
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$1$2, reason: invalid class name */
        public final class AnonymousClass2 implements FlowCollector {
            public final /* synthetic */ MediaDeviceViewModel this$0;

            public AnonymousClass2(MediaDeviceViewModel mediaDeviceViewModel) {
                this.this$0 = mediaDeviceViewModel;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final java.lang.Object emit(boolean r14, kotlin.coroutines.Continuation r15) {
                /*
                    Method dump skipped, instructions count: 401
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel.AnonymousClass1.AnonymousClass2.emit(boolean, kotlin.coroutines.Continuation):java.lang.Object");
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit(((Boolean) obj).booleanValue(), continuation);
            }
        }
    }
}
