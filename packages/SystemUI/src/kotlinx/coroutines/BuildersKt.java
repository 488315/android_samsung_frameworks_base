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
        CoroutineContext coroutineContextNewCoroutineContext = CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext);
        coroutineStart.getClass();
        DeferredCoroutine lazyDeferredCoroutine = coroutineStart == CoroutineStart.LAZY ? new LazyDeferredCoroutine(coroutineContextNewCoroutineContext, function2) : new DeferredCoroutine(coroutineContextNewCoroutineContext, true);
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
        CoroutineContext coroutineContextNewCoroutineContext = CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext);
        coroutineStart.getClass();
        StandaloneCoroutine lazyStandaloneCoroutine = coroutineStart == CoroutineStart.LAZY ? new LazyStandaloneCoroutine(coroutineContextNewCoroutineContext, function2) : new StandaloneCoroutine(coroutineContextNewCoroutineContext, true);
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

    public static final Object runBlocking(CoroutineContext coroutineContext, Function2 function2) throws Throwable {
        EventLoop eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        CoroutineContext coroutineContextNewCoroutineContext;
        Thread threadCurrentThread = Thread.currentThread();
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) coroutineContext.get(ContinuationInterceptor.Key);
        if (continuationInterceptor == null) {
            ThreadLocalEventLoop.INSTANCE.getClass();
            eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = ThreadLocalEventLoop.getEventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();
            coroutineContextNewCoroutineContext = CoroutineContextKt.newCoroutineContext(GlobalScope.INSTANCE, coroutineContext.plus(eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host));
        } else {
            if (continuationInterceptor instanceof EventLoop) {
            }
            ThreadLocalEventLoop.INSTANCE.getClass();
            eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = (EventLoop) ThreadLocalEventLoop.ref.get();
            coroutineContextNewCoroutineContext = CoroutineContextKt.newCoroutineContext(GlobalScope.INSTANCE, coroutineContext);
        }
        threadCurrentThread.getClass();
        BlockingCoroutine blockingCoroutine = new BlockingCoroutine(coroutineContextNewCoroutineContext, threadCurrentThread, eventLoop$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
        blockingCoroutine.start(CoroutineStart.DEFAULT, blockingCoroutine, function2);
        EventLoop eventLoop = blockingCoroutine.eventLoop;
        if (eventLoop != null) {
            int i = EventLoop.$r8$clinit;
            eventLoop.incrementUseCount(false);
        }
        while (!Thread.interrupted()) {
            try {
                EventLoop eventLoop2 = blockingCoroutine.eventLoop;
                long jProcessNextEvent = eventLoop2 != null ? eventLoop2.processNextEvent() : Long.MAX_VALUE;
                if (blockingCoroutine.isCompleted()) {
                    EventLoop eventLoop3 = blockingCoroutine.eventLoop;
                    if (eventLoop3 != null) {
                        int i2 = EventLoop.$r8$clinit;
                        eventLoop3.decrementUseCount(false);
                    }
                    Object objUnboxState = JobSupportKt.unboxState(blockingCoroutine._state.value);
                    CompletedExceptionally completedExceptionally = objUnboxState instanceof CompletedExceptionally ? (CompletedExceptionally) objUnboxState : null;
                    if (completedExceptionally == null) {
                        return objUnboxState;
                    }
                    throw completedExceptionally.cause;
                }
                LockSupport.parkNanos(blockingCoroutine, jProcessNextEvent);
            } catch (Throwable th) {
                EventLoop eventLoop4 = blockingCoroutine.eventLoop;
                if (eventLoop4 != null) {
                    int i3 = EventLoop.$r8$clinit;
                    eventLoop4.decrementUseCount(false);
                }
                throw th;
            }
        }
        InterruptedException interruptedException = new InterruptedException();
        blockingCoroutine.cancelImpl$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(interruptedException);
        throw interruptedException;
    }

    public static final Object withContext(CoroutineContext coroutineContext, Function2 function2, Continuation continuation) throws Throwable {
        Object objUnboxState;
        CoroutineContext context = continuation.getContext();
        CoroutineContext coroutineContextPlus = !((Boolean) coroutineContext.fold(Boolean.FALSE, new CoroutineContextKt$$ExternalSyntheticLambda0(0))).booleanValue() ? context.plus(coroutineContext) : CoroutineContextKt.foldCopies(context, coroutineContext, false);
        JobKt.ensureActive(coroutineContextPlus);
        if (coroutineContextPlus == context) {
            ScopeCoroutine scopeCoroutine = new ScopeCoroutine(coroutineContextPlus, continuation);
            objUnboxState = UndispatchedKt.startUndispatchedOrReturn(scopeCoroutine, scopeCoroutine, function2);
        } else {
            ContinuationInterceptor.Key key = ContinuationInterceptor.Key;
            if (Intrinsics.areEqual(coroutineContextPlus.get(key), context.get(key))) {
                UndispatchedCoroutine undispatchedCoroutine = new UndispatchedCoroutine(coroutineContextPlus, continuation);
                CoroutineContext coroutineContext2 = undispatchedCoroutine.context;
                Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(coroutineContext2, null);
                try {
                    Object objStartUndispatchedOrReturn = UndispatchedKt.startUndispatchedOrReturn(undispatchedCoroutine, undispatchedCoroutine, function2);
                    ThreadContextKt.restoreThreadContext(coroutineContext2, objUpdateThreadContext);
                    objUnboxState = objStartUndispatchedOrReturn;
                } catch (Throwable th) {
                    ThreadContextKt.restoreThreadContext(coroutineContext2, objUpdateThreadContext);
                    throw th;
                }
            } else {
                DispatchedCoroutine dispatchedCoroutine = new DispatchedCoroutine(coroutineContextPlus, continuation);
                CancellableKt.startCoroutineCancellable(function2, dispatchedCoroutine, dispatchedCoroutine);
                AtomicInt atomicInt = dispatchedCoroutine._decision;
                while (true) {
                    int i = atomicInt.value;
                    if (i != 0) {
                        if (i != 2) {
                            throw new IllegalStateException("Already suspended");
                        }
                        objUnboxState = JobSupportKt.unboxState(dispatchedCoroutine._state.value);
                        if (objUnboxState instanceof CompletedExceptionally) {
                            throw ((CompletedExceptionally) objUnboxState).cause;
                        }
                    } else if (dispatchedCoroutine._decision.compareAndSet(0, 1)) {
                        objUnboxState = CoroutineSingletons.COROUTINE_SUSPENDED;
                        break;
                    }
                }
            }
        }
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return objUnboxState;
    }
}
