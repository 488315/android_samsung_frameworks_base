package com.android.settingslib.volume.data.repository;

import com.android.settingslib.volume.shared.model.AudioStreamModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AudioRepositoryImpl$getAudioStream$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $audioStream;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AudioRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioRepositoryImpl$getAudioStream$4(AudioRepositoryImpl audioRepositoryImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioRepositoryImpl;
        this.$audioStream = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AudioRepositoryImpl$getAudioStream$4 audioRepositoryImpl$getAudioStream$4 = new AudioRepositoryImpl$getAudioStream$4(this.this$0, this.$audioStream, continuation);
        audioRepositoryImpl$getAudioStream$4.L$0 = obj;
        return audioRepositoryImpl$getAudioStream$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioRepositoryImpl$getAudioStream$4) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            AudioStreamModel m977access$getCurrentAudioStreamtLTdkI8 = AudioRepositoryImpl.m977access$getCurrentAudioStreamtLTdkI8(this.this$0, this.$audioStream);
            this.label = 1;
            if (flowCollector.emit(m977access$getCurrentAudioStreamtLTdkI8, this) == coroutineSingletons) {
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
