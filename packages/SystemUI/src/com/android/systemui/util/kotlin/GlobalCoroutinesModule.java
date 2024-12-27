package com.android.systemui.util.kotlin;

import com.android.app.tracing.coroutines.TraceContextElementKt;
import com.android.app.tracing.coroutines.TraceDataThreadLocal;
import com.android.systemui.Flags;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

public final class GlobalCoroutinesModule {
    public static final int $stable = 0;

    public final CoroutineScope applicationScope(CoroutineContext coroutineContext) {
        return CoroutineScopeKt.CoroutineScope(coroutineContext);
    }

    public final CoroutineContext mainCoroutineContext(CoroutineContext coroutineContext) {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        MainCoroutineDispatcher immediate = MainDispatcherLoader.dispatcher.getImmediate();
        immediate.getClass();
        return CoroutineContext.DefaultImpls.plus(immediate, coroutineContext);
    }

    public final CoroutineDispatcher mainDispatcher() {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        return MainDispatcherLoader.dispatcher.getImmediate();
    }

    public final CoroutineContext tracingCoroutineContext() {
        TraceDataThreadLocal traceDataThreadLocal = TraceContextElementKt.traceThreadLocal;
        Flags.FEATURE_FLAGS.getClass();
        return EmptyCoroutineContext.INSTANCE;
    }
}
