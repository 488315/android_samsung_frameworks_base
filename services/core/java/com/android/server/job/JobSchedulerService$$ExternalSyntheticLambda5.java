package com.android.server.job;

import android.os.UserHandle;
import com.android.server.job.controllers.JobStatus;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class JobSchedulerService$$ExternalSyntheticLambda5 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int f$0;

    public /* synthetic */ JobSchedulerService$$ExternalSyntheticLambda5(int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = i;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        int i2 = this.f$0;
        JobStatus jobStatus = (JobStatus) obj;
        switch (i) {
            case 0:
                if (i2 == -1 || UserHandle.getAppId(jobStatus.callingUid) == i2 || UserHandle.getAppId(jobStatus.sourceUid) == i2) {
                }
                break;
            case 1:
                if (i2 == -1 || UserHandle.getAppId(jobStatus.callingUid) == i2 || UserHandle.getAppId(jobStatus.sourceUid) == i2) {
                }
                break;
            default:
                if (UserHandle.getUserId(jobStatus.callingUid) == i2 || jobStatus.sourceUserId == i2) {
                }
                break;
        }
        return true;
    }
}
