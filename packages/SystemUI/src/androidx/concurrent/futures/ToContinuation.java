package androidx.concurrent.futures;

import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import kotlin.Result;
import kotlinx.coroutines.CancellableContinuation;

/* loaded from: classes.dex */
public final class ToContinuation implements Runnable {
    public final CancellableContinuation continuation;
    public final ListenableFuture futureToObserve;

    public ToContinuation(ListenableFuture listenableFuture, CancellableContinuation cancellableContinuation) {
        this.futureToObserve = listenableFuture;
        this.continuation = cancellableContinuation;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.futureToObserve.isCancelled()) {
            this.continuation.cancel(null);
            return;
        }
        try {
            CancellableContinuation cancellableContinuation = this.continuation;
            int i = Result.$r8$clinit;
            cancellableContinuation.resumeWith(AbstractResolvableFuture.getUninterruptibly(this.futureToObserve));
        } catch (ExecutionException e) {
            CancellableContinuation cancellableContinuation2 = this.continuation;
            int i2 = Result.$r8$clinit;
            Throwable cause = e.getCause();
            cause.getClass();
            cancellableContinuation2.resumeWith(new Result.Failure(cause));
        }
    }
}
