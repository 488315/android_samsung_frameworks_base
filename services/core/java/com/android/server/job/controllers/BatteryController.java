package com.android.server.job.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManagerInternal;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.LocalServices;
import com.android.server.job.JobSchedulerService;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class BatteryController extends RestrictingController {
  public static final boolean DEBUG;
  public final ArraySet mChangedJobs;
  public final FlexibilityController mFlexibilityController;
  public Boolean mLastReportedStatsdBatteryNotLow;
  public Boolean mLastReportedStatsdStablePower;
  public final PowerTracker mPowerTracker;
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
    PowerTracker powerTracker = new PowerTracker();
    this.mPowerTracker = powerTracker;
    powerTracker.startTracking();
    this.mFlexibilityController = flexibilityController;
  }

  @Override // com.android.server.job.controllers.StateController
  public void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
    if (jobStatus.hasPowerConstraint()) {
      long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
      this.mTrackedTasks.add(jobStatus);
      jobStatus.setTrackingController(1);
      if (jobStatus.hasChargingConstraint()) {
        if (hasTopExemptionLocked(jobStatus)) {
          jobStatus.setChargingConstraintSatisfied(millis, this.mPowerTracker.isPowerConnected());
        } else {
          jobStatus.setChargingConstraintSatisfied(
              millis, this.mService.isBatteryCharging() && this.mService.isBatteryNotLow());
        }
      }
      jobStatus.setBatteryNotLowConstraintSatisfied(millis, this.mService.isBatteryNotLow());
    }
  }

  @Override // com.android.server.job.controllers.RestrictingController
  public void startTrackingRestrictedJobLocked(JobStatus jobStatus) {
    maybeStartTrackingJobLocked(jobStatus, null);
  }

  @Override // com.android.server.job.controllers.StateController
  public void prepareForExecutionLocked(JobStatus jobStatus) {
    if (jobStatus.hasPowerConstraint()) {
      boolean z = DEBUG;
      if (z) {
        Slog.d("JobScheduler.Battery", "Prepping for " + jobStatus.toShortString());
      }
      if (this.mService.getUidBias(jobStatus.getSourceUid()) == 40) {
        if (z) {
          Slog.d("JobScheduler.Battery", jobStatus.toShortString() + " is top started job");
        }
        this.mTopStartedJobs.add(jobStatus);
      }
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void unprepareFromExecutionLocked(JobStatus jobStatus) {
    this.mTopStartedJobs.remove(jobStatus);
  }

  @Override // com.android.server.job.controllers.StateController
  public void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
    if (jobStatus.clearTrackingController(1)) {
      this.mTrackedTasks.remove(jobStatus);
      this.mTopStartedJobs.remove(jobStatus);
    }
  }

  @Override // com.android.server.job.controllers.RestrictingController
  public void stopTrackingRestrictedJobLocked(JobStatus jobStatus) {
    if (jobStatus.hasPowerConstraint()) {
      return;
    }
    maybeStopTrackingJobLocked(jobStatus, null);
  }

  @Override // com.android.server.job.controllers.StateController
  public void onBatteryStateChangedLocked() {
    AppSchedulingModuleThread.getHandler()
        .post(
            new Runnable() { // from class:
                             // com.android.server.job.controllers.BatteryController$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                BatteryController.this.lambda$onBatteryStateChangedLocked$0();
              }
            });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$onBatteryStateChangedLocked$0() {
    synchronized (this.mLock) {
      maybeReportNewChargingStateLocked();
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void onUidBiasChangedLocked(int i, int i2, int i3) {
    if (i2 == 40 || i3 == 40) {
      maybeReportNewChargingStateLocked();
    }
  }

  public final boolean hasTopExemptionLocked(JobStatus jobStatus) {
    return this.mService.getUidBias(jobStatus.getSourceUid()) == 40
        || this.mTopStartedJobs.contains(jobStatus);
  }

  public final void maybeReportNewChargingStateLocked() {
    boolean isPowerConnected = this.mPowerTracker.isPowerConnected();
    boolean z = this.mService.isBatteryCharging() && this.mService.isBatteryNotLow();
    boolean isBatteryNotLow = this.mService.isBatteryNotLow();
    if (DEBUG) {
      Slog.d(
          "JobScheduler.Battery",
          "maybeReportNewChargingStateLocked: "
              + isPowerConnected
              + "/"
              + z
              + "/"
              + isBatteryNotLow);
    }
    Boolean bool = this.mLastReportedStatsdStablePower;
    if (bool == null || bool.booleanValue() != z) {
      logDeviceWideConstraintStateToStatsd(1, z);
      this.mLastReportedStatsdStablePower = Boolean.valueOf(z);
    }
    Boolean bool2 = this.mLastReportedStatsdBatteryNotLow;
    if (bool2 == null || bool2.booleanValue() != isBatteryNotLow) {
      logDeviceWideConstraintStateToStatsd(2, isBatteryNotLow);
      this.mLastReportedStatsdBatteryNotLow = Boolean.valueOf(isBatteryNotLow);
    }
    long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
    this.mFlexibilityController.setConstraintSatisfied(
        1, this.mService.isBatteryCharging(), millis);
    this.mFlexibilityController.setConstraintSatisfied(2, isBatteryNotLow, millis);
    for (int size = this.mTrackedTasks.size() - 1; size >= 0; size--) {
      JobStatus jobStatus = (JobStatus) this.mTrackedTasks.valueAt(size);
      if (jobStatus.hasChargingConstraint()) {
        if (hasTopExemptionLocked(jobStatus) && jobStatus.getEffectivePriority() >= 300) {
          if (jobStatus.setChargingConstraintSatisfied(millis, isPowerConnected)) {
            this.mChangedJobs.add(jobStatus);
          }
        } else if (jobStatus.setChargingConstraintSatisfied(millis, z)) {
          this.mChangedJobs.add(jobStatus);
        }
      }
      if (jobStatus.hasBatteryNotLowConstraint()
          && jobStatus.setBatteryNotLowConstraintSatisfied(millis, isBatteryNotLow)) {
        this.mChangedJobs.add(jobStatus);
      }
    }
    if (z || isBatteryNotLow) {
      this.mStateChangedListener.onRunJobNow(null);
    } else if (this.mChangedJobs.size() > 0) {
      this.mStateChangedListener.onControllerStateChanged(this.mChangedJobs);
    }
    this.mChangedJobs.clear();
  }

  public final class PowerTracker extends BroadcastReceiver {
    public boolean mPowerConnected;

    public PowerTracker() {}

    public void startTracking() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
      intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
      BatteryController.this.mContext.registerReceiver(this, intentFilter);
      this.mPowerConnected =
          ((BatteryManagerInternal) LocalServices.getService(BatteryManagerInternal.class))
              .isPowered(15);
    }

    public boolean isPowerConnected() {
      return this.mPowerConnected;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      synchronized (BatteryController.this.mLock) {
        String action = intent.getAction();
        if ("android.intent.action.ACTION_POWER_CONNECTED".equals(action)) {
          if (BatteryController.DEBUG) {
            Slog.d(
                "JobScheduler.Battery",
                "Power connected @ " + JobSchedulerService.sElapsedRealtimeClock.millis());
          }
          if (this.mPowerConnected) {
            return;
          } else {
            this.mPowerConnected = true;
          }
        } else if ("android.intent.action.ACTION_POWER_DISCONNECTED".equals(action)) {
          if (BatteryController.DEBUG) {
            Slog.d(
                "JobScheduler.Battery",
                "Power disconnected @ " + JobSchedulerService.sElapsedRealtimeClock.millis());
          }
          if (!this.mPowerConnected) {
            return;
          } else {
            this.mPowerConnected = false;
          }
        }
        BatteryController.this.maybeReportNewChargingStateLocked();
      }
    }
  }

  public ArraySet getTrackedJobs() {
    return this.mTrackedTasks;
  }

  public ArraySet getTopStartedJobs() {
    return this.mTopStartedJobs;
  }

  @Override // com.android.server.job.controllers.StateController
  public void dumpControllerStateLocked(
      IndentingPrintWriter indentingPrintWriter, Predicate predicate) {
    indentingPrintWriter.println("Power connected: " + this.mPowerTracker.isPowerConnected());
    StringBuilder sb = new StringBuilder();
    sb.append("Stable power: ");
    sb.append(this.mService.isBatteryCharging() && this.mService.isBatteryNotLow());
    indentingPrintWriter.println(sb.toString());
    indentingPrintWriter.println("Not low: " + this.mService.isBatteryNotLow());
    for (int i = 0; i < this.mTrackedTasks.size(); i++) {
      JobStatus jobStatus = (JobStatus) this.mTrackedTasks.valueAt(i);
      if (predicate.test(jobStatus)) {
        indentingPrintWriter.print("#");
        jobStatus.printUniqueId(indentingPrintWriter);
        indentingPrintWriter.print(" from ");
        UserHandle.formatUid(indentingPrintWriter, jobStatus.getSourceUid());
        indentingPrintWriter.println();
      }
    }
  }

  @Override // com.android.server.job.controllers.StateController
  public void dumpControllerStateLocked(
      ProtoOutputStream protoOutputStream, long j, Predicate predicate) {
    long start = protoOutputStream.start(j);
    long start2 = protoOutputStream.start(1146756268034L);
    protoOutputStream.write(
        1133871366145L, this.mService.isBatteryCharging() && this.mService.isBatteryNotLow());
    protoOutputStream.write(1133871366146L, this.mService.isBatteryNotLow());
    for (int i = 0; i < this.mTrackedTasks.size(); i++) {
      JobStatus jobStatus = (JobStatus) this.mTrackedTasks.valueAt(i);
      if (predicate.test(jobStatus)) {
        long start3 = protoOutputStream.start(2246267895813L);
        jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
        protoOutputStream.write(1120986464258L, jobStatus.getSourceUid());
        protoOutputStream.end(start3);
      }
    }
    protoOutputStream.end(start2);
    protoOutputStream.end(start);
  }
}
