package androidx.compose.runtime;

import androidx.compose.runtime.BroadcastFrameClock;
import androidx.compose.runtime.internal.AtomicInt;
import java.util.ArrayList;
import java.util.List;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CancellableContinuationImpl;

/* loaded from: classes.dex */
public final class BroadcastFrameClock implements MonotonicFrameClock {
    public List awaiters;
    public Throwable failureCause;
    public final AtomicInt hasAwaitersUnlocked;
    public final Object lock;
    public final Function0 onNewAwaiters;
    public List spareList;

    final class FrameAwaiter<R> {
        public final Continuation continuation;
        public final Function1 onFrame;

        public FrameAwaiter(Function1 function1, Continuation continuation) {
            this.onFrame = function1;
            this.continuation = continuation;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public BroadcastFrameClock() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final Object fold(Object obj, Function2 function2) {
        return function2.invoke(obj, this);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext.Element get(CoroutineContext.Key key) {
        return CoroutineContext.DefaultImpls.get(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext minusKey(CoroutineContext.Key key) {
        return CoroutineContext.DefaultImpls.minusKey(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return CoroutineContext.DefaultImpls.plus(this, coroutineContext);
    }

    public final void sendFrame(long j) {
        Object failure;
        synchronized (this.lock) {
            try {
                List list = this.awaiters;
                this.awaiters = this.spareList;
                this.spareList = list;
                this.hasAwaitersUnlocked.set(0);
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    FrameAwaiter frameAwaiter = (FrameAwaiter) list.get(i);
                    frameAwaiter.getClass();
                    try {
                        int i2 = Result.$r8$clinit;
                        failure = frameAwaiter.onFrame.mo781invoke(Long.valueOf(j));
                    } catch (Throwable th) {
                        int i3 = Result.$r8$clinit;
                        failure = new Result.Failure(th);
                    }
                    frameAwaiter.continuation.resumeWith(failure);
                }
                list.clear();
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    @Override // androidx.compose.runtime.MonotonicFrameClock
    public final Object withFrameNanos(Function1 function1, Continuation continuation) {
        Function0 function0;
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        final FrameAwaiter frameAwaiter = new FrameAwaiter(function1, cancellableContinuationImpl);
        synchronized (this.lock) {
            Throwable th = this.failureCause;
            if (th != null) {
                int i = Result.$r8$clinit;
                cancellableContinuationImpl.resumeWith(new Result.Failure(th));
            } else {
                boolean zIsEmpty = this.awaiters.isEmpty();
                ((ArrayList) this.awaiters).add(frameAwaiter);
                if (zIsEmpty) {
                    this.hasAwaitersUnlocked.set(1);
                }
                cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: androidx.compose.runtime.BroadcastFrameClock$withFrameNanos$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        BroadcastFrameClock broadcastFrameClock = this.this$0;
                        Object obj2 = broadcastFrameClock.lock;
                        BroadcastFrameClock.FrameAwaiter<Object> frameAwaiter2 = frameAwaiter;
                        synchronized (obj2) {
                            broadcastFrameClock.awaiters.remove(frameAwaiter2);
                            if (broadcastFrameClock.awaiters.isEmpty()) {
                                broadcastFrameClock.hasAwaitersUnlocked.set(0);
                            }
                        }
                        return Unit.INSTANCE;
                    }
                });
                if (zIsEmpty && (function0 = this.onNewAwaiters) != null) {
                    try {
                        function0.invoke();
                    } catch (Throwable th2) {
                        synchronized (this.lock) {
                            try {
                                if (this.failureCause == null) {
                                    this.failureCause = th2;
                                    List list = this.awaiters;
                                    int size = list.size();
                                    for (int i2 = 0; i2 < size; i2++) {
                                        Continuation continuation2 = ((FrameAwaiter) ((ArrayList) list).get(i2)).continuation;
                                        int i3 = Result.$r8$clinit;
                                        continuation2.resumeWith(new Result.Failure(th2));
                                    }
                                    ((ArrayList) this.awaiters).clear();
                                    this.hasAwaitersUnlocked.set(0);
                                    Unit unit = Unit.INSTANCE;
                                }
                            } catch (Throwable th3) {
                                throw th3;
                            }
                        }
                    }
                }
            }
        }
        Object result = cancellableContinuationImpl.getResult();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return result;
    }

    public BroadcastFrameClock(Function0 function0) {
        this.onNewAwaiters = function0;
        this.lock = new Object();
        this.awaiters = new ArrayList();
        this.spareList = new ArrayList();
        this.hasAwaitersUnlocked = new AtomicInt(0);
    }

    public /* synthetic */ BroadcastFrameClock(Function0 function0, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : function0);
    }
}
