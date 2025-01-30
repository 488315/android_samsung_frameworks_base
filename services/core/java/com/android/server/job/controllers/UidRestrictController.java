package com.android.server.job.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.server.job.JobSchedulerService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public final class UidRestrictController extends StateController {
    public static final boolean DEBUG;
    public static final ArraySet sIgnoreUids;
    public UidRestrictOperationListener mListener;
    public final ArraySet mRestrictedUids;
    public final HashMap mTrackedTasks;

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.UidRestrict", 3);
        sIgnoreUids = new ArraySet(new Integer[]{1000});
    }

    public static boolean isUidIgnored(int i) {
        return sIgnoreUids.contains(Integer.valueOf(i));
    }

    public UidRestrictController(JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mTrackedTasks = new HashMap();
        this.mRestrictedUids = new ArraySet();
        this.mListener = null;
        UidRestrictOperationListener uidRestrictOperationListener = new UidRestrictOperationListener();
        this.mListener = uidRestrictOperationListener;
        uidRestrictOperationListener.startTracking();
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        int sourceUid = jobStatus.getSourceUid();
        if (isUidIgnored(sourceUid)) {
            return;
        }
        long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
        ArraySet arraySet = (ArraySet) this.mTrackedTasks.get(Integer.valueOf(sourceUid));
        if (arraySet == null) {
            arraySet = new ArraySet();
            this.mTrackedTasks.put(Integer.valueOf(sourceUid), arraySet);
        }
        arraySet.add(jobStatus);
        jobStatus.setTrackingController(256);
        jobStatus.setUidRestrictConstraintSatisfied(millis, !this.mRestrictedUids.contains(Integer.valueOf(sourceUid)));
    }

    @Override // com.android.server.job.controllers.StateController
    public void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        if (jobStatus.clearTrackingController(256)) {
            ArraySet arraySet = (ArraySet) this.mTrackedTasks.get(Integer.valueOf(jobStatus.getSourceUid()));
            if (arraySet != null) {
                arraySet.remove(jobStatus);
            }
        }
    }

    public boolean setUidRestrictedLocked(int i, boolean z) {
        if (this.mRestrictedUids.contains(Integer.valueOf(i)) == z) {
            return false;
        }
        if (z) {
            this.mRestrictedUids.add(Integer.valueOf(i));
        } else {
            this.mRestrictedUids.remove(new Integer(i));
        }
        updateUidRestrictConstraintUidLocked(i);
        return true;
    }

    public final void updateUidRestrictConstraintUidLocked(int i) {
        ArraySet arraySet = (ArraySet) this.mTrackedTasks.get(Integer.valueOf(i));
        ArraySet arraySet2 = new ArraySet();
        if (arraySet != null) {
            long millis = JobSchedulerService.sElapsedRealtimeClock.millis();
            boolean z = !this.mRestrictedUids.contains(Integer.valueOf(i));
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                JobStatus jobStatus = (JobStatus) it.next();
                if (jobStatus.setUidRestrictConstraintSatisfied(millis, z)) {
                    arraySet2.add(jobStatus);
                }
            }
        }
        if (arraySet2.isEmpty()) {
            return;
        }
        this.mStateChangedListener.onControllerStateChanged(arraySet2);
    }

    public final class UidRestrictOperationListener extends BroadcastReceiver {
        public UidRestrictOperationListener() {
        }

        public void startTracking() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.ACTION_JOB_RESTRICT_UID");
            UidRestrictController.this.mContext.registerReceiver(this, intentFilter);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            onReceiveInternal(intent);
        }

        public void onReceiveInternal(Intent intent) {
            synchronized (UidRestrictController.this.mLock) {
                if ("android.intent.ACTION_JOB_RESTRICT_UID".equals(intent.getAction())) {
                    int intExtra = intent.getIntExtra("uid", -1);
                    boolean booleanExtra = intent.getBooleanExtra("restrict", false);
                    if (intExtra > 0) {
                        if (UidRestrictController.DEBUG) {
                            Slog.d("JobScheduler.UidRestrict", "Pending jobs: u=" + intExtra + ", restrict=" + booleanExtra);
                        }
                        UidRestrictController.this.setUidRestrictedLocked(intExtra, booleanExtra);
                    }
                }
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(IndentingPrintWriter indentingPrintWriter, Predicate predicate) {
        indentingPrintWriter.println("Restricted uids: ");
        Iterator it = this.mRestrictedUids.iterator();
        while (it.hasNext()) {
            indentingPrintWriter.println("--" + ((Integer) it.next()));
        }
        indentingPrintWriter.println();
        indentingPrintWriter.println("Tracking jobs:");
        for (Map.Entry entry : this.mTrackedTasks.entrySet()) {
            ArraySet arraySet = (ArraySet) entry.getValue();
            if (!arraySet.isEmpty()) {
                indentingPrintWriter.println("  uid=" + entry.getKey() + "(" + arraySet.size() + "):");
                for (int i = 0; i < arraySet.size(); i++) {
                    JobStatus jobStatus = (JobStatus) arraySet.valueAt(i);
                    if (predicate.test(jobStatus)) {
                        indentingPrintWriter.print("  --#");
                        jobStatus.printUniqueId(indentingPrintWriter);
                        indentingPrintWriter.print(" from ");
                        UserHandle.formatUid(indentingPrintWriter, jobStatus.getSourceUid());
                        indentingPrintWriter.println();
                    }
                }
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public void dumpControllerStateLocked(ProtoOutputStream protoOutputStream, long j, Predicate predicate) {
        long start = protoOutputStream.start(j);
        long start2 = protoOutputStream.start(1146756268042L);
        Iterator it = this.mTrackedTasks.entrySet().iterator();
        while (it.hasNext()) {
            ArraySet arraySet = (ArraySet) ((Map.Entry) it.next()).getValue();
            if (!arraySet.isEmpty()) {
                for (int i = 0; i < arraySet.size(); i++) {
                    JobStatus jobStatus = (JobStatus) arraySet.valueAt(i);
                    if (predicate.test(jobStatus)) {
                        long start3 = protoOutputStream.start(2246267895813L);
                        jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                        protoOutputStream.write(1120986464258L, jobStatus.getSourceUid());
                        protoOutputStream.write(1133871366147L, this.mRestrictedUids.contains(Integer.valueOf(jobStatus.getSourceUid())));
                        protoOutputStream.end(start3);
                    }
                }
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }
}
