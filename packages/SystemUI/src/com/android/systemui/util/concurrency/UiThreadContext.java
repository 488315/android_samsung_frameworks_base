package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import com.android.systemui.util.Assert;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;

public final class UiThreadContext {
    public static final int $stable = 8;
    private final Choreographer choreographer;
    private final Executor executor;
    private final Handler handler;
    private final Looper looper;

    public UiThreadContext(Looper looper, Handler handler, Executor executor, Choreographer choreographer) {
        this.looper = looper;
        this.handler = handler;
        this.executor = executor;
        this.choreographer = choreographer;
    }

    public final Choreographer getChoreographer() {
        return this.choreographer;
    }

    public final Executor getExecutor() {
        return this.executor;
    }

    public final Handler getHandler() {
        return this.handler;
    }

    public final Looper getLooper() {
        return this.looper;
    }

    public final void isCurrentThread() {
        Assert.isCurrentThread(this.looper);
    }

    public final <T> T runWithScissors(Function0 function0) {
        return (T) UiThreadContextKt.runWithScissors(this.handler, function0);
    }

    public final void runWithScissors(Runnable runnable) {
        this.handler.runWithScissors(runnable, 150L);
    }
}
