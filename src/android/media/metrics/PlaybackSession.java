package android.media.metrics;

import android.annotation.NonNull;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class PlaybackSession implements AutoCloseable {
    private boolean mClosed = false;
    private final String mId;
    private final LogSessionId mLogSessionId;
    private final MediaMetricsManager mManager;

    public PlaybackSession(String str, MediaMetricsManager mediaMetricsManager) {
        this.mId = str;
        this.mManager = mediaMetricsManager;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) mediaMetricsManager);
        this.mLogSessionId = new LogSessionId(str);
    }

    public void reportPlaybackMetrics(PlaybackMetrics playbackMetrics) {
        this.mManager.reportPlaybackMetrics(this.mId, playbackMetrics);
    }

    public void reportPlaybackErrorEvent(PlaybackErrorEvent playbackErrorEvent) {
        this.mManager.reportPlaybackErrorEvent(this.mId, playbackErrorEvent);
    }

    public void reportNetworkEvent(NetworkEvent networkEvent) {
        this.mManager.reportNetworkEvent(this.mId, networkEvent);
    }

    public void reportPlaybackStateEvent(PlaybackStateEvent playbackStateEvent) {
        this.mManager.reportPlaybackStateEvent(this.mId, playbackStateEvent);
    }

    public void reportTrackChangeEvent(TrackChangeEvent trackChangeEvent) {
        this.mManager.reportTrackChangeEvent(this.mId, trackChangeEvent);
    }

    public LogSessionId getSessionId() {
        return this.mLogSessionId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mId, ((PlaybackSession) obj).mId);
    }

    public int hashCode() {
        return Objects.hash(this.mId);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mClosed = true;
        this.mManager.releaseSessionId(this.mLogSessionId.getStringId());
    }
}
