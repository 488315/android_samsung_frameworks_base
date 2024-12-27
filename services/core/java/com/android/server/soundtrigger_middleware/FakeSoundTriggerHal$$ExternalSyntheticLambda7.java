package com.android.server.soundtrigger_middleware;

import android.hardware.soundtrigger3.ISoundTriggerHwCallback;
import android.media.soundtrigger_middleware.ISoundTriggerInjection;

import com.android.internal.util.FunctionalUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class FakeSoundTriggerHal$$ExternalSyntheticLambda7
        implements FunctionalUtils.ThrowingConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FakeSoundTriggerHal.ModelSession.RecognitionSession f$0;

    public /* synthetic */ FakeSoundTriggerHal$$ExternalSyntheticLambda7(
            FakeSoundTriggerHal.ModelSession.RecognitionSession recognitionSession, int i) {
        this.$r8$classId = i;
        this.f$0 = recognitionSession;
    }

    public final void acceptOrThrow(Object obj) {
        int i = this.$r8$classId;
        FakeSoundTriggerHal.ModelSession.RecognitionSession recognitionSession = this.f$0;
        switch (i) {
            case 0:
                ((ISoundTriggerInjection) obj).onRecognitionStopped(recognitionSession);
                break;
            case 1:
                ((ISoundTriggerHwCallback) obj)
                        .phraseRecognitionCallback(
                                FakeSoundTriggerHal.ModelSession.this.mModelHandle,
                                FakeSoundTriggerHal.m893$$Nest$smcreateDefaultKeyphraseEvent(1));
                break;
            default:
                ((ISoundTriggerHwCallback) obj)
                        .recognitionCallback(
                                FakeSoundTriggerHal.ModelSession.this.mModelHandle,
                                FakeSoundTriggerHal.createDefaultEvent(1));
                break;
        }
    }
}
