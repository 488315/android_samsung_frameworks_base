package kotlinx.coroutines;

import kotlin.NoWhenBranchMatchedException;
import kotlin.Result;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.intrinsics.CancellableKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class AbstractCoroutine extends JobSupport implements Continuation, CoroutineScope {
    public final CoroutineContext context;

    public AbstractCoroutine(CoroutineContext coroutineContext, boolean z, boolean z2) {
        super(z2);
        if (z) {
            initParentJob((Job) coroutineContext.get(Job.Key));
        }
        this.context = coroutineContext.plus(this);
    }

    @Override // kotlinx.coroutines.JobSupport
    public final String cancellationExceptionMessage() {
        return getClass().getSimpleName().concat(" was cancelled");
    }

    @Override // kotlin.coroutines.Continuation
    public final CoroutineContext getContext() {
        return this.context;
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public final CoroutineContext getCoroutineContext() {
        return this.context;
    }

    @Override // kotlinx.coroutines.JobSupport
    public final void handleOnCompletionException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(CompletionHandlerException completionHandlerException) {
        CoroutineExceptionHandlerKt.handleCoroutineException(completionHandlerException, this.context);
    }

    @Override // kotlinx.coroutines.JobSupport
    public final void onCompletionInternal(Object obj) {
        if (!(obj instanceof CompletedExceptionally)) {
            onCompleted(obj);
        } else {
            CompletedExceptionally completedExceptionally = (CompletedExceptionally) obj;
            onCancelled(completedExceptionally.cause, completedExceptionally._handled.getValue());
        }
    }

    @Override // kotlin.coroutines.Continuation
    public final void resumeWith(Object obj) {
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(obj);
        if (m3422exceptionOrNullimpl != null) {
            obj = new CompletedExceptionally(m3422exceptionOrNullimpl, false, 2, null);
        }
        Object makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj);
        if (makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return;
        }
        afterResume(makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
    }

    public final void start(CoroutineStart coroutineStart, AbstractCoroutine abstractCoroutine, Function2 function2) {
        Object invoke;
        coroutineStart.getClass();
        int i = CoroutineStart.WhenMappings.$EnumSwitchMapping$0[coroutineStart.ordinal()];
        if (i == 1) {
            CancellableKt.startCoroutineCancellable(function2, abstractCoroutine, this);
            return;
        }
        if (i == 2) {
            Continuation intercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(function2, abstractCoroutine, this));
            int i2 = Result.$r8$clinit;
            intercepted.resumeWith(Unit.INSTANCE);
            return;
        }
        if (i != 3) {
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            return;
        }
        try {
            CoroutineContext coroutineContext = this.context;
            Object updateThreadContext = ThreadContextKt.updateThreadContext(coroutineContext, null);
            try {
                if (function2 instanceof BaseContinuationImpl) {
                    TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
                    invoke = function2.invoke(abstractCoroutine, this);
                } else {
                    invoke = IntrinsicsKt__IntrinsicsJvmKt.wrapWithContinuationImpl(function2, abstractCoroutine, this);
                }
                ThreadContextKt.restoreThreadContext(coroutineContext, updateThreadContext);
                if (invoke != CoroutineSingletons.COROUTINE_SUSPENDED) {
                    int i3 = Result.$r8$clinit;
                    resumeWith(invoke);
                }
            } catch (Throwable th) {
                ThreadContextKt.restoreThreadContext(coroutineContext, updateThreadContext);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            if (th instanceof DispatchException) {
                th = ((DispatchException) th).getCause();
            }
            int i4 = Result.$r8$clinit;
            resumeWith(new Result.Failure(th));
        }
    }

    public void onCompleted(Object obj) {
    }

    public void onCancelled(Throwable th, boolean z) {
    }
}
