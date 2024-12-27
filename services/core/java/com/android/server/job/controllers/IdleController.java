package com.android.server.job.controllers;

import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.proto.ProtoOutputStream;

import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;
import com.android.server.job.controllers.idle.CarIdlenessTracker;
import com.android.server.job.controllers.idle.DeviceIdlenessTracker;
import com.android.server.job.controllers.idle.IdlenessTracker;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IdleController extends RestrictingController {
    public final FlexibilityController mFlexibilityController;
    public final IdlenessTracker mIdleTracker;
    public final ArraySet mTrackedTasks;

    public IdleController(
            JobSchedulerService jobSchedulerService, FlexibilityController flexibilityController) {
        super(jobSchedulerService);
        this.mTrackedTasks = new ArraySet();
        if (this.mContext
                .getPackageManager()
                .hasSystemFeature("android.hardware.type.automotive")) {
            CarIdlenessTracker carIdlenessTracker = new CarIdlenessTracker();
            carIdlenessTracker.mIdle = false;
            carIdlenessTracker.mGarageModeOn = false;
            carIdlenessTracker.mForced = false;
            carIdlenessTracker.mScreenOn = true;
            this.mIdleTracker = carIdlenessTracker;
        } else {
            this.mIdleTracker = new DeviceIdlenessTracker();
        }
        this.mFlexibilityController = flexibilityController;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpConstants(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println();
        indentingPrintWriter.println("IdleController:");
        indentingPrintWriter.increaseIndent();
        this.mIdleTracker.dumpConstants(indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        indentingPrintWriter.println("Currently idle: " + this.mIdleTracker.isIdle());
        indentingPrintWriter.println("Idleness tracker:");
        this.mIdleTracker.dump((PrintWriter) indentingPrintWriter);
        indentingPrintWriter.println();
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
        long start2 = protoOutputStream.start(1146756268038L);
        protoOutputStream.write(1133871366145L, this.mIdleTracker.isIdle());
        this.mIdleTracker.dump(protoOutputStream);
        for (int i = 0; i < this.mTrackedTasks.size(); i++) {
            JobStatus jobStatus = (JobStatus) this.mTrackedTasks.valueAt(i);
            if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                long start3 = protoOutputStream.start(2246267895810L);
                jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1120986464258L, jobStatus.sourceUid);
                protoOutputStream.end(start3);
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.hasConstraint(4)) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.mTrackedTasks.add(jobStatus);
            jobStatus.setTrackingController(8);
            jobStatus.setConstraintSatisfied(4, elapsedRealtime, this.mIdleTracker.isIdle());
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(8)) {
            this.mTrackedTasks.remove(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onBatteryStateChangedLocked() {
        IdlenessTracker idlenessTracker = this.mIdleTracker;
        JobSchedulerService jobSchedulerService = this.mService;
        idlenessTracker.onBatteryStateChanged(
                jobSchedulerService.isBatteryCharging(), jobSchedulerService.isBatteryNotLow());
    }

    @Override // com.android.server.job.controllers.StateController
    public final void processConstantLocked(DeviceConfig.Properties properties, String str) {
        this.mIdleTracker.processConstant(properties, str);
    }

    public final void reportNewIdleState(boolean z) {
        synchronized (this.mLock) {
            try {
                StateController.logDeviceWideConstraintStateToStatsd(4, z);
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                this.mFlexibilityController.setConstraintSatisfied(4, elapsedRealtime, z);
                for (int size = this.mTrackedTasks.size() - 1; size >= 0; size--) {
                    ((JobStatus) this.mTrackedTasks.valueAt(size))
                            .setConstraintSatisfied(4, elapsedRealtime, z);
                }
                if (!this.mTrackedTasks.isEmpty()) {
                    this.mStateChangedListener.onControllerStateChanged(this.mTrackedTasks);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void startTrackingLocked() {
        this.mIdleTracker.startTracking(this.mContext, this.mService, this);
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public final void startTrackingRestrictedJobLocked(JobStatus jobStatus) {
        maybeStartTrackingJobLocked(jobStatus, null);
    }

    @Override // com.android.server.job.controllers.RestrictingController
    public final void stopTrackingRestrictedJobLocked(JobStatus jobStatus) {
        if (jobStatus.hasConstraint(4)) {
            return;
        }
        maybeStopTrackingJobLocked(jobStatus, null);
    }
}
