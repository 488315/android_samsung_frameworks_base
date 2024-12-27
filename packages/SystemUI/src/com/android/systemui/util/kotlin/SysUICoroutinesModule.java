package com.android.systemui.util.kotlin;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.ExecutorCoroutineDispatcherImpl;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SysUICoroutinesModule {
    public static final int $stable = 0;

    public final CoroutineScope bgApplicationScope(CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        return new ContextScope(coroutineScope.getCoroutineContext().plus(coroutineContext));
    }

    public final CoroutineContext bgCoroutineContext(CoroutineContext coroutineContext, CoroutineDispatcher coroutineDispatcher) {
        return CoroutineContext.DefaultImpls.plus(coroutineDispatcher, coroutineContext);
    }

    public final CoroutineDispatcher bgDispatcher() {
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        if (availableProcessors < 1) {
            throw new IllegalArgumentException(LazyListMeasuredItem$$ExternalSyntheticOutline0.m(availableProcessors, "Expected at least one thread, but ", " specified").toString());
        }
        final AtomicInteger atomicInteger = new AtomicInteger();
        final String str = "SystemUIBg";
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(availableProcessors, new ThreadFactory() { // from class: kotlinx.coroutines.ThreadPoolDispatcherKt__ThreadPoolDispatcherKt$newFixedThreadPoolContext$executor$1
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                String str2;
                if (availableProcessors == 1) {
                    str2 = str;
                } else {
                    str2 = str + "-" + atomicInteger.incrementAndGet();
                }
                Thread thread = new Thread(runnable, str2);
                thread.setDaemon(true);
                return thread;
            }
        });
        Intrinsics.checkNotNull(newScheduledThreadPool);
        return new ExecutorCoroutineDispatcherImpl(newScheduledThreadPool);
    }
}
