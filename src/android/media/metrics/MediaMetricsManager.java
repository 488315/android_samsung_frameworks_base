package android.media.metrics;

import android.os.PersistableBundle;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public final class MediaMetricsManager {
    public static final long INVALID_TIMESTAMP = -1;
    private static final String TAG = "MediaMetricsManager";
    private IMediaMetricsManager mService;
    private int mUserId;

    public MediaMetricsManager(IMediaMetricsManager iMediaMetricsManager, int i) {
        this.mService = iMediaMetricsManager;
        this.mUserId = i;
    }

    public void reportPlaybackMetrics(String str, PlaybackMetrics playbackMetrics) {
        try {
            this.mService.reportPlaybackMetrics(str, playbackMetrics, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportBundleMetrics(String str, PersistableBundle persistableBundle) {
        try {
            this.mService.reportBundleMetrics(str, persistableBundle, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportNetworkEvent(String str, NetworkEvent networkEvent) {
        try {
            this.mService.reportNetworkEvent(str, networkEvent, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportPlaybackStateEvent(String str, PlaybackStateEvent playbackStateEvent) {
        try {
            this.mService.reportPlaybackStateEvent(str, playbackStateEvent, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportTrackChangeEvent(String str, TrackChangeEvent trackChangeEvent) {
        try {
            this.mService.reportTrackChangeEvent(str, trackChangeEvent, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public PlaybackSession createPlaybackSession() {
        try {
            return new PlaybackSession(this.mService.getPlaybackSessionId(this.mUserId), this);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public RecordingSession createRecordingSession() {
        try {
            return new RecordingSession(this.mService.getRecordingSessionId(this.mUserId), this);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public TranscodingSession createTranscodingSession() {
        try {
            return new TranscodingSession(this.mService.getTranscodingSessionId(this.mUserId), this);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public EditingSession createEditingSession() {
        try {
            return new EditingSession(this.mService.getEditingSessionId(this.mUserId), this);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public BundleSession createBundleSession() {
        try {
            return new BundleSession(this.mService.getBundleSessionId(this.mUserId), this);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseSessionId(String str) {
        try {
            this.mService.releaseSessionId(str, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportPlaybackErrorEvent(String str, PlaybackErrorEvent playbackErrorEvent) {
        try {
            this.mService.reportPlaybackErrorEvent(str, playbackErrorEvent, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportEditingEndedEvent(String str, EditingEndedEvent editingEndedEvent) {
        try {
            this.mService.reportEditingEndedEvent(str, editingEndedEvent, this.mUserId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
