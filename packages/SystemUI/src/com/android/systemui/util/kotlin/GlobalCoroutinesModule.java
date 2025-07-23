package com.android.systemui.util.kotlin;

import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceDataThreadLocal;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class GlobalCoroutinesModule {
    public static final int $stable = 0;

    public final CoroutineScope applicationScope(CoroutineContext coroutineContext) {
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        return CoroutineScopeKt.CoroutineScope(coroutineContext.plus(EmptyCoroutineContext.INSTANCE));
    }

    public final CoroutineContext mainCoroutineContext() {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        return MainDispatcherLoader.dispatcher.immediate;
    }

    public final CoroutineDispatcher mainDispatcher() {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        return MainDispatcherLoader.dispatcher.immediate;
    }
}
