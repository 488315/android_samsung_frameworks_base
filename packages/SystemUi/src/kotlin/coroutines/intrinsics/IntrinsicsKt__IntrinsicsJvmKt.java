package kotlin.coroutines.intrinsics;

import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.RestrictedContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.internal.DispatchedContinuation;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class IntrinsicsKt__IntrinsicsJvmKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final Continuation createCoroutineUnintercepted(final Continuation continuation, final Function2 function2, final Object obj) {
        if (function2 instanceof BaseContinuationImpl) {
            return ((BaseContinuationImpl) function2).create(obj, continuation);
        }
        final CoroutineContext context = continuation.getContext();
        return context == EmptyCoroutineContext.INSTANCE ? new RestrictedContinuationImpl(continuation) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3
            private int label;

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj2) {
                int i = this.label;
                if (i != 0) {
                    if (i != 1) {
                        throw new IllegalStateException("This coroutine had already completed".toString());
                    }
                    this.label = 2;
                    ResultKt.throwOnFailure(obj2);
                    return obj2;
                }
                this.label = 1;
                ResultKt.throwOnFailure(obj2);
                Function2 function22 = function2;
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function22);
                return function22.invoke(obj, this);
            }
        } : new ContinuationImpl(continuation, context) { // from class: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4
            private int label;

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj2) {
                int i = this.label;
                if (i != 0) {
                    if (i != 1) {
                        throw new IllegalStateException("This coroutine had already completed".toString());
                    }
                    this.label = 2;
                    ResultKt.throwOnFailure(obj2);
                    return obj2;
                }
                this.label = 1;
                ResultKt.throwOnFailure(obj2);
                Function2 function22 = function2;
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function22);
                return function22.invoke(obj, this);
            }
        };
    }

    public static final Continuation intercepted(Continuation continuation) {
        ContinuationImpl continuationImpl = continuation instanceof ContinuationImpl ? (ContinuationImpl) continuation : null;
        if (continuationImpl == null) {
            return continuation;
        }
        Continuation continuation2 = continuationImpl.intercepted;
        if (continuation2 != null) {
            return continuation2;
        }
        ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) continuationImpl.getContext().get(ContinuationInterceptor.Key);
        Continuation dispatchedContinuation = continuationInterceptor != null ? new DispatchedContinuation((CoroutineDispatcher) continuationInterceptor, continuationImpl) : continuationImpl;
        continuationImpl.intercepted = dispatchedContinuation;
        return dispatchedContinuation;
    }
}
