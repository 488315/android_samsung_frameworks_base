package com.android.server.appfunctions;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class AppFunctionExecutors {
    public static final Executor THREAD_POOL_EXECUTOR =
            new ThreadPoolExecutor(
                    Runtime.getRuntime().availableProcessors(),
                    Runtime.getRuntime().availableProcessors(),
                    0,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue(),
                    new NamedThreadFactory("AppFunctionExecutors"));
}
