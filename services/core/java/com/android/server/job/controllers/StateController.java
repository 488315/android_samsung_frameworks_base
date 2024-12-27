package com.android.server.job.controllers;

import android.content.Context;
import android.provider.DeviceConfig;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;
import com.android.server.job.restrictions.JobRestriction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class StateController {
    public final JobSchedulerService.Constants mConstants;
    public final Context mContext;
    public final Object mLock;
    public final JobSchedulerService mService;
    public final JobSchedulerService mStateChangedListener;

    public StateController(JobSchedulerService jobSchedulerService) {
        this.mService = jobSchedulerService;
        this.mStateChangedListener = jobSchedulerService;
        this.mContext = jobSchedulerService.getContext();
        this.mLock = jobSchedulerService.mLock;
        this.mConstants = jobSchedulerService.mConstants;
    }

    public static void logDeviceWideConstraintStateToStatsd(int i, boolean z) {
        int i2;
        boolean z2 = JobStatus.DEBUG;
        if (i == 1) {
            i2 = 1;
        } else if (i != 2) {
            switch (i) {
                case Integer.MIN_VALUE:
                    i2 = 4;
                    break;
                case 4:
                    i2 = 6;
                    break;
                case 8:
                    i2 = 3;
                    break;
                case 2097152:
                    i2 = 15;
                    break;
                case 4194304:
                    i2 = 11;
                    break;
                case 8388608:
                    i2 = 14;
                    break;
                case 16777216:
                    i2 = 10;
                    break;
                case 33554432:
                    i2 = 9;
                    break;
                case 67108864:
                    i2 = 8;
                    break;
                case 268435456:
                    i2 = 7;
                    break;
                case 1073741824:
                    i2 = 5;
                    break;
                default:
                    i2 = 0;
                    break;
            }
        } else {
            i2 = 2;
        }
        FrameworkStatsLog.write(
                FrameworkStatsLog.DEVICE_WIDE_JOB_CONSTRAINT_CHANGED, i2, z ? 2 : 1);
    }

    public static String packageToString(int i, String str) {
        return AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, "<", ">", str);
    }

    public void dumpConstants(IndentingPrintWriter indentingPrintWriter) {}

    public void dumpConstants(ProtoOutputStream protoOutputStream) {}

    public abstract void dumpControllerStateLocked(
            IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5);

    public void dumpControllerStateLocked(
            ProtoOutputStream protoOutputStream,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {}

    public void evaluateStateLocked(JobStatus jobStatus) {}

    public abstract void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2);

    public abstract void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2);

    public void onAppRemovedLocked(int i, String str) {}

    public void onBatteryStateChangedLocked() {}

    public void onConstantsUpdatedLocked() {}

    public void onSystemServicesReady() {}

    public void onUidBiasChangedLocked(int i, int i2, int i3) {}

    public void onUserAddedLocked(int i) {}

    public void onUserRemovedLocked(int i) {}

    public void prepareForExecutionLocked(JobStatus jobStatus) {}

    public void prepareForUpdatedConstantsLocked() {}

    public void processConstantLocked(DeviceConfig.Properties properties, String str) {}

    public void reevaluateStateLocked(int i) {}

    public void rescheduleForFailureLocked(JobStatus jobStatus, JobStatus jobStatus2) {}

    public void startTrackingLocked() {}

    public void unprepareFromExecutionLocked(JobStatus jobStatus) {}

    public final boolean wouldBeReadyWithConstraintLocked(JobStatus jobStatus, int i) {
        boolean readinessStatusWithConstraint = jobStatus.readinessStatusWithConstraint(i, true);
        boolean z = JobSchedulerService.DEBUG;
        if (z) {
            StringBuilder sb = new StringBuilder("wouldBeReadyWithConstraintLocked: ");
            sb.append(jobStatus.toShortString());
            sb.append(" constraint=");
            sb.append(i);
            sb.append(" readyWithConstraint=");
            ProxyManager$$ExternalSyntheticOutline0.m(
                    "JobScheduler.SC", sb, readinessStatusWithConstraint);
        }
        if (!readinessStatusWithConstraint) {
            return false;
        }
        JobSchedulerService jobSchedulerService = this.mService;
        boolean containsJob = jobSchedulerService.mJobs.containsJob(jobStatus);
        boolean areUsersStartedLocked = jobSchedulerService.areUsersStartedLocked(jobStatus);
        boolean z2 = jobSchedulerService.mBackingUpUids.get(jobStatus.sourceUid);
        if (z) {
            Slog.v(
                    "JobScheduler",
                    "areComponentsInPlaceLocked: "
                            + jobStatus.toShortString()
                            + " exists="
                            + containsJob
                            + " userStarted="
                            + areUsersStartedLocked
                            + " backingUp="
                            + z2);
        }
        if (!containsJob || !areUsersStartedLocked || z2) {
            return false;
        }
        JobRestriction checkIfRestricted = jobSchedulerService.checkIfRestricted(jobStatus);
        if (checkIfRestricted == null) {
            return jobSchedulerService.isComponentUsable(jobStatus);
        }
        if (!z) {
            return false;
        }
        StringBuilder sb2 = new StringBuilder("areComponentsInPlaceLocked: ");
        sb2.append(jobStatus.toShortString());
        sb2.append(" restricted due to ");
        GmsAlarmManager$$ExternalSyntheticOutline0.m(
                sb2, checkIfRestricted.mInternalReason, "JobScheduler");
        return false;
    }
}
