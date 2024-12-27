package com.android.server.utils.quota;

import android.util.ArraySet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class QuotaTracker$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ QuotaTracker f$0;

    public /* synthetic */ QuotaTracker$$ExternalSyntheticLambda0(QuotaTracker quotaTracker) {
        this.f$0 = quotaTracker;
    }

    public /* synthetic */ QuotaTracker$$ExternalSyntheticLambda0(
            QuotaTracker quotaTracker, int i, String str, String str2) {
        this.f$0 = quotaTracker;
    }

    @Override // java.lang.Runnable
    public final void run() {
        QuotaChangeListener[] quotaChangeListenerArr;
        int i = this.$r8$classId;
        QuotaTracker quotaTracker = this.f$0;
        switch (i) {
            case 0:
                synchronized (quotaTracker.mLock) {
                    ArraySet arraySet = quotaTracker.mQuotaChangeListeners;
                    quotaChangeListenerArr =
                            (QuotaChangeListener[])
                                    arraySet.toArray(new QuotaChangeListener[arraySet.size()]);
                }
                if (quotaChangeListenerArr.length <= 0) {
                    return;
                }
                QuotaChangeListener quotaChangeListener = quotaChangeListenerArr[0];
                throw null;
            default:
                synchronized (quotaTracker.mLock) {
                    if (quotaTracker.mQuotaChangeListeners.size() > 0) {
                        CountQuotaTracker countQuotaTracker = (CountQuotaTracker) quotaTracker;
                        countQuotaTracker.mEventTimes.forEach(
                                new CountQuotaTracker$$ExternalSyntheticLambda5(
                                        countQuotaTracker, new UptcMap(), 1));
                    }
                }
                return;
        }
    }
}
