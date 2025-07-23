package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.controller.device.ControllerType;
import com.android.systemui.media.mediaoutput.controller.device.DeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.AudioDeviceExt;
import com.android.systemui.media.mediaoutput.entity.BluetoothDevice;
import com.android.systemui.media.mediaoutput.entity.BuiltInDevice;
import com.android.systemui.media.mediaoutput.entity.ChromeCastDevice;
import com.android.systemui.media.mediaoutput.entity.DisconnectedDevice;
import com.android.systemui.media.mediaoutput.entity.GroupDevice;
import com.android.systemui.media.mediaoutput.ext.CachedBluetoothDeviceExtKt;
import java.util.Collection;
import java.util.LinkedHashMap;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SessionAudioPathViewModel$transfer$4 extends SuspendLambda implements Function1 {
    final /* synthetic */ AudioDevice $device;
    int label;
    final /* synthetic */ SessionAudioPathViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SessionAudioPathViewModel$transfer$4(AudioDevice audioDevice, SessionAudioPathViewModel sessionAudioPathViewModel, Continuation continuation) {
        super(1, continuation);
        this.$device = audioDevice;
        this.this$0 = sessionAudioPathViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new SessionAudioPathViewModel$transfer$4(this.$device, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((SessionAudioPathViewModel$transfer$4) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SaEvent saEvent;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
            SaEvent.ChangeAudioOutput changeAudioOutput = SaEvent.ChangeAudioOutput.INSTANCE;
            String name = this.$device.getFinalControllerType().name();
            Long l = new Long(this.this$0.numOfAudioOutputChanges);
            moSaLogging.getClass();
            MoSaLogging.send(changeAudioOutput, name, l);
            SessionAudioPathViewModel sessionAudioPathViewModel = this.this$0;
            sessionAudioPathViewModel.numOfAudioOutputChanges++;
            DeviceController deviceController = (DeviceController) ((LinkedHashMap) sessionAudioPathViewModel.controllerMap).get(this.$device.getFinalControllerType());
            if (deviceController != null) {
                AudioDevice audioDevice = this.$device;
                this.label = 1;
                if (deviceController.transfer(audioDevice, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        DeviceController deviceController2 = (DeviceController) ((LinkedHashMap) this.this$0.controllerMap).get(ControllerType.BuiltIn);
        if (deviceController2 != null) {
            if (this.$device instanceof BluetoothDevice) {
                Iterable<AudioDevice> iterable = (Iterable) this.this$0._audioDevices.getValue();
                if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                    for (AudioDevice audioDevice2 : iterable) {
                        if (audioDevice2 instanceof BluetoothDevice) {
                            AudioDeviceExt.INSTANCE.getClass();
                            if (AudioDeviceExt.isActive(audioDevice2)) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        AudioDeviceExt audioDeviceExt = AudioDeviceExt.INSTANCE;
        AudioDevice audioDevice3 = this.$device;
        audioDeviceExt.getClass();
        if (audioDevice3 instanceof BuiltInDevice) {
            saEvent = SaEvent.PhoneSpeaker.INSTANCE;
        } else if (audioDevice3 instanceof BluetoothDevice) {
            CachedBluetoothDevice cachedBluetoothDevice = ((BluetoothDevice) audioDevice3).cachedBluetoothDevice;
            saEvent = CachedBluetoothDeviceExtKt.isBudsDevice(cachedBluetoothDevice != null ? cachedBluetoothDevice : null) ? SaEvent.ConnectedBuds.INSTANCE : SaEvent.ConnectedBt.INSTANCE;
        } else {
            saEvent = audioDevice3 instanceof DisconnectedDevice ? SaEvent.DisconnectedBt.INSTANCE : audioDevice3 instanceof ChromeCastDevice ? SaEvent.WifiSpeaker.INSTANCE : audioDevice3 instanceof GroupDevice ? SaEvent.GroupWifiSpeaker.INSTANCE : null;
        }
        if (saEvent != null) {
            MoSaLogging.send$default(MoSaLogging.INSTANCE, saEvent);
        }
        return Unit.INSTANCE;
    }
}
