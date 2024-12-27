package com.android.server.voiceinteraction;

import android.service.voice.HotwordDetectionServiceFailure;
import android.util.Slog;

import java.util.function.Consumer;

public final /* synthetic */ class HotwordDetectionConnection$$ExternalSyntheticLambda1
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DetectorSession detectorSession = (DetectorSession) obj;
        switch (this.$r8$classId) {
            case 0:
                detectorSession.destroyLocked();
                break;
            case 1:
                if (!(detectorSession instanceof VisualQueryDetectorSession)) {
                    detectorSession.reportErrorLocked(
                            new HotwordDetectionServiceFailure(
                                    10,
                                    "Shutdown hotword detection service on voice activation op"
                                        + " disabled!"));
                    detectorSession.destroyLocked();
                    break;
                }
                break;
            default:
                detectorSession.getClass();
                Slog.v("DetectorSession", "setDebugHotwordLoggingLocked: false");
                detectorSession.mDebugHotwordLogging = false;
                break;
        }
    }
}
