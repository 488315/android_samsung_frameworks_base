package com.android.server.soundtrigger_middleware;

import android.media.soundtrigger_middleware.PhraseRecognitionEventSys;
import android.media.soundtrigger_middleware.RecognitionEventSys;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */
class SoundTriggerHalConcurrentCaptureHandler$CallbackWrapper$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */
    SoundTriggerHalConcurrentCaptureHandler$CallbackWrapper$$ExternalSyntheticLambda0(
            SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper callbackWrapper,
            int i,
            PhraseRecognitionEventSys phraseRecognitionEventSys) {
        this.f$0 = callbackWrapper;
        this.f$1 = i;
        this.f$2 = phraseRecognitionEventSys;
    }

    public /* synthetic */
    SoundTriggerHalConcurrentCaptureHandler$CallbackWrapper$$ExternalSyntheticLambda0(
            SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper callbackWrapper,
            int i,
            RecognitionEventSys recognitionEventSys) {
        this.f$0 = callbackWrapper;
        this.f$1 = i;
        this.f$2 = recognitionEventSys;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper callbackWrapper = this.f$0;
                callbackWrapper.mDelegateCallback.recognitionCallback(
                        this.f$1, (RecognitionEventSys) this.f$2);
                break;
            default:
                SoundTriggerHalConcurrentCaptureHandler.CallbackWrapper callbackWrapper2 = this.f$0;
                callbackWrapper2.mDelegateCallback.phraseRecognitionCallback(
                        this.f$1, (PhraseRecognitionEventSys) this.f$2);
                break;
        }
    }
}
