package android.security.identity;

import java.time.Instant;

public final class AuthenticationKeyMetadata {
    private Instant mExpirationDate;
    private int mUsageCount;

    AuthenticationKeyMetadata(int usageCount, Instant expirationDate) {
        this.mUsageCount = usageCount;
        this.mExpirationDate = expirationDate;
    }

    public int getUsageCount() {
        return this.mUsageCount;
    }

    public Instant getExpirationDate() {
        return this.mExpirationDate;
    }
}
