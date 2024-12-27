package com.android.server.soundtrigger_middleware;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0
        implements ISoundTriggerHal.GlobalCallback {
    public final /* synthetic */ SoundTriggerHalConcurrentCaptureHandler f$0;
    public final /* synthetic */ ISoundTriggerHal.GlobalCallback f$1;

    public /* synthetic */ SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda0(
            SoundTriggerHalConcurrentCaptureHandler soundTriggerHalConcurrentCaptureHandler,
            ISoundTriggerHal.GlobalCallback globalCallback) {
        this.f$0 = soundTriggerHalConcurrentCaptureHandler;
        this.f$1 = globalCallback;
    }

    @Override // com.android.server.soundtrigger_middleware.ISoundTriggerHal.GlobalCallback
    public final void onResourcesAvailable() {
        SoundTriggerHalConcurrentCaptureHandler soundTriggerHalConcurrentCaptureHandler = this.f$0;
        soundTriggerHalConcurrentCaptureHandler.getClass();
        ISoundTriggerHal.GlobalCallback globalCallback = this.f$1;
        Objects.requireNonNull(globalCallback);
        soundTriggerHalConcurrentCaptureHandler.mCallbackThread.push(
                new SoundTriggerHalConcurrentCaptureHandler$$ExternalSyntheticLambda3(
                        0, globalCallback));
    }
}
