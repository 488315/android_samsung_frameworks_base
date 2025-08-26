package kotlinx.coroutines.intrinsics;

import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.DispatchException;
import kotlinx.coroutines.internal.DispatchedContinuationKt;

/* loaded from: classes4.dex */
public abstract class CancellableKt {
    public static final void startCoroutineCancellable(Function2 function2, AbstractCoroutine abstractCoroutine, AbstractCoroutine abstractCoroutine2) {
        try {
            Continuation continuationIntercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(function2, abstractCoroutine, abstractCoroutine2));
            int i = Result.$r8$clinit;
            DispatchedContinuationKt.resumeCancellableWith(Unit.INSTANCE, continuationIntercepted);
        } catch (Throwable th) {
            th = th;
            if (th instanceof DispatchException) {
                th = ((DispatchException) th).getCause();
            }
            int i2 = Result.$r8$clinit;
            abstractCoroutine2.resumeWith(new Result.Failure(th));
            throw th;
        }
    }
}
