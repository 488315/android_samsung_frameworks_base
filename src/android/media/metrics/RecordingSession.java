package android.media.metrics;

import android.annotation.NonNull;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class RecordingSession implements AutoCloseable {
    private boolean mClosed = false;
    private final String mId;
    private final LogSessionId mLogSessionId;
    private final MediaMetricsManager mManager;

    public RecordingSession(String str, MediaMetricsManager mediaMetricsManager) {
        this.mId = str;
        this.mManager = mediaMetricsManager;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) mediaMetricsManager);
        this.mLogSessionId = new LogSessionId(str);
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
        return Objects.equals(this.mId, ((RecordingSession) obj).mId);
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
