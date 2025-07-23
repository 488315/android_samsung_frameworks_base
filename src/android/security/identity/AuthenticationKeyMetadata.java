package android.security.identity;

import java.time.Instant;

/* loaded from: classes3.dex */
public final class AuthenticationKeyMetadata {
    private Instant mExpirationDate;
    private int mUsageCount;

    AuthenticationKeyMetadata(int i, Instant instant) {
        this.mUsageCount = i;
        this.mExpirationDate = instant;
    }

    public int getUsageCount() {
        return this.mUsageCount;
    }

    public Instant getExpirationDate() {
        return this.mExpirationDate;
    }
}
