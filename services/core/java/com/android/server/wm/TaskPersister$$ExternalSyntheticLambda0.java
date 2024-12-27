package com.android.server.wm;

import java.io.File;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TaskPersister$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ TaskPersister$$ExternalSyntheticLambda0(int i, Task task) {
        this.$r8$classId = i;
        this.f$0 = task;
    }

    public /* synthetic */ TaskPersister$$ExternalSyntheticLambda0(String str) {
        this.$r8$classId = 2;
        this.f$0 = str;
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                return ((Task) obj2) == ((TaskPersister.TaskWriteQueueItem) obj).mTask;
            case 1:
                return new File(((TaskPersister.ImageWriteQueueItem) obj).mFilePath)
                        .getName()
                        .startsWith(Integer.toString(((Task) obj2).mTaskId));
            default:
                return ((TaskPersister.ImageWriteQueueItem) obj).mFilePath.equals((String) obj2);
        }
    }
}
