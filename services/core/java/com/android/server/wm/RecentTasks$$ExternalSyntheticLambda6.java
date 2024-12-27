package com.android.server.wm;

import java.util.function.Function;

public final /* synthetic */ class RecentTasks$$ExternalSyntheticLambda6 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        Task task = (Task) obj;
        WindowProcessController windowProcessController = task.mRootProcess;
        if (windowProcessController != null) {
            task.mHostProcessName = windowProcessController.mName;
        }
        return task.mHostProcessName;
    }
}
