package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class NonCancellable extends AbstractCoroutineContextElement implements Job {
    public static final NonCancellable INSTANCE = new NonCancellable();

    private NonCancellable() {
        super(Job.Key);
    }

    @Override // kotlinx.coroutines.Job
    public final ChildHandle attachChild(JobSupport jobSupport) {
        return NonDisposableHandle.INSTANCE;
    }

    @Override // kotlinx.coroutines.Job
    public final CancellationException getCancellationException() {
        throw new IllegalStateException("This job is always active");
    }

    @Override // kotlinx.coroutines.Job
    public final DisposableHandle invokeOnCompletion(Function1 function1) {
        return NonDisposableHandle.INSTANCE;
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isActive() {
        return true;
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isCancelled$1() {
        return false;
    }

    @Override // kotlinx.coroutines.Job
    public final Object join(ContinuationImpl continuationImpl) {
        throw new UnsupportedOperationException("This job is always active");
    }

    @Override // kotlinx.coroutines.Job
    public final boolean start() {
        return false;
    }

    public final String toString() {
        return "NonCancellable";
    }

    @Override // kotlinx.coroutines.Job
    public final DisposableHandle invokeOnCompletion(boolean z, boolean z2, Function1 function1) {
        return NonDisposableHandle.INSTANCE;
    }

    @Override // kotlinx.coroutines.Job
    public final void cancel(CancellationException cancellationException) {
    }
}
