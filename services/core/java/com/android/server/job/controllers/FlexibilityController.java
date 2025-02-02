package com.android.server.job.controllers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArrayMap;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.clipboard.ClipboardService;
import com.android.server.job.JobSchedulerService;
import com.android.server.utils.AlarmQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class FlexibilityController extends StateController {
  public static final boolean DEBUG;
  public static final int NUM_FLEXIBLE_CONSTRAINTS;
  public static final int NUM_JOB_SPECIFIC_FLEXIBLE_CONSTRAINTS;
  public static final int NUM_OPTIONAL_FLEXIBLE_CONSTRAINTS;
  public static final int NUM_SYSTEM_WIDE_FLEXIBLE_CONSTRAINTS;
  public long mDeadlineProximityLimitMs;
  boolean mDeviceSupportsFlexConstraints;
  public long mFallbackFlexibilityDeadlineMs;
  final FcConfig mFcConfig;
  final FlexibilityAlarmQueue mFlexibilityAlarmQueue;
  boolean mFlexibilityEnabled;
  final FlexibilityTracker mFlexibilityTracker;
  public final FcHandler mHandler;
  public long mMaxRescheduledDeadline;
  public long mMinTimeBetweenFlexibilityAlarmsMs;
  public int[] mPercentToDropConstraints;
  final PrefetchController.PrefetchChangedListener mPrefetchChangedListener;
  final PrefetchController mPrefetchController;
  final SparseArrayMap mPrefetchLifeCycleStart;
  public long mRescheduledJobDeadline;
  int mSatisfiedFlexibleConstraints;

  static {
    DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.Flex", 3);
    NUM_JOB_SPECIFIC_FLEXIBLE_CONSTRAINTS = Integer.bitCount(268435456);
    NUM_OPTIONAL_FLEXIBLE_CONSTRAINTS = Integer.bitCount(7);
    NUM_SYSTEM_WIDE_FLEXIBLE_CONSTRAINTS = Integer.bitCount(7);
    NUM_FLEXIBLE_CONSTRAINTS = Integer.bitCount(268435463);
  }

  public FlexibilityController(
      JobSchedulerService jobSchedulerService, PrefetchController prefetchController) {
    super(jobSchedulerService);
    this.mFallbackFlexibilityDeadlineMs = 259200000L;
    this.mRescheduledJobDeadline = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    this.mMaxRescheduledDeadline = 432000000L;
    this.mFlexibilityEnabled = false;
    this.mMinTimeBetweenFlexibilityAlarmsMs = 60000L;
    this.mDeadlineProximityLimitMs = 900000L;
    this.mPrefetchLifeCycleStart = new SparseArrayMap();
    PrefetchController.PrefetchChangedListener prefetchChangedListener =
        new PrefetchController
            .PrefetchChangedListener() { // from class:
                                         // com.android.server.job.controllers.FlexibilityController.1
          @Override // com.android.server.job.controllers.PrefetchController.PrefetchChangedListener
          public void onPrefetchCacheUpdated(
              ArraySet arraySet, int i, String str, long j, long j2, long j3) {
            synchronized (FlexibilityController.this.mLock) {
              long launchTimeThresholdMs =
                  FlexibilityController.this.mPrefetchController.getLaunchTimeThresholdMs();
              boolean z = true;
              boolean z2 = j - launchTimeThresholdMs < j3;
              if (j2 - launchTimeThresholdMs >= j3) {
                z = false;
              }
              if (z != z2) {
                SparseArrayMap sparseArrayMap = FlexibilityController.this.mPrefetchLifeCycleStart;
                sparseArrayMap.add(
                    i,
                    str,
                    Long.valueOf(
                        Math.max(
                            j3, ((Long) sparseArrayMap.getOrDefault(i, str, 0L)).longValue())));
              }
              for (int i2 = 0; i2 < arraySet.size(); i2++) {
                JobStatus jobStatus = (JobStatus) arraySet.valueAt(i2);
                if (jobStatus.hasFlexibilityConstraint()) {
                  FlexibilityController.this.mFlexibilityTracker.resetJobNumDroppedConstraints(
                      jobStatus, j3);
                  FlexibilityController.this.mFlexibilityAlarmQueue.scheduleDropNumConstraintsAlarm(
                      jobStatus, j3);
                }
              }
            }
          }
        };
    this.mPrefetchChangedListener = prefetchChangedListener;
    this.mHandler = new FcHandler(AppSchedulingModuleThread.get().getLooper());
    boolean z =
        !this.mContext.getPackageManager().hasSystemFeature("android.hardware.type.automotive");
    this.mDeviceSupportsFlexConstraints = z;
    this.mFlexibilityEnabled = z & this.mFlexibilityEnabled;
    this.mFlexibilityTracker = new FlexibilityTracker(NUM_FLEXIBLE_CONSTRAINTS);
    FcConfig fcConfig = new FcConfig();
    this.mFcConfig = fcConfig;
    this.mFlexibilityAlarmQueue =
        new FlexibilityAlarmQueue(this.mContext, AppSchedulingModuleThread.get().getLooper());
    this.mPercentToDropConstraints = fcConfig.DEFAULT_PERCENT_TO_DROP_FLEXIBLE_CONSTRAINTS;
    this.mPrefetchController = prefetchController;
    if (this.mFlexibilityEnabled) {
      prefetchController.registerPrefetchChangedListener(prefetchChangedListener);
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
    if (jobStatus.hasFlexibilityConstraint()) {
      long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
      if (!this.mDeviceSupportsFlexConstraints) {
        jobStatus.setFlexibilityConstraintSatisfied(millis, true);
        return;
      }
      jobStatus.setFlexibilityConstraintSatisfied(millis, isFlexibilitySatisfiedLocked(jobStatus));
      this.mFlexibilityTracker.add(jobStatus);
      jobStatus.setTrackingController(128);
      this.mFlexibilityAlarmQueue.scheduleDropNumConstraintsAlarm(jobStatus, millis);
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
    if (jobStatus.clearTrackingController(128)) {
      this.mFlexibilityAlarmQueue.removeAlarmForKey(jobStatus);
      this.mFlexibilityTracker.remove(jobStatus);
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void onAppRemovedLocked(String str, int i) {
    this.mPrefetchLifeCycleStart.delete(UserHandle.getUserId(i), str);
  }

  @Override // com.android.server.job.controllers.StateController
  public void onUserRemovedLocked(int i) {
    this.mPrefetchLifeCycleStart.delete(i);
  }

  public boolean isFlexibilitySatisfiedLocked(JobStatus jobStatus) {
    return !this.mFlexibilityEnabled
        || this.mService.getUidBias(jobStatus.getSourceUid()) == 40
        || getNumSatisfiedFlexibleConstraintsLocked(jobStatus)
            >= jobStatus.getNumRequiredFlexibleConstraints()
        || this.mService.isCurrentlyRunningLocked(jobStatus);
  }

  public int getNumSatisfiedFlexibleConstraintsLocked(JobStatus jobStatus) {
    return Integer.bitCount(
            this.mSatisfiedFlexibleConstraints & jobStatus.getPreferredConstraintFlags())
        + (jobStatus.getHasAccessToUnmetered() ? 1 : 0);
  }

  public void setConstraintSatisfied(int i, boolean z, long j) {
    synchronized (this.mLock) {
      if (((this.mSatisfiedFlexibleConstraints & i) != 0) == z) {
        return;
      }
      if (DEBUG) {
        Slog.d("JobScheduler.Flex", "setConstraintSatisfied:  constraint: " + i + " state: " + z);
      }
      int i2 = this.mSatisfiedFlexibleConstraints & (~i);
      if (!z) {
        i = 0;
      }
      this.mSatisfiedFlexibleConstraints = i | i2;
      this.mHandler.obtainMessage(0).sendToTarget();
    }
  }

  public boolean isConstraintSatisfied(int i) {
    return (this.mSatisfiedFlexibleConstraints & i) != 0;
  }

  public long getLifeCycleBeginningElapsedLocked(JobStatus jobStatus) {
    if (!jobStatus.getJob().isPrefetch()) {
      return jobStatus.getEarliestRunTime() == 0
          ? jobStatus.enqueueTime
          : jobStatus.getEarliestRunTime();
    }
    long max = Math.max(jobStatus.enqueueTime, jobStatus.getEarliestRunTime());
    long nextEstimatedLaunchTimeLocked =
        this.mPrefetchController.getNextEstimatedLaunchTimeLocked(jobStatus);
    long longValue =
        ((Long)
                this.mPrefetchLifeCycleStart.getOrDefault(
                    jobStatus.getSourceUserId(), jobStatus.getSourcePackageName(), 0L))
            .longValue();
    if (nextEstimatedLaunchTimeLocked != Long.MAX_VALUE) {
      longValue =
          Math.max(
              longValue,
              nextEstimatedLaunchTimeLocked - this.mPrefetchController.getLaunchTimeThresholdMs());
    }
    return Math.max(longValue, max);
  }

  public long getLifeCycleEndElapsedLocked(JobStatus jobStatus, long j) {
    if (jobStatus.getJob().isPrefetch()) {
      long nextEstimatedLaunchTimeLocked =
          this.mPrefetchController.getNextEstimatedLaunchTimeLocked(jobStatus);
      if (jobStatus.getLatestRunTimeElapsed() != Long.MAX_VALUE) {
        return Math.min(
            nextEstimatedLaunchTimeLocked - this.mConstants.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS,
            jobStatus.getLatestRunTimeElapsed());
      }
      if (nextEstimatedLaunchTimeLocked != Long.MAX_VALUE) {
        return nextEstimatedLaunchTimeLocked
            - this.mConstants.PREFETCH_FORCE_BATCH_RELAX_THRESHOLD_MS;
      }
      return Long.MAX_VALUE;
    }
    if (jobStatus.getNumPreviousAttempts() > 1) {
      return j
          + Math.min(
              (long)
                  Math.scalb(this.mRescheduledJobDeadline, jobStatus.getNumPreviousAttempts() - 2),
              this.mMaxRescheduledDeadline);
    }
    return jobStatus.getLatestRunTimeElapsed() == Long.MAX_VALUE
        ? j + this.mFallbackFlexibilityDeadlineMs
        : jobStatus.getLatestRunTimeElapsed();
  }

  public int getCurPercentOfLifecycleLocked(JobStatus jobStatus, long j) {
    long lifeCycleBeginningElapsedLocked = getLifeCycleBeginningElapsedLocked(jobStatus);
    long lifeCycleEndElapsedLocked =
        getLifeCycleEndElapsedLocked(jobStatus, lifeCycleBeginningElapsedLocked);
    if (lifeCycleEndElapsedLocked == Long.MAX_VALUE || lifeCycleBeginningElapsedLocked >= j) {
      return 0;
    }
    if (j > lifeCycleEndElapsedLocked
        || lifeCycleEndElapsedLocked == lifeCycleBeginningElapsedLocked) {
      return 100;
    }
    return (int)
        (((j - lifeCycleBeginningElapsedLocked) * 100)
            / (lifeCycleEndElapsedLocked - lifeCycleBeginningElapsedLocked));
  }

  public long getNextConstraintDropTimeElapsedLocked(JobStatus jobStatus) {
    long lifeCycleBeginningElapsedLocked = getLifeCycleBeginningElapsedLocked(jobStatus);
    return getNextConstraintDropTimeElapsedLocked(
        jobStatus,
        lifeCycleBeginningElapsedLocked,
        getLifeCycleEndElapsedLocked(jobStatus, lifeCycleBeginningElapsedLocked));
  }

  public long getNextConstraintDropTimeElapsedLocked(JobStatus jobStatus, long j, long j2) {
    if (j2 != Long.MAX_VALUE) {
      if (jobStatus.getNumDroppedFlexibleConstraints() != this.mPercentToDropConstraints.length) {
        return j + (((j2 - j) * r4[jobStatus.getNumDroppedFlexibleConstraints()]) / 100);
      }
    }
    return Long.MAX_VALUE;
  }

  @Override // com.android.server.job.controllers.StateController
  public void onUidBiasChangedLocked(int i, int i2, int i3) {
    if (i2 == 40 || i3 == 40) {
      long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
      ArraySet jobsBySourceUid = this.mService.getJobStore().getJobsBySourceUid(i);
      boolean z = false;
      for (int i4 = 0; i4 < jobsBySourceUid.size(); i4++) {
        JobStatus jobStatus = (JobStatus) jobsBySourceUid.valueAt(i4);
        if (jobStatus.hasFlexibilityConstraint()) {
          jobStatus.setFlexibilityConstraintSatisfied(
              millis, isFlexibilitySatisfiedLocked(jobStatus));
          z |= jobStatus.getJob().isPrefetch();
        }
      }
      if (z && i2 == 40) {
        int userId = UserHandle.getUserId(i);
        ArraySet packagesForUidLocked = this.mService.getPackagesForUidLocked(i);
        if (packagesForUidLocked == null) {
          return;
        }
        for (int i5 = 0; i5 < packagesForUidLocked.size(); i5++) {
          String str = (String) packagesForUidLocked.valueAt(i5);
          SparseArrayMap sparseArrayMap = this.mPrefetchLifeCycleStart;
          sparseArrayMap.add(
              userId,
              str,
              Long.valueOf(
                  Math.max(
                      ((Long) sparseArrayMap.getOrDefault(userId, str, 0L)).longValue(), millis)));
        }
      }
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void onConstantsUpdatedLocked() {
    if (this.mFcConfig.mShouldReevaluateConstraints) {
      AppSchedulingModuleThread.getHandler()
          .post(
              new Runnable() { // from class:
                               // com.android.server.job.controllers.FlexibilityController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  FlexibilityController.this.lambda$onConstantsUpdatedLocked$0();
                }
              });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onConstantsUpdatedLocked$0() {
    ArraySet arraySet = new ArraySet();
    synchronized (this.mLock) {
      long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
      for (int i = 0; i < this.mFlexibilityTracker.size(); i++) {
        ArraySet jobsByNumRequiredConstraints =
            this.mFlexibilityTracker.getJobsByNumRequiredConstraints(i);
        for (int i2 = 0; i2 < jobsByNumRequiredConstraints.size(); i2++) {
          JobStatus jobStatus = (JobStatus) jobsByNumRequiredConstraints.valueAt(i2);
          this.mFlexibilityTracker.resetJobNumDroppedConstraints(jobStatus, millis);
          this.mFlexibilityAlarmQueue.scheduleDropNumConstraintsAlarm(jobStatus, millis);
          if (jobStatus.setFlexibilityConstraintSatisfied(
              millis, isFlexibilitySatisfiedLocked(jobStatus))) {
            arraySet.add(jobStatus);
          }
        }
      }
    }
    if (arraySet.size() > 0) {
      this.mStateChangedListener.onControllerStateChanged(arraySet);
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void prepareForUpdatedConstantsLocked() {
    this.mFcConfig.mShouldReevaluateConstraints = false;
  }

  @Override // com.android.server.job.controllers.StateController
  public void processConstantLocked(DeviceConfig.Properties properties, String str) {
    this.mFcConfig.processConstantLocked(properties, str);
  }

  class FlexibilityTracker {
    public final ArrayList mTrackedJobs = new ArrayList();

    public FlexibilityTracker(int i) {
      for (int i2 = 0; i2 <= i; i2++) {
        this.mTrackedJobs.add(new ArraySet());
      }
    }

    public ArraySet getJobsByNumRequiredConstraints(int i) {
      if (i > this.mTrackedJobs.size()) {
        Slog.wtfStack("JobScheduler.Flex", "Asked for a larger number of constraints than exists.");
        return null;
      }
      return (ArraySet) this.mTrackedJobs.get(i);
    }

    public void add(JobStatus jobStatus) {
      if (jobStatus.getNumRequiredFlexibleConstraints() < 0) {
        return;
      }
      ((ArraySet) this.mTrackedJobs.get(jobStatus.getNumRequiredFlexibleConstraints()))
          .add(jobStatus);
    }

    public void remove(JobStatus jobStatus) {
      ((ArraySet) this.mTrackedJobs.get(jobStatus.getNumRequiredFlexibleConstraints()))
          .remove(jobStatus);
    }

    public void resetJobNumDroppedConstraints(JobStatus jobStatus, long j) {
      int curPercentOfLifecycleLocked =
          FlexibilityController.this.getCurPercentOfLifecycleLocked(jobStatus, j);
      int i =
          FlexibilityController.NUM_SYSTEM_WIDE_FLEXIBLE_CONSTRAINTS
              + (jobStatus.getPreferUnmetered() ? 1 : 0);
      int i2 = 0;
      for (int i3 = 0; i3 < i; i3++) {
        if (curPercentOfLifecycleLocked
            >= FlexibilityController.this.mPercentToDropConstraints[i3]) {
          i2++;
        }
      }
      adjustJobsRequiredConstraints(
          jobStatus, jobStatus.getNumDroppedFlexibleConstraints() - i2, j);
    }

    public boolean adjustJobsRequiredConstraints(JobStatus jobStatus, int i, long j) {
      if (i != 0) {
        remove(jobStatus);
        jobStatus.adjustNumRequiredFlexibleConstraints(i);
        jobStatus.setFlexibilityConstraintSatisfied(
            j, FlexibilityController.this.isFlexibilitySatisfiedLocked(jobStatus));
        add(jobStatus);
      }
      return jobStatus.getNumRequiredFlexibleConstraints() > 0;
    }

    public int size() {
      return this.mTrackedJobs.size();
    }

    public void dump(IndentingPrintWriter indentingPrintWriter, Predicate predicate) {
      for (int i = 0; i < this.mTrackedJobs.size(); i++) {
        ArraySet arraySet = (ArraySet) this.mTrackedJobs.get(i);
        for (int i2 = 0; i2 < arraySet.size(); i2++) {
          JobStatus jobStatus = (JobStatus) arraySet.valueAt(i2);
          if (predicate.test(jobStatus)) {
            indentingPrintWriter.print("#");
            jobStatus.printUniqueId(indentingPrintWriter);
            indentingPrintWriter.print(" from ");
            UserHandle.formatUid(indentingPrintWriter, jobStatus.getSourceUid());
            indentingPrintWriter.print(" Num Required Constraints: ");
            indentingPrintWriter.print(jobStatus.getNumRequiredFlexibleConstraints());
            indentingPrintWriter.println();
          }
        }
      }
    }
  }

  class FlexibilityAlarmQueue extends AlarmQueue {
    public FlexibilityAlarmQueue(Context context, Looper looper) {
      super(
          context,
          looper,
          "*job.flexibility_check*",
          "Flexible Constraint Check",
          true,
          FlexibilityController.this.mMinTimeBetweenFlexibilityAlarmsMs);
    }

    @Override // com.android.server.utils.AlarmQueue
    public boolean isForUser(JobStatus jobStatus, int i) {
      return jobStatus.getSourceUserId() == i;
    }

    public void scheduleDropNumConstraintsAlarm(JobStatus jobStatus, long j) {
      synchronized (FlexibilityController.this.mLock) {
        long lifeCycleBeginningElapsedLocked =
            FlexibilityController.this.getLifeCycleBeginningElapsedLocked(jobStatus);
        long lifeCycleEndElapsedLocked =
            FlexibilityController.this.getLifeCycleEndElapsedLocked(
                jobStatus, lifeCycleBeginningElapsedLocked);
        long nextConstraintDropTimeElapsedLocked =
            FlexibilityController.this.getNextConstraintDropTimeElapsedLocked(
                jobStatus, lifeCycleBeginningElapsedLocked, lifeCycleEndElapsedLocked);
        if (FlexibilityController.DEBUG) {
          Slog.d(
              "JobScheduler.Flex",
              "scheduleDropNumConstraintsAlarm: "
                  + jobStatus.getSourcePackageName()
                  + " "
                  + jobStatus.getSourceUserId()
                  + " numRequired: "
                  + jobStatus.getNumRequiredFlexibleConstraints()
                  + " numSatisfied: "
                  + Integer.bitCount(FlexibilityController.this.mSatisfiedFlexibleConstraints)
                  + " curTime: "
                  + j
                  + " earliest: "
                  + lifeCycleBeginningElapsedLocked
                  + " latest: "
                  + lifeCycleEndElapsedLocked
                  + " nextTime: "
                  + nextConstraintDropTimeElapsedLocked);
        }
        if (lifeCycleEndElapsedLocked - j < FlexibilityController.this.mDeadlineProximityLimitMs) {
          if (FlexibilityController.DEBUG) {
            Slog.d("JobScheduler.Flex", "deadline proximity met: " + jobStatus);
          }
          FlexibilityController.this.mFlexibilityTracker.adjustJobsRequiredConstraints(
              jobStatus, -jobStatus.getNumRequiredFlexibleConstraints(), j);
          return;
        }
        if (nextConstraintDropTimeElapsedLocked == Long.MAX_VALUE) {
          removeAlarmForKey(jobStatus);
          return;
        }
        if (lifeCycleEndElapsedLocked - nextConstraintDropTimeElapsedLocked
            <= FlexibilityController.this.mDeadlineProximityLimitMs) {
          if (FlexibilityController.DEBUG) {
            Slog.d("JobScheduler.Flex", "last alarm set: " + jobStatus);
          }
          addAlarm(
              jobStatus,
              lifeCycleEndElapsedLocked - FlexibilityController.this.mDeadlineProximityLimitMs);
          return;
        }
        addAlarm(jobStatus, nextConstraintDropTimeElapsedLocked);
      }
    }

    @Override // com.android.server.utils.AlarmQueue
    public void processExpiredAlarms(ArraySet arraySet) {
      synchronized (FlexibilityController.this.mLock) {
        ArraySet arraySet2 = new ArraySet();
        long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
        for (int i = 0; i < arraySet.size(); i++) {
          JobStatus jobStatus = (JobStatus) arraySet.valueAt(i);
          boolean isConstraintSatisfied = jobStatus.isConstraintSatisfied(2097152);
          if (FlexibilityController.this.mFlexibilityTracker.adjustJobsRequiredConstraints(
              jobStatus, -1, millis)) {
            scheduleDropNumConstraintsAlarm(jobStatus, millis);
          }
          if (isConstraintSatisfied != jobStatus.isConstraintSatisfied(2097152)) {
            arraySet2.add(jobStatus);
          }
        }
        FlexibilityController.this.mStateChangedListener.onControllerStateChanged(arraySet2);
      }
    }
  }

  public class FcHandler extends Handler {
    public FcHandler(Looper looper) {
      super(looper);
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      if (message.what != 0) {
        return;
      }
      removeMessages(0);
      synchronized (FlexibilityController.this.mLock) {
        long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
        ArraySet arraySet = new ArraySet();
        for (int i = 0; i <= FlexibilityController.NUM_OPTIONAL_FLEXIBLE_CONSTRAINTS; i++) {
          ArraySet jobsByNumRequiredConstraints =
              FlexibilityController.this.mFlexibilityTracker.getJobsByNumRequiredConstraints(i);
          if (jobsByNumRequiredConstraints != null) {
            for (int i2 = 0; i2 < jobsByNumRequiredConstraints.size(); i2++) {
              JobStatus jobStatus = (JobStatus) jobsByNumRequiredConstraints.valueAt(i2);
              if (jobStatus.setFlexibilityConstraintSatisfied(
                  millis, FlexibilityController.this.isFlexibilitySatisfiedLocked(jobStatus))) {
                arraySet.add(jobStatus);
              }
            }
          }
        }
        if (arraySet.size() > 0) {
          FlexibilityController.this.mStateChangedListener.onControllerStateChanged(arraySet);
        }
      }
    }
  }

  class FcConfig {
    static final long DEFAULT_DEADLINE_PROXIMITY_LIMIT_MS = 900000;
    static final long DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_MS = 259200000;
    final int[] DEFAULT_PERCENT_TO_DROP_FLEXIBLE_CONSTRAINTS;
    public int[] PERCENTS_TO_DROP_NUM_FLEXIBLE_CONSTRAINTS;
    public boolean mShouldReevaluateConstraints = false;
    public boolean FLEXIBILITY_ENABLED = false;
    public long DEADLINE_PROXIMITY_LIMIT_MS = DEFAULT_DEADLINE_PROXIMITY_LIMIT_MS;
    public long FALLBACK_FLEXIBILITY_DEADLINE_MS = DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_MS;
    public long MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS = 60000;
    public long RESCHEDULED_JOB_DEADLINE_MS = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    public long MAX_RESCHEDULED_DEADLINE_MS = 432000000;

    public FcConfig() {
      int[] iArr = {50, 60, 70, 80};
      this.DEFAULT_PERCENT_TO_DROP_FLEXIBLE_CONSTRAINTS = iArr;
      this.PERCENTS_TO_DROP_NUM_FLEXIBLE_CONSTRAINTS = iArr;
    }

    public void processConstantLocked(DeviceConfig.Properties properties, String str) {
      boolean z;
      str.hashCode();
      z = false;
      switch (str) {
        case "fc_min_time_between_flexibility_alarms_ms":
          this.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS = properties.getLong(str, 60000L);
          long j = FlexibilityController.this.mMinTimeBetweenFlexibilityAlarmsMs;
          long j2 = this.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS;
          if (j != j2) {
            FlexibilityController.this.mMinTimeBetweenFlexibilityAlarmsMs = j2;
            FlexibilityController.this.mFlexibilityAlarmQueue.setMinTimeBetweenAlarmsMs(
                this.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS);
            this.mShouldReevaluateConstraints = true;
            break;
          }
          break;
        case "fc_max_rescheduled_deadline_ms":
          this.MAX_RESCHEDULED_DEADLINE_MS = properties.getLong(str, 432000000L);
          long j3 = FlexibilityController.this.mMaxRescheduledDeadline;
          long j4 = this.MAX_RESCHEDULED_DEADLINE_MS;
          if (j3 != j4) {
            FlexibilityController.this.mMaxRescheduledDeadline = j4;
            this.mShouldReevaluateConstraints = true;
            break;
          }
          break;
        case "fc_rescheduled_job_deadline_ms":
          this.RESCHEDULED_JOB_DEADLINE_MS =
              properties.getLong(str, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
          long j5 = FlexibilityController.this.mRescheduledJobDeadline;
          long j6 = this.RESCHEDULED_JOB_DEADLINE_MS;
          if (j5 != j6) {
            FlexibilityController.this.mRescheduledJobDeadline = j6;
            this.mShouldReevaluateConstraints = true;
            break;
          }
          break;
        case "fc_percents_to_drop_num_flexible_constraints":
          int[] parsePercentToDropString = parsePercentToDropString(properties.getString(str, ""));
          this.PERCENTS_TO_DROP_NUM_FLEXIBLE_CONSTRAINTS = parsePercentToDropString;
          if (parsePercentToDropString != null
              && !Arrays.equals(
                  FlexibilityController.this.mPercentToDropConstraints,
                  this.PERCENTS_TO_DROP_NUM_FLEXIBLE_CONSTRAINTS)) {
            FlexibilityController.this.mPercentToDropConstraints =
                this.PERCENTS_TO_DROP_NUM_FLEXIBLE_CONSTRAINTS;
            this.mShouldReevaluateConstraints = true;
            break;
          }
          break;
        case "fc_flexibility_deadline_proximity_limit_ms":
          this.DEADLINE_PROXIMITY_LIMIT_MS =
              properties.getLong(str, DEFAULT_DEADLINE_PROXIMITY_LIMIT_MS);
          long j7 = FlexibilityController.this.mDeadlineProximityLimitMs;
          long j8 = this.DEADLINE_PROXIMITY_LIMIT_MS;
          if (j7 != j8) {
            FlexibilityController.this.mDeadlineProximityLimitMs = j8;
            this.mShouldReevaluateConstraints = true;
            break;
          }
          break;
        case "fc_enable_flexibility":
          if (properties.getBoolean(str, false)
              && FlexibilityController.this.mDeviceSupportsFlexConstraints) {
            z = true;
          }
          this.FLEXIBILITY_ENABLED = z;
          FlexibilityController flexibilityController = FlexibilityController.this;
          if (flexibilityController.mFlexibilityEnabled != z) {
            flexibilityController.mFlexibilityEnabled = z;
            this.mShouldReevaluateConstraints = true;
            if (z) {
              flexibilityController.mPrefetchController.registerPrefetchChangedListener(
                  flexibilityController.mPrefetchChangedListener);
              break;
            } else {
              flexibilityController.mPrefetchController.unRegisterPrefetchChangedListener(
                  flexibilityController.mPrefetchChangedListener);
              break;
            }
          }
          break;
        case "fc_fallback_flexibility_deadline_ms":
          this.FALLBACK_FLEXIBILITY_DEADLINE_MS =
              properties.getLong(str, DEFAULT_FALLBACK_FLEXIBILITY_DEADLINE_MS);
          long j9 = FlexibilityController.this.mFallbackFlexibilityDeadlineMs;
          long j10 = this.FALLBACK_FLEXIBILITY_DEADLINE_MS;
          if (j9 != j10) {
            FlexibilityController.this.mFallbackFlexibilityDeadlineMs = j10;
            this.mShouldReevaluateConstraints = true;
            break;
          }
          break;
      }
    }

    public final int[] parsePercentToDropString(String str) {
      String[] split = str.split(",");
      int i = FlexibilityController.NUM_FLEXIBLE_CONSTRAINTS;
      int[] iArr = new int[i];
      if (i != split.length) {
        return this.DEFAULT_PERCENT_TO_DROP_FLEXIBLE_CONSTRAINTS;
      }
      int i2 = 0;
      int i3 = 0;
      while (i2 < split.length) {
        try {
          int parseInt = Integer.parseInt(split[i2]);
          iArr[i2] = parseInt;
          if (parseInt < i3) {
            Slog.wtf(
                "JobScheduler.Flex", "Percents to drop constraints were not in increasing order.");
            return this.DEFAULT_PERCENT_TO_DROP_FLEXIBLE_CONSTRAINTS;
          }
          i2++;
          i3 = parseInt;
        } catch (NumberFormatException e) {
          Slog.e("JobScheduler.Flex", "Provided string was improperly formatted.", e);
          return this.DEFAULT_PERCENT_TO_DROP_FLEXIBLE_CONSTRAINTS;
        }
      }
      return iArr;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
      indentingPrintWriter.println();
      indentingPrintWriter.print(FlexibilityController.class.getSimpleName());
      indentingPrintWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
      indentingPrintWriter.increaseIndent();
      indentingPrintWriter
          .print("fc_enable_flexibility", Boolean.valueOf(this.FLEXIBILITY_ENABLED))
          .println();
      indentingPrintWriter
          .print(
              "fc_flexibility_deadline_proximity_limit_ms",
              Long.valueOf(this.DEADLINE_PROXIMITY_LIMIT_MS))
          .println();
      indentingPrintWriter
          .print(
              "fc_fallback_flexibility_deadline_ms",
              Long.valueOf(this.FALLBACK_FLEXIBILITY_DEADLINE_MS))
          .println();
      indentingPrintWriter
          .print(
              "fc_min_time_between_flexibility_alarms_ms",
              Long.valueOf(this.MIN_TIME_BETWEEN_FLEXIBILITY_ALARMS_MS))
          .println();
      indentingPrintWriter
          .print(
              "fc_percents_to_drop_num_flexible_constraints",
              this.PERCENTS_TO_DROP_NUM_FLEXIBLE_CONSTRAINTS)
          .println();
      indentingPrintWriter
          .print("fc_rescheduled_job_deadline_ms", Long.valueOf(this.RESCHEDULED_JOB_DEADLINE_MS))
          .println();
      indentingPrintWriter
          .print("fc_max_rescheduled_deadline_ms", Long.valueOf(this.MAX_RESCHEDULED_DEADLINE_MS))
          .println();
      indentingPrintWriter.decreaseIndent();
    }
  }

  public FcConfig getFcConfig() {
    return this.mFcConfig;
  }

  @Override // com.android.server.job.controllers.StateController
  public void dumpConstants(IndentingPrintWriter indentingPrintWriter) {
    this.mFcConfig.dump(indentingPrintWriter);
  }

  @Override // com.android.server.job.controllers.StateController
  public void dumpControllerStateLocked(
      IndentingPrintWriter indentingPrintWriter, Predicate predicate) {
    indentingPrintWriter.println(
        "# Constraints Satisfied: " + Integer.bitCount(this.mSatisfiedFlexibleConstraints));
    indentingPrintWriter.print("Satisfied Flexible Constraints: ");
    JobStatus.dumpConstraints(indentingPrintWriter, this.mSatisfiedFlexibleConstraints);
    indentingPrintWriter.println();
    indentingPrintWriter.println();
    this.mFlexibilityTracker.dump(indentingPrintWriter, predicate);
    indentingPrintWriter.println();
    this.mFlexibilityAlarmQueue.dump(indentingPrintWriter);
  }
}
