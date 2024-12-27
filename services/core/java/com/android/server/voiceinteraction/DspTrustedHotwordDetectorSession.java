package com.android.server.voiceinteraction;

import android.os.RemoteException;
import android.service.voice.HotwordRejectedResult;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;

import java.io.PrintWriter;
import java.util.concurrent.ScheduledFuture;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DspTrustedHotwordDetectorSession extends DetectorSession {
    public ScheduledFuture mCancellationKeyPhraseDetectionFuture;
    public HotwordRejectedResult mLastHotwordRejectedResult;
    public boolean mValidatingDspTrigger;

    @Override // com.android.server.voiceinteraction.DetectorSession
    public final void dumpLocked(PrintWriter printWriter) {
        super.dumpLocked(printWriter);
        printWriter.print("    ");
        printWriter.print("mValidatingDspTrigger=");
        printWriter.println(this.mValidatingDspTrigger);
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    public final void informRestartProcessLocked() {
        Slog.v("DspTrustedHotwordDetectorSession", "informRestartProcessLocked");
        boolean z = this.mValidatingDspTrigger;
        int i = this.mVoiceInteractionServiceUid;
        if (z) {
            try {
                this.mCallback.onRejected(new HotwordRejectedResult.Builder().build());
                FrameworkStatsLog.write(
                        FrameworkStatsLog.HOTWORD_DETECTOR_KEYPHRASE_TRIGGERED, 1, 10, i);
            } catch (RemoteException unused) {
                Slog.w("DspTrustedHotwordDetectorSession", "Failed to call #rejected");
                FrameworkStatsLog.write(FrameworkStatsLog.HOTWORD_DETECTOR_EVENTS, 1, 16, i);
                notifyOnDetectorRemoteException();
            }
            this.mValidatingDspTrigger = false;
        }
        this.mUpdateStateAfterStartFinished.set(false);
        try {
            this.mCallback.onProcessRestarted();
        } catch (RemoteException e) {
            Slog.w(
                    "DspTrustedHotwordDetectorSession",
                    "Failed to communicate #onProcessRestarted",
                    e);
            FrameworkStatsLog.write(FrameworkStatsLog.HOTWORD_DETECTOR_EVENTS, 1, 18, i);
            notifyOnDetectorRemoteException();
        }
        this.mPerformingExternalSourceHotwordDetection = false;
        closeExternalAudioStreamLocked("process restarted");
    }
}
