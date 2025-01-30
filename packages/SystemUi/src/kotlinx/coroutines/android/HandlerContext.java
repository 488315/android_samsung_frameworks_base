package kotlinx.coroutines.android;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.CancellationException;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.NonDisposableHandle;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class HandlerContext extends HandlerDispatcher {
    private volatile HandlerContext _immediate;
    public final Handler handler;
    public final HandlerContext immediate;
    public final boolean invokeImmediately;
    public final String name;

    /* JADX WARN: Multi-variable type inference failed */
    private HandlerContext(Handler handler, String str, boolean z) {
        super(0 == true ? 1 : 0);
        this.handler = handler;
        this.name = str;
        this.invokeImmediately = z;
        this._immediate = z ? this : null;
        HandlerContext handlerContext = this._immediate;
        if (handlerContext == null) {
            handlerContext = new HandlerContext(handler, str, true);
            this._immediate = handlerContext;
        }
        this.immediate = handlerContext;
    }

    public final void cancelOnRejection(CoroutineContext coroutineContext, Runnable runnable) {
        CancellationException cancellationException = new CancellationException("The task was rejected, the handler underlying the dispatcher '" + this + "' was closed");
        Job job = (Job) coroutineContext.get(Job.Key);
        if (job != null) {
            job.cancel(cancellationException);
        }
        Dispatchers.f667IO.dispatch(coroutineContext, runnable);
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final void dispatch(CoroutineContext coroutineContext, Runnable runnable) {
        if (this.handler.post(runnable)) {
            return;
        }
        cancelOnRejection(coroutineContext, runnable);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof HandlerContext) && ((HandlerContext) obj).handler == this.handler;
    }

    @Override // kotlinx.coroutines.MainCoroutineDispatcher
    public final MainCoroutineDispatcher getImmediate() {
        return this.immediate;
    }

    public final int hashCode() {
        return System.identityHashCode(this.handler);
    }

    @Override // kotlinx.coroutines.android.HandlerDispatcher, kotlinx.coroutines.Delay
    public final DisposableHandle invokeOnTimeout(long j, final Runnable runnable, CoroutineContext coroutineContext) {
        if (j > 4611686018427387903L) {
            j = 4611686018427387903L;
        }
        if (this.handler.postDelayed(runnable, j)) {
            return new DisposableHandle() { // from class: kotlinx.coroutines.android.HandlerContext$invokeOnTimeout$1
                @Override // kotlinx.coroutines.DisposableHandle
                public final void dispose() {
                    HandlerContext.this.handler.removeCallbacks(runnable);
                }
            };
        }
        cancelOnRejection(coroutineContext, runnable);
        return NonDisposableHandle.INSTANCE;
    }

    @Override // kotlinx.coroutines.CoroutineDispatcher
    public final boolean isDispatchNeeded() {
        return (this.invokeImmediately && Intrinsics.areEqual(Looper.myLooper(), this.handler.getLooper())) ? false : true;
    }

    @Override // kotlinx.coroutines.Delay
    public final void scheduleResumeAfterDelay(long j, final CancellableContinuationImpl cancellableContinuationImpl) {
        final Runnable runnable = new Runnable() { // from class: kotlinx.coroutines.android.HandlerContext$scheduleResumeAfterDelay$$inlined$Runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                ((CancellableContinuationImpl) CancellableContinuation.this).resumeUndispatched(this, Unit.INSTANCE);
            }
        };
        if (j > 4611686018427387903L) {
            j = 4611686018427387903L;
        }
        if (this.handler.postDelayed(runnable, j)) {
            cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: kotlinx.coroutines.android.HandlerContext$scheduleResumeAfterDelay$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    HandlerContext.this.handler.removeCallbacks(runnable);
                    return Unit.INSTANCE;
                }
            });
        } else {
            cancelOnRejection(cancellableContinuationImpl.context, runnable);
        }
    }

    @Override // kotlinx.coroutines.MainCoroutineDispatcher, kotlinx.coroutines.CoroutineDispatcher
    public final String toString() {
        MainCoroutineDispatcher mainCoroutineDispatcher;
        String str;
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        MainCoroutineDispatcher mainCoroutineDispatcher2 = MainDispatcherLoader.dispatcher;
        if (this == mainCoroutineDispatcher2) {
            str = "Dispatchers.Main";
        } else {
            try {
                mainCoroutineDispatcher = mainCoroutineDispatcher2.getImmediate();
            } catch (UnsupportedOperationException unused) {
                mainCoroutineDispatcher = null;
            }
            str = this == mainCoroutineDispatcher ? "Dispatchers.Main.immediate" : null;
        }
        if (str != null) {
            return str;
        }
        String str2 = this.name;
        if (str2 == null) {
            str2 = this.handler.toString();
        }
        return this.invokeImmediately ? str2.concat(".immediate") : str2;
    }

    public /* synthetic */ HandlerContext(Handler handler, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(handler, (i & 2) != 0 ? null : str);
    }

    public HandlerContext(Handler handler, String str) {
        this(handler, str, false);
    }
}
