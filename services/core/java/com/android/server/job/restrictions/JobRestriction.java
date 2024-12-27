package com.android.server.job.restrictions;

import android.util.IndentingPrintWriter;

import com.android.server.job.JobSchedulerService;
import com.android.server.job.controllers.JobStatus;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class JobRestriction {
    public final int mInternalReason;
    public final int mPendingReason;
    public final JobSchedulerService mService;
    public final int mStopReason;

    public JobRestriction(JobSchedulerService jobSchedulerService, int i, int i2, int i3) {
        this.mService = jobSchedulerService;
        this.mPendingReason = i2;
        this.mStopReason = i;
        this.mInternalReason = i3;
    }

    public abstract void dumpConstants(IndentingPrintWriter indentingPrintWriter);

    public abstract boolean isJobRestricted(JobStatus jobStatus, int i);

    public void onSystemServicesReady() {}
}
