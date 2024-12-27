package com.android.server.job.controllers;

import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DeviceIdleJobsController$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DeviceIdleJobsController f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DeviceIdleJobsController$$ExternalSyntheticLambda1(DeviceIdleJobsController deviceIdleJobsController, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = deviceIdleJobsController;
        this.f$1 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DeviceIdleJobsController deviceIdleJobsController = this.f$0;
                PrintWriter printWriter = (IndentingPrintWriter) this.f$1;
                JobStatus jobStatus = (JobStatus) obj;
                deviceIdleJobsController.getClass();
                printWriter.print("#");
                jobStatus.printUniqueId(printWriter);
                printWriter.print(" from ");
                UserHandle.formatUid(printWriter, jobStatus.sourceUid);
                printWriter.print(": ");
                printWriter.print(jobStatus.sourcePackageName);
                printWriter.print((jobStatus.satisfiedConstraints & 33554432) != 0 ? " RUNNABLE" : " WAITING");
                if (jobStatus.appHasDozeExemption) {
                    printWriter.print(" WHITELISTED");
                }
                if (deviceIdleJobsController.mAllowInIdleJobs.contains(jobStatus)) {
                    printWriter.print(" ALLOWED_IN_DOZE");
                }
                printWriter.println();
                break;
            default:
                DeviceIdleJobsController deviceIdleJobsController2 = this.f$0;
                ProtoOutputStream protoOutputStream = (ProtoOutputStream) this.f$1;
                JobStatus jobStatus2 = (JobStatus) obj;
                deviceIdleJobsController2.getClass();
                long start = protoOutputStream.start(2246267895810L);
                jobStatus2.writeToShortProto(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1120986464258L, jobStatus2.sourceUid);
                protoOutputStream.write(1138166333443L, jobStatus2.sourcePackageName);
                protoOutputStream.write(1133871366148L, (jobStatus2.satisfiedConstraints & 33554432) != 0);
                protoOutputStream.write(1133871366149L, jobStatus2.appHasDozeExemption);
                protoOutputStream.write(1133871366150L, deviceIdleJobsController2.mAllowInIdleJobs.contains(jobStatus2));
                protoOutputStream.end(start);
                break;
        }
    }
}
