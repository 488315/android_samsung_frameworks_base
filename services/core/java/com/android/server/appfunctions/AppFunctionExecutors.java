package com.android.server.appfunctions;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
