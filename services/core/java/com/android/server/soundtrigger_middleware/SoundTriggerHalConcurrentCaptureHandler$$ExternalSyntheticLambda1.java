package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger.PhraseRecognitionEvent;
import android.media.soundtrigger.PhraseRecognitionExtra;
import android.media.soundtrigger.RecognitionEvent;
import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda1
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ int f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda1(
            int i, SoundTriggerHalConcurrentCaptureHandler.LoadedModel loadedModel) {
        this.f$0 = i;
        this.f$1 = loadedModel;
    }

    public /* synthetic */ SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda1(
            SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper callbackWrapper, int i) {
        this.f$1 = callbackWrapper;
        this.f$0 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                int i = this.f$0;
                SoundTriggerHalConcurrentCaptureHandler.LoadedModel loadedModel =
                        (SoundTriggerHalConcurrentCaptureHandler.LoadedModel) this.f$1;
                int i2 = loadedModel.type;
                ISoundTriggerHal.ModelCallback modelCallback = loadedModel.callback;
                if (i2 == 0) {
                    PhraseRecognitionEvent phraseRecognitionEvent = new PhraseRecognitionEvent();
                    RecognitionEvent recognitionEvent = new RecognitionEvent();
                    recognitionEvent.data = new byte[0];
                    phraseRecognitionEvent.common = recognitionEvent;
                    phraseRecognitionEvent.phraseExtras = new PhraseRecognitionExtra[0];
                    recognitionEvent.type = 0;
                    recognitionEvent.status = 1;
                    PhraseRecognitionEventSys phraseRecognitionEventSys =
                            new PhraseRecognitionEventSys();
                    phraseRecognitionEventSys.phraseRecognitionEvent = phraseRecognitionEvent;
                    modelCallback.phraseRecognitionCallback(i, phraseRecognitionEventSys);
                    break;
                } else if (i2 == 1) {
                    RecognitionEvent recognitionEvent2 = new RecognitionEvent();
                    recognitionEvent2.data = new byte[0];
                    recognitionEvent2.type = 1;
                    recognitionEvent2.status = 1;
                    RecognitionEventSys recognitionEventSys = new RecognitionEventSys();
                    recognitionEventSys.recognitionEvent = recognitionEvent2;
                    modelCallback.recognitionCallback(i, recognitionEventSys);
                    break;
                }
                break;
            default:
                SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper callbackWrapper =
                        (SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper) this.f$1;
                callbackWrapper.mDelegateCallback.modelUnloaded(this.f$0);
                break;
        }
    }
}
