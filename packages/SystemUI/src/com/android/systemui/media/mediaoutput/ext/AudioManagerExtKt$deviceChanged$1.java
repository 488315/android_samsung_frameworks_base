package com.android.systemui.media.mediaoutput.ext;

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
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class AudioManagerExtKt$deviceChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ AudioManager $this_deviceChanged;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioManagerExtKt$deviceChanged$1(AudioManager audioManager, Continuation continuation) {
        super(2, continuation);
        this.$this_deviceChanged = audioManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AudioManagerExtKt$deviceChanged$1 audioManagerExtKt$deviceChanged$1 = new AudioManagerExtKt$deviceChanged$1(this.$this_deviceChanged, continuation);
        audioManagerExtKt$deviceChanged$1.L$0 = obj;
        return audioManagerExtKt$deviceChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioManagerExtKt$deviceChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.media.AudioDeviceCallback, com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt$deviceChanged$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new AudioDeviceCallback() { // from class: com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt$deviceChanged$1$callback$1
                @Override // android.media.AudioDeviceCallback
                public final void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
                    ProducerScope producerScope2 = ProducerScope.this;
                    BuildersKt.launch$default(producerScope2, null, null, new AudioManagerExtKt$deviceChanged$1$callback$1$onAudioDevicesAdded$1(producerScope2, null), 3);
                }

                @Override // android.media.AudioDeviceCallback
                public final void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
                    ProducerScope producerScope2 = ProducerScope.this;
                    BuildersKt.launch$default(producerScope2, null, null, new AudioManagerExtKt$deviceChanged$1$callback$1$onAudioDevicesRemoved$1(producerScope2, null), 3);
                }
            };
            Log.d("AudioManagerExt", "registerAudioDeviceCallback()");
            this.$this_deviceChanged.registerAudioDeviceCallback(r1, null);
            final AudioManager audioManager = this.$this_deviceChanged;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt$deviceChanged$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    AudioManager audioManager2 = audioManager;
                    Log.d("AudioManagerExt", "unregisterAudioDeviceCallback()");
                    audioManager2.unregisterAudioDeviceCallback(r1);
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
