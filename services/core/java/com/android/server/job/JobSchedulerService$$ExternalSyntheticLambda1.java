package com.android.server.job;

import com.android.server.AppStateTrackerImpl;
import com.android.server.job.controllers.JobStatus;

import java.util.function.Predicate;

public final /* synthetic */ class JobSchedulerService$$ExternalSyntheticLambda1
        implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ JobSchedulerService f$0;

    public /* synthetic */ JobSchedulerService$$ExternalSyntheticLambda1(
            JobSchedulerService jobSchedulerService, int i) {
        this.$r8$classId = i;
        this.f$0 = jobSchedulerService;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        JobSchedulerService jobSchedulerService = this.f$0;
        switch (i) {
            case 0:
                int intValue = ((Integer) obj).intValue();
                AppStateTrackerImpl appStateTrackerImpl = jobSchedulerService.mAppStateTracker;
                if (appStateTrackerImpl.isUidActive(intValue)) {
                    return true;
                }
                long time = appStateTrackerImpl.mStatLogger.getTime();
                boolean isUidActive =
                        appStateTrackerImpl.mActivityManagerInternal.isUidActive(intValue);
                appStateTrackerImpl.mStatLogger.logDurationStat(9, time);
                return isUidActive;
            default:
                return jobSchedulerService.isCurrentlyRunningLocked((JobStatus) obj);
        }
    }
}
