package kotlinx.coroutines;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LazyStandaloneCoroutine extends StandaloneCoroutine {
    public final Continuation continuation;

    public LazyStandaloneCoroutine(CoroutineContext coroutineContext, Function2 function2) {
        super(coroutineContext, false);
        this.continuation = IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(function2, this, this);
    }

    @Override // kotlinx.coroutines.JobSupport
    public final void onStart() {
        try {
            Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(this.continuation);
            int i = Result.$r8$clinit;
            DispatchedContinuationKt.resumeCancellableWith(Unit.INSTANCE, intercepted);
        } catch (Throwable th) {
            th = th;
            if (th instanceof DispatchException) {
                th = ((DispatchException) th).getCause();
            }
            int i2 = Result.$r8$clinit;
            resumeWith(new Result.Failure(th));
            throw th;
        }
    }
}
