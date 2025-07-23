package androidx.concurrent.futures;

import com.google.common.util.concurrent.AbstractFuture;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.ExecutionException;
import kotlin.Unit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CancellableContinuationImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ListenableFutureKt {
    public static final Object await(final AbstractFuture abstractFuture, ContinuationImpl continuationImpl) {
        try {
            if (abstractFuture.isDone()) {
                return AbstractResolvableFuture.getUninterruptibly(abstractFuture);
            }
            CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuationImpl), 1);
            cancellableContinuationImpl.initCancellability();
            abstractFuture.addListener(new ToContinuation(abstractFuture, cancellableContinuationImpl), DirectExecutor.INSTANCE);
            cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: androidx.concurrent.futures.ListenableFutureKt$await$2$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj) {
                    ListenableFuture.this.cancel(false);
                    return Unit.INSTANCE;
                }
            });
            Object result = cancellableContinuationImpl.getResult();
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            return result;
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            cause.getClass();
            throw cause;
        }
    }
}
