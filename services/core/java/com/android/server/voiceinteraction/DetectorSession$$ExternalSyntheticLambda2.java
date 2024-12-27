package com.android.server.voiceinteraction;

import android.os.RemoteException;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class DetectorSession$$ExternalSyntheticLambda2 implements BiConsumer {
    public final /* synthetic */ DetectorSession f$0;

    public /* synthetic */ DetectorSession$$ExternalSyntheticLambda2(DetectorSession detectorSession) {
        this.f$0 = detectorSession;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        DetectorSession detectorSession = this.f$0;
        Throwable th = (Throwable) obj2;
        int i = detectorSession.mVoiceInteractionServiceUid;
        if (!(th instanceof TimeoutException)) {
            if (th != null) {
                Slog.w("DetectorSession", "Failed to update state: " + th);
                return;
            }
            return;
        }
        Slog.w("DetectorSession", "updateState timed out");
        int i2 = 1;
        if (detectorSession.mUpdateStateAfterStartFinished.getAndSet(true)) {
            return;
        }
        try {
            detectorSession.mCallback.onStatusReported(100);
            if (detectorSession.getDetectorType() != 3) {
                int detectorType = detectorSession.getDetectorType();
                if (detectorType != 1) {
                    i2 = 2;
                    if (detectorType != 2) {
                        i2 = 0;
                    }
                }
                FrameworkStatsLog.write(FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_INIT_RESULT_REPORTED, i2, 4, i);
            }
        } catch (RemoteException e) {
            Slog.w("DetectorSession", "Failed to report initialization status UNKNOWN", e);
            if (detectorSession.getDetectorType() != 3) {
                HotwordMetricsLogger.writeDetectorEvent(detectorSession.getDetectorType(), 14, i);
            }
            detectorSession.notifyOnDetectorRemoteException();
        }
    }
}
