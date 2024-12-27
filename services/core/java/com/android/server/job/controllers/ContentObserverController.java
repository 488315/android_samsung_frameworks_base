package com.android.server.job.controllers;

import android.app.job.JobInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;

import com.android.server.AppSchedulingModuleThread;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;

public final class ContentObserverController extends StateController {
    public static final boolean DEBUG;
    public final Handler mHandler;
    public final SparseArray mObservers;
    public final ArraySet mTrackedTasks;

    public final class JobInstance {
        public ArraySet mChangedAuthorities;
        public ArraySet mChangedUris;
        public final JobStatus mJobStatus;
        public boolean mTriggerPending;
        public final ArrayList mMyObservers = new ArrayList();
        public final TriggerRunnable mExecuteRunner = new TriggerRunnable(this);
        public final TriggerRunnable mTimeoutRunner = new TriggerRunnable(this);

        public JobInstance(JobStatus jobStatus) {
            this.mJobStatus = jobStatus;
            JobInfo.TriggerContentUri[] triggerContentUris = jobStatus.job.getTriggerContentUris();
            SparseArray sparseArray = ContentObserverController.this.mObservers;
            int i = jobStatus.sourceUserId;
            ArrayMap arrayMap = (ArrayMap) sparseArray.get(i);
            if (arrayMap == null) {
                arrayMap = new ArrayMap();
                ContentObserverController.this.mObservers.put(i, arrayMap);
            }
            if (triggerContentUris != null) {
                for (JobInfo.TriggerContentUri triggerContentUri : triggerContentUris) {
                    ObserverInstance observerInstance =
                            (ObserverInstance) arrayMap.get(triggerContentUri);
                    if (observerInstance == null) {
                        observerInstance =
                                ContentObserverController.this
                                .new ObserverInstance(
                                        ContentObserverController.this.mHandler,
                                        triggerContentUri,
                                        i);
                        arrayMap.put(triggerContentUri, observerInstance);
                        boolean z = (triggerContentUri.getFlags() & 1) != 0;
                        if (ContentObserverController.DEBUG) {
                            StringBuilder sb = new StringBuilder("New observer ");
                            sb.append(observerInstance);
                            sb.append(" for ");
                            sb.append(triggerContentUri.getUri());
                            sb.append(" andDescendants=");
                            sb.append(z);
                            sb.append(" sourceUserId=");
                            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                    sb, i, "JobScheduler.ContentObserver");
                        }
                        ContentObserverController.this
                                .mContext
                                .getContentResolver()
                                .registerContentObserver(
                                        triggerContentUri.getUri(), z, observerInstance, i);
                    } else if (ContentObserverController.DEBUG) {
                        Slog.v(
                                "JobScheduler.ContentObserver",
                                "Reusing existing observer "
                                        + observerInstance
                                        + " for "
                                        + triggerContentUri.getUri()
                                        + " andDescendants="
                                        + ((triggerContentUri.getFlags() & 1) != 0));
                    }
                    observerInstance.mJobs.add(this);
                    this.mMyObservers.add(observerInstance);
                }
            }
        }

        public final void detachLocked() {
            int size = this.mMyObservers.size();
            for (int i = 0; i < size; i++) {
                ObserverInstance observerInstance = (ObserverInstance) this.mMyObservers.get(i);
                observerInstance.mJobs.remove(this);
                if (observerInstance.mJobs.size() == 0) {
                    if (ContentObserverController.DEBUG) {
                        Slog.i(
                                "JobScheduler.ContentObserver",
                                "Unregistering observer "
                                        + observerInstance
                                        + " for "
                                        + observerInstance.mUri.getUri());
                    }
                    ContentObserverController contentObserverController =
                            ContentObserverController.this;
                    contentObserverController
                            .mContext
                            .getContentResolver()
                            .unregisterContentObserver(observerInstance);
                    ArrayMap arrayMap =
                            (ArrayMap)
                                    contentObserverController.mObservers.get(
                                            observerInstance.mUserId);
                    if (arrayMap != null) {
                        arrayMap.remove(observerInstance.mUri);
                    }
                }
            }
        }

        public final void scheduleLocked() {
            boolean z = this.mTriggerPending;
            JobStatus jobStatus = this.mJobStatus;
            ContentObserverController contentObserverController = ContentObserverController.this;
            if (!z) {
                this.mTriggerPending = true;
                contentObserverController.mHandler.postDelayed(
                        this.mTimeoutRunner, jobStatus.getTriggerContentMaxDelay());
            }
            Handler handler = contentObserverController.mHandler;
            TriggerRunnable triggerRunnable = this.mExecuteRunner;
            handler.removeCallbacks(triggerRunnable);
            int size = this.mChangedUris.size();
            Handler handler2 = contentObserverController.mHandler;
            if (size >= 40) {
                handler2.post(triggerRunnable);
            } else {
                handler2.postDelayed(triggerRunnable, jobStatus.getTriggerContentUpdateDelay());
            }
        }
    }

    public final class ObserverInstance extends ContentObserver {
        public final ArraySet mJobs;
        public final JobInfo.TriggerContentUri mUri;
        public final int mUserId;

        public ObserverInstance(
                Handler handler, JobInfo.TriggerContentUri triggerContentUri, int i) {
            super(handler);
            this.mJobs = new ArraySet();
            this.mUri = triggerContentUri;
            this.mUserId = i;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (ContentObserverController.DEBUG) {
                StringBuilder sb = new StringBuilder("onChange(self=");
                sb.append(z);
                sb.append(") for ");
                sb.append(uri);
                sb.append(" when mUri=");
                sb.append(this.mUri);
                sb.append(" mUserId=");
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        sb, this.mUserId, "JobScheduler.ContentObserver");
            }
            synchronized (ContentObserverController.this.mLock) {
                try {
                    int size = this.mJobs.size();
                    for (int i = 0; i < size; i++) {
                        JobInstance jobInstance = (JobInstance) this.mJobs.valueAt(i);
                        if (jobInstance.mChangedUris == null) {
                            jobInstance.mChangedUris = new ArraySet();
                        }
                        if (jobInstance.mChangedUris.size() < 50) {
                            jobInstance.mChangedUris.add(uri);
                        }
                        if (jobInstance.mChangedAuthorities == null) {
                            jobInstance.mChangedAuthorities = new ArraySet();
                        }
                        jobInstance.mChangedAuthorities.add(uri.getAuthority());
                        jobInstance.scheduleLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class TriggerRunnable implements Runnable {
        public final JobInstance mInstance;

        public TriggerRunnable(JobInstance jobInstance) {
            this.mInstance = jobInstance;
        }

        @Override // java.lang.Runnable
        public final void run() {
            boolean z;
            JobInstance jobInstance = this.mInstance;
            synchronized (ContentObserverController.this.mLock) {
                try {
                    z = false;
                    if (jobInstance.mTriggerPending) {
                        JobSchedulerService.sElapsedRealtimeClock.getClass();
                        boolean constraintSatisfied =
                                jobInstance.mJobStatus.setConstraintSatisfied(
                                        67108864, SystemClock.elapsedRealtime(), true);
                        if (jobInstance.mTriggerPending) {
                            ContentObserverController contentObserverController =
                                    ContentObserverController.this;
                            contentObserverController.mHandler.removeCallbacks(
                                    jobInstance.mExecuteRunner);
                            contentObserverController.mHandler.removeCallbacks(
                                    jobInstance.mTimeoutRunner);
                            jobInstance.mTriggerPending = false;
                        }
                        z = constraintSatisfied;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (z) {
                ArraySet arraySet = new ArraySet();
                arraySet.add(jobInstance.mJobStatus);
                ContentObserverController.this.mStateChangedListener.onControllerStateChanged(
                        arraySet);
            }
        }
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.ContentObserver", 3);
    }

    public ContentObserverController(JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mTrackedTasks = new ArraySet();
        this.mObservers = new SparseArray();
        this.mHandler = new Handler(AppSchedulingModuleThread.get().getLooper());
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            IndentingPrintWriter indentingPrintWriter,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        Predicate predicate = jobSchedulerService$$ExternalSyntheticLambda5;
        for (int i = 0; i < this.mTrackedTasks.size(); i++) {
            JobStatus jobStatus = (JobStatus) this.mTrackedTasks.valueAt(i);
            if (predicate.test(jobStatus)) {
                indentingPrintWriter.print("#");
                jobStatus.printUniqueId(indentingPrintWriter);
                indentingPrintWriter.print(" from ");
                UserHandle.formatUid(indentingPrintWriter, jobStatus.sourceUid);
                indentingPrintWriter.println();
            }
        }
        indentingPrintWriter.println();
        int size = this.mObservers.size();
        if (size > 0) {
            indentingPrintWriter.println("Observers:");
            indentingPrintWriter.increaseIndent();
            int i2 = 0;
            while (i2 < size) {
                ArrayMap arrayMap = (ArrayMap) this.mObservers.get(this.mObservers.keyAt(i2));
                int size2 = arrayMap.size();
                int i3 = 0;
                while (i3 < size2) {
                    ObserverInstance observerInstance = (ObserverInstance) arrayMap.valueAt(i3);
                    int size3 = observerInstance.mJobs.size();
                    int i4 = 0;
                    while (true) {
                        if (i4 >= size3) {
                            break;
                        }
                        if (predicate.test(
                                ((JobInstance) observerInstance.mJobs.valueAt(i4)).mJobStatus)) {
                            JobInfo.TriggerContentUri triggerContentUri =
                                    (JobInfo.TriggerContentUri) arrayMap.keyAt(i3);
                            indentingPrintWriter.print(triggerContentUri.getUri());
                            indentingPrintWriter.print(" 0x");
                            indentingPrintWriter.print(
                                    Integer.toHexString(triggerContentUri.getFlags()));
                            indentingPrintWriter.print(" (");
                            indentingPrintWriter.print(System.identityHashCode(observerInstance));
                            indentingPrintWriter.println("):");
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.println("Jobs:");
                            indentingPrintWriter.increaseIndent();
                            for (int i5 = 0; i5 < size3; i5++) {
                                JobInstance jobInstance =
                                        (JobInstance) observerInstance.mJobs.valueAt(i5);
                                indentingPrintWriter.print("#");
                                jobInstance.mJobStatus.printUniqueId(indentingPrintWriter);
                                indentingPrintWriter.print(" from ");
                                JobStatus jobStatus2 = jobInstance.mJobStatus;
                                UserHandle.formatUid(indentingPrintWriter, jobStatus2.sourceUid);
                                if (jobInstance.mChangedAuthorities != null) {
                                    indentingPrintWriter.println(":");
                                    indentingPrintWriter.increaseIndent();
                                    if (jobInstance.mTriggerPending) {
                                        indentingPrintWriter.print("Trigger pending: update=");
                                        TimeUtils.formatDuration(
                                                jobStatus2.getTriggerContentUpdateDelay(),
                                                indentingPrintWriter);
                                        indentingPrintWriter.print(", max=");
                                        TimeUtils.formatDuration(
                                                jobStatus2.getTriggerContentMaxDelay(),
                                                indentingPrintWriter);
                                        indentingPrintWriter.println();
                                    }
                                    indentingPrintWriter.println("Changed Authorities:");
                                    for (int i6 = 0;
                                            i6 < jobInstance.mChangedAuthorities.size();
                                            i6++) {
                                        indentingPrintWriter.println(
                                                (String)
                                                        jobInstance.mChangedAuthorities.valueAt(
                                                                i6));
                                    }
                                    if (jobInstance.mChangedUris != null) {
                                        indentingPrintWriter.println("          Changed URIs:");
                                        for (int i7 = 0;
                                                i7 < jobInstance.mChangedUris.size();
                                                i7++) {
                                            indentingPrintWriter.println(
                                                    jobInstance.mChangedUris.valueAt(i7));
                                        }
                                    }
                                    indentingPrintWriter.decreaseIndent();
                                } else {
                                    indentingPrintWriter.println();
                                }
                            }
                            indentingPrintWriter.decreaseIndent();
                            indentingPrintWriter.decreaseIndent();
                        } else {
                            i4++;
                            predicate = jobSchedulerService$$ExternalSyntheticLambda5;
                        }
                    }
                    i3++;
                    predicate = jobSchedulerService$$ExternalSyntheticLambda5;
                }
                i2++;
                predicate = jobSchedulerService$$ExternalSyntheticLambda5;
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(
            ProtoOutputStream protoOutputStream,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5) {
        long j;
        long j2;
        ArrayMap arrayMap;
        int i;
        ObserverInstance observerInstance;
        ContentObserverController contentObserverController = this;
        Predicate predicate = jobSchedulerService$$ExternalSyntheticLambda5;
        long start = protoOutputStream.start(2246267895812L);
        long start2 = protoOutputStream.start(1146756268036L);
        for (int i2 = 0; i2 < contentObserverController.mTrackedTasks.size(); i2++) {
            JobStatus jobStatus = (JobStatus) contentObserverController.mTrackedTasks.valueAt(i2);
            if (predicate.test(jobStatus)) {
                long start3 = protoOutputStream.start(2246267895809L);
                jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1120986464258L, jobStatus.sourceUid);
                protoOutputStream.end(start3);
            }
        }
        int size = contentObserverController.mObservers.size();
        int i3 = 0;
        while (i3 < size) {
            int i4 = size;
            long start4 = protoOutputStream.start(2246267895810L);
            int keyAt = contentObserverController.mObservers.keyAt(i3);
            protoOutputStream.write(1120986464257L, keyAt);
            ArrayMap arrayMap2 = (ArrayMap) contentObserverController.mObservers.get(keyAt);
            int size2 = arrayMap2.size();
            int i5 = 0;
            while (i5 < size2) {
                ObserverInstance observerInstance2 = (ObserverInstance) arrayMap2.valueAt(i5);
                int size3 = observerInstance2.mJobs.size();
                int i6 = 0;
                while (true) {
                    if (i6 >= size3) {
                        j = start;
                        j2 = start2;
                        arrayMap = arrayMap2;
                        i = size2;
                        break;
                    }
                    if (predicate.test(
                            ((JobInstance) observerInstance2.mJobs.valueAt(i6)).mJobStatus)) {
                        j = start;
                        j2 = start2;
                        long start5 = protoOutputStream.start(2246267895810L);
                        JobInfo.TriggerContentUri triggerContentUri =
                                (JobInfo.TriggerContentUri) arrayMap2.keyAt(i5);
                        Uri uri = triggerContentUri.getUri();
                        if (uri != null) {
                            protoOutputStream.write(1138166333441L, uri.toString());
                        }
                        protoOutputStream.write(1120986464258L, triggerContentUri.getFlags());
                        int i7 = 0;
                        while (i7 < size3) {
                            long start6 = protoOutputStream.start(2246267895811L);
                            JobInstance jobInstance =
                                    (JobInstance) observerInstance2.mJobs.valueAt(i7);
                            ArrayMap arrayMap3 = arrayMap2;
                            int i8 = size2;
                            jobInstance.mJobStatus.writeToShortProto(
                                    protoOutputStream, 1146756268033L);
                            JobStatus jobStatus2 = jobInstance.mJobStatus;
                            ObserverInstance observerInstance3 = observerInstance2;
                            int i9 = size3;
                            protoOutputStream.write(1120986464258L, jobStatus2.sourceUid);
                            if (jobInstance.mChangedAuthorities == null) {
                                protoOutputStream.end(start6);
                                observerInstance = observerInstance3;
                            } else {
                                if (jobInstance.mTriggerPending) {
                                    observerInstance = observerInstance3;
                                    protoOutputStream.write(
                                            1112396529667L,
                                            jobStatus2.getTriggerContentUpdateDelay());
                                    protoOutputStream.write(
                                            1112396529668L, jobStatus2.getTriggerContentMaxDelay());
                                } else {
                                    observerInstance = observerInstance3;
                                }
                                for (int i10 = 0;
                                        i10 < jobInstance.mChangedAuthorities.size();
                                        i10++) {
                                    protoOutputStream.write(
                                            2237677961221L,
                                            (String) jobInstance.mChangedAuthorities.valueAt(i10));
                                }
                                if (jobInstance.mChangedUris != null) {
                                    for (int i11 = 0;
                                            i11 < jobInstance.mChangedUris.size();
                                            i11++) {
                                        Uri uri2 = (Uri) jobInstance.mChangedUris.valueAt(i11);
                                        if (uri2 != null) {
                                            protoOutputStream.write(
                                                    2237677961222L, uri2.toString());
                                        }
                                    }
                                }
                                protoOutputStream.end(start6);
                            }
                            i7++;
                            arrayMap2 = arrayMap3;
                            size2 = i8;
                            size3 = i9;
                            observerInstance2 = observerInstance;
                        }
                        arrayMap = arrayMap2;
                        i = size2;
                        protoOutputStream.end(start5);
                    } else {
                        i6++;
                        predicate = jobSchedulerService$$ExternalSyntheticLambda5;
                    }
                }
                i5++;
                predicate = jobSchedulerService$$ExternalSyntheticLambda5;
                start2 = j2;
                start = j;
                arrayMap2 = arrayMap;
                size2 = i;
            }
            protoOutputStream.end(start4);
            i3++;
            contentObserverController = this;
            predicate = jobSchedulerService$$ExternalSyntheticLambda5;
            size = i4;
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        JobInstance jobInstance;
        if (jobStatus.hasContentTriggerConstraint()) {
            JobSchedulerService.sElapsedRealtimeClock.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (jobStatus.contentObserverJobInstance == null) {
                jobStatus.contentObserverJobInstance = new JobInstance(jobStatus);
            }
            if (DEBUG) {
                Slog.i("JobScheduler.ContentObserver", "Tracking content-trigger job " + jobStatus);
            }
            this.mTrackedTasks.add(jobStatus);
            jobStatus.setTrackingController(4);
            JobInstance jobInstance2 = jobStatus.contentObserverJobInstance;
            ArraySet arraySet = jobInstance2.mChangedAuthorities;
            boolean z = true;
            boolean z2 = arraySet != null;
            if (jobStatus.changedAuthorities != null) {
                if (arraySet == null) {
                    jobInstance2.mChangedAuthorities = new ArraySet();
                }
                Iterator it = jobStatus.changedAuthorities.iterator();
                while (it.hasNext()) {
                    jobStatus.contentObserverJobInstance.mChangedAuthorities.add(
                            (String) it.next());
                }
                if (jobStatus.changedUris != null) {
                    JobInstance jobInstance3 = jobStatus.contentObserverJobInstance;
                    if (jobInstance3.mChangedUris == null) {
                        jobInstance3.mChangedUris = new ArraySet();
                    }
                    Iterator it2 = jobStatus.changedUris.iterator();
                    while (it2.hasNext()) {
                        jobStatus.contentObserverJobInstance.mChangedUris.add((Uri) it2.next());
                    }
                }
            } else {
                z = z2;
            }
            jobStatus.changedAuthorities = null;
            jobStatus.changedUris = null;
            jobStatus.setConstraintSatisfied(67108864, elapsedRealtime, z);
        }
        if (jobStatus2 == null || (jobInstance = jobStatus2.contentObserverJobInstance) == null) {
            return;
        }
        jobInstance.detachLocked();
        jobStatus2.contentObserverJobInstance = null;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(4)) {
            this.mTrackedTasks.remove(jobStatus);
            JobInstance jobInstance = jobStatus.contentObserverJobInstance;
            if (jobInstance != null) {
                if (jobInstance.mTriggerPending) {
                    ContentObserverController contentObserverController =
                            ContentObserverController.this;
                    contentObserverController.mHandler.removeCallbacks(jobInstance.mExecuteRunner);
                    contentObserverController.mHandler.removeCallbacks(jobInstance.mTimeoutRunner);
                    jobInstance.mTriggerPending = false;
                }
                if (jobStatus2 != null) {
                    JobInstance jobInstance2 = jobStatus.contentObserverJobInstance;
                    if (jobInstance2 != null && jobInstance2.mChangedAuthorities != null) {
                        if (jobStatus2.contentObserverJobInstance == null) {
                            jobStatus2.contentObserverJobInstance = new JobInstance(jobStatus2);
                        }
                        JobInstance jobInstance3 = jobStatus2.contentObserverJobInstance;
                        JobInstance jobInstance4 = jobStatus.contentObserverJobInstance;
                        jobInstance3.mChangedAuthorities = jobInstance4.mChangedAuthorities;
                        jobInstance3.mChangedUris = jobInstance4.mChangedUris;
                        jobInstance4.mChangedAuthorities = null;
                        jobInstance4.mChangedUris = null;
                    }
                } else {
                    jobStatus.contentObserverJobInstance.detachLocked();
                    jobStatus.contentObserverJobInstance = null;
                }
            }
            if (DEBUG) {
                Slog.i("JobScheduler.ContentObserver", "No longer tracking job " + jobStatus);
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void prepareForExecutionLocked(JobStatus jobStatus) {
        JobInstance jobInstance;
        if (!jobStatus.hasContentTriggerConstraint()
                || (jobInstance = jobStatus.contentObserverJobInstance) == null) {
            return;
        }
        jobStatus.changedUris = jobInstance.mChangedUris;
        jobStatus.changedAuthorities = jobInstance.mChangedAuthorities;
        jobInstance.mChangedUris = null;
        jobInstance.mChangedAuthorities = null;
    }

    @Override // com.android.server.job.controllers.StateController
    public final void rescheduleForFailureLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus2.hasContentTriggerConstraint() && jobStatus.hasContentTriggerConstraint()) {
            jobStatus.changedAuthorities = jobStatus2.changedAuthorities;
            jobStatus.changedUris = jobStatus2.changedUris;
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void unprepareFromExecutionLocked(JobStatus jobStatus) {
        JobInstance jobInstance;
        if (!jobStatus.hasContentTriggerConstraint()
                || (jobInstance = jobStatus.contentObserverJobInstance) == null) {
            return;
        }
        ArraySet arraySet = jobInstance.mChangedUris;
        if (arraySet == null) {
            jobInstance.mChangedUris = jobStatus.changedUris;
        } else {
            arraySet.addAll(jobStatus.changedUris);
        }
        JobInstance jobInstance2 = jobStatus.contentObserverJobInstance;
        ArraySet arraySet2 = jobInstance2.mChangedAuthorities;
        if (arraySet2 == null) {
            jobInstance2.mChangedAuthorities = jobStatus.changedAuthorities;
        } else {
            arraySet2.addAll(jobStatus.changedAuthorities);
        }
        jobStatus.changedUris = null;
        jobStatus.changedAuthorities = null;
    }
}
