package com.android.systemui.media.mediaoutput.viewmodel;

import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSession;
import com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController;
import com.android.systemui.media.mediaoutput.dagger.DeviceSessionControllerFactory;
import com.android.systemui.media.mediaoutput.entity.EntityString;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
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
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

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
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._sessionControllersFlow = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.sessionControllersFlow = readonlyStateFlowAsStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(0L);
        this.updateCurrent = stateFlowImplMutableStateFlow2;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImplMutableStateFlow2, readonlyStateFlowAsStateFlow, new MediaDeviceViewModel$currentSessionController$1(null));
        this.currentSessionController = new Flow() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object next;
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
                        Iterator it = ((List) obj).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                            if (Intrinsics.areEqual(((DeviceSessionController) ((DeviceSession) next)).getId(), this.this$0.deviceId)) {
                                break;
                            }
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(next, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
                ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(FlowKt.asStateFlow(MediaDeviceViewModel.this.mediaSdkManager.supportServiceClientStateManager.mediaSdkSupportServiceClient._serviceConnectedStateFlow), new MediaDeviceViewModel$1$invokeSuspend$$inlined$flatMapLatest$1(null, MediaDeviceViewModel.this));
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(MediaDeviceViewModel.this);
                this.label = 1;
                if (channelFlowTransformLatestTransformLatest.collect(anonymousClass2, this) == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$1$2, reason: invalid class name */
        public final class AnonymousClass2 implements FlowCollector {
            public final /* synthetic */ MediaDeviceViewModel this$0;

            public AnonymousClass2(MediaDeviceViewModel mediaDeviceViewModel) {
                this.this$0 = mediaDeviceViewModel;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(boolean z, Continuation continuation) {
                MediaDeviceViewModel$1$2$emit$1 mediaDeviceViewModel$1$2$emit$1;
                List devices;
                if (continuation instanceof MediaDeviceViewModel$1$2$emit$1) {
                    mediaDeviceViewModel$1$2$emit$1 = (MediaDeviceViewModel$1$2$emit$1) continuation;
                    int i = mediaDeviceViewModel$1$2$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        mediaDeviceViewModel$1$2$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        mediaDeviceViewModel$1$2$emit$1 = new MediaDeviceViewModel$1$2$emit$1(this, continuation);
                    }
                }
                Object obj = mediaDeviceViewModel$1$2$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = mediaDeviceViewModel$1$2$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(obj);
                    Log.d("MediaDeviceViewModel", "serviceConnected = true");
                    MediaDeviceViewModel mediaDeviceViewModel = this.this$0;
                    List list = (List) mediaDeviceViewModel._sessionControllersFlow.getValue();
                    int iMapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                    if (iMapCapacity < 16) {
                        iMapCapacity = 16;
                    }
                    LinkedHashMap linkedHashMap = new LinkedHashMap(iMapCapacity);
                    for (Object obj2 : list) {
                        linkedHashMap.put(((DeviceSessionController) ((DeviceSession) obj2)).getId(), obj2);
                    }
                    SmartThingsMediaSdkManager smartThingsMediaSdkManager = mediaDeviceViewModel.mediaSdkManager;
                    EmptyList emptyList = null;
                    SmartThingsMediaSdkManager smartThingsMediaSdkManager2 = ((Boolean) FlowKt.asStateFlow(smartThingsMediaSdkManager.supportServiceClientStateManager.mediaSdkSupportServiceClient._serviceConnectedStateFlow).$$delegate_0.getValue()).booleanValue() ? smartThingsMediaSdkManager : null;
                    if (smartThingsMediaSdkManager2 != null && (devices = smartThingsMediaSdkManager2.mediaSdkOperationManager.deviceStatusOperationImpl.getDevices()) != null) {
                        List list2 = devices;
                        Iterator it = list2.iterator();
                        while (it.hasNext()) {
                            Log.d("MediaDeviceViewModel", "\t" + ((DeviceDomain) it.next()));
                        }
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : list2) {
                            DeviceDomain deviceDomain = (DeviceDomain) obj3;
                            if (!z) {
                                if (smartThingsMediaSdkManager.mediaSdkOperationManager.deviceStatusOperationImpl.isSupported(deviceDomain.deviceId)) {
                                }
                            }
                            arrayList.add(obj3);
                        }
                        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                        int size = arrayList.size();
                        int i3 = 0;
                        int i4 = 0;
                        while (i4 < size) {
                            Object obj4 = arrayList.get(i4);
                            i4++;
                            DeviceDomain deviceDomain2 = (DeviceDomain) obj4;
                            EntityString entityStringCreate = (DeviceSession) linkedHashMap.get(deviceDomain2.deviceId);
                            if (entityStringCreate == null) {
                                entityStringCreate = mediaDeviceViewModel.sessionControllerFactory.create(deviceDomain2);
                            }
                            arrayList2.add(entityStringCreate);
                        }
                        int size2 = arrayList2.size();
                        while (i3 < size2) {
                            Object obj5 = arrayList2.get(i3);
                            i3++;
                            Log.d("MediaDeviceViewModel", "\t" + ((DeviceSession) obj5));
                        }
                        emptyList = arrayList2;
                    }
                    if (emptyList == null) {
                        emptyList = EmptyList.INSTANCE;
                    }
                    String str = mediaDeviceViewModel.deviceId;
                    if ((str != null && !StringsKt__StringsKt.isBlank(str)) || emptyList.size() > 1) {
                        smartThingsMediaSdkManager.mediaSdkOperationManager.mediaOutputDeviceOperationImpl.startCloudSync();
                    }
                    Iterator it2 = CollectionsKt___CollectionsKt.minus((Iterable) list, (Iterable) CollectionsKt___CollectionsKt.toSet(emptyList)).iterator();
                    while (it2.hasNext()) {
                        ((DeviceSessionController) ((DeviceSession) it2.next())).close();
                    }
                    mediaDeviceViewModel$1$2$emit$1.L$0 = emptyList;
                    mediaDeviceViewModel$1$2$emit$1.label = 1;
                    mediaDeviceViewModel._sessionControllersFlow.setValue(emptyList);
                    if (Unit.INSTANCE == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }

            @Override // kotlinx.coroutines.flow.FlowCollector
            public final /* bridge */ /* synthetic */ Object emit(Object obj, Continuation continuation) {
                return emit(((Boolean) obj).booleanValue(), continuation);
            }
        }
    }
}
