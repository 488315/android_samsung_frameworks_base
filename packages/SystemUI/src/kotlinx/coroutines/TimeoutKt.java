package kotlinx.coroutines;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class TimeoutKt {
    public static final Object setupTimeout(TimeoutCoroutine timeoutCoroutine, Function2 function2) {
        Object completedExceptionally;
        Object makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host;
        JobKt.invokeOnCompletion$default(timeoutCoroutine, new DisposeOnCompletion(DelayKt.getDelay(timeoutCoroutine.uCont.getContext()).invokeOnTimeout(timeoutCoroutine.time, timeoutCoroutine, timeoutCoroutine.context)));
        try {
            if (function2 instanceof BaseContinuationImpl) {
                TypeIntrinsics.beforeCheckcastToFunctionOfArity(2, function2);
                completedExceptionally = function2.invoke(timeoutCoroutine, timeoutCoroutine);
            } else {
                completedExceptionally = IntrinsicsKt__IntrinsicsJvmKt.wrapWithContinuationImpl(function2, timeoutCoroutine, timeoutCoroutine);
            }
        } catch (Throwable th) {
            completedExceptionally = new CompletedExceptionally(th, false, 2, null);
        }
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (completedExceptionally == coroutineSingletons || (makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host = timeoutCoroutine.makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(completedExceptionally)) == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return coroutineSingletons;
        }
        if (makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host instanceof CompletedExceptionally) {
            Throwable th2 = ((CompletedExceptionally) makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host).cause;
            if (!(th2 instanceof TimeoutCancellationException)) {
                throw th2;
            }
            if (((TimeoutCancellationException) th2).coroutine != timeoutCoroutine) {
                throw th2;
            }
            if (completedExceptionally instanceof CompletedExceptionally) {
                throw ((CompletedExceptionally) completedExceptionally).cause;
            }
        } else {
            completedExceptionally = JobSupportKt.unboxState(makeCompletingOnce$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host);
        }
        return completedExceptionally;
    }

    public static final Object withTimeout(long j, Function2 function2, ContinuationImpl continuationImpl) {
        if (j <= 0) {
            throw new TimeoutCancellationException("Timed out immediately");
        }
        Object obj = setupTimeout(new TimeoutCoroutine(j, continuationImpl), function2);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return obj;
    }

    /* renamed from: withTimeout-KLykuaI, reason: not valid java name */
    public static final Object m3451withTimeoutKLykuaI(long j, Function2 function2, ContinuationImpl continuationImpl) {
        return withTimeout(DelayKt.m3450toDelayMillisLRDsOJo(j), function2, continuationImpl);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0067 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r2v1, types: [T, kotlinx.coroutines.TimeoutCoroutine] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object withTimeoutOrNull(long r6, kotlin.jvm.functions.Function2 r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1
            if (r0 == 0) goto L13
            r0 = r9
            kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1 r0 = (kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1 r0 = new kotlinx.coroutines.TimeoutKt$withTimeoutOrNull$1
            r0.<init>(r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L39
            if (r2 != r3) goto L31
            java.lang.Object r6 = r0.L$1
            kotlin.jvm.internal.Ref$ObjectRef r6 = (kotlin.jvm.internal.Ref$ObjectRef) r6
            java.lang.Object r7 = r0.L$0
            kotlin.jvm.functions.Function2 r7 = (kotlin.jvm.functions.Function2) r7
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L2f
            return r9
        L2f:
            r7 = move-exception
            goto L61
        L31:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L39:
            kotlin.ResultKt.throwOnFailure(r9)
            r4 = 0
            int r9 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r9 > 0) goto L43
            goto L67
        L43:
            kotlin.jvm.internal.Ref$ObjectRef r9 = new kotlin.jvm.internal.Ref$ObjectRef
            r9.<init>()
            r0.L$0 = r8     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L5f
            r0.L$1 = r9     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L5f
            r0.J$0 = r6     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L5f
            r0.label = r3     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L5f
            kotlinx.coroutines.TimeoutCoroutine r2 = new kotlinx.coroutines.TimeoutCoroutine     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L5f
            r2.<init>(r6, r0)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L5f
            r9.element = r2     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L5f
            java.lang.Object r6 = setupTimeout(r2, r8)     // Catch: kotlinx.coroutines.TimeoutCancellationException -> L5f
            if (r6 != r1) goto L5e
            return r1
        L5e:
            return r6
        L5f:
            r7 = move-exception
            r6 = r9
        L61:
            kotlinx.coroutines.Job r8 = r7.coroutine
            T r6 = r6.element
            if (r8 != r6) goto L69
        L67:
            r6 = 0
            return r6
        L69:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.TimeoutKt.withTimeoutOrNull(long, kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
