package com.android.server.job.controllers;

import android.app.AlarmManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.WorkSource;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.modules.expresslog.Counter;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TimeController extends StateController {
    public static final boolean DEBUG;
    static final long DELAY_COALESCE_TIME_MS = 30000;
    public AlarmManager mAlarmService;
    public final AnonymousClass2 mDeadlineExpiredListener;
    public volatile long mLastFiredDelayExpiredElapsedMillis;
    public long mNextDelayExpiredElapsedMillis;
    public final AnonymousClass2 mNextDelayExpiredListener;
    public long mNextJobExpiredElapsedMillis;
    public final PriorityQueue mTrackedJobs;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.job.controllers.TimeController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Long.compare(
                    ((JobStatus) obj).latestRunTimeElapsedMillis,
                    ((JobStatus) obj2).latestRunTimeElapsedMillis);
        }
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.Time", 3);
    }

    /* JADX WARN: Type inference failed for: r3v3, types: [com.android.server.job.controllers.TimeController$2] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.server.job.controllers.TimeController$2] */
    public TimeController(JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mAlarmService = null;
        this.mTrackedJobs = new PriorityQueue(new AnonymousClass1());
        final int i = 0;
        this.mDeadlineExpiredListener =
                new AlarmManager.OnAlarmListener(
                        this) { // from class: com.android.server.job.controllers.TimeController.2
                    public final /* synthetic */ TimeController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        switch (i) {
                            case 0:
                                if (TimeController.DEBUG) {
                                    Slog.d("JobScheduler.Time", "Deadline-expired alarm fired");
                                }
                                this.this$0.checkExpiredDeadlinesAndResetAlarm();
                                break;
                            default:
                                if (TimeController.DEBUG) {
                                    Slog.d("JobScheduler.Time", "Delay-expired alarm fired");
                                }
                                TimeController timeController = this.this$0;
                                JobSchedulerService.sElapsedRealtimeClock.getClass();
                                timeController.mLastFiredDelayExpiredElapsedMillis =
                                        SystemClock.elapsedRealtime();
                                this.this$0.checkExpiredDelaysAndResetAlarm();
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mNextDelayExpiredListener =
                new AlarmManager.OnAlarmListener(
                        this) { // from class: com.android.server.job.controllers.TimeController.2
                    public final /* synthetic */ TimeController this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.app.AlarmManager.OnAlarmListener
                    public final void onAlarm() {
                        switch (i2) {
                            case 0:
                                if (TimeController.DEBUG) {
                                    Slog.d("JobScheduler.Time", "Deadline-expired alarm fired");
                                }
                                this.this$0.checkExpiredDeadlinesAndResetAlarm();
                                break;
                            default:
                                if (TimeController.DEBUG) {
                                    Slog.d("JobScheduler.Time", "Delay-expired alarm fired");
                                }
                                TimeController timeController = this.this$0;
                                JobSchedulerService.sElapsedRealtimeClock.getClass();
                                timeController.mLastFiredDelayExpiredElapsedMillis =
                                        SystemClock.elapsedRealtime();
                                this.this$0.checkExpiredDelaysAndResetAlarm();
                                break;
                        }
                    }
                };
        this.mNextJobExpiredElapsedMillis = Long.MAX_VALUE;
        this.mNextDelayExpiredElapsedMillis = Long.MAX_VALUE;
    }

    public static boolean canStopTrackingJobLocked(JobStatus jobStatus) {
        return (!jobStatus.hasConstraint(Integer.MIN_VALUE)
                        || jobStatus.isConstraintSatisfied(Integer.MIN_VALUE))
                && (!jobStatus.hasConstraint(1073741824)
                        || jobStatus.isConstraintSatisfied(1073741824));
    }

    public static boolean evaluateDeadlineConstraint(JobStatus jobStatus, long j) {
        boolean z = false;
        if (jobStatus.latestRunTimeElapsedMillis > j) {
            return false;
        }
        if (jobStatus.hasConstraint(Integer.MIN_VALUE)) {
            jobStatus.setConstraintSatisfied(Integer.MIN_VALUE, j, true);
        }
        if (jobStatus.setConstraintSatisfied(1073741824, j, true)) {
            if (!jobStatus.job.isPeriodic() && jobStatus.hasConstraint(1073741824)) {
                z = true;
            }
            jobStatus.mReadyDeadlineSatisfied = z;
        }
        return true;
    }

    public void checkExpiredDeadlinesAndResetAlarm() {
        long j;
        int i;
        String str;
        synchronized (this.mLock) {
            try {
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Iterator it = this.mTrackedJobs.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        j = Long.MAX_VALUE;
                        i = 0;
                        str = null;
                        break;
                    }
                    JobStatus jobStatus = (JobStatus) it.next();
                    if (jobStatus.hasConstraint(1073741824)) {
                        if (!evaluateDeadlineConstraint(jobStatus, elapsedRealtime)) {
                            if (wouldBeReadyWithConstraintLocked(jobStatus, 1073741824)) {
                                j = jobStatus.latestRunTimeElapsedMillis;
                                i = jobStatus.sourceUid;
                                str = jobStatus.sourcePackageName;
                                break;
                            } else if (DEBUG) {
                                Slog.i(
                                        "JobScheduler.Time",
                                        "Skipping "
                                                + jobStatus
                                                + " because deadline won't make it ready.");
                            }
                        } else {
                            if (jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest)) {
                                this.mStateChangedListener.onRunJobNow(jobStatus);
                            }
                            Counter.logIncrement(
                                    "job_scheduler.value_job_scheduler_job_deadline_expired_counter");
                            it.remove();
                        }
                    }
                }
                setDeadlineExpiredAlarmLocked(j, this.mService.deriveWorkSource(i, str));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void checkExpiredDelaysAndResetAlarm() {
        synchronized (this.mLock) {
            try {
                ArraySet arraySet = new ArraySet();
                Iterator it = this.mTrackedJobs.iterator();
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                long j = Long.MAX_VALUE;
                int i = 0;
                String str = null;
                while (it.hasNext()) {
                    JobStatus jobStatus = (JobStatus) it.next();
                    if (jobStatus.hasConstraint(Integer.MIN_VALUE)) {
                        if (jobStatus.earliestRunTimeElapsedMillis <= elapsedRealtime) {
                            jobStatus.setConstraintSatisfied(
                                    Integer.MIN_VALUE, elapsedRealtime, true);
                            if (canStopTrackingJobLocked(jobStatus)) {
                                it.remove();
                            }
                            arraySet.add(jobStatus);
                        } else if (wouldBeReadyWithConstraintLocked(jobStatus, Integer.MIN_VALUE)) {
                            long j2 = jobStatus.earliestRunTimeElapsedMillis;
                            if (j > j2) {
                                i = jobStatus.sourceUid;
                                str = jobStatus.sourcePackageName;
                                j = j2;
                            }
                        } else if (DEBUG) {
                            Slog.i(
                                    "JobScheduler.Time",
                                    "Skipping "
                                            + jobStatus
                                            + " because delay won't make it ready.");
                        }
                    }
                }
                if (arraySet.size() > 0) {
                    this.mStateChangedListener.onControllerStateChanged(arraySet);
                }
                setDelayExpiredAlarmLocked(j, this.mService.deriveWorkSource(i, str));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        indentingPrintWriter.println("Elapsed clock: " + elapsedRealtime);
        indentingPrintWriter.print("Next delay alarm in ");
        TimeUtils.formatDuration(
                this.mNextDelayExpiredElapsedMillis, elapsedRealtime, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Last delay alarm fired @ ");
        TimeUtils.formatDuration(
                elapsedRealtime, this.mLastFiredDelayExpiredElapsedMillis, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.print("Next deadline alarm in ");
        TimeUtils.formatDuration(
                this.mNextJobExpiredElapsedMillis, elapsedRealtime, indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.println();
        Iterator it = this.mTrackedJobs.iterator();
        while (it.hasNext()) {
            JobStatus jobStatus = (JobStatus) it.next();
            if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                indentingPrintWriter.print("#");
                jobStatus.printUniqueId(indentingPrintWriter);
                indentingPrintWriter.print(" from ");
                UserHandle.formatUid(indentingPrintWriter, jobStatus.sourceUid);
                indentingPrintWriter.print(": Delay=");
                if (jobStatus.hasConstraint(Integer.MIN_VALUE)) {
                    TimeUtils.formatDuration(
                            jobStatus.earliestRunTimeElapsedMillis,
                            elapsedRealtime,
                            indentingPrintWriter);
                } else {
                    indentingPrintWriter.print("N/A");
                }
                indentingPrintWriter.print(", Deadline=");
                if (jobStatus.hasConstraint(1073741824)) {
                    TimeUtils.formatDuration(
                            jobStatus.latestRunTimeElapsedMillis,
                            elapsedRealtime,
                            indentingPrintWriter);
                } else {
                    indentingPrintWriter.print("N/A");
                }
                indentingPrintWriter.println();
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            ProtoOutputStream protoOutputStream,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        long j = 2246267895812L;
        long start = protoOutputStream.start(2246267895812L);
        long start2 = protoOutputStream.start(1146756268040L);
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        protoOutputStream.write(1112396529665L, elapsedRealtime);
        protoOutputStream.write(
                1112396529666L, this.mNextDelayExpiredElapsedMillis - elapsedRealtime);
        protoOutputStream.write(
                1112396529667L, this.mNextJobExpiredElapsedMillis - elapsedRealtime);
        Iterator it = this.mTrackedJobs.iterator();
        while (it.hasNext()) {
            JobStatus jobStatus = (JobStatus) it.next();
            if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                long start3 = protoOutputStream.start(j);
                jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1133871366147L, jobStatus.hasConstraint(Integer.MIN_VALUE));
                protoOutputStream.write(
                        1112396529668L, jobStatus.earliestRunTimeElapsedMillis - elapsedRealtime);
                protoOutputStream.write(1133871366149L, jobStatus.hasConstraint(1073741824));
                protoOutputStream.write(
                        1112396529670L, jobStatus.latestRunTimeElapsedMillis - elapsedRealtime);
                protoOutputStream.end(start3);
                j = 2246267895812L;
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void evaluateStateLocked(JobStatus jobStatus) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        boolean hasConstraint = jobStatus.hasConstraint(1073741824);
        JobSchedulerService jobSchedulerService = this.mService;
        String str = jobStatus.sourcePackageName;
        int i = jobStatus.sourceUid;
        if (hasConstraint && !jobStatus.isConstraintSatisfied(1073741824)) {
            long j = this.mNextJobExpiredElapsedMillis;
            long j2 = jobStatus.latestRunTimeElapsedMillis;
            if (j2 <= j) {
                if (evaluateDeadlineConstraint(jobStatus, elapsedRealtime)) {
                    if (jobStatus.isReady(jobStatus.mSatisfiedConstraintsOfInterest)) {
                        this.mStateChangedListener.onRunJobNow(jobStatus);
                    }
                    this.mTrackedJobs.remove(jobStatus);
                    Counter.logIncrement(
                            "job_scheduler.value_job_scheduler_job_deadline_expired_counter");
                } else if (wouldBeReadyWithConstraintLocked(jobStatus, 1073741824)) {
                    setDeadlineExpiredAlarmLocked(j2, jobSchedulerService.deriveWorkSource(i, str));
                }
            }
        }
        if (!jobStatus.hasConstraint(Integer.MIN_VALUE)
                || jobStatus.isConstraintSatisfied(Integer.MIN_VALUE)) {
            return;
        }
        long j3 = this.mNextDelayExpiredElapsedMillis;
        long j4 = jobStatus.earliestRunTimeElapsedMillis;
        if (j4 <= j3) {
            if (j4 > elapsedRealtime) {
                if (wouldBeReadyWithConstraintLocked(jobStatus, Integer.MIN_VALUE)) {
                    setDelayExpiredAlarmLocked(j4, jobSchedulerService.deriveWorkSource(i, str));
                }
            } else {
                jobStatus.setConstraintSatisfied(Integer.MIN_VALUE, elapsedRealtime, true);
                if (canStopTrackingJobLocked(jobStatus)) {
                    this.mTrackedJobs.remove(jobStatus);
                }
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.hasConstraint(Integer.MIN_VALUE) || jobStatus.hasConstraint(1073741824)) {
            maybeStopTrackingJobLocked(jobStatus, null);
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (jobStatus.hasConstraint(1073741824)
                    && evaluateDeadlineConstraint(jobStatus, elapsedRealtime)) {
                return;
            }
            boolean hasConstraint = jobStatus.hasConstraint(Integer.MIN_VALUE);
            long j = jobStatus.earliestRunTimeElapsedMillis;
            if (hasConstraint && j <= elapsedRealtime) {
                jobStatus.setConstraintSatisfied(Integer.MIN_VALUE, elapsedRealtime, true);
                if (!jobStatus.hasConstraint(1073741824)) {
                    return;
                }
            }
            this.mTrackedJobs.add(jobStatus);
            jobStatus.setTrackingController(32);
            WorkSource deriveWorkSource =
                    this.mService.deriveWorkSource(
                            jobStatus.sourceUid, jobStatus.sourcePackageName);
            if (jobStatus.hasConstraint(Integer.MIN_VALUE)
                    && wouldBeReadyWithConstraintLocked(jobStatus, Integer.MIN_VALUE)
                    && j < this.mNextDelayExpiredElapsedMillis) {
                setDelayExpiredAlarmLocked(j, deriveWorkSource);
            }
            if (jobStatus.hasConstraint(1073741824)
                    && wouldBeReadyWithConstraintLocked(jobStatus, 1073741824)) {
                long j2 = this.mNextJobExpiredElapsedMillis;
                long j3 = jobStatus.latestRunTimeElapsedMillis;
                if (j3 < j2) {
                    setDeadlineExpiredAlarmLocked(j3, deriveWorkSource);
                }
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(32) && this.mTrackedJobs.remove(jobStatus)) {
            checkExpiredDelaysAndResetAlarm();
            checkExpiredDeadlinesAndResetAlarm();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void reevaluateStateLocked(int i) {
        checkExpiredDeadlinesAndResetAlarm();
        checkExpiredDelaysAndResetAlarm();
    }

    public final void setDeadlineExpiredAlarmLocked(long j, WorkSource workSource) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long max = Math.max(j, SystemClock.elapsedRealtime());
        if (this.mNextJobExpiredElapsedMillis == max) {
            return;
        }
        this.mNextJobExpiredElapsedMillis = max;
        updateAlarmWithListenerLocked(
                "*job.deadline*", 2, this.mDeadlineExpiredListener, max, workSource);
    }

    public final void setDelayExpiredAlarmLocked(long j, WorkSource workSource) {
        long max = Math.max(j, this.mLastFiredDelayExpiredElapsedMillis + 30000);
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long max2 = Math.max(max, SystemClock.elapsedRealtime());
        if (this.mNextDelayExpiredElapsedMillis == max2) {
            return;
        }
        this.mNextDelayExpiredElapsedMillis = max2;
        updateAlarmWithListenerLocked(
                "*job.delay*", 3, this.mNextDelayExpiredListener, max2, workSource);
    }

    public final void updateAlarmWithListenerLocked(
            String str,
            int i,
            AlarmManager.OnAlarmListener onAlarmListener,
            long j,
            WorkSource workSource) {
        if (this.mAlarmService == null) {
            this.mAlarmService = (AlarmManager) this.mContext.getSystemService("alarm");
        }
        if (j == Long.MAX_VALUE) {
            this.mAlarmService.cancel(onAlarmListener);
            return;
        }
        if (DEBUG) {
            Slog.d("JobScheduler.Time", "Setting " + str + " for: " + j);
        }
        this.mAlarmService.set(
                i,
                j,
                -1L,
                0L,
                str,
                onAlarmListener,
                AppSchedulingModuleThread.getHandler(),
                workSource);
    }
}
