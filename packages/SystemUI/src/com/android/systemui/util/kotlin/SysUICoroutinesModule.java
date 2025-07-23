package com.android.systemui.util.kotlin;

import android.os.Handler;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceDataThreadLocal;
import com.android.systemui.util.settings.SettingsSingleThreadBackground;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.ExecutorCoroutineDispatcherImpl;
import kotlinx.coroutines.ExecutorsKt;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.android.HandlerDispatcherKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SysUICoroutinesModule {
    public static final int $stable = 0;

    public final CoroutineScope bgApplicationScope(CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        return new ContextScope(coroutineScope.getCoroutineContext().plus(coroutineContext));
    }

    public final CoroutineDispatcher bgDispatcher() {
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        if (availableProcessors < 1) {
            throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(availableProcessors, "Expected at least one thread, but ", " specified").toString());
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
        newScheduledThreadPool.getClass();
        return new ExecutorCoroutineDispatcherImpl(newScheduledThreadPool);
    }

    public final CoroutineDispatcher notifInflationCoroutineDispatcher(Executor executor, CoroutineDispatcher coroutineDispatcher) {
        return ExecutorsKt.from(executor);
    }

    @SettingsSingleThreadBackground
    public final CoroutineDispatcher settingsBgDispatcher(Handler handler) {
        int i = HandlerDispatcherKt.$r8$clinit;
        return new HandlerContext(handler, "SettingsBg");
    }

    @SettingsSingleThreadBackground
    public final CoroutineScope settingsScope(CoroutineDispatcher coroutineDispatcher) {
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        coroutineDispatcher.getClass();
        return CoroutineScopeKt.CoroutineScope(CoroutineContext.DefaultImpls.plus(coroutineDispatcher, emptyCoroutineContext));
    }

    public final CoroutineDispatcher uiBgDispatcher(Executor executor) {
        return ExecutorsKt.from(executor);
    }

    public final CoroutineContext bgCoroutineContext(CoroutineDispatcher coroutineDispatcher) {
        return coroutineDispatcher;
    }

    public final CoroutineContext uiBgCoroutineContext(CoroutineDispatcher coroutineDispatcher) {
        return coroutineDispatcher;
    }
}
