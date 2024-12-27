package com.android.server.job;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class PendingJobQueue$$ExternalSyntheticLambda0 implements Comparator {
    public final /* synthetic */ int $r8$classId;

    /* JADX WARN: Code restructure failed: missing block: B:14:0x002f, code lost:
    
        if (r6 != false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:?, code lost:
    
        return -1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:?, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x003f, code lost:
    
        if (r6 != false) goto L16;
     */
    @Override // java.util.Comparator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int compare(java.lang.Object r7, java.lang.Object r8) {
        /*
            r6 = this;
            int r6 = r6.$r8$classId
            switch(r6) {
                case 0: goto L6f;
                default: goto L5;
            }
        L5:
            com.android.server.job.PendingJobQueue$AppJobQueue$AdjustedJobStatus r7 = (com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus) r7
            com.android.server.job.PendingJobQueue$AppJobQueue$AdjustedJobStatus r8 = (com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus) r8
            r6 = 0
            if (r7 != r8) goto Ld
            goto L6e
        Ld:
            com.android.server.job.controllers.JobStatus r7 = r7.job
            com.android.server.job.controllers.JobStatus r8 = r8.job
            if (r7 != r8) goto L14
            goto L6e
        L14:
            int r6 = r7.overrideState
            int r0 = r8.overrideState
            if (r6 == r0) goto L1f
            int r6 = java.lang.Integer.compare(r0, r6)
            goto L6e
        L1f:
            android.app.job.JobInfo r6 = r7.job
            boolean r6 = r6.isUserInitiated()
            android.app.job.JobInfo r0 = r8.job
            boolean r0 = r0.isUserInitiated()
            r1 = 1
            r2 = -1
            if (r6 == r0) goto L35
            if (r6 == 0) goto L33
        L31:
            r6 = r2
            goto L6e
        L33:
            r6 = r1
            goto L6e
        L35:
            boolean r6 = r7.isRequestedExpeditedJob()
            boolean r0 = r8.isRequestedExpeditedJob()
            if (r6 == r0) goto L42
            if (r6 == 0) goto L33
            goto L31
        L42:
            java.lang.String r6 = r7.mNamespace
            java.lang.String r0 = r8.mNamespace
            boolean r6 = java.util.Objects.equals(r6, r0)
            if (r6 == 0) goto L5b
            int r6 = r7.getEffectivePriority()
            int r0 = r8.getEffectivePriority()
            if (r6 == r0) goto L5b
            int r6 = java.lang.Integer.compare(r0, r6)
            goto L6e
        L5b:
            int r6 = r7.lastEvaluatedBias
            int r0 = r8.lastEvaluatedBias
            if (r6 == r0) goto L66
            int r6 = java.lang.Integer.compare(r0, r6)
            goto L6e
        L66:
            long r6 = r7.enqueueTime
            long r0 = r8.enqueueTime
            int r6 = java.lang.Long.compare(r6, r0)
        L6e:
            return r6
        L6f:
            com.android.server.job.PendingJobQueue$AppJobQueue r7 = (com.android.server.job.PendingJobQueue.AppJobQueue) r7
            com.android.server.job.PendingJobQueue$AppJobQueue r8 = (com.android.server.job.PendingJobQueue.AppJobQueue) r8
            long r0 = r7.peekNextTimestamp()
            long r2 = r8.peekNextTimestamp()
            r4 = -1
            int r6 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r6 != 0) goto L89
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 != 0) goto L87
            r6 = 0
            goto La2
        L87:
            r6 = 1
            goto La2
        L89:
            int r6 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r6 != 0) goto L8f
            r6 = -1
            goto La2
        L8f:
            int r6 = r7.peekNextOverrideState()
            int r7 = r8.peekNextOverrideState()
            if (r6 == r7) goto L9e
            int r6 = java.lang.Integer.compare(r7, r6)
            goto La2
        L9e:
            int r6 = java.lang.Long.compare(r0, r2)
        La2:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.job.PendingJobQueue$$ExternalSyntheticLambda0.compare(java.lang.Object, java.lang.Object):int");
    }
}
