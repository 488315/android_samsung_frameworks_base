package android.media.metrics;

import java.util.Objects;

/* loaded from: classes3.dex */
public final class LogSessionId {
    public static final LogSessionId LOG_SESSION_ID_NONE = new LogSessionId("");
    private final String mSessionId;

    public LogSessionId(String str) {
        this.mSessionId = (String) Objects.requireNonNull(str);
    }

    public String getStringId() {
        return this.mSessionId;
    }

    public String toString() {
        return this.mSessionId;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.mSessionId, ((LogSessionId) obj).mSessionId);
    }

    public int hashCode() {
        return Objects.hash(this.mSessionId);
    }
}
