package com.android.systemui.util.concurrency;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

public interface ThreadFactory {
    DelayableExecutor buildDelayableExecutorOnHandler(Handler handler);

    DelayableExecutor buildDelayableExecutorOnLooper(Looper looper);

    DelayableExecutor buildDelayableExecutorOnNewThread(String str);

    Executor buildExecutorOnNewThread(String str);

    Handler buildHandlerOnNewThread(String str);

    Looper buildLooperOnNewThread(String str);
}
