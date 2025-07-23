package kotlinx.coroutines;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.scheduling.Task;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class DispatchedTask extends Task {
    public int resumeMode;

    public DispatchedTask(int i) {
        this.resumeMode = i;
    }

    public abstract Continuation getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();

    public Throwable getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    public final void handleFatalException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Throwable th) {
        CoroutineExceptionHandlerKt.handleCoroutineException(new CoroutinesInternalError("Fatal exception in coroutines machinery for " + this + ". Please read KDoc to 'handleFatalException' method and report this incident to maintainers", th), getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host().getContext());
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x003d, code lost:
    
        r4 = (kotlinx.coroutines.Job) r5.get(kotlinx.coroutines.Job.Key);
     */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void run() {
        /*
            r11 = this;
            kotlin.coroutines.Continuation r0 = r11.getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            kotlinx.coroutines.internal.DispatchedContinuation r0 = (kotlinx.coroutines.internal.DispatchedContinuation) r0     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            kotlin.coroutines.Continuation r1 = r0.continuation     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            java.lang.Object r0 = r0.countOrElement     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            kotlin.coroutines.CoroutineContext r2 = r1.getContext()     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            java.lang.Object r0 = kotlinx.coroutines.internal.ThreadContextKt.updateThreadContext(r2, r0)     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            kotlinx.coroutines.internal.Symbol r3 = kotlinx.coroutines.internal.ThreadContextKt.NO_THREAD_ELEMENTS     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            r4 = 0
            if (r0 == r3) goto L22
            kotlinx.coroutines.UndispatchedCoroutine r3 = kotlinx.coroutines.CoroutineContextKt.updateUndispatchedCompletion(r1, r2, r0)     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            goto L23
        L1c:
            r0 = move-exception
            goto L92
        L1f:
            r0 = move-exception
            goto L96
        L22:
            r3 = r4
        L23:
            kotlin.coroutines.CoroutineContext r5 = r1.getContext()     // Catch: java.lang.Throwable -> L46
            java.lang.Object r6 = r11.takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()     // Catch: java.lang.Throwable -> L46
            java.lang.Throwable r7 = r11.getExceptionalResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r6)     // Catch: java.lang.Throwable -> L46
            if (r7 != 0) goto L48
            int r8 = r11.resumeMode     // Catch: java.lang.Throwable -> L46
            r9 = 1
            if (r8 == r9) goto L3b
            r10 = 2
            if (r8 != r10) goto L3a
            goto L3b
        L3a:
            r9 = 0
        L3b:
            if (r9 == 0) goto L48
            kotlinx.coroutines.Job$Key r4 = kotlinx.coroutines.Job.Key     // Catch: java.lang.Throwable -> L46
            kotlin.coroutines.CoroutineContext$Element r4 = r5.get(r4)     // Catch: java.lang.Throwable -> L46
            kotlinx.coroutines.Job r4 = (kotlinx.coroutines.Job) r4     // Catch: java.lang.Throwable -> L46
            goto L48
        L46:
            r1 = move-exception
            goto L86
        L48:
            if (r4 == 0) goto L62
            boolean r5 = r4.isActive()     // Catch: java.lang.Throwable -> L46
            if (r5 != 0) goto L62
            java.util.concurrent.CancellationException r4 = r4.getCancellationException()     // Catch: java.lang.Throwable -> L46
            r11.cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r4)     // Catch: java.lang.Throwable -> L46
            int r5 = kotlin.Result.$r8$clinit     // Catch: java.lang.Throwable -> L46
            kotlin.Result$Failure r5 = new kotlin.Result$Failure     // Catch: java.lang.Throwable -> L46
            r5.<init>(r4)     // Catch: java.lang.Throwable -> L46
            r1.resumeWith(r5)     // Catch: java.lang.Throwable -> L46
            goto L78
        L62:
            if (r7 == 0) goto L6f
            int r4 = kotlin.Result.$r8$clinit     // Catch: java.lang.Throwable -> L46
            kotlin.Result$Failure r4 = new kotlin.Result$Failure     // Catch: java.lang.Throwable -> L46
            r4.<init>(r7)     // Catch: java.lang.Throwable -> L46
            r1.resumeWith(r4)     // Catch: java.lang.Throwable -> L46
            goto L78
        L6f:
            int r4 = kotlin.Result.$r8$clinit     // Catch: java.lang.Throwable -> L46
            java.lang.Object r4 = r11.getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r6)     // Catch: java.lang.Throwable -> L46
            r1.resumeWith(r4)     // Catch: java.lang.Throwable -> L46
        L78:
            kotlin.Unit r1 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L46
            if (r3 == 0) goto L82
            boolean r1 = r3.clearThreadContext()     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            if (r1 == 0) goto La5
        L82:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r2, r0)     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            return
        L86:
            if (r3 == 0) goto L8e
            boolean r3 = r3.clearThreadContext()     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
            if (r3 == 0) goto L91
        L8e:
            kotlinx.coroutines.internal.ThreadContextKt.restoreThreadContext(r2, r0)     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
        L91:
            throw r1     // Catch: java.lang.Throwable -> L1c kotlinx.coroutines.DispatchException -> L1f
        L92:
            r11.handleFatalException$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r0)
            goto La5
        L96:
            kotlin.coroutines.Continuation r11 = r11.getDelegate$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host()
            kotlin.coroutines.CoroutineContext r11 = r11.getContext()
            java.lang.Throwable r0 = r0.getCause()
            kotlinx.coroutines.CoroutineExceptionHandlerKt.handleCoroutineException(r0, r11)
        La5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.DispatchedTask.run():void");
    }

    public abstract Object takeState$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host();

    public void cancelCompletedResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Throwable th) {
    }

    public Object getSuccessfulResult$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(Object obj) {
        return obj;
    }
}
