package com.android.server.job.controllers;

import android.app.ActivityManagerInternal;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.net.Uri;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArrayMap;
import android.util.proto.ProtoOutputStream;

import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.server.AppStateTracker;
import com.android.server.AppStateTrackerImpl;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;
import com.android.server.job.JobStore;
import com.android.server.pm.PackageManagerService;

import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BackgroundJobsController extends StateController {
    public static final boolean DEBUG;
    public final ActivityManagerInternal mActivityManagerInternal;
    public final AppStateTrackerImpl mAppStateTracker;
    public final AnonymousClass1 mBroadcastReceiver;
    public final AnonymousClass2 mForceAppStandbyListener;
    public final PackageManagerInternal mPackageManagerInternal;
    public final SparseArrayMap mPackageStoppedState;
    public final UpdateJobFunctor mUpdateJobFunctor;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UpdateJobFunctor implements Consumer {
        public int mActiveState;
        public final ArraySet mChangedJobs = new ArraySet();
        public int mTotalCount = 0;
        public int mCheckedCount = 0;
        public long mUpdateTimeElapsed = 0;

        public UpdateJobFunctor() {}

        @Override // java.util.function.Consumer
        public final void accept(Object obj) {
            JobStatus jobStatus = (JobStatus) obj;
            this.mTotalCount++;
            this.mCheckedCount++;
            if (BackgroundJobsController.this.updateSingleJobRestrictionLocked(
                    jobStatus, this.mUpdateTimeElapsed, this.mActiveState)) {
                this.mChangedJobs.add(jobStatus);
            }
        }
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.Background", 3);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.server.job.controllers.BackgroundJobsController$1] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.server.job.controllers.BackgroundJobsController$2] */
    public BackgroundJobsController(JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mPackageStoppedState = new SparseArrayMap();
        this.mUpdateJobFunctor = new UpdateJobFunctor();
        this.mBroadcastReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.job.controllers.BackgroundJobsController.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        boolean z = JobSchedulerService.DEBUG;
                        Uri data = intent.getData();
                        String schemeSpecificPart =
                                data != null ? data.getSchemeSpecificPart() : null;
                        int intExtra = intent.getIntExtra("android.intent.extra.UID", -1);
                        String action = intent.getAction();
                        if (intExtra == -1) {
                            Slog.e(
                                    "JobScheduler.Background",
                                    "Didn't get package UID in intent (" + action + ")");
                            return;
                        }
                        if (BackgroundJobsController.DEBUG) {
                            BootReceiver$$ExternalSyntheticOutline0.m(
                                    StorageManagerService$$ExternalSyntheticOutline0.m(
                                            intExtra, "Got ", action, " for ", "/"),
                                    schemeSpecificPart,
                                    "JobScheduler.Background");
                        }
                        action.getClass();
                        if (action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                            synchronized (BackgroundJobsController.this.mLock) {
                                BackgroundJobsController.this.mPackageStoppedState.delete(
                                        intExtra, schemeSpecificPart);
                                BackgroundJobsController backgroundJobsController =
                                        BackgroundJobsController.this;
                                backgroundJobsController.getClass();
                                backgroundJobsController.updateJobRestrictionsLocked(intExtra, 2);
                            }
                            return;
                        }
                        if (action.equals("android.intent.action.PACKAGE_UNSTOPPED")) {
                            synchronized (BackgroundJobsController.this.mLock) {
                                BackgroundJobsController.this.mPackageStoppedState.add(
                                        intExtra, schemeSpecificPart, Boolean.FALSE);
                                BackgroundJobsController.this.updateJobRestrictionsLocked(
                                        intExtra, 0);
                            }
                        }
                    }
                };
        this.mForceAppStandbyListener =
                new AppStateTrackerImpl
                        .Listener() { // from class:
                                      // com.android.server.job.controllers.BackgroundJobsController.2
                    @Override // com.android.server.AppStateTrackerImpl.Listener
                    public final void updateAllJobs() {
                        synchronized (BackgroundJobsController.this.mLock) {
                            BackgroundJobsController.this.updateJobRestrictionsLocked(-1, 0);
                        }
                    }

                    @Override // com.android.server.AppStateTrackerImpl.Listener
                    public final void updateJobsForUid(int i, boolean z) {
                        synchronized (BackgroundJobsController.this.mLock) {
                            BackgroundJobsController backgroundJobsController =
                                    BackgroundJobsController.this;
                            backgroundJobsController.getClass();
                            backgroundJobsController.updateJobRestrictionsLocked(i, z ? 1 : 2);
                        }
                    }

                    @Override // com.android.server.AppStateTrackerImpl.Listener
                    public final void updateJobsForUidPackage(int i, boolean z) {
                        synchronized (BackgroundJobsController.this.mLock) {
                            BackgroundJobsController backgroundJobsController =
                                    BackgroundJobsController.this;
                            backgroundJobsController.getClass();
                            backgroundJobsController.updateJobRestrictionsLocked(i, z ? 1 : 2);
                        }
                    }
                };
        ActivityManagerInternal activityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        Objects.requireNonNull(activityManagerInternal);
        this.mActivityManagerInternal = activityManagerInternal;
        AppStateTracker appStateTracker =
                (AppStateTracker) LocalServices.getService(AppStateTracker.class);
        Objects.requireNonNull(appStateTracker);
        this.mAppStateTracker = (AppStateTrackerImpl) appStateTracker;
        this.mPackageManagerInternal =
                (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            final IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        indentingPrintWriter.println("Aconfig flags:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print(
                "android.content.pm.stay_stopped", Boolean.valueOf(Flags.stayStopped()));
        indentingPrintWriter.println();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
        this.mAppStateTracker.dump(indentingPrintWriter);
        indentingPrintWriter.println();
        indentingPrintWriter.println("Stopped packages:");
        indentingPrintWriter.increaseIndent();
        this.mPackageStoppedState.forEach(
                new SparseArrayMap
                        .TriConsumer() { // from class:
                                         // com.android.server.job.controllers.BackgroundJobsController$$ExternalSyntheticLambda0
                    public final void accept(int i, Object obj, Object obj2) {
                        IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                        indentingPrintWriter2.print(i);
                        indentingPrintWriter2.print(":");
                        indentingPrintWriter2.print((String) obj);
                        indentingPrintWriter2.print("=");
                        indentingPrintWriter2.println((Boolean) obj2);
                    }
                });
        indentingPrintWriter.println();
        this.mService.mJobs.forEachJob(
                jobSchedulerService$$ExternalSyntheticLambda5,
                new BackgroundJobsController$$ExternalSyntheticLambda1(
                        this, indentingPrintWriter, 0));
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            ProtoOutputStream protoOutputStream,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        long start = protoOutputStream.start(2246267895812L);
        long start2 = protoOutputStream.start(1146756268033L);
        this.mAppStateTracker.dumpProto(protoOutputStream, 1146756268033L);
        this.mService.mJobs.forEachJob(
                jobSchedulerService$$ExternalSyntheticLambda5,
                new BackgroundJobsController$$ExternalSyntheticLambda1(this, protoOutputStream, 1));
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void evaluateStateLocked(JobStatus jobStatus) {
        if (jobStatus.isRequestedExpeditedJob()) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            updateSingleJobRestrictionLocked(jobStatus, SystemClock.elapsedRealtime(), 0);
        }
    }

    public final boolean isPackageStoppedLocked(int i, String str) {
        if (this.mPackageStoppedState.contains(i, str)) {
            return ((Boolean) this.mPackageStoppedState.get(i, str)).booleanValue();
        }
        try {
            boolean isPackageStoppedForUser =
                    ((PackageManagerService.PackageManagerInternalImpl)
                                    this.mPackageManagerInternal)
                            .mService
                            .snapshotComputer()
                            .isPackageStoppedForUser(str, i);
            if (DEBUG) {
                Slog.d(
                        "JobScheduler.Background",
                        "Pulled stopped state of "
                                + str
                                + " ("
                                + i
                                + "): "
                                + isPackageStoppedForUser);
            }
            this.mPackageStoppedState.add(i, str, Boolean.valueOf(isPackageStoppedForUser));
            return isPackageStoppedForUser;
        } catch (PackageManager.NameNotFoundException unused) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "Couldn't determine stopped state for unknown package: ",
                    str,
                    "JobScheduler.Background");
            return false;
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        updateSingleJobRestrictionLocked(jobStatus, SystemClock.elapsedRealtime(), 0);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {}

    @Override // com.android.server.job.controllers.StateController
    public final void onAppRemovedLocked(int i, String str) {
        this.mPackageStoppedState.delete(i, str);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void onUserRemovedLocked(int i) {
        for (int numMaps = this.mPackageStoppedState.numMaps() - 1; numMaps >= 0; numMaps--) {
            if (UserHandle.getUserId(this.mPackageStoppedState.keyAt(numMaps)) == i) {
                this.mPackageStoppedState.deleteAt(numMaps);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void startTrackingLocked() {
        this.mAppStateTracker.addListener(this.mForceAppStandbyListener);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.PACKAGE_UNSTOPPED");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiverAsUser(
                this.mBroadcastReceiver, UserHandle.ALL, intentFilter, null, null);
    }

    public final void updateJobRestrictionsLocked(int i, int i2) {
        UpdateJobFunctor updateJobFunctor = this.mUpdateJobFunctor;
        updateJobFunctor.mActiveState = i2;
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        updateJobFunctor.mUpdateTimeElapsed = SystemClock.elapsedRealtime();
        updateJobFunctor.mChangedJobs.clear();
        updateJobFunctor.mTotalCount = 0;
        updateJobFunctor.mCheckedCount = 0;
        boolean z = DEBUG;
        long elapsedRealtimeNanos = z ? SystemClock.elapsedRealtimeNanos() : 0L;
        JobStore jobStore = this.mService.mJobs;
        if (i > 0) {
            jobStore.forEachJobForSourceUid(i, updateJobFunctor);
        } else {
            jobStore.forEachJob(updateJobFunctor);
        }
        long elapsedRealtimeNanos2 =
                z ? SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos : 0L;
        if (z) {
            Slog.d(
                    "JobScheduler.Background",
                    String.format(
                            "Job status updated: %d/%d checked/total jobs, %d us",
                            Integer.valueOf(updateJobFunctor.mCheckedCount),
                            Integer.valueOf(updateJobFunctor.mTotalCount),
                            Long.valueOf(elapsedRealtimeNanos2 / 1000)));
        }
        if (updateJobFunctor.mChangedJobs.size() > 0) {
            this.mStateChangedListener.onControllerStateChanged(updateJobFunctor.mChangedJobs);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0089  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateSingleJobRestrictionLocked(
            com.android.server.job.controllers.JobStatus r9, long r10, int r12) {
        /*
            Method dump skipped, instructions count: 201
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.job.controllers.BackgroundJobsController.updateSingleJobRestrictionLocked(com.android.server.job.controllers.JobStatus,"
                    + " long, int):boolean");
    }
}
