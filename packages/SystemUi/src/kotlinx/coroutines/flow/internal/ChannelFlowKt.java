package kotlinx.coroutines.flow.internal;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.internal.ThreadContextKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class ChannelFlowKt {
    public static final Object withContextUndispatched(CoroutineContext coroutineContext, Object obj, Object obj2, Function2 function2, Continuation continuation) {
        Object updateThreadContext = ThreadContextKt.updateThreadContext(coroutineContext, obj2);
        try {
            StackFrameContinuation stackFrameContinuation = new StackFrameContinuation(continuation, coroutineContext);
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
            Object invoke = function2.invoke(obj, stackFrameContinuation);
            ThreadContextKt.restoreThreadContext(coroutineContext, updateThreadContext);
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            return invoke;
        } catch (Throwable th) {
            ThreadContextKt.restoreThreadContext(coroutineContext, updateThreadContext);
            throw th;
        }
    }
}
