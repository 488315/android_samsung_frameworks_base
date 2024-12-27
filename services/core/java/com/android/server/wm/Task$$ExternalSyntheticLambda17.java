package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class Task$$ExternalSyntheticLambda17 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Task f$0;

    public /* synthetic */ Task$$ExternalSyntheticLambda17(int i, Task task) {
        this.$r8$classId = i;
        this.f$0 = task;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Task task = this.f$0;
        ActivityRecord activityRecord = (ActivityRecord) obj;
        switch (i) {
            case 0:
                task.cleanUpActivityReferences(activityRecord);
                break;
            default:
                task.getClass();
                if (!activityRecord.finishing) {
                    activityRecord.finishIfPossible("finish-voice", false);
                    task.mAtmService.updateOomAdj();
                    break;
                }
                break;
        }
    }
}
