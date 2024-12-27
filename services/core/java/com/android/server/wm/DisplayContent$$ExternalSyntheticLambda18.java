package com.android.server.wm;

import java.util.function.Consumer;

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
