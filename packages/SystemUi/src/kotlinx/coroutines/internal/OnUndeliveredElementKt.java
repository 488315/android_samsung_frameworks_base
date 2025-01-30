package kotlinx.coroutines.internal;

import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class OnUndeliveredElementKt {
    public static final Function1 bindCancellationFun(final Function1 function1, final Object obj, final CoroutineContext coroutineContext) {
        return new Function1() { // from class: kotlinx.coroutines.internal.OnUndeliveredElementKt$bindCancellationFun$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                Function1 function12 = Function1.this;
                Object obj3 = obj;
                CoroutineContext coroutineContext2 = coroutineContext;
                UndeliveredElementException callUndeliveredElementCatchingException = OnUndeliveredElementKt.callUndeliveredElementCatchingException(function12, obj3, null);
                if (callUndeliveredElementCatchingException != null) {
                    CoroutineExceptionHandlerKt.handleCoroutineException(callUndeliveredElementCatchingException, coroutineContext2);
                }
                return Unit.INSTANCE;
            }
        };
    }

    public static final UndeliveredElementException callUndeliveredElementCatchingException(Function1 function1, Object obj, UndeliveredElementException undeliveredElementException) {
        try {
            function1.invoke(obj);
        } catch (Throwable th) {
            if (undeliveredElementException == null || undeliveredElementException.getCause() == th) {
                return new UndeliveredElementException("Exception in undelivered element handler for " + obj, th);
            }
            ExceptionsKt__ExceptionsKt.addSuppressed(undeliveredElementException, th);
        }
        return undeliveredElementException;
    }
}
