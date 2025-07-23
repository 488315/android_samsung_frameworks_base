package com.android.systemui.media.mediaoutput.viewmodel;

import android.util.Log;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.TvConnectedDevice;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceAudioPathViewModel extends ViewModel implements AudioPathInteraction {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _audioDevices;
    public final ReadonlyStateFlow audioDevices;
    public final String deviceId;
    public final SmartThingsMediaSdkManager mediaSdkManager;
    public int numOfAudioOutputChanges;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = DeviceAudioPathViewModel.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
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
                final CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(DeviceAudioPathViewModel.this.mediaSdkManager.supportServiceClientStateManager.mediaSdkSupportServiceClient._serviceConnectedStateFlow);
                final DeviceAudioPathViewModel deviceAudioPathViewModel = DeviceAudioPathViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean booleanValue = ((Boolean) obj2).booleanValue();
                        DeviceAudioPathViewModel deviceAudioPathViewModel2 = DeviceAudioPathViewModel.this;
                        SmartThingsMediaSdkManager smartThingsMediaSdkManager = deviceAudioPathViewModel2.mediaSdkManager;
                        if (!booleanValue) {
                            smartThingsMediaSdkManager = null;
                        }
                        if (smartThingsMediaSdkManager != null) {
                            BuildersKt.launch$default(coroutineScope, null, null, new DeviceAudioPathViewModel$1$1$2$1(smartThingsMediaSdkManager, deviceAudioPathViewModel2, null), 3);
                        } else {
                            deviceAudioPathViewModel2._audioDevices.setValue(EmptyList.INSTANCE);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public DeviceAudioPathViewModel(SmartThingsMediaSdkManager smartThingsMediaSdkManager, SavedStateHandle savedStateHandle) {
        this.mediaSdkManager = smartThingsMediaSdkManager;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._audioDevices = MutableStateFlow;
        this.audioDevices = FlowKt.asStateFlow(MutableStateFlow);
        this.numOfAudioOutputChanges = 1;
        Log.d("DeviceAudioPathViewModel", "init() - " + smartThingsMediaSdkManager);
        String str = savedStateHandle != null ? (String) savedStateHandle.get("deviceId") : null;
        if (!Intrinsics.areEqual(this.deviceId, str)) {
            MediaSessions$H$$ExternalSyntheticOutline0.m("deviceId changed : ", this.deviceId, " -> ", str, "DeviceAudioPathViewModel");
            this.deviceId = str;
            BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new DeviceAudioPathViewModel$deviceId$1(this, null), 3);
        }
        BuildersKt.launch$default(androidx.lifecycle.ViewModelKt.getViewModelScope(this), null, null, new AnonymousClass1(null), 3);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0103, code lost:
    
        if (r13.equals("TV") == false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0136, code lost:
    
        r13 = com.android.systemui.media.mediaoutput.icons.Icons.Device.INSTANCE;
        r13 = com.android.systemui.media.mediaoutput.icons.device.TvKt.getTv();
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0134, code lost:
    
        if (r13.equals("DEFAULT") != false) goto L58;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0155  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$updateDevices(com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel r23, kotlin.coroutines.Continuation r24) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel.access$updateDevices(com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final ReadonlyStateFlow getAudioDevices() {
        return this.audioDevices;
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Log.d("DeviceAudioPathViewModel", "onCleared()");
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void transfer(AudioDevice audioDevice) {
        String str;
        if (!(audioDevice instanceof TvConnectedDevice) || (str = this.deviceId) == null) {
            return;
        }
        TvConnectedDevice tvConnectedDevice = (TvConnectedDevice) audioDevice;
        this.mediaSdkManager.mediaSdkOperationManager.mediaOutputSelectedOperationImpl.selectMediaOutput(str, tvConnectedDevice.id);
        MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
        SaEvent.ChangeAudioOutputOnTv changeAudioOutputOnTv = SaEvent.ChangeAudioOutputOnTv.INSTANCE;
        String obj = tvConnectedDevice.name.toString();
        Long valueOf = Long.valueOf(this.numOfAudioOutputChanges);
        moSaLogging.getClass();
        MoSaLogging.send(changeAudioOutputOnTv, obj, valueOf);
        this.numOfAudioOutputChanges++;
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void adjustVolume(AudioDevice audioDevice, int i) {
    }
}
