package kotlinx.coroutines;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlinx.coroutines.internal.ArrayQueue;
import kotlinx.coroutines.internal.DispatchedContinuation;
import kotlinx.coroutines.internal.DispatchedContinuationKt;
import kotlinx.coroutines.internal.Symbol;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class YieldKt {
    /* JADX WARN: Removed duplicated region for block: B:28:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object yield(Continuation continuation) {
        Object obj;
        CoroutineContext context = continuation.getContext();
        JobKt.ensureActive(context);
        Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation);
        DispatchedContinuation dispatchedContinuation = intercepted instanceof DispatchedContinuation ? (DispatchedContinuation) intercepted : null;
        if (dispatchedContinuation == null) {
            obj = Unit.INSTANCE;
        } else {
            boolean z = true;
            if (dispatchedContinuation.dispatcher.isDispatchNeeded()) {
                dispatchedContinuation._state = Unit.INSTANCE;
                dispatchedContinuation.resumeMode = 1;
                dispatchedContinuation.dispatcher.dispatchYield(context, dispatchedContinuation);
            } else {
                YieldContext yieldContext = new YieldContext();
                CoroutineContext plus = context.plus(yieldContext);
                Unit unit = Unit.INSTANCE;
                dispatchedContinuation._state = unit;
                dispatchedContinuation.resumeMode = 1;
                dispatchedContinuation.dispatcher.dispatchYield(plus, dispatchedContinuation);
                if (yieldContext.dispatcherWasUnconfined) {
                    Symbol symbol = DispatchedContinuationKt.UNDEFINED;
                    ThreadLocalEventLoop.INSTANCE.getClass();
                    EventLoop m295x4695df28 = ThreadLocalEventLoop.m295x4695df28();
                    ArrayQueue arrayQueue = m295x4695df28.unconfinedQueue;
                    if (!(arrayQueue == null || arrayQueue.head == arrayQueue.tail)) {
                        if (m295x4695df28.isUnconfinedLoopActive()) {
                            dispatchedContinuation._state = unit;
                            dispatchedContinuation.resumeMode = 1;
                            m295x4695df28.dispatchUnconfined(dispatchedContinuation);
                            obj = !z ? CoroutineSingletons.COROUTINE_SUSPENDED : Unit.INSTANCE;
                        } else {
                            m295x4695df28.incrementUseCount(true);
                            try {
                                dispatchedContinuation.run();
                                do {
                                } while (m295x4695df28.processUnconfinedEvent());
                            } finally {
                                try {
                                } finally {
                                }
                            }
                        }
                    }
                    z = false;
                    if (!z) {
                    }
                }
            }
            obj = CoroutineSingletons.COROUTINE_SUSPENDED;
        }
        return obj == CoroutineSingletons.COROUTINE_SUSPENDED ? obj : Unit.INSTANCE;
    }
}
