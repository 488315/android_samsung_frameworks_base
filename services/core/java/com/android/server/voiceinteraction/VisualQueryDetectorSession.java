package com.android.server.voiceinteraction;

import android.media.AudioFormat;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.service.voice.IMicrophoneHotwordDetectionVoiceInteractionCallback;
import android.util.Slog;

import com.android.internal.app.IVisualQueryDetectionAttentionListener;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VisualQueryDetectorSession extends DetectorSession {
    public IVisualQueryDetectionAttentionListener mAttentionListener;
    public boolean mEgressingData;
    public boolean mEnableAccessibilityDataEgress;
    public boolean mQueryStreaming;

    @Override // com.android.server.voiceinteraction.DetectorSession
    public final void dumpLocked(PrintWriter printWriter) {
        super.dumpLocked(printWriter);
        printWriter.print("    ");
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    public final void informRestartProcessLocked() {
        Slog.v("VisualQueryDetectorSession", "informRestartProcessLocked");
        this.mUpdateStateAfterStartFinished.set(false);
        try {
            this.mCallback.onProcessRestarted();
        } catch (RemoteException e) {
            Slog.w("VisualQueryDetectorSession", "Failed to communicate #onProcessRestarted", e);
            notifyOnDetectorRemoteException();
        }
    }

    @Override // com.android.server.voiceinteraction.DetectorSession
    public final void startListeningFromExternalSourceLocked(
            ParcelFileDescriptor parcelFileDescriptor,
            AudioFormat audioFormat,
            PersistableBundle persistableBundle,
            IMicrophoneHotwordDetectionVoiceInteractionCallback
                    iMicrophoneHotwordDetectionVoiceInteractionCallback) {
        throw new UnsupportedOperationException(
                "HotwordDetectionService method should not be called from"
                    + " VisualQueryDetectorSession.");
    }
}
