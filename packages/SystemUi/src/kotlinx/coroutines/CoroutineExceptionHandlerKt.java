package kotlinx.coroutines;

import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.android.AndroidExceptionPreHandler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class CoroutineExceptionHandlerKt {
    public static final void handleCoroutineException(Throwable th, CoroutineContext coroutineContext) {
        try {
            CoroutineExceptionHandler coroutineExceptionHandler = (CoroutineExceptionHandler) coroutineContext.get(CoroutineExceptionHandler.Key);
            if (coroutineExceptionHandler != null) {
                ((AndroidExceptionPreHandler) coroutineExceptionHandler).handleException(coroutineContext, th);
            } else {
                CoroutineExceptionHandlerImplKt.handleCoroutineExceptionImpl(th, coroutineContext);
            }
        } catch (Throwable th2) {
            if (th != th2) {
                RuntimeException runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
                ExceptionsKt__ExceptionsKt.addSuppressed(runtimeException, th);
                th = runtimeException;
            }
            CoroutineExceptionHandlerImplKt.handleCoroutineExceptionImpl(th, coroutineContext);
        }
    }
}
