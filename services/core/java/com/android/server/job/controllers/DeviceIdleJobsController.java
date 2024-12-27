package com.android.server.job.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.jobs.ArrayUtils;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleInternal;
import com.android.server.LocalServices;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class DeviceIdleJobsController extends StateController {
    public static final boolean DEBUG;
    public final ArraySet mAllowInIdleJobs;
    public final AnonymousClass1 mBroadcastReceiver;
    public boolean mDeviceIdleMode;
    public final DeviceIdleUpdateFunctor mDeviceIdleUpdateFunctor;
    public int[] mDeviceIdleWhitelistAppIds;
    public final SparseBooleanArray mForegroundUids;
    public final DeviceIdleJobsDelayHandler mHandler;
    public final DeviceIdleInternal mLocalDeviceIdleController;
    public final PowerManager mPowerManager;
    public int[] mPowerSaveTempWhitelistAppIds;
    public final DeviceIdleJobsController$$ExternalSyntheticLambda0 mShouldRushEvaluation;

    public final class DeviceIdleJobsDelayHandler extends Handler {
        public DeviceIdleJobsDelayHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            synchronized (DeviceIdleJobsController.this.mLock) {
                try {
                    DeviceIdleJobsController.this.mDeviceIdleUpdateFunctor.prepare();
                    DeviceIdleJobsController deviceIdleJobsController =
                            DeviceIdleJobsController.this;
                    deviceIdleJobsController.mService.mJobs.forEachJob(
                            deviceIdleJobsController.mDeviceIdleUpdateFunctor);
                    if (DeviceIdleJobsController.this.mDeviceIdleUpdateFunctor.mChangedJobs.size()
                            > 0) {
                        DeviceIdleJobsController deviceIdleJobsController2 =
                                DeviceIdleJobsController.this;
                        deviceIdleJobsController2.mStateChangedListener.onControllerStateChanged(
                                deviceIdleJobsController2.mDeviceIdleUpdateFunctor.mChangedJobs);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class DeviceIdleUpdateFunctor implements Consumer {
        public final ArraySet mChangedJobs = new ArraySet();
        public long mUpdateTimeElapsed = 0;

        public DeviceIdleUpdateFunctor() {}

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            JobStatus jobStatus = (JobStatus) obj;
            if (DeviceIdleJobsController.this.updateTaskStateLocked(
                    jobStatus, this.mUpdateTimeElapsed)) {
                this.mChangedJobs.add(jobStatus);
            }
        }

        public final void prepare() {
            this.mChangedJobs.clear();
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            this.mUpdateTimeElapsed = SystemClock.elapsedRealtime();
        }
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.DeviceIdle", 3);
    }

    public DeviceIdleJobsController(JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mForegroundUids = new SparseBooleanArray();
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.job.controllers.DeviceIdleJobsController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        int i;
                        String action = intent.getAction();
                        action.getClass();
                        switch (action) {
                            case "android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED":
                                synchronized (DeviceIdleJobsController.this.mLock) {
                                    try {
                                        DeviceIdleJobsController deviceIdleJobsController =
                                                DeviceIdleJobsController.this;
                                        deviceIdleJobsController.mPowerSaveTempWhitelistAppIds =
                                                deviceIdleJobsController.mLocalDeviceIdleController
                                                        .getPowerSaveTempWhitelistAppIds();
                                        if (DeviceIdleJobsController.DEBUG) {
                                            Slog.d(
                                                    "JobScheduler.DeviceIdle",
                                                    "Got temp whitelist "
                                                            + Arrays.toString(
                                                                    DeviceIdleJobsController.this
                                                                            .mPowerSaveTempWhitelistAppIds));
                                        }
                                        ArraySet arraySet = new ArraySet();
                                        JobSchedulerService.sElapsedRealtimeClock.getClass();
                                        long elapsedRealtime = SystemClock.elapsedRealtime();
                                        while (i
                                                < DeviceIdleJobsController.this.mAllowInIdleJobs
                                                        .size()) {
                                            DeviceIdleJobsController deviceIdleJobsController2 =
                                                    DeviceIdleJobsController.this;
                                            if (deviceIdleJobsController2.updateTaskStateLocked(
                                                    (JobStatus)
                                                            deviceIdleJobsController2
                                                                    .mAllowInIdleJobs.valueAt(i),
                                                    elapsedRealtime)) {
                                                arraySet.add(
                                                        (JobStatus)
                                                                DeviceIdleJobsController.this
                                                                        .mAllowInIdleJobs.valueAt(
                                                                        i));
                                            }
                                            i++;
                                        }
                                        if (arraySet.size() > 0) {
                                            DeviceIdleJobsController.this.mStateChangedListener
                                                    .onControllerStateChanged(arraySet);
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            case "android.os.action.POWER_SAVE_WHITELIST_CHANGED":
                                synchronized (DeviceIdleJobsController.this.mLock) {
                                    try {
                                        DeviceIdleJobsController deviceIdleJobsController3 =
                                                DeviceIdleJobsController.this;
                                        deviceIdleJobsController3.mDeviceIdleWhitelistAppIds =
                                                deviceIdleJobsController3.mLocalDeviceIdleController
                                                        .getPowerSaveWhitelistUserAppIds();
                                        if (DeviceIdleJobsController.DEBUG) {
                                            Slog.d(
                                                    "JobScheduler.DeviceIdle",
                                                    "Got whitelist "
                                                            + Arrays.toString(
                                                                    DeviceIdleJobsController.this
                                                                            .mDeviceIdleWhitelistAppIds));
                                        }
                                    } finally {
                                    }
                                }
                                return;
                            case "android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED":
                            case "android.os.action.DEVICE_IDLE_MODE_CHANGED":
                                DeviceIdleJobsController deviceIdleJobsController4 =
                                        DeviceIdleJobsController.this;
                                PowerManager powerManager = deviceIdleJobsController4.mPowerManager;
                                boolean z =
                                        powerManager != null
                                                && (powerManager.isDeviceIdleMode()
                                                        || DeviceIdleJobsController.this
                                                                .mPowerManager
                                                                .isLightDeviceIdleMode());
                                synchronized (deviceIdleJobsController4.mLock) {
                                    try {
                                        i = deviceIdleJobsController4.mDeviceIdleMode != z ? 1 : 0;
                                        deviceIdleJobsController4.mDeviceIdleMode = z;
                                        StateController.logDeviceWideConstraintStateToStatsd(
                                                33554432, !z);
                                        if (DeviceIdleJobsController.DEBUG) {
                                            Slog.d(
                                                    "JobScheduler.DeviceIdle",
                                                    "mDeviceIdleMode="
                                                            + deviceIdleJobsController4
                                                                    .mDeviceIdleMode);
                                        }
                                        deviceIdleJobsController4.mDeviceIdleUpdateFunctor
                                                .prepare();
                                        if (z) {
                                            deviceIdleJobsController4.mHandler.removeMessages(1);
                                            deviceIdleJobsController4.mService.mJobs.forEachJob(
                                                    deviceIdleJobsController4
                                                            .mDeviceIdleUpdateFunctor);
                                        } else {
                                            deviceIdleJobsController4.mService.mJobs.forEachJob(
                                                    deviceIdleJobsController4.mShouldRushEvaluation,
                                                    deviceIdleJobsController4
                                                            .mDeviceIdleUpdateFunctor);
                                            deviceIdleJobsController4.mHandler
                                                    .sendEmptyMessageDelayed(1, 3000L);
                                        }
                                    } finally {
                                    }
                                }
                                if (i != 0) {
                                    JobSchedulerService jobSchedulerService2 =
                                            deviceIdleJobsController4.mStateChangedListener;
                                    synchronized (jobSchedulerService2.mLock) {
                                        try {
                                            if (JobSchedulerService.DEBUG) {
                                                Slog.d("JobScheduler", "Doze state changed: " + z);
                                            }
                                            if (!z && jobSchedulerService2.mReadyToRock) {
                                                DeviceIdleInternal deviceIdleInternal =
                                                        jobSchedulerService2
                                                                .mLocalDeviceIdleController;
                                                if (deviceIdleInternal != null
                                                        && !jobSchedulerService2.mReportedActive) {
                                                    jobSchedulerService2.mReportedActive = true;
                                                    deviceIdleInternal.setJobsActive(true);
                                                }
                                                jobSchedulerService2
                                                        .mHandler
                                                        .obtainMessage(1)
                                                        .sendToTarget();
                                            }
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                return;
                            default:
                                return;
                        }
                    }
                };
        this.mShouldRushEvaluation =
                new Predicate() { // from class:
                                  // com.android.server.job.controllers.DeviceIdleJobsController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        DeviceIdleJobsController deviceIdleJobsController =
                                DeviceIdleJobsController.this;
                        JobStatus jobStatus = (JobStatus) obj;
                        deviceIdleJobsController.getClass();
                        return jobStatus.isRequestedExpeditedJob()
                                || deviceIdleJobsController.mForegroundUids.get(
                                        jobStatus.sourceUid);
                    }
                };
        this.mHandler = new DeviceIdleJobsDelayHandler(AppSchedulingModuleThread.get().getLooper());
        this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
        DeviceIdleInternal deviceIdleInternal =
                (DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class);
        this.mLocalDeviceIdleController = deviceIdleInternal;
        this.mDeviceIdleWhitelistAppIds = deviceIdleInternal.getPowerSaveWhitelistUserAppIds();
        this.mPowerSaveTempWhitelistAppIds = deviceIdleInternal.getPowerSaveTempWhitelistAppIds();
        this.mDeviceIdleUpdateFunctor = new DeviceIdleUpdateFunctor();
        this.mAllowInIdleJobs = new ArraySet();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
        intentFilter.addAction("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_WHITELIST_CHANGED");
        intentFilter.addAction("android.os.action.POWER_SAVE_TEMP_WHITELIST_CHANGED");
        this.mContext.registerReceiverAsUser(
                broadcastReceiver, UserHandle.ALL, intentFilter, null, null);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        indentingPrintWriter.println("Idle mode: " + this.mDeviceIdleMode);
        indentingPrintWriter.println();
        this.mService.mJobs.forEachJob(
                jobSchedulerService$$ExternalSyntheticLambda5,
                new DeviceIdleJobsController$$ExternalSyntheticLambda1(
                        this, indentingPrintWriter, 0));
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            ProtoOutputStream protoOutputStream,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        long start = protoOutputStream.start(2246267895812L);
        long start2 = protoOutputStream.start(1146756268037L);
        protoOutputStream.write(1133871366145L, this.mDeviceIdleMode);
        this.mService.mJobs.forEachJob(
                jobSchedulerService$$ExternalSyntheticLambda5,
                new DeviceIdleJobsController$$ExternalSyntheticLambda1(this, protoOutputStream, 1));
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if ((jobStatus.job.getFlags() & 2) != 0) {
            this.mAllowInIdleJobs.add(jobStatus);
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        updateTaskStateLocked(jobStatus, SystemClock.elapsedRealtime());
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if ((jobStatus.job.getFlags() & 2) != 0) {
            this.mAllowInIdleJobs.remove(jobStatus);
        }
    }

    public final void setUidActiveLocked(int i, boolean z) {
        if (z != this.mForegroundUids.get(i)) {
            if (DEBUG) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        BatteryService$$ExternalSyntheticOutline0.m(i, "uid ", " going "),
                        z ? "active" : "inactive",
                        "JobScheduler.DeviceIdle");
            }
            this.mForegroundUids.put(i, z);
            DeviceIdleUpdateFunctor deviceIdleUpdateFunctor = this.mDeviceIdleUpdateFunctor;
            deviceIdleUpdateFunctor.prepare();
            this.mService.mJobs.forEachJobForSourceUid(i, deviceIdleUpdateFunctor);
            if (deviceIdleUpdateFunctor.mChangedJobs.size() > 0) {
                this.mStateChangedListener.onControllerStateChanged(
                        deviceIdleUpdateFunctor.mChangedJobs);
            }
        }
    }

    public final boolean updateTaskStateLocked(JobStatus jobStatus, long j) {
        int flags = jobStatus.job.getFlags() & 2;
        int i = jobStatus.sourceUid;
        boolean z =
                flags != 0
                        && (this.mForegroundUids.get(i)
                                || ArrayUtils.contains(
                                        this.mPowerSaveTempWhitelistAppIds,
                                        UserHandle.getAppId(i)));
        boolean z2 =
                Arrays.binarySearch(this.mDeviceIdleWhitelistAppIds, UserHandle.getAppId(i)) >= 0;
        boolean z3 = !this.mDeviceIdleMode || z2 || z;
        jobStatus.appHasDozeExemption = z2;
        if (!jobStatus.setConstraintSatisfied(33554432, j, z3)) {
            return false;
        }
        jobStatus.mReadyNotDozing = z3 || jobStatus.canRunInDoze();
        return true;
    }
}
