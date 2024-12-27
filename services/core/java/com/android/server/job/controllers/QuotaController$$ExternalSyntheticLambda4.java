package com.android.server.job.controllers;

import android.os.UserHandle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import android.util.proto.ProtoOutputStream;

import com.android.server.job.JobSchedulerService$$ExternalSyntheticLambda5;

import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class QuotaController$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ QuotaController f$0;
    public final /* synthetic */ Predicate f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ QuotaController$$ExternalSyntheticLambda4(
            QuotaController quotaController,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5,
            IndentingPrintWriter indentingPrintWriter) {
        this.f$0 = quotaController;
        this.f$1 = jobSchedulerService$$ExternalSyntheticLambda5;
        this.f$2 = indentingPrintWriter;
    }

    public /* synthetic */ QuotaController$$ExternalSyntheticLambda4(
            QuotaController quotaController,
            JobSchedulerService$$ExternalSyntheticLambda5
                    jobSchedulerService$$ExternalSyntheticLambda5,
            ProtoOutputStream protoOutputStream) {
        this.f$0 = quotaController;
        this.f$1 = jobSchedulerService$$ExternalSyntheticLambda5;
        this.f$2 = protoOutputStream;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                QuotaController quotaController = this.f$0;
                Predicate predicate = this.f$1;
                IndentingPrintWriter indentingPrintWriter = (IndentingPrintWriter) this.f$2;
                ArraySet arraySet = (ArraySet) obj;
                quotaController.getClass();
                for (int i = 0; i < arraySet.size(); i++) {
                    JobStatus jobStatus = (JobStatus) arraySet.valueAt(i);
                    if (predicate.test(jobStatus)) {
                        indentingPrintWriter.print("#");
                        jobStatus.printUniqueId(indentingPrintWriter);
                        indentingPrintWriter.print(" from ");
                        UserHandle.formatUid(indentingPrintWriter, jobStatus.sourceUid);
                        if (quotaController.mTopStartedJobs.contains(jobStatus)) {
                            indentingPrintWriter.print(" (TOP)");
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.print(
                                JobStatus.bucketName(jobStatus.getEffectiveStandbyBucket()));
                        indentingPrintWriter.print(", ");
                        if (jobStatus.shouldTreatAsExpeditedJob()) {
                            indentingPrintWriter.print("within EJ quota");
                        } else if (jobStatus.startedAsExpeditedJob) {
                            indentingPrintWriter.print("out of EJ quota");
                        } else if (jobStatus.isConstraintSatisfied(16777216)) {
                            indentingPrintWriter.print("within regular quota");
                        } else {
                            indentingPrintWriter.print("not within quota");
                        }
                        indentingPrintWriter.print(", ");
                        if (jobStatus.shouldTreatAsExpeditedJob()) {
                            indentingPrintWriter.print(
                                    quotaController.getRemainingEJExecutionTimeLocked(
                                            jobStatus.sourceUserId, jobStatus.sourcePackageName));
                            indentingPrintWriter.print("ms remaining in EJ quota");
                        } else if (jobStatus.startedAsExpeditedJob) {
                            indentingPrintWriter.print(
                                    "should be stopped after min execution time");
                        } else {
                            indentingPrintWriter.print(
                                    quotaController.getRemainingExecutionTimeLocked(jobStatus));
                            indentingPrintWriter.print("ms remaining in quota");
                        }
                        indentingPrintWriter.println();
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                break;
            default:
                QuotaController quotaController2 = this.f$0;
                Predicate predicate2 = this.f$1;
                ProtoOutputStream protoOutputStream = (ProtoOutputStream) this.f$2;
                ArraySet arraySet2 = (ArraySet) obj;
                quotaController2.getClass();
                for (int i2 = 0; i2 < arraySet2.size(); i2++) {
                    JobStatus jobStatus2 = (JobStatus) arraySet2.valueAt(i2);
                    if (predicate2.test(jobStatus2)) {
                        long start = protoOutputStream.start(2246267895812L);
                        jobStatus2.writeToShortProto(protoOutputStream, 1146756268033L);
                        protoOutputStream.write(1120986464258L, jobStatus2.sourceUid);
                        protoOutputStream.write(
                                1159641169923L, jobStatus2.getEffectiveStandbyBucket());
                        protoOutputStream.write(
                                1133871366148L,
                                quotaController2.mTopStartedJobs.contains(jobStatus2));
                        protoOutputStream.write(
                                1133871366149L, jobStatus2.isConstraintSatisfied(16777216));
                        protoOutputStream.write(
                                1112396529670L,
                                quotaController2.getRemainingExecutionTimeLocked(jobStatus2));
                        protoOutputStream.write(
                                1133871366151L, jobStatus2.isRequestedExpeditedJob());
                        protoOutputStream.write(1133871366152L, jobStatus2.mExpeditedQuotaApproved);
                        protoOutputStream.end(start);
                    }
                }
                break;
        }
    }
}
