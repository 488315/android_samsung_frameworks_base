package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.Phrase;
import android.media.soundtrigger.PhraseSoundModel;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;

import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class FakeSoundTriggerHal$$ExternalSyntheticLambda4
        implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ FakeSoundTriggerHal.ModelSession f$2;

    public /* synthetic */ FakeSoundTriggerHal$$ExternalSyntheticLambda4(
            FakeSoundTriggerHal fakeSoundTriggerHal,
            PhraseSoundModel phraseSoundModel,
            FakeSoundTriggerHal.ModelSession modelSession) {
        this.$r8$classId = 1;
        this.f$0 = fakeSoundTriggerHal;
        this.f$1 = phraseSoundModel;
        this.f$2 = modelSession;
    }

    public /* synthetic */ FakeSoundTriggerHal$$ExternalSyntheticLambda4(
            Object obj, Object obj2, FakeSoundTriggerHal.ModelSession modelSession, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = modelSession;
    }

    public final void acceptOrThrow(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ISoundTriggerInjection iSoundTriggerInjection = (ISoundTriggerInjection) obj;
                iSoundTriggerInjection.onSoundModelLoaded(
                        (SoundModel) this.f$1,
                        (Phrase[]) null,
                        this.f$2,
                        ((FakeSoundTriggerHal) this.f$0).mGlobalEventSession);
                break;
            case 1:
                FakeSoundTriggerHal fakeSoundTriggerHal = (FakeSoundTriggerHal) this.f$0;
                PhraseSoundModel phraseSoundModel = (PhraseSoundModel) this.f$1;
                FakeSoundTriggerHal.ModelSession modelSession = this.f$2;
                fakeSoundTriggerHal.getClass();
                ((ISoundTriggerInjection) obj)
                        .onSoundModelLoaded(
                                phraseSoundModel.common,
                                phraseSoundModel.phrases,
                                modelSession,
                                fakeSoundTriggerHal.mGlobalEventSession);
                break;
            default:
                ((ISoundTriggerInjection) obj)
                        .onRecognitionStarted(
                                -1,
                                (RecognitionConfig) this.f$0,
                                (FakeSoundTriggerHal.ModelSession.RecognitionSession) this.f$1,
                                this.f$2);
                break;
        }
    }
}
