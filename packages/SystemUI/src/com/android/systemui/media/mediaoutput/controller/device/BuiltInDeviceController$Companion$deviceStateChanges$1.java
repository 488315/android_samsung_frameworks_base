package com.android.systemui.media.mediaoutput.controller.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

final class BuiltInDeviceController$Companion$deviceStateChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Pair<Context, AudioManager> $this_deviceStateChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BuiltInDeviceController$Companion$deviceStateChanges$1(Pair<? extends Context, ? extends AudioManager> pair, Continuation continuation) {
        super(2, continuation);
        this.$this_deviceStateChanges = pair;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BuiltInDeviceController$Companion$deviceStateChanges$1 builtInDeviceController$Companion$deviceStateChanges$1 = new BuiltInDeviceController$Companion$deviceStateChanges$1(this.$this_deviceStateChanges, continuation);
        builtInDeviceController$Companion$deviceStateChanges$1.L$0 = obj;
        return builtInDeviceController$Companion$deviceStateChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuiltInDeviceController$Companion$deviceStateChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.media.AudioDeviceCallback, com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$callback$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.content.BroadcastReceiver, com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Pair<Context, AudioManager> pair = this.$this_deviceStateChanges;
            final ?? r1 = new AudioDeviceCallback() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$callback$1
                @Override // android.media.AudioDeviceCallback
                public final void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
                    updateListForValidMedia(audioDeviceInfoArr);
                }

                @Override // android.media.AudioDeviceCallback
                public final void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
                    updateListForValidMedia(audioDeviceInfoArr);
                }

                public final void updateListForValidMedia(AudioDeviceInfo[] audioDeviceInfoArr) {
                    boolean isWiredHeadsetOn = ((AudioManager) pair.getSecond()).isWiredHeadsetOn();
                    for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                        AudioDeviceInfoExt.INSTANCE.getClass();
                        if (AudioDeviceInfoExt.isValidDeviceTypeForMedia(audioDeviceInfo, isWiredHeadsetOn)) {
                            ProducerScope producerScope2 = producerScope;
                            BuildersKt.launch$default(producerScope2, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope2, pair, null), 3);
                            return;
                        }
                    }
                }
            };
            final Pair<Context, AudioManager> pair2 = this.$this_deviceStateChanges;
            final ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Log.d("BuiltInDeviceController", "onReceive() - " + intent);
                    String action = intent.getAction();
                    if (action != null) {
                        switch (action.hashCode()) {
                            case -1966727609:
                                if (!action.equals("android.samsung.media.action.AUDIO_MODE")) {
                                    return;
                                }
                                ProducerScope producerScope2 = ProducerScope.this;
                                BuildersKt.launch$default(producerScope2, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope2, pair2, null), 3);
                                return;
                            case -1940635523:
                                if (!action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                                    return;
                                }
                                break;
                            case -1315844839:
                                if (!action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) {
                                    return;
                                }
                                break;
                            case -805245182:
                                if (!action.equals("android.intent.action.MULTISOUND_STATE_CHANGE")) {
                                    return;
                                }
                                ProducerScope producerScope22 = ProducerScope.this;
                                BuildersKt.launch$default(producerScope22, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope22, pair2, null), 3);
                                return;
                            case 487423555:
                                if (!action.equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                                    return;
                                }
                                ProducerScope producerScope222 = ProducerScope.this;
                                BuildersKt.launch$default(producerScope222, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope222, pair2, null), 3);
                                return;
                            case 1920758225:
                                if (!action.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                                    return;
                                }
                                ProducerScope producerScope2222 = ProducerScope.this;
                                BuildersKt.launch$default(producerScope2222, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope2222, pair2, null), 3);
                                return;
                            default:
                                return;
                        }
                        if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
                            ProducerScope producerScope3 = ProducerScope.this;
                            BuildersKt.launch$default(producerScope3, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope3, pair2, null), 3);
                        }
                    }
                }
            };
            ((AudioManager) this.$this_deviceStateChanges.getSecond()).registerAudioDeviceCallback(r1, null);
            Context context = (Context) this.$this_deviceStateChanges.getFirst();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.MULTISOUND_STATE_CHANGE");
            intentFilter.addAction("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED");
            intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            intentFilter.addAction("android.samsung.media.action.AUDIO_MODE");
            intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
            Unit unit = Unit.INSTANCE;
            context.registerReceiver(r3, intentFilter);
            final Pair<Context, AudioManager> pair3 = this.$this_deviceStateChanges;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Log.d("BuiltInDeviceController", "unregister");
                    ((AudioManager) pair3.getSecond()).unregisterAudioDeviceCallback(r1);
                    ((Context) pair3.getFirst()).unregisterReceiver(r3);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
