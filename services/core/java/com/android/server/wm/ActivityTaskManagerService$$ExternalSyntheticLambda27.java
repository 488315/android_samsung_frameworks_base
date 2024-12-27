package com.android.server.wm;

import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

public final /* synthetic */ class ActivityTaskManagerService$$ExternalSyntheticLambda27
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ActivityTaskManagerService$$ExternalSyntheticLambda27(
            int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((TaskFragment) obj).dumpInner((PrintWriter) obj2, "  ", null, true);
                break;
            default:
                List list = (List) obj2;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                if (!activityRecord.finishing) {
                    list.add(activityRecord);
                    break;
                }
                break;
        }
    }
}
