package com.android.settingslib.volume.data.repository;

import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import com.android.internal.util.ConcurrentUtils;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes.dex */
final class AudioRepositoryImpl$communicationDevice$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AudioRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioRepositoryImpl$communicationDevice$1(AudioRepositoryImpl audioRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AudioRepositoryImpl$communicationDevice$1 audioRepositoryImpl$communicationDevice$1 = new AudioRepositoryImpl$communicationDevice$1(this.this$0, continuation);
        audioRepositoryImpl$communicationDevice$1.L$0 = obj;
        return audioRepositoryImpl$communicationDevice$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioRepositoryImpl$communicationDevice$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            AudioManager.OnCommunicationDeviceChangedListener onCommunicationDeviceChangedListener = new AudioManager.OnCommunicationDeviceChangedListener() { // from class: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$communicationDevice$1$listener$1
                @Override // android.media.AudioManager.OnCommunicationDeviceChangedListener
                public final void onCommunicationDeviceChanged(AudioDeviceInfo audioDeviceInfo) {
                    ((ChannelCoroutine) producerScope).mo3476trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            this.this$0.audioManager.addOnCommunicationDeviceChangedListener(ConcurrentUtils.DIRECT_EXECUTOR, onCommunicationDeviceChangedListener);
            AudioRepositoryImpl$mode$1$$ExternalSyntheticLambda0 audioRepositoryImpl$mode$1$$ExternalSyntheticLambda0 = new AudioRepositoryImpl$mode$1$$ExternalSyntheticLambda0(this.this$0, onCommunicationDeviceChangedListener, 1);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, audioRepositoryImpl$mode$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
