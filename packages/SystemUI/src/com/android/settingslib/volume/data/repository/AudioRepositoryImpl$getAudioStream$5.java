package com.android.settingslib.volume.data.repository;

import android.media.AudioSystem;
import com.android.settingslib.volume.shared.AudioLogger;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.settingslib.volume.shared.model.AudioStreamModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.shared.VolumeLogger;
import com.android.systemui.volume.shared.VolumeLogger$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AudioRepositoryImpl$getAudioStream$5 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $audioStream;
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AudioRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioRepositoryImpl$getAudioStream$5(AudioRepositoryImpl audioRepositoryImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioRepositoryImpl;
        this.$audioStream = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AudioRepositoryImpl$getAudioStream$5 audioRepositoryImpl$getAudioStream$5 = new AudioRepositoryImpl$getAudioStream$5(this.this$0, this.$audioStream, continuation);
        audioRepositoryImpl$getAudioStream$5.L$0 = obj;
        return audioRepositoryImpl$getAudioStream$5;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioRepositoryImpl$getAudioStream$5) create((AudioStreamModel) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AudioStreamModel audioStreamModel = (AudioStreamModel) this.L$0;
        AudioLogger audioLogger = this.this$0.logger;
        int i = this.$audioStream;
        VolumeLogger volumeLogger = (VolumeLogger) audioLogger;
        volumeLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        VolumeLogger$$ExternalSyntheticLambda0 volumeLogger$$ExternalSyntheticLambda0 = new VolumeLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = volumeLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("SysUI_Volume", logLevel, volumeLogger$$ExternalSyntheticLambda0, null);
        AudioStream.Companion companion = AudioStream.Companion;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = AudioSystem.streamToString(i);
        logMessageImpl.int1 = audioStreamModel.volume;
        logBuffer.commit(obtain);
        return Unit.INSTANCE;
    }
}
