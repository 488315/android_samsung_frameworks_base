package com.android.systemui.media.mediaoutput.controller.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MusicShareDeviceController$Companion$castDeviceChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $context;
    final /* synthetic */ AudioManager $this_castDeviceChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MusicShareDeviceController$Companion$castDeviceChanges$1(AudioManager audioManager, Context context, Continuation continuation) {
        super(2, continuation);
        this.$this_castDeviceChanges = audioManager;
        this.$context = context;
    }

    public static final Job invokeSuspend$updateDevices(ProducerScope producerScope, AudioManager audioManager) {
        return BuildersKt.launch$default(producerScope, null, null, new MusicShareDeviceController$Companion$castDeviceChanges$1$updateDevices$1(producerScope, audioManager, null), 3);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MusicShareDeviceController$Companion$castDeviceChanges$1 musicShareDeviceController$Companion$castDeviceChanges$1 = new MusicShareDeviceController$Companion$castDeviceChanges$1(this.$this_castDeviceChanges, this.$context, continuation);
        musicShareDeviceController$Companion$castDeviceChanges$1.L$0 = obj;
        return musicShareDeviceController$Companion$castDeviceChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MusicShareDeviceController$Companion$castDeviceChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.media.AudioDeviceCallback, com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$Companion$castDeviceChanges$1$callback$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.content.BroadcastReceiver, com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$Companion$castDeviceChanges$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final AudioManager audioManager = this.$this_castDeviceChanges;
            final ?? r1 = new AudioDeviceCallback() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$Companion$castDeviceChanges$1$callback$1
                @Override // android.media.AudioDeviceCallback
                public final void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
                    Log.d("MusicShareDeviceController", "onAudioDevicesAdded()");
                    MusicShareDeviceController$Companion$castDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, audioManager);
                }

                @Override // android.media.AudioDeviceCallback
                public final void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
                    Log.d("MusicShareDeviceController", "onAudioDevicesRemoved()");
                    MusicShareDeviceController$Companion$castDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, audioManager);
                }
            };
            final AudioManager audioManager2 = this.$this_castDeviceChanges;
            final ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$Companion$castDeviceChanges$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Log.d("MusicShareDeviceController", "onReceive() - " + intent);
                    String action = intent.getAction();
                    if (action != null) {
                        switch (action.hashCode()) {
                            case -1940635523:
                                if (!action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                                    return;
                                }
                                break;
                            case -1513304802:
                                if (action.equals("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED")) {
                                    MusicShareDeviceController$Companion$castDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, audioManager2);
                                    return;
                                }
                                return;
                            case -1315844839:
                                if (!action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) {
                                    return;
                                }
                                break;
                            case 1920758225:
                                if (!action.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                                    return;
                                }
                                break;
                            default:
                                return;
                        }
                        if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
                            MusicShareDeviceController$Companion$castDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this, audioManager2);
                        }
                    }
                }
            };
            this.$this_castDeviceChanges.registerAudioDeviceCallback(r1, null);
            Context context = this.$context;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
            Unit unit = Unit.INSTANCE;
            context.registerReceiver(r3, intentFilter);
            invokeSuspend$updateDevices(producerScope, this.$this_castDeviceChanges);
            final AudioManager audioManager3 = this.$this_castDeviceChanges;
            final Context context2 = this.$context;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$Companion$castDeviceChanges$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Log.d("MusicShareDeviceController", "unregisterAudioDeviceCallback");
                    audioManager3.unregisterAudioDeviceCallback(r1);
                    context2.unregisterReceiver(r3);
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
