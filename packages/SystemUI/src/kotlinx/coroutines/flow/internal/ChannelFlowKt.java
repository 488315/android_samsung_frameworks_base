package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ChannelFlowKt {
    public static final Object withContextUndispatched(CoroutineContext coroutineContext, Object obj, Object obj2, Function2 function2, Continuation continuation) {
        Object invoke;
        Object updateThreadContext = ThreadContextKt.updateThreadContext(coroutineContext, obj2);
        try {
            StackFrameContinuation stackFrameContinuation = new StackFrameContinuation(continuation, coroutineContext);
            if (function2 instanceof BaseContinuationImpl) {
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
                invoke = function2.invoke(obj, stackFrameContinuation);
            } else {
                invoke = IntrinsicsKt__IntrinsicsJvmKt.wrapWithContinuationImpl(function2, obj, stackFrameContinuation);
            }
            ThreadContextKt.restoreThreadContext(coroutineContext, updateThreadContext);
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            return invoke;
        } catch (Throwable th) {
            ThreadContextKt.restoreThreadContext(coroutineContext, updateThreadContext);
            throw th;
        }
    }
}
