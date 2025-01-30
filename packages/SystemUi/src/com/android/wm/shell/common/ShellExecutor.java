package com.android.wm.shell.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface ShellExecutor extends Executor {
    default void executeBlocking(final Runnable runnable) {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        ((HandlerExecutor) this).execute(new Runnable() { // from class: com.android.wm.shell.common.ShellExecutor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Runnable runnable2 = runnable;
                CountDownLatch countDownLatch2 = countDownLatch;
                runnable2.run();
                countDownLatch2.countDown();
            }
        });
        countDownLatch.await(2, timeUnit);
    }
}
