package kotlinx.coroutines;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.internal.DispatchedContinuation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class DebugStringsKt {
    public static final String getClassSimpleName(Object obj) {
        return obj.getClass().getSimpleName();
    }

    public static final String getHexAddress(Object obj) {
        return Integer.toHexString(System.identityHashCode(obj));
    }

    public static final String toDebugString(Continuation continuation) {
        Object failure;
        if (continuation instanceof DispatchedContinuation) {
            return continuation.toString();
        }
        try {
            int i = Result.$r8$clinit;
            failure = continuation + "@" + getHexAddress(continuation);
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        if (Result.m2859exceptionOrNullimpl(failure) != null) {
            failure = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(continuation.getClass().getName(), "@", getHexAddress(continuation));
        }
        return (String) failure;
    }
}
