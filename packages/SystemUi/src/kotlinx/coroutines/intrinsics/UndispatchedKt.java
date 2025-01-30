package kotlinx.coroutines.intrinsics;

import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.JobSupportKt;
import kotlinx.coroutines.internal.ScopeCoroutine;
import kotlinx.coroutines.selects.SelectBuilderImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class UndispatchedKt {
    public static final void startCoroutineUnintercepted(SelectBuilderImpl selectBuilderImpl, Function2 function2, Object obj) {
        try {
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
            Object invoke = function2.invoke(obj, selectBuilderImpl);
            if (invoke != CoroutineSingletons.COROUTINE_SUSPENDED) {
                int i = Result.$r8$clinit;
                selectBuilderImpl.resumeWith(invoke);
            }
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            selectBuilderImpl.resumeWith(new Result.Failure(th));
        }
    }

    public static final Object startUndispatchedOrReturn(ScopeCoroutine scopeCoroutine, ScopeCoroutine scopeCoroutine2, Function2 function2) {
        Object completedExceptionally;
        Object m294xe12a6b8b;
        try {
            TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
            completedExceptionally = function2.invoke(scopeCoroutine2, scopeCoroutine);
        } catch (Throwable th) {
            completedExceptionally = new CompletedExceptionally(th, false, 2, null);
        }
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (completedExceptionally == coroutineSingletons || (m294xe12a6b8b = scopeCoroutine.m294xe12a6b8b(completedExceptionally)) == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return coroutineSingletons;
        }
        if (m294xe12a6b8b instanceof CompletedExceptionally) {
            throw ((CompletedExceptionally) m294xe12a6b8b).cause;
        }
        return JobSupportKt.unboxState(m294xe12a6b8b);
    }
}
