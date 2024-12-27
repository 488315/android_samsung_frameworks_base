package com.android.server.job.controllers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UidRestrictController extends StateController {
    public static final boolean DEBUG;
    public static final ArraySet sIgnoreUids;
    public final ArraySet mRestrictedUids;
    public final HashMap mTrackedTasks;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidRestrictOperationListener extends BroadcastReceiver {
        public UidRestrictOperationListener() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            onReceiveInternal(intent);
        }

        public void onReceiveInternal(Intent intent) {
            synchronized (UidRestrictController.this.mLock) {
                try {
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
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    static {
        DEBUG = JobSchedulerService.DEBUG || Log.isLoggable("JobScheduler.UidRestrict", 3);
        sIgnoreUids = new ArraySet(new Integer[]{1000});
    }

    public UidRestrictController(JobSchedulerService jobSchedulerService) {
        super(jobSchedulerService);
        this.mTrackedTasks = new HashMap();
        this.mRestrictedUids = new ArraySet();
        this.mContext.registerReceiver(new UidRestrictOperationListener(), BatteryService$$ExternalSyntheticOutline0.m("android.intent.ACTION_JOB_RESTRICT_UID"));
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(IndentingPrintWriter indentingPrintWriter, JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5) {
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
                    if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                        indentingPrintWriter.print("  --#");
                        jobStatus.printUniqueId(indentingPrintWriter);
                        indentingPrintWriter.print(" from ");
                        UserHandle.formatUid(indentingPrintWriter, jobStatus.sourceUid);
                        indentingPrintWriter.println();
                    }
                }
            }
        }
    }

    @Override // com.android.server.job.controllers.StateController
    public final void dumpControllerStateLocked(ProtoOutputStream protoOutputStream, JobSchedulerService$$ExternalSyntheticLambda5 jobSchedulerService$$ExternalSyntheticLambda5) {
        long start = protoOutputStream.start(2246267895812L);
        long start2 = protoOutputStream.start(1146756268042L);
        Iterator it = this.mTrackedTasks.entrySet().iterator();
        while (it.hasNext()) {
            ArraySet arraySet = (ArraySet) ((Map.Entry) it.next()).getValue();
            if (!arraySet.isEmpty()) {
                for (int i = 0; i < arraySet.size(); i++) {
                    JobStatus jobStatus = (JobStatus) arraySet.valueAt(i);
                    if (jobSchedulerService$$ExternalSyntheticLambda5.test(jobStatus)) {
                        long start3 = protoOutputStream.start(2246267895813L);
                        jobStatus.writeToShortProto(protoOutputStream, 1146756268033L);
                        int i2 = jobStatus.sourceUid;
                        protoOutputStream.write(1120986464258L, i2);
                        protoOutputStream.write(1133871366147L, this.mRestrictedUids.contains(Integer.valueOf(i2)));
                        protoOutputStream.end(start3);
                    }
                }
            }
        }
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStartTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        int i = jobStatus.sourceUid;
        if (sIgnoreUids.contains(Integer.valueOf(i))) {
            return;
        }
        JobSchedulerService.sElapsedRealtimeClock.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        ArraySet arraySet = (ArraySet) this.mTrackedTasks.get(Integer.valueOf(i));
        if (arraySet == null) {
            arraySet = new ArraySet();
            this.mTrackedTasks.put(Integer.valueOf(i), arraySet);
        }
        arraySet.add(jobStatus);
        jobStatus.setTrackingController(256);
        jobStatus.setConstraintSatisfied(1048576, elapsedRealtime, !this.mRestrictedUids.contains(Integer.valueOf(i)));
    }

    @Override // com.android.server.job.controllers.StateController
    public final void maybeStopTrackingJobLocked(JobStatus jobStatus, JobStatus jobStatus2) {
        ArraySet arraySet;
        if (!jobStatus.clearTrackingController(256) || (arraySet = (ArraySet) this.mTrackedTasks.get(Integer.valueOf(jobStatus.sourceUid))) == null) {
            return;
        }
        arraySet.remove(jobStatus);
    }

    public final void setUidRestrictedLocked(int i, boolean z) {
        if (this.mRestrictedUids.contains(Integer.valueOf(i)) != z) {
            if (z) {
                this.mRestrictedUids.add(Integer.valueOf(i));
            } else {
                this.mRestrictedUids.remove(Integer.valueOf(i));
            }
            ArraySet arraySet = (ArraySet) this.mTrackedTasks.get(Integer.valueOf(i));
            ArraySet arraySet2 = new ArraySet();
            if (arraySet != null) {
                JobSchedulerService.sElapsedRealtimeClock.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                boolean z2 = !this.mRestrictedUids.contains(Integer.valueOf(i));
                Iterator it = arraySet.iterator();
                while (it.hasNext()) {
                    JobStatus jobStatus = (JobStatus) it.next();
                    if (jobStatus.setConstraintSatisfied(1048576, elapsedRealtime, z2)) {
                        arraySet2.add(jobStatus);
                    }
                }
            }
            if (arraySet2.isEmpty()) {
                return;
            }
            this.mStateChangedListener.onControllerStateChanged(arraySet2);
        }
    }
}
