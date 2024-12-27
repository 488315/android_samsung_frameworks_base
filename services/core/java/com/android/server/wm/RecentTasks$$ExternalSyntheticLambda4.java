package com.android.server.wm;

import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class RecentTasks$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RecentTasks$$ExternalSyntheticLambda4(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((ArrayList) obj2).add((String) obj);
                break;
            case 1:
                ((ArrayList) obj2).remove((String) obj);
                break;
            default:
                RecentTasks recentTasks = (RecentTasks) obj2;
                Task task = (Task) obj;
                recentTasks.getClass();
                task.mDedicatedTask =
                        task.mHostProcessName != null
                                && recentTasks
                                        .mUserToProcs
                                        .get(task.mUserId)
                                        .containsKey(task.mHostProcessName);
                break;
        }
    }
}
