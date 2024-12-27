package android.companion.virtual.audio;

import android.annotation.SystemApi;
import android.companion.virtual.IVirtualDevice;
import android.content.Context;
import android.hardware.display.VirtualDisplay;
import android.media.AudioFormat;
import android.media.AudioPlaybackConfiguration;
import android.media.AudioRecordingConfiguration;
import android.os.RemoteException;

import java.io.Closeable;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes.dex */
public final class VirtualAudioDevice implements Closeable {
    private final AudioConfigurationChangeCallback mCallback;
    private final Context mContext;
    private final Executor mExecutor;
    private final CloseListener mListener;
    private VirtualAudioSession mOngoingSession;
    private final IVirtualDevice mVirtualDevice;
    private final VirtualDisplay mVirtualDisplay;

    @SystemApi
    public interface AudioConfigurationChangeCallback {
        void onPlaybackConfigChanged(List<AudioPlaybackConfiguration> list);

        void onRecordingConfigChanged(List<AudioRecordingConfiguration> list);
    }

    public interface CloseListener {
        void onClosed();
    }

    public VirtualAudioDevice(
            Context context,
            IVirtualDevice virtualDevice,
            VirtualDisplay virtualDisplay,
            Executor executor,
            AudioConfigurationChangeCallback callback,
            CloseListener listener) {
        this.mContext = context;
        this.mVirtualDevice = virtualDevice;
        this.mVirtualDisplay = virtualDisplay;
        this.mExecutor = executor;
        this.mCallback = callback;
        this.mListener = listener;
    }

    public AudioInjection startAudioInjection(AudioFormat injectionFormat) {
        Objects.requireNonNull(injectionFormat, "injectionFormat must not be null");
        if (this.mOngoingSession != null && this.mOngoingSession.getAudioInjection() != null) {
            throw new IllegalStateException(
                    "Cannot start an audio injection while a session is ongoing. Call close() on"
                            + " this device first to end the previous session.");
        }
        if (this.mOngoingSession == null) {
            this.mOngoingSession =
                    new VirtualAudioSession(this.mContext, this.mCallback, this.mExecutor);
        }
        try {
            this.mVirtualDevice.onAudioSessionStarting(
                    this.mVirtualDisplay.getDisplay().getDisplayId(),
                    this.mOngoingSession,
                    this.mOngoingSession.getAudioConfigChangedListener());
            return this.mOngoingSession.startAudioInjection(injectionFormat);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AudioCapture startAudioCapture(AudioFormat captureFormat) {
        Objects.requireNonNull(captureFormat, "captureFormat must not be null");
        if (this.mOngoingSession != null && this.mOngoingSession.getAudioCapture() != null) {
            throw new IllegalStateException(
                    "Cannot start an audio capture while a session is ongoing. Call close() on this"
                            + " device first to end the previous session.");
        }
        if (this.mOngoingSession == null) {
            this.mOngoingSession =
                    new VirtualAudioSession(this.mContext, this.mCallback, this.mExecutor);
        }
        try {
            this.mVirtualDevice.onAudioSessionStarting(
                    this.mVirtualDisplay.getDisplay().getDisplayId(),
                    this.mOngoingSession,
                    this.mOngoingSession.getAudioConfigChangedListener());
            return this.mOngoingSession.startAudioCapture(captureFormat);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public AudioCapture getAudioCapture() {
        if (this.mOngoingSession != null) {
            return this.mOngoingSession.getAudioCapture();
        }
        return null;
    }

    public AudioInjection getAudioInjection() {
        if (this.mOngoingSession != null) {
            return this.mOngoingSession.getAudioInjection();
        }
        return null;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.mOngoingSession != null) {
            this.mOngoingSession.close();
            this.mOngoingSession = null;
            try {
                this.mVirtualDevice.onAudioSessionEnded();
                if (this.mListener != null) {
                    this.mListener.onClosed();
                }
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }
}
