package kotlinx.coroutines.intrinsics;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.JobSupportKt;
import kotlinx.coroutines.internal.ScopeCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class UndispatchedKt {
    public static final Object startUndispatchedOrReturn(ScopeCoroutine scopeCoroutine, ScopeCoroutine scopeCoroutine2, Function2 function2) {
        Object completedExceptionally;
        Object makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        try {
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
            completedExceptionally = function2.invoke(scopeCoroutine2, scopeCoroutine);
        } catch (Throwable th) {
            completedExceptionally = new CompletedExceptionally(th, false, 2, null);
        }
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (completedExceptionally == coroutineSingletons || (makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = scopeCoroutine.makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(completedExceptionally)) == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return coroutineSingletons;
        }
        scopeCoroutine.afterCompletionUndispatched();
        if (makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CompletedExceptionally) {
            throw ((CompletedExceptionally) makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).cause;
        }
        return JobSupportKt.unboxState(makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
    }
}
