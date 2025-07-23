package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ThreadFactory {
    DelayableExecutor buildDelayableExecutorOnHandler(Handler handler);

    DelayableExecutor buildDelayableExecutorOnLooper(Looper looper);

    DelayableExecutor buildDelayableExecutorOnNewThread(String str);

    Executor buildExecutorOnNewThread(String str);

    Handler buildHandlerOnNewThread(String str);

    Looper buildLooperOnNewThread(String str);
}
