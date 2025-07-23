package android.media.metrics;

import android.annotation.NonNull;
import android.os.PersistableBundle;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class BundleSession implements AutoCloseable {
    public static final String KEY_STATSD_ATOM = "bundlesession-statsd-atom";
    private final String mId;
    private final LogSessionId mLogSessionId;
    private final MediaMetricsManager mManager;

    public BundleSession(String str, MediaMetricsManager mediaMetricsManager) {
        this.mId = str;
        this.mManager = mediaMetricsManager;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) mediaMetricsManager);
        this.mLogSessionId = new LogSessionId(str);
    }

    public void reportBundleMetrics(PersistableBundle persistableBundle) {
        this.mManager.reportBundleMetrics(this.mId, persistableBundle);
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
        return Objects.equals(this.mId, ((BundleSession) obj).mId);
    }

    public int hashCode() {
        return Objects.hash(this.mId);
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.mManager.releaseSessionId(this.mLogSessionId.getStringId());
    }
}
