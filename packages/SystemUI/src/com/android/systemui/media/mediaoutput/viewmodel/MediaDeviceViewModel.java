package com.android.systemui.media.mediaoutput.viewmodel;

import android.util.Log;
import androidx.datastore.core.DataStore;
import androidx.lifecycle.CoroutineLiveData;
import androidx.lifecycle.FlowLiveDataConversions;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$10;
import com.android.systemui.media.mediaoutput.entity.MediaDeviceInfo;
import com.android.systemui.media.mediaoutput.entity.MediaInfo;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.shade.ShadeController;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import java.util.Iterator;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

public final class MediaDeviceViewModel extends ViewModel implements MediaInteraction {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _mediaDevicesFlow;
    public final ActivityStarter activityStarter;
    public final DataStore dataStore;
    public final String deviceId;
    public final Lazy empty$delegate;
    public boolean isSupportForUnsupportedTV;
    public final CoroutineLiveData mediaDevices;
    public final Flow mediaInfo;
    public final SmartThingsMediaSdkManager mediaSdkManager;
    public final ShadeController shadeController;
    public final StateFlowImpl updateDeviceInfo;

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
                DataStoreDebugLabsExt dataStoreDebugLabsExt = DataStoreDebugLabsExt.INSTANCE;
                DataStore dataStore = MediaDeviceViewModel.this.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$10 dataStoreDebugLabsExt$special$$inlined$map$10 = new DataStoreDebugLabsExt$special$$inlined$map$10(dataStore.getData());
                final MediaDeviceViewModel mediaDeviceViewModel = MediaDeviceViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        MediaDeviceViewModel mediaDeviceViewModel2 = MediaDeviceViewModel.this;
                        mediaDeviceViewModel2.isSupportForUnsupportedTV = booleanValue;
                        Object access$updateDevices = MediaDeviceViewModel.access$updateDevices(mediaDeviceViewModel2, continuation);
                        return access$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? access$updateDevices : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreDebugLabsExt$special$$inlined$map$10.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = MediaDeviceViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MediaDeviceViewModel.this.mediaSdkManager.supportServiceClientStateManager.mediaSdkSupportServiceClient._serviceConnectedStateFlow);
                final MediaDeviceViewModel mediaDeviceViewModel = MediaDeviceViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        MediaDeviceViewModel mediaDeviceViewModel2 = MediaDeviceViewModel.this;
                        SmartThingsMediaSdkManager smartThingsMediaSdkManager = mediaDeviceViewModel2.mediaSdkManager;
                        if (!booleanValue) {
                            smartThingsMediaSdkManager = null;
                        }
                        if (smartThingsMediaSdkManager != null) {
                            BuildersKt.launch$default(coroutineScope, null, null, new MediaDeviceViewModel$2$1$2$1(smartThingsMediaSdkManager, mediaDeviceViewModel2, null), 3);
                        } else {
                            mediaDeviceViewModel2._mediaDevicesFlow.setValue(EmptyList.INSTANCE);
                            Unit unit = Unit.INSTANCE;
                            if (unit == CoroutineSingletons.COROUTINE_SUSPENDED) {
                                return unit;
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (asStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MediaDeviceViewModel(SmartThingsMediaSdkManager smartThingsMediaSdkManager, ActivityStarter activityStarter, ShadeController shadeController, DataStore dataStore, SavedStateHandle savedStateHandle) {
        this.mediaSdkManager = smartThingsMediaSdkManager;
        this.activityStarter = activityStarter;
        this.shadeController = shadeController;
        this.dataStore = dataStore;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(0L);
        this.updateDeviceInfo = MutableStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._mediaDevicesFlow = MutableStateFlow2;
        CoroutineLiveData asLiveData$default = FlowLiveDataConversions.asLiveData$default(MutableStateFlow2);
        this.mediaDevices = asLiveData$default;
        this.empty$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$empty$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                MediaDeviceInfo.Companion.getClass();
                return (MediaDeviceInfo) MediaDeviceInfo.empty$delegate.getValue();
            }
        });
        final MediatorLiveData mediatorLiveData = new MediatorLiveData();
        mediatorLiveData.addSource(asLiveData$default, new Observer() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$mediaInfo$1$1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MediatorLiveData.this.setValue(MediaDeviceViewModel.access$mediaInfo$lambda$1$selectedInfo((List) obj, this));
            }
        });
        mediatorLiveData.addSource(FlowLiveDataConversions.asLiveData$default(MutableStateFlow), new Observer() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$mediaInfo$1$2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MediaDeviceViewModel mediaDeviceViewModel = this;
                List list = (List) mediaDeviceViewModel.mediaDevices.getValue();
                MediatorLiveData.this.setValue(list != null ? MediaDeviceViewModel.access$mediaInfo$lambda$1$selectedInfo(list, mediaDeviceViewModel) : null);
            }
        });
        this.mediaInfo = FlowLiveDataConversions.asFlow(mediatorLiveData);
        Log.d("MediaDeviceViewModel", "init()");
        String str = savedStateHandle != null ? (String) savedStateHandle.get("deviceId") : null;
        if (!Intrinsics.areEqual(this.deviceId, str)) {
            MWBixbyController$$ExternalSyntheticOutline0.m("deviceId changed : ", this.deviceId, " -> ", str, "MediaDeviceViewModel");
            this.deviceId = str;
            smartThingsMediaSdkManager.mediaSdkOperationManager.mediaOutputDeviceOperationImpl.startCloudSync();
            BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new MediaDeviceViewModel$deviceId$1(this, null), 3);
        }
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass2(null), 3);
    }

    public static final MediaDeviceInfo access$mediaInfo$lambda$1$selectedInfo(List list, MediaDeviceViewModel mediaDeviceViewModel) {
        Object obj;
        if (list != null) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (Intrinsics.areEqual(((MediaDeviceInfo) obj).id, mediaDeviceViewModel.deviceId)) {
                    break;
                }
            }
            MediaDeviceInfo mediaDeviceInfo = (MediaDeviceInfo) obj;
            if (mediaDeviceInfo != null) {
                return mediaDeviceInfo;
            }
        }
        return (MediaDeviceInfo) mediaDeviceViewModel.empty$delegate.getValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updateDevices(com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel r21, kotlin.coroutines.Continuation r22) {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel.access$updateDevices(com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction
    public final void execute(MediaInfo mediaInfo, long j, long j2) {
        Log.d("MediaDeviceViewModel", "execute() - " + j);
        final String id = mediaInfo.getId();
        SmartThingsMediaSdkManager smartThingsMediaSdkManager = this.mediaSdkManager;
        if (j == 2) {
            if (id == null) {
                return;
            }
            smartThingsMediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.setMute(id);
            return;
        }
        if (j == 4) {
            if (id == null) {
                return;
            }
            smartThingsMediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.updateVolume(-1, id);
        } else if (j == 8) {
            if (id == null) {
                return;
            }
            smartThingsMediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.updateVolume(1, id);
        } else if (j == 16) {
            if (id == null) {
                return;
            }
            smartThingsMediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.togglePlayback(id);
        } else if (j == 32) {
            this.activityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$execute$1
                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    MediaDeviceViewModel mediaDeviceViewModel = MediaDeviceViewModel.this;
                    mediaDeviceViewModel.shadeController.animateCollapseShade(0);
                    mediaDeviceViewModel.mediaSdkManager.mediaSdkOperationManager.deviceControlOperationImpl.launchRemoteControlPlugIn(id);
                    return true;
                }
            }, null, true);
        }
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction
    public final MediaInfo getEmpty() {
        return (MediaDeviceInfo) this.empty$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.MediaInteraction
    public final Flow getMediaInfo() {
        return this.mediaInfo;
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("MediaDeviceViewModel", "onCleared()");
        this.mediaSdkManager.mediaSdkOperationManager.mediaOutputDeviceOperationImpl.stopCloudSync();
    }
}
