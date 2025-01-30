package kotlinx.coroutines;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LazyStandaloneCoroutine extends StandaloneCoroutine {
    public final Continuation continuation;

    public LazyStandaloneCoroutine(CoroutineContext coroutineContext, Function2 function2) {
        super(coroutineContext, false);
        this.continuation = IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(this, function2, this);
    }

    @Override // kotlinx.coroutines.JobSupport
    public final void onStart() {
        try {
            Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(this.continuation);
            int i = Result.$r8$clinit;
            DispatchedContinuationKt.resumeCancellableWith(intercepted, Unit.INSTANCE, null);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            resumeWith(new Result.Failure(th));
            throw th;
        }
    }
}
