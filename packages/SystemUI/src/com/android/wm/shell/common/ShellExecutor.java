package com.android.wm.shell.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ShellExecutor extends Executor {
    default void executeBlocking(final Runnable runnable) {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        execute(new Runnable() { // from class: com.android.wm.shell.common.ShellExecutor$$ExternalSyntheticLambda0
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
