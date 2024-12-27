package com.android.server.am;

import android.os.SystemClock;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.modules.expresslog.Counter;

public final class DropboxRateLimiter {
    public final DefaultClock mClock;
    public final ArrayMap mErrorClusterRecords;
    public long mLastMapCleanUp;
    public int mRateLimitAllowedEntries;
    public long mRateLimitBufferDuration;
    public long mRateLimitBufferExpiryFactor;
    public long mStrictRateLimitBufferDuration;
    public int mStrictRatelimitAllowedEntries;

    public final class DefaultClock {}

    public final class ErrorRecord {
        public long mStartTime;
        public int mCount = 1;
        public int mSuccessiveRateLimitCycles = 0;

        public ErrorRecord(long j) {
            this.mStartTime = j;
        }
    }

    public final class RateLimitResult {
        public final int mDroppedCountSinceRateLimitActivated;
        public final boolean mShouldRateLimit;

        public RateLimitResult(int i, boolean z) {
            this.mShouldRateLimit = z;
            this.mDroppedCountSinceRateLimitActivated = i;
        }
    }

    public DropboxRateLimiter() {
        DefaultClock defaultClock = new DefaultClock();
        this.mErrorClusterRecords = new ArrayMap();
        this.mLastMapCleanUp = 0L;
        this.mClock = defaultClock;
        this.mRateLimitBufferDuration = 600000L;
        this.mRateLimitBufferExpiryFactor = 3L;
        this.mRateLimitAllowedEntries = 6;
        this.mStrictRatelimitAllowedEntries = 1;
        this.mStrictRateLimitBufferDuration = 1200000L;
    }

    public final void init() {
        this.mRateLimitBufferDuration =
                DeviceConfig.getLong(
                        "dropbox", "DropboxRateLimiter__rate_limit_buffer_duration", 600000L);
        this.mRateLimitBufferExpiryFactor =
                DeviceConfig.getLong(
                        "dropbox", "DropboxRateLimiter__rate_limit_buffer_expiry_factor", 3L);
        this.mRateLimitAllowedEntries =
                DeviceConfig.getInt("dropbox", "DropboxRateLimiter__rate_limit_allowed_entries", 6);
        this.mStrictRatelimitAllowedEntries =
                DeviceConfig.getInt(
                        "dropbox", "DropboxRateLimiter__strict_rate_limit_allowed_entries", 1);
        this.mStrictRateLimitBufferDuration =
                DeviceConfig.getLong(
                        "dropbox",
                        "DropboxRateLimiter__strict_rate_limit_buffer_duration",
                        1200000L);
    }

    public final void maybeRemoveExpiredRecords(long j) {
        if (j - this.mLastMapCleanUp
                <= this.mRateLimitBufferExpiryFactor * this.mRateLimitBufferDuration) {
            return;
        }
        for (int size = this.mErrorClusterRecords.size() - 1; size >= 0; size--) {
            ErrorRecord errorRecord = (ErrorRecord) this.mErrorClusterRecords.valueAt(size);
            DropboxRateLimiter dropboxRateLimiter = DropboxRateLimiter.this;
            if (j - errorRecord.mStartTime
                    > (errorRecord.mSuccessiveRateLimitCycles >= 2
                                    ? dropboxRateLimiter.mStrictRateLimitBufferDuration
                                    : dropboxRateLimiter.mRateLimitBufferDuration)
                            * dropboxRateLimiter.mRateLimitBufferExpiryFactor) {
                Counter.logIncrement(
                        "stability_errors.value_dropbox_buffer_expired_count",
                        ((ErrorRecord) this.mErrorClusterRecords.valueAt(size)).mCount);
                this.mErrorClusterRecords.removeAt(size);
            }
        }
        this.mLastMapCleanUp = j;
    }

    public final void reset() {
        synchronized (this.mErrorClusterRecords) {
            this.mErrorClusterRecords.clear();
        }
        this.mLastMapCleanUp = 0L;
        Slog.i("DropboxRateLimiter", "Rate limiter reset.");
    }

    public final RateLimitResult shouldRateLimit(String str, String str2) {
        int i;
        this.mClock.getClass();
        long uptimeMillis = SystemClock.uptimeMillis();
        synchronized (this.mErrorClusterRecords) {
            try {
                maybeRemoveExpiredRecords(uptimeMillis);
                ErrorRecord errorRecord = (ErrorRecord) this.mErrorClusterRecords.get(str + str2);
                int i2 = 0;
                if (errorRecord == null) {
                    ErrorRecord errorRecord2 = new ErrorRecord(uptimeMillis);
                    this.mErrorClusterRecords.put(str + str2, errorRecord2);
                    return new RateLimitResult(0, false);
                }
                long j = uptimeMillis - errorRecord.mStartTime;
                int i3 = errorRecord.mSuccessiveRateLimitCycles;
                boolean z = i3 >= 2;
                DropboxRateLimiter dropboxRateLimiter = DropboxRateLimiter.this;
                if (j
                        <= (z
                                ? dropboxRateLimiter.mStrictRateLimitBufferDuration
                                : dropboxRateLimiter.mRateLimitBufferDuration)) {
                    int i4 = errorRecord.mCount + 1;
                    errorRecord.mCount = i4;
                    if (i4
                            <= (i3 >= 2
                                    ? dropboxRateLimiter.mStrictRatelimitAllowedEntries
                                    : dropboxRateLimiter.mRateLimitAllowedEntries)) {
                        return new RateLimitResult(0, false);
                    }
                    if (i4
                            >= (i3 >= 2
                                    ? dropboxRateLimiter.mStrictRatelimitAllowedEntries
                                    : dropboxRateLimiter.mRateLimitAllowedEntries)) {
                        i2 =
                                i4
                                        - (i3 >= 2
                                                ? dropboxRateLimiter.mStrictRatelimitAllowedEntries
                                                : dropboxRateLimiter.mRateLimitAllowedEntries);
                    }
                    return new RateLimitResult(i2, true);
                }
                int i5 = errorRecord.mCount;
                if (i5
                        < (i3 >= 2
                                ? dropboxRateLimiter.mStrictRatelimitAllowedEntries
                                : dropboxRateLimiter.mRateLimitAllowedEntries)) {
                    i = 0;
                } else {
                    i =
                            i5
                                    - (i3 >= 2
                                            ? dropboxRateLimiter.mStrictRatelimitAllowedEntries
                                            : dropboxRateLimiter.mRateLimitAllowedEntries);
                }
                errorRecord.mStartTime = uptimeMillis;
                errorRecord.mCount = 1;
                if (i > 0) {
                    if (j
                            < (i3 >= 2
                                            ? dropboxRateLimiter.mStrictRateLimitBufferDuration
                                            : dropboxRateLimiter.mRateLimitBufferDuration)
                                    * 2) {
                        errorRecord.mSuccessiveRateLimitCycles = i3 + 1;
                        return new RateLimitResult(i, false);
                    }
                }
                errorRecord.mSuccessiveRateLimitCycles = 0;
                return new RateLimitResult(i, false);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
