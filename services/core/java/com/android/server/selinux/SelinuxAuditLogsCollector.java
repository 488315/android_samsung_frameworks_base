package com.android.server.selinux;

import android.util.Log;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SelinuxAuditLogsCollector {
    public static final boolean DEBUG = Log.isLoggable("SelinuxAuditLogs", 3);
    static final Matcher SELINUX_MATCHER =
            Pattern.compile("^.*\\bavc:\\s+(?<denial>.*)$").matcher("");
    public final QuotaLimiter mQuotaLimiter;
    public final RateLimiter mRateLimiter;
    Instant mLastWrite = Instant.MIN;
    public final AtomicBoolean mStopRequested = new AtomicBoolean(false);

    public SelinuxAuditLogsCollector(RateLimiter rateLimiter, QuotaLimiter quotaLimiter) {
        this.mRateLimiter = rateLimiter;
        this.mQuotaLimiter = quotaLimiter;
    }
}
