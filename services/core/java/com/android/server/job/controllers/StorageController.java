package com.android.server.job.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;

import com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticOutline0;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;

public final class StorageController extends StateController {
    public static final boolean DEBUG;
    public final StorageTracker mStorageTracker;
    public final ArraySet mTrackedTasks;

    public final class StorageTracker extends BroadcastReceiver {
        public int mLastStorageSeq = -1;
        public boolean mStorageLow;

        public StorageTracker() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            onReceiveInternal(intent);
        }

        public void onReceiveInternal(Intent intent) {
            String action = intent.getAction();
            this.mLastStorageSeq = intent.getIntExtra("seq", this.mLastStorageSeq);
            if ("android.intent.action.DEVICE_STORAGE_LOW".equals(action)) {
                if (StorageController.DEBUG) {
                    StringBuilder sb =
                            new StringBuilder("Available storage too low to do work. @ ");
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    sb.append(SystemClock.elapsedRealtime());
                    Slog.d("JobScheduler.Storage", sb.toString());
                }
                this.mStorageLow = true;
                StorageController.m630$$Nest$mmaybeReportNewStorageState(StorageController.this);
                return;
            }
            if ("android.intent.action.DEVICE_STORAGE_OK".equals(action)) {
                if (StorageController.DEBUG) {
                    StringBuilder sb2 =
                            new StringBuilder("Available storage high enough to do work. @ ");
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    sb2.append(SystemClock.elapsedRealtime());
                    Slog.d("JobScheduler.Storage", sb2.toString());
                }
                this.mStorageLow = false;
                StorageController.m630$$Nest$mmaybeReportNewStorageState(StorageController.this);
            }
        }
    }

    /* renamed from: -$$Nest$mmaybeReportNewStorageState, reason: not valid java name */
    public static void m630$$Nest$mmaybeReportNewStorageState(StorageController storageController) {
        boolean z;
        storageController.getClass();
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        boolean z2 = !storageController.mStorageTracker.mStorageLow;
        synchronized (storageController.mLock) {
            try {
                z = false;
                for (int size = storageController.mTrackedTasks.size() - 1; size >= 0; size--) {
                    z |=
                            ((JobStatus) storageController.mTrackedTasks.valueAt(size))
                                    .setConstraintSatisfied(8, elapsedRealtime, z2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z2) {
            storageController.mStateChangedListener.onRunJobNow(null);
        } else if (z) {
            storageController.mStateChangedListener.onControllerStateChanged(
                    storageController.mTrackedTasks);
        }
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.Storage", 3);
    }

    public StorageController(JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mTrackedTasks = new ArraySet();
        this.mStorageTracker = new StorageTracker();
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        StringBuilder sb = new StringBuilder("Not low: ");
        StorageTracker storageTracker = this.mStorageTracker;
        StringBuilder m =
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        sb, !storageTracker.mStorageLow, indentingPrintWriter, "Sequence: ");
        m.append(storageTracker.mLastStorageSeq);
        indentingPrintWriter.println(m.toString());
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
        long start2 = protoOutputStream.start(1146756268039L);
        StorageTracker storageTracker = this.mStorageTracker;
        protoOutputStream.write(1133871366145L, !storageTracker.mStorageLow);
        protoOutputStream.write(1120986464258L, storageTracker.mLastStorageSeq);
        for (int i = 0; i < this.mTrackedTasks.size(); i++) {
            JobStatus jobStatus = (JobStatus) this.mTrackedTasks.valueAt(i);
            if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                long start3 = protoOutputStream.start(2246267895811L);
                jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1120986464258L, jobStatus.sourceUid);
                protoOutputStream.end(start3);
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    public StorageTracker getTracker() {
        return this.mStorageTracker;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.hasConstraint(8)) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.mTrackedTasks.add(jobStatus);
            jobStatus.setTrackingController(16);
            jobStatus.setConstraintSatisfied(8, elapsedRealtime, !this.mStorageTracker.mStorageLow);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(16)) {
            this.mTrackedTasks.remove(jobStatus);
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void startTrackingLocked() {
        StorageTracker storageTracker = this.mStorageTracker;
        storageTracker.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.DEVICE_STORAGE_LOW");
        intentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
        StorageController.this.mContext.registerReceiver(storageTracker, intentFilter);
    }
}
