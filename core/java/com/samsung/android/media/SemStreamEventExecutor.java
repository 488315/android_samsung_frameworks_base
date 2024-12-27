package com.samsung.android.media;

import java.util.concurrent.Executor;

/* loaded from: classes6.dex */
public class SemStreamEventExecutor implements Executor {
    @Override // java.util.concurrent.Executor
    public void execute(Runnable command) {
        command.run();
    }
}
