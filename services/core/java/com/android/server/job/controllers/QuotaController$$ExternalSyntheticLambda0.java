package com.android.server.job.controllers;

import android.app.usage.UsageEvents;
import android.os.SystemClock;
import android.util.IndentingPrintWriter;
import com.android.server.job.JobSchedulerService;
import com.android.server.job.controllers.QuotaController;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class QuotaController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ QuotaController$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i;
        boolean z;
        int i2 = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i2) {
            case 0:
                QuotaController quotaController = (QuotaController) obj2;
                List list = (List) obj;
                if (list != null) {
                    list.removeIf(quotaController.mTimedEventTooOld);
                    return;
                } else {
                    quotaController.getClass();
                    return;
                }
            default:
                IndentingPrintWriter indentingPrintWriter = (IndentingPrintWriter) obj2;
                QuotaController.TopAppTimer topAppTimer = (QuotaController.TopAppTimer) obj;
                topAppTimer.getClass();
                indentingPrintWriter.print("TopAppTimer{");
                indentingPrintWriter.print(topAppTimer.mPkg);
                indentingPrintWriter.print("} ");
                synchronized (topAppTimer.this$0.mLock) {
                    z = topAppTimer.mActivities.size() > 0;
                }
                if (z) {
                    indentingPrintWriter.print("started at ");
                    indentingPrintWriter.print(topAppTimer.mStartTimeElapsed);
                    indentingPrintWriter.print(" (");
                    JobSchedulerService.sElapsedRealtimeClock.getClass();
                    indentingPrintWriter.print(SystemClock.elapsedRealtime() - topAppTimer.mStartTimeElapsed);
                    indentingPrintWriter.print("ms ago)");
                } else {
                    indentingPrintWriter.print("NOT active");
                }
                indentingPrintWriter.println();
                indentingPrintWriter.increaseIndent();
                for (i = 0; i < topAppTimer.mActivities.size(); i++) {
                    indentingPrintWriter.println(((UsageEvents.Event) topAppTimer.mActivities.valueAt(i)).getClassName());
                }
                indentingPrintWriter.decreaseIndent();
                return;
        }
    }
}
