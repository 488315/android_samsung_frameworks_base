package com.android.settingslib.volume.domain.interactor;

import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import com.android.settingslib.volume.shared.model.AudioStreamModel;
import com.android.settingslib.volume.shared.model.RingerMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AudioVolumeInteractor$getAudioStream$1 extends SuspendLambda implements Function4 {
    /* synthetic */ int I$0;
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ AudioVolumeInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioVolumeInteractor$getAudioStream$1(AudioVolumeInteractor audioVolumeInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = audioVolumeInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        int i = ((RingerMode) obj2).value;
        boolean booleanValue = ((Boolean) obj3).booleanValue();
        AudioVolumeInteractor$getAudioStream$1 audioVolumeInteractor$getAudioStream$1 = new AudioVolumeInteractor$getAudioStream$1(this.this$0, (Continuation) obj4);
        audioVolumeInteractor$getAudioStream$1.L$0 = (AudioStreamModel) obj;
        audioVolumeInteractor$getAudioStream$1.I$0 = i;
        audioVolumeInteractor$getAudioStream$1.Z$0 = booleanValue;
        return audioVolumeInteractor$getAudioStream$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object num;
        AudioStreamModel audioStreamModel;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            AudioStreamModel audioStreamModel2 = (AudioStreamModel) this.L$0;
            int i2 = this.I$0;
            boolean z = this.Z$0;
            AudioVolumeInteractor audioVolumeInteractor = this.this$0;
            this.L$0 = audioStreamModel2;
            this.I$0 = 0;
            this.label = 1;
            audioVolumeInteractor.getClass();
            if (z) {
                num = ((AudioRepositoryImpl) audioVolumeInteractor.audioRepository).m979getLastAudibleVolumeVrMivd8(audioStreamModel2.audioStream, this);
            } else {
                int i3 = audioStreamModel2.audioStream;
                int i4 = audioStreamModel2.minVolume;
                boolean z2 = audioStreamModel2.isMuted;
                if ((i3 == 2 || i3 == 5) && i2 == 1) {
                    if (i3 == 2 || (i3 == 5 && z2)) {
                        num = new Integer(i4);
                    }
                    num = new Integer(audioStreamModel2.volume);
                } else {
                    if (z2) {
                        num = new Integer(i4);
                    }
                    num = new Integer(audioStreamModel2.volume);
                }
            }
            if (num == coroutineSingletons) {
                return coroutineSingletons;
            }
            obj = num;
            audioStreamModel = audioStreamModel2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            audioStreamModel = (AudioStreamModel) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        return new AudioStreamModel(audioStreamModel.audioStream, ((Number) obj).intValue(), audioStreamModel.minVolume, audioStreamModel.maxVolume, audioStreamModel.isAffectedByMute, audioStreamModel.isAffectedByRingerMode, audioStreamModel.isMuted, null);
    }
}
