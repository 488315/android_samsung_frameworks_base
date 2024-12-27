package com.android.server.soundtrigger_middleware;

import android.hardware.soundtrigger3.ISoundTriggerHwCallback;
import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.RecognitionEvent;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2
        implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ int $r8$classId = 2;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2(
            FakeSoundTriggerHal.ModelSession.RecognitionSession recognitionSession,
            PhraseRecognitionEvent phraseRecognitionEvent) {
        this.f$0 = recognitionSession;
        this.f$1 = phraseRecognitionEvent;
    }

    public /* synthetic */ FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2(
            FakeSoundTriggerHal.ModelSession.RecognitionSession recognitionSession,
            RecognitionEvent recognitionEvent) {
        this.f$0 = recognitionSession;
        this.f$1 = recognitionEvent;
    }

    public /* synthetic */ FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2(
            FakeSoundTriggerHal.ModelSession modelSession,
            PhraseRecognitionEvent phraseRecognitionEvent) {
        this.f$0 = modelSession;
        this.f$1 = phraseRecognitionEvent;
    }

    public /* synthetic */ FakeSoundTriggerHal$ModelSession$$ExternalSyntheticLambda2(
            FakeSoundTriggerHal.ModelSession modelSession, RecognitionEvent recognitionEvent) {
        this.f$0 = modelSession;
        this.f$1 = recognitionEvent;
    }

    public final void acceptOrThrow(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                FakeSoundTriggerHal.ModelSession modelSession =
                        (FakeSoundTriggerHal.ModelSession) this.f$0;
                ((ISoundTriggerHwCallback) obj)
                        .phraseRecognitionCallback(
                                modelSession.mModelHandle, (PhraseRecognitionEvent) this.f$1);
                break;
            case 1:
                FakeSoundTriggerHal.ModelSession modelSession2 =
                        (FakeSoundTriggerHal.ModelSession) this.f$0;
                ((ISoundTriggerHwCallback) obj)
                        .recognitionCallback(
                                modelSession2.mModelHandle, (RecognitionEvent) this.f$1);
                break;
            case 2:
                FakeSoundTriggerHal.ModelSession.RecognitionSession recognitionSession =
                        (FakeSoundTriggerHal.ModelSession.RecognitionSession) this.f$0;
                ((ISoundTriggerHwCallback) obj)
                        .phraseRecognitionCallback(
                                recognitionSession.this$1.mModelHandle,
                                (PhraseRecognitionEvent) this.f$1);
                break;
            default:
                FakeSoundTriggerHal.ModelSession.RecognitionSession recognitionSession2 =
                        (FakeSoundTriggerHal.ModelSession.RecognitionSession) this.f$0;
                ((ISoundTriggerHwCallback) obj)
                        .recognitionCallback(
                                recognitionSession2.this$1.mModelHandle,
                                (RecognitionEvent) this.f$1);
                break;
        }
    }
}
