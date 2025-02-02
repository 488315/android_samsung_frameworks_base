package com.android.server.job.controllers.idle;

import android.R;
import android.app.AlarmManager;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.job.JobSchedulerService;
import java.io.PrintWriter;
import java.util.Set;

/* loaded from: classes2.dex */
public final class DeviceIdlenessTracker extends BroadcastReceiver implements IdlenessTracker {
  public static final boolean DEBUG;
  public AlarmManager mAlarm;
  public boolean mDockIdle;
  public boolean mIdle;
  public IdlenessListener mIdleListener;
  public long mIdleWindowSlop;
  public long mInactivityIdleThreshold;
  public PowerManager mPowerManager;
  public boolean mProjectionActive;
  public final UiModeManager.OnProjectionStateChangedListener mOnProjectionStateChangedListener =
      new UiModeManager
          .OnProjectionStateChangedListener() { // from class:
                                                // com.android.server.job.controllers.idle.DeviceIdlenessTracker$$ExternalSyntheticLambda0
        public final void onProjectionStateChanged(int i, Set set) {
          DeviceIdlenessTracker.this.onProjectionStateChanged(i, set);
        }
      };
  public AlarmManager.OnAlarmListener mIdleAlarmListener =
      new AlarmManager
          .OnAlarmListener() { // from class:
                               // com.android.server.job.controllers.idle.DeviceIdlenessTracker$$ExternalSyntheticLambda1
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
          DeviceIdlenessTracker.this.lambda$new$0();
        }
      };
  public boolean mScreenOn = true;

  static {
    DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.DeviceIdlenessTracker", 3);
  }

  @Override // com.android.server.job.controllers.idle.IdlenessTracker
  public boolean isIdle() {
    return this.mIdle;
  }

  @Override // com.android.server.job.controllers.idle.IdlenessTracker
  public void startTracking(Context context, IdlenessListener idlenessListener) {
    this.mIdleListener = idlenessListener;
    this.mInactivityIdleThreshold =
        context.getResources().getInteger(R.integer.config_longPressOnBackBehavior);
    this.mIdleWindowSlop = context.getResources().getInteger(R.integer.config_lockSoundVolumeDb);
    this.mAlarm = (AlarmManager) context.getSystemService("alarm");
    this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.SCREEN_ON");
    intentFilter.addAction("android.intent.action.SCREEN_OFF");
    intentFilter.addAction("android.intent.action.DREAMING_STARTED");
    intentFilter.addAction("android.intent.action.DREAMING_STOPPED");
    intentFilter.addAction("com.android.server.ACTION_TRIGGER_IDLE");
    intentFilter.addAction("android.intent.action.DOCK_IDLE");
    intentFilter.addAction("android.intent.action.DOCK_ACTIVE");
    context.registerReceiver(this, intentFilter, null, AppSchedulingModuleThread.getHandler());
    ((UiModeManager) context.getSystemService(UiModeManager.class))
        .addOnProjectionStateChangedListener(
            -1, AppSchedulingModuleThread.getExecutor(), this.mOnProjectionStateChangedListener);
  }

  public final void onProjectionStateChanged(int i, Set set) {
    boolean z = i != 0;
    if (this.mProjectionActive == z) {
      return;
    }
    if (DEBUG) {
      Slog.v("JobScheduler.DeviceIdlenessTracker", "Projection state changed: " + z);
    }
    this.mProjectionActive = z;
    if (z) {
      cancelIdlenessCheck();
      if (this.mIdle) {
        this.mIdle = false;
        this.mIdleListener.reportNewIdleState(false);
        return;
      }
      return;
    }
    maybeScheduleIdlenessCheck("Projection ended");
  }

  @Override // com.android.server.job.controllers.idle.IdlenessTracker
  public void dump(PrintWriter printWriter) {
    printWriter.print("  mIdle: ");
    printWriter.println(this.mIdle);
    printWriter.print("  mScreenOn: ");
    printWriter.println(this.mScreenOn);
    printWriter.print("  mDockIdle: ");
    printWriter.println(this.mDockIdle);
    printWriter.print("  mProjectionActive: ");
    printWriter.println(this.mProjectionActive);
  }

  @Override // com.android.server.job.controllers.idle.IdlenessTracker
  public void dump(ProtoOutputStream protoOutputStream, long j) {
    long start = protoOutputStream.start(j);
    long start2 = protoOutputStream.start(1146756268033L);
    protoOutputStream.write(1133871366145L, this.mIdle);
    protoOutputStream.write(1133871366146L, this.mScreenOn);
    protoOutputStream.write(1133871366147L, this.mDockIdle);
    protoOutputStream.write(1133871366149L, this.mProjectionActive);
    protoOutputStream.end(start2);
    protoOutputStream.end(start);
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Removed duplicated region for block: B:39:0x0094  */
  /* JADX WARN: Removed duplicated region for block: B:42:0x00a0  */
  @Override // android.content.BroadcastReceiver
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void onReceive(Context context, Intent intent) {
    String action;
    boolean z;
    action = intent.getAction();
    z = DEBUG;
    if (z) {
      Slog.v("JobScheduler.DeviceIdlenessTracker", "Received action: " + action);
    }
    action.hashCode();
    switch (action) {
      case "android.intent.action.SCREEN_OFF":
      case "android.intent.action.DOCK_IDLE":
      case "android.intent.action.DREAMING_STARTED":
        if (action.equals("android.intent.action.DOCK_IDLE")) {
          if (this.mScreenOn) {
            this.mDockIdle = true;
          }
        } else {
          this.mScreenOn = false;
          this.mDockIdle = false;
        }
        maybeScheduleIdlenessCheck(action);
        break;
      case "android.intent.action.SCREEN_ON":
        this.mScreenOn = true;
        this.mDockIdle = false;
        if (z) {
          Slog.v("JobScheduler.DeviceIdlenessTracker", "exiting idle");
        }
        cancelIdlenessCheck();
        if (this.mIdle) {
          this.mIdle = false;
          this.mIdleListener.reportNewIdleState(false);
          break;
        }
        break;
      case "android.intent.action.DREAMING_STOPPED":
        if (!this.mPowerManager.isInteractive()) {}
        this.mScreenOn = true;
        this.mDockIdle = false;
        if (z) {}
        cancelIdlenessCheck();
        if (this.mIdle) {}
        break;
      case "com.android.server.ACTION_TRIGGER_IDLE":
        lambda$new$0();
        break;
      case "android.intent.action.DOCK_ACTIVE":
        if (!this.mScreenOn) {}
        if (!this.mPowerManager.isInteractive()) {}
        this.mScreenOn = true;
        this.mDockIdle = false;
        if (z) {}
        cancelIdlenessCheck();
        if (this.mIdle) {}
        break;
    }
  }

  public final void maybeScheduleIdlenessCheck(String str) {
    if ((!this.mScreenOn || this.mDockIdle) && !this.mProjectionActive) {
      long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
      long j = millis + this.mInactivityIdleThreshold;
      if (DEBUG) {
        Slog.v(
            "JobScheduler.DeviceIdlenessTracker",
            "Scheduling idle : " + str + " now:" + millis + " when=" + j);
      }
      this.mAlarm.setWindow(
          2,
          j,
          this.mIdleWindowSlop,
          "JS idleness",
          AppSchedulingModuleThread.getExecutor(),
          this.mIdleAlarmListener);
    }
  }

  public final void cancelIdlenessCheck() {
    this.mAlarm.cancel(this.mIdleAlarmListener);
  }

  /* renamed from: handleIdleTrigger, reason: merged with bridge method [inline-methods] */
  public final void lambda$new$0() {
    if (!this.mIdle && ((!this.mScreenOn || this.mDockIdle) && !this.mProjectionActive)) {
      if (DEBUG) {
        Slog.v(
            "JobScheduler.DeviceIdlenessTracker",
            "Idle trigger fired @ " + JobSchedulerService.sElapsedRealtimeClock.millis());
      }
      this.mIdle = true;
      this.mIdleListener.reportNewIdleState(true);
      return;
    }
    if (DEBUG) {
      Slog.v(
          "JobScheduler.DeviceIdlenessTracker",
          "TRIGGER_IDLE received but not changing state; idle="
              + this.mIdle
              + " screen="
              + this.mScreenOn
              + " projection="
              + this.mProjectionActive);
    }
  }
}
