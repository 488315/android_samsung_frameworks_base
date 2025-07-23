package kotlin.coroutines.intrinsics;

import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class IntrinsicsKt__IntrinsicsJvmKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static Continuation createCoroutineUnintercepted(final Function2 function2, final Continuation continuation, final Continuation continuation2) {
        if (function2 instanceof BaseContinuationImpl) {
            return ((BaseContinuationImpl) function2).create(continuation, continuation2);
        }
        final CoroutineContext context = continuation2.getContext();
        return context == EmptyCoroutineContext.INSTANCE ? new RestrictedContinuationImpl(continuation2) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3
            private int label;

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                int i = this.label;
                if (i != 0) {
                    if (i != 1) {
                        throw new IllegalStateException("This coroutine had already completed");
                    }
                    this.label = 2;
                    ResultKt.throwOnFailure(obj);
                    return obj;
                }
                this.label = 1;
                ResultKt.throwOnFailure(obj);
                Function2 function22 = function2;
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function22);
                return function22.invoke(continuation, this);
            }
        } : new ContinuationImpl(continuation2, context) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4
            private int label;

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                int i = this.label;
                if (i != 0) {
                    if (i != 1) {
                        throw new IllegalStateException("This coroutine had already completed");
                    }
                    this.label = 2;
                    ResultKt.throwOnFailure(obj);
                    return obj;
                }
                this.label = 1;
                ResultKt.throwOnFailure(obj);
                Function2 function22 = function2;
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function22);
                return function22.invoke(continuation, this);
            }
        };
    }

    public static Continuation intercepted(Continuation continuation) {
        Continuation intercepted;
        ContinuationImpl continuationImpl = continuation instanceof ContinuationImpl ? (ContinuationImpl) continuation : null;
        return (continuationImpl == null || (intercepted = continuationImpl.intercepted()) == null) ? continuation : intercepted;
    }

    public static Object wrapWithContinuationImpl(Function2 function2, Object obj, final Continuation continuation) {
        final CoroutineContext context = continuation.getContext();
        Object obj2 = context == EmptyCoroutineContext.INSTANCE ? new RestrictedContinuationImpl(continuation) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createSimpleCoroutineForSuspendFunction$1
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj3) {
                ResultKt.throwOnFailure(obj3);
                return obj3;
            }
        } : new ContinuationImpl(continuation, context) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createSimpleCoroutineForSuspendFunction$2
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj3) {
                ResultKt.throwOnFailure(obj3);
                return obj3;
            }
        };
        TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
        return function2.invoke(obj, obj2);
    }
}
