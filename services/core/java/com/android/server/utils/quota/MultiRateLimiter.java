package com.android.server.utils.quota;

import android.content.Context;
import com.android.server.utils.quota.QuotaTracker;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class MultiRateLimiter {
    public static final CountQuotaTracker[] EMPTY_TRACKER_ARRAY = new CountQuotaTracker[0];
    public final Object mLock;
    public final CountQuotaTracker[] mQuotaTrackers;

    public MultiRateLimiter(List list) {
        this.mLock = new Object();
        this.mQuotaTrackers = (CountQuotaTracker[]) list.toArray(EMPTY_TRACKER_ARRAY);
    }

    public void noteEvent(int i, String str, String str2) {
        synchronized (this.mLock) {
            noteEventLocked(i, str, str2);
        }
    }

    public boolean isWithinQuota(int i, String str, String str2) {
        boolean isWithinQuotaLocked;
        synchronized (this.mLock) {
            isWithinQuotaLocked = isWithinQuotaLocked(i, str, str2);
        }
        return isWithinQuotaLocked;
    }

    public void clear(int i, String str) {
        synchronized (this.mLock) {
            clearLocked(i, str);
        }
    }

    public final void noteEventLocked(int i, String str, String str2) {
        for (CountQuotaTracker countQuotaTracker : this.mQuotaTrackers) {
            countQuotaTracker.noteEvent(i, str, str2);
        }
    }

    public final boolean isWithinQuotaLocked(int i, String str, String str2) {
        for (CountQuotaTracker countQuotaTracker : this.mQuotaTrackers) {
            if (!countQuotaTracker.isWithinQuota(i, str, str2)) {
                return false;
            }
        }
        return true;
    }

    public final void clearLocked(int i, String str) {
        for (CountQuotaTracker countQuotaTracker : this.mQuotaTrackers) {
            countQuotaTracker.onAppRemovedLocked(i, str);
        }
    }

    public class Builder {
        public final Categorizer mCategorizer;
        public final Category mCategory;
        public final Context mContext;
        public final QuotaTracker.Injector mInjector;
        public final List mQuotaTrackers;

        public Builder(Context context, QuotaTracker.Injector injector) {
            this.mQuotaTrackers = new ArrayList();
            this.mContext = context;
            this.mInjector = injector;
            this.mCategorizer = Categorizer.SINGLE_CATEGORIZER;
            this.mCategory = Category.SINGLE_CATEGORY;
        }

        public Builder(Context context) {
            this(context, null);
        }

        public Builder addRateLimit(int i, Duration duration) {
            CountQuotaTracker countQuotaTracker;
            if (this.mInjector != null) {
                countQuotaTracker = new CountQuotaTracker(this.mContext, this.mCategorizer, this.mInjector);
            } else {
                countQuotaTracker = new CountQuotaTracker(this.mContext, this.mCategorizer);
            }
            countQuotaTracker.setCountLimit(this.mCategory, i, duration.toMillis());
            this.mQuotaTrackers.add(countQuotaTracker);
            return this;
        }

        public Builder addRateLimit(RateLimit rateLimit) {
            return addRateLimit(rateLimit.mLimit, rateLimit.mWindowSize);
        }

        public Builder addRateLimits(RateLimit[] rateLimitArr) {
            for (RateLimit rateLimit : rateLimitArr) {
                addRateLimit(rateLimit);
            }
            return this;
        }

        public MultiRateLimiter build() {
            return new MultiRateLimiter(this.mQuotaTrackers);
        }
    }

    public class RateLimit {
        public final int mLimit;
        public final Duration mWindowSize;

        public RateLimit(int i, Duration duration) {
            this.mLimit = i;
            this.mWindowSize = duration;
        }

        public static RateLimit create(int i, Duration duration) {
            return new RateLimit(i, duration);
        }
    }
}
