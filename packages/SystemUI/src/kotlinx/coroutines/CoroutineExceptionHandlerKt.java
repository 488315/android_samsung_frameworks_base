package kotlinx.coroutines;

import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.internal.CoroutineExceptionHandlerImpl_commonKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class CoroutineExceptionHandlerKt {
    public static final void handleCoroutineException(Throwable th, CoroutineContext coroutineContext) {
        if (th instanceof DispatchException) {
            th = ((DispatchException) th).getCause();
        }
        try {
            CoroutineExceptionHandler coroutineExceptionHandler = (CoroutineExceptionHandler) coroutineContext.get(CoroutineExceptionHandler.Key);
            if (coroutineExceptionHandler != null) {
                coroutineExceptionHandler.handleException(th, coroutineContext);
            } else {
                CoroutineExceptionHandlerImpl_commonKt.handleUncaughtCoroutineException(th, coroutineContext);
            }
        } catch (Throwable th2) {
            if (th != th2) {
                RuntimeException runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
                ExceptionsKt__ExceptionsKt.addSuppressed(runtimeException, th);
                th = runtimeException;
            }
            CoroutineExceptionHandlerImpl_commonKt.handleUncaughtCoroutineException(th, coroutineContext);
        }
    }
}
