package com.android.server.voiceinteraction;

import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class HotwordDetectionConnection$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ HotwordDetectionConnection f$0;

    public /* synthetic */ HotwordDetectionConnection$$ExternalSyntheticLambda3(HotwordDetectionConnection hotwordDetectionConnection, int i) {
        this.$r8$classId = i;
        this.f$0 = hotwordDetectionConnection;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        HotwordDetectionConnection hotwordDetectionConnection = this.f$0;
        hotwordDetectionConnection.getClass();
        switch (i) {
            case 0:
                Slog.v("HotwordDetectionConnection", "Timeout to reset mDebugHotwordLogging to false");
                synchronized (hotwordDetectionConnection.mLock) {
                    hotwordDetectionConnection.mDebugHotwordLogging = false;
                    hotwordDetectionConnection.runForEachDetectorSessionLocked(new HotwordDetectionConnection$$ExternalSyntheticLambda1(2));
                }
                return;
            default:
                Slog.v("HotwordDetectionConnection", "Time to restart the process, TTL has passed");
                synchronized (hotwordDetectionConnection.mLock) {
                    try {
                        hotwordDetectionConnection.restartProcessLocked();
                        int i2 = hotwordDetectionConnection.mDetectorType;
                        if (i2 != 3) {
                            FrameworkStatsLog.write(FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED, i2 != 1 ? i2 != 2 ? 0 : 2 : 1, 2, hotwordDetectionConnection.mVoiceInteractionServiceUid);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
        }
    }
}
