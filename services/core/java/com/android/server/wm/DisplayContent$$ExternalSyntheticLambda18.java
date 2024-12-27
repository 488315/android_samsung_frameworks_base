package com.android.server.wm;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class DisplayContent$$ExternalSyntheticLambda18 implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DisplayContent$$ExternalSyntheticLambda18(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Task task = (Task) obj;
        switch (this.$r8$classId) {
            case 0:
                task.removeIfPossible("releaseSelfIfNeeded");
                break;
            default:
                task.getRootTask().removeChild(task, "removeAllTasks");
                break;
        }
    }
}
