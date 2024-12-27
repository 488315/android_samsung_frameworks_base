package com.android.server.job;

import com.android.server.job.controllers.JobStatus;
import com.android.server.job.controllers.StateController;

import java.util.ArrayList;
import java.util.function.Consumer;

public final /* synthetic */ class JobSchedulerService$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ JobSchedulerService f$0;

    public /* synthetic */ JobSchedulerService$$ExternalSyntheticLambda0(
            JobSchedulerService jobSchedulerService, int i) {
        this.$r8$classId = i;
        this.f$0 = jobSchedulerService;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                JobSchedulerService jobSchedulerService = this.f$0;
                JobStatus jobStatus = (JobStatus) obj;
                for (int i = 0; i < ((ArrayList) jobSchedulerService.mControllers).size(); i++) {
                    ((StateController) ((ArrayList) jobSchedulerService.mControllers).get(i))
                            .maybeStartTrackingJobLocked(jobStatus, null);
                }
                break;
            case 1:
                JobSchedulerService jobSchedulerService2 = this.f$0;
                JobStatus jobStatus2 = (JobStatus) obj;
                jobSchedulerService2.getClass();
                if (jobStatus2.updateMediaBackupExemptionStatus()) {
                    jobSchedulerService2.mChangedJobList.add(jobStatus2);
                    break;
                }
                break;
            default:
                this.f$0.cancelJobImplLocked((JobStatus) obj, null, 13, 7, "user removed");
                break;
        }
    }
}
