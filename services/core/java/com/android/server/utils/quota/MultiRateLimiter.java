package com.android.server.utils.quota;

import android.content.Context;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public final class MultiRateLimiter {
    public static final CountQuotaTracker[] EMPTY_TRACKER_ARRAY = new CountQuotaTracker[0];
    public final Object mLock = new Object();
    public final CountQuotaTracker[] mQuotaTrackers;

    public final class Builder {
        public final Context mContext;
        public final QuotaTracker.Injector mInjector;
        public final List mQuotaTrackers = new ArrayList();
        public final Categorizer$$ExternalSyntheticLambda0 mCategorizer =
                Categorizer.SINGLE_CATEGORIZER;
        public final Category mCategory = Category.SINGLE_CATEGORY;

        public Builder(Context context, QuotaTracker.Injector injector) {
            this.mContext = context;
            this.mInjector = injector;
        }

        public final void addRateLimit(int i, Duration duration) {
            Categorizer$$ExternalSyntheticLambda0 categorizer$$ExternalSyntheticLambda0 =
                    this.mCategorizer;
            QuotaTracker.Injector injector = this.mInjector;
            CountQuotaTracker countQuotaTracker =
                    injector != null
                            ? new CountQuotaTracker(
                                    this.mContext, categorizer$$ExternalSyntheticLambda0, injector)
                            : new CountQuotaTracker(
                                    this.mContext, categorizer$$ExternalSyntheticLambda0);
            countQuotaTracker.setCountLimit(this.mCategory, i, duration.toMillis());
            ((ArrayList) this.mQuotaTrackers).add(countQuotaTracker);
        }
    }

    public final class RateLimit {
        public final int mLimit;
        public final Duration mWindowSize;

        public RateLimit(int i, Duration duration) {
            this.mLimit = i;
            this.mWindowSize = duration;
        }
    }

    public MultiRateLimiter(List list) {
        this.mQuotaTrackers = (CountQuotaTracker[]) list.toArray(EMPTY_TRACKER_ARRAY);
    }

    public final void clear(int i, String str) {
        synchronized (this.mLock) {
            for (CountQuotaTracker countQuotaTracker : this.mQuotaTrackers) {
                countQuotaTracker.onAppRemovedLocked(i, str);
            }
        }
    }

    public final boolean isWithinQuota(int i, String str, String str2) {
        boolean z;
        synchronized (this.mLock) {
            CountQuotaTracker[] countQuotaTrackerArr = this.mQuotaTrackers;
            int length = countQuotaTrackerArr.length;
            z = false;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z = true;
                    break;
                }
                if (!countQuotaTrackerArr[i2].isWithinQuota(i, str, str2)) {
                    break;
                }
                i2++;
            }
        }
        return z;
    }

    public final void noteEvent(int i, String str, String str2) {
        synchronized (this.mLock) {
            for (CountQuotaTracker countQuotaTracker : this.mQuotaTrackers) {
                countQuotaTracker.noteEvent(i, str, str2);
            }
        }
    }
}
