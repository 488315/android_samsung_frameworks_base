package android.security.keystore;

import java.security.ProviderException;

/* loaded from: classes3.dex */
public class BackendBusyException extends ProviderException {
    private final long mBackOffHintMillis;

    public BackendBusyException(long j) {
        super("The keystore backend has no operation slots available. Retry later.");
        if (j < 0) {
            throw new IllegalArgumentException("Back-off hint cannot be negative.");
        }
        this.mBackOffHintMillis = j;
    }

    public BackendBusyException(long j, String str) {
        super(str);
        if (j < 0) {
            throw new IllegalArgumentException("Back-off hint cannot be negative.");
        }
        this.mBackOffHintMillis = j;
    }

    public BackendBusyException(long j, String str, Throwable th) {
        super(str, th);
        if (j < 0) {
            throw new IllegalArgumentException("Back-off hint cannot be negative.");
        }
        this.mBackOffHintMillis = j;
    }

    public long getBackOffHintMillis() {
        return this.mBackOffHintMillis;
    }
}
