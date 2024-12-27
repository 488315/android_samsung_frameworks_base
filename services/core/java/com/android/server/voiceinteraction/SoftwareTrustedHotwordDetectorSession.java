package com.android.server.voiceinteraction;

import android.media.AudioFormat;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.service.voice.HotwordDetectedResult;
import android.service.voice.HotwordDetectionServiceFailure;
import android.service.voice.HotwordRejectedResult;
import android.service.voice.IDspHotwordDetectionCallback;
import android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback;
import android.util.Slog;
import java.io.IOException;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoftwareTrustedHotwordDetectorSession extends DetectorSession {
    public boolean mPerformingSoftwareHotwordDetection;
    public IMicrophoneHotwordDetectionVoiceInteractionCallback mSoftwareCallback;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.voiceinteraction.SoftwareTrustedHotwordDetectorSession$1, reason: invalid class name */
    public final class AnonymousClass1 extends IDspHotwordDetectionCallback.Stub {
        public AnonymousClass1() {
        }

        public final void onDetected(HotwordDetectedResult hotwordDetectedResult) {
            synchronized (SoftwareTrustedHotwordDetectorSession.this.mLock) {
                try {
                    HotwordMetricsLogger.writeKeyphraseTriggerEvent(2, 5, SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                    SoftwareTrustedHotwordDetectorSession softwareTrustedHotwordDetectorSession = SoftwareTrustedHotwordDetectorSession.this;
                    if (!softwareTrustedHotwordDetectorSession.mPerformingSoftwareHotwordDetection) {
                        Slog.i("SoftwareTrustedHotwordDetectorSession", "Hotword detection has already completed");
                        HotwordMetricsLogger.writeKeyphraseTriggerEvent(2, 7, SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                        return;
                    }
                    softwareTrustedHotwordDetectorSession.mPerformingSoftwareHotwordDetection = false;
                    try {
                        Binder.withCleanCallingIdentity(new DetectorSession$$ExternalSyntheticLambda6(softwareTrustedHotwordDetectorSession));
                        SoftwareTrustedHotwordDetectorSession.this.saveProximityValueToBundle(hotwordDetectedResult);
                        try {
                            HotwordDetectedResult startCopyingAudioStreams = SoftwareTrustedHotwordDetectorSession.this.mHotwordAudioStreamCopier.startCopyingAudioStreams(hotwordDetectedResult, true);
                            try {
                                SoftwareTrustedHotwordDetectorSession.this.mSoftwareCallback.onDetected(startCopyingAudioStreams, (AudioFormat) null, (ParcelFileDescriptor) null);
                                Slog.i("SoftwareTrustedHotwordDetectorSession", "Egressed " + HotwordDetectedResult.getUsageSize(startCopyingAudioStreams) + " bits from hotword trusted process");
                                if (SoftwareTrustedHotwordDetectorSession.this.mDebugHotwordLogging) {
                                    Slog.i("SoftwareTrustedHotwordDetectorSession", "Egressed detected result: " + startCopyingAudioStreams);
                                }
                            } catch (RemoteException e) {
                                SoftwareTrustedHotwordDetectorSession.this.notifyOnDetectorRemoteException();
                                HotwordMetricsLogger.writeDetectorEvent(2, 17, SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                                throw e;
                            }
                        } catch (IOException e2) {
                            Slog.w("SoftwareTrustedHotwordDetectorSession", "Ignoring #onDetected due to a IOException", e2);
                            try {
                                SoftwareTrustedHotwordDetectorSession.this.mSoftwareCallback.onHotwordDetectionServiceFailure(new HotwordDetectionServiceFailure(6, "Copy audio stream failure."));
                            } catch (RemoteException e3) {
                                SoftwareTrustedHotwordDetectorSession.this.notifyOnDetectorRemoteException();
                                HotwordMetricsLogger.writeDetectorEvent(2, 15, SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                                throw e3;
                            }
                        }
                    } catch (SecurityException e4) {
                        Slog.w("SoftwareTrustedHotwordDetectorSession", "Ignoring #onDetected due to a SecurityException", e4);
                        HotwordMetricsLogger.writeKeyphraseTriggerEvent(2, 8, SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                        try {
                            SoftwareTrustedHotwordDetectorSession.this.mSoftwareCallback.onHotwordDetectionServiceFailure(new HotwordDetectionServiceFailure(5, "Security exception occurs in #onDetected method."));
                        } catch (RemoteException e5) {
                            SoftwareTrustedHotwordDetectorSession.this.notifyOnDetectorRemoteException();
                            HotwordMetricsLogger.writeDetectorEvent(2, 15, SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
                            throw e5;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onRejected(HotwordRejectedResult hotwordRejectedResult) {
            HotwordMetricsLogger.writeKeyphraseTriggerEvent(2, 6, SoftwareTrustedHotwordDetectorSession.this.mVoiceInteractionServiceUid);
        }
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    public final void dumpLocked(PrintWriter printWriter) {
        super.dumpLocked(printWriter);
        printWriter.print("    ");
        printWriter.print("mPerformingSoftwareHotwordDetection=");
        printWriter.println(this.mPerformingSoftwareHotwordDetection);
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    public final void informRestartProcessLocked() {
        int i = this.mVoiceInteractionServiceUid;
        Slog.v("SoftwareTrustedHotwordDetectorSession", "informRestartProcessLocked");
        this.mUpdateStateAfterStartFinished.set(false);
        try {
            this.mCallback.onProcessRestarted();
        } catch (RemoteException e) {
            Slog.w("SoftwareTrustedHotwordDetectorSession", "Failed to communicate #onProcessRestarted", e);
            HotwordMetricsLogger.writeDetectorEvent(2, 18, i);
            notifyOnDetectorRemoteException();
        }
        if (this.mPerformingSoftwareHotwordDetection) {
            Slog.i("SoftwareTrustedHotwordDetectorSession", "Process restarted: calling startRecognition() again");
            this.mRemoteDetectionService.run(new SoftwareTrustedHotwordDetectorSession$$ExternalSyntheticLambda1(new AnonymousClass1()));
            HotwordMetricsLogger.writeDetectorEvent(2, 9, i);
        }
        this.mPerformingExternalSourceHotwordDetection = false;
        closeExternalAudioStreamLocked("process restarted");
    }
}
