package com.android.server.job;

import java.util.Comparator;

public final /* synthetic */ class JobConcurrencyManager$$ExternalSyntheticLambda2
        implements Comparator {
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0029, code lost:

       if (r1.lastEvaluatedBias == 40) goto L9;
    */
    @Override // java.util.Comparator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int compare(java.lang.Object r5, java.lang.Object r6) {
        /*
            r4 = this;
            com.android.server.job.JobConcurrencyManager$ContextAssignment r5 = (com.android.server.job.JobConcurrencyManager.ContextAssignment) r5
            com.android.server.job.JobConcurrencyManager$ContextAssignment r6 = (com.android.server.job.JobConcurrencyManager.ContextAssignment) r6
            r4 = 0
            if (r5 != r6) goto L8
            goto L34
        L8:
            com.android.server.job.JobServiceContext r5 = r5.context
            com.android.server.job.controllers.JobStatus r0 = r5.mRunningJob
            com.android.server.job.JobServiceContext r6 = r6.context
            com.android.server.job.controllers.JobStatus r1 = r6.mRunningJob
            r2 = 1
            if (r0 != 0) goto L18
            if (r1 != 0) goto L16
            goto L34
        L16:
            r4 = r2
            goto L34
        L18:
            r4 = -1
            if (r1 != 0) goto L1c
            goto L34
        L1c:
            int r0 = r0.lastEvaluatedBias
            r3 = 40
            if (r0 != r3) goto L27
            int r0 = r1.lastEvaluatedBias
            if (r0 == r3) goto L2c
            goto L34
        L27:
            int r4 = r1.lastEvaluatedBias
            if (r4 != r3) goto L2c
            goto L16
        L2c:
            long r0 = r6.mExecutionStartTimeElapsed
            long r4 = r5.mExecutionStartTimeElapsed
            int r4 = java.lang.Long.compare(r0, r4)
        L34:
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.job.JobConcurrencyManager$$ExternalSyntheticLambda2.compare(java.lang.Object,"
                    + " java.lang.Object):int");
    }
}
