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
        Throwable thM3441exceptionOrNullimpl = Result.m3441exceptionOrNullimpl(obj);
        if (thM3441exceptionOrNullimpl != null) {
            obj = new CompletedExceptionally(thM3441exceptionOrNullimpl, false, 2, null);
        }
        Object objMakeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(obj);
        if (objMakeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return;
        }
        afterResume(objMakeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
    }

    public final void start(CoroutineStart coroutineStart, AbstractCoroutine abstractCoroutine, Function2 function2) {
        Object objInvoke;
        coroutineStart.getClass();
        int i = CoroutineStart.WhenMappings.$EnumSwitchMapping$0[coroutineStart.ordinal()];
        if (i == 1) {
            CancellableKt.startCoroutineCancellable(function2, abstractCoroutine, this);
            return;
        }
        if (i == 2) {
            Continuation continuationIntercepted = IntrinsicsKt__IntrinsicsJvmKt.intercepted(IntrinsicsKt__IntrinsicsJvmKt.createCoroutineUnintercepted(function2, abstractCoroutine, this));
            int i2 = Result.$r8$clinit;
            continuationIntercepted.resumeWith(Unit.INSTANCE);
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
            Object objUpdateThreadContext = ThreadContextKt.updateThreadContext(coroutineContext, null);
            try {
                if (function2 instanceof BaseContinuationImpl) {
                    TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
                    objInvoke = function2.invoke(abstractCoroutine, this);
                } else {
                    objInvoke = IntrinsicsKt__IntrinsicsJvmKt.wrapWithContinuationImpl(function2, abstractCoroutine, this);
                }
                ThreadContextKt.restoreThreadContext(coroutineContext, objUpdateThreadContext);
                if (objInvoke != CoroutineSingletons.COROUTINE_SUSPENDED) {
                    int i3 = Result.$r8$clinit;
                    resumeWith(objInvoke);
                }
            } catch (Throwable th) {
                ThreadContextKt.restoreThreadContext(coroutineContext, objUpdateThreadContext);
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
