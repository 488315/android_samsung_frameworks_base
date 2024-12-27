package com.android.server.job.controllers;

import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticOutline0;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BatteryController extends RestrictingController {
    public static final boolean DEBUG;
    public final ArraySet mChangedJobs;
    public final FlexibilityController mFlexibilityController;
    public Boolean mLastReportedStatsdBatteryNotLow;
    public Boolean mLastReportedStatsdStablePower;
    public final ArraySet mTopStartedJobs;
    public final ArraySet mTrackedTasks;

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.Battery", 3);
    }

    public BatteryController(
            JobSchedulerService jobSchedulerService, FlexibilityController flexibilityController) {
        super(jobSchedulerService);
        this.mTrackedTasks = new ArraySet();
        this.mTopStartedJobs = new ArraySet();
        this.mChangedJobs = new ArraySet();
        this.mLastReportedStatsdBatteryNotLow = null;
        this.mLastReportedStatsdStablePower = null;
        this.mFlexibilityController = flexibilityController;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        StringBuilder sb = new StringBuilder("Stable power: ");
        JobSchedulerService jobSchedulerService = this.mService;
        StringBuilder m =
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        sb,
                        jobSchedulerService.isBatteryCharging()
                                && jobSchedulerService.isBatteryNotLow(),
                        indentingPrintWriter,
                        "Not low: ");
        m.append(jobSchedulerService.isBatteryNotLow());
        indentingPrintWriter.println(m.toString());
        for (int i = 0; i < this.mTrackedTasks.size(); i++) {
            JobStatus jobStatus = (JobStatus) this.mTrackedTasks.valueAt(i);
            if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                indentingPrintWriter.print("#");
                jobStatus.printUniqueId(indentingPrintWriter);
                indentingPrintWriter.print(" from ");
                UserHandle.formatUid(indentingPrintWriter, jobStatus.sourceUid);
                indentingPrintWriter.println();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            ProtoOutputStream protoOutputStream,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        long start = protoOutputStream.start(2246267895812L);
        long start2 = protoOutputStream.start(1146756268034L);
        JobSchedulerService jobSchedulerService = this.mService;
        protoOutputStream.write(
                1133871366145L,
                jobSchedulerService.isBatteryCharging() && jobSchedulerService.isBatteryNotLow());
        protoOutputStream.write(1133871366146L, jobSchedulerService.isBatteryNotLow());
        for (int i = 0; i < this.mTrackedTasks.size(); i++) {
            JobStatus jobStatus = (JobStatus) this.mTrackedTasks.valueAt(i);
            if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                long start3 = protoOutputStream.start(2246267895813L);
                jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1120986464258L, jobStatus.sourceUid);
                protoOutputStream.end(start3);
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    public ArraySet getTopStartedJobs() {
        return this.mTopStartedJobs;
    }

    public ArraySet getTrackedJobs() {
        return this.mTrackedTasks;
    }

    public final void maybeReportNewChargingStateLocked() {
        JobSchedulerService jobSchedulerService = this.mService;
        boolean isPowerConnected = jobSchedulerService.isPowerConnected();
        boolean z =
                jobSchedulerService.isBatteryCharging() && jobSchedulerService.isBatteryNotLow();
        boolean isBatteryNotLow = jobSchedulerService.isBatteryNotLow();
        if (DEBUG) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    "JobScheduler.Battery",
                    FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                            "maybeReportNewChargingStateLocked: ", isPowerConnected, "/", z, "/"),
                    isBatteryNotLow);
        }
        Boolean bool = this.mLastReportedStatsdStablePower;
        if (bool == null || bool.booleanValue() != z) {
            StateController.logDeviceWideConstraintStateToStatsd(1, z);
            this.mLastReportedStatsdStablePower = Boolean.valueOf(z);
        }
        Boolean bool2 = this.mLastReportedStatsdBatteryNotLow;
        if (bool2 == null || bool2.booleanValue() != isBatteryNotLow) {
            StateController.logDeviceWideConstraintStateToStatsd(2, isBatteryNotLow);
            this.mLastReportedStatsdBatteryNotLow = Boolean.valueOf(isBatteryNotLow);
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        boolean isBatteryCharging = jobSchedulerService.isBatteryCharging();
        FlexibilityController flexibilityController = this.mFlexibilityController;
        flexibilityController.setConstraintSatisfied(1, elapsedRealtime, isBatteryCharging);
        flexibilityController.setConstraintSatisfied(2, elapsedRealtime, isBatteryNotLow);
        for (int size = this.mTrackedTasks.size() - 1; size >= 0; size--) {
            JobStatus jobStatus = (JobStatus) this.mTrackedTasks.valueAt(size);
            if (jobStatus.hasConstraint(1)) {
                if ((jobSchedulerService.getUidBias(jobStatus.sourceUid) == 40
                                || this.mTopStartedJobs.contains(jobStatus))
                        && jobStatus.getEffectivePriority() >= 300) {
                    if (jobStatus.setConstraintSatisfied(1, elapsedRealtime, isPowerConnected)) {
                        this.mChangedJobs.add(jobStatus);
                    }
                } else if (jobStatus.setConstraintSatisfied(1, elapsedRealtime, z)) {
                    this.mChangedJobs.add(jobStatus);
                }
            }
            if (jobStatus.hasConstraint(2)
                    && jobStatus.setConstraintSatisfied(2, elapsedRealtime, isBatteryNotLow)) {
                this.mChangedJobs.add(jobStatus);
            }
        }
        JobSchedulerService jobSchedulerService2 = this.mStateChangedListener;
        if (z || isBatteryNotLow) {
            jobSchedulerService2.onRunJobNow(null);
        } else if (this.mChangedJobs.size() > 0) {
            jobSchedulerService2.onControllerStateChanged(this.mChangedJobs);
        }
        this.mChangedJobs.clear();
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.hasConstraint(3)) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.mTrackedTasks.add(jobStatus);
            jobStatus.setTrackingController(1);
            boolean hasConstraint = jobStatus.hasConstraint(1);
            JobSchedulerService jobSchedulerService = this.mService;
            if (hasConstraint) {
                if (jobSchedulerService.getUidBias(jobStatus.sourceUid) == 40
                        || this.mTopStartedJobs.contains(jobStatus)) {
                    jobStatus.setConstraintSatisfied(
                            1, elapsedRealtime, jobSchedulerService.isPowerConnected());
                } else {
                    jobStatus.setConstraintSatisfied(
                            1,
                            elapsedRealtime,
                            jobSchedulerService.isBatteryCharging()
                                    && jobSchedulerService.isBatteryNotLow());
                }
            }
            jobStatus.setConstraintSatisfied(
                    2, elapsedRealtime, jobSchedulerService.isBatteryNotLow());
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(1)) {
            this.mTrackedTasks.remove(jobStatus);
            this.mTopStartedJobs.remove(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onBatteryStateChangedLocked() {
        AppSchedulingModuleThread.getHandler()
                .post(
                        new Runnable() { // from class:
                                         // com.android.server.job.controllers.BatteryController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                BatteryController batteryController = BatteryController.this;
                                synchronized (batteryController.mLock) {
                                    batteryController.maybeReportNewChargingStateLocked();
                                }
                            }
                        });
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUidBiasChangedLocked(int i, int i2, int i3) {
        if (i2 == 40 || i3 == 40) {
            maybeReportNewChargingStateLocked();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void prepareForExecutionLocked(JobStatus jobStatus) {
        if (jobStatus.hasConstraint(3)) {
            boolean z = DEBUG;
            if (z) {
                Slog.d("JobScheduler.Battery", "Prepping for " + jobStatus.toShortString());
            }
            if (this.mService.getUidBias(jobStatus.sourceUid) == 40) {
                if (z) {
                    Slog.d(
                            "JobScheduler.Battery",
                            jobStatus.toShortString() + " is top started job");
                }
                this.mTopStartedJobs.add(jobStatus);
            }
        }
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public final void startTrackingRestrictedJobLocked(JobStatus jobStatus) {
        maybeStartTrackingJobLocked(jobStatus, null);
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public final void stopTrackingRestrictedJobLocked(JobStatus jobStatus) {
        if (jobStatus.hasConstraint(3)) {
            return;
        }
        maybeStopTrackingJobLocked(jobStatus, null);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void unprepareFromExecutionLocked(JobStatus jobStatus) {
        this.mTopStartedJobs.remove(jobStatus);
    }
}
