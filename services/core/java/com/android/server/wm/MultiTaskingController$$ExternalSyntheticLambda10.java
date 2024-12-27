package com.android.server.wm;

import android.app.ActivityManager;
import android.util.Slog;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class MultiTaskingController$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ MultiTaskingController$$ExternalSyntheticLambda10(MultiTaskingController multiTaskingController, PrintWriter printWriter) {
        this.$r8$classId = 1;
        this.f$1 = multiTaskingController;
        this.f$2 = printWriter;
        this.f$0 = "  ";
    }

    public /* synthetic */ MultiTaskingController$$ExternalSyntheticLambda10(String str, ArrayList arrayList, ArrayList arrayList2) {
        this.$r8$classId = 0;
        this.f$0 = str;
        this.f$1 = arrayList;
        this.f$2 = arrayList2;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                String str = this.f$0;
                ArrayList arrayList = (ArrayList) this.f$1;
                ArrayList arrayList2 = (ArrayList) this.f$2;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                String str2 = activityRecord.packageName;
                if (str2 != null && str2.equals(str) && !arrayList.contains(Integer.valueOf(activityRecord.task.mTaskId))) {
                    ActivityManager.RecentTaskInfo recentTaskInfo = new ActivityManager.RecentTaskInfo();
                    activityRecord.task.fillTaskInfo(recentTaskInfo, true);
                    arrayList2.add(recentTaskInfo);
                    arrayList.add(Integer.valueOf(recentTaskInfo.taskId));
                    Slog.d("MultiTaskingController", "getTaskIdFromPackageName, recentTaskInfo=" + recentTaskInfo);
                    break;
                }
                break;
            default:
                MultiTaskingController multiTaskingController = (MultiTaskingController) this.f$1;
                PrintWriter printWriter = (PrintWriter) this.f$2;
                String str3 = this.f$0;
                multiTaskingController.getClass();
                MultiTaskingController.printTaskDisplayAreaLocked(printWriter, str3 + "  ", (TaskDisplayArea) obj);
                break;
        }
    }
}
