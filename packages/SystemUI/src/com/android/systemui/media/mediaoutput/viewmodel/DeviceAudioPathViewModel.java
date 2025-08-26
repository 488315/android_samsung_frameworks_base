package com.android.systemui.media.mediaoutput.viewmodel;

import android.util.Log;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.entity.TvConnectedDevice;
import com.android.systemui.media.mediaoutput.ext.ResourceString;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.device.GroupSpeakerKt;
import com.android.systemui.media.mediaoutput.icons.device.HdmiKt;
import com.android.systemui.media.mediaoutput.icons.device.LevelBoxKt;
import com.android.systemui.media.mediaoutput.icons.device.LineKt;
import com.android.systemui.media.mediaoutput.icons.device.SoundAccessoryKt;
import com.android.systemui.media.mediaoutput.icons.device.TvKt;
import com.android.systemui.media.mediaoutput.icons.device.UsbKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputDeviceDomain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
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

/* loaded from: classes2.dex */
public final class DeviceAudioPathViewModel extends ViewModel implements AudioPathInteraction {
    public static final Companion Companion = new Companion(null);
    public final StateFlowImpl _audioDevices;
    public final ReadonlyStateFlow audioDevices;
    public final String deviceId;
    public final SmartThingsMediaSdkManager mediaSdkManager;
    public int numOfAudioOutputChanges;

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
                ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(DeviceAudioPathViewModel.this.mediaSdkManager.supportServiceClientStateManager.mediaSdkSupportServiceClient._serviceConnectedStateFlow);
                final DeviceAudioPathViewModel deviceAudioPathViewModel = DeviceAudioPathViewModel.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        DeviceAudioPathViewModel deviceAudioPathViewModel2 = deviceAudioPathViewModel;
                        SmartThingsMediaSdkManager smartThingsMediaSdkManager = deviceAudioPathViewModel2.mediaSdkManager;
                        if (!zBooleanValue) {
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
                if (readonlyStateFlowAsStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public DeviceAudioPathViewModel(SmartThingsMediaSdkManager smartThingsMediaSdkManager, SavedStateHandle savedStateHandle) {
        this.mediaSdkManager = smartThingsMediaSdkManager;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(EmptyList.INSTANCE);
        this._audioDevices = stateFlowImplMutableStateFlow;
        this.audioDevices = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
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
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$updateDevices(DeviceAudioPathViewModel deviceAudioPathViewModel, Continuation continuation) {
        DeviceAudioPathViewModel$updateDevices$1 deviceAudioPathViewModel$updateDevices$1;
        ImageVector tv;
        deviceAudioPathViewModel.getClass();
        if (continuation instanceof DeviceAudioPathViewModel$updateDevices$1) {
            deviceAudioPathViewModel$updateDevices$1 = (DeviceAudioPathViewModel$updateDevices$1) continuation;
            int i = deviceAudioPathViewModel$updateDevices$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                deviceAudioPathViewModel$updateDevices$1.label = i - Integer.MIN_VALUE;
            } else {
                deviceAudioPathViewModel$updateDevices$1 = new DeviceAudioPathViewModel$updateDevices$1(deviceAudioPathViewModel, continuation);
            }
        }
        Object obj = deviceAudioPathViewModel$updateDevices$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = deviceAudioPathViewModel$updateDevices$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Log.d("DeviceAudioPathViewModel", "updateDevices()");
            String str = deviceAudioPathViewModel.deviceId;
            if (str == null) {
                return Unit.INSTANCE;
            }
            SmartThingsMediaSdkManager smartThingsMediaSdkManager = deviceAudioPathViewModel.mediaSdkManager;
            MediaOutputDeviceDomain currentMediaOutput = smartThingsMediaSdkManager.mediaSdkOperationManager.mediaOutputSelectedOperationImpl.getCurrentMediaOutput(str);
            List mediaOutputDevice = smartThingsMediaSdkManager.mediaSdkOperationManager.mediaOutputSelectedOperationImpl.getMediaOutputDevice(str);
            Iterator it = mediaOutputDevice.iterator();
            while (it.hasNext()) {
                Log.d("DeviceAudioPathViewModel", "\t" + ((MediaOutputDeviceDomain) it.next()));
            }
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(mediaOutputDevice, 10));
            Iterator it2 = mediaOutputDevice.iterator();
            while (true) {
                if (it2.hasNext()) {
                    MediaOutputDeviceDomain mediaOutputDeviceDomain = (MediaOutputDeviceDomain) it2.next();
                    TvConnectedDevice.Companion companion = TvConnectedDevice.Companion;
                    boolean zAreEqual = Intrinsics.areEqual(mediaOutputDeviceDomain.deviceId, currentMediaOutput != null ? currentMediaOutput.deviceId : null);
                    companion.getClass();
                    ImageVectorConverterPainter.Companion companion2 = ImageVectorConverterPainter.Companion;
                    String str2 = mediaOutputDeviceDomain.deviceType;
                    if (str2 != null) {
                        switch (str2.hashCode()) {
                            case -2032180703:
                                if (!str2.equals("DEFAULT")) {
                                    Icons.Device device = Icons.Device.INSTANCE;
                                    tv = (ImageVector) LevelBoxKt.LevelBox$delegate.getValue();
                                    break;
                                } else {
                                    Icons.Device device2 = Icons.Device.INSTANCE;
                                    tv = TvKt.getTv();
                                    break;
                                }
                            case -531504168:
                                if (str2.equals("OPTICAL")) {
                                    Icons.Device device3 = Icons.Device.INSTANCE;
                                    tv = (ImageVector) LineKt.Line$delegate.getValue();
                                    break;
                                }
                                break;
                            case 2130:
                                if (str2.equals("BT")) {
                                    Icons.Device device4 = Icons.Device.INSTANCE;
                                    tv = (ImageVector) SoundAccessoryKt.SoundAccessory$delegate.getValue();
                                    break;
                                }
                                break;
                            case 2690:
                                if (!str2.equals("TV")) {
                                }
                                break;
                            case 84324:
                                if (str2.equals(PeripheralConstants.ConnectivityType.USB)) {
                                    Icons.Device device5 = Icons.Device.INSTANCE;
                                    tv = (ImageVector) UsbKt.Usb$delegate.getValue();
                                    break;
                                }
                                break;
                            case 2212760:
                                if (str2.equals("HDMI")) {
                                    Icons.Device device6 = Icons.Device.INSTANCE;
                                    tv = (ImageVector) HdmiKt.Hdmi$delegate.getValue();
                                    break;
                                }
                                break;
                            case 2495670:
                                if (str2.equals("QSYM")) {
                                    Icons.Device device7 = Icons.Device.INSTANCE;
                                    tv = (ImageVector) GroupSpeakerKt.GroupSpeaker$delegate.getValue();
                                    break;
                                }
                                break;
                        }
                    }
                    companion2.getClass();
                    arrayList.add(new TvConnectedDevice(mediaOutputDeviceDomain.deviceId, mediaOutputDeviceDomain.deviceName, null, ImageVectorConverterPainter.Companion.toConverter(tv), null, zAreEqual ? State.SELECTED : State.CONNECTED, 0, 0, IKnoxCustomManager.Stub.TRANSACTION_getWifiState, null));
                } else {
                    boolean zIsEmpty = arrayList.isEmpty();
                    List listSingletonList = arrayList;
                    if (zIsEmpty) {
                        listSingletonList = null;
                    }
                    if (listSingletonList == null) {
                        TvConnectedDevice.Companion.getClass();
                        ResourceString resourceString = new ResourceString(R.string.tv_speaker, null, 2, null);
                        ImageVectorConverterPainter.Companion companion3 = ImageVectorConverterPainter.Companion;
                        Icons.Device device8 = Icons.Device.INSTANCE;
                        ImageVector tv2 = TvKt.getTv();
                        companion3.getClass();
                        listSingletonList = Collections.singletonList(new TvConnectedDevice(str, resourceString, null, ImageVectorConverterPainter.Companion.toConverter(tv2), null, State.SELECTED, 0, 0, IKnoxCustomManager.Stub.TRANSACTION_getWifiState, null));
                    }
                    deviceAudioPathViewModel$updateDevices$1.L$0 = smartThingsMediaSdkManager;
                    deviceAudioPathViewModel$updateDevices$1.label = 1;
                    deviceAudioPathViewModel._audioDevices.setValue(listSingletonList);
                    if (Unit.INSTANCE == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
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
        String string = tvConnectedDevice.name.toString();
        Long lValueOf = Long.valueOf(this.numOfAudioOutputChanges);
        moSaLogging.getClass();
        MoSaLogging.send(changeAudioOutputOnTv, string, lValueOf);
        this.numOfAudioOutputChanges++;
    }

    @Override // com.android.systemui.media.mediaoutput.viewmodel.AudioPathInteraction
    public final void adjustVolume(AudioDevice audioDevice, int i) {
    }
}
