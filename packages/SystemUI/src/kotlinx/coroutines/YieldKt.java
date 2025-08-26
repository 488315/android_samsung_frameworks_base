package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

/* loaded from: classes4.dex */
public abstract class YieldKt {
    public static final Object yield(ContinuationImpl continuationImpl) {
        Object obj;
        CoroutineContext context = continuationImpl.getContext();
        JobKt.ensureActive(context);
        Continuation continuationIntercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuationImpl);
        DispatchedContinuation dispatchedContinuation = continuationIntercepted instanceof DispatchedContinuation ? (DispatchedContinuation) continuationIntercepted : null;
        if (dispatchedContinuation == null) {
            obj = Unit.INSTANCE;
        } else {
            if (DispatchedContinuationKt.safeIsDispatchNeeded(dispatchedContinuation.dispatcher, context)) {
                dispatchedContinuation._state = Unit.INSTANCE;
                dispatchedContinuation.resumeMode = 1;
                dispatchedContinuation.dispatcher.dispatchYield(context, dispatchedContinuation);
            } else {
                YieldContext yieldContext = new YieldContext();
                CoroutineContext coroutineContextPlus = context.plus(yieldContext);
                Unit unit = Unit.INSTANCE;
                dispatchedContinuation._state = unit;
                dispatchedContinuation.resumeMode = 1;
                dispatchedContinuation.dispatcher.dispatchYield(coroutineContextPlus, dispatchedContinuation);
                if (yieldContext.dispatcherWasUnconfined) {
                    ThreadLocalEventLoop.INSTANCE.getClass();
                    EventLoop eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ThreadLocalEventLoop.getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
                    ArrayDeque arrayDeque = eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.unconfinedQueue;
                    if (arrayDeque != null ? arrayDeque.isEmpty() : true) {
                        obj = Unit.INSTANCE;
                    } else {
                        if (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.useCount >= 4294967296L) {
                            dispatchedContinuation._state = unit;
                            dispatchedContinuation.resumeMode = 1;
                            eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.dispatchUnconfined(dispatchedContinuation);
                            obj = CoroutineSingletons.COROUTINE_SUSPENDED;
                        } else {
                            eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.incrementUseCount(true);
                            try {
                                dispatchedContinuation.run();
                                do {
                                } while (eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host.processUnconfinedEvent());
                            } finally {
                                try {
                                } finally {
                                }
                            }
                            obj = Unit.INSTANCE;
                        }
                    }
                }
            }
            obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        }
        return obj == CoroutineSingletons.COROUTINE_SUSPENDED ? obj : Unit.INSTANCE;
    }
}
