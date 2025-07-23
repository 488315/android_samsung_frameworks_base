package com.android.settingslib.volume.data.repository;

import android.media.AudioSystem;
import com.android.settingslib.volume.shared.AudioLogger;
import com.android.settingslib.volume.shared.model.AudioStream;
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
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AudioRepositoryImpl$setVolume$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $audioStream;
    final /* synthetic */ int $volume;
    int label;
    final /* synthetic */ AudioRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioRepositoryImpl$setVolume$2(AudioRepositoryImpl audioRepositoryImpl, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioRepositoryImpl;
        this.$audioStream = i;
        this.$volume = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AudioRepositoryImpl$setVolume$2(this.this$0, this.$audioStream, this.$volume, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioRepositoryImpl$setVolume$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        AudioLogger audioLogger = this.this$0.logger;
        int i = this.$audioStream;
        int i2 = this.$volume;
        VolumeLogger volumeLogger = (VolumeLogger) audioLogger;
        volumeLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        VolumeLogger$$ExternalSyntheticLambda0 volumeLogger$$ExternalSyntheticLambda0 = new VolumeLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = volumeLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("SysUI_Volume", logLevel, volumeLogger$$ExternalSyntheticLambda0, null);
        AudioStream.Companion companion = AudioStream.Companion;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = AudioSystem.streamToString(i);
        logMessageImpl.int1 = i2;
        logBuffer.commit(obtain);
        this.this$0.audioManager.setStreamVolume(this.$audioStream, this.$volume, 0);
        return Unit.INSTANCE;
    }
}
