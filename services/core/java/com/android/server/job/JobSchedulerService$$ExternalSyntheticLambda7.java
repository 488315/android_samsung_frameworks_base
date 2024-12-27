package com.android.server.job;

import android.os.Handler;

import com.android.server.job.controllers.JobStatus;

import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class JobSchedulerService$$ExternalSyntheticLambda7
        implements Runnable {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ JobStatus f$1;
    public final /* synthetic */ CountDownLatch f$2;
    public final /* synthetic */ Handler f$3;
    public final /* synthetic */ long f$4 = 1000;

    public /* synthetic */ JobSchedulerService$$ExternalSyntheticLambda7(
            int i, JobStatus jobStatus, CountDownLatch countDownLatch, Handler handler) {
        this.f$0 = i;
        this.f$1 = jobStatus;
        this.f$2 = countDownLatch;
        this.f$3 = handler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f$0;
        JobStatus jobStatus = this.f$1;
        CountDownLatch countDownLatch = this.f$2;
        Handler handler = this.f$3;
        long j = this.f$4;
        if (i <= 0 || jobStatus.isConstraintsSatisfied(jobStatus.mSatisfiedConstraintsOfInterest)) {
            countDownLatch.countDown();
        } else {
            handler.postDelayed(
                    new JobSchedulerService$$ExternalSyntheticLambda7(
                            i - 1, jobStatus, countDownLatch, handler),
                    j);
        }
    }
}
