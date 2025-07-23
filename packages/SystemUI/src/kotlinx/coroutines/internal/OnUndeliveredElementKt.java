package kotlinx.coroutines.internal;

import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class OnUndeliveredElementKt {
    public static final void callUndeliveredElement(Function1 function1, Object obj, CoroutineContext coroutineContext) {
        UndeliveredElementException callUndeliveredElementCatchingException = callUndeliveredElementCatchingException(function1, obj, null);
        if (callUndeliveredElementCatchingException != null) {
            CoroutineExceptionHandlerKt.handleCoroutineException(callUndeliveredElementCatchingException, coroutineContext);
        }
    }

    public static final UndeliveredElementException callUndeliveredElementCatchingException(Function1 function1, Object obj, UndeliveredElementException undeliveredElementException) {
        try {
            function1.mo779invoke(obj);
            return undeliveredElementException;
        } catch (Throwable th) {
            if (undeliveredElementException != null && undeliveredElementException.getCause() != th) {
                ExceptionsKt__ExceptionsKt.addSuppressed(undeliveredElementException, th);
                return undeliveredElementException;
            }
            return new UndeliveredElementException("Exception in undelivered element handler for " + obj, th);
        }
    }
}
