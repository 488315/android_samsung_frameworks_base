package com.android.server.wm;

import java.util.function.Consumer;

public final /* synthetic */ class LockTaskController$$ExternalSyntheticLambda1
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Task task = (Task) obj;
        task.setLockTaskAuth(task.getRootActivity(true, false));
    }
}
