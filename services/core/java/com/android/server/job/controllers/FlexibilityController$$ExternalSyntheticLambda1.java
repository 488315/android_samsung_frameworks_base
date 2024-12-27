package com.android.server.job.controllers;

import android.os.SystemClock;
import android.util.ArraySet;
import android.util.Slog;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.controllers.FlexibilityController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class FlexibilityController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FlexibilityController$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ArraySet arraySet;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                FlexibilityController flexibilityController = (FlexibilityController) obj;
                flexibilityController.getClass();
                ArraySet arraySet2 = new ArraySet();
                synchronized (flexibilityController.mLock) {
                    try {
                        JobSchedulerService.sElapsedRealtimeClock.getClass();
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        for (int i2 = 0; i2 < flexibilityController.mFlexibilityTracker.mTrackedJobs.size(); i2++) {
                            FlexibilityController.FlexibilityTracker flexibilityTracker = flexibilityController.mFlexibilityTracker;
                            if (i2 > flexibilityTracker.mTrackedJobs.size()) {
                                Slog.wtfStack("JobScheduler.Flex", "Asked for a larger number of constraints than exists.");
                                arraySet = null;
                            } else {
                                arraySet = (ArraySet) flexibilityTracker.mTrackedJobs.get(i2);
                            }
                            for (int size = arraySet.size() - 1; size >= 0; size--) {
                                JobStatus jobStatus = (JobStatus) arraySet.valueAt(size);
                                flexibilityController.mFlexibilityTracker.updateFlexibleConstraints(jobStatus, elapsedRealtime);
                                flexibilityController.mFlexibilityAlarmQueue.scheduleDropNumConstraintsAlarm(jobStatus, elapsedRealtime);
                                if (jobStatus.setConstraintSatisfied(2097152, elapsedRealtime, flexibilityController.isFlexibilitySatisfiedLocked(jobStatus))) {
                                    arraySet2.add(jobStatus);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (arraySet2.size() > 0) {
                    flexibilityController.mStateChangedListener.onControllerStateChanged(arraySet2);
                    return;
                }
                return;
            case 1:
                ((FlexibilityController.SpecialAppTracker) obj).updateCarrierPrivilegedCallbackRegistration();
                return;
            default:
                ((FlexibilityController.SpecialAppTracker) obj).updatePowerAllowlistCache();
                return;
        }
    }
}
