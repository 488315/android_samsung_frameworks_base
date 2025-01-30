package com.android.server.am;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.modules.expresslog.Counter;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;

/* loaded from: classes.dex */
public class DropboxRateLimiter {
    public final Clock mClock;
    public final ArrayMap mErrorClusterRecords;
    public long mLastMapCleanUp;

    public interface Clock {
        long uptimeMillis();
    }

    public DropboxRateLimiter() {
        this(new DefaultClock());
    }

    public DropboxRateLimiter(Clock clock) {
        this.mErrorClusterRecords = new ArrayMap();
        this.mLastMapCleanUp = 0L;
        this.mClock = clock;
    }

    public RateLimitResult shouldRateLimit(String str, String str2) {
        long uptimeMillis = this.mClock.uptimeMillis();
        synchronized (this.mErrorClusterRecords) {
            maybeRemoveExpiredRecords(uptimeMillis);
            ErrorRecord errorRecord = (ErrorRecord) this.mErrorClusterRecords.get(errorKey(str, str2));
            if (errorRecord == null) {
                this.mErrorClusterRecords.put(errorKey(str, str2), new ErrorRecord(uptimeMillis, 1));
                return new RateLimitResult(false, 0);
            }
            long startTime = uptimeMillis - errorRecord.getStartTime();
            if (startTime > errorRecord.getBufferDuration()) {
                int recentlyDroppedCount = recentlyDroppedCount(errorRecord);
                errorRecord.setStartTime(uptimeMillis);
                errorRecord.setCount(1);
                if (recentlyDroppedCount > 0 && startTime < errorRecord.getBufferDuration() * 2) {
                    errorRecord.incrementSuccessiveRateLimitCycles();
                } else {
                    errorRecord.setSuccessiveRateLimitCycles(0);
                }
                return new RateLimitResult(false, recentlyDroppedCount);
            }
            errorRecord.incrementCount();
            if (errorRecord.getCount() > errorRecord.getAllowedEntries()) {
                return new RateLimitResult(true, recentlyDroppedCount(errorRecord));
            }
            return new RateLimitResult(false, 0);
        }
    }

    public final int recentlyDroppedCount(ErrorRecord errorRecord) {
        if (errorRecord == null || errorRecord.getCount() < errorRecord.getAllowedEntries()) {
            return 0;
        }
        return errorRecord.getCount() - errorRecord.getAllowedEntries();
    }

    public final void maybeRemoveExpiredRecords(long j) {
        if (j - this.mLastMapCleanUp <= 1800000) {
            return;
        }
        for (int size = this.mErrorClusterRecords.size() - 1; size >= 0; size--) {
            if (((ErrorRecord) this.mErrorClusterRecords.valueAt(size)).hasExpired(j)) {
                Counter.logIncrement("stability_errors.value_dropbox_buffer_expired_count", ((ErrorRecord) this.mErrorClusterRecords.valueAt(size)).getCount());
                this.mErrorClusterRecords.removeAt(size);
            }
        }
        this.mLastMapCleanUp = j;
    }

    public void reset() {
        synchronized (this.mErrorClusterRecords) {
            this.mErrorClusterRecords.clear();
        }
        this.mLastMapCleanUp = 0L;
        Slog.i("DropboxRateLimiter", "Rate limiter reset.");
    }

    public String errorKey(String str, String str2) {
        return str + str2;
    }

    public class RateLimitResult {
        public final int mDroppedCountSinceRateLimitActivated;
        public final boolean mShouldRateLimit;

        public RateLimitResult(boolean z, int i) {
            this.mShouldRateLimit = z;
            this.mDroppedCountSinceRateLimitActivated = i;
        }

        public boolean shouldRateLimit() {
            return this.mShouldRateLimit;
        }

        public int droppedCountSinceRateLimitActivated() {
            return this.mDroppedCountSinceRateLimitActivated;
        }

        public String createHeader() {
            return "Dropped-Count: " + this.mDroppedCountSinceRateLimitActivated + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
        }
    }

    public class ErrorRecord {
        public int mCount;
        public long mStartTime;
        public int mSuccessiveRateLimitCycles = 0;

        public ErrorRecord(long j, int i) {
            this.mStartTime = j;
            this.mCount = i;
        }

        public void setStartTime(long j) {
            this.mStartTime = j;
        }

        public void setCount(int i) {
            this.mCount = i;
        }

        public void incrementCount() {
            this.mCount++;
        }

        public void setSuccessiveRateLimitCycles(int i) {
            this.mSuccessiveRateLimitCycles = i;
        }

        public void incrementSuccessiveRateLimitCycles() {
            this.mSuccessiveRateLimitCycles++;
        }

        public long getStartTime() {
            return this.mStartTime;
        }

        public int getCount() {
            return this.mCount;
        }

        public boolean isRepeated() {
            return this.mSuccessiveRateLimitCycles >= 2;
        }

        public int getAllowedEntries() {
            return isRepeated() ? 1 : 6;
        }

        public long getBufferDuration() {
            return isRepeated() ? 1200000L : 600000L;
        }

        public boolean hasExpired(long j) {
            return j - this.mStartTime > getBufferDuration() * 3;
        }
    }

    public class DefaultClock implements Clock {
        public DefaultClock() {
        }

        @Override // com.android.server.am.DropboxRateLimiter.Clock
        public long uptimeMillis() {
            return SystemClock.uptimeMillis();
        }
    }
}
