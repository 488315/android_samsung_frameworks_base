package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.DispatchedContinuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class DebugStringsKt {
    public static final String getHexAddress(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    public static final String toDebugString(Continuation continuation) {
        Object failure;
        if (continuation instanceof DispatchedContinuation) {
            return ((DispatchedContinuation) continuation).toString();
        }
        try {
            int i = Result.$r8$clinit;
            failure = continuation + "@" + getHexAddress(continuation);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        if (Result.m3422exceptionOrNullimpl(failure) != null) {
            failure = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(continuation.getClass().getName(), "@", getHexAddress(continuation));
        }
        return (String) failure;
    }
}
