package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

/* loaded from: classes4.dex */
public abstract class BuildersKt {
    public static final DeferredCoroutine async(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2 function2) {
        CoroutineContext newCoroutineContext = CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext);
        coroutineStart.getClass();
        DeferredCoroutine lazyDeferredCoroutine = coroutineStart == CoroutineStart.LAZY ? new LazyDeferredCoroutine(newCoroutineContext, function2) : new DeferredCoroutine(newCoroutineContext, true);
        lazyDeferredCoroutine.start(coroutineStart, lazyDeferredCoroutine, function2);
        return lazyDeferredCoroutine;
    }

    public static /* synthetic */ DeferredCoroutine async$default(CoroutineScope coroutineScope, CoroutineStart coroutineStart, Function2 function2, int i) {
        EmptyCoroutineContext emptyCoroutineContext = EmptyCoroutineContext.INSTANCE;
        if ((i & 2) != 0) {
            coroutineStart = CoroutineStart.DEFAULT;
        }
        return async(coroutineScope, emptyCoroutineContext, coroutineStart, function2);
    }

    public static final StandaloneCoroutine launch(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2 function2) {
        CoroutineContext newCoroutineContext = CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext);
        coroutineStart.getClass();
        StandaloneCoroutine lazyStandaloneCoroutine = coroutineStart == CoroutineStart.LAZY ? new LazyStandaloneCoroutine(newCoroutineContext, function2) : new StandaloneCoroutine(newCoroutineContext, true);
        lazyStandaloneCoroutine.start(coroutineStart, lazyStandaloneCoroutine, function2);
        return lazyStandaloneCoroutine;
    }

    public static /* synthetic */ StandaloneCoroutine launch$default(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2 function2, int i) {
        if ((i & 1) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 2) != 0) {
            coroutineStart = CoroutineStart.DEFAULT;
        }
        return launch(coroutineScope, coroutineContext, coroutineStart, function2);
    }

    public static final Object runBlocking(CoroutineContext coroutineContext, Function2 function2) {
        EventLoop eventLoop;
        CoroutineContext newCoroutineContext;
        Thread currentThread = Thread.currentThread();
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) coroutineContext.get(ContinuationInterceptor.Key);
        if (continuationInterceptor == null) {
            ThreadLocalEventLoop.INSTANCE.getClass();
            eventLoop = ThreadLocalEventLoop.getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            newCoroutineContext = CoroutineContextKt.newCoroutineContext(GlobalScope.INSTANCE, coroutineContext.plus(eventLoop));
        } else {
            if (continuationInterceptor instanceof EventLoop) {
            }
            ThreadLocalEventLoop.INSTANCE.getClass();
            eventLoop = (EventLoop) ThreadLocalEventLoop.ref.get();
            newCoroutineContext = CoroutineContextKt.newCoroutineContext(GlobalScope.INSTANCE, coroutineContext);
        }
        currentThread.getClass();
        BlockingCoroutine blockingCoroutine = new BlockingCoroutine(newCoroutineContext, currentThread, eventLoop);
        blockingCoroutine.start(CoroutineStart.DEFAULT, blockingCoroutine, function2);
        EventLoop eventLoop2 = blockingCoroutine.eventLoop;
        if (eventLoop2 != null) {
            int i = EventLoop.$r8$clinit;
            eventLoop2.incrementUseCount(false);
        }
        while (!Thread.interrupted()) {
            try {
                EventLoop eventLoop3 = blockingCoroutine.eventLoop;
                long processNextEvent = eventLoop3 != null ? eventLoop3.processNextEvent() : Long.MAX_VALUE;
                if (blockingCoroutine.isCompleted()) {
                    EventLoop eventLoop4 = blockingCoroutine.eventLoop;
                    if (eventLoop4 != null) {
                        int i2 = EventLoop.$r8$clinit;
                        eventLoop4.decrementUseCount(false);
                    }
                    Object unboxState = JobSupportKt.unboxState(blockingCoroutine._state.value);
                    CompletedExceptionally completedExceptionally = unboxState instanceof CompletedExceptionally ? (CompletedExceptionally) unboxState : null;
                    if (completedExceptionally == null) {
                        return unboxState;
                    }
                    throw completedExceptionally.cause;
                }
                LockSupport.parkNanos(blockingCoroutine, processNextEvent);
            } catch (Throwable th) {
                EventLoop eventLoop5 = blockingCoroutine.eventLoop;
                if (eventLoop5 != null) {
                    int i3 = EventLoop.$r8$clinit;
                    eventLoop5.decrementUseCount(false);
                }
                throw th;
            }
        }
        InterruptedException interruptedException = new InterruptedException();
        blockingCoroutine.cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(interruptedException);
        throw interruptedException;
    }

    public static final Object withContext(CoroutineContext coroutineContext, Function2 function2, Continuation continuation) {
        Object unboxState;
        CoroutineContext context = continuation.getContext();
        CoroutineContext plus = !((Boolean) coroutineContext.fold(Boolean.FALSE, new CoroutineContextKt$$ExternalSyntheticLambda0(0))).booleanValue() ? context.plus(coroutineContext) : CoroutineContextKt.foldCopies(context, coroutineContext, false);
        JobKt.ensureActive(plus);
        if (plus == context) {
            ScopeCoroutine scopeCoroutine = new ScopeCoroutine(plus, continuation);
            unboxState = UndispatchedKt.startUndispatchedOrReturn(scopeCoroutine, scopeCoroutine, function2);
        } else {
            ContinuationInterceptor.Key key = ContinuationInterceptor.Key;
            if (Intrinsics.areEqual(plus.get(key), context.get(key))) {
                UndispatchedCoroutine undispatchedCoroutine = new UndispatchedCoroutine(plus, continuation);
                CoroutineContext coroutineContext2 = undispatchedCoroutine.context;
                Object updateThreadContext = ThreadContextKt.updateThreadContext(coroutineContext2, null);
                try {
                    Object startUndispatchedOrReturn = UndispatchedKt.startUndispatchedOrReturn(undispatchedCoroutine, undispatchedCoroutine, function2);
                    ThreadContextKt.restoreThreadContext(coroutineContext2, updateThreadContext);
                    unboxState = startUndispatchedOrReturn;
                } catch (Throwable th) {
                    ThreadContextKt.restoreThreadContext(coroutineContext2, updateThreadContext);
                    throw th;
                }
            } else {
                DispatchedCoroutine dispatchedCoroutine = new DispatchedCoroutine(plus, continuation);
                CancellableKt.startCoroutineCancellable(function2, dispatchedCoroutine, dispatchedCoroutine);
                AtomicInt atomicInt = dispatchedCoroutine._decision;
                while (true) {
                    int i = atomicInt.value;
                    if (i != 0) {
                        if (i != 2) {
                            throw new IllegalStateException("Already suspended");
                        }
                        unboxState = JobSupportKt.unboxState(dispatchedCoroutine._state.value);
                        if (unboxState instanceof CompletedExceptionally) {
                            throw ((CompletedExceptionally) unboxState).cause;
                        }
                    } else if (dispatchedCoroutine._decision.compareAndSet(0, 1)) {
                        unboxState = CoroutineSingletons.COROUTINE_SUSPENDED;
                        break;
                    }
                }
            }
        }
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return unboxState;
    }
}
