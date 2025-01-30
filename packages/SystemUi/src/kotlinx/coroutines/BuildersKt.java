package kotlinx.coroutines;

import java.util.concurrent.locks.LockSupport;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.atomicfu.AtomicInt;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class BuildersKt {
    public static final StandaloneCoroutine launch(CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineStart coroutineStart, Function2 function2) {
        CoroutineContext newCoroutineContext = CoroutineContextKt.newCoroutineContext(coroutineScope, coroutineContext);
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
            eventLoop = ThreadLocalEventLoop.m295x4695df28();
            newCoroutineContext = CoroutineContextKt.newCoroutineContext(GlobalScope.INSTANCE, coroutineContext.plus(eventLoop));
        } else {
            if (continuationInterceptor instanceof EventLoop) {
            }
            ThreadLocalEventLoop.INSTANCE.getClass();
            eventLoop = (EventLoop) ThreadLocalEventLoop.ref.get();
            newCoroutineContext = CoroutineContextKt.newCoroutineContext(GlobalScope.INSTANCE, coroutineContext);
        }
        BlockingCoroutine blockingCoroutine = new BlockingCoroutine(newCoroutineContext, currentThread, eventLoop);
        blockingCoroutine.start(CoroutineStart.DEFAULT, blockingCoroutine, function2);
        EventLoop eventLoop2 = blockingCoroutine.eventLoop;
        if (eventLoop2 != null) {
            int i = EventLoop.$r8$clinit;
            eventLoop2.incrementUseCount(false);
        }
        while (!Thread.interrupted()) {
            try {
                long processNextEvent = eventLoop2 != null ? eventLoop2.processNextEvent() : Long.MAX_VALUE;
                if (!(blockingCoroutine.m293x8adbf455() instanceof Incomplete)) {
                    Object unboxState = JobSupportKt.unboxState(blockingCoroutine.m293x8adbf455());
                    CompletedExceptionally completedExceptionally = unboxState instanceof CompletedExceptionally ? (CompletedExceptionally) unboxState : null;
                    if (completedExceptionally == null) {
                        return unboxState;
                    }
                    throw completedExceptionally.cause;
                }
                LockSupport.parkNanos(blockingCoroutine, processNextEvent);
            } finally {
                if (eventLoop2 != null) {
                    int i2 = EventLoop.$r8$clinit;
                    eventLoop2.decrementUseCount(false);
                }
            }
        }
        InterruptedException interruptedException = new InterruptedException();
        blockingCoroutine.m292x7bd83b56(interruptedException);
        throw interruptedException;
    }

    public static final Object withContext(CoroutineContext coroutineContext, Function2 function2, Continuation continuation) {
        Object unboxState;
        CoroutineContext context = continuation.getContext();
        boolean z = false;
        CoroutineContext plus = !((Boolean) coroutineContext.fold(Boolean.FALSE, CoroutineContextKt$hasCopyableElements$1.INSTANCE)).booleanValue() ? context.plus(coroutineContext) : CoroutineContextKt.foldCopies(context, coroutineContext, false);
        JobKt.ensureActive(plus);
        if (plus == context) {
            ScopeCoroutine scopeCoroutine = new ScopeCoroutine(plus, continuation);
            unboxState = UndispatchedKt.startUndispatchedOrReturn(scopeCoroutine, scopeCoroutine, function2);
        } else {
            ContinuationInterceptor.Key key = ContinuationInterceptor.Key;
            if (Intrinsics.areEqual(plus.get(key), context.get(key))) {
                UndispatchedCoroutine undispatchedCoroutine = new UndispatchedCoroutine(plus, continuation);
                Object updateThreadContext = ThreadContextKt.updateThreadContext(plus, null);
                try {
                    Object startUndispatchedOrReturn = UndispatchedKt.startUndispatchedOrReturn(undispatchedCoroutine, undispatchedCoroutine, function2);
                    ThreadContextKt.restoreThreadContext(plus, updateThreadContext);
                    unboxState = startUndispatchedOrReturn;
                } catch (Throwable th) {
                    ThreadContextKt.restoreThreadContext(plus, updateThreadContext);
                    throw th;
                }
            } else {
                DispatchedCoroutine dispatchedCoroutine = new DispatchedCoroutine(plus, continuation);
                try {
                    Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(dispatchedCoroutine, function2, dispatchedCoroutine));
                    int i = Result.$r8$clinit;
                    DispatchedContinuationKt.resumeCancellableWith(intercepted, Unit.INSTANCE, null);
                    AtomicInt atomicInt = dispatchedCoroutine._decision;
                    while (true) {
                        int i2 = atomicInt.value;
                        if (i2 != 0) {
                            if (i2 != 2) {
                                throw new IllegalStateException("Already suspended".toString());
                            }
                        } else if (dispatchedCoroutine._decision.compareAndSet(0, 1)) {
                            z = true;
                            break;
                        }
                    }
                    if (z) {
                        unboxState = CoroutineSingletons.COROUTINE_SUSPENDED;
                    } else {
                        unboxState = JobSupportKt.unboxState(dispatchedCoroutine.m293x8adbf455());
                        if (unboxState instanceof CompletedExceptionally) {
                            throw ((CompletedExceptionally) unboxState).cause;
                        }
                    }
                } catch (Throwable th2) {
                    int i3 = Result.$r8$clinit;
                    dispatchedCoroutine.resumeWith(new Result.Failure(th2));
                    throw th2;
                }
            }
        }
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return unboxState;
    }
}
